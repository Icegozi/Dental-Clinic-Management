/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mynewteeth;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import mynewteeth.frontend.DashboardFrame;
import mynewteeth.frontend.DialogProvider;
import mynewteeth.frontend.LoginFrame;

/**
 *
 * @author Us
 */
public class Processor {

    public Processor() {
        
    }

    private static final String APP_CONFIG_URI = "src/mynewteeth/backend/configs/appconfig.txt";
    private static final String ACCOUNT_FILE_URI = "src/mynewteeth/backend/data_repository/local_data/raw_data/taikhoan.txt";
    private static LoginFrame loginFrame;

    public static void main(String[] args) {
        loginAutomatically();
    }

    private static FileInputStream fileInputStream = null;
    private static BufferedReader bufferedReader = null;

    private static void loginAutomatically() {
        loginFrame = new LoginFrame();
        loginFrame.setTitle("Login");
        loginFrame.getContentPane().setBackground(Color.WHITE);
        loginFrame.setResizable(false);
        loginFrame.setLocationRelativeTo(null);

        // Check condition 
        // read file app config and file taikhoan , then compare them , if match , navigate user to dashboard and show login frame otherwise
        new Thread(() -> {
            String[] autoLoginConfigs = new String[3];
            String[] accountInfos = new String[4];
            try {
                // read config file
                fileInputStream = new FileInputStream(APP_CONFIG_URI);
                bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                int i = 0;
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.equals("") || i == 2 && !Boolean.parseBoolean(line)) {
                        // show login frame
                        loginFrame.setVisible(true);
                        closeReadingFileResources();
                        return;
                    }
                    autoLoginConfigs[i++] = line;
                }

                // if saved acc is ok , then check whether it is matchs with any account in file taikhoan
                fileInputStream = new FileInputStream(ACCOUNT_FILE_URI);
                bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                while ((line = bufferedReader.readLine()) != null) {
                    accountInfos = line.split("#");
                    if (accountInfos[0].equals(autoLoginConfigs[0])) {
                        if (accountInfos[1].equals(autoLoginConfigs[1])) {
                            if (Integer.parseInt(accountInfos[3]) == 1) {
                                DashboardFrame d = new DashboardFrame();
                                d.setTitle("Dashboard");
                                d.getContentPane().setBackground(Color.WHITE);
                                d.setResizable(false);
                                d.setLocationRelativeTo(null);
                                d.setVisible(true);
                                loginFrame.saveUserLogStatus(accountInfos[0], accountInfos[1]);
                                loginFrame.dispose();
                                closeReadingFileResources();
                                return;
                            }
                            DialogProvider.showMessageDialog("Tài khoản này đã bị chặn truy cập !", "Thông báo !");
                            break;
                        }
                        DialogProvider.showMessageDialog("Thông tin tài khoản này đã được thay đổi gần đây ! \n Đăng nhập lại để tiếp tục !", "Thông báo !");
                        break;
                    }
                }
                loginFrame.setVisible(true);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Processor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Processor.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                closeReadingFileResources();
            }
        }).start();
    }

    private static void closeReadingFileResources() {
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                Logger.getLogger(Processor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (fileInputStream != null) {
            try {
                fileInputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(Processor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
