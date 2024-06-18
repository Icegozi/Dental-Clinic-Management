/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mynewteeth.frontend.dashboard_sub_class;

import java.awt.Image;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import mynewteeth.backend.controller.BacSiController;
import mynewteeth.backend.interfaces.IOptionDialogAction;
import mynewteeth.backend.interfaces.IUpdateData;
import mynewteeth.backend.model.BacSi;
import mynewteeth.frontend.DialogProvider;

/**
 *
 * @author Us
 */
public class TabBacSi {

    private final JTable bacSiTable;
    private final JTextField maBSTextField;
    private final JTextField tenBSTextField;
    private final JTextField ngaySinhBSTextField;
    private final JTextField gioiTinhBSTextField;
    private final JTextField queQuanBSTextField;
    private final JTextField soDTTextField;
    private final JTextField chuyenMonTextField;
    private final JTextField chucVuTextField;
    private final JTextField luongThangTextField;
    private final JButton timKiemBtn;
    private final JButton sapXepTenBtn;
    private final JButton sapXepLuong;
    private final JButton themBacSiButton;
    private final JButton capNhatBSButton;
    private final JButton xoaBacSiButton;
    private final JLabel anhBacSiLabel;
    private IUpdateData callback;

    private BacSiController controller;

    public TabBacSi(JTable bacSiTable, JTextField maBSTextField, JTextField tenBSTextField,
            JTextField ngaySinhBSTextField, JTextField gioiTinhBSTextField, JTextField queQuanBSTextField,
            JTextField soDTTextField, JTextField chuyenMonTextField, JTextField chucVuTextField,
            JTextField luongThangTextField, JButton timKiemBtn, JButton sapXepTenBtn, JButton sapXepLuong,
            JButton themBacSiButton, JButton capNhatBSButton, JButton xoaBacSiButton, JLabel anhBacSiLabel, IUpdateData updateDataCallback) {

        this.bacSiTable = bacSiTable;
        this.maBSTextField = maBSTextField;
        this.tenBSTextField = tenBSTextField;
        this.ngaySinhBSTextField = ngaySinhBSTextField;
        this.gioiTinhBSTextField = gioiTinhBSTextField;
        this.queQuanBSTextField = queQuanBSTextField;
        this.soDTTextField = soDTTextField;
        this.chuyenMonTextField = chuyenMonTextField;
        this.chucVuTextField = chucVuTextField;
        this.luongThangTextField = luongThangTextField;
        this.timKiemBtn = timKiemBtn;
        this.sapXepTenBtn = sapXepTenBtn;
        this.sapXepLuong = sapXepLuong;
        this.themBacSiButton = themBacSiButton;
        this.capNhatBSButton = capNhatBSButton;
        this.xoaBacSiButton = xoaBacSiButton;
        this.anhBacSiLabel = anhBacSiLabel;
        callback = updateDataCallback;

        controller = new BacSiController();
        controller.loadFromFile("src/mynewteeth/backend/data_repository/local_data/raw_data/BacSi.txt");
        bindData();
        handleTableBehavior();
        handAddingAction();
        handleDeletingAction();
        handleUpdatingAction();
        handleSearchingAction();
    }

    private void bindData() {
        // Lấy dữ liệu đã được load từ Controller lên rồi đổ vào UI
        List<BacSi> danhSachBacSi = controller.getDanhSachBacSi();

        // Ví dụ: Giả sử mô hình của bảng là DefaultTableModel
        DefaultTableModel model = (DefaultTableModel) bacSiTable.getModel();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        model.setRowCount(0);
        for (BacSi bacSi : danhSachBacSi) {

            Object[] rowData = {
                bacSi.getMaBacSi(),
                bacSi.getHoTen(),
                sdf.format(bacSi.getNgaySinh()),
                bacSi.getGioiTinh(),
                bacSi.getQueQuan(),
                bacSi.getSoDienThoai(),
                bacSi.getChuyenMon(),
                bacSi.getChucVu(),
                bacSi.getLuongThang()

            };
            model.addRow(rowData);
        }
        
        // set Image for imagelabel 
        ImageIcon imageIcon = new ImageIcon("src/mynewteeth/backend/data_repository/assets/app_images/new_teeth_logo.PNG");
        anhBacSiLabel.setIcon(new ImageIcon(imageIcon.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH)));
    }

    private void handleTableBehavior() {
        // Xử lý sự kiện liên quan đến bảng : Khi click vào 1 ròng trên bảng => Lấy dữ liệu dòng đó đưa vào các text field
        bacSiTable.getSelectionModel().addListSelectionListener(event -> {
            int selectedRow = bacSiTable.getSelectedRow();
            if (selectedRow != -1) {
                List<BacSi> danhSachBacSi = controller.getDanhSachBacSi();
                String maBacSiSelected = (String) bacSiTable.getValueAt(selectedRow, 0); // Lấy mã bác sĩ từ dòng được chọn

                // Tìm đối tượng BacSi có mã trùng với mã được chọn
                for (BacSi bacSi : danhSachBacSi) {
                    if (bacSi.getMaBacSi().equals(maBacSiSelected)) {
                        // Gán các thuộc tính cần thiết của đối tượng BacSi vào các TextField
                        maBSTextField.setText(bacSi.getMaBacSi());
                        tenBSTextField.setText(bacSi.getHoTen());
                        String ngaySinhStr = new SimpleDateFormat("dd/MM/yyyy").format(bacSi.getNgaySinh());
                        ngaySinhBSTextField.setText(ngaySinhStr);
                        gioiTinhBSTextField.setText(bacSi.getGioiTinh());

                        // Thêm các thuộc tính cần thiết khác
                        queQuanBSTextField.setText(bacSi.getQueQuan());
                        soDTTextField.setText(bacSi.getSoDienThoai());
                        chuyenMonTextField.setText(bacSi.getChuyenMon());
                        chucVuTextField.setText(bacSi.getChucVu());
                        luongThangTextField.setText(String.valueOf(bacSi.getLuongThang()));

                        // Kết thúc vòng lặp sau khi đã tìm thấy đối tượng phù hợp
                        break;
                    }
                }
            }
        });
    }

    private void handAddingAction() {
        themBacSiButton.addActionListener((e) -> {
            System.out.println("Add a new item!");
            String maBacSi = maBSTextField.getText();
            String hoTen = tenBSTextField.getText();
            String ngaySinhStr = ngaySinhBSTextField.getText();
            System.out.println(ngaySinhStr);
            String gioiTinh = gioiTinhBSTextField.getText();
            String queQuan = queQuanBSTextField.getText();
            String soDienThoai = soDTTextField.getText();
            String chuyenMon = chuyenMonTextField.getText();
            String chucVu = chucVuTextField.getText();
            String luongThangStr = luongThangTextField.getText();

            // Kiểm tra tính hợp lệ của dữ liệu
            if (maBacSi.isEmpty() || hoTen.isEmpty() || ngaySinhStr.isEmpty() || gioiTinh.isEmpty()
                    || queQuan.isEmpty() || soDienThoai.isEmpty() || chuyenMon.isEmpty() || chucVu.isEmpty()
                    || luongThangStr.isEmpty()) {
                DialogProvider.showMessageDialog("Vui lòng điền đầy đủ thông tin!", "Thông báo");
                return;
            }

            // Chuyển đổi chuỗi ngày sinh thành đối tượng Date
            Date ngaySinh;
            try {
                ngaySinh = new SimpleDateFormat("dd/MM/yyyy").parse(ngaySinhStr);
            } catch (ParseException ex) {
                DialogProvider.showMessageDialog("Ngày sinh không hợp lệ!", "Thông báo");
                return;
            }
            try {
                if (!soDienThoai.matches("^(\\(?\\d{3}\\)?[- .]?\\d{3}[- .]?\\d{4})$")) {
                    throw new Exception("Số điện thoại không hợp lệ!");
                }
            } catch (Exception ex) {
                DialogProvider.showMessageDialog(ex.getMessage(), "Thông báo");
                return;
            }

            // Chuyển đổi chuỗi lương tháng thành số
            double luongThang;
            try {
                luongThang = Double.parseDouble(luongThangStr);
            } catch (NumberFormatException ex) {
                DialogProvider.showMessageDialog("Lương tháng không hợp lệ!", "Thông báo");
                return;
            }

            // Tạo đối tượng BacSi mới
            BacSi newBacSi = new BacSi(maBacSi, hoTen, ngaySinh, gioiTinh, queQuan, soDienThoai, chuyenMon, chucVu, luongThang);

            // Gửi dữ liệu đến lớp xử lý logic để thêm bác sĩ
            try {
                boolean success = controller.addBacSi(newBacSi);

                if (success) {
                    // Cập nhật giao diện người dùng sau khi thêm bác sĩ thành công
                    DialogProvider.showMessageDialog("Thêm bác sĩ thành công!", "Thông báo");
                    // Clear các trường nhập liệu
                    maBSTextField.setText("");
                    tenBSTextField.setText("");
                    ngaySinhBSTextField.setText("");
                    gioiTinhBSTextField.setText("");
                    queQuanBSTextField.setText("");
                    soDTTextField.setText("");
                    chuyenMonTextField.setText("");
                    chucVuTextField.setText("");
                    luongThangTextField.setText("");
                    // Cập nhật dữ liệu cho bảng
                    bindData();
                    callback.onUpdate(newBacSi);
                } else {
                    DialogProvider.showMessageDialog("Có lỗi xảy ra khi thêm bác sĩ!", "Thông báo");
                }
            } catch (Exception ex) {
                DialogProvider.showMessageDialog("Có lỗi xảy ra khi thêm bác sĩ!", "Thông báo");
                ex.printStackTrace(); // In ra stack trace để xem lỗi là gì
            }
        });
    }

    private void handleDeletingAction() {
        xoaBacSiButton.addActionListener((e) -> {
            int selectedRow = bacSiTable.getSelectedRow();
            if (selectedRow == -1) {
                DialogProvider.showMessageDialog("Vui lòng chọn một bác sĩ để xóa!", "Thông báo");
                return;
            }

            String maBacSi = (String) bacSiTable.getValueAt(selectedRow, 0);

            DialogProvider.showConfirmDialog("Bạn chắc chắn muốn xóa bác sĩ này?", "Cảnh báo!", "Chiến", new IOptionDialogAction() {
                @Override
                public void onYesOption(Object object) {
                    // Xử lý logic xóa bác sĩ
                    boolean success = controller.removeBacSiByMa(maBacSi);

                    if (success) {
                        DialogProvider.showMessageDialog("Xóa bác sĩ thành công!", "Thông báo!");
                        // Cập nhật lại bảng
                        bindData();
                        callback.onUpdate(maBacSi);
                        maBSTextField.setText("");
                        tenBSTextField.setText("");
                        ngaySinhBSTextField.setText("");
                        gioiTinhBSTextField.setText("");
                        queQuanBSTextField.setText("");
                        soDTTextField.setText("");
                        chuyenMonTextField.setText("");
                        chucVuTextField.setText("");
                        luongThangTextField.setText("");
                    } else {
                        DialogProvider.showMessageDialog("Có lỗi xảy ra, vui lòng thử lại!", "Thông báo");
                    }
                }

                @Override
                public void onNoOption() {
                    // Không làm gì cả khi người dùng chọn No
                }
            });
        });
    }

    private void handleUpdatingAction() {
        capNhatBSButton.addActionListener((e) -> {
            int selectedRow = bacSiTable.getSelectedRow();
            if (selectedRow == -1) {
                DialogProvider.showMessageDialog("Vui lòng chọn một bác sĩ để sửa!", "Thông báo");
                return;
            }

            String maBacSi = maBSTextField.getText();
            String hoTen = tenBSTextField.getText();
            String ngaySinhStr = ngaySinhBSTextField.getText();
            String gioiTinh = gioiTinhBSTextField.getText();
            String queQuan = queQuanBSTextField.getText();
            String soDienThoai = soDTTextField.getText();
            String chuyenMon = chuyenMonTextField.getText();
            String chucVu = chucVuTextField.getText();
            String luongThangStr = luongThangTextField.getText();

            // Kiểm tra tính hợp lệ của dữ liệu
            if (maBacSi.isEmpty() || hoTen.isEmpty() || ngaySinhStr.isEmpty() || gioiTinh.isEmpty()
                    || queQuan.isEmpty() || soDienThoai.isEmpty() || chuyenMon.isEmpty() || chucVu.isEmpty()
                    || luongThangStr.isEmpty()) {
                DialogProvider.showMessageDialog("Vui lòng điền đầy đủ thông tin!", "Thông báo");
                return;
            }

            // Chuyển đổi chuỗi ngày sinh thành đối tượng Date
            Date ngaySinh;
            try {
                ngaySinh = new SimpleDateFormat("dd/MM/yyyy").parse(ngaySinhStr);
            } catch (ParseException ex) {
                DialogProvider.showMessageDialog("Ngày sinh không hợp lệ!", "Thông báo");
                return;
            }

            // Chuyển đổi chuỗi lương tháng thành số
            double luongThang;
            try {
                luongThang = Double.parseDouble(luongThangStr);
            } catch (NumberFormatException ex) {
                DialogProvider.showMessageDialog("Lương tháng không hợp lệ!", "Thông báo");
                return;
            }

            // Kiểm tra định dạng số điện thoại
            if (!soDienThoai.matches("\\d{10}")) {
                DialogProvider.showMessageDialog("Số điện thoại không hợp lệ!", "Thông báo");
                return;
            }

            // Tạo đối tượng BacSi mới
            BacSi updatedBacSi = new BacSi(maBacSi, hoTen, ngaySinh, gioiTinh, queQuan, soDienThoai, chuyenMon, chucVu, luongThang);

            // Gửi dữ liệu đến lớp xử lý logic để cập nhật bác sĩ
            boolean success = controller.updateBacSi(updatedBacSi);

            // Cập nhật giao diện người dùng sau khi sửa bác sĩ thành công
            if (success) {
                DialogProvider.showMessageDialog("Cập nhật bác sĩ thành công!", "Thông báo");
                // Cập nhật dữ liệu cho bảng
                bindData();
                callback.onUpdate(updatedBacSi);
            } else {
                DialogProvider.showMessageDialog("Có lỗi xảy ra, vui lòng thử lại!", "Thông báo");
            }
        });
    }

    private void handleSearchingAction() {
        timKiemBtn.addActionListener((e) -> {
            String maBS = maBSTextField.getText().trim();
            List<BacSi> danhSachBacSi = controller.getDanhSachBacSi();

            if (maBS.isEmpty()) {
                DialogProvider.showMessageDialog("Vui lòng nhập mã bác sĩ!", "Thông báo");
                return;
            }

            // Tìm bác sĩ theo mã
            BacSi foundBacSi = null;
            int rowIndex = -1;
            for (int i = 0; i < danhSachBacSi.size(); i++) {
                BacSi bacSi = danhSachBacSi.get(i);
                if (bacSi.getMaBacSi().equals(maBS)) {
                    foundBacSi = bacSi;
                    rowIndex = i;
                    break;
                }
            }

            if (foundBacSi != null) {
                // Hiển thị thông tin lên các trường văn bản
                tenBSTextField.setText(foundBacSi.getHoTen());
                ngaySinhBSTextField.setText(new SimpleDateFormat("dd/MM/yyyy").format(foundBacSi.getNgaySinh()));
                gioiTinhBSTextField.setText(foundBacSi.getGioiTinh());
                queQuanBSTextField.setText(foundBacSi.getQueQuan());
                soDTTextField.setText(foundBacSi.getSoDienThoai());
                chuyenMonTextField.setText(foundBacSi.getChuyenMon());
                chucVuTextField.setText(foundBacSi.getChucVu());
                luongThangTextField.setText(String.valueOf(foundBacSi.getLuongThang()));

                // Chọn dòng tương ứng trong bảng
                bacSiTable.setRowSelectionInterval(rowIndex, rowIndex);
                bacSiTable.scrollRectToVisible(bacSiTable.getCellRect(rowIndex, 0, true));
            } else {
                DialogProvider.showMessageDialog("Không tìm thấy bác sĩ với mã này!", "Thông báo");
            }
        });
    }
}
