/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mynewteeth.backend.model;
import java.util.Date;
import mynewteeth.backend.interfaces.InvalidMaBenhNhanException;
import mynewteeth.backend.interfaces.InvalidSoDienThoaiException;
import mynewteeth.backend.interfaces.InvalidTenBenhNhanException;

/**
 *
 * @author Us
 */
public class BenhNhan {
    
    private String maBenhNhan;
    private String tenBenhNhan;
    private String gioiTinh;
    private Date ngaySinh;
    private String queQuan;
    private String soDienThoai;

    // Constructor đầy đủ
    public BenhNhan(String maBenhNhan, String tenBenhNhan, String gioiTinh, Date ngaySinh, String queQuan, String soDienThoai) {
        this.maBenhNhan = maBenhNhan;
        this.tenBenhNhan = tenBenhNhan;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.queQuan = queQuan;
        this.soDienThoai = soDienThoai;
    }

    public BenhNhan() {
    }
    

    // Getters và Setters cho tất cả các thuộc tính
    public String getMaBenhNhan() {
        return maBenhNhan;
    }

    public void setMaBenhNhan(String maBenhNhan) {
        this.maBenhNhan = maBenhNhan;
    }

    public String getTenBenhNhan() {
        return tenBenhNhan;
    }

    public void setTenBenhNhan(String tenBenhNhan) {
        this.tenBenhNhan = tenBenhNhan;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getQueQuan() {
        return queQuan;
    }

    public void setQueQuan(String queQuan) {
        this.queQuan = queQuan;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }
    
     // Kiểm tra số điện thoại
    public void SoDienThoaiValid() throws InvalidSoDienThoaiException {
        if (soDienThoai == null || soDienThoai.trim().isEmpty()) {
            throw new InvalidSoDienThoaiException("Số điện thoại không được để trống");
        }
        if (soDienThoai.length() != 10) {
            throw new InvalidSoDienThoaiException("Số điện thoại phải có độ dài 10 ký tự");
        }
        for (char c : soDienThoai.toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new InvalidSoDienThoaiException("Số điện thoại chỉ được chứa các chữ số");
            }
        }

    }
    
    public void TenBenhNhanNull()throws InvalidTenBenhNhanException{
        if (tenBenhNhan == null || tenBenhNhan.trim().isEmpty()) {
            throw new InvalidTenBenhNhanException("Tên bệnh nhân không được để trống");
        }
    }

    public void maBenhNhanFomat() throws InvalidMaBenhNhanException{
        if(!maBenhNhan.matches("^BN\\d{3}$")){
             throw new InvalidMaBenhNhanException("Mã bệnh nhân không đúng định dạng: " + maBenhNhan);
        }
    }

    @Override
    public String toString() {
        return "BenhNhan{" + "maBenhNhan=" + maBenhNhan + ", tenBenhNhan=" + tenBenhNhan + ", gioiTinh=" + gioiTinh + ", ngaySinh=" + ngaySinh + ", queQuan=" + queQuan + ", soDienThoai=" + soDienThoai + '}';
    }
}
