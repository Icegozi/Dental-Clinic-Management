/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mynewteeth.frontend.dashboard_sub_class;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import mynewteeth.backend.controller.HoSoBenhNhanController;
import mynewteeth.backend.interfaces.InvalidMaBenhNhanException;
import mynewteeth.backend.model.BacSi;
import mynewteeth.backend.model.BenhNhan;
import mynewteeth.backend.model.HoSoBenhNhan;
import mynewteeth.backend.model.Thuoc;
import mynewteeth.backend.model.VatTu;
import mynewteeth.backend.util.InvalidMaBacSiException;
import mynewteeth.backend.util.InvalidMaHoSoBenhNhanException;
import mynewteeth.backend.util.InvalidMaThuocException;

/**
 *
 * @author Us
 */
public class TabHoSoBenhNhan {

    private JTextField maBATextField;
    private JTextField ngayKhamTextField;
    private JTextField trieuChungTextField;
    private JTextField chanDoanTextField;
    private JTextField tenBacSiTextField;
    private JTextField maBacSiTextField;
    private JTextField ghiChuTextField;
    private JTextField ngayTaiKhamTextField;
    private JTable thuocKeDonTable;
    private JTable benhAnTable;
    private JButton timKiemBAButton;
    private JButton capNhatBAButton;
    private JButton xoaBAButton;
    private JButton themBAButton;
    private JTextField maBenhNhanTextField;
    private JLabel tenBNLabel, gioiTinhBNLabel, ngaySinhBNLabel, dienThoaiBNLabel;
    
    private HoSoBenhNhanController controller;
    
    public TabHoSoBenhNhan(JTextField maBATextField, JTextField ngayKhamTextField, JTextField trieuChungTextField,
            JTextField chanDoanTextField, JTextField tenBacSiTextField, JTextField maBacSiTextField, JTextField ghiChuTextField,
            JTextField ngayTaiKhamTextField, JTable thuocKeDonTable, JTable benhAnTable, JButton timKiemBAButton,
            JButton capNhatBAButton, JButton xoaBAButton, JButton themBAButton, JTextField maBenhNhanTextField,
            JLabel tenBNLabel, JLabel gioiTinhBNLabel,  JLabel ngaySinhBNLabel,  JLabel dienThoaiBNLabel) {

        this.maBATextField = maBATextField;
        this.ngayKhamTextField = ngayKhamTextField;
        this.trieuChungTextField = trieuChungTextField;
        this.chanDoanTextField = chanDoanTextField;
        this.tenBacSiTextField = tenBacSiTextField;
        this.maBacSiTextField = maBacSiTextField;
        this.ghiChuTextField = ghiChuTextField;
        this.ngayTaiKhamTextField = ngayTaiKhamTextField;
        this.thuocKeDonTable = thuocKeDonTable;
        this.benhAnTable = benhAnTable;
        this.timKiemBAButton = timKiemBAButton;
        this.capNhatBAButton = capNhatBAButton;
        this.xoaBAButton = xoaBAButton;
        this.themBAButton = themBAButton;
        this.maBenhNhanTextField = maBenhNhanTextField;
        this.tenBNLabel = tenBNLabel;
        this.gioiTinhBNLabel = gioiTinhBNLabel;
        this.ngaySinhBNLabel = ngaySinhBNLabel;
        this.dienThoaiBNLabel = dienThoaiBNLabel;
        
        controller = new HoSoBenhNhanController();
        bindData();
        // handle action
        handAddingAction();
        handleTableBehavior();
        handleUpdatingAction();
        handSearchingAction();
        handRemovingAction();
        updateTableThuoc();
    }

    /// Lấy liệu đã được load từ Controller lên rồi đổ vào UI
    private void bindData() {
        List<HoSoBenhNhan> hsbn = controller.getDanhSachHoSoBenhNhan();
        DefaultTableModel model = (DefaultTableModel) benhAnTable.getModel();

        // Xóa dữ liệu cũ trong bảng (nếu có)
        model.setRowCount(0);

        // Định dạng ngày tháng
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Thêm từng bệnh nhân vào bảng
        for (HoSoBenhNhan hs : hsbn) {
            String ngayKhamFormatted = dateFormat.format(hs.getNgayKham()); // Định dạng ngày khám

            Object[] rowData = {
                hs.getMaHoSoBenhNhan(),
                hs.getBenhNhan().getTenBenhNhan(),
                ngayKhamFormatted, // Sử dụng ngày đã định dạng
                hs.getBacSi().getHoTen()
            };
            model.addRow(rowData);
        }

        DefaultTableModel model1 = (DefaultTableModel) thuocKeDonTable.getModel();
        model1.setRowCount(10); // Đặt số lượng hàng mặc định trong bảng thuốc
    }

    //biến lấy mã bênh án hiện tại khi chọn vào table
    private String selectBenhAn;

    //hiển thị thông tin vào các text khi chon 1 dòng trong table
    private void handleTableBehavior() {
        benhAnTable.getSelectionModel().addListSelectionListener(e -> {
            //test_top
            if (e.getValueIsAdjusting()) {
                return;
            }
            int selectedIndex = benhAnTable.getSelectedRow();
            if (selectedIndex != -1) {
                for (HoSoBenhNhan f : controller.getDanhSachHoSoBenhNhan()) {
                    if (f.getMaHoSoBenhNhan().equals(benhAnTable.getValueAt(selectedIndex, 0).toString())) {
                        maBATextField.setText(f.getMaHoSoBenhNhan());
                        trieuChungTextField.setText(f.getTrieuChung());
                        chanDoanTextField.setText(f.getChuanDoanBanDau());
                        //tenBacSiTextField.setText(f.getBacSi().getHoTen());
                        maBacSiTextField.setText(f.getBacSi().getMaBacSi());
                        ghiChuTextField.setText(f.getGhiChuBacSi());
                        maBenhNhanTextField.setText(f.getBenhNhan().getMaBenhNhan());
                        selectBenhAn = f.getMaHoSoBenhNhan();
                        //tenBNLabel.setText(f.getBenhNhan().getTenBenhNhan());
                        gioiTinhBNLabel.setText(f.getBenhNhan().getGioiTinh());
                        dienThoaiBNLabel.setText(f.getBenhNhan().getSoDienThoai());
                        tenBacSiTextField.setText(controller.findTenBacSiByMa(f.getBacSi().getMaBacSi()));
                        tenBNLabel.setText(controller.findTenBenhNhanByMaBenhNhan(f.getBenhNhan().getMaBenhNhan()));

                        // Format the date of birth
                        Date ngaySinh = f.getBenhNhan().getNgaySinh();
                        Date ngayTaiKham = f.getNgayTaiKham();
                        Date ngayKham = f.getNgayKham();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String fomattedNgayTaiKham = dateFormat.format(ngayTaiKham);
                        String formattedNgaySinh = dateFormat.format(ngaySinh);
                        String formattedNgayKham = dateFormat.format(ngayKham);
                        ngaySinhBNLabel.setText(formattedNgaySinh);
                        ngayTaiKhamTextField.setText(fomattedNgayTaiKham);
                        ngayKhamTextField.setText(formattedNgayKham);
                        //controller.loadFromFile_thuoc(controller.file_thuoc);
                        
                        
                        
                        //hiển thi thuốc vào bảng thuốc
                        DefaultTableModel model1 = (DefaultTableModel) thuocKeDonTable.getModel();
                        model1.setRowCount(0);
                        for (Thuoc xx : controller.getThuocController()) {
                            if (xx.getMaBenhAn().equals(f.getMaHoSoBenhNhan())) {
                                for (VatTu yy : controller.getVaTuConTroller()) {
                                    if (xx.getMaVatTu().equals(yy.getMaVatTu())) {
                                        Object[] rowData = {
                                            yy.getMaVatTu(),
                                            yy.getTenVatTu(),
                                            yy.getLoai(),
                                            xx.getSoLuong(),
                                            xx.getGiaTien()
                                        };
                                        model1.addRow(rowData);
                                    }
                                }
                            }
                        }

                        // Thêm 5 dòng trống vào bảng thuốc
                        for (int i = 0; i < 10; i++) {
                            model1.addRow(new Object[]{null, null, null, null, null});
                        }
                    }
                }
            }

        });
    }

    // Viết các hàm xử lý dữ liệu và xử lý sự kiện 
    // Hàm cập nhật dữ liệu từ các Tab khác - KHÔNG ĐƯỢC XÓA 
    public void updateData(Object updatedObject) {
        if (updatedObject instanceof String) {

        } else if (updatedObject instanceof BenhNhan) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String OldTenBenhNhan = tenBNLabel.getText();
            tenBNLabel.setText(((BenhNhan) updatedObject).getTenBenhNhan());
            dienThoaiBNLabel.setText(((BenhNhan) updatedObject).getSoDienThoai());
            ngaySinhBNLabel.setText(dateFormat.format(((BenhNhan) updatedObject).getNgaySinh()));
            gioiTinhBNLabel.setText(((BenhNhan) updatedObject).getGioiTinh());
            BenhNhan benhNhan = (BenhNhan) updatedObject;        

            // Lấy thông tin cần cập nhật từ BenhNhan
            String tenBenhNhan = benhNhan.getTenBenhNhan();

            // Cập nhật thông tin vào BenhAnTable
            DefaultTableModel model = (DefaultTableModel) benhAnTable.getModel();
            int rowCount = model.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                // Giả sử cột 0 là cột chứa tên bệnh nhân (tùy vào cấu trúc của bảng)
                if (model.getValueAt(i, 1).equals(OldTenBenhNhan)) {
                    model.setValueAt(tenBenhNhan, i, 1); 
                    break;
                }
            }
        } else if (updatedObject instanceof BacSi) {
            // Update logic for BacSi
        } else {
            // Update logic for VatTu
        }
    }

    //Tìm kiếm hồ sơ bệnh nhân theo mã hồ sơ bệnh nhân
    private void handSearchingAction() {
        timKiemBAButton.addActionListener(e -> {
            String maHoSoBenhNhan = maBATextField.getText().trim();
            HoSoBenhNhan hoSoBenhNhan = controller.findHoSoBenhNhanByMa(maHoSoBenhNhan);

            if (hoSoBenhNhan != null) {
                // Đổ thông tin hồ sơ bệnh nhân vào UI
                maBATextField.setText(hoSoBenhNhan.getMaHoSoBenhNhan());
                trieuChungTextField.setText(hoSoBenhNhan.getTrieuChung());
                chanDoanTextField.setText(hoSoBenhNhan.getChuanDoanBanDau());
                tenBacSiTextField.setText(hoSoBenhNhan.getBacSi().getHoTen());
                maBacSiTextField.setText(hoSoBenhNhan.getBacSi().getMaBacSi());
                ghiChuTextField.setText(hoSoBenhNhan.getGhiChuBacSi());
                maBenhNhanTextField.setText(hoSoBenhNhan.getBenhNhan().getMaBenhNhan());
                tenBNLabel.setText(hoSoBenhNhan.getBenhNhan().getTenBenhNhan());
                gioiTinhBNLabel.setText(hoSoBenhNhan.getBenhNhan().getGioiTinh());
                dienThoaiBNLabel.setText(hoSoBenhNhan.getBenhNhan().getSoDienThoai());

                // Chọn dòng có mã hồ sơ bệnh nhân trong bảng
                DefaultTableModel model = (DefaultTableModel) benhAnTable.getModel();
                for (int i = 0; i < model.getRowCount(); i++) {
                    if (model.getValueAt(i, 0).equals(maHoSoBenhNhan)) {
                        benhAnTable.setRowSelectionInterval(i, i);
                        break;
                    }
                }
                DefaultTableModel model1 = (DefaultTableModel) thuocKeDonTable.getModel();
                model1.setRowCount(0);
                for (Thuoc xx : controller.getThuocController()) {
                    if (xx.getMaBenhAn().equals(maHoSoBenhNhan)) {
                        for (VatTu yy : controller.getVaTuConTroller()) {
                            if (xx.getMaVatTu().equals(yy.getMaVatTu())) {
                                Object[] rowData = {
                                    yy.getMaVatTu(),
                                    yy.getTenVatTu(),
                                    yy.getLoai(),
                                    xx.getSoLuong(),
                                    xx.getGiaTien()
                                };
                                model1.addRow(rowData);
                            }
                        }
                    }
                }

                // Thêm 5 dòng trống vào bảng thuốc
                for (int i = 0; i < 10; i++) {
                    model1.addRow(new Object[]{null, null, null, null, null});
                }
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy hồ sơ bệnh nhân với mã: " + maHoSoBenhNhan);
            }
        });
    }

    //Xóa các giá trị trong giao diện
    private void resetTextField() {
        maBATextField.setText("");
        trieuChungTextField.setText("");
        chanDoanTextField.setText("");
        maBacSiTextField.setText("");
        ghiChuTextField.setText("");
        maBenhNhanTextField.setText("");
        gioiTinhBNLabel.setText("");
        dienThoaiBNLabel.setText("");
        tenBacSiTextField.setText("");
        tenBNLabel.setText("");
        ngaySinhBNLabel.setText("");
        ngayTaiKhamTextField.setText("");
        ngayKhamTextField.setText("");

        benhAnTable.clearSelection();

        DefaultTableModel model1 = (DefaultTableModel) thuocKeDonTable.getModel();
        model1.setRowCount(0);
        // Thêm 5 dòng trống vào bảng thuốc
        for (int i = 0; i < 10; i++) {
            model1.addRow(new Object[]{null, null, null, null, null});
        }
    }

    //lấy dữ liệu trong bảng và trả về danh sách vật tư
    private void getThuocJtable(List<VatTu> vt) throws InvalidMaThuocException {
        DefaultTableModel model = (DefaultTableModel) thuocKeDonTable.getModel();
        int rowCount = model.getRowCount();

        // Duyệt qua tất cả các hàng của bảng thuocKeDonTable
        for (int i = 0; i < rowCount; i++) {
            // Lấy mã vật tư từ cột đầu tiên (giả sử mã vật tư nằm ở cột đầu tiên)
            String maVatTu = (String) model.getValueAt(i, 0);

            if (maVatTu != null && !maVatTu.trim().isEmpty()) {
                // Kiểm tra giá trị của cột số lượng và giá tiền
                Object soLuongObj = model.getValueAt(i, 3);
                Object giaTienObj = model.getValueAt(i, 4);

                if (soLuongObj == null || soLuongObj.toString().trim().isEmpty()) {
                    throw new InvalidMaThuocException("Số lượng chưa được nhập ở hàng " + (i + 1));
                }

                if (giaTienObj == null || giaTienObj.toString().trim().isEmpty()) {
                    throw new InvalidMaThuocException("Giá tiền chưa được nhập ở hàng " + (i + 1));
                }

                int soLuong;
                double giaTien;

                try {
                    soLuong = Integer.parseInt(soLuongObj.toString().trim());
                    if (soLuong < 0) {
                        throw new InvalidMaThuocException("Số lượng phải là số nguyên không âm ở hàng " + (i + 1));
                    }
                } catch (NumberFormatException e) {
                    throw new InvalidMaThuocException("Số lượng có định dạng không hợp lệ ở hàng " + (i + 1));
                }

                try {
                    giaTien = Double.parseDouble(giaTienObj.toString().trim());
                    if (giaTien < 0) {
                        throw new InvalidMaThuocException("Giá tiền phải là số không âm ở hàng " + (i + 1));
                    }
                } catch (NumberFormatException e) {
                    throw new InvalidMaThuocException("Giá tiền có định dạng không hợp lệ ở hàng " + (i + 1));
                }

                // Kiểm tra nếu mã vật tư tồn tại trong getVaTuConTroller()
                if (containsVatTuByMa(controller.getVaTuConTroller(), maVatTu)) {
                    // Tìm đối tượng VatTu theo mã vật tư
                    VatTu vatTu = controller.findVatTuByMa(maVatTu);

                    // Kiểm tra nếu VatTu không null (tìm thấy vật tư)
                    if (vatTu != null) {
                        // Kiểm tra xem mã vật tư đã có trong danh sách chưa
                        if (containsVatTuByMa(vt, maVatTu)) {
                            throw new InvalidMaThuocException("Thuốc bị trùng nhau, vui lòng sửa lại: " + maVatTu);
                        } else {
                            vatTu.setGiaNhap(giaTien);
                            vatTu.setSoLuong(soLuong);
                            vt.add(vatTu);
                        }
                    }
                } else {
                    throw new InvalidMaThuocException("Không có thuốc trong kho với mã: " + maVatTu);
                }
            }
        }
    }

    //lấy dữ liệu trong bảng và tra về danh sách thuốc
    private void layDuLieuTuBangThuoc(String maHoSoBenhNhan) {
        DefaultTableModel model = (DefaultTableModel) thuocKeDonTable.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {
            String maVatTu = (String) model.getValueAt(i, 0);
            String tenVatTu = (String) model.getValueAt(i, 1);
            String loai = (String) model.getValueAt(i, 2);
            int soLuong = Integer.parseInt(model.getValueAt(i, 3).toString());
            double giaTien = Double.parseDouble(model.getValueAt(i, 4).toString());

            // Kiểm tra nếu hàng không rỗng thì thêm vào danh sách
            if (maVatTu != null && !maVatTu.isEmpty()) {
                Thuoc thuoc = new Thuoc(maHoSoBenhNhan, maVatTu, soLuong, giaTien); // Tạo đối tượng Thuốc
                controller.getThuocController().add(thuoc); // Thêm vào danh sách
            }
        }
    }

    //Kiểm tra mã vật tư có tồn tại trong danh sách không
    private boolean containsVatTuByMa(List<VatTu> vatTus, String maVatTu) {
        for (VatTu vatTu : vatTus) {
            if (vatTu.getMaVatTu().equals(maVatTu)) {
                return true;
            }
        }
        return false;
    }

    //thêm hồ sơ bệnh nhân 
    private void handAddingAction() {
        themBAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String maHoSoBenhNhan = maBATextField.getText().trim();
                String ngayKham = ngayKhamTextField.getText().trim();
                String trieuChung = trieuChungTextField.getText().trim();
                String chanDoan = chanDoanTextField.getText().trim();
                String maBacSi = maBacSiTextField.getText().trim();
                String ghiChu = ghiChuTextField.getText().trim();
                String ngayTaiKham = ngayTaiKhamTextField.getText().trim();
                String maBenhNhan = maBenhNhanTextField.getText().trim();
                BacSi newBS = controller.findBacSiByMa(maBacSi);
                BenhNhan newBN = controller.findBenhNhanByMa(maBenhNhan);
                List<VatTu> newVT = new ArrayList<>();

                if (maHoSoBenhNhan.isEmpty() || ngayKham.isEmpty() || trieuChung.isEmpty() || chanDoan.isEmpty() || maBacSi.isEmpty() || ngayTaiKham.isEmpty() || maBenhNhan.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!");
                    return; // Dừng lại nếu có thông tin bị thiếu
                }

                if (!isValidDateFormat(ngayKham) || !isValidDateFormat(ngayTaiKham)) {
                    JOptionPane.showMessageDialog(null, "Định dạng ngày tháng phải là yyyy-MM-dd!");
                    return;
                }
                // Tìm tên Bệnh Nhân dựa vào mã Bệnh Nhân
                String tenBenhNhan = controller.findTenBenhNhanByMaBenhNhan(maBenhNhan);

                // Tìm Tên Bác Sỹ dựa vào mã Bác Sỹ
                String tenBacSi = controller.findTenBacSiByMa(maBacSi);

                try {
                    // Lấy thuốc từ bảng thuocKeDonTable
                    getThuocJtable(newVT);
                    controller.themThuoc(maHoSoBenhNhan, newVT);
                    controller.insertToThuocFile(controller.getSubThuoc());
                    boolean valid1 = controller.themHoSoBenhNhanBenhNhan(maHoSoBenhNhan, ngayKham, trieuChung, chanDoan, maBacSi, maBacSi, ghiChu, ngayTaiKham, maBenhNhan, newVT);
                    if (valid1) {
                        // Thêm thông tin vào bảng benhAnTable và file
                        DefaultTableModel model = (DefaultTableModel) benhAnTable.getModel();
                        DefaultTableModel model1 = (DefaultTableModel) thuocKeDonTable.getModel();
                        model.addRow(new Object[]{maHoSoBenhNhan, tenBenhNhan, ngayKham, tenBacSi});
                        controller.saveToHoSoBenhNhanFile(maHoSoBenhNhan, ngayKham, trieuChung, chanDoan, tenBacSi, maBacSi, ghiChu, ngayTaiKham, maBenhNhan);
                        JOptionPane.showMessageDialog(null, "Thêm bệnh án thành công!");
                        resetTextField();
                        model1.setRowCount(0); // Xóa tất cả các hàng
                        for (int i = 0; i < 10; i++) { // Thêm 10 hàng trống
                            model1.addRow(new Object[]{"", "", "", "", ""});
                        }
                    }
                } catch (InvalidMaThuocException d) {
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

    //Sự kiện cập nhật hồ sơ bệnh nhân
    private void updateBenhAnTable(String maHoSoBenhNhan, String tenBenhNhan, String ngayKham, String tenBacSi, String maBenhNhan) {
        DefaultTableModel model = (DefaultTableModel) benhAnTable.getModel();
        // Tìm dòng cần cập nhật
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).equals(maHoSoBenhNhan)) {
                // Cập nhật thông tin cho dòng tương ứng trong bảng
                model.setValueAt(tenBenhNhan, i, 1); // Tên bệnh nhân
                model.setValueAt(ngayKham, i, 2); // Ngày khám
                model.setValueAt(tenBacSi, i, 3); // Tên bác sĩ
                break;
            }
        }

    }

    //sửa hồ sơ bệnh nhân theo mã hồ sơ 
    private void handleUpdatingAction() {
        capNhatBAButton.addActionListener(e -> {
            //System.out.println("hello world");
            String maHoSoBenhNhan = maBATextField.getText().trim();
            String ngayKham = ngayKhamTextField.getText().trim();
            String trieuChung = trieuChungTextField.getText().trim();
            String chanDoan = chanDoanTextField.getText().trim();
            String maBacSi = maBacSiTextField.getText().trim();
            String ghiChu = ghiChuTextField.getText().trim();
            String ngayTaiKham = ngayTaiKhamTextField.getText().trim();
            String maBenhNhan = maBenhNhanTextField.getText().trim();
            List<VatTu> newListVT = new ArrayList<>();

            if (maHoSoBenhNhan.isEmpty() || ngayKham.isEmpty() || trieuChung.isEmpty() || chanDoan.isEmpty() || maBacSi.isEmpty() || ngayTaiKham.isEmpty() || maBenhNhan.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!");
                return;
            }

            if (!isValidDateFormat(ngayKham) || !isValidDateFormat(ngayTaiKham)) {
                JOptionPane.showMessageDialog(null, "Định dạng ngày tháng phải là yyyy-MM-dd!");
                return;
            }
            try {

                getThuocJtable(newListVT);
                controller._xoaThuoc(maHoSoBenhNhan);
                controller.saveToThuocFile(controller.getThuocController());
                controller.suaThuoc(maHoSoBenhNhan, newListVT);
                controller.maBenhNhanIsExist(maBenhNhan);
                controller.maBacSiIsExist(maBacSi);
                controller.maBenhNhan_BacSiExist(maHoSoBenhNhan, maBacSi, maBenhNhan);
                controller.suaHoSoBenhNhan(maHoSoBenhNhan, ngayKham, trieuChung, chanDoan, maBacSi, ghiChu, ngayTaiKham, maBenhNhan);
                controller.insertToThuocFile(controller.getSubThuoc());

                bindData();
//                tenBacSiTextField.setText(controller.findTenBacSiByMa(maBacSi));
//                tenBNLabel.setText(controller.findTenBenhNhanByMaBenhNhan(maBenhNhan));
//                gioiTinhBNLabel.setText("");
//                ngaySinhBNLabel.setText("");
//                dienThoaiBNLabel.setText("");
                updateBenhAnTable(maHoSoBenhNhan, controller.findTenBenhNhanByMaBenhNhan(maBenhNhan), ngayKham, controller.findTenBacSiByMa(maBacSi), maBenhNhan);
                controller.getThuocController().clear();
                controller.loadFromFile_thuoc(controller.file_thuoc);
                resetTextField();
            } catch (InvalidMaHoSoBenhNhanException | InvalidMaBacSiException | InvalidMaBenhNhanException | InvalidMaThuocException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }

        });
    }

    //Xóa hồ sơ bệnh nhân đã chọn trong bảng
    private void handRemovingAction() {
        xoaBAButton.addActionListener(e -> {
            int selectedIndex = benhAnTable.getSelectedRow();
            if (selectedIndex != -1) {
                String maHoSoBenhNhan = benhAnTable.getValueAt(selectedIndex, 0).toString(); // Lấy mã hồ sơ bệnh nhân từ dòng được chọn

                // Xác nhận việc xóa
                int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa hồ sơ bệnh nhân với mã: " + maHoSoBenhNhan + "?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    // Xóa hồ sơ bệnh nhân trong danh sách trong controller
                    boolean found = controller.removeHoSoBenhNhanByMa(maHoSoBenhNhan);
                    controller._xoaThuoc(maHoSoBenhNhan);
                    if (found) {
                        // Cập nhật file lưu trữ
                        controller.SaveToFile();
                        controller.saveToThuocFile(controller.getThuocController());

                        // Cập nhật bảng
                        DefaultTableModel model = (DefaultTableModel) benhAnTable.getModel();
                        model.removeRow(selectedIndex);

                        DefaultTableModel model1 = (DefaultTableModel) thuocKeDonTable.getModel();
                        model1.setRowCount(0);

                        JOptionPane.showMessageDialog(null, "Xóa bệnh án thành công!");

                        //cập nhật textfield
                        resetTextField();
                    } else {
                        JOptionPane.showMessageDialog(null, "Không tìm thấy hồ sơ bệnh nhân với mã: " + maHoSoBenhNhan);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một hồ sơ bệnh nhân để xóa.");
            }
        });
    }

    //cập nhật giá trị bảng thuốc khi thay đổi mã
    private void updateTableThuoc() {
        thuocKeDonTable.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();

                    // Giả sử cột mã thuốc là cột thứ 0, tên thuốc là cột thứ 1, và đơn vị là cột thứ 2
                    if (column == 0) { // Kiểm tra nếu cột thay đổi là cột mã thuốc
                        DefaultTableModel model = (DefaultTableModel) e.getSource();
                        String maThuoc = (String) model.getValueAt(row, column);

                        // Tìm vật tư theo mã
                        VatTu vatTu = controller.findVatTuByMa(maThuoc);
                        if (vatTu != null) {
                            // Cập nhật các cột tên thuốc và đơn vị
                            model.setValueAt(vatTu.getTenVatTu(), row, 1);
                            model.setValueAt(vatTu.getLoai(), row, 2);
                            System.out.println("Cập nhật dòng " + row + ": mã thuốc " + maThuoc + " -> tên thuốc " + vatTu.getTenVatTu() + ", đơn vị " + vatTu.getLoai());
                        } else {
                            System.out.println("Không tìm thấy mã thuốc: " + maThuoc);
                        }
                    }
                }
            }
        });

    }
}
