/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mynewteeth.backend.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;
import mynewteeth.backend.model.BenhNhan;
import mynewteeth.backend.model.DichVu;
import mynewteeth.backend.model.DichVuHoaDon;
import mynewteeth.backend.model.HoaDon;
import mynewteeth.backend.model.VatTu;
import mynewteeth.backend.model.VatTuHoaDon;

/**
 *
 * @author Us
 */
public class HoaDonController {

    private static final String HOA_DON_FILE_PATH = "src/mynewteeth/backend/data_repository/local_data/raw_data/HoaDon.txt";
    private static final String BENH_NHAN_FILE_PATH = "src/mynewteeth/backend/data_repository/local_data/raw_data/BenhNhan.txt";
    private static final String VAT_TU_FILE_PATH = "src/mynewteeth/backend/data_repository/local_data/raw_data/VatTu.txt";
    private static final String DICH_VU_FILE_PATH = "src/mynewteeth/backend/data_repository/local_data/raw_data/DichVu.txt";
    private static final String HOA_DON_VAT_TU_FILE_PATH = "src/mynewteeth/backend/data_repository/local_data/raw_data/HoaDonVatTu.txt";
    private static final String HOA_DON_DICH_VU_FILE_PATH = "src/mynewteeth/backend/data_repository/local_data/raw_data/HoaDonDichVu.txt";

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private List<HoaDon> listHoaDon;
    private List<BenhNhan> listBenhNhan;
    private List<VatTu> listVatTu;
    private List<DichVu> listDichVu;
    private CyclicBarrier barrier;

    public HoaDonController() {
        init();
    }

    private void init() {
        listHoaDon = new ArrayList<>();
        listBenhNhan = new ArrayList<>();
        listVatTu = new ArrayList<>();
        listDichVu = new ArrayList<>();
        barrier = new CyclicBarrier(4);
    }

    public void loadData() {
        loadBenhNhanData();
        loadVatTuData();
        loadDichVu();
        loadHoaDonData();
    }

    public List<HoaDon> getListHoaDon() {
        return listHoaDon;
    }

    public List<BenhNhan> getListBenhNhan() {
        return listBenhNhan;
    }

    public List<VatTu> getListVatTu() {
        return listVatTu;
    }

    public List<DichVu> getListDichVu() {
        return listDichVu;
    }

    // load List of Hoa Don to display on Table of Hoa Don
    private void sortListHoaDonAscOrder(int left, int right) { // sort list with asc order to optimize search
        int i = left, mid = (left + right) / 2, j = right;
        do {
            while (listHoaDon.get(i).getSoHD().compareTo(listHoaDon.get(mid).getSoHD()) < 0) {
                i++;
            }
            while (listHoaDon.get(j).getSoHD().compareTo(listHoaDon.get(mid).getSoHD()) > 0) {
                j--;
            }
            if (i <= j) {
                HoaDon temp = listHoaDon.get(i);
                listHoaDon.set(i, listHoaDon.get(j));
                listHoaDon.set(j, temp);
                i++;
                j--;
            }
        } while (i <= j);
        if (left < j) {
            sortListHoaDonAscOrder(left, j);
        }
        if (i < right) {
            sortListHoaDonAscOrder(i, right);
        }
    }

    private HoaDon previousHoaDon = null;
    private void findHoaDonBySoHoaDon(String soHoaDon) {
        if (previousHoaDon != null && previousHoaDon.getSoHD().equals(soHoaDon)) {
            return;
        }
        int left = 0, right = listHoaDon.size() - 1;
        int mid = (left + right) / 2;
        while (left <= right) {  /// check , lỗi sai phát hiện từ lương ! --------------------------------------------------------------------
            if (listHoaDon.get(mid).getSoHD().equals(soHoaDon)) {
                previousHoaDon = listHoaDon.get(mid);
                return;
            } else if (listHoaDon.get(mid).getSoHD().compareTo(soHoaDon) > 0) {
                right = mid;
            } else {
                left = mid;
            }
            mid = (left + right) / 2;
        }
        previousHoaDon = null;
    }

    // Load file HoaDon-VatTu
    private VatTu findVatTuByMa(String maVatTu) {
        for (VatTu vatTu : listVatTu) {
            if (vatTu.getMaVatTu().equals(maVatTu)) {
                return vatTu;
            }
        }
        return null;
    }

    private void loadFileVatTuHoaDon(BufferedReader reader) throws IOException {
        String[] vatTuHoaDonInfo;
        String line;
        while ((line = reader.readLine()) != null) {
            vatTuHoaDonInfo = line.split("#");
            if(previousHoaDon == null) {
                findHoaDonBySoHoaDon(vatTuHoaDonInfo[0]);
            }
            if(!previousHoaDon.getSoHD().equals(vatTuHoaDonInfo[0])) {
                findHoaDonBySoHoaDon(vatTuHoaDonInfo[0]);
            }
            VatTu vatTu = findVatTuByMa(vatTuHoaDonInfo[1]);
            previousHoaDon.getListVatTu().add(new VatTuHoaDon(vatTu, Integer.parseInt(vatTuHoaDonInfo[2]), Double.parseDouble(vatTuHoaDonInfo[3])));
        }
    }

    // Load file HoaDon-DichVu
    private DichVu findDichVuByMa(String maDichVu) {
        for (DichVu dichVu : listDichVu) {
            if (dichVu.getMaDichVu().equals(maDichVu)) {
                return dichVu;
            }
        }
        return null;
    }

    private void loadFileDichVuHoaDon(BufferedReader reader) throws IOException {
        String[] dichVuHoaDonInfo;
        String line;
        while ((line = reader.readLine()) != null) {
            dichVuHoaDonInfo = line.split("#");
            if(!previousHoaDon.getSoHD().equals(dichVuHoaDonInfo[0])) {
                findHoaDonBySoHoaDon(dichVuHoaDonInfo[0]);
            }
            DichVu dichVu = findDichVuByMa(dichVuHoaDonInfo[1]);
            previousHoaDon.getListDichVu().add(new DichVuHoaDon(dichVu, Integer.parseInt(dichVuHoaDonInfo[2]),
                    Double.parseDouble(dichVuHoaDonInfo[3])));
        }
    }

    private HoaDon convertIntoHoaDon(String[] info) throws ParseException {
        String soHD = info[0];
        Date ngayLap = formatter.parse(info[1]);
        BenhNhan benhNhan = convertIntoBenhNhan(new String[]{info[2], info[3], info[4], info[5], info[6], info[7]});
        double tongTien = Double.parseDouble(info[8]);
        return new HoaDon(soHD, ngayLap, benhNhan, new ArrayList<>(), new ArrayList<>(), tongTien); 
    }

    private void loadHoaDonData() {
        try {
            barrier.await();
            FileInputStream fileInput = new FileInputStream(HOA_DON_FILE_PATH);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInput));
            String line;
            while ((line = reader.readLine()) != null) {
                listHoaDon.add(convertIntoHoaDon(line.split("#")));
            }
            reader.close();
            fileInput.close();
            // optimize searching
            sortListHoaDonAscOrder(0, listHoaDon.size() - 1);
            // load VatTu-HoaDon , then bind to HoaDon
            fileInput = new FileInputStream(HOA_DON_VAT_TU_FILE_PATH);
            reader = new BufferedReader(new InputStreamReader(fileInput));
            loadFileVatTuHoaDon(reader);
            reader.close();
            fileInput.close();
            // load VatTu-DichVu, then bind to HoaDon
            fileInput = new FileInputStream(HOA_DON_DICH_VU_FILE_PATH);
            reader = new BufferedReader(new InputStreamReader(fileInput));
            loadFileDichVuHoaDon(reader);
            reader.close();
            fileInput.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HoaDonController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ParseException | InterruptedException | BrokenBarrierException ex) {
            Logger.getLogger(HoaDonController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // load list of VatTu
    private VatTu convertIntoVatTu(String[] info) throws ParseException {
        return new VatTu(info[0], info[1], info[2], info[3], Double.parseDouble(info[4]),
                (new SimpleDateFormat("yyyy-MM-dd")).parse(info[5]), Integer.parseInt(info[6]));
    }

    private void loadVatTuData() {
        new Thread(() -> {
            try {
                FileInputStream fileInput = new FileInputStream(VAT_TU_FILE_PATH);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fileInput));
                String line;
                while ((line = reader.readLine()) != null) {
                    listVatTu.add(convertIntoVatTu(line.split("#")));
                }
                reader.close();
                fileInput.close();
                barrier.await();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(HoaDonController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException | ParseException | InterruptedException | BrokenBarrierException ex) {
                Logger.getLogger(HoaDonController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }

    // load list of DichVu 
    private DichVu convertIntoDichVu(String[] info) {
        return new DichVu(info[0], info[1], Double.parseDouble(info[2]));
    }

    private void loadDichVu() {
        new Thread(() -> {
            try {
                FileInputStream fileInput = new FileInputStream(DICH_VU_FILE_PATH);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fileInput));
                String line;
                while ((line = reader.readLine()) != null) {
                    listDichVu.add(convertIntoDichVu(line.split("#")));
                }
                reader.close();
                fileInput.close();
                barrier.await();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(HoaDonController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException | InterruptedException | BrokenBarrierException ex) {
                Logger.getLogger(HoaDonController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }

    // load list of BenhNhan 
    private BenhNhan convertIntoBenhNhan(String[] info) throws ParseException {
        return new BenhNhan(info[0], info[1], info[2], (new SimpleDateFormat("yyyy-MM-dd")).parse(info[3]), info[4], info[5]);
    }

    private void loadBenhNhanData() {
        new Thread(() -> {
            try {
                FileInputStream fileInput = new FileInputStream(BENH_NHAN_FILE_PATH);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fileInput));
                String line;
                while ((line = reader.readLine()) != null) {
                    listBenhNhan.add(convertIntoBenhNhan(line.split("#")));
                }
                reader.close();
                fileInput.close();
                barrier.await();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(HoaDonController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException | ParseException | InterruptedException | BrokenBarrierException ex) {
                Logger.getLogger(HoaDonController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }
    
    public BenhNhan findBenhNhan(String id, String name) {
        for(BenhNhan patient : listBenhNhan) {
            if(patient.getMaBenhNhan().equalsIgnoreCase(id) || patient.getTenBenhNhan().equalsIgnoreCase(name)) {
                return patient;
            }
        }
        return null;
    }

    private void updateInDichVuHoaDonFile() {
        try {
            FileWriter fileWriter = new FileWriter(HOA_DON_DICH_VU_FILE_PATH);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write("");
            writer.close();
            fileWriter.close();
            fileWriter = new FileWriter(HOA_DON_DICH_VU_FILE_PATH, true);
            writer = new BufferedWriter(fileWriter);
            for (HoaDon bill : listHoaDon) {
                if (bill.getListDichVu().isEmpty()) {
                    continue;
                }
                for (DichVuHoaDon service : bill.getListDichVu()) {
                    writer.append(bill.getSoHD() + "#" + service.getDichVu().getMaDichVu() + "#" + String.valueOf(service.getSoLuong()) + "#"
                            + service.getGiaBanThuc());
                    writer.newLine();
                }
            }
            writer.close();
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(HoaDonController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateInVatTuHoaDonFile() {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(HOA_DON_VAT_TU_FILE_PATH);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write("");
            writer.close();
            fileWriter.close();
            fileWriter = new FileWriter(HOA_DON_VAT_TU_FILE_PATH, true);
            writer = new BufferedWriter(fileWriter);
            for (HoaDon bill : listHoaDon) {
                if (bill.getListVatTu().isEmpty()) {
                    continue;
                }
                for (VatTuHoaDon material : bill.getListVatTu()) {
                    writer.append(bill.getSoHD() + "#" + material.getVatTu().getMaVatTu() + "#"
                            + material.getSoLuong() + "#" + material.getDonGia());
                    writer.newLine();
                }
            }
            writer.close();
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(HoaDonController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private void updateBillBaseInfo() {
        try {
            SimpleDateFormat secondFormatter = new SimpleDateFormat("yyyy-MM-dd");
            FileWriter fileWriter = new FileWriter(HOA_DON_FILE_PATH);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write("");
            writer.close();
            fileWriter.close();
            fileWriter = new FileWriter(HOA_DON_FILE_PATH, true);
            writer = new BufferedWriter(fileWriter);
            for(HoaDon bill : listHoaDon) {
                if(bill == null || bill.getBenhNhan() == null || bill.getBenhNhan().getNgaySinh() == null) {
                    System.out.println("null");
                    return;
                }
                writer.append(bill.getSoHD() + "#" + formatter.format(bill.getNgayLap()) + "#" + bill.getBenhNhan().getMaBenhNhan()
                + "#" + bill.getBenhNhan().getTenBenhNhan() + "#" + bill.getBenhNhan().getGioiTinh() + "#" + secondFormatter.format(bill.getBenhNhan().getNgaySinh())
                + "#" + bill.getBenhNhan().getQueQuan() + "#" + bill.getBenhNhan().getSoDienThoai() + "#" + bill.getTongTien());
                writer.newLine();
            }
            writer.close();
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(HoaDonController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateBill(int index, HoaDon updatedHoaDon) {
        if (index < 0) {
            System.out.println("Something went wrong !");
            return;
        }
        listHoaDon.set(index, updatedHoaDon);
        // update thong tin co ban cua hoa don !
        new Thread(() -> {
            updateBillBaseInfo();
        }).start();
        // update dịch vụ 
        new Thread(() -> {
            updateInDichVuHoaDonFile();
        }).start();
        // update vat tu
        new Thread(() -> {
            updateInVatTuHoaDonFile();
        }).start();
    }
}
