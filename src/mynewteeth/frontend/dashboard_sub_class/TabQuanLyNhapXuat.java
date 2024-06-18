/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mynewteeth.frontend.dashboard_sub_class;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import mynewteeth.backend.controller.PhieuNhapXuatController;
import mynewteeth.backend.model.PNX_VP;
import mynewteeth.backend.model.PhieuNhapXuat;

/**
 *
 * @author Us
 */
public class TabQuanLyNhapXuat {

    private final JTable nhapXuatTable;
    private final JTextField maDonTextField;
    private final JTextField ngayGDTextField;
    private final JTextField loaiGDTextField;
    private final JTextField nhaCCTextField;
    private final JTextField tongTienTextField;
    private final JTable vatPhamGDTable;
    private final JButton themNhapXuatButton;
    private final JButton xoaNhapXuatButton;
    private final JButton suaNhapXuatButton;
    
    private PhieuNhapXuatController controllerNhapXuat;

    public TabQuanLyNhapXuat(JTable nhapXuatTable, JTextField maDonTextField, JTextField ngayGDTextField,
            JTextField loaiGDTextField, JTextField nhaCCTextField, JTextField tongTienTextField,
            JTable vatPhamGDTable, JButton themNhapXuatButton, JButton xoaNhapXuatButton, JButton suaNhapXuatButton) {
        this.nhapXuatTable = nhapXuatTable;
        this.maDonTextField = maDonTextField;
        this.ngayGDTextField = ngayGDTextField;
        this.loaiGDTextField = loaiGDTextField;
        this.nhaCCTextField = nhaCCTextField;
        this.tongTienTextField = tongTienTextField;
        this.vatPhamGDTable = vatPhamGDTable;
        this.themNhapXuatButton = themNhapXuatButton;
        this.xoaNhapXuatButton = xoaNhapXuatButton;
        this.suaNhapXuatButton = suaNhapXuatButton;
        
        try {
            controllerNhapXuat = new PhieuNhapXuatController();
            bindData();
            handAddingAction();
            handleTableBehavior();
            handleUpdatingAction();
            handRemovingAction();
            updateTableThuoc();
            test();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void resetTextField() {
        maDonTextField.setText("");
        ngayGDTextField.setText("");
        loaiGDTextField.setText("");
        nhaCCTextField.setText("");
        tongTienTextField.setText("");
        vatPhamGDTable.clearSelection();
        DefaultTableModel model1 = (DefaultTableModel) vatPhamGDTable.getModel();
        model1.setRowCount(0);
        // Thêm 5 dòng trống vào bảng thuốc
        for (int i = 0; i < 10; i++) {
            model1.addRow(new Object[]{null, null, null, null, null, null, null});
        }
    }

    private void getVatPhamGDJtable(List<PNX_VP> vt) throws Exception {
        DefaultTableModel model = (DefaultTableModel) vatPhamGDTable.getModel();
        int rowCount = model.getRowCount();

        // Duyệt qua tất cả các hàng của bảng thuocKeDonTable
        for (int i = 0; i < rowCount; i++) {
            // Lấy mã vật tư từ cột đầu tiên (giả sử mã vật tư nằm ở cột đầu tiên)
            String maVatPham = (String) model.getValueAt(i, 0);

            if (maVatPham != null && !maVatPham.trim().isEmpty()) {
                String tenVatPham = (String) model.getValueAt(i, 1);
                String loai = (String) model.getValueAt(i, 2);
                String donVi = (String) model.getValueAt(i, 3);
                // Kiểm tra giá trị của cột số lượng và giá tiền
                Object soLuongObj = model.getValueAt(i, 4);
                Object giaTienObj = model.getValueAt(i, 5);
                Object thanhTienObj = model.getValueAt(i, 6);

                if (soLuongObj == null || soLuongObj.toString().trim().isEmpty()) {
                    throw new Exception("Số lượng chưa được nhập ở hàng " + (i + 1));
                }

                if (giaTienObj == null || giaTienObj.toString().trim().isEmpty()) {
                    throw new Exception("Giá tiền chưa được nhập ở hàng " + (i + 1));
                }

                int soLuong;
                double giaTien;
                double thanhTien;

                try {
                    soLuong = Integer.parseInt(soLuongObj.toString().trim());
                    if (soLuong < 0) {
                        throw new Exception("Số lượng phải là số nguyên không âm ở hàng " + (i + 1));
                    }
                } catch (NumberFormatException e) {
                    throw new Exception("Số lượng có định dạng không hợp lệ ở hàng " + (i + 1));
                }

                try {
                    giaTien = Double.parseDouble(giaTienObj.toString().trim());
                    if (giaTien < 0) {
                        throw new Exception("Giá tiền phải là số không âm ở hàng " + (i + 1));
                    }
                } catch (NumberFormatException e) {
                    throw new Exception("Giá tiền có định dạng không hợp lệ ở hàng " + (i + 1));
                }

                try {
                    thanhTien = Double.parseDouble(thanhTienObj.toString().trim());
                    if (giaTien < 0) {
                        throw new Exception("Giá tiền phải là số không âm ở hàng " + (i + 1));
                    }
                } catch (NumberFormatException e) {
                    throw new Exception("Giá tiền có định dạng không hợp lệ ở hàng " + (i + 1));
                }

                // Thêm vật tư vào danh sách
                vt.add(new PNX_VP(maDonTextField.getText(), maVatPham, tenVatPham, loai, donVi, soLuong, giaTien, thanhTien));
            }
        }
    }

    // Lấy liệu đã được load từ Controller lên rồi đổ vào UI
    private void bindData() {
        try {
            List<PhieuNhapXuat> pnx = controllerNhapXuat.getDanhSachPhieuNhapXuat();
            DefaultTableModel model = (DefaultTableModel) nhapXuatTable.getModel();

            // Xóa dữ liệu cũ trong bảng (nếu có)
            model.setRowCount(0);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            for (PhieuNhapXuat pnx1 : pnx) {
                String ngayGiaoDichFormatted = dateFormat.format(pnx1.getNgayGiaoDich());

                Object[] rowData = {
                    pnx1.getMaDon(),
                    ngayGiaoDichFormatted,
                    pnx1.getLoaiGiaoDich(),
                    pnx1.getNhaCungCap(),
                    pnx1.getTongTien()
                };
                model.addRow(rowData);
            }

            DefaultTableModel model1 = (DefaultTableModel) vatPhamGDTable.getModel();
            model1.setRowCount(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleTableBehavior() {
        nhapXuatTable.getSelectionModel().addListSelectionListener(e -> {
            //test_top
            if (e.getValueIsAdjusting()) {
                return;
            }
            int selectedIndex = nhapXuatTable.getSelectedRow();
            if (selectedIndex != -1) {
                for (PhieuNhapXuat f : controllerNhapXuat.getDanhSachPhieuNhapXuat()) {
                    if (f.getMaDon().equals(nhapXuatTable.getValueAt(selectedIndex, 0).toString())) {
                        maDonTextField.setText(f.getMaDon());
                        Date ngayGiaoDich = f.getNgayGiaoDich();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String fomattedNgayGiaoDich = dateFormat.format(ngayGiaoDich);
                        ngayGDTextField.setText(fomattedNgayGiaoDich);
                        loaiGDTextField.setText(f.getLoaiGiaoDich());
                        nhaCCTextField.setText(f.getNhaCungCap());
                        tongTienTextField.setText(Double.toString(f.getTongTien()));

                        //hiển thi thuốc vào bảng thuốc
                        DefaultTableModel model1 = (DefaultTableModel) vatPhamGDTable.getModel();
                        model1.setRowCount(0);

                        for (PNX_VP xx : controllerNhapXuat.getVatPhamConTroller()) {
                            if (xx.getMaDon().equals(f.getMaDon())) {
                                Object[] rowData = {
                                    xx.getMaVatPham(),
                                    xx.getTenVatPham(),
                                    xx.getLoai(),
                                    xx.getDonVi(),
                                    xx.getSoLuong(),
                                    xx.getGiaTien(),
                                    xx.getThanhTien()
                                };
                                model1.addRow(rowData);
                            }
                        }

                        // Thêm 5 dòng trống vào bảng 
                        for (int i = 0; i < 10; i++) {
                            model1.addRow(new Object[]{null, null, null, null, null, null, null});
                        }
                    }
                }
            }

        });
    }

    public boolean maDonIsExist(String maDon) {
        for (PhieuNhapXuat pnx : this.controllerNhapXuat.getDanhSachPhieuNhapXuat()) {
            if (pnx.getMaDon().equals(maDon)) {
                return true;
            }
        }
        return false;
    }

    private void handAddingAction() {
        themNhapXuatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String maDon = maDonTextField.getText().trim();
                String ngayGD = ngayGDTextField.getText().trim();
                String loaiGD = loaiGDTextField.getText().trim();
                String nhaCC = nhaCCTextField.getText().trim();
                double tongTien = Double.parseDouble(tongTienTextField.getText());
                List<PNX_VP> newVT = new ArrayList<>();

                if (maDon.isEmpty() || ngayGD.isEmpty() || loaiGD.isEmpty() 
                        || nhaCC.isEmpty() || tongTienTextField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!");
                    return; // Dừng lại nếu có thông tin bị thiếu
                }

                if (!isValidDateFormat(ngayGD)) {
                    JOptionPane.showMessageDialog(null, "Định dạng ngày tháng phải là yyyy-MM-dd!");
                    return;
                }
                double tongTienDouble;
                try {
                    tongTienDouble = tongTien;
                    if (tongTienDouble <= 0) {
                        throw new Exception("Tổng tiền phải là số dương");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Tổng tiền không hợp lệ. Vui lòng nhập số.");
                    return;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                    return;
                }

                try {
                    // Lấy thuốc từ bảng thuocKeDonTable
                    getVatPhamGDJtable(newVT);
                    if (maDonIsExist(maDon)) {
                        JOptionPane.showMessageDialog(null, "Mã đơn đã tồn tại, vui lòng nhập mã khác!");
                        return;
                    }
                    boolean valid1 = controllerNhapXuat.themPNX(maDon, ngayGD, loaiGD, nhaCC, tongTienDouble, newVT);
                    if (valid1) {
                        DefaultTableModel model = (DefaultTableModel) nhapXuatTable.getModel();
                        model.addRow(new Object[]{maDon, ngayGD, loaiGD, nhaCC, tongTien});

                        JOptionPane.showMessageDialog(null, "Thêm phiếu nhập xuất thành công!");

                        // Không cần reset model1, chỉ cần thêm một dòng trống mới vào vatPhamGDTable
                        DefaultTableModel model1 = (DefaultTableModel) vatPhamGDTable.getModel();
                        model1.addRow(new Object[]{"", "", "", "", "", "", ""});
                    }
                } catch (Exception d) {
                    JOptionPane.showMessageDialog(null, d.getMessage());
                }
            }
        });
    }

    private boolean isValidDateFormat(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private void test() {
        vatPhamGDTable.getModel().addTableModelListener((e) -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                Object newValue = ((DefaultTableModel) e.getSource()).getValueAt(row, column);
                System.out.println("Cell at row " + row + ", column " + column + " changed to: " + newValue);
            }
        });
        vatPhamGDTable.getSelectionModel().addListSelectionListener((e) -> {
            if (e.getValueIsAdjusting()) {
                return;
            }
            System.out.println("Chon row : " + vatPhamGDTable.getSelectedRow());
        });
    }

    private void handleUpdatingAction() {
        suaNhapXuatButton.addActionListener(e -> {
            String maDon = maDonTextField.getText().trim();
            String ngayGD = ngayGDTextField.getText().trim();
            String loaiGD = loaiGDTextField.getText().trim();
            String nhaCC = nhaCCTextField.getText().trim();
            String tongTien = tongTienTextField.getText().trim();
            List<PNX_VP> newListVT = new ArrayList<>();

            if (maDon.isEmpty() || ngayGD.isEmpty() || loaiGD.isEmpty() || nhaCC.isEmpty() || tongTien.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!");
                return;
            }

            if (!isValidDateFormat(ngayGD) || !isValidDateFormat(ngayGD)) {
                JOptionPane.showMessageDialog(null, "Định dạng ngày tháng phải là yyyy-MM-dd!");
                return;
            }
            double tongTienDouble;
            try {
                tongTienDouble = Double.parseDouble(tongTien);
                if (tongTienDouble <= 0) {
                    throw new Exception("Tổng tiền phải là số dương");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Tổng tiền không hợp lệ. Vui lòng nhập số.");
                return;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
                return;
            }
            try {

                getVatPhamGDJtable(newListVT);
                controllerNhapXuat.suaPNX(maDon, ngayGD, loaiGD, nhaCC, tongTienDouble,newListVT );
                bindData();
                resetTextField();
                JOptionPane.showMessageDialog(null, "Sửa thông tin phiếu nhập xuất thành công!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }

        });
    }

    private void handRemovingAction() {
        xoaNhapXuatButton.addActionListener(e -> {
            int selectedIndex = nhapXuatTable.getSelectedRow();
            if (selectedIndex != -1) {
                String maDon = nhapXuatTable.getValueAt(selectedIndex, 0).toString(); // Lấy mã hồ sơ bệnh nhân từ dòng được chọn

                // Xác nhận việc xóa
                int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa phiếu nhập xuất với mã: " + maDon + "?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    // Xóa hồ sơ bệnh nhân trong danh sách trong controller
                    try {
                        controllerNhapXuat._xoaPNX_VP(maDon);
                        boolean found = controllerNhapXuat._xoaPNX(maDon);

                        if (found) {
                            // Cập nhật file lưu trữ
                            controllerNhapXuat.saveToPNXFile();

                            // Cập nhật bảng
                            DefaultTableModel model = (DefaultTableModel) nhapXuatTable.getModel();
                            model.removeRow(selectedIndex);

                            DefaultTableModel model1 = (DefaultTableModel) vatPhamGDTable.getModel();
                            model1.setRowCount(0);

                            JOptionPane.showMessageDialog(null, "Xóa phiếu nhập xuất thành công!");

                            //cập nhật textfield
                            resetTextField();
                        } else {
                            JOptionPane.showMessageDialog(null, "Không tìm thấy phiếu nhập xuất với mã: " + maDon);
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi khi lưu file");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một phiếu nhập xuất để xóa.");
            }
        });
    }

    private void updateTableThuoc() {
        vatPhamGDTable.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();

                    DefaultTableModel model = (DefaultTableModel) e.getSource();

                    // Xử lý khi cột thay đổi là cột số lượng (cột thứ 4)
                    if (column == 4) {
                        handleSoLuong(row, model);
                    }

                    // Xử lý khi cột thay đổi là cột giá tiền (cột thứ 5)
                    if (column == 5) {
                        handleGiaTien(row, model);
                    }
                }
            }

            private void handleSoLuong(int row, DefaultTableModel model) {
                int soLuong = 0;
                boolean kt = false;

                // Lấy giá trị số lượng từ bảng
                Object soLuongObj = model.getValueAt(row, 4);
                if (soLuongObj != null) {
                    try {
                        soLuong = Integer.parseInt(soLuongObj.toString());
                        kt = true;
                    } catch (NumberFormatException ex) {
                        System.out.println("Lỗi khi chuyển đổi số lượng thành số nguyên: " + soLuongObj.toString());
                        kt = false;
                    }
                } else {
                    System.out.println("Số lượng bị bỏ trống.");
                    kt = false;
                }
     
            }

            private void handleGiaTien(int row, DefaultTableModel model) {
                double giaTien = 1;
                boolean kt = false;

                // Lấy giá trị giá tiền từ bảng
                Object giaTienObj = model.getValueAt(row, 5);
                if (giaTienObj != null) {
                    try {
                        giaTien = Double.parseDouble(giaTienObj.toString());
                        kt = true;
                    } catch (NumberFormatException ex) {
                        System.out.println("Lỗi khi chuyển đổi giá tiền thành số thực: " + giaTienObj.toString());
                        kt = false;
                    }
                } else {
                    System.out.println("Giá trị giá tiền là null.");
                    kt = false;
                }

                // Nếu giá tiền hợp lệ, kiểm tra và cập nhật thành tiền
                if (kt) {
                    updateThanhTien(row, model, giaTien);
                }
            }

            private void updateThanhTien(int row, DefaultTableModel model, double giaTri) {
                // Lấy giá trị số lượng từ bảng
                Object soLuongObj = model.getValueAt(row, 4);
                if (soLuongObj != null) {
                    int soLuong = Integer.parseInt(soLuongObj.toString());
                    double thanhTien = soLuong * giaTri;
                    model.setValueAt(String.valueOf(thanhTien), row, 6);
                }
            }
        });
    }

    public void updateData(Object updatedObject) {
        // Test - xóa đi khi làm
        if (updatedObject instanceof String) {
            System.out.println("Tab quản lý nhập xuất update data : " + updatedObject);
        }

        // Viết logic cập nhật UI với dữ liệu vừa được thay đổi 
    }

}
