/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mynewteeth.frontend.dashboard_sub_class;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import mynewteeth.backend.controller.LichHenController;
import mynewteeth.backend.model.BacSi;
import mynewteeth.backend.model.BenhNhan;
import mynewteeth.backend.model.LichHen;
import mynewteeth.backend.model.TaiKhoan;

/**
 *
 * @author Us
 */
public class TabLichHen {

    private final JTable lichHenTable;
    private final JTextField maLichHenTextField;
    private final JTextField maBNHenTextField;
    private final JTextField tenBNHenTextField;
    private final JTextField sdtHenTextField;
    private final JTextField ngayHenTextField;
    private final JTextField tenBSHenTextField;
    private final JTextField maBSHenTetxField;
    private final JComboBox<String> dichVuHenComboBox;
    private final JTable dichVuHenTable;
    private final JTextArea ghiChuHenTextField;
    private final JButton themLichHenButton;
    private final JButton xoaLichHenButton;
    private final JButton capNhatLichHenButton;
    private final JButton timKiemLichHenButton;
    
    private LichHenController lichHenController;

    public TabLichHen(JTable lichHenTable, JTextField maLichHenTextField, JTextField maBNHenTextField,
            JTextField tenBNHenTextField, JTextField sdtHenTextField, JTextField ngayHenTextField,
            JTextField tenBSHenTextField, JTextField maBSHenTetxField, JComboBox<String> dichVuHenComboBox,
            JTable dichVuHenTable, JTextArea ghiChuHenTextField, JButton themLichHenButton, JButton xoaLichHenButton,
            JButton capNhatLichHenButton, JButton timKiemLichHenButton) {

        this.lichHenTable = lichHenTable;
        this.maLichHenTextField = maLichHenTextField;
        this.maBNHenTextField = maBNHenTextField;
        this.tenBNHenTextField = tenBNHenTextField;
        this.sdtHenTextField = sdtHenTextField;
        this.ngayHenTextField = ngayHenTextField;
        this.tenBSHenTextField = tenBSHenTextField;
        this.maBSHenTetxField = maBSHenTetxField;
        this.dichVuHenComboBox = dichVuHenComboBox;
        this.dichVuHenTable = dichVuHenTable;
        this.ghiChuHenTextField = ghiChuHenTextField;
        this.themLichHenButton = themLichHenButton;
        this.xoaLichHenButton = xoaLichHenButton;
        this.capNhatLichHenButton = capNhatLichHenButton;
        this.timKiemLichHenButton = timKiemLichHenButton;
        
        lichHenController = new LichHenController();
        bindData();
        themLichHen();
        handleTableBehavior();
        capNhatLichHen();
        xoaLichHen();
        timKiemLichHen();
    }
    
    public void updateData(Object updatedObject) {
        if (updatedObject instanceof String) {
            System.out.println("Tab lịch hẹn update data : " + updatedObject);
            return;
        }

        if (updatedObject instanceof BenhNhan) {
            // dữ liệu thay đôi là bệnh nhân 
        } else {
            // dữ liệu thay đôi là Bác sĩ
        }
    }

    public void resetText() {
        this.ghiChuHenTextField.setText("");
        this.maBNHenTextField.setText("");
        this.maBSHenTetxField.setText("");
        this.maLichHenTextField.setText("");
        this.sdtHenTextField.setText("");
        this.tenBNHenTextField.setText("");
        this.tenBSHenTextField.setText("");
    }

    public void bindData() {
        List<LichHen> ls = lichHenController.getDsLichHen();
        DefaultTableModel model = (DefaultTableModel) lichHenTable.getModel();

        // Xóa dữ liệu cũ trong bảng (nếu có)
        model.setRowCount(0);

        // Định dạng ngày tháng
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Thêm từng bệnh nhân vào bảng
        for (LichHen hs : ls) {
            String ngayHen = dateFormat.format(hs.getNgayHen()); // Định dạng ngày khám

            Object[] rowData = {
                hs.getMaLicHen(),
                hs.getBenhNhan().getTenBenhNhan(),
                hs.getGhiChu(),
                hs.getNgayHen(),
                hs.getBenhNhan().getSoDienThoai(),};
            model.addRow(rowData);
        }

        DefaultTableModel model1 = (DefaultTableModel) this.dichVuHenTable.getModel();

        // Xóa hết các hàng cũ trong table
        model1.setRowCount(0);

        // Lặp qua danh sách tài khoản và thêm vào table
        for (TaiKhoan k : lichHenController.getDsTaiKhoan()) {
            // Định dạng ngày tạo
            String ngayTao = dateFormat.format(k.getCreatedDay());

            // Tạo một hàng mới để thêm vào table
            Object[] rowTaiKhoan = {
                k.getAccountName(),
                k.getPassword(),
                ngayTao, // Đã định dạng ngày tháng thành chuỗi
                k.getState()
            };
            model1.addRow(rowTaiKhoan);
        }
    }

    public boolean isValidLHFormat(String s) {
        // Định nghĩa biểu thức chính quy cho định dạng LHXXX
        String pattern = "^LH\\d{3}$";
        // Tạo đối tượng Pattern
        Pattern compiledPattern = Pattern.compile(pattern);
        // Tạo đối tượng Matcher
        Matcher matcher = compiledPattern.matcher(s);
        // Kiểm tra xem chuỗi s có khớp với biểu thức chính quy không
        return matcher.matches();
    }

    public boolean isValidDateFormat(String dateStr) {
        // Định dạng ngày tháng năm yyyy-MM-dd
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            // Thử phân tích cú pháp chuỗi
            LocalDate.parse(dateStr, formatter);
            return true;
        } catch (DateTimeParseException e) {
            // Nếu có ngoại lệ, định dạng không hợp lệ
            return false;
        }
    }

    public void themLichHen() {
        themLichHenButton.addActionListener(e -> {
            String maLichHen = maLichHenTextField.getText();
            String maBenhNhan = maBNHenTextField.getText();
            String maBacSi = maBSHenTetxField.getText();
            String ngayHen = this.ngayHenTextField.getText();
            String tenDichVu = this.dichVuHenComboBox.getSelectedItem().toString();
            String lyDo = this.ghiChuHenTextField.getText();
            BenhNhan bn = lichHenController.findBenhNhan(maBenhNhan);
            BacSi bs = lichHenController.findBacSi(maBacSi);
            DefaultTableModel cc = (DefaultTableModel) dichVuHenTable.getModel();
            int selectedRow = dichVuHenTable.getSelectedRow();
            TaiKhoan tk = lichHenController.findTaiKhoan(cc.getValueAt(selectedRow, 0).toString());
            if (maLichHen.isEmpty() || maBenhNhan.isEmpty() || maBacSi.isEmpty() || lyDo.isEmpty() || tenDichVu.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }
            for (LichHen lh : lichHenController.getDsLichHen()) {
                if (lh.getMaLicHen().equals(maLichHen)) {
                    JOptionPane.showMessageDialog(null, "Mã lịch hẹn đã tồn tại trong danh sách! vui lòng nhập lai!");
                    return;
                }
            }
            boolean found = false;

            for (BenhNhan x : lichHenController.getDsBenhNhan()) {
                if (maBenhNhan.equals(x.getMaBenhNhan())) {
                    found = true;
                    break; // Tìm thấy thì thoát vòng lặp
                }
            }

            if (!found) {
                JOptionPane.showMessageDialog(null, "Mã bệnh nhân không tồn tại trong danh sách! Vui lòng nhập lại!");
            }

            boolean ktBacSi = false;

            for (BacSi y : lichHenController.getDsBacSi()) {
                if (maBacSi.equals(y.getMaBacSi())) {
                    ktBacSi = true;
                    break; // Tìm thấy thì thoát vòng lặp
                }
            }

            if (!ktBacSi) {
                JOptionPane.showMessageDialog(null, "Mã bác sĩ không tồn tại trong danh sách! Vui lòng nhập lại!");
            }

            if (!isValidLHFormat(maLichHen)) {
                JOptionPane.showMessageDialog(null, "Mã lịch hẹn sai định dang vui lòng nhập lại! vui lòng nhập lai!");
                return;
            }

            if (!isValidDateFormat(ngayHen)) {
                JOptionPane.showMessageDialog(null, "Nhập sai định dạng ngày hẹn! vui lòng nhập lai!");
                return;
            }
            lichHenController.themLichHen(maLichHen, tenDichVu, lyDo, ngayHen, bn, bs, tk);
            resetText();
            bindData();
        });
    }

    private void handleTableBehavior() {
        this.lichHenTable.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                return;
            }
            int selectedIndex = lichHenTable.getSelectedRow();
            if (selectedIndex != -1) {
                // Lấy dữ liệu của hàng đã chọn từ bảng
                String maLichHen = lichHenTable.getValueAt(selectedIndex, 0).toString(); // Lấy mã lịch hẹn từ cột 0

                // Tìm LichHen tương ứng với mã lịch hẹn đã chọn
                LichHen selectedLichHen = null;
                for (LichHen f : lichHenController.getDsLichHen()) {
                    if (f.getMaLicHen().equals(maLichHen)) {
                        selectedLichHen = f;
                        break;
                    }
                }

                // Nếu không tìm thấy LichHen với mã lịch hẹn đã chọn, không làm gì cả
                if (selectedLichHen == null) {
                    return;
                }

                // Định dạng ngày tháng
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                // Đặt các giá trị vào các thành phần giao diện
                this.ghiChuHenTextField.setText(selectedLichHen.getGhiChu());
                this.maBNHenTextField.setText(selectedLichHen.getBenhNhan().getMaBenhNhan());
                this.maBSHenTetxField.setText(selectedLichHen.getBacSi().getMaBacSi());
                this.maLichHenTextField.setText(selectedLichHen.getMaLicHen());
                this.sdtHenTextField.setText(selectedLichHen.getBenhNhan().getSoDienThoai());
                this.tenBNHenTextField.setText(selectedLichHen.getBenhNhan().getTenBenhNhan());
                this.tenBSHenTextField.setText(selectedLichHen.getBacSi().getHoTen());
                this.ngayHenTextField.setText(dateFormat.format(selectedLichHen.getNgayHen()));

                // Thiết lập lại mô hình cho JComboBox
                DefaultComboBoxModel<String> comboBoxModel = (DefaultComboBoxModel<String>) this.dichVuHenComboBox.getModel();
                int selectedIndexCombo = 0; // Mặc định chọn phần tử đầu tiên nếu không tìm thấy

                // Tìm index của lựa chọn trong JComboBox
                for (int i = 0; i < comboBoxModel.getSize(); i++) {
                    String item = comboBoxModel.getElementAt(i);
                    if (item.equalsIgnoreCase(selectedLichHen.getTenDichVu())) {
                        selectedIndexCombo = i;
                        break;
                    }
                }

                // Đặt lại index đã chọn cho JComboBox
                this.dichVuHenComboBox.setSelectedIndex(selectedIndexCombo);

                // Tìm và chọn dòng trong dichVuHenTable dựa trên mã tài khoản
                DefaultTableModel model = (DefaultTableModel) dichVuHenTable.getModel();
                for (int row = 0; row < model.getRowCount(); row++) {
                    String maTaiKhoanTable = model.getValueAt(row, 0).toString(); // Giả sử cột 0 chứa mã tài khoản
                    if (selectedLichHen.getTaiKhoan().getAccountName().equals(maTaiKhoanTable)) {
                        // Chọn dòng tương ứng trong dichVuHenTable
                        dichVuHenTable.setRowSelectionInterval(row, row);
                        // Scroll đến vị trí của dòng đã chọn nếu cần thiết
                        dichVuHenTable.scrollRectToVisible(dichVuHenTable.getCellRect(row, 0, true));
                        break;
                    }
                }
            }
        });
    }

    public void capNhatLichHen() {
        capNhatLichHenButton.addActionListener(e -> {
            int selectedRow = lichHenTable.getSelectedRow();
            if (selectedRow != -1) {
                String maLichHen = maLichHenTextField.getText();
                String maBenhNhan = maBNHenTextField.getText();
                String maBacSi = maBSHenTetxField.getText();
                String ngayHenStr = ngayHenTextField.getText();
                String tenDichVu = dichVuHenComboBox.getSelectedItem().toString();
                String lyDo = ghiChuHenTextField.getText();
                BenhNhan bn = lichHenController.findBenhNhan(maBenhNhan);
                BacSi bs = lichHenController.findBacSi(maBacSi);
                DefaultTableModel cc = (DefaultTableModel) dichVuHenTable.getModel();
                int selectedTableRow = dichVuHenTable.getSelectedRow();
                TaiKhoan tk = lichHenController.findTaiKhoan(cc.getValueAt(selectedTableRow, 0).toString());

                if (maLichHen.isEmpty() || maBenhNhan.isEmpty() || maBacSi.isEmpty() || lyDo.isEmpty() || tenDichVu.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
                    return;
                }

                if (!isValidLHFormat(maLichHen)) {
                    JOptionPane.showMessageDialog(null, "Mã lịch hẹn sai định dạng! Vui lòng nhập lại!");
                    return;
                }

                if (!isValidDateFormat(ngayHenStr)) {
                    JOptionPane.showMessageDialog(null, "Nhập sai định dạng ngày hẹn! Vui lòng nhập lại!");
                    return;
                }

                LichHen selectedLichHen = lichHenController.getDsLichHen().get(selectedRow);
                selectedLichHen.setMaLicHen(maLichHen);
                selectedLichHen.setTenDichVu(tenDichVu);
                selectedLichHen.setGhiChu(lyDo);
                try {
                    selectedLichHen.setNgayHen(new SimpleDateFormat("yyyy-MM-dd").parse(ngayHenStr));
                } catch (ParseException ex) {
                    
                }
                selectedLichHen.setBenhNhan(bn);
                selectedLichHen.setBacSi(bs);
                selectedLichHen.setTaiKhoan(tk);

                // Lưu dữ liệu vào file
                lichHenController.SaveFile();

                // Cập nhật lại dữ liệu trên bảng
                bindData();

                JOptionPane.showMessageDialog(null, "Cập nhật lịch hẹn thành công!");
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn lịch hẹn để cập nhật!");
            }
        });
    }
    
    public void xoaLichHen(){
        this.xoaLichHenButton.addActionListener(e ->{
              int selectedRow = lichHenTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn lịch hẹn để xóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lấy mã lịch hẹn của dòng được chọn
        String maLichHen = lichHenTable.getValueAt(selectedRow, 0).toString();
        List<LichHen> dslh = lichHenController.getDsLichHen();
        // Xóa lịch hẹn khỏi danh sách
        boolean found = false;
        for (LichHen lichHen : dslh) {
            if (lichHen.getMaLicHen().equals(maLichHen)) {
                dslh.remove(lichHen);
                found = true;
                break;
            }
        }
        lichHenController.setDsLichHen(dslh);
        // Xóa dòng khỏi bảng
        if (found) {
            DefaultTableModel model = (DefaultTableModel) lichHenTable.getModel();
            model.removeRow(selectedRow);
            // Lưu danh sách mới vào file
            lichHenController.SaveFile();
            JOptionPane.showMessageDialog(null, "Xóa lịch hẹn thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy mã lịch hẹn.", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
        });
    }
    
    public void timKiemLichHen() {
    timKiemLichHenButton.addActionListener(e -> {
        String maLichHen = maLichHenTextField.getText();

        if (maLichHen.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập mã lịch hẹn để tìm kiếm.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LichHen foundLichHen = null;
        for (LichHen lichHen : lichHenController.getDsLichHen()) {
            if (lichHen.getMaLicHen().equals(maLichHen)) {
                foundLichHen = lichHen;
                break;
            }
        }

        if (foundLichHen != null) {
            // Định dạng ngày tháng
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // Đặt các giá trị vào các thành phần giao diện
            this.ghiChuHenTextField.setText(foundLichHen.getGhiChu());
            this.maBNHenTextField.setText(foundLichHen.getBenhNhan().getMaBenhNhan());
            this.maBSHenTetxField.setText(foundLichHen.getBacSi().getMaBacSi());
            this.maLichHenTextField.setText(foundLichHen.getMaLicHen());
            this.sdtHenTextField.setText(foundLichHen.getBenhNhan().getSoDienThoai());
            this.tenBNHenTextField.setText(foundLichHen.getBenhNhan().getTenBenhNhan());
            this.tenBSHenTextField.setText(foundLichHen.getBacSi().getHoTen());
            this.ngayHenTextField.setText(dateFormat.format(foundLichHen.getNgayHen()));

            // Thiết lập lại mô hình cho JComboBox
            DefaultComboBoxModel<String> comboBoxModel = (DefaultComboBoxModel<String>) this.dichVuHenComboBox.getModel();
            int selectedIndexCombo = 0; // Mặc định chọn phần tử đầu tiên nếu không tìm thấy

            // Tìm index của lựa chọn trong JComboBox
            for (int i = 0; i < comboBoxModel.getSize(); i++) {
                String item = comboBoxModel.getElementAt(i);
                if (item.equalsIgnoreCase(foundLichHen.getTenDichVu())) {
                    selectedIndexCombo = i;
                    break;
                }
            }

            // Đặt lại index đã chọn cho JComboBox
            this.dichVuHenComboBox.setSelectedIndex(selectedIndexCombo);

            // Tìm và chọn dòng trong dichVuHenTable dựa trên mã tài khoản
            DefaultTableModel model = (DefaultTableModel) dichVuHenTable.getModel();
            for (int row = 0; row < model.getRowCount(); row++) {
                String maTaiKhoanTable = model.getValueAt(row, 0).toString(); // Giả sử cột 0 chứa mã tài khoản
                if (foundLichHen.getTaiKhoan().getAccountName().equals(maTaiKhoanTable)) {
                    // Chọn dòng tương ứng trong dichVuHenTable
                    dichVuHenTable.setRowSelectionInterval(row, row);
                    // Scroll đến vị trí của dòng đã chọn nếu cần thiết
                    dichVuHenTable.scrollRectToVisible(dichVuHenTable.getCellRect(row, 0, true));
                    break;
                }
            }

            // Tìm và chọn dòng trong lichHenTable
            DefaultTableModel lichHenTableModel = (DefaultTableModel) lichHenTable.getModel();
            for (int row = 0; row < lichHenTableModel.getRowCount(); row++) {
                String maLichHenTable = lichHenTableModel.getValueAt(row, 0).toString();
                if (maLichHenTable.equals(maLichHen)) {
                    // Chọn dòng tương ứng trong lichHenTable
                    lichHenTable.setRowSelectionInterval(row, row);
                    // Scroll đến vị trí của dòng đã chọn nếu cần thiết
                    lichHenTable.scrollRectToVisible(lichHenTable.getCellRect(row, 0, true));
                    break;
                }
            }

            JOptionPane.showMessageDialog(null, "Tìm thấy lịch hẹn.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy mã lịch hẹn.", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    });
}
    
}
