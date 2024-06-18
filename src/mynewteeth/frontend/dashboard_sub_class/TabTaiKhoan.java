/*
Doan Khac Minh
 Created by Minh on 6-6
 Finished on 8-6
 */
package mynewteeth.frontend.dashboard_sub_class;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import mynewteeth.backend.interfaces.ILogout;
import mynewteeth.backend.interfaces.IOptionDialogAction;
import mynewteeth.backend.model.TaiKhoan;
import mynewteeth.frontend.AutoLogoutFrame;
import mynewteeth.frontend.DialogProvider;
import mynewteeth.frontend.LoginFrame;

/**
 *
 * @author Us
 */
public class TabTaiKhoan {

    private final JTable taiKhoanTable;
    private final JTextField taiKhoanTextField;
    private final JTextField matKhauTextField;
    private final JTextField ngayTaoTextField;
    private final JButton doiMKButton;
    private final JButton chanTKButton;
    private final JButton xoaTKButton;
    private final JButton dangXuatButton;
    private final JButton themTKButton;
    private final ILogout callback;

    private static final String ACC_PATTERN = "[a-zA-Z0-9]{8,}";
    private static final String PASS_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20})";
    private static final String APP_CONFIG_URI = "src/mynewteeth/backend/configs/appconfig.txt";
    private static final String ACCOUNT_FILE_URI = "src/mynewteeth/backend/data_repository/local_data/raw_data/taikhoan.txt";
    private static final String USER_LOG_STATUS = "src/mynewteeth/backend/configs/userlogstatus.txt";
    private FileInputStream fileInputStream = null;
    private BufferedReader bufferedReader = null;
    private final List<TaiKhoan> accounts = new ArrayList<>();
    private final CyclicBarrier barrier = new CyclicBarrier(2);
    private DefaultTableModel accountTableModel;
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public TabTaiKhoan(JTable taiKhoanTable, JTextField tiaKhoanTextField, JTextField matKhauTextField,
            JTextField ngayTaoTextField, JButton doiMKButton, JButton chanTKButton, JButton xoaTKButton, JButton dangXuatButton,
            JButton themTKButton, ILogout callback) {

        this.taiKhoanTable = taiKhoanTable;
        this.taiKhoanTextField = tiaKhoanTextField;
        this.matKhauTextField = matKhauTextField;
        this.ngayTaoTextField = ngayTaoTextField;
        this.doiMKButton = doiMKButton;
        this.chanTKButton = chanTKButton;
        this.xoaTKButton = xoaTKButton;
        this.dangXuatButton = dangXuatButton;
        this.themTKButton = themTKButton;
        this.callback = callback;
        loadAccounts();
        bindData();
        addNewAccount();
        changePassword();
        deleteAccount();
        blockAccount();
        logout();
    }

    private void closeReader() {
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

    private void loadAccounts() {
        new Thread(() -> {
            try {
                fileInputStream = new FileInputStream(ACCOUNT_FILE_URI);
                bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                String line;
                String[] acc;
                while ((line = bufferedReader.readLine()) != null) {
                    acc = line.split("#");
                    accounts.add(new TaiKhoan(acc[0], acc[1], dateFormat.parse(acc[2]), Integer.parseInt(acc[3])));
                }
                barrier.await();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException | BrokenBarrierException | ParseException ex) {
                Logger.getLogger(TabTaiKhoan.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                closeReader();
            }
        }).start();
    }

    private void setAccountTextField(TaiKhoan acc) {
        taiKhoanTextField.setText((acc != null) ? acc.getAccountName() : "");
        matKhauTextField.setText((acc != null) ? acc.getPassword() : "");
        ngayTaoTextField.setText((acc != null) ? dateFormat.format(acc.getCreatedDay()) : "");
    }

    private void bindData() {
        new Thread(() -> {
            try {
                barrier.await();
                accountTableModel = (DefaultTableModel) taiKhoanTable.getModel();
                for (TaiKhoan acc : accounts) {
                    accountTableModel.addRow(acc.getFragmentInfo());
                }
            } catch (InterruptedException | BrokenBarrierException ex) {
                Logger.getLogger(TabTaiKhoan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
        ngayTaoTextField.setText(LocalDate.now().toString());
        taiKhoanTable.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            // This function triggers many event when user interact with table . So some unnecessary internal function is also executed . 
            // This will causes logic here will be re executed many times and logic goes wrong ! 
            if (e.getValueIsAdjusting()) {
                return;
            }
            // get acc from list then bind it to UI 
            int selectedRow = taiKhoanTable.getSelectedRow();
            setAccountTextField((selectedRow >= 0) ? accounts.get(selectedRow) : null);
        });
    }

    private boolean checkInputValidation(String acc, String pass) {
        if (acc.equals("") || pass.equals("")) {
            DialogProvider.showMessageDialog("Các trường thông tin không được trống !", "Thông báo !");
            return false;
        } else if (!Pattern.matches(ACC_PATTERN, acc)) {
            DialogProvider.showMessageDialog("Tài khoản chỉ bao gồm chữ cái và số và ít nhất 8 kí tự !", "Thông báo !");
            return false;
        } else if (!Pattern.matches(PASS_PATTERN, pass)) {
            DialogProvider.showMessageDialog("Mật khẩu phải chứa chữ cái thường , chữ hoa , số và tối thiểu 8 kí tự !", "Thông báo !");
            return false;
        }
        return true;
    }

    private boolean checkAccExist(String acc) {
        for (TaiKhoan account : accounts) {
            if (account.getAccountName().equals(acc)) {
                DialogProvider.showMessageDialog("Tài khoản này đã tồn tại !", "Thông báo !");
                return true;
            }
        }
        return false;
    }

    private void addNewAccountTable(TaiKhoan createdAcc) {
        accounts.add(createdAcc);
        accountTableModel.addRow(createdAcc.getFragmentInfo());
    }

    private FileWriter fileWriter;
    private BufferedWriter writer;

    private void closeWriter() {
        try {
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(TabTaiKhoan.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(TabTaiKhoan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void writeNewAccToFile(TaiKhoan createdAcc) {
        new Thread(() -> {
            try {
                fileWriter = new FileWriter(ACCOUNT_FILE_URI, true);
                writer = new BufferedWriter(fileWriter);
                writer.append(createdAcc.convertToString());
                writer.newLine();
            } catch (IOException ex) {
                Logger.getLogger(TabTaiKhoan.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                closeWriter();
            }
        }).start();
    }

    private void addNewAccount() {
        themTKButton.addActionListener((e) -> {
            String acc = taiKhoanTextField.getText(), pass = matKhauTextField.getText();
            if (!checkInputValidation(acc, pass) || checkAccExist(acc)) {
                return;
            }
            try {
                TaiKhoan createdAcc = new TaiKhoan(acc, pass, dateFormat.parse(LocalDate.now().toString()), 1);
                addNewAccountTable(createdAcc);
                writeNewAccToFile(createdAcc);
                DialogProvider.showMessageDialog("Thêm tài khoản thành công !", "Thông báo !");
            } catch (ParseException ex) {
                Logger.getLogger(TabTaiKhoan.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Use FileUtils to get more supports for writing to file, but it requires downloading and integrating lib jar-type file to project.
    // This may causes conflict between the versions that team members pull from Github and work on. 
    // So my choice is that i update password in list accounts , then rewrite the file with that list
    private void removeRememberingPassIfChanged(TaiKhoan changedAcc) throws FileNotFoundException, IOException {
        fileInputStream = new FileInputStream(APP_CONFIG_URI);
        bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        String line;
        if ((line = bufferedReader.readLine()) != null) {
            closeReader();
            if (changedAcc.getAccountName().equals(line)) {
                // clear saved Acc
                fileWriter = new FileWriter(APP_CONFIG_URI);
                writer = new BufferedWriter(fileWriter);
                writer.write("");
            }
        }
    }

    private void updateChangeInFile(TaiKhoan changedAcc) {
        try {
            // clear file
            fileWriter = new FileWriter(ACCOUNT_FILE_URI);
            writer = new BufferedWriter(fileWriter);
            writer.write("");
            closeWriter();
            // recreate Writer with append mode
            fileWriter = new FileWriter(ACCOUNT_FILE_URI, true);
            writer = new BufferedWriter(fileWriter);
            for (TaiKhoan acc : accounts) {
                writer.append(acc.convertToString());
                writer.newLine();
            }
            closeWriter();
            // remove account that used to login automatically if it is changed
            removeRememberingPassIfChanged(changedAcc);
        } catch (IOException ex) {
            Logger.getLogger(TabTaiKhoan.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeWriter();
        }
    }

    private void doOnPasswordChanged(TaiKhoan changedAcc, String pass) {
        changedAcc.setPassword(pass);
        taiKhoanTable.setValueAt(changedAcc.getPassword(), accounts.indexOf(changedAcc), 1);
        updateChangeInFile(changedAcc);
        // disposeWaitingFrame();
        DialogProvider.showMessageDialog("Thay đổi mật khẩu thành công !", "Thông báo !");
    }

    private void logoutAfterSeconds() {
        AutoLogoutFrame logoutFrame = new AutoLogoutFrame();
        logoutFrame.setResizable(false);
        logoutFrame.setLocationRelativeTo(null);
        logoutFrame.setVisible(true);
        logoutFrame.countDown(() -> {
            callback.logout();
        });
    }

    private void logoutIfCurrentAccChanged(TaiKhoan account, String acc, String pass, String changeMessage) throws FileNotFoundException, IOException {
        FileInputStream userLogInputReader = new FileInputStream(USER_LOG_STATUS);
        BufferedReader userLogReader = new BufferedReader(new InputStreamReader(userLogInputReader));
        String line;
        // check if account is logging in, it it will be logged out
        if ((line = userLogReader.readLine()) != null && line.equals(acc)) {
            DialogProvider.showSyncConfirmDialog(changeMessage, "Cảnh báo !", null, new IOptionDialogAction() {
                @Override
                public void onYesOption(Object object) {
                    // update and log user out to login frame
                    doOnPasswordChanged(account, pass);
                    logoutAfterSeconds();
                }

                @Override
                public void onNoOption() {
                    // handle if user dont want to change any more
                    taiKhoanTable.getSelectionModel().clearSelection();
                }
            });
            return;
        }
        doOnPasswordChanged(account, pass);
        userLogReader.close();
        userLogInputReader.close();
    }

    private void changePassword() {
        doiMKButton.addActionListener((e) -> {
            String acc = taiKhoanTextField.getText(), pass = matKhauTextField.getText();
            if (taiKhoanTable.getSelectedRow() < 0) {
                DialogProvider.showMessageDialog("Hãy chọn 1 tài khoản trong bảng để sửa !", "Cảnh báo !");
                return;
            }
            final TaiKhoan account = accounts.get(taiKhoanTable.getSelectedRow());
            if (!account.getAccountName().equals(acc)) {
                DialogProvider.showMessageDialog("Không được thay đổi tên tài khoản !", "Cảnh báo !");
                return;
            }
            if (account.getPassword().equals(pass)) {
                DialogProvider.showMessageDialog("Mật khẩu không được trùng mật khẩu cũ !", "Cảnh báo !");
                return;
            }
            if (!Pattern.matches(PASS_PATTERN, pass)) {
                DialogProvider.showMessageDialog("Mật khẩu phải chứa chữ cái thường , chữ hoa , số và tối thiểu 8 kí tự !", "Thông báo !");
                return;
            }
            // update object's data
            new Thread(() -> {
                try {
                    logoutIfCurrentAccChanged(account, acc, pass, "Bạn đang đăng nhập trên tài khoản này ! Đổi mật khẩu sẽ đăng xuất khỏi tài khoản này. Vẫn đổi ?");
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(TabTaiKhoan.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(TabTaiKhoan.class.getName()).log(Level.SEVERE, null, ex);
                }
            }).start();
            //showWaitingFrame();
        });
    }

    /*Dont remove this ! This is waiting screen for indicating user that their request is executed . But in this case , app finishs work too fast
            and this may make a unusual Animation.*/
//    private final WaitingFrame waitingFrame = new WaitingFrame();
//
//    private void showWaitingFrame() {
//        waitingFrame.setResizable(false);
//        waitingFrame.setLocationRelativeTo(null);
//        waitingFrame.setVisible(true);
//    }
//
//    private void disposeWaitingFrame() {
//        waitingFrame.dispose();
//    }
    private void deleteAccount() {
        xoaTKButton.addActionListener((e) -> {
            if (taiKhoanTable.getSelectedRow() < 0) {
                DialogProvider.showMessageDialog("Hãy chọn 1 tài khoản trong bảng để xóa !", "Cảnh báo !");
                return;
            }
            final TaiKhoan deletedAccount = accounts.get(taiKhoanTable.getSelectedRow());
            DialogProvider.showConfirmDialog("Bạn chắc chắn muốn xóa tài khoản " + deletedAccount.getAccountName() + " ?",
                    "Cảnh báo !", deletedAccount, new IOptionDialogAction() {
                @Override
                public void onYesOption(Object object) {
                    try {
                        FileInputStream userLogInputReader = new FileInputStream(USER_LOG_STATUS);
                        BufferedReader userLogReader = new BufferedReader(new InputStreamReader(userLogInputReader));
                        String line;
                        // check if account is logging in, it it will be logged out
                        if ((line = userLogReader.readLine()) != null && line.equals(((TaiKhoan) object).getAccountName())) {
                            DialogProvider.showSyncConfirmDialog("Tài khoản này đang được đăng nhập trên thiết bị của bạn."
                                    + "\nXóa tài khoản này sẽ bị đăng xuất. Vẫn xóa ?", "Cảnh báo !", object, new IOptionDialogAction() {
                                @Override
                                public void onYesOption(Object object) {
                                    accountTableModel.removeRow(accounts.indexOf((TaiKhoan) object));
                                    accounts.remove((TaiKhoan) object);
                                    updateChangeInFile((TaiKhoan) object);
                                    DialogProvider.showMessageDialog("Xóa tài khoàn thành công !", "Thông báo !");
                                    logoutAfterSeconds();
                                }

                                @Override
                                public void onNoOption() {
                                    taiKhoanTable.getSelectionModel().clearSelection();
                                }
                            });
                            return;
                        }
                        accountTableModel.removeRow(accounts.indexOf((TaiKhoan) object));
                        accounts.remove((TaiKhoan) object);
                        updateChangeInFile((TaiKhoan) object);
                        DialogProvider.showMessageDialog("Xóa tài khoàn thành công !", "Thông báo !");
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(TabTaiKhoan.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(TabTaiKhoan.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                @Override
                public void onNoOption() {
                    taiKhoanTable.getSelectionModel().clearSelection();
                }
            });
        });
    }

    private void blockAccount() {
        chanTKButton.addActionListener((e) -> {
            int selectedRow = taiKhoanTable.getSelectedRow();
            if (selectedRow < 0) {
                DialogProvider.showMessageDialog("Vui lòng chọn một tài khoản để chặn !", "Thông báo !");
                return;
            }
            final TaiKhoan acc = accounts.get(selectedRow);
            if (acc.getState() == 1) {
                try {
                    // nếu đang đăng nhập => hỏi người dung đăng xuất
                    fileInputStream = new FileInputStream(APP_CONFIG_URI);
                    bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                    String line = bufferedReader.readLine();
                    closeReader();
                    if (line != null && line.equals(acc.getAccountName())) {
                        DialogProvider.showConfirmDialog("Bạn đang chặn tài khoản đang đăng nhập !"
                                + "\n Tài khoản này sẽ tự động đăng xuất nếu tiếp tục ! Vãn chặn ?", "Cảnh báo !", acc, new IOptionDialogAction() {
                            @Override
                            public void onYesOption(Object object) {
                                taiKhoanTable.setValueAt("Bị chặn", selectedRow, 3);
                                acc.setState(0);
                                updateChangeInFile(acc);
                                DialogProvider.showMessageDialog("Chặn tài khoản này thành công !", "Thông báo");
                                logoutAfterSeconds();
                            }

                            @Override
                            public void onNoOption() {
                                taiKhoanTable.getSelectionModel().clearSelection();
                            }
                        });
                        return;
                    }
                    taiKhoanTable.setValueAt("Bị chặn", selectedRow, 3);
                    acc.setState(0);
                    updateChangeInFile(acc);
                    DialogProvider.showMessageDialog("Chặn tài khoản này thành công !", "Thông báo !");
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(TabTaiKhoan.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(TabTaiKhoan.class.getName()).log(Level.SEVERE, null, ex);
                }
                return;
            }
            taiKhoanTable.setValueAt("Hoạt động", selectedRow, 3);
            acc.setState(1);
            updateChangeInFile(acc);
            DialogProvider.showMessageDialog("Bỏ chặn tài khoản này thành công !", "Thông báo !");
            taiKhoanTable.getSelectionModel().clearSelection();
        });
    }

    private void logout() {
        dangXuatButton.addActionListener((e) -> {
            DialogProvider.showConfirmDialog("Đăng xuất khỏi thiết bị này ?", "Cảnh báo !", null, new IOptionDialogAction() {
                @Override
                public void onYesOption(Object object) {
                    callback.logout();
                }

                @Override
                public void onNoOption() {

                }
            });
        });
    }
}
