/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mynewteeth.frontend.dashboard_sub_class;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import mynewteeth.backend.controller.BenhNhanController;
import mynewteeth.backend.interfaces.IOptionDialogAction;
import mynewteeth.backend.interfaces.IUpdateData;
import mynewteeth.backend.interfaces.InvalidSoDienThoaiException;
import mynewteeth.backend.model.BenhNhan;
import mynewteeth.frontend.DialogProvider;

/**
 *
 * @author Us
 */
public class TabBenhNhan {

    private final JTable benhNhanTable;
    private final JTextField maBNTextField;
    private final JTextField tenBNTextField;
    private final JTextField ngaySinhBNTextField;
    private final JTextField dienThoaiBNTextField;
    private final JTextField queQuanBNTextField;
    private final JTextField gioiTinhBnTextField;
    private final JButton themBNButton, xoaBNButton, suaBNButton, timKiemButton;
    private IUpdateData callback;
    
    private BenhNhanController controller;
 
    public TabBenhNhan(JTable benhNhanTable, JTextField maBNTextField, JTextField tenBNTextField,
            JTextField ngaySinhBNTextField, JTextField dienThoaiBNTextField, JTextField queQuanBNTextField, JTextField gioiTinhBnTextField,
            JButton themBNButton, JButton xoaBNButton, JButton suaBNButton, JButton timKiemButton, IUpdateData updateDataCallback) {
        this.benhNhanTable = benhNhanTable;
        this.maBNTextField = maBNTextField;
        this.tenBNTextField = tenBNTextField;
        this.ngaySinhBNTextField = ngaySinhBNTextField;
        this.dienThoaiBNTextField = dienThoaiBNTextField;
        this.queQuanBNTextField = queQuanBNTextField;
        this.gioiTinhBnTextField = gioiTinhBnTextField;
        this.themBNButton = themBNButton;
        this.xoaBNButton = xoaBNButton;
        this.suaBNButton = suaBNButton;
        this.timKiemButton = timKiemButton;
        callback = updateDataCallback;
        // add Controller here
        controller = new BenhNhanController();
        bindData();
        // handle action
        handleTableBehavior();
        handAddingAction();
        handleDeletingAction();
        handleUpdatingAction();
        handleSearchingAction();

    }
    
    private void bindData() {
        // Lấy liệu đã được load từ Controller lên rồi đổ vào UI
        List<BenhNhan> danhSachBenhNhan = controller.getDanhSachBenhNhan();

        // Ví dụ: Giả sử mô hình của bảng là DefaultTableModel
        DefaultTableModel model = (DefaultTableModel) benhNhanTable.getModel();

        // Xóa dữ liệu cũ trong bảng (nếu có)
        model.setRowCount(0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // Thêm từng bệnh nhân vào bảng
        for (BenhNhan benhNhan : danhSachBenhNhan) {
            Object[] rowData = {
                benhNhan.getMaBenhNhan(),
                benhNhan.getTenBenhNhan(),
                benhNhan.getGioiTinh(),
                dateFormat.format(benhNhan.getNgaySinh()), // Định dạng ngày sinh
                benhNhan.getQueQuan(),
                benhNhan.getSoDienThoai()

            };
            model.addRow(rowData);
        }

    }

    private void handleTableBehavior() {

        // Xử lý sự kiện liên quan đến bảng : Khi click vào 1 ròng trên bảng => Lấy dữ liệu dòng đó đưa vào các text field
        benhNhanTable.getSelectionModel().addListSelectionListener(event -> {
            if (event.getValueIsAdjusting()) {
                return;
            }
            int selectedRow = benhNhanTable.getSelectedRow();
            if (selectedRow != -1) {
                maBNTextField.setText((String) benhNhanTable.getValueAt(selectedRow, 0));
                tenBNTextField.setText((String) benhNhanTable.getValueAt(selectedRow, 1));
                gioiTinhBnTextField.setText((String) benhNhanTable.getValueAt(selectedRow, 2));

                // Lấy chuỗi ngày sinh từ bảng
                String ngaySinhStr = (String) benhNhanTable.getValueAt(selectedRow, 3);
                
                // Đặt giá trị ngày sinh vào text field
                ngaySinhBNTextField.setText(ngaySinhStr);

                queQuanBNTextField.setText((String) benhNhanTable.getValueAt(selectedRow, 4));
                dienThoaiBNTextField.setText((String) benhNhanTable.getValueAt(selectedRow, 5));
            }
        });
    }

    private void resetTextField() {
        maBNTextField.setText("");
        tenBNTextField.setText("");
        gioiTinhBnTextField.setText("");
        ngaySinhBNTextField.setText("");
        queQuanBNTextField.setText("");
        dienThoaiBNTextField.setText("");
    }

    private void handAddingAction() {
        themBNButton.addActionListener((e) -> {
            String maBN = maBNTextField.getText();
            String tenBN = tenBNTextField.getText();
            String ngaySinhStr = ngaySinhBNTextField.getText();
            String dienThoai = dienThoaiBNTextField.getText();
            String queQuan = queQuanBNTextField.getText();
            String gioiTinh = gioiTinhBnTextField.getText();

            // Kiểm tra tính hợp lệ của dữ liệu
            if (maBN.isEmpty() || tenBN.isEmpty() || ngaySinhStr.isEmpty() || dienThoai.isEmpty() || queQuan.isEmpty() || gioiTinh.isEmpty()) {
                DialogProvider.showMessageDialog("Vui lòng điền đầy đủ thông tin!", "Thông báo");
                return;
            }

            // Chuyển đổi chuỗi ngày sinh thành đối tượng Date
            Date ngaySinh;
            try {
                ngaySinh = new SimpleDateFormat("yyyy-MM-dd").parse(ngaySinhStr);
            } catch (ParseException ex) {
                DialogProvider.showMessageDialog("Ngày sinh không hợp lệ!", "Thông báo");
                return;
            }

            // Tạo đối tượng Bệnh Nhân mới
            BenhNhan newBenhNhan = new BenhNhan(maBN, tenBN, gioiTinh, ngaySinh, queQuan, dienThoai);

            // Gửi dữ liệu đến lớp xử lý logic để thêm bệnh nhân
            boolean success = controller.addBenhNhan(newBenhNhan);

            // Cập nhật giao diện người dùng sau khi thêm bệnh nhân thành công
            if (success) {
                DialogProvider.showMessageDialog("Thêm bệnh nhân thành công!", "Thông báo");
                // Clear các trường nhập liệu
                resetTextField();
                // Cập nhật dữ liệu cho bảng
                bindData();
            }
        });
    }

    private void handleDeletingAction() {
        xoaBNButton.addActionListener((e) -> {
            int selectedRow = benhNhanTable.getSelectedRow();
            if (selectedRow == -1) {
                DialogProvider.showMessageDialog("Vui lòng chọn một bệnh nhân để xóa!", "Thông báo");
                return;
            }

            String maBenhNhan = (String) benhNhanTable.getValueAt(selectedRow, 0);
            BenhNhan bn = controller.findBenhNhanByMa(maBenhNhan);
            DialogProvider.showConfirmDialog("Bạn chắc chắn muốn xóa bệnh nhân này?", "Cảnh báo!", "", new IOptionDialogAction() {
                @Override
                public void onYesOption(Object object) {
                    // Xử lý logic xóa bệnh nhân
                    boolean success = controller.removeBenhNhanByMa(maBenhNhan);

                    if (success) {
                        DialogProvider.showMessageDialog("Xóa bệnh nhân thành công!", "Thông báo!");
                        // Cập nhật lại bảng
                        bindData();
                        resetTextField();
                        callback.onUpdate(bn);
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

    //benhNhanTable
    private void handleUpdatingAction() {
        suaBNButton.addActionListener((e) -> {
            int selectedRow = benhNhanTable.getSelectedRow();
            if (selectedRow == -1) {
                DialogProvider.showMessageDialog("Vui lòng chọn một bệnh nhân để sửa!", "Thông báo");
                return;
            }

            String maBN = maBNTextField.getText();
            String tenBN = tenBNTextField.getText();
            String ngaySinhStr = ngaySinhBNTextField.getText();
            String dienThoai = dienThoaiBNTextField.getText();
            String queQuan = queQuanBNTextField.getText();
            String gioiTinh = gioiTinhBnTextField.getText();

            // Kiểm tra tính hợp lệ của dữ liệu
            if (maBN.isEmpty() || tenBN.isEmpty() || ngaySinhStr.isEmpty() || dienThoai.isEmpty() || queQuan.isEmpty() || gioiTinh.isEmpty()) {
                DialogProvider.showMessageDialog("Vui lòng điền đầy đủ thông tin!", "Thông báo");
                return;
            }

            // Chuyển đổi chuỗi ngày sinh thành đối tượng Date
            Date ngaySinh;
            try {
                ngaySinh = new SimpleDateFormat("yyyy-MM-dd").parse(ngaySinhStr);
            } catch (ParseException ex) {
                DialogProvider.showMessageDialog("Ngày sinh không hợp lệ!", "Thông báo");
                return;
            }

            // Kiểm tra số điện thoại trùng lặp
            try {
                if (controller.isPhoneNumberDuplicate(dienThoai, maBN)) {
                    throw new InvalidSoDienThoaiException("Số điện thoại đã tồn tại!");
                }

                // Tạo đối tượng Bệnh Nhân mới
                BenhNhan updatedBenhNhan = new BenhNhan(maBN, tenBN, gioiTinh, ngaySinh, queQuan, dienThoai);

                // Cập nhật thông tin bệnh nhân trong danh sách
                if (controller.updateBenhNhan(updatedBenhNhan)) {
                    DialogProvider.showMessageDialog("Cập nhật bệnh nhân thành công!", "Thông báo");

                    // Cập nhật lại bảng dữ liệu
                    benhNhanTable.setValueAt(updatedBenhNhan.getMaBenhNhan(), selectedRow, 0);
                    benhNhanTable.setValueAt(updatedBenhNhan.getTenBenhNhan(), selectedRow, 1);
                    benhNhanTable.setValueAt(new SimpleDateFormat("yyyy-MM-dd").format(updatedBenhNhan.getNgaySinh()), selectedRow, 3);
                    benhNhanTable.setValueAt(updatedBenhNhan.getGioiTinh(), selectedRow, 2);
                    benhNhanTable.setValueAt(updatedBenhNhan.getQueQuan(), selectedRow, 4);
                    benhNhanTable.setValueAt(updatedBenhNhan.getSoDienThoai(), selectedRow, 5);
                    
                    callback.onUpdate(updatedBenhNhan);
                } else {
                    DialogProvider.showMessageDialog("Cập nhật bệnh nhân thất bại!", "Thông báo");
                }
            } catch (InvalidSoDienThoaiException ex) {
                DialogProvider.showMessageDialog(ex.getMessage(), "Thông báo");
            }
        });
    }

    private void handleSearchingAction() {
        timKiemButton.addActionListener((e) -> {
            String maBN = maBNTextField.getText().trim();
            List<BenhNhan> danhSachBenhNhan = controller.getDanhSachBenhNhan();

            if (maBN.isEmpty()) {
                DialogProvider.showMessageDialog("Vui lòng nhập mã bệnh nhân!", "Thông báo");
                return;
            }

            // Tìm bệnh nhân theo mã
            BenhNhan foundBenhNhan = null;
            int rowIndex = -1;
            for (int i = 0; i < danhSachBenhNhan.size(); i++) {
                BenhNhan benhNhan = danhSachBenhNhan.get(i);
                if (benhNhan.getMaBenhNhan().equals(maBN)) {
                    foundBenhNhan = benhNhan;
                    rowIndex = i;
                    break;
                }
            }

            if (foundBenhNhan != null) {
                // Hiển thị thông tin lên các trường văn bản
                tenBNTextField.setText(foundBenhNhan.getTenBenhNhan());
                gioiTinhBnTextField.setText(foundBenhNhan.getGioiTinh());
                Date ngaySinh = foundBenhNhan.getNgaySinh();
                if (ngaySinh != null) {
                    ngaySinhBNTextField.setText(new SimpleDateFormat("dd/MM/yyyy").format(ngaySinh));
                } else {
                    ngaySinhBNTextField.setText("");
                }
                queQuanBNTextField.setText(foundBenhNhan.getQueQuan());
                dienThoaiBNTextField.setText(foundBenhNhan.getSoDienThoai());

                // Chọn dòng tương ứng trong bảng
                benhNhanTable.setRowSelectionInterval(rowIndex, rowIndex);
                benhNhanTable.scrollRectToVisible(benhNhanTable.getCellRect(rowIndex, 0, true));
            } else {
                DialogProvider.showMessageDialog("Không tìm thấy bệnh nhân với mã này!", "Thông báo");
            }
        });
    }
}
