/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mynewteeth.backend.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;
import mynewteeth.backend.interfaces.InvalidMaBenhNhanException;
import mynewteeth.backend.model.BacSi;
import mynewteeth.backend.model.BenhNhan;
import mynewteeth.backend.model.HoSoBenhNhan;
import mynewteeth.backend.model.Thuoc;
import mynewteeth.backend.model.VatTu;
import mynewteeth.backend.util.InvalidMaBacSiException;
import mynewteeth.backend.util.InvalidMaHoSoBenhNhanException;

/**
 *
 * @author Us
 */
public class HoSoBenhNhanController {
    private List<HoSoBenhNhan> danhSachHoSoBenhNhan;
    private List<BenhNhan> benhNhanConTroller;
    private List<VatTu> vaTuConTroller;
    private List<BacSi> bacSiController;
    private List<Thuoc> thuocController;
    private List<Thuoc> subThuoc;
    public String file_vatTu = "src/mynewteeth/backend/data_repository/local_data/raw_data/VatTu.txt";
    public String file_bacSi = "src/mynewteeth/backend/data_repository/local_data/raw_data/BacSi.txt";
    public String file_benhNhan = "src/mynewteeth/backend/data_repository/local_data/raw_data/BenhNhan.txt";
    public String file_thuoc = "src/mynewteeth/backend/data_repository/local_data/raw_data/Thuoc.txt";
    public String file_BenhAn = "src/mynewteeth/backend/data_repository/local_data/raw_data/HoSoBenhNhan.txt";

    public HoSoBenhNhanController() {
        danhSachHoSoBenhNhan = new ArrayList<>();
        vaTuConTroller = new ArrayList<>();
        benhNhanConTroller = new ArrayList<>();
        bacSiController = new ArrayList<>();
        thuocController = new ArrayList<>();
        subThuoc = new ArrayList<>();
        loadFromFile_benhnhan(file_benhNhan);
        loadFromFile_bacsi(file_bacSi);
        loadFromFile_vattu(file_vatTu);
        loadFromFile_thuoc(file_thuoc);
        loadFromFile();
    }

    public List<Thuoc> getThuocController() {
        return thuocController;
    }

    public List<HoSoBenhNhan> getDanhSachHoSoBenhNhan() {
        return danhSachHoSoBenhNhan;
    }

    public List<BenhNhan> getBenhNhanConTroller() {
        return benhNhanConTroller;
    }

    public List<VatTu> getVaTuConTroller() {
        return vaTuConTroller;
    }

    public List<BacSi> getBacSiController() {
        return bacSiController;
    }

    public List<Thuoc> getSubThuoc() {
        return subThuoc;
    }

    public void setSubThuoc(List<Thuoc> subThuoc) {
        this.subThuoc = subThuoc;
    }

    public void setDanhSachHoSoBenhNhan(List<HoSoBenhNhan> danhSachHoSoBenhNhan) {
        this.danhSachHoSoBenhNhan = danhSachHoSoBenhNhan;
    }

    public void setBenhNhanConTroller(List<BenhNhan> benhNhanConTroller) {
        this.benhNhanConTroller = benhNhanConTroller;
    }

    public void setVaTuConTroller(List<VatTu> vaTuConTroller) {
        this.vaTuConTroller = vaTuConTroller;
    }

    public void setBacSiController(List<BacSi> bacSiController) {
        this.bacSiController = bacSiController;
    }

    public void loadFromFile_benhnhan(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("#");
                if (parts.length == 6) {
                    String maBenhNhan = parts[0];
                    String tenBenhNhan = parts[1];
                    String gioiTinh = parts[2];
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date ngaySinh = sdf.parse(parts[3]);
                    String queQuan = parts[4];
                    String soDienThoai = parts[5];
                    BenhNhan benhNhan = new BenhNhan(maBenhNhan, tenBenhNhan, gioiTinh, ngaySinh, queQuan, soDienThoai);
                    benhNhanConTroller.add(benhNhan);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            //System.out.println("File not found: " + e.getMessage());
        } catch (ParseException e) {
            //System.out.println("Parse exception: " + e.getMessage());
        }
    }

    public void loadFromFile_bacsi(String fileName) {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("#");
                if (parts.length == 9) {
                    String maBacSy = parts[0];
                    String tenBacSy = parts[1];
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date ngaySinh = sdf.parse(parts[2]);
                    String gioiTinh = parts[3];
                    String queQuan = parts[4];
                    String soDienThoai = parts[5];
                    String chuyenMon = parts[6];
                    String chucVu = parts[7];
                    double luongThang = Double.parseDouble(parts[8].replace(",", "")); // Xóa dấu phẩy nếu có trong giá trị lương

                    BacSi bacsi = new BacSi(maBacSy, tenBacSy, ngaySinh, gioiTinh, queQuan, soDienThoai, chuyenMon, chucVu, luongThang);
                    bacSiController.add(bacsi);
                }
            }
        } catch (FileNotFoundException e) {
            //System.out.println("File not found: " + e.getMessage());
        } catch (ParseException e) {
            //System.out.println("Parse exception: " + e.getMessage());
        }
    }

    public void loadFromFile_vattu(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("#");
                if (parts.length == 7) {
                    String maVatTu = parts[0];
                    String tenVatTu = parts[1];
                    String loai = parts[2];
                    String tinhTrang = parts[3];
                    double giaNhap = Double.parseDouble(parts[4]);
                    Date ngayNhap = sdf.parse(parts[5]);
                    int soLuong = Integer.parseInt(parts[6]);

                    // Tạo đối tượng VatTu và thêm vào danh sách
                    VatTu vatTu = new VatTu(maVatTu, tenVatTu, loai, tinhTrang, giaNhap, ngayNhap, soLuong);
                    vaTuConTroller.add(vatTu);
                } else {
                    //System.out.println("Error: Invalid data format in file");
                }
            }
        } catch (IOException e) {
            //System.out.println("File not found: " + e.getMessage());
        } catch (ParseException e) {
            //System.out.println("Parse exception: " + e.getMessage());
        } catch (NumberFormatException e) {
            //System.out.println("Number format exception: " + e.getMessage());
        }
    }

    public void loadFromFile_thuoc(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("#");
                if (parts.length == 4) {
                    String maBenhAn = parts[0];
                    String maVatTu = parts[1];
                    String thuocStr = parts[2];
                    String giaTienStr = parts[3];
                    double giaTien = Double.parseDouble(giaTienStr);
                    try {
                        int soLuong = Integer.parseInt(thuocStr);
                        // Tạo đối tượng Thuoc và thêm vào danh sách
                        Thuoc thuoc = new Thuoc(maBenhAn, maVatTu, soLuong, giaTien);
                        thuocController.add(thuoc);
                    } catch (NumberFormatException e) {
                        System.out.println("Định dạng dữ không hợp lệ: " + thuocStr);
                    }
                } else {
                }
            }
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public String findTenBenhNhanByMaBenhNhan(String maBenhNhan) {
        String filePath = "src/mynewteeth/backend/data_repository/local_data/raw_data/BenhNhan.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("#");
                if (parts.length >= 2 && parts[0].equals(maBenhNhan)) {
                    return parts[1]; // Trả về tên Bệnh Nhân nếu mã trùng khớp
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HoSoBenhNhan findHoSoBenhNhanByMa(String maHoSoBenhNhan) {
        for (HoSoBenhNhan hoSo : danhSachHoSoBenhNhan) {
            if (hoSo.getMaHoSoBenhNhan().equals(maHoSoBenhNhan)) {
                return hoSo;
            }
        }
        return null;
    }

    public String findTenBacSiByMa(String maBacSi) {
        String filePath = "src/mynewteeth/backend/data_repository/local_data/raw_data/BacSi.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("#");
                if (parts.length >= 2 && parts[0].equals(maBacSi)) {
                    return parts[1]; // Trả về tên Bác sĩ nếu mã trùng khớp
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy mã Bác sĩ
    }

    public BenhNhan findBenhNhanByMa(String maBenhNhan) {
        BenhNhan x = new BenhNhan();
        for (BenhNhan bn : benhNhanConTroller) {
            if (bn.getMaBenhNhan().equals(maBenhNhan)) {
                x = bn;
            }
        }
        return x;
    }

    public BacSi findBacSiByMa(String maBacSi) {
        BacSi x = new BacSi();
        for (BacSi bs : bacSiController) {
            if (bs.getMaBacSi().equals(maBacSi)) {
                x = bs;
            }
        }
        return x;
    }

    public void maHoSoBenhNhanFomat(String maBenhAn) throws InvalidMaHoSoBenhNhanException {
        if (!maBenhAn.matches("^BA\\d{3}$")) {
            throw new InvalidMaHoSoBenhNhanException("Định dang mã hồ sơ bị sai (VD: BA001)");
        }
    }

    public void maHoSoBenhNhanExist(String maBenhan) throws InvalidMaHoSoBenhNhanException {
        for (HoSoBenhNhan c : danhSachHoSoBenhNhan) {
            if (c.getMaHoSoBenhNhan().equals(maBenhan)) {
                throw new InvalidMaHoSoBenhNhanException("Hồ sơ bệnh nhân đã tồn tại, vui lòng nhập lại");
            }
        }
    }

    public void maBenhNhan_BacSiExist(String maBenhAn, String maBacSi, String maBenhNhan) throws InvalidMaHoSoBenhNhanException {
        boolean found = false;

        for (HoSoBenhNhan x : danhSachHoSoBenhNhan) {
            if (x.getBenhNhan().getMaBenhNhan().equals(maBenhNhan) && x.getBacSi().getMaBacSi().equals(maBacSi)) {
                found = true;
                // Kiểm tra nếu mã hồ sơ bệnh nhân đã thay đổi
                if (!x.getMaHoSoBenhNhan().equals(maBenhAn)) {
                    throw new InvalidMaHoSoBenhNhanException("Không được thay đổi mã hồ sơ bệnh nhân!");
                }
                break;
            }
        }
    }

    public boolean themHoSoBenhNhanBenhNhan(String maHoSoBenhNhan, String ngayKham, String trieuChung, String chanDoan, String tenBacSi, String maBacSi, String ghiChu, String ngayTaiKham, String maBenhNhan, List<VatTu> vt) {
        try {
            maHoSoBenhNhanFomat(maHoSoBenhNhan);
            maHoSoBenhNhanExist(maHoSoBenhNhan);
            BacSi bacSi = findBacSiByMa(maBacSi);
            BenhNhan benhNhan = findBenhNhanByMa(maBenhNhan);
            List<VatTu> listVatTu = vt;

            // Định dạng ngày tháng
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date ngayKhamDate = null;
            Date ngayTaiKhamDate = null;

            try {
                if (ngayKham != null && !ngayKham.isEmpty()) {
                    ngayKhamDate = sdf.parse(ngayKham);
                }
                if (ngayTaiKham != null && !ngayTaiKham.isEmpty()) {
                    ngayTaiKhamDate = sdf.parse(ngayTaiKham);
                }
            } catch (ParseException e) {
                System.out.println("Lỗi định dạng ngày tháng: " + e.getMessage());
            }

            HoSoBenhNhan h = new HoSoBenhNhan(maHoSoBenhNhan, benhNhan, bacSi, ngayKhamDate,
                    trieuChung, chanDoan, "", ngayTaiKhamDate, ghiChu, 0, listVatTu);

            danhSachHoSoBenhNhan.add(h);
            return true;
        } catch (InvalidMaHoSoBenhNhanException e) {
            javax.swing.JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    // Hàm thêm thông tin vào file HoSoBenhNhan.txt
    public void saveToHoSoBenhNhanFile(String maHoSoBenhNhan, String ngayKham, String trieuChung, String chanDoan, String tenBacSi, String maBacSi, String ghiChu, String ngayTaiKham, String maBenhNhan) {
        try {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file_BenhAn, true))) {
                // Ghi thông tin vào file
                writer.write(maHoSoBenhNhan + "#" + ngayKham + "#" + trieuChung + "#" + chanDoan + "#" + tenBacSi + "#" + maBacSi + "#" + ghiChu + "#" + ngayTaiKham + "#" + maBenhNhan);
                writer.newLine();

            } catch (IOException e) {
                // Xử lý nếu có lỗi khi ghi vào file
                //JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi khi ghi vào file HoSoBenhNhan.txt: " + e.getMessage());

            }
        } catch (Exception ex) {
            //javax.swing.JOptionPane.showMessageDialog(null, ex.getMessage());

        }
    }

    public void saveToThuocFile(List<Thuoc> thuocList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file_thuoc, false))) {
            // Ghi thông tin vào file
            for (Thuoc t : thuocList) {
                VatTu vt = findVatTuByMa(t.getMaVatTu());
                if (vt != null) {
                    writer.write(t.getMaBenhAn() + "#" + t.getMaVatTu() + "#" + t.getSoLuong() + "#" + t.getGiaTien());
                    writer.newLine();
                }
            }
            subThuoc.clear();
        } catch (IOException e) {
            // Xử lý nếu có lỗi khi ghi vào file
            //JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi khi ghi vào file: " + e.getMessage());
            System.err.println("Đã xảy ra lỗi khi ghi vào file: " + e.getMessage());
        } catch (Exception ex) {
            //javax.swing.JOptionPane.showMessageDialog(null, ex.getMessage());
            System.err.println("Lỗi không xác định: " + ex.getMessage());
        }
    }

    public void insertToThuocFile(List<Thuoc> thuocList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file_thuoc, true))) {
            // Ghi thông tin vào file
            for (Thuoc t : thuocList) {
                VatTu vt = findVatTuByMa(t.getMaVatTu());
                if (vt != null) {
                    writer.write(t.getMaBenhAn() + "#" + t.getMaVatTu() + "#" + t.getSoLuong() + "#" + t.getGiaTien());
                    writer.newLine();
                }
            }
            subThuoc.clear();
        } catch (IOException e) {
            System.err.println("Đã xảy ra lỗi khi ghi vào file: " + e.getMessage());
        } catch (Exception ex) {
            System.err.println("Lỗi không xác định: " + ex.getMessage());
        }
    }

    public void themThuoc(String maHoSo, List<VatTu> VatTu) {
        for (VatTu vt : VatTu) {
            Thuoc thuoc = new Thuoc(maHoSo, vt.getMaVatTu(), vt.getSoLuong(), vt.getGiaNhap());
            subThuoc.add(thuoc);
            thuocController.add(thuoc);
        }
    }

    public void suaThuoc(String maHoSo, List<VatTu> VatTu) {
        for (VatTu vt : VatTu) {
            Thuoc thuoc = new Thuoc(maHoSo, vt.getMaVatTu(), vt.getSoLuong(), vt.getGiaNhap());
            subThuoc.add(thuoc);
        }
    }

    public void xoaThuoc(String maHoSo) {
        // Xóa các thuốc liên quan đến hồ sơ bệnh nhân
        Iterator<Thuoc> iterator = thuocController.iterator();
        while (iterator.hasNext()) {
            Thuoc thuoc = iterator.next();
            if (thuoc.getMaBenhAn().equals(maHoSo)) {
                iterator.remove();
            }
        }

        // Xóa hồ sơ bệnh nhân
        Iterator<HoSoBenhNhan> hsIterator = danhSachHoSoBenhNhan.iterator();
        while (hsIterator.hasNext()) {
            HoSoBenhNhan hs = hsIterator.next();
            if (hs.getMaHoSoBenhNhan().equals(maHoSo)) {
                hsIterator.remove();
            }
        }
    }

    public void _xoaThuoc(String maHoSo) {
        Iterator<Thuoc> iterator = thuocController.iterator();
        while (iterator.hasNext()) {
            Thuoc thuoc = iterator.next();
            if (thuoc.getMaBenhAn().equals(maHoSo)) {
                iterator.remove();
            }
        }

    }

    public void loadFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(this.file_BenhAn))) {
            String line;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("#");
                if (parts.length == 9) {
                    String maHoSoBenhNhan = parts[0];
                    Date ngayKham = parts[1].isEmpty() ? null : sdf.parse(parts[1]);
                    String trieuChung = parts[2];
                    String chuanDoanBanDau = parts[3];
                    String tienSuBenh = parts[4];
                    Date ngayTaiKham = parts[7].isEmpty() ? null : sdf.parse(parts[7]);
                    String ghiChuBacSi = parts[6];

                    BenhNhan benhNhan = findBenhNhanByMa(parts[8]);
                    BacSi bacSi = findBacSiByMa(parts[5]);

                    HoSoBenhNhan hoSoBenhNhan = new HoSoBenhNhan(maHoSoBenhNhan, benhNhan, bacSi, ngayKham,
                            trieuChung, chuanDoanBanDau, tienSuBenh, ngayTaiKham, ghiChuBacSi, 0, vaTuConTroller);
                    danhSachHoSoBenhNhan.add(hoSoBenhNhan);
                } else {
                    //System.out.println("Error: Invalid data format in file");
                }
            }
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (ParseException e) {
            System.out.println("Parse exception: " + e.getMessage());
        }
    }

    // Hàm lưu danh sách hồ sơ bệnh nhân vào file
    public void SaveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file_BenhAn))) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (HoSoBenhNhan hoSo : danhSachHoSoBenhNhan) {
                String ngayKham = hoSo.getNgayKham() != null ? sdf.format(hoSo.getNgayKham()) : "";
                String ngayTaiKham = hoSo.getNgayTaiKham() != null ? sdf.format(hoSo.getNgayTaiKham()) : "";
                writer.write(hoSo.getMaHoSoBenhNhan() 
                        + "#" + ngayKham + "#" + hoSo.getTrieuChung() 
                        + "#" + hoSo.getChuanDoanBanDau() + "#" 
                        + hoSo.getBacSi().getHoTen()
                        + "#" + hoSo.getBacSi().getMaBacSi() 
                        + "#" + hoSo.getGhiChuBacSi() + "#" 
                        + ngayTaiKham + "#" 
                        + hoSo.getBenhNhan().getMaBenhNhan());
                writer.newLine();
            }
        } catch (IOException e) {
            //System.out.println("Đã xảy ra lỗi khi ghi vào file HoSoBenhNhan.txt: " + e.getMessage());
        }
    }

    //Thêm thông tin vào file thuốc
    public void SaveToFile_thuoc(int soLuong, double giaTien) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file_thuoc))) {
            for (HoSoBenhNhan hs : danhSachHoSoBenhNhan) {
                for (VatTu vt : hs.getListVatTu()) {
                    writer.write(hs.getMaHoSoBenhNhan() + "#" + vt.getMaVatTu() + "#" + soLuong + "#" + giaTien);
                }

                writer.newLine();
            }
        } catch (IOException e) {
            //System.out.println("Đã xảy ra lỗi khi ghi vào Thuoc.txt: " + e.getMessage());
        }
    }

    public void MaBenhAnExists(String maBenhAn) throws InvalidMaHoSoBenhNhanException {
        for (HoSoBenhNhan bn : danhSachHoSoBenhNhan) {
            if (bn.getMaHoSoBenhNhan().equals(maBenhAn)) {
                throw new InvalidMaHoSoBenhNhanException("Mã hồ sơ bênh nhân đã tồn tại vui lòng nhập lại");
            }
        }
    }

    public boolean removeHoSoBenhNhanByMa(String maHoSoBenhNhan) {
        HoSoBenhNhan hoSoBenhNhanCanXoa = null;
        for (HoSoBenhNhan h : danhSachHoSoBenhNhan) {
            if (h.getMaHoSoBenhNhan().equals(maHoSoBenhNhan)) {
                hoSoBenhNhanCanXoa = h;
                break;
            }
        }
        if (hoSoBenhNhanCanXoa != null) {
            danhSachHoSoBenhNhan.remove(hoSoBenhNhanCanXoa);
            System.out.println("Đã xóa hồ sơ bệnh nhân: " + maHoSoBenhNhan);
            return true;
        } else {
            System.out.println("Không tìm thấy hồ sơ bệnh nhân: " + maHoSoBenhNhan);
            return false;
        }
    }

    public void maBenhNhanIsExist(String maBenhNhan) throws InvalidMaBenhNhanException {
        boolean kt = true;
        for (BenhNhan c : benhNhanConTroller) {
            if (c.getMaBenhNhan().equals(maBenhNhan)) {
                kt = false;
            }
        }
        if (kt) {
            throw new InvalidMaBenhNhanException("Mã bệnh nhân không tồn tại trong danh sách");
        }
    }

    public void maBacSiIsExist(String BacSi) throws InvalidMaBacSiException {
        boolean kt = true;
        for (BacSi a : bacSiController) {
            if (a.getMaBacSi().equals(BacSi)) {
                kt = false;
            }
        }
        if (kt) {
            throw new InvalidMaBacSiException("Mã bác sĩ không tồn tại trong danh sách");
        }
    }

    public void suaHoSoBenhNhan(String maHoSoBenhNhan, String ngayKham, String trieuChung, String chanDoan, String maBacSi, String ghiChu, String ngayTaiKham, String maBenhNhan) {
        // Tìm kiếm hồ sơ bệnh nhân cần sửa
        HoSoBenhNhan hoSoCanSua = findHoSoBenhNhanByMa(maHoSoBenhNhan);
        if (hoSoCanSua != null) {
            // Định dạng ngày tháng
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date ngayKhamDate = null;
            Date ngayTaiKhamDate = null;

            try {
                if (ngayKham != null && !ngayKham.isEmpty()) {
                    ngayKhamDate = sdf.parse(ngayKham);
                }
                if (ngayTaiKham != null && !ngayTaiKham.isEmpty()) {
                    ngayTaiKhamDate = sdf.parse(ngayTaiKham);
                }
            } catch (ParseException e) {
                //System.out.println("Lỗi định dạng ngày tháng: " + e.getMessage());
            }
            // Cập nhật thông tin cho hồ sơ bệnh nhân
            hoSoCanSua.setNgayKham(ngayKhamDate);
            hoSoCanSua.setTrieuChung(trieuChung);
            hoSoCanSua.setChuanDoanBanDau(chanDoan);
            hoSoCanSua.setGhiChuBacSi(ghiChu);
            hoSoCanSua.setNgayTaiKham(ngayTaiKhamDate);

            // Tìm và cập nhật bác sĩ và bệnh nhân nếu cần
            BacSi bacSi = findBacSiByMa(maBacSi);

            hoSoCanSua.setBacSi(bacSi);

            BenhNhan benhNhan = findBenhNhanByMa(maBenhNhan);

            hoSoCanSua.setBenhNhan(benhNhan);

            SaveToFile();
            JOptionPane.showMessageDialog(null, "Đã cập nhật hồ sơ bệnh nhân: " + maHoSoBenhNhan);
            System.out.println("Đã cập nhật hồ sơ bệnh nhân: " + maHoSoBenhNhan);

        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy hồ sơ bệnh nhân: " + maHoSoBenhNhan);
            System.out.println("Không tìm thấy hồ sơ bệnh nhân: " + maHoSoBenhNhan);
        }
    }

    public boolean isMaBenhNhanExist(String maBenhNhan) {
        loadFromFile_benhnhan(file_benhNhan);
        if (benhNhanConTroller.contains(maBenhNhan)) {
            return true;
        }
        return false;
    }

    public VatTu findVatTuByMa(String maVatTu) {
        for (VatTu x : vaTuConTroller) {
            if (x.getMaVatTu().equals(maVatTu)) {
                return x;
            }
        }
        return null;
    }

    public Thuoc findThuocByMa(String maBenhNhan, String maVatTu) {
        Thuoc b = new Thuoc();
        for (BenhNhan bn : benhNhanConTroller) {
            if (bn.equals(maBenhNhan)) {
                for (Thuoc thuoc : thuocController) {
                    if (thuoc.getMaVatTu().equals(maVatTu)) {
                        b = thuoc;
                    }
                }

            }
        }
        return b;
    }
}
