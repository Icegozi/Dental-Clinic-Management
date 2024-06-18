/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mynewteeth.backend.controller;

/**
 *
 * @author Us
 */
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
import mynewteeth.backend.interfaces.InvalidMaBenhNhanException;
import mynewteeth.backend.interfaces.InvalidSoDienThoaiException;
import mynewteeth.backend.interfaces.InvalidTenBenhNhanException;
import mynewteeth.backend.model.BenhNhan;

/**
 *
 * @author PC
 */
public class BenhNhanController {

    private List<BenhNhan> danhSachBenhNhan;

    private String fileName = "src/mynewteeth/backend/data_repository/local_data/raw_data/BenhNhan.txt";
   
    public BenhNhanController() {
        this.danhSachBenhNhan = new ArrayList<>();
        loadFromFile_BenhNhan();
    }

    public void SoDienThoaiExists(String soDienThoai) throws InvalidSoDienThoaiException {
        for (BenhNhan bn : danhSachBenhNhan) {
            if (bn.getSoDienThoai().equals(soDienThoai)) {
                throw new InvalidSoDienThoaiException("Số điện thoại đã tồn tại vui lòng nhập lại");
            }
        }
    }

    public void MaBenhNhanExists(String maBenhNhan) throws InvalidMaBenhNhanException {
        for (BenhNhan bn : danhSachBenhNhan) {
            if (bn.getMaBenhNhan().equals(maBenhNhan)) {
                throw new InvalidMaBenhNhanException("Mã bệnh nhân đã tồn tại vui lòng nhập lại");
            }
        }
    }

    public boolean addBenhNhan(BenhNhan benhNhan) {
        try {
            MaBenhNhanExists(benhNhan.getMaBenhNhan());
            benhNhan.TenBenhNhanNull();
            benhNhan.SoDienThoaiValid();
            SoDienThoaiExists(benhNhan.getSoDienThoai());
            benhNhan.maBenhNhanFomat();
            danhSachBenhNhan.add(benhNhan);
            appendToFile(benhNhan);
            return true;
        } catch (InvalidSoDienThoaiException | InvalidMaBenhNhanException | InvalidTenBenhNhanException e) {
            javax.swing.JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    private void appendToFile(BenhNhan benhNhan) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String ngaySinhStr = sdf.format(benhNhan.getNgaySinh());
            String line = benhNhan.getMaBenhNhan() + "#" + benhNhan.getTenBenhNhan() + "#" + benhNhan.getGioiTinh() + "#"
                    + ngaySinhStr + "#" + benhNhan.getQueQuan() + "#" + benhNhan.getSoDienThoai();
            writer.write(line);
            writer.newLine(); // Xuống dòng để ghi bệnh nhân tiếp theo
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }



    public void loadFromFile_BenhNhan() {
        try {
            File file = new File(this.fileName);
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
                    danhSachBenhNhan.add(benhNhan);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
        } catch (ParseException e) {
            //e.printStackTrace();
        }
    }

 

    private void updateFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) { 
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (BenhNhan benhNhan : danhSachBenhNhan) {
                String ngaySinhStr = sdf.format(benhNhan.getNgaySinh());
                String line = benhNhan.getMaBenhNhan() + "#" + benhNhan.getTenBenhNhan() + "#" + benhNhan.getGioiTinh() + "#"
                        + ngaySinhStr + "#" + benhNhan.getQueQuan() + "#" + benhNhan.getSoDienThoai();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean updateBenhNhan(BenhNhan updatedBenhNhan) {
        for (int i = 0; i < danhSachBenhNhan.size(); i++) {
            if (danhSachBenhNhan.get(i).getMaBenhNhan().equals(updatedBenhNhan.getMaBenhNhan())) {
                danhSachBenhNhan.set(i, updatedBenhNhan);
                updateFile();
                return true;
            }
        }
        return false;
    }

        public boolean removeBenhNhanByMa(String maBenhNhan) {
        boolean found = false;
        for (BenhNhan bn : danhSachBenhNhan) {
            if (bn.getMaBenhNhan().equals(maBenhNhan)) {
                danhSachBenhNhan.remove(bn);
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

   

    public BenhNhan findBenhNhanByMa(String maBenhNhan) {
        BenhNhan newBenhNhan = new BenhNhan();
        for (BenhNhan bn : danhSachBenhNhan) {
            if (bn.getMaBenhNhan().equals(maBenhNhan)) {
                newBenhNhan = bn;
            }
        }
        return newBenhNhan;
    }

    public boolean isPhoneNumberDuplicate(String phoneNumber, String maBN) {
        for (BenhNhan bn : danhSachBenhNhan) {
            if (!bn.getMaBenhNhan().equals(maBN) && bn.getSoDienThoai().equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }

    public List<BenhNhan> getDanhSachBenhNhan() {
        return danhSachBenhNhan;
    }
}

