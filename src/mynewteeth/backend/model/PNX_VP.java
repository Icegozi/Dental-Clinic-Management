/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mynewteeth.backend.model;

/**
 *
 * @author Us
 */
public class PNX_VP {
    private String maDon;
    private String maVatPham;
    private String tenVatPham;
    private String loai;
    private String donVi;
    private int soLuong;
    private double giaTien;
    private double thanhTien;

    public PNX_VP(String maDon, String maVatPham, String tenVatPham, String loai, String donVi, int soLuong, double giaTien, double thanhTien) {
        this.maDon = maDon;
        this.maVatPham = maVatPham;
        this.tenVatPham = tenVatPham;
        this.loai = loai;
        this.donVi = donVi;
        this.soLuong = soLuong;
        this.giaTien = giaTien;
        this.thanhTien = thanhTien;
    }

    public PNX_VP() {
        this.maDon = "";
        this.maVatPham = "";
        this.tenVatPham = "";
        this.loai = "";
        this.donVi = "";
        this.soLuong = 0;
        this.giaTien = 0.0;
        this.thanhTien = 0.0;
    }

    public String getMaDon() {
        return maDon;
    }

    public void setMaDon(String maDon) {
        this.maDon = maDon;
    }

    public String getMaVatPham() {
        return maVatPham;
    }

    public void setMaVatPham(String maVatPham) {
        this.maVatPham = maVatPham;
    }

    public String getTenVatPham() {
        return tenVatPham;
    }

    public void setTenVatPham(String tenVatPham) {
        this.tenVatPham = tenVatPham;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
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

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

    @Override
    public String toString() {
        return "PNX_VP{" + "maDon=" + maDon + ", maVatPham=" + maVatPham + ", tenVatPham=" + tenVatPham + ", loai=" + loai + ", donVi=" + donVi + ", soLuong=" + soLuong + ", giaTien=" + giaTien + ", thanhTien=" + thanhTien + '}';
    }
}
