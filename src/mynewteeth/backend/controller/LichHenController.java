/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mynewteeth.backend.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import mynewteeth.backend.model.BacSi;
import mynewteeth.backend.model.BenhNhan;
import mynewteeth.backend.model.LichHen;
import mynewteeth.backend.model.TaiKhoan;

/**
 *
 * @author Us
 */
public class LichHenController {
    private List<LichHen> dsLichHen;
    private List<BenhNhan> dsBenhNhan;
    private List<BacSi> dsBacSi;
    private List<TaiKhoan> dsTaiKhoan;
    public String file_name_lich_hen = "src/mynewteeth/backend/data_repository/local_data/raw_data/LichHen.txt";
    public String file_name_benh_nhan = "src/mynewteeth/backend/data_repository/local_data/raw_data/BenhNhan.txt";
    public String file_name_bac_si = "src/mynewteeth/backend/data_repository/local_data/raw_data/BacSi.txt";
    public String file_name_tai_khoan = "src/mynewteeth/backend/data_repository/local_data/raw_data/taikhoan.txt";

    public LichHenController() {
        dsLichHen = new ArrayList<>();
        dsBenhNhan = new ArrayList<>();
        dsBacSi = new ArrayList<>();
        dsTaiKhoan = new ArrayList<>();
        this.loadFromFile_BacSi();
        this.loadFromFile_BenhNhan();
        this.loadFromFile_TaiKhoan();
        this.loadFromFile_LichHen();
    }

    public void loadFromFile_BenhNhan() {
        try {
            File file = new File(this.file_name_benh_nhan);
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
                    dsBenhNhan.add(benhNhan);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile_BacSi() {
        try (Scanner scanner = new Scanner(new File(this.file_name_bac_si))) {
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
                    dsBacSi.add(bacsi);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (ParseException e) {
            System.out.println("Parse exception: " + e.getMessage());
        }
    }

    public void loadFromFile_TaiKhoan() {
        try (Scanner scanner = new Scanner(new File(this.file_name_tai_khoan))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("#");
                if (parts.length == 4) {
                    String tenTaiKhoan = parts[0];
                    String matKhau = parts[1];
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date ngayTao = sdf.parse(parts[2]);
                    int trangThai = Integer.parseInt(parts[3]);
                    TaiKhoan taiKhoan = new TaiKhoan(tenTaiKhoan, matKhau, ngayTao, trangThai);
                    dsTaiKhoan.add(taiKhoan);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (ParseException e) {
            System.out.println("Parse exception: " + e.getMessage());
        }
    }

    public void loadFromFile_LichHen() {
        try (Scanner scanner = new Scanner(new File(this.file_name_lich_hen))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("#");
                if (parts.length == 7) {
                    String maLicHen = parts[0];
                    String tenDichVu = parts[1];
                    String ghiChu = parts[2];
                    String ngayHenStr = parts[3];
                    String maBenhNhan = parts[4];
                    String maBacSi = parts[5];
                    String tenTaiKhoan = parts[6];
                    BenhNhan bn = this.findBenhNhan(maBenhNhan);
                    BacSi bs = this.findBacSi(maBacSi);
                    TaiKhoan tk = this.findTaiKhoan(tenTaiKhoan);
                    SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd");
                    Date ngayHen = sdr.parse(ngayHenStr);
                    if (bn != null && bs != null && tk != null) {
                        LichHen ls = new LichHen(maLicHen, tenDichVu, ghiChu, ngayHen, bn, bs, tk);
                        dsLichHen.add(ls);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (ParseException e) {
            System.out.println("Parse exception: " + e.getMessage());
        }
    }

    public BenhNhan findBenhNhan(String maBenhNhan) {
        for (BenhNhan x : dsBenhNhan) {
            if (x.getMaBenhNhan().equals(maBenhNhan)) {
                return x;
            }
        }
        return null;
    }

    public BacSi findBacSi(String maBacSi) {
        for (BacSi x : dsBacSi) {
            if (x.getMaBacSi().equals(maBacSi)) {
                return x;
            }
        }
        return null;
    }

    public TaiKhoan findTaiKhoan(String tenTaiKhoan) {
        for (TaiKhoan x : dsTaiKhoan) {
            if (x.getAccountName().equals(tenTaiKhoan)) {
                return x;
            }
        }
        return null;
    }

    public void themLichHen(String maLichHen, String tenDichVu, String lyDo, String ngayHenStr, BenhNhan benhNhan, BacSi bacSi, TaiKhoan taiKhoan) {
       
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.file_name_lich_hen, true))) {
            // Chuyển đổi ngày hẹn từ String sang Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date ngayHen = dateFormat.parse(ngayHenStr);
            LichHen newLichHen = new LichHen(maLichHen, tenDichVu, lyDo , ngayHen, benhNhan, bacSi, taiKhoan);
            dsLichHen.add(newLichHen);
            // Ghi thông tin vào file
            String ngayHenFormatted = dateFormat.format(ngayHen);
            writer.write(maLichHen + "#" + tenDichVu + "#" + lyDo + "#" + ngayHenFormatted + "#" + benhNhan.getMaBenhNhan() + "#" + bacSi.getMaBacSi() + "#" + taiKhoan.getAccountName());
            writer.newLine();
        } catch (IOException e) {
            // Xử lý nếu có lỗi khi ghi vào file
            // JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi khi ghi vào file HoSoBenhNhan.txt: " + e.getMessage());
        } catch (Exception e) {
            // Xử lý các lỗi chuyển đổi ngày tháng
            e.printStackTrace();
            // JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi khi chuyển đổi ngày tháng: " + e.getMessage());
        }
    }
    
    public void SaveFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.file_name_lich_hen))) {
            // Chuyển đổi ngày hẹn từ String sang Date
            for(LichHen f : this.dsLichHen){
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                // Ghi thông tin vào file
                String ngayHenFormatted = dateFormat.format(f.getNgayHen());
                writer.write(f.getMaLicHen() + "#" + f.getTenDichVu() + "#" + f.getGhiChu() + "#" + ngayHenFormatted + "#" + f.getBenhNhan().getMaBenhNhan() + "#" + f.getBacSi().getMaBacSi() + "#" + f.getTaiKhoan().getAccountName());
                writer.newLine();
            }
        } catch (IOException e) {
            // Xử lý nếu có lỗi khi ghi vào file
            // JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi khi ghi vào file HoSoBenhNhan.txt: " + e.getMessage());
        } catch (Exception e) {
            // Xử lý các lỗi chuyển đổi ngày tháng
            e.printStackTrace();
            // JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi khi chuyển đổi ngày tháng: " + e.getMessage());
        }
    }

    public List<LichHen> getDsLichHen() {
        return dsLichHen;
    }

    public List<BenhNhan> getDsBenhNhan() {
        return dsBenhNhan;
    }

    public List<BacSi> getDsBacSi() {
        return dsBacSi;
    }

    public List<TaiKhoan> getDsTaiKhoan() {
        return dsTaiKhoan;
    }

    public void setDsLichHen(List<LichHen> dsLichHen) {
        this.dsLichHen = dsLichHen;
    }
}
