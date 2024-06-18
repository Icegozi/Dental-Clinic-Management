/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package mynewteeth.frontend;

import java.awt.Color;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author Us
 */
public class LoginFrame extends javax.swing.JFrame {

    /**
     * Creates new form LoginFrame
     */
    public LoginFrame() {
        initComponents();
        loadAccount();
        myUiInit();
        initFrameBehavior();
    }

    private void myUiInit() {
        // login image
        ImageIcon bannerImgIcon = new ImageIcon("src/mynewteeth/backend/data_repository/assets/app_images/anh_rang.PNG");
        avatar_image_label.setIcon(new ImageIcon(bannerImgIcon.getImage().getScaledInstance(430, 480, Image.SCALE_SMOOTH)));
        // app logo
        ImageIcon logoImgIcon = new ImageIcon("src/mynewteeth/backend/data_repository/assets/app_images/new_teeth_logo.PNG");
        logo_image_label.setIcon(new ImageIcon(logoImgIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
    }

    private static final String ACCOUNT_FILE_URI = "src/mynewteeth/backend/data_repository/local_data/raw_data/taikhoan.txt";
    private static final String APP_CONFIG_URI = "src/mynewteeth/backend/configs/appconfig.txt";
    private static final String USER_LOG_STATUS = "src/mynewteeth/backend/configs/userlogstatus.txt";
    private FileInputStream fileInputStream = null;
    private BufferedReader bufferedReader = null;
    private final List<String[]> accounts = new ArrayList<>();

    private void loadAccount() {
        new Thread(() -> {
            try {
                fileInputStream = new FileInputStream(ACCOUNT_FILE_URI);
                bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    accounts.add(line.split("#"));
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fileInputStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }

    private void initFrameBehavior() {
        dangNhapButton.addActionListener((e) -> {
            String account = taiKhoanTextField.getText(), password = String.valueOf(matKhauPassText.getPassword());
            if (account.equals("") || password.equals("")) {
                DialogProvider.showMessageDialog("Tài khoản và Mật khẩu không được trống !", ACCOUNT_FILE_URI);
                return;
            }
            for (String[] acc : accounts) {
                if (acc[0].equals(account)) {
                    if (acc[1].equals(password)) {
                        if (Integer.parseInt(acc[3]) == 1) {
                            DashboardFrame d = new DashboardFrame();
                            d.setTitle("Dashboard");
                            d.getContentPane().setBackground(Color.WHITE);
                            d.setResizable(false);
                            d.setLocationRelativeTo(null);
                            d.setVisible(true);
                            saveLoginAutomaticallyToFile(account, password);
                            saveUserLogStatus(account, password);
                            this.dispose();
                            return;
                        }
                        DialogProvider.showMessageDialog("Tài khoản này đã bị chặn !", account);
                        return;
                    }
                    DialogProvider.showMessageDialog("Sai mật khẩu !", account);
                    return;
                }
            }
            DialogProvider.showMessageDialog("Thông tin tài khoản hoặc mật khẩu không đúng !", account);
        });
    }

    private BufferedWriter writer;
    private FileWriter fileWriter;

    private void closeWriter() {
        try {
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveUserLogStatus(String acc, String pass) {
        new Thread(() -> {
            try {
                FileWriter logFileWriter = new FileWriter(USER_LOG_STATUS);
                BufferedWriter logWriter = new BufferedWriter(logFileWriter);
                logWriter.write(acc + "\n" + pass);
                logWriter.close();
                logFileWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }

    private void saveLoginAutomaticallyToFile(String acc, String pass) {
        if (nhoTKCheckBox.isSelected()) {
            new Thread(() -> {
                try {
                    fileWriter = new FileWriter(APP_CONFIG_URI);
                    writer = new BufferedWriter(fileWriter);
                    writer.flush();
                    writer.write(acc + "\n" + pass + "\n" + "true");
                } catch (IOException ex) {
                    Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    closeWriter();
                }
            }).start();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        avatar_image_label = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        taiKhoanTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        nhoTKCheckBox = new javax.swing.JCheckBox();
        matKhauPassText = new javax.swing.JPasswordField();
        dangNhapButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        logo_image_label = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(1000, 600));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        avatar_image_label.setBackground(new java.awt.Color(0, 51, 102));
        avatar_image_label.setForeground(new java.awt.Color(0, 51, 102));
        avatar_image_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("Tài khoản :");

        taiKhoanTextField.setBackground(new java.awt.Color(198, 222, 246));
        taiKhoanTextField.setForeground(new java.awt.Color(0, 51, 102));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText("Mật khẩu :");

        nhoTKCheckBox.setBackground(new java.awt.Color(255, 255, 255));
        nhoTKCheckBox.setForeground(new java.awt.Color(0, 51, 102));
        nhoTKCheckBox.setText("Nhớ tài khoản");

        matKhauPassText.setBackground(new java.awt.Color(207, 232, 249));
        matKhauPassText.setForeground(new java.awt.Color(0, 51, 102));

        dangNhapButton.setBackground(new java.awt.Color(255, 255, 255));
        dangNhapButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        dangNhapButton.setForeground(new java.awt.Color(0, 51, 102));
        dangNhapButton.setText("Đăng nhập");
        dangNhapButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 3, true));
        dangNhapButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dangNhapButtonMouseClicked(evt);
            }
        });

        jTextPane1.setEditable(false);
        jTextPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTextPane1.setBorder(null);
        jTextPane1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextPane1.setForeground(new java.awt.Color(204, 51, 0));
        jTextPane1.setText("Đây là ứng dụng dành cho quản trị viên. Vui lòng liên hệ quản trị viên để được hỗ trợ qua thuê bao : 0931910JQK.");
        jScrollPane1.setViewportView(jTextPane1);

        logo_image_label.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        logo_image_label.setForeground(new java.awt.Color(0, 51, 102));
        logo_image_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel1.setBackground(new java.awt.Color(0, 51, 102));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 102));
        jLabel1.setText("Quên mật khẩu ?");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(avatar_image_label, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(153, 153, 153)
                        .addComponent(logo_image_label, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(taiKhoanTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(nhoTKCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1))
                            .addComponent(matKhauPassText)
                            .addComponent(dangNhapButton, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(61, Short.MAX_VALUE)
                .addComponent(logo_image_label, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(taiKhoanTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(matKhauPassText, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nhoTKCheckBox)
                    .addComponent(jLabel1))
                .addGap(32, 32, 32)
                .addComponent(dangNhapButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(avatar_image_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dangNhapButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dangNhapButtonMouseClicked

    }//GEN-LAST:event_dangNhapButtonMouseClicked

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new LoginFrame().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel avatar_image_label;
    private javax.swing.JButton dangNhapButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JLabel logo_image_label;
    private javax.swing.JPasswordField matKhauPassText;
    private javax.swing.JCheckBox nhoTKCheckBox;
    private javax.swing.JTextField taiKhoanTextField;
    // End of variables declaration//GEN-END:variables
}
