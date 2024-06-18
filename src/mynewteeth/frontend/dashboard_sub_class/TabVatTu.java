/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mynewteeth.frontend.dashboard_sub_class;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import mynewteeth.backend.controller.VatTuController;
import mynewteeth.backend.interfaces.IUpdateData;
import mynewteeth.backend.model.VatTu;
import mynewteeth.frontend.DialogProvider;

/**
 *
 * @author Us
 */
public class TabVatTu {

    private final JTable vatTuTable;
    private final JTextField vatTuTextField;
    private final JTextField tenVatTuTextField;
    private final JTextField loaiVatTuTextField;
    private final JTextField tinhTrangVatTuTextField;
    private final JTextField giaNhapVatTuTextField;
    private final JTextField ngayNhapVatTuTextField;
    private final JTextField soLuongVatTuTextField;
    private final JButton themVTButton;
    private final JButton xoaVatTuButton;
    private final JButton capNhatVatTuButton;
    private final JButton timKiemVatTuButton;
    private final IUpdateData callback;

    private final VatTuController controller;
    private final DefaultTableModel materialsTableModel;
    private VatTu selectedMaterial;

    public TabVatTu(JTable vatTuTable, JTextField vatTuTextField, JTextField tenVatTuTextField,
            JTextField loaiVatTuTextField, JTextField tinhTrangVatTuTextField, JTextField giaNhapVatTuTextField,
            JTextField ngayNhapVatTuTextField, JTextField soLuongVatTuTextField, JButton themVTButton,
            JButton xoaVatTuButton, JButton capNhatVatTuButton, JButton timKiemVatTuButton, IUpdateData updateCallback) {

        this.vatTuTable = vatTuTable;
        this.vatTuTextField = vatTuTextField;
        this.tenVatTuTextField = tenVatTuTextField;
        this.loaiVatTuTextField = loaiVatTuTextField;
        this.tinhTrangVatTuTextField = tinhTrangVatTuTextField;
        this.giaNhapVatTuTextField = giaNhapVatTuTextField;
        this.ngayNhapVatTuTextField = ngayNhapVatTuTextField;
        this.soLuongVatTuTextField = soLuongVatTuTextField;
        this.themVTButton = themVTButton;
        this.xoaVatTuButton = xoaVatTuButton;
        this.capNhatVatTuButton = capNhatVatTuButton;
        this.timKiemVatTuButton = timKiemVatTuButton;
        this.callback = updateCallback;

        controller = new VatTuController();
        materialsTableModel = (DefaultTableModel) vatTuTable.getModel();

        loadAndBindData();
    }

    private void loadAndBindData() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                controller.loadData();
                return null;
            }

            @Override
            protected void done() {
                bindTableData();
                setUpTableBehavior();
                addMaterial();
                deleteMaterial();
                updateMaterial();
                searchMaterial();
            }
        }.execute();
    }

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    private void bindTableData() {
        for (VatTu m : controller.getListVatTu()) {
            materialsTableModel.addRow(new Object[]{m.getMaVatTu(), m.getTenVatTu(), m.getLoai(), m.getTinhTrang(),
                m.getGiaNhap(), formatter.format(m.getNgayNhap()), m.getSoLuong()});
        }
    }

    private void setUpTableBehavior() {
        vatTuTable.getSelectionModel().addListSelectionListener((e) -> {
            if (e.getValueIsAdjusting()) {
                return;
            }
            int selectedIndex = vatTuTable.getSelectedRow();
            if (selectedIndex < 0) {
                selectedMaterial = null;
                return;
            }
            selectedMaterial = controller.getListVatTu().get(selectedIndex);
            updateUiOnTableClick();
        });
    }

    private void updateUiOnTableClick() {
        vatTuTextField.setText(selectedMaterial.getMaVatTu());
        tenVatTuTextField.setText(selectedMaterial.getTenVatTu());
        loaiVatTuTextField.setText(selectedMaterial.getLoai());
        tinhTrangVatTuTextField.setText(selectedMaterial.getTinhTrang());
        giaNhapVatTuTextField.setText(String.valueOf(selectedMaterial.getGiaNhap()));
        ngayNhapVatTuTextField.setText(formatter.format(selectedMaterial.getNgayNhap()));
        soLuongVatTuTextField.setText(String.valueOf(selectedMaterial.getSoLuong()));
    }

    private boolean checkInputValidation() {
        double importedPrice = -1;
        int amount = -1;
        try {
            importedPrice = Double.parseDouble(giaNhapVatTuTextField.getText().trim());
        } catch (NumberFormatException e) {
            DialogProvider.showMessageDialog("Giá nhập phải là số thực không âm !", "Cảnh báo !");
            return false;
        }
        try {
            if (importedPrice < 0) {
                throw new Exception("Giá nhập không được âm !");
            }
        } catch (Exception e) {
            DialogProvider.showMessageDialog(e.getMessage(), "Cảnh báo !");
            return false;
        }

        try {
            formatter.parse(ngayNhapVatTuTextField.getText().trim());
        } catch (ParseException ex) {
            DialogProvider.showMessageDialog("Ngày nhập không đúng định dạng ! Vui lòng nhập theo định dạng (yyyy-MM-dd)", "Cảnh báo !");
            return false;
        }

        try {
            amount = Integer.parseInt(soLuongVatTuTextField.getText().trim());
        } catch (NumberFormatException e) {
            DialogProvider.showMessageDialog("Số lượng phải là số nguyên không âm !", "Cảnh báo !");
            return false;
        }
        try {
            if (amount < 0) {
                throw new Exception("Số lượng không được âm !");
            }
        } catch (Exception e) {
            DialogProvider.showMessageDialog(e.getMessage(), "Cảnh báo !");
            return false;
        }
        return true;
    }

    private boolean checkEmptyFields() {
        if (vatTuTextField.getText().equals("") || tenVatTuTextField.getText().equals("")
                || loaiVatTuTextField.getText().equals("") || tinhTrangVatTuTextField.getText().equals("")
                || giaNhapVatTuTextField.getText().equals("") || ngayNhapVatTuTextField.getText().equals("")
                || soLuongVatTuTextField.getText().equals("")) {
            DialogProvider.showMessageDialog("Không được bỏ trống bất kì trường thông tin nào !", "Cảnh báo !");
            return false;
        }
        return true;
    }

    private boolean checkMaterialExisted() {
        String materialId = vatTuTextField.getText().trim();
        String materialName = tenVatTuTextField.getText().trim();
        for (VatTu material : controller.getListVatTu()) {
            if (materialId.equalsIgnoreCase(material.getMaVatTu()) || materialName.equalsIgnoreCase(material.getTenVatTu())) {
                DialogProvider.showMessageDialog("Vật tư này đã tồn tại !", "Cảnh báo !");
                return false;
            }
        }
        return true;
    }

    private VatTu createMaterialFromFields() {
        try {
            return new VatTu(vatTuTextField.getText(), tenVatTuTextField.getText(), loaiVatTuTextField.getText(),
                    tinhTrangVatTuTextField.getText(), Double.parseDouble(giaNhapVatTuTextField.getText()), formatter.parse(ngayNhapVatTuTextField.getText()),
                    Integer.parseInt(soLuongVatTuTextField.getText()));
        } catch (ParseException ex) {
            DialogProvider.showMessageDialog("Có lỗi đã xảy ra , thay đổi sẽ không được cập nhật !", "Thông báo !");
        }
        return null;
    }

    private void updateUiOnAdding() {
        materialsTableModel.addRow(addedMaterial.getTableRowFormat());
    }

    private boolean satisfied = false;
    private VatTu addedMaterial = null;

    private void addMaterial() {
        themVTButton.addActionListener((e) -> {
            satisfied = checkEmptyFields() && checkInputValidation() && checkMaterialExisted();
            if (!satisfied) {
                vatTuTable.getSelectionModel().clearSelection();
                return;
            }
            addedMaterial = createMaterialFromFields();
            updateUiOnAdding();
            controller.addMaterial(addedMaterial);
            vatTuTable.getSelectionModel().clearSelection();
            DialogProvider.showMessageDialog("Thêm vật tư thành công !", "Thông báo !");
        });
    }
    
    private void clearUiOnDelete() {
        vatTuTextField.setText("");
        tenVatTuTextField.setText("");
        loaiVatTuTextField.setText("");
        tinhTrangVatTuTextField.setText("");
        giaNhapVatTuTextField.setText("");
        ngayNhapVatTuTextField.setText("");
        soLuongVatTuTextField.setText("");
    }

    private void deleteMaterial() {
        xoaVatTuButton.addActionListener((e) -> {
            int selectedIndex = vatTuTable.getSelectedRow();
            if (selectedIndex < 0 || selectedMaterial == null) {
                vatTuTable.getSelectionModel().clearSelection();
                DialogProvider.showMessageDialog("Vui lòng chọn 1 dòng trong bảng để xóa !", "Thông báo !");
                return;
            }
            materialsTableModel.removeRow(selectedIndex);
            controller.getListVatTu().remove(selectedIndex);
            clearUiOnDelete();
            // here we only rewrite list of VatTu that has removed choosen VatTu
            controller.update();
            vatTuTable.getSelectionModel().clearSelection(); // check
            DialogProvider.showMessageDialog("Đã xóa vật tư này !", "Thông báo !");
        });
    }

    private boolean checkIfChangeValid() {
        boolean notExisted = true;
        String id = vatTuTextField.getText();
        String name = tenVatTuTextField.getText();
        for (VatTu material : controller.getListVatTu()) {
            if(!selectedMaterial.equals(material) && (id.equalsIgnoreCase(material.getMaVatTu()) || name.equalsIgnoreCase(material.getTenVatTu()))) {
                DialogProvider.showMessageDialog("Mã hoặc tên vật tư đã tồn tại !", "Thông báo !");
                notExisted = false;
                break;
            }
        }
        return notExisted && checkInputValidation();
    }

    private void changeModelData() {
        selectedMaterial.setMaVatTu(vatTuTextField.getText());
        selectedMaterial.setTenVatTu(tenVatTuTextField.getText());
        selectedMaterial.setLoai(loaiVatTuTextField.getText().trim());
        selectedMaterial.setTinhTrang(tinhTrangVatTuTextField.getText());
        selectedMaterial.setGiaNhap(Double.parseDouble(giaNhapVatTuTextField.getText()));
        try {
            selectedMaterial.setNgayNhap(formatter.parse(ngayNhapVatTuTextField.getText()));
        } catch (ParseException ex) {
            Logger.getLogger(TabVatTu.class.getName()).log(Level.SEVERE, null, ex);
        }
        selectedMaterial.setSoLuong(Integer.parseInt(soLuongVatTuTextField.getText()));
    }

    private void changeUiOnUpdating() {
        int selectedIndex = vatTuTable.getSelectedRow();
        vatTuTable.setValueAt(selectedMaterial.getMaVatTu(), selectedIndex, 0);
        vatTuTable.setValueAt(selectedMaterial.getTenVatTu(), selectedIndex, 1);
        vatTuTable.setValueAt(selectedMaterial.getLoai(), selectedIndex, 2);
        vatTuTable.setValueAt(selectedMaterial.getTinhTrang(), selectedIndex, 3);
        vatTuTable.setValueAt(selectedMaterial.getGiaNhap(), selectedIndex, 4);
        vatTuTable.setValueAt(formatter.format(selectedMaterial.getNgayNhap()), selectedIndex, 5);
        vatTuTable.setValueAt(selectedMaterial.getSoLuong(), selectedIndex, 6);
    }

    private void updateMaterial() {
        capNhatVatTuButton.addActionListener((e) -> {
            if(selectedMaterial == null) {
                DialogProvider.showMessageDialog("Hãy chọn 1 vật tư từ bảng để cập nhật !", "Thông báo !");
                return;
            }
            if (!checkIfChangeValid()) {
                vatTuTable.getSelectionModel().clearSelection();
                clearUiOnDelete();
                return;
            }
            changeModelData();
            changeUiOnUpdating();
            controller.update();
            //vatTuTable.getSelectionModel().clearSelection();
            System.out.println(selectedMaterial == null);
            //callback.onUpdate();
            DialogProvider.showMessageDialog("Cập nhật thành công !", "Thông báo !");
        });
    }
    
    private int findMaterialIndex(String id, String name) {
        for(int i = 0; i < controller.getListVatTu().size(); i++) {
            if(controller.getListVatTu().get(i).getMaVatTu().equalsIgnoreCase(id) || controller.getListVatTu().get(i).getTenVatTu().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1;
    }
    
    private void searchMaterial() {
        timKiemVatTuButton.addActionListener((e) -> {
            String idInput = vatTuTextField.getText().trim();
            String nameInput = tenVatTuTextField.getText().trim();
            if(idInput.equals("") && nameInput.equals("")) {
                DialogProvider.showMessageDialog("Vui lòng nhập thông tin mã vật tư hoặc tên vật tư để tìm kiếm !", "Thông báo !");
                return;
            }
            int searchingResult = findMaterialIndex(idInput, nameInput);
            if(searchingResult == -1) {
                DialogProvider.showMessageDialog("Không tìm thấy vật phẩm này !", "Thông báo !");
                return;
            }
            vatTuTable.setRowSelectionInterval(searchingResult, searchingResult);
            changeUiOnUpdating();
        });
    }
}
