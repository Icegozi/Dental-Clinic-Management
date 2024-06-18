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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mynewteeth.backend.model.VatTu;

/**
 *
 * @author Us
 */
public class VatTuController {

    private static final String VAT_TU_PATH = "src/mynewteeth/backend/data_repository/local_data/raw_data/VatTu.txt";
    private List<VatTu> listVatTu;

    public VatTuController() {
        init();
    }

    private void init() {
        listVatTu = new ArrayList<>();
    }

    public List<VatTu> getListVatTu() {
        return listVatTu;
    }

    public void setListVatTu(List<VatTu> listVatTu) {
        this.listVatTu = listVatTu;
    }

    private VatTu convertIntoVatTu(String[] info) {
        try {
            return new VatTu(info[0], info[1], info[2], info[3], Double.parseDouble(info[4]),
                    new SimpleDateFormat("yyyy-MM-dd").parse(info[5]), Integer.parseInt(info[6]));
        } catch (ParseException ex) {
            return null;
        }
    }

    public void loadData() {
        try {
            FileInputStream fileInputStream = new FileInputStream(VAT_TU_PATH);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                listVatTu.add(convertIntoVatTu(line.split("#")));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VatTuController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VatTuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addMaterial(VatTu addedMaterial) {
        listVatTu.add(addedMaterial);
        // write to file 
        new Thread(() -> {
            try {
                FileWriter fileWriter = new FileWriter(VAT_TU_PATH, true);
                BufferedWriter writer = new BufferedWriter(fileWriter);
                writer.newLine();
                writer.append(addedMaterial.convertToFileFormat());
                writer.close();
                fileWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(VatTuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }

    public void update() {
        new Thread(() -> {
            try {
                // open and write empty content to file
                FileWriter fileWriter = new FileWriter(VAT_TU_PATH);
                BufferedWriter writer = new BufferedWriter(fileWriter);
                writer.write("");
                writer.close();
                fileWriter.close();
                fileWriter = new FileWriter(VAT_TU_PATH, true);
                writer = new BufferedWriter(fileWriter);
                System.out.println(listVatTu.size());
                for(int i = 0; i < listVatTu.size(); i++) {
                    writer.append(listVatTu.get(i).convertToFileFormat());
                    if(i != listVatTu.size() - 1) {
                        writer.newLine();
                    }
                }
                writer.close();
                fileWriter.close();
                //System.out.println(listVatTu.size());
            } catch (IOException ex) {
                Logger.getLogger(VatTuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }

}
