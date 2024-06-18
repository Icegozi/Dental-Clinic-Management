/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mynewteeth.frontend.dashboard_sub_class;

import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import mynewteeth.backend.controller.HoaDonController;
import mynewteeth.backend.interfaces.ICustomDialogAction;
import mynewteeth.backend.model.BenhNhan;
import mynewteeth.backend.model.DichVu;
import mynewteeth.backend.model.DichVuHoaDon;
import mynewteeth.backend.model.HoaDon;
import mynewteeth.backend.model.VatTu;
import mynewteeth.backend.model.VatTuHoaDon;
import mynewteeth.frontend.DialogProvider;

/**
 *
 * @author Us
 */
public class TabHoaDon {

    private final JTable hoaDonTable;
    private final JTextField soHDTextField;
    private final JTextField ngayKhamHDTextField;
    private final JComboBox tenBenhNhanComboBox;
    private final JComboBox maBenhNhanComboBox;
    private final JComboBox<String> dichVuComboBox;
    private final JTable dichVuTable;
    private final JComboBox<String> thuocHDComboBox;
    private final JTable thuocHDTable;
    private final JLabel thanhTienLabel;
    private final JButton themHDButton;
    private final JButton capNhatHDButton;
    private final JButton xoaHDButton;
    private final JButton timKiemHDButton;
    private final JButton boChonButton;
    private final JButton donDepButton;

    private HoaDonController hoaDonController;
    private DefaultTableModel hoaDonTableModel;
    private DefaultTableModel dichVuTableModel;
    private DefaultTableModel vatTuTableModel;
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private HoaDon selectedHoaDon;

    private DichVuHoaDon selectedService;
    private DichVuHoaDon updatedService;
    private final List<DichVuHoaDon> listUpdatedDichVu = new ArrayList<>();
    // -------------------------------------------------------------------
    private VatTuHoaDon selectedMedicine;
    private VatTuHoaDon updatedMedicine;
    private final List<VatTuHoaDon> listUpdatedVatTu = new ArrayList<>();

    public TabHoaDon(JTable hoaDonTable, JTextField soHDTextField, JTextField ngayKhamHDTextField,
            JComboBox tenBenhNhanComboBox, JComboBox maBenhNhanComboBox, JComboBox<String> dichVuComboBox,
            JTable dichVuTable, JComboBox<String> thuocHDComboBox, JTable thuocHDTable,
            JLabel thanhTienLabel, JButton themHDButton, JButton capNhatHDButton, JButton xoaHDButton,
            JButton timKiemHDButton, JButton boChonButton, JButton donDepButton) {

        this.hoaDonTable = hoaDonTable;
        this.soHDTextField = soHDTextField;
        this.ngayKhamHDTextField = ngayKhamHDTextField;
        this.tenBenhNhanComboBox = tenBenhNhanComboBox;
        this.maBenhNhanComboBox = maBenhNhanComboBox;
        this.dichVuComboBox = dichVuComboBox;
        this.dichVuTable = dichVuTable;
        this.thuocHDComboBox = thuocHDComboBox;
        this.thuocHDTable = thuocHDTable;
        this.thanhTienLabel = thanhTienLabel;
        this.themHDButton = themHDButton;
        this.capNhatHDButton = capNhatHDButton;
        this.xoaHDButton = xoaHDButton;
        this.timKiemHDButton = timKiemHDButton;
        this.boChonButton = boChonButton;
        this.donDepButton = donDepButton;
        init();
        loadAndBindData();
        updateHoaDon();
        addHoaDon();
    }

    private void init() {
        hoaDonController = new HoaDonController();
        hoaDonTableModel = (DefaultTableModel) hoaDonTable.getModel();
        dichVuTableModel = (DefaultTableModel) dichVuTable.getModel();
        vatTuTableModel = (DefaultTableModel) thuocHDTable.getModel();
    }

    private void loadAndBindData() {
        (new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                hoaDonController.loadData();
                return null;
            }

            @Override
            protected void done() {
                bindDataOntabDisplayed();
                setUpComponentBehavior();
            }
        }).execute();
    }

    private void bindDataOntabDisplayed() {
        // bind data to HoaDon table
        for (HoaDon bill : hoaDonController.getListHoaDon()) {
            hoaDonTableModel.addRow(new Object[]{bill.getSoHD(), formatter.format(bill.getNgayLap()),
                bill.getBenhNhan().getTenBenhNhan(), bill.getTongTien()});
        }
        // bind patient's info in ComboBox
        for (BenhNhan benhNhan : hoaDonController.getListBenhNhan()) {
            maBenhNhanComboBox.addItem(benhNhan.getMaBenhNhan());
            tenBenhNhanComboBox.addItem(benhNhan.getTenBenhNhan());
        }
        for (DichVu service : hoaDonController.getListDichVu()) {
            dichVuComboBox.addItem(service.getMaDichVu() + " - " + service.getTenDichVu());
        }
        for (VatTu medicine : hoaDonController.getListVatTu()) {
            thuocHDComboBox.addItem(medicine.getMaVatTu() + " - " + medicine.getTenVatTu());
        }
        dichVuComboBox.setSelectedIndex(-1);
        thuocHDComboBox.setSelectedIndex(-1);
    }

    private void displayDataInDetails() {
        // display basic info into TextFielt and ComboBox
        soHDTextField.setText(selectedHoaDon.getSoHD());
        ngayKhamHDTextField.setText(formatter.format(selectedHoaDon.getNgayLap()));
        maBenhNhanComboBox.setSelectedItem(selectedHoaDon.getBenhNhan().getMaBenhNhan());
        tenBenhNhanComboBox.setSelectedItem(selectedHoaDon.getBenhNhan().getTenBenhNhan());
        thanhTienLabel.setText(BigDecimal.valueOf(selectedHoaDon.getTongTien()).toPlainString());
        // display info in table
        dichVuTableModel.setRowCount(0);
        for (DichVuHoaDon service : selectedHoaDon.getListDichVu()) {
            int soLuong = service.getSoLuong();
            double giaTien = service.getGiaBanThuc();
            dichVuTableModel.addRow(new Object[]{service.getDichVu().getTenDichVu(), soLuong, giaTien, giaTien * soLuong});
        }
        vatTuTableModel.setRowCount(0);
        for (VatTuHoaDon medicine : selectedHoaDon.getListVatTu()) {
            int amount = medicine.getSoLuong();
            double cost = medicine.getDonGia();
            vatTuTableModel.addRow(new Object[]{medicine.getVatTu().getMaVatTu(), medicine.getVatTu().getTenVatTu(),
                medicine.getVatTu().getLoai(), amount, cost, amount * cost});
        }
    }

    private void clear() {
        soHDTextField.setText("");
        ngayKhamHDTextField.setText("");
        maBenhNhanComboBox.setSelectedIndex(-1);
        tenBenhNhanComboBox.setSelectedIndex(-1);
        dichVuComboBox.setSelectedIndex(-1);
        thuocHDComboBox.setSelectedIndex(-1);
        dichVuTableModel.setRowCount(0);
        vatTuTableModel.setRowCount(0);
        thanhTienLabel.setText("");
    }

    private void setUpComponentBehavior() {
        hoaDonTable.getSelectionModel().addListSelectionListener((e) -> {
            if (e.getValueIsAdjusting()) {
                return;
            }
            if (hoaDonTable.getSelectedRow() < 0) {
                return;
            }
            selectedHoaDon = hoaDonController.getListHoaDon().get(hoaDonTable.getSelectedRow());
            listUpdatedDichVu.clear();
            listUpdatedDichVu.addAll(selectedHoaDon.getListDichVu());
            // lấy danh sách vật tư gốc và cập nhật theo bảng vật tư
            listUpdatedVatTu.clear();
            listUpdatedVatTu.addAll(selectedHoaDon.getListVatTu());
            displayDataInDetails();
        });
        tenBenhNhanComboBox.addItemListener((e) -> {
            if (e.getStateChange() == ItemEvent.DESELECTED) {
                return;
            }
            maBenhNhanComboBox.setSelectedIndex(tenBenhNhanComboBox.getSelectedIndex());
        });
        maBenhNhanComboBox.addItemListener((e) -> {
            if (e.getStateChange() == ItemEvent.DESELECTED) {
                return;
            }
            tenBenhNhanComboBox.setSelectedIndex(maBenhNhanComboBox.getSelectedIndex());
        });
        donDepButton.addActionListener((e) -> {
            clear();
        });
        boChonButton.addActionListener((e) -> {
            clear();
            clearHoaDonChanges();
        });
        setUpDetailTablesBehavior();
    }

    private void calculateBillMoney() {
        double totalMoney = 0;
        for (DichVuHoaDon service : listUpdatedDichVu) {
            if (service == null) {
                continue;
            }
            totalMoney += (service.getSoLuong() * service.getGiaBanThuc());
        }
        for (VatTuHoaDon material : listUpdatedVatTu) {
            if (material == null) {
                continue;
            }
            totalMoney += (material.getDonGia() * material.getSoLuong());
        }
        thanhTienLabel.setText(BigDecimal.valueOf(totalMoney).toPlainString());
    }

    private void setUpComboBoxTables() {
        // set up them cho table dịch vụ
        dichVuComboBox.addItemListener((e) -> {
            if (e.getStateChange() == ItemEvent.DESELECTED) {
                return;
            }
            String tenDichVu = ((String) dichVuComboBox.getSelectedItem()).split("-")[1].trim();
            DichVu service = findDichVuByName(tenDichVu);
            if (service == null) {
                System.out.println("Check lai dich vu nay , dang bi loi khong ton tai !");
                return;
            }
            DichVuHoaDon newService = new DichVuHoaDon(service, 1, service.getGiaTien());
            double totalMoney = newService.getSoLuong() * newService.getGiaBanThuc();
            dichVuTableModel.addRow(new Object[]{newService.getDichVu().getTenDichVu(), newService.getSoLuong(),
                newService.getDichVu().getGiaTien(), totalMoney});
            listUpdatedDichVu.add(newService);
            dichVuTable.getSelectionModel().clearSelection();
            calculateBillMoney();
        });
        // set up them cho table vat tu
        thuocHDComboBox.addItemListener((e) -> {
            if (e.getStateChange() == ItemEvent.DESELECTED) {
                return;
            }
            String medicineId = ((String) thuocHDComboBox.getSelectedItem()).split("-")[0].trim();
            System.out.println(medicineId);
            VatTu medicine = findMedicineByMa(medicineId);
            if (medicine == null) {
                System.out.println("Check lai vat tu nay , dang bi loi khong ton tai !");
                return;
            }
            VatTuHoaDon newMedicine = new VatTuHoaDon(medicine, 1, medicine.getGiaNhap());
            double totalMoney = newMedicine.getSoLuong() * newMedicine.getDonGia();
            vatTuTableModel.addRow(new Object[]{newMedicine.getVatTu().getMaVatTu(), newMedicine.getVatTu().getTenVatTu(),
                newMedicine.getVatTu().getLoai(), newMedicine.getSoLuong(), newMedicine.getDonGia(), totalMoney});
            listUpdatedVatTu.add(newMedicine);
            thuocHDTable.getSelectionModel().clearSelection();
            calculateBillMoney();
        });
    }

    // ---------------------------------------------------------Xử lý dịch vụ-----------------------------------------------------------------
    private DichVu findDichVuByName(String serviceName) {
        for (DichVu service : hoaDonController.getListDichVu()) {
            if (service.getTenDichVu().equalsIgnoreCase(serviceName)) {
                return service;
            }
        }
        return null;
    }

    private VatTu findMedicineByMa(String id) {
        for (VatTu m : hoaDonController.getListVatTu()) {
            if (m.getMaVatTu().equalsIgnoreCase(id)) {
                return m;
            }
        }
        return null;
    }

    private void updateDichVuTableOnDeleteRow(int row) {
        dichVuTable.setValueAt(null, row, 0);
        dichVuTable.setValueAt(null, row, 1);
        dichVuTable.setValueAt(null, row, 2);
        dichVuTable.setValueAt(null, row, 3);
    }

    private void deleteService(int row) {
        isUpdated = true;
        updateDichVuTableOnDeleteRow(row);
        dichVuTable.getSelectionModel().clearSelection();
        listUpdatedDichVu.set(row, null);
        calculateBillMoney();
    }

    private void setServiceEmpty(int row) {
        isUpdated = true;
        dichVuTable.setValueAt(null, row, 0);
        updatedService = selectedService.cloneDichVuHoaDon();
        updatedService.getDichVu().setTenDichVu("");
        listUpdatedDichVu.set(row, updatedService);
        dichVuTable.getSelectionModel().clearSelection();
    }

    private boolean isUpdated = false;

    private void changeDichVuIfValid(String newServiceName, int row, int col) {
        DichVu newService = null;
        // xu ly truong hop khong tim thay dich vu nguowi dung vua nhap
        if ((newService = findDichVuByName(newServiceName)) == null) {
            DialogProvider.showMessageDialog("Dịch vụ này không tồn tại !", "Thông báo !");
            dichVuTable.setValueAt(selectedService.getDichVu().getTenDichVu(), row, col);
            //isUpdated = true; 
        } else {
            // xu ly truong hop tim thay dich vu nguoi dung vua nhap
            updatedService = new DichVuHoaDon(newService, 1, newService.getGiaTien());
            isUpdated = true;
            dichVuTable.setValueAt(updatedService.getDichVu().getTenDichVu(), row, 0);
            dichVuTable.setValueAt(updatedService.getSoLuong(), row, 1);
            double giaTien = updatedService.getGiaBanThuc();
            dichVuTable.setValueAt(giaTien, row, 2);
            dichVuTable.setValueAt(giaTien * updatedService.getSoLuong(), row, 3);
            if (listUpdatedDichVu.size() - 1 >= row) { // truong hop sua di sua lai 1 dong nhieu lan
                listUpdatedDichVu.set(row, updatedService);
            } else {
                listUpdatedDichVu.add(updatedService);
            }
            dichVuTable.getSelectionModel().clearSelection();
            calculateBillMoney();
        }
    }

    private void changeAServiceAmount(int row, int col) {
        int serviceAmount = (int) dichVuTable.getValueAt(row, 1);
        if (serviceAmount < 0) {
            dichVuTable.setValueAt(selectedService.getSoLuong(), row, col);
            dichVuTable.getSelectionModel().clearSelection();
            DialogProvider.showMessageDialog("So luong khong duoc am !", "Thong bao !");
            return;
        } else if (serviceAmount == selectedService.getSoLuong()) {
            dichVuTable.getSelectionModel().clearSelection();
            return;
        }
        updatedService = selectedService.cloneDichVuHoaDon();
        updatedService.setSoLuong(serviceAmount);
        isUpdated = true;
        double totalMoney = updatedService.getSoLuong() * updatedService.getGiaBanThuc();
        dichVuTable.setValueAt(serviceAmount, row, col);
        dichVuTable.setValueAt(totalMoney, row, 3);
        listUpdatedDichVu.set(row, updatedService);
        dichVuTable.getSelectionModel().clearSelection();
        calculateBillMoney();
    }

    private void changeServiceCost(int row, int col) {
        double cost = (double) dichVuTable.getValueAt(row, col);
        if (cost < 0) {
            dichVuTable.setValueAt(selectedService.getDichVu().getGiaTien(), row, col);
            dichVuTable.getSelectionModel().clearSelection();
            DialogProvider.showMessageDialog("Gia tien khong duoc am !", "Thong bao !");
            return;
        } else if (cost == selectedService.getGiaBanThuc()) {
            dichVuTable.getSelectionModel().clearSelection();
            return;
        }
        updatedService = selectedService.cloneDichVuHoaDon();
        updatedService.setGiaBanThuc(cost);
        isUpdated = true;
        double totalMoney = updatedService.getSoLuong() * updatedService.getGiaBanThuc();
        dichVuTable.setValueAt(cost, row, col);
        dichVuTable.setValueAt(totalMoney, row, 3);
        listUpdatedDichVu.set(row, updatedService);
        dichVuTable.getSelectionModel().clearSelection();
        calculateBillMoney();
    }

    private void setUpDetailTablesBehavior() {
        setUpComboBoxTables();
        setUpDichVuTableBehavior();
        setUpVatTuTableBehavior();
    }

    private void setUpDichVuTableBehavior() {
        // bang Dich Vu
        // su kien thay doi gia tri tai 1 o trong bang
        dichVuTable.getSelectionModel().addListSelectionListener((e) -> { // hoan thanh ------------------------
            if (e.getValueIsAdjusting()) {
                return;
            }
            int selectedRow = dichVuTable.getSelectedRow();
            selectedService = (selectedRow >= 0) ? listUpdatedDichVu.get(selectedRow) : null;
            isUpdated = false;
            if (selectedService == null && selectedRow >= 0) {
                DialogProvider.showMessageDialog("Dịch vụ này đã bị xóa ! Vui lòng thao tác trên một dòng khác !", "Thông báo !");
                dichVuTable.getSelectionModel().clearSelection();
            }
        });
        dichVuTableModel.addTableModelListener((e) -> {
            if (isUpdated) {
                return;
            }
            final int row;
            int col;
            if (e.getType() == TableModelEvent.UPDATE) {
                row = e.getLastRow();
                col = e.getColumn();
                String newServiceName = (String) dichVuTable.getValueAt(row, 0);
                if (col == 0) { // xu ly nguoi dung sua ten dich vu
                    // truong hop user thay doi ten dich nhung khong nhap text
                    if (newServiceName.equals("")) {
                        DialogProvider.CustomDialog.getDialog("Hệ thống sẽ tự loại bỏ các dịch vụ không hợp lệ hoặc để trống ! "
                                + "Vẫn để trống ?",
                                "Cảnh báo !", new String[]{"Xóa DV", "Bỏ trống", "Hoàn tác"}, new ICustomDialogAction() {
                            @Override
                            public void onFirstChoosed() {
                                deleteService(row);
                                DialogProvider.CustomDialog.disposeDialog();
                            }

                            @Override
                            public void onSecondChoosed() {
                                setServiceEmpty(row);
                                DialogProvider.CustomDialog.disposeDialog();
                            }

                            @Override
                            public void onThirdChoosed() {
                                isUpdated = true;
                                dichVuTable.setValueAt(selectedService.getDichVu().getTenDichVu(), row, col);
                                dichVuTable.getSelectionModel().clearSelection();
                                DialogProvider.CustomDialog.disposeDialog();
                            }
                        }).setVisible(true);
                        return;
                    }
                    // truong hop user thay doi ten dich vu va co nhap text
                    if (!selectedService.getDichVu().getTenDichVu().equals(newServiceName)) {
                        changeDichVuIfValid(newServiceName, row, col);
                    }
                    return;
                } else if (col == 1) { // xu ly nguoi dung sua so luong dich vu
                    changeAServiceAmount(row, col);
                    return;
                }
                // xu ly nguoi dung sua gia tien col = 2
                changeServiceCost(row, col);
            }
        });
    }

    private void filterInvalidService() {
        for (int i = 0; i < listUpdatedDichVu.size(); i++) {
            if (listUpdatedDichVu.get(i) == null || listUpdatedDichVu.get(i).getDichVu().getTenDichVu().equals("") || listUpdatedDichVu.get(i).getSoLuong() == 0) {
                listUpdatedDichVu.remove(i);
                i--;
            }
        }
    }

    // -----------------------------------------------------Xử lý vật tư-----------------------------------------------------------------
    private boolean isVatTuTableUpdated = false;

    private void setUpVatTuTableBehavior() {
        thuocHDTable.getSelectionModel().addListSelectionListener((e) -> {
            if (e.getValueIsAdjusting()) {
                return;
            }
            int selectedRow = thuocHDTable.getSelectedRow();
            selectedMedicine = (selectedRow >= 0) ? listUpdatedVatTu.get(selectedRow) : null;
            isVatTuTableUpdated = false;
            if (selectedMedicine == null && selectedRow >= 0) {
                DialogProvider.showMessageDialog("Vật phẩm này đã bị xóa ! Vui lòng thao tác trên một dòng khác", "Thông báo !");
                thuocHDTable.getSelectionModel().clearSelection();
            }
        });
        vatTuTableModel.addTableModelListener((e) -> {
            if (isVatTuTableUpdated) {
                return;
            }
            final int row;
            int col;
            if (e.getType() == TableModelEvent.UPDATE) {
                row = e.getLastRow();
                col = e.getColumn();
                if (col == 0) {
                    String newMaVatTu = (String) thuocHDTable.getValueAt(row, col);
                    // trường hợp user xóa nhưng để trống
                    if (newMaVatTu.equals("")) {
                        handleUpdatedMaVatTuEmpty(row, col);
                        return;
                    }
                    // trường hợp xóa nhưng không để trống và mã khác 
                    if (selectedMedicine.getVatTu().getMaVatTu().equalsIgnoreCase(newMaVatTu)) { // trường hợp mã không thay đổi (cải tiến)
                        actOnUndoUpdatingMedicineWithMa(row, col);
                        return;
                    }
                    // trường hợp có thay đổi , không null và không trùng mã cũ
                    actOnNonNullMedicineMaChange(newMaVatTu, row, col);
                    return;
                }
                if (col == 3) { // change so luong !
                    changeVatTuAmount(row, col);
                    return;
                }
                changeVatTuCost(row, 4);
            }
        });
    }

    private void handleUpdatedMaVatTuEmpty(int row, int col) {
        DialogProvider.CustomDialog.getDialog("Hệ thống sẽ tự loại bỏ các vật phẩm không hợp lệ, để trống hoặc số lượng bằng 0 ! "
                + "Vẫn để trống ?", "Cảnh báo !", new String[]{"Xóa VP", "Để trống", "Hoàn tác"}, new ICustomDialogAction() {
            @Override
            public void onFirstChoosed() {
                actOnDeleteMedicineWithMa(row);
                DialogProvider.CustomDialog.disposeDialog();
            }

            @Override
            public void onSecondChoosed() {
                actOnEmptyMedicineWithMa(row);
                DialogProvider.CustomDialog.disposeDialog();
            }

            @Override
            public void onThirdChoosed() {
                actOnUndoUpdatingMedicineWithMa(row, col);
                DialogProvider.CustomDialog.disposeDialog();
            }
        }).setVisible(true);
    }

    private void updateTableVatTuOnDelete(int row) {
        thuocHDTable.setValueAt(null, row, 0);
        thuocHDTable.setValueAt(null, row, 1);
        thuocHDTable.setValueAt(null, row, 2);
        thuocHDTable.setValueAt(null, row, 3);
        thuocHDTable.setValueAt(null, row, 4);
        thuocHDTable.setValueAt(null, row, 5);
    }

    private void actOnDeleteMedicineWithMa(int row) {
        isVatTuTableUpdated = true;
        updateTableVatTuOnDelete(row);
        thuocHDTable.getSelectionModel().clearSelection();
        listUpdatedVatTu.set(row, null);
        calculateBillMoney();
    }

    // loi , xoa dong thu nhat di roi nhung no van bang 0 o bang !
    private void actOnEmptyMedicineWithMa(int row) {
        isVatTuTableUpdated = true;
        thuocHDTable.setValueAt(null, row, 0);
        updatedMedicine = selectedMedicine.cloneVatTuHoaDon();
        updatedMedicine.getVatTu().setMaVatTu("");
        listUpdatedVatTu.set(row, updatedMedicine);
        thuocHDTable.getSelectionModel().clearSelection();
    }

    private void actOnUndoUpdatingMedicineWithMa(int row, int col) {
        isVatTuTableUpdated = true;
        thuocHDTable.setValueAt(selectedMedicine.getVatTu().getMaVatTu(), row, col);
        thuocHDTable.getSelectionModel().clearSelection();
    }

    private void actOnNonNullMedicineMaChange(String userInput, int row, int col) {
        VatTu newMedicine = null;
        // không tìm thấy thuốc
        if ((newMedicine = findVatTuByMa(userInput)) == null) {
            isVatTuTableUpdated = true;
            thuocHDTable.setValueAt(selectedMedicine.getVatTu().getMaVatTu(), row, col);
            DialogProvider.showMessageDialog("Vật phẩm này không tồn tại !", "Thông báo !");
            return;
        }
        // tìm thấy thuốc
        updatedMedicine = new VatTuHoaDon(newMedicine, 1, 0);
        isVatTuTableUpdated = true;
        thuocHDTable.setValueAt(updatedMedicine.getVatTu().getMaVatTu(), row, 0);
        thuocHDTable.setValueAt(updatedMedicine.getVatTu().getTenVatTu(), row, 1);
        thuocHDTable.setValueAt(updatedMedicine.getVatTu().getLoai(), row, 2);
        thuocHDTable.setValueAt(updatedMedicine.getSoLuong(), row, 3);
        double donGia = updatedMedicine.getDonGia();
        thuocHDTable.setValueAt(donGia, row, 4);
        thuocHDTable.setValueAt(donGia * updatedMedicine.getSoLuong(), row, 5);
        if (listUpdatedVatTu.size() - 1 >= row) {
            listUpdatedVatTu.set(row, updatedMedicine);
        } else {
            listUpdatedVatTu.add(updatedMedicine);
        }
        thuocHDTable.getSelectionModel().clearSelection();
        calculateBillMoney();
    }

    private VatTu findVatTuByMa(String ma) {
        for (VatTu medicine : hoaDonController.getListVatTu()) {
            if (medicine.getMaVatTu().equals(ma)) {
                return medicine;
            }
        }
        return null;
    }

    private void changeVatTuAmount(int row, int col) {
        int amount = (int) thuocHDTable.getValueAt(row, col);
        if (amount < 0) {
            isVatTuTableUpdated = true;
            thuocHDTable.setValueAt(selectedMedicine.getSoLuong(), row, col);
            thuocHDTable.getSelectionModel().clearSelection();
            DialogProvider.showMessageDialog("Số lượng không được âm !", "Thông báo !");
            return;
        }
        if (amount == selectedMedicine.getSoLuong()) {
            thuocHDTable.getSelectionModel().clearSelection();
            return;
        }
        updatedMedicine = selectedMedicine.cloneVatTuHoaDon();
        updatedMedicine.setSoLuong(amount);
        isVatTuTableUpdated = true;
        double totalMoney = updatedMedicine.getSoLuong() * updatedMedicine.getDonGia();
        thuocHDTable.setValueAt(amount, row, col);
        thuocHDTable.setValueAt(totalMoney, row, 5);
        listUpdatedVatTu.set(row, updatedMedicine);
        thuocHDTable.getSelectionModel().clearSelection();
        calculateBillMoney();
    }

    private void changeVatTuCost(int row, int col) {
        double cost = (double) thuocHDTable.getValueAt(row, col);
        if (cost < 0) {
            thuocHDTable.setValueAt(selectedMedicine.getDonGia(), row, col);
            thuocHDTable.getSelectionModel().clearSelection();
            DialogProvider.showMessageDialog("Giá tiền không được âm !", "Thông báo !");
            return;
        }
        if (cost == selectedMedicine.getDonGia()) {
            thuocHDTable.getSelectionModel().clearSelection();
            return;
        }
        updatedMedicine = selectedMedicine.cloneVatTuHoaDon();
        updatedMedicine.setDonGia(cost);
        isVatTuTableUpdated = true;
        double totalMoney = updatedMedicine.getSoLuong() * updatedMedicine.getDonGia();
        thuocHDTable.setValueAt(cost, row, col);
        thuocHDTable.setValueAt(totalMoney, row, 5);
        listUpdatedVatTu.set(row, updatedMedicine);
        thuocHDTable.clearSelection();
        calculateBillMoney();
    }

    private void filterInvalidMedicine() {
        for (int i = 0; i < listUpdatedVatTu.size(); i++) {
            if (listUpdatedVatTu.get(i) == null) {
                listUpdatedVatTu.remove(i);
                i--;
                continue;
            }
            if (listUpdatedVatTu.get(i).getVatTu().getMaVatTu().equals("") || listUpdatedVatTu.get(i).getSoLuong() == 0) {
                listUpdatedVatTu.remove(i);
                vatTuTableModel.removeRow(i);
                i--;
            }
        }
    }

    // ----------------------------------------------------------------------------------------------------------------------------------
    private void updateTablesOnChanges() {
        dichVuTableModel.setRowCount(0);
        for (DichVuHoaDon d : listUpdatedDichVu) {
            dichVuTableModel.addRow(new Object[]{d.getDichVu().getTenDichVu(), d.getSoLuong(), d.getGiaBanThuc(),
                d.getSoLuong() * d.getGiaBanThuc()});
        }
        vatTuTableModel.setRowCount(0);
        for (VatTuHoaDon v : listUpdatedVatTu) {
            vatTuTableModel.addRow(new Object[]{v.getVatTu().getMaVatTu(), v.getVatTu().getTenVatTu(), v.getVatTu().getLoai(),
                v.getSoLuong(), v.getDonGia(), v.getSoLuong() * v.getDonGia()});
        }
    }

    private void updateItemChangeOnHoaDonTable() {
        // cap nhat table hoa don
        int choosenIdx = hoaDonTable.getSelectedRow();
        hoaDonTable.setValueAt(selectedHoaDon.getSoHD(), choosenIdx, 0);
        hoaDonTable.setValueAt(formatter.format(selectedHoaDon.getNgayLap()), choosenIdx, 1);
        hoaDonTable.setValueAt(selectedHoaDon.getBenhNhan().getTenBenhNhan(), choosenIdx, 2);
        hoaDonTable.setValueAt(BigDecimal.valueOf(selectedHoaDon.getTongTien()), choosenIdx, 3);
    }

    private void updateHoaDon() {
        capNhatHDButton.addActionListener((e) -> {
            if (hoaDonTable.getSelectedRow() < 0) {
                DialogProvider.showMessageDialog("Hãy chọn 1 hóa đơn trong bảng để cập nhật !", "Thông báo");
                return;
            }
            // Xử lý thay đổi dịch vụ
            filterInvalidService();
            // sync data between original data with table updated data
            selectedHoaDon.getListDichVu().clear();
            selectedHoaDon.getListDichVu().addAll(listUpdatedDichVu);
            dichVuTable.getSelectionModel().clearSelection();

            // Xử lý thay đổi thuốc 
            filterInvalidMedicine();
            selectedHoaDon.getListVatTu().clear();
            selectedHoaDon.getListVatTu().addAll(listUpdatedVatTu);
            thuocHDTable.getSelectionModel().clearSelection();
            if (!updateBillBasicInfo()) {
                backupBillInfoIfInvalid();
                return;
            }
            updateTablesOnChanges();
            calculateBillMoney();
            selectedHoaDon.setTongTien(Double.parseDouble(thanhTienLabel.getText().trim()));
            updateItemChangeOnHoaDonTable();
            int updatedHoaDonIdx = hoaDonTable.getSelectedRow();
            hoaDonController.updateBill(updatedHoaDonIdx, selectedHoaDon); // update vao file
            DialogProvider.showMessageDialog("Cập nhật bệnh án thành công !", "Thông báo !");
        });
    }

    private void backupBillInfoIfInvalid() {
        soHDTextField.setText(selectedHoaDon.getSoHD());
        ngayKhamHDTextField.setText(formatter.format(selectedHoaDon.getNgayLap()));
        maBenhNhanComboBox.setSelectedItem(selectedHoaDon.getBenhNhan().getMaBenhNhan());
        tenBenhNhanComboBox.setSelectedItem(selectedHoaDon.getBenhNhan().getTenBenhNhan());
    }

    private boolean checkIfInputInvalid(String id, String date) {
        for (int i = 0; i < hoaDonController.getListHoaDon().size(); i++) {
            if (hoaDonTable.getSelectedRow() != i && hoaDonController.getListHoaDon().get(i).getSoHD().equalsIgnoreCase(id)) {
                DialogProvider.showMessageDialog("Số hóa đơn này đã tồn tại !", "Thông báo !");
                return false;
            }
        }
        try {
            formatter.parse(date);
        } catch (ParseException ex) {
            DialogProvider.showMessageDialog("Vui lòng nhập đúng định dạng ngày yyyy-MM-dd", "Thông báo !");
            return false;
        }
        return true;
    }

    private boolean updateBillBasicInfo() {
        String inputSoHoaDon = soHDTextField.getText().trim();
        Date createdDay = null;
        if (checkIfInputInvalid(inputSoHoaDon, ngayKhamHDTextField.getText().trim())) {
            selectedHoaDon.setSoHD(inputSoHoaDon);
            try {
                createdDay = formatter.parse(ngayKhamHDTextField.getText().trim());
            } catch (ParseException ex) {
                System.out.println("Log bug : Lỗi ngày !");
            }
            selectedHoaDon.setNgayLap(createdDay);
        } else {
            return false;
        }
        String newPatientId = (String) maBenhNhanComboBox.getSelectedItem();
        BenhNhan newPatient = hoaDonController.findBenhNhan(newPatientId, "");
        if (newPatient != null) {
            selectedHoaDon.setBenhNhan(newPatient);
        }
        return true;
    }

    private void clearHoaDonChanges() {
        // xóa danh sách dữ liệu trạng thái của bảng dịch vụ
        listUpdatedDichVu.clear();
        dichVuTable.getSelectionModel().clearSelection();
        // xóa danh sách dữ liệu trạng thái của bảng vật tư
        listUpdatedVatTu.clear();
        thuocHDTable.getSelectionModel().clearSelection();
        hoaDonTable.getSelectionModel().clearSelection();
    }

    private void addHoaDon() {
        themHDButton.addActionListener((e) -> {
            if (hoaDonTable.getSelectedRow() > 0) {
                DialogProvider.showMessageDialog("Bạn đang tham chiếu tới 1 hóa đơn khác , vui lòng bỏ lựa chọn để thêm !", "Thông báo !");
                return;
            }
        });
    }

    public void updateData(Object updatedObject) {
        if (updatedObject instanceof String) {
            // xoa thuoc se khien cho tat ca cac hoa don phai xoa loai thuoc nay o tat ca cac hoa don 
            // truong hop xoa thuoc , can truyen ma vao !
        }

        if (updatedObject instanceof BenhNhan) {
            // Viết logic cập nhật UI với dữ liệu vừa được thay đổi 
        } else { // Vat tu

        }
    }
}
