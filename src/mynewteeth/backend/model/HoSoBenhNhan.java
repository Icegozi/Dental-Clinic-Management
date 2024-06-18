/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mynewteeth.backend.model;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Us
 */
public class HoSoBenhNhan {

    private String maHoSoBenhNhan;
    private BenhNhan benhNhan;
    private BacSi bacSi;
    private Date ngayKham;
    private String trieuChung;
    private String chuanDoanBanDau;
    private String tienSuBenh;
    private Date ngayTaiKham;
    private String ghiChuBacSi;
    private double tongTien;
    private List<VatTu> listVatTu;
    
    public HoSoBenhNhan(String maHoSoBenhNhan, BenhNhan benhNhan, BacSi bacSi, Date ngayKham,
            String trieuChung, String chuanDoanBanDau, String tienSuBenh, Date ngayTaiKham, String ghiChuBacSi, double tongTien, List<VatTu> listVatTu) {
        this.maHoSoBenhNhan = maHoSoBenhNhan;
        this.benhNhan = benhNhan;
        this.bacSi = bacSi;
        this.ngayKham = ngayKham;
        this.trieuChung = trieuChung;
        this.chuanDoanBanDau = chuanDoanBanDau;
        this.tienSuBenh = tienSuBenh;
        this.ngayTaiKham = ngayTaiKham;
        this.ghiChuBacSi = ghiChuBacSi;
        this.tongTien = tongTien;
        this.listVatTu = listVatTu;
    }

    public String getMaHoSoBenhNhan() {
        return maHoSoBenhNhan;
    }

    public void setMaHoSoBenhNhan(String maHoSoBenhNhan) {
        this.maHoSoBenhNhan = maHoSoBenhNhan;
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

    public Date getNgayKham() {
        return ngayKham;
    }

    public void setNgayKham(Date ngayKham) {
        this.ngayKham = ngayKham;
    }

    public String getTrieuChung() {
        return trieuChung;
    }

    public void setTrieuChung(String trieuChung) {
        this.trieuChung = trieuChung;
    }

    public String getChuanDoanBanDau() {
        return chuanDoanBanDau;
    }

    public void setChuanDoanBanDau(String chuanDoanBanDau) {
        this.chuanDoanBanDau = chuanDoanBanDau;
    }

    public String getTienSuBenh() {
        return tienSuBenh;
    }

    public void setTienSuBenh(String tienSuBenh) {
        this.tienSuBenh = tienSuBenh;
    }

    public Date getNgayTaiKham() {
        return ngayTaiKham;
    }

    public void setNgayTaiKham(Date ngayTaiKham) {
        this.ngayTaiKham = ngayTaiKham;
    }

    public String getGhiChuBacSi() {
        return ghiChuBacSi;
    }

    public void setGhiChuBacSi(String ghiChuBacSi) {
        this.ghiChuBacSi = ghiChuBacSi;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public List<VatTu> getListVatTu() {
        return listVatTu;
    }
}
