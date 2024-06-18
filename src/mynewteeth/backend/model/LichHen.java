/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mynewteeth.backend.model;

import java.util.Date;

/**
 *
 * @author Us
 */
public class LichHen {
    private String maLicHen;
    private String tenDichVu;
    private String ghiChu;
    private Date ngayHen;
    private BenhNhan benhNhan;
    private BacSi bacSi;
    private TaiKhoan taiKhoan;
    
    public LichHen(String maLicHen, String tenDichVu, String ghiChu , Date ngayHen, BenhNhan benhNhan, BacSi bacSi, TaiKhoan taiKhoan) {
        this.maLicHen = maLicHen;
        this.tenDichVu = tenDichVu;
        this.ghiChu = ghiChu;
        this.ngayHen = ngayHen;
        this.benhNhan = benhNhan;
        this.bacSi = bacSi;
        this.taiKhoan = taiKhoan;
    }

    public LichHen() {
    }

    
    public Date getNgayHen() {
        return ngayHen;
    }

    public void setNgayHen(Date ngayHen) {
        this.ngayHen = ngayHen;
    }

    public String getMaLicHen() {
        return maLicHen;
    }

    public void setMaLicHen(String maLicHen) {
        this.maLicHen = maLicHen;
    }

    public String getTenDichVu() {
        return tenDichVu;
    }

    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public BenhNhan getBenhNhan() {
        return benhNhan;
    }

    public void setBenhNhan(BenhNhan benhNhan) {
        this.benhNhan = benhNhan;
    }

    public BacSi getBacSi() {
        return bacSi;
    }

    public void setBacSi(BacSi bacSi) {
        this.bacSi = bacSi;
    }

    public TaiKhoan getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    @Override
    public String toString() {
        return "LichHen{" + "maLicHen=" + maLicHen + ", tenDichVu=" + tenDichVu + ", ghiChu=" + ghiChu + ", benhNhan=" + benhNhan.getMaBenhNhan() + ", bacSi=" + bacSi.getHoTen() + ", taiKhoan=" + taiKhoan.getAccountName() + '}';
    }
}
