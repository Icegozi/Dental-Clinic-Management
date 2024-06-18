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

/**
 *
 * @author Us
 */
public class BacSiController {

    private List<BacSi> danhSachBacSi;

    public List<BacSi> getDanhSachBacSi() {
        return danhSachBacSi;
    }

    public BacSiController() {
        this.danhSachBacSi = new ArrayList<>(); // Khởi tạo danhSachBacSi

    }

    public List<BacSi> getDanhSachBacSiSafe() {
        try {
            return getDanhSachBacSi();
        } catch (Exception ex) {
            ex.printStackTrace(); // Or handle the exception as needed
            return new ArrayList<>(); // Return an empty list if an exception occurs
        }
    }

    public void setDanhSachBacSi(List<BacSi> danhSachBacSi) {
        this.danhSachBacSi = danhSachBacSi;
    }

    public BacSiController(List<BacSi> danhSachBacSi) {
        this.danhSachBacSi = danhSachBacSi;
    }

    public void MaBacSiExists(String maBacSi) throws Exception {
        // Kiểm tra mã vật tư theo regex
        if (!maBacSi.matches("^BS\\d+$")) {
            throw new Exception("Mã bác sĩ không hợp lệ. Phải có định dạng 'BS' + số thứ tự mã.");
        }

        for (BacSi vt : danhSachBacSi) {
            if (vt.getMaBacSi().equals(maBacSi)) {
                throw new Exception("Mã bác sĩ đã tồn tại, vui lòng nhập lại.");
            }
        }
    }

    public boolean addBacSi(BacSi bacSi) {
        try {
            MaBacSiExists(bacSi.getMaBacSi());
            danhSachBacSi.add(bacSi);
            appendToFile(bacSi);
            return true;
        } catch (Exception e) {
            // Xử lý ngoại lệ nếu có lỗi xảy ra

            javax.swing.JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi khi thêm bác sĩ: " + e.getMessage());
            return false;
        }
    }

    public void appendToFile(BacSi bacSi) {
        String fileName = "src/mynewteeth/backend/data_repository/local_data/raw_data/BacSi.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");                // check 
            String ngaySinhStr = sdf.format(bacSi.getNgaySinh()); // Định dạng ngày sinh thành chuỗi
            String line = bacSi.getMaBacSi() + "#"
                    + bacSi.getHoTen() + "#"
                    + ngaySinhStr + "#" // Sử dụng chuỗi đã định dạng
                    + bacSi.getGioiTinh() + "#"
                    + bacSi.getQueQuan() + "#"
                    + bacSi.getSoDienThoai() + "#"
                    + bacSi.getChuyenMon() + "#"
                    + bacSi.getChucVu() + "#"
                    + bacSi.getLuongThang();
            writer.write(line);
            writer.newLine(); // Move to the next line for the next item
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile(String fileName) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // check here
        Scanner scanner = null;

        try {
            File file = new File(fileName);
            scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("#");

                if (parts.length == 9) {
                    String maBacSi = parts[0];
                    String hoTen = parts[1];

                    // Kiểm tra định dạng của ngày sinh trước khi parse
                    Date ngaySinh = null;
                    try {
                        ngaySinh = sdf.parse(parts[2]);
                    } catch (ParseException e) {
                        System.out.println("Parse exception for date: " + e.getMessage());
                        continue; // Bỏ qua dòng dữ liệu không hợp lệ
                    }

                    String gioiTinh = parts[3];
                    String queQuan = parts[4];
                    String soDienThoai = parts[5];
                    String chuyenMon = parts[6];
                    String chucVu = parts[7];

                    // Kiểm tra định dạng của lương tháng trước khi parse
                    double luongThang = 0.0;
                    try {
                        luongThang = Double.parseDouble(parts[8]);
                    } catch (NumberFormatException e) {
                        System.out.println("Number format exception for salary: " + e.getMessage());
                        continue; // Bỏ qua dòng dữ liệu không hợp lệ
                    }

                    BacSi bacSi = new BacSi(maBacSi, hoTen, ngaySinh, gioiTinh, queQuan, soDienThoai, chuyenMon, chucVu, luongThang);
                    danhSachBacSi.add(bacSi);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    private void updateFile() {
        String fileName = "src/mynewteeth/backend/data_repository/local_data/raw_data/BacSi.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (BacSi bacSi : danhSachBacSi) {
                String ngaySinhStr = sdf.format(bacSi.getNgaySinh());
                String line = bacSi.getMaBacSi() + "#"
                        + bacSi.getHoTen() + "#"
                        + ngaySinhStr + "#"
                        + bacSi.getGioiTinh() + "#"
                        + bacSi.getQueQuan() + "#"
                        + bacSi.getSoDienThoai() + "#"
                        + bacSi.getChuyenMon() + "#"
                        + bacSi.getChucVu() + "#"
                        + bacSi.getLuongThang();
                writer.write(line);
                writer.newLine(); // Di chuyển đến dòng tiếp theo cho đối tượng tiếp theo
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean removeBacSiByMa(String maBacSi) {
        boolean found = false;
        for (BacSi vt : danhSachBacSi) {
            if (vt.getMaBacSi().equals(maBacSi)) {
                danhSachBacSi.remove(vt);
                found = true;
                break;
            }
        }

        if (found) {
            updateFile();
            return true;
        } else {
            return false;
        }
    }

    public boolean updateBacSi(BacSi updatedBacSi) {
        for (int i = 0; i < danhSachBacSi.size(); i++) {
            if (danhSachBacSi.get(i).getMaBacSi().equals(updatedBacSi.getMaBacSi())) {
                danhSachBacSi.set(i, updatedBacSi);
                updateFile();
                return true;
            }
        }
        return false;
    }
}
