/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mynewteeth.backend.model;

/**
 *
 * @author Us
 */
public class DichVuHoaDon {
    private DichVu dichVu;
    private int soLuong;
    private double giaBanThuc;

    public DichVu getDichVu() {
        return dichVu;
    }

    public void setDichVu(DichVu dichVu) {
        this.dichVu = dichVu;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGiaBanThuc() {
        return giaBanThuc;
    }

    public void setGiaBanThuc(double giaBanThuc) {
        this.giaBanThuc = giaBanThuc;
    }

    public DichVuHoaDon(DichVu dichVu, int soLuong, double giaBanThuc) {
        this.dichVu = dichVu;
        this.soLuong = soLuong;
        this.giaBanThuc = giaBanThuc;
    }
    
    public DichVuHoaDon cloneDichVuHoaDon() {
        return new DichVuHoaDon(dichVu.cloneDichVu(), soLuong, giaBanThuc);
    }
}
