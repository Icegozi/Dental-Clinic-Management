/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mynewteeth.backend.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Us
 */
public class PhieuNhapXuat {
    private String maDon;
    private Date ngayGiaoDich;
    private String loaiGiaoDich;
    private String nhaCungCap;
    private double tongTien;
    private List<PNX_VP> listVP;

    public PhieuNhapXuat(String maDon, 
            Date ngayGiaoDich, 
            String loaiGiaoDich, 
            String nhaCungCap, 
            double tongTien, 
            List<PNX_VP> listVP) {
        this.maDon = maDon;
        this.ngayGiaoDich = ngayGiaoDich;
        this.loaiGiaoDich = loaiGiaoDich;
        this.nhaCungCap = nhaCungCap;
        this.tongTien = tongTien;
        this.listVP = listVP;
    }

    public PhieuNhapXuat() {
        this.listVP = new ArrayList<>();
    }

    public String getMaDon() {
        return maDon;
    }

    public void setMaDon(String maDon) {
        this.maDon = maDon;
    }

    public Date getNgayGiaoDich() {
        return ngayGiaoDich;
    }

    public void setNgayGiaoDich(Date ngayGiaoDich) {
        this.ngayGiaoDich = ngayGiaoDich;
    }

    public String getLoaiGiaoDich() {
        return loaiGiaoDich;
    }

    public void setLoaiGiaoDich(String loaiGiaoDich) {
        this.loaiGiaoDich = loaiGiaoDich;
    }

    public String getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(String nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public List<PNX_VP> getListVP() {
        return listVP;
    }

    public void setListVP(List<PNX_VP> listVP) {
        this.listVP = listVP;
    }
}
