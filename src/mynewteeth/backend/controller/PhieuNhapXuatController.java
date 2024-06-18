/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mynewteeth.backend.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import mynewteeth.backend.model.PNX_VP;
import mynewteeth.backend.model.PhieuNhapXuat;

/**
 *
 * @author Us
 */
public class PhieuNhapXuatController {
    private List<PhieuNhapXuat> danhSachPhieuNhapXuat;
    private List<PNX_VP> vatPhamConTroller;
    private List<PNX_VP> subDon;

    public String file_PNX = "src/mynewteeth/backend/data_repository/local_data/raw_data/PhieuNhapXuat.txt";
    public String file_PNX_VP = "src/mynewteeth/backend/data_repository/local_data/raw_data/PNX_VP.txt";

    public PhieuNhapXuatController() throws ParseException {
        danhSachPhieuNhapXuat = new ArrayList<>();
        vatPhamConTroller = new ArrayList<>();
        subDon = new ArrayList<>();
        loadFromFile_PNX(file_PNX);
        loadFromFile_PNX_VP(file_PNX_VP);
    }

    public List<PhieuNhapXuat> getDanhSachPhieuNhapXuat() {
        return danhSachPhieuNhapXuat;
    }

    public void setDanhSachPhieuNhapXuat(List<PhieuNhapXuat> danhSachPhieuNhapXuat) {
        this.danhSachPhieuNhapXuat = danhSachPhieuNhapXuat;
    }

    public List<PNX_VP> getVatPhamConTroller() {
        return vatPhamConTroller;
    }

    public void setVatPhamConTroller(List<PNX_VP> vatPhamConTroller) {
        this.vatPhamConTroller = vatPhamConTroller;
    }

    public String getFile_PNX() {
        return file_PNX;
    }

    public void setFile_PNX(String file_PNX) {
        this.file_PNX = file_PNX;
    }

    public String getFile_PNX_VP() {
        return file_PNX_VP;
    }

    public void setFile_PNX_VP(String file_PNX_VP) {
        this.file_PNX_VP = file_PNX_VP;
    }

    public PhieuNhapXuat findPNXByMa(String maPhieuNhapXuat) {
        for (PhieuNhapXuat phieu : danhSachPhieuNhapXuat) {
            if (phieu.getMaDon().equals(maPhieuNhapXuat)) {
                return phieu;
            }
        }
        return null;
    }

    public PNX_VP findVatPhamByMa(String maVatPham) {
        for (PNX_VP vt : this.vatPhamConTroller) {
            if (vt.getMaVatPham().equals(maVatPham)) {
                return vt;
            }
        }
        return null; // Trả về null nếu không tìm thấy
    }

    public void loadFromFile_PNX_VP(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("#");
                if (parts.length == 8) {
                    try {
                        String maDon = parts[0];
                        String maVatPham = parts[1];
                        String tenVatPham = parts[2];
                        String loai = parts[3];
                        String donVi = parts[4];
                        int soLuong = Integer.parseInt(parts[5]);
                        double giaTien = Double.parseDouble(parts[6]);
                        double thanhTien = Double.parseDouble(parts[7]);
                        PNX_VP pnx_vp = new PNX_VP(maDon, maVatPham, tenVatPham, loai, donVi, soLuong, giaTien, thanhTien);
                        vatPhamConTroller.add(pnx_vp);
                    } catch (NumberFormatException e) {
                        System.out.println("Định dạng dữ liệu không hợp lệ: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public void loadFromFile_PNX(String fileName) throws ParseException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("#");
                if (parts.length == 5) {
                    String maDon = parts[0];
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date ngayGiaoDich = parts[1].isEmpty() ? null : sdf.parse(parts[1]);
                    String loai = parts[2];
                    String nhaCungCap = parts[3];
                    double tongTien = Double.parseDouble(parts[4]);

                    List<PNX_VP> listVP = new ArrayList<>(); // Initialize the list here

                    PhieuNhapXuat pnx = new PhieuNhapXuat(maDon, ngayGiaoDich, loai, nhaCungCap, tongTien, listVP);
                    danhSachPhieuNhapXuat.add(pnx);
                }
            }
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (ParseException e) {
            System.out.println("Parse exception: " + e.getMessage());
        }
    }

    public void maPNXExist(String maDon) throws Exception {
        for (PhieuNhapXuat c : danhSachPhieuNhapXuat) {
            if (c.getMaDon().equals(maDon)) {
                throw new Exception("Đơn đã tồn tại, vui lòng nhập lại");
            }
        }
    }

    public boolean themPNX(String maDon, String ngayGD, String loaiGD, String nhaCC, Double tongTien, List<PNX_VP> dsPnk_vp) {
        try {
            maPNXExist(maDon);
            List<PNX_VP> listvp = dsPnk_vp;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date ngayGDDate = null;
            try {
                if (ngayGD != null) {
                    ngayGDDate = sdf.parse(ngayGD);
                }
            } catch (ParseException e) {
                System.out.println("Lỗi định dạng ngày tháng: " + e.getMessage());
            }
            PhieuNhapXuat a = new PhieuNhapXuat(maDon, ngayGDDate, loaiGD, nhaCC, 0, listvp);
            danhSachPhieuNhapXuat.add(a);
            for(PNX_VP vp : listvp){
                this.vatPhamConTroller.add(vp);
            }
            this.saveToPNXFile(maDon, ngayGD, loaiGD, nhaCC, tongTien);
            this.saveToPNXVPFile();
            return true;
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    public void saveToPNXFile(String maDon, String ngayGD, String loaiGD, String nhaCC, Double tongTien) {
        try {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file_PNX, true))) {
                // Ghi thông tin vào file
                writer.write(maDon + "#" + ngayGD + "#" + loaiGD + "#" + nhaCC + "#" + tongTien);
                writer.newLine();

            } catch (IOException e) {
                // Xử lý nếu có lỗi khi ghi vào file
                JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi khi ghi vào file PhieuNhapXuat.txt: " + e.getMessage());

            }
        } catch (Exception ex) {
            //javax.swing.JOptionPane.showMessageDialog(null, ex.getMessage());

        }
    }

    public void saveToPNXVPFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file_PNX_VP))) {
            // Ghi thông tin vào file
            for (PNX_VP t : this.vatPhamConTroller) {
                writer.write(t.getMaDon() + "#" + t.getMaVatPham() + "#" + 
                        t.getTenVatPham() + "#" + t.getLoai() + "#" + t.getDonVi() 
                        + "#" + t.getSoLuong() + "#" + t.getGiaTien() 
                        + "#" + t.getThanhTien());
                writer.newLine();
            }
            subDon.clear();
        } catch (IOException e) {
            // Xử lý nếu có lỗi khi ghi vào file
            //JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi khi ghi vào file: " + e.getMessage());
            System.err.println("Đã xảy ra lỗi khi ghi vào file: " + e.getMessage());
        } catch (Exception ex) {
            //javax.swing.JOptionPane.showMessageDialog(null, ex.getMessage());
            System.err.println("Lỗi không xác định: " + ex.getMessage());
        }
    }

    public void themVP(String maDon, List<PNX_VP> PNX_VP) {
        for (PNX_VP pnx_vp : PNX_VP) {
            PNX_VP a = new PNX_VP(pnx_vp.getMaDon(), pnx_vp.getMaVatPham(), 
                    pnx_vp.getTenVatPham(), pnx_vp.getLoai(), pnx_vp.getDonVi(), 
                    pnx_vp.getSoLuong(), pnx_vp.getGiaTien(), pnx_vp.getThanhTien());
            subDon.add(a);
            vatPhamConTroller.add(a);
        }
    }

    public boolean removePNXByMa(String maPhieuNhapXuat) {
        PhieuNhapXuat phieuCanXoa = null;
        for (PhieuNhapXuat h : danhSachPhieuNhapXuat) {
            if (h.getMaDon().equals(maPhieuNhapXuat)) {
                phieuCanXoa = h;
                break;
            }
        }
        if (phieuCanXoa != null) {
            danhSachPhieuNhapXuat.remove(phieuCanXoa);
            System.out.println("Đã xóa hồ sơ bệnh nhân: " + maPhieuNhapXuat);
            return true;
        } else {
            System.out.println("Không tìm thấy phiếu cần xóa: " + maPhieuNhapXuat);
            return false;
        }
    }

    public void _xoaPNX_VP(String maDon) {
        Iterator<PNX_VP> iterator = vatPhamConTroller.iterator();
        while (iterator.hasNext()) {
            PNX_VP pnx_vp = iterator.next();
            if (pnx_vp.getMaDon().equals(maDon)) {
                iterator.remove();
            }
        }

    }

    public void saveToPNXFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file_PNX))) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (PhieuNhapXuat pnx : danhSachPhieuNhapXuat) {
                String line = pnx.getMaDon() + "#"
                        + dateFormat.format(pnx.getNgayGiaoDich()) + "#"
                        + pnx.getLoaiGiaoDich() + "#"
                        + pnx.getNhaCungCap() + "#"
                        + pnx.getTongTien();
                writer.write(line);
                writer.newLine();
            }
        }
    }

    public boolean _xoaPNX(String maDon) {
        for(PhieuNhapXuat pnx : this.danhSachPhieuNhapXuat){
            System.out.println(pnx.getMaDon());
        }
        for(PhieuNhapXuat pnx : this.danhSachPhieuNhapXuat){
            if(pnx.getMaDon().equals(maDon)){
                this.danhSachPhieuNhapXuat.remove(pnx);
                return true;
            }
        }
        return false;
    }

    public void suaPNX(String maDon, String ngayGD, String loaiGD, String nhaCC, Double tongTien, List<PNX_VP> vp) {
        // Tìm kiếm hồ sơ bệnh nhân cần sửa
        PhieuNhapXuat phieuCanSua = findPNXByMa(maDon);
        if (phieuCanSua != null) {
            // Định dạng ngày tháng
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date ngayGDDate = null;

            try {
                if (ngayGD != null && !ngayGD.isEmpty()) {
                    ngayGDDate = sdf.parse(ngayGD);
                }
            } catch (ParseException e) {
                //System.out.println("Lỗi định dạng ngày tháng: " + e.getMessage());
            }
            // Cập nhật thông tin cho hồ sơ bệnh nhân
            phieuCanSua.setMaDon(maDon);
            phieuCanSua.setNgayGiaoDich(ngayGDDate);
            phieuCanSua.setLoaiGiaoDich(loaiGD);
            phieuCanSua.setNhaCungCap(nhaCC);
            phieuCanSua.setTongTien(tongTien);
            xoaVP(maDon);
            themVP(vp);
            saveToPNXFile(maDon, ngayGD, loaiGD, nhaCC, tongTien);
            saveToPNXVPFile();
            JOptionPane.showMessageDialog(null, "Đã cập nhật hóa đon: " + maDon);
            System.out.println("Đã cập nhật hóa đơn: " + maDon);

        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy dơn: " + maDon);
            System.out.println("Không tìm thấy đơn: " + maDon);
        }
    }
    
    public void themVP(List<PNX_VP> vp){
        for(PNX_VP x : vp){
            this.vatPhamConTroller.add(x);
        }
    }
    
    public void xoaVP(String maDon) {
    Iterator<PNX_VP> iterator = this.vatPhamConTroller.iterator();
    while (iterator.hasNext()) {
        PNX_VP x = iterator.next();
        if (x.getMaDon().equals(maDon)) {
            iterator.remove();
        }
    }
}
}
