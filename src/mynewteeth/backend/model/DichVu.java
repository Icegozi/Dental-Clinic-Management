/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mynewteeth.backend.model;

/**
 *
 * @author Us
 */
public class DichVu {
    private String maDichVu;
    private String tenDichVu;
    private double giaTien;

    public String getMaDichVu() {
        return maDichVu;
    }

    public void setMaDichVu(String maDichVu) {
        this.maDichVu = maDichVu;
    }

    public String getTenDichVu() {
        return tenDichVu;
    }

    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }

    public double getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(double giaTien) {
        this.giaTien = giaTien;
    }

    public DichVu(String maDichVu, String tenDichVu, double giaTien) {
        this.maDichVu = maDichVu;
        this.tenDichVu = tenDichVu;
        this.giaTien = giaTien;
    }
    
    public DichVu cloneDichVu() {
        return new DichVu(maDichVu, tenDichVu, giaTien);
    }
}
