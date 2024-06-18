/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mynewteeth.backend.model;

/**
 *
 * @author Us
 */
public class VatTuHoaDon {
    private VatTu vatTu;
    private int soLuong;
    private double donGia;

    public VatTu getVatTu() {
        return vatTu;
    }

    public void setVatTu(VatTu vatTu) {
        this.vatTu = vatTu;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public VatTuHoaDon(VatTu vatTu, int soLuong, double donGia) {
        this.vatTu = vatTu;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }
    
    public VatTuHoaDon cloneVatTuHoaDon() {
        return new VatTuHoaDon(vatTu.cloneVatTu(), soLuong, donGia);
    }
}
