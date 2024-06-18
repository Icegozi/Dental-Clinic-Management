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
public class HoaDon {
    private String soHD;
    private Date ngayLap;
    private BenhNhan benhNhan;
    private List<DichVuHoaDon> listDichVu;
    private List<VatTuHoaDon> listVatTu;
    private double tongTien;

    public String getSoHD() {
        return soHD;
    }

    public void setSoHD(String soHD) {
        this.soHD = soHD;
    }

    public Date getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(Date ngayLap) {
        this.ngayLap = ngayLap;
    }

    public BenhNhan getBenhNhan() {
        return benhNhan;
    }

    public void setBenhNhan(BenhNhan benhNhan) {
        this.benhNhan = benhNhan;
    }

    public List<DichVuHoaDon> getListDichVu() {
        return listDichVu;
    }

    public void setListDichVu(List<DichVuHoaDon> listDichVu) {
        this.listDichVu = listDichVu;
    }

    public List<VatTuHoaDon> getListVatTu() {
        return listVatTu;
    }

    public void setListVatTu(List<VatTuHoaDon> listVatTu) {
        this.listVatTu = listVatTu;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public HoaDon(String soHD, Date ngayLap, BenhNhan benhNhan, List<DichVuHoaDon> listDichVu, List<VatTuHoaDon> listVatTu, double tongTien) {
        this.soHD = soHD;
        this.ngayLap = ngayLap;
        this.benhNhan = benhNhan;
        this.listDichVu = listDichVu;
        this.listVatTu = listVatTu;
        this.tongTien = tongTien;
    }

    @Override
    public String toString() {
        String result = "So HD : " + soHD + "\t" + "Ngay lap : " + ngayLap + "\n";
        result += ("Ma benh nhan : " + benhNhan.getMaBenhNhan() + "\tTen benh nhan : " + benhNhan.getTenBenhNhan() + "\n");
        for(DichVuHoaDon dvhd : listDichVu) {
            result += "Ma dich vu : " + dvhd.getDichVu().getMaDichVu() + "\tTen dich vu : " + dvhd.getDichVu().getTenDichVu();
            result += "\tSo luong : " + dvhd.getSoLuong() + "\n";
        }
        for(VatTuHoaDon vt : listVatTu) {
            result += "Ma vat tu : " + vt.getVatTu().getMaVatTu()+ "\tTen vat tu : " + vt.getVatTu().getTenVatTu();
            result += "\tSo luong : " + vt.getVatTu().getSoLuong() + "\tDon gia : " + vt.getDonGia() + "\n";
        }
        return result;
    }
    
    
}
