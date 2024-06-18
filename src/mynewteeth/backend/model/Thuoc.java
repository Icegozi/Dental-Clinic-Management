/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mynewteeth.backend.model;

/**
 *
 * @author Us
 */
public class Thuoc {
    private String maBenhAn;
    private String maVatTu;
    private int soLuong;
    private double giaTien;

    public Thuoc(String maBenhAn, String maVatTu, int soLuong, double giaTien) {
        this.maBenhAn = maBenhAn;
        this.maVatTu = maVatTu;
        this.soLuong = soLuong;
        this.giaTien = giaTien;
    }

    public Thuoc() {
    }

    public String getMaBenhAn() {
        return maBenhAn;
    }

    public void setMaBenhAn(String maBenhAn) {
        this.maBenhAn = maBenhAn;
    }

    public String getMaVatTu() {
        return maVatTu;
    }

    public void setMaVatTu(String maVatTu) {
        this.maVatTu = maVatTu;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(double giaTien) {
        this.giaTien = giaTien;
    }
}
