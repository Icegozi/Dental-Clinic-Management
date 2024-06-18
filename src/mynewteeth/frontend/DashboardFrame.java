/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package mynewteeth.frontend;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mynewteeth.frontend.dashboard_sub_class.TabBacSi;
import mynewteeth.frontend.dashboard_sub_class.TabBenhNhan;
import mynewteeth.frontend.dashboard_sub_class.TabHoSoBenhNhan;
import mynewteeth.frontend.dashboard_sub_class.TabHoaDon;
import mynewteeth.frontend.dashboard_sub_class.TabLichHen;
import mynewteeth.frontend.dashboard_sub_class.TabQuanLyNhapXuat;
import mynewteeth.frontend.dashboard_sub_class.TabTaiKhoan;
import mynewteeth.frontend.dashboard_sub_class.TabVatTu;

/**
 *
 * @author Us
 */
public class DashboardFrame extends javax.swing.JFrame {

    /**
     * Creates new form DashboardFrame
     */
    private TabBenhNhan tabBenhNhan;
    private TabHoSoBenhNhan tabHoSoBenhNhan;
    private TabBacSi tabBacSi;
    private TabHoaDon tabHoaDon;
    private TabVatTu tabVatTu;
    private TabQuanLyNhapXuat tabQuanLyNhapXuat;
    private TabLichHen tabLichHen;
    private TabTaiKhoan tabTaiKhoan;

    public DashboardFrame() {
        initComponents();
        // my custom
        initTabBenhNhan();
        initHoSoBNTab();
        initTabBacSi();
        initTabHoaDon();
        initTabVatTu();
        initTabQuanLyNhapXuat();
        initTabLichHen();
        initTabTaiKhoan();
    }

    private void initTabBenhNhan() {
        tabBenhNhan = new TabBenhNhan(benhNhanTable, maBNTextField, tenBNTextField,
                ngaySinhBNTextField, dienThoaiBNTextField, queQuanBNTextField, gioiTinhBnTextField,
                themBNButton, xoaBNButton, suaBNButton, timKiemButton, (Object updatedData) -> {
                    tabHoSoBenhNhan.updateData(updatedData);
                    tabHoaDon.updateData(updatedData);
                    tabLichHen.updateData(updatedData);
                });
    }

    private void initHoSoBNTab() {
        tabHoSoBenhNhan = new TabHoSoBenhNhan(maBATextField, ngayKhamTextField, trieuChungTextField, chanDoanTextField,
                tenBacSiTextField, maBacSiTextField, ghiChuTextField, ngayTaiKhamTextField, thuocKeDonTable,
                benhAnTable, timKiemBAButton, capNhatBAButton, xoaBAButton, themBAButton,
                maBenhNhanTextField, tenBNLabel, gioiTinhBNLabel, ngaySinhBNLabel, dienThoaiBNLabel);
    }

    private void initTabBacSi() {
        tabBacSi = new TabBacSi(bacSiTable, maBSTextField, tenBSTextField, ngaySinhBSTextField,
                gioiTinhBSTextField, queQuanBSTextField, soDTTextField, chuyenMonTextField,
                chucVuTextField, luongThangTextField, timKiemBtn, sapXepTenBtn, sapXepLuong,
                themBacSiButton, capNhatBSButton, xoaBacSiButton, anhBacSiLabel, (Object updatedData) -> {
                    tabHoSoBenhNhan.updateData(updatedData);
                    tabLichHen.updateData(updatedData);
                });
    }

    private void initTabHoaDon() {
        tabHoaDon = new TabHoaDon(hoaDonTable, soHDTextField, ngayKhamHDTextField, tenBenhNhanComboBox,
                maBenhNhanComboBox, dichVuComboBox, dichVuTable, thuocHDComboBox, thuocHDTable, thanhTienLabel, themHDButton,
                capNhatHDButton, xoaHDButton, timKiemHDButton, boChonButton, donDepButton);
    }

    private void initTabVatTu() {
        tabVatTu = new TabVatTu(vatTuTable, vatTuTextField, tenVatTuTextField, loaiVatTuTextField, tinhTrangVatTuTextField,
                giaNhapVatTuTextField, ngayNhapVatTuTextField, soLuongVatTuTextField, themVTButton, xoaVatTuButton, capNhatVatTuButton,
                timKiemVatTuButton, (Object updatedData) -> {
                    //tabHoSoBenhNhan.updateData(updatedData);  ---------------------------------Disable synchronous update-----------------------------------
                    tabHoaDon.updateData(updatedData);                   
                    //tabQuanLyNhapXuat.updateData(updatedData); ---------------------------------Disable synchronous update-----------------------------------
                });
    }

    private void initTabQuanLyNhapXuat() {
        tabQuanLyNhapXuat = new TabQuanLyNhapXuat(nhapXuatTable, maDonTextField, ngayGDTextField, loaiGDTextField,
                nhaCCTextField, tongTienTextField, vatPhamGDTable, themNhapXuatButton, xoaNhapXuatButton, suaNhapXuatButton);
    }

    private void initTabLichHen() {
        tabLichHen = new TabLichHen(lichHenTable, maLichHenTextField, maBNHenTextField, tenBNHenTextField,
                sdtHenTextField, ngayHenTextField, tenBSHenTextField, maBSHenTetxField, dichVuHenComboBox,
                dichVuHenTable, ghiChuHenTextField, themLichHenButton, xoaLichHenButton, capNhatLichHenButton, timKiemLichHenButton);
    }

    private static final String APP_CONFIG_URI = "src/mynewteeth/backend/configs/appconfig.txt";

    private void initTabTaiKhoan() {
        tabTaiKhoan = new TabTaiKhoan(taiKhoanTable, tiaKhoanTextField, matKhauTextField, ngayTaoTextField,
                doiMKButton, chanTKButton, xoaTKButton, dangXuatButton, themTKButton, () -> {
                    LoginFrame loginFrame = new LoginFrame();
                    loginFrame.setTitle("Login");
                    loginFrame.getContentPane().setBackground(Color.WHITE);
                    loginFrame.setResizable(false);
                    loginFrame.setLocationRelativeTo(null);
                    loginFrame.setVisible(true);
                    new Thread(() -> {
                        try {
                            FileWriter fileWriter = new FileWriter(APP_CONFIG_URI);
                            BufferedWriter writer = new BufferedWriter(fileWriter);
                            writer.write("");
                            writer.close();
                            fileWriter.close();
                        } catch (IOException ex) {
                            Logger.getLogger(DashboardFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }).start();
                    DashboardFrame.this.dispose();
                });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        dashboardTabPane = new javax.swing.JTabbedPane();
        benhNhanPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        benhNhanTable = new javax.swing.JTable();
        thaoTacPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        maBNTextField = new javax.swing.JTextField();
        tenBNTextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        ngaySinhBNTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        dienThoaiBNTextField = new javax.swing.JTextField();
        xoaBNButton = new javax.swing.JButton();
        suaBNButton = new javax.swing.JButton();
        themBNButton = new javax.swing.JButton();
        timKiemButton = new javax.swing.JButton();
        jLabel43 = new javax.swing.JLabel();
        gioiTinhBnTextField = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        queQuanBNTextField = new javax.swing.JTextField();
        hoSoBNPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        ngayKhamTextField = new javax.swing.JTextField();
        trieuChungTextField = new javax.swing.JTextField();
        gioiTinhBNLabel = new javax.swing.JLabel();
        tenBNLabel = new javax.swing.JLabel();
        ngaySinhBNLabel = new javax.swing.JLabel();
        dienThoaiBNLabel = new javax.swing.JLabel();
        maBATextField = new javax.swing.JTextField();
        chanDoanTextField = new javax.swing.JTextField();
        tenBacSiTextField = new javax.swing.JTextField();
        ghiChuTextField = new javax.swing.JTextField();
        ngayTaiKhamTextField = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        thuocKeDonTable = new javax.swing.JTable();
        jLabel20 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        maBacSiTextField = new javax.swing.JTextField();
        maBenhNhanTextField = new javax.swing.JTextField();
        xoaBAButton = new javax.swing.JButton();
        timKiemBAButton = new javax.swing.JButton();
        capNhatBAButton = new javax.swing.JButton();
        themBAButton = new javax.swing.JButton();
        jScrollPane13 = new javax.swing.JScrollPane();
        benhAnTable = new javax.swing.JTable();
        bacSiTabPanel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        bacSiTable = new javax.swing.JTable();
        jLabel25 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        anhBacSiLabel = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        tenBSTextField = new javax.swing.JTextField();
        maBSTextField = new javax.swing.JTextField();
        ngaySinhBSTextField = new javax.swing.JTextField();
        gioiTinhBSTextField = new javax.swing.JTextField();
        queQuanBSTextField = new javax.swing.JTextField();
        soDTTextField = new javax.swing.JTextField();
        chuyenMonTextField = new javax.swing.JTextField();
        chucVuTextField = new javax.swing.JTextField();
        luongThangTextField = new javax.swing.JTextField();
        themBacSiButton = new javax.swing.JButton();
        xoaBacSiButton = new javax.swing.JButton();
        capNhatBSButton = new javax.swing.JButton();
        timKiemBtn = new javax.swing.JButton();
        sapXepLuong = new javax.swing.JButton();
        sapXepTenBtn = new javax.swing.JButton();
        hoaDonTabPane = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        hoaDonTable = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        dichVuComboBox = new javax.swing.JComboBox<>();
        jScrollPane7 = new javax.swing.JScrollPane();
        dichVuTable = new javax.swing.JTable();
        jLabel54 = new javax.swing.JLabel();
        thuocHDComboBox = new javax.swing.JComboBox<>();
        jScrollPane8 = new javax.swing.JScrollPane();
        thuocHDTable = new javax.swing.JTable();
        jLabel55 = new javax.swing.JLabel();
        thanhTienLabel = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        soHDTextField = new javax.swing.JTextField();
        ngayKhamHDTextField = new javax.swing.JTextField();
        capNhatHDButton = new javax.swing.JButton();
        themHDButton = new javax.swing.JButton();
        xoaHDButton = new javax.swing.JButton();
        timKiemHDButton = new javax.swing.JButton();
        tenBenhNhanComboBox = new javax.swing.JComboBox<>();
        maBenhNhanComboBox = new javax.swing.JComboBox<>();
        boChonButton = new javax.swing.JButton();
        donDepButton = new javax.swing.JButton();
        quanLyVatTu = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        vatTuTable = new javax.swing.JTable();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        loaiVatTuTextField = new javax.swing.JTextField();
        vatTuTextField = new javax.swing.JTextField();
        tenVatTuTextField = new javax.swing.JTextField();
        tinhTrangVatTuTextField = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        giaNhapVatTuTextField = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        ngayNhapVatTuTextField = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        soLuongVatTuTextField = new javax.swing.JTextField();
        themVTButton = new javax.swing.JButton();
        capNhatVatTuButton = new javax.swing.JButton();
        timKiemVatTuButton = new javax.swing.JButton();
        xoaVatTuButton = new javax.swing.JButton();
        jLabel69 = new javax.swing.JLabel();
        nhapXuatPane = new javax.swing.JPanel();
        jLabel63 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        nhapXuatTable = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        vatPhamGDTable = new javax.swing.JTable();
        jLabel75 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        maDonTextField = new javax.swing.JTextField();
        ngayGDTextField = new javax.swing.JTextField();
        loaiGDTextField = new javax.swing.JTextField();
        nhaCCTextField = new javax.swing.JTextField();
        tongTienTextField = new javax.swing.JTextField();
        suaNhapXuatButton = new javax.swing.JButton();
        themNhapXuatButton = new javax.swing.JButton();
        xoaNhapXuatButton = new javax.swing.JButton();
        lichHenPane = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        lichHenTable = new javax.swing.JTable();
        jPanel19 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        maLichHenTextField = new javax.swing.JTextField();
        maBNHenTextField = new javax.swing.JTextField();
        tenBNHenTextField = new javax.swing.JTextField();
        sdtHenTextField = new javax.swing.JTextField();
        ngayHenTextField = new javax.swing.JTextField();
        tenBSHenTextField = new javax.swing.JTextField();
        maBSHenTetxField = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        ghiChuHenTextField = new javax.swing.JTextArea();
        jScrollPane14 = new javax.swing.JScrollPane();
        dichVuHenTable = new javax.swing.JTable();
        dichVuHenComboBox = new javax.swing.JComboBox<>();
        jPanel8 = new javax.swing.JPanel();
        xoaLichHenButton = new javax.swing.JButton();
        timKiemLichHenButton = new javax.swing.JButton();
        themLichHenButton = new javax.swing.JButton();
        capNhatLichHenButton = new javax.swing.JButton();
        taiKhoanPane = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        tiaKhoanTextField = new javax.swing.JTextField();
        jLabel77 = new javax.swing.JLabel();
        ngayTaoTextField = new javax.swing.JTextField();
        jLabel78 = new javax.swing.JLabel();
        matKhauTextField = new javax.swing.JTextField();
        xoaTKButton = new javax.swing.JButton();
        chanTKButton = new javax.swing.JButton();
        themTKButton = new javax.swing.JButton();
        dangXuatButton = new javax.swing.JButton();
        doiMKButton = new javax.swing.JButton();
        jScrollPane12 = new javax.swing.JScrollPane();
        taiKhoanTable = new javax.swing.JTable();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 51, 102));
        setResizable(false);
        setSize(new java.awt.Dimension(1366, 900));

        jLabel1.setBackground(new java.awt.Color(153, 204, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Phòng khám răng NEWTEETH");

        dashboardTabPane.setBackground(new java.awt.Color(0, 51, 102));
        dashboardTabPane.setForeground(new java.awt.Color(255, 255, 255));

        benhNhanPanel.setBackground(new java.awt.Color(0, 51, 102));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Danh sách bệnh nhân");

        benhNhanTable.setBackground(new java.awt.Color(231, 237, 243));
        benhNhanTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã bệnh nhân", "Tên bệnh nhân", "Giới tính", "Ngày sinh", "Quê quán", "Điện thoại"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(benhNhanTable);
        if (benhNhanTable.getColumnModel().getColumnCount() > 0) {
            benhNhanTable.getColumnModel().getColumn(0).setResizable(false);
            benhNhanTable.getColumnModel().getColumn(1).setResizable(false);
            benhNhanTable.getColumnModel().getColumn(2).setResizable(false);
            benhNhanTable.getColumnModel().getColumn(3).setResizable(false);
            benhNhanTable.getColumnModel().getColumn(4).setResizable(false);
            benhNhanTable.getColumnModel().getColumn(5).setResizable(false);
        }

        thaoTacPanel.setBackground(new java.awt.Color(231, 237, 243));
        thaoTacPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Mã bệnh nhân :");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Tên bệnh nhân :");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Ngày sinh :");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Điện thoại");

        xoaBNButton.setBackground(new java.awt.Color(153, 204, 255));
        xoaBNButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        xoaBNButton.setText("Xóa bệnh nhân");
        xoaBNButton.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));

        suaBNButton.setBackground(new java.awt.Color(153, 204, 255));
        suaBNButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        suaBNButton.setText("Sửa TT BN");
        suaBNButton.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));

        themBNButton.setBackground(new java.awt.Color(153, 204, 255));
        themBNButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        themBNButton.setText("Thêm BN");
        themBNButton.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));

        timKiemButton.setBackground(new java.awt.Color(153, 204, 255));
        timKiemButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        timKiemButton.setText("Tìm kiếm");
        timKiemButton.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel43.setText("Giới tính :");

        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel44.setText("Quê quán :");

        javax.swing.GroupLayout thaoTacPanelLayout = new javax.swing.GroupLayout(thaoTacPanel);
        thaoTacPanel.setLayout(thaoTacPanelLayout);
        thaoTacPanelLayout.setHorizontalGroup(
            thaoTacPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thaoTacPanelLayout.createSequentialGroup()
                .addGroup(thaoTacPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(thaoTacPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(thaoTacPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tenBNTextField)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(thaoTacPanelLayout.createSequentialGroup()
                                .addComponent(maBNTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(gioiTinhBnTextField))
                            .addGroup(thaoTacPanelLayout.createSequentialGroup()
                                .addComponent(ngaySinhBNTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(dienThoaiBNTextField))
                            .addComponent(queQuanBNTextField)
                            .addGroup(thaoTacPanelLayout.createSequentialGroup()
                                .addGroup(thaoTacPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(thaoTacPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(52, 52, 52)
                                        .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(thaoTacPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 52, Short.MAX_VALUE))))
                    .addGroup(thaoTacPanelLayout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(thaoTacPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(themBNButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(suaBNButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(thaoTacPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(timKiemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(xoaBNButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)))
                .addContainerGap())
        );
        thaoTacPanelLayout.setVerticalGroup(
            thaoTacPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thaoTacPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(thaoTacPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel43))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(thaoTacPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(maBNTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gioiTinhBnTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tenBNTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(thaoTacPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(thaoTacPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ngaySinhBNTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dienThoaiBNTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel44)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(queQuanBNTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addGroup(thaoTacPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(xoaBNButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(themBNButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(thaoTacPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(suaBNButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timKiemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout benhNhanPanelLayout = new javax.swing.GroupLayout(benhNhanPanel);
        benhNhanPanel.setLayout(benhNhanPanelLayout);
        benhNhanPanelLayout.setHorizontalGroup(
            benhNhanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, benhNhanPanelLayout.createSequentialGroup()
                .addGroup(benhNhanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, benhNhanPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(thaoTacPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        benhNhanPanelLayout.setVerticalGroup(
            benhNhanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(benhNhanPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(benhNhanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                    .addComponent(thaoTacPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        dashboardTabPane.addTab("Bệnh nhân", benhNhanPanel);

        jPanel2.setBackground(new java.awt.Color(0, 51, 102));

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 102));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Thông tin bệnh án");

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Mã bệnh nhân : ");

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Tên bệnh nhân : ");

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Giới tính : ");

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Ngày sinh :");

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Điện thoại :");

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Mã bệnh án : ");

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Ngày khám :");

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Triệu chứng :");

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Chuẩn đoán ban đầu : ");

        jLabel17.setText("Bác sĩ :");

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Ghi chú của bác sĩ :");

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Ngày tái khám : ");

        ngayKhamTextField.setBackground(new java.awt.Color(227, 238, 245));

        trieuChungTextField.setBackground(new java.awt.Color(227, 238, 245));

        gioiTinhBNLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        gioiTinhBNLabel.setText("Nam");

        tenBNLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tenBNLabel.setText("Nguyễn Văn A");

        ngaySinhBNLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ngaySinhBNLabel.setText("01-01-2001");

        dienThoaiBNLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        dienThoaiBNLabel.setText("0123456JQK");

        maBATextField.setBackground(new java.awt.Color(227, 238, 245));

        chanDoanTextField.setBackground(new java.awt.Color(227, 238, 245));

        tenBacSiTextField.setBackground(new java.awt.Color(227, 238, 245));
        tenBacSiTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tenBacSiTextFieldActionPerformed(evt);
            }
        });

        ghiChuTextField.setBackground(new java.awt.Color(227, 238, 245));

        ngayTaiKhamTextField.setBackground(new java.awt.Color(227, 238, 245));

        thuocKeDonTable.setBackground(new java.awt.Color(231, 237, 243));
        thuocKeDonTable.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        thuocKeDonTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã thuốc", "Tên thuốc", "Đơn vị", "Số lượng", "Giá tiền (/ Đơn vị)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(thuocKeDonTable);
        if (thuocKeDonTable.getColumnModel().getColumnCount() > 0) {
            thuocKeDonTable.getColumnModel().getColumn(0).setResizable(false);
            thuocKeDonTable.getColumnModel().getColumn(1).setResizable(false);
            thuocKeDonTable.getColumnModel().getColumn(2).setResizable(false);
            thuocKeDonTable.getColumnModel().getColumn(3).setResizable(false);
            thuocKeDonTable.getColumnModel().getColumn(4).setResizable(false);
        }

        jLabel20.setBackground(new java.awt.Color(153, 153, 255));
        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Danh sách thuốc được kê đơn cho bệnh nhân");

        jLabel41.setText("Mã bác sĩ :");

        maBacSiTextField.setBackground(new java.awt.Color(227, 238, 245));

        maBenhNhanTextField.setBackground(new java.awt.Color(227, 238, 245));
        maBenhNhanTextField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        maBenhNhanTextField.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(gioiTinhBNLabel)
                        .addGap(49, 49, 49)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ngaySinhBNLabel)
                        .addGap(73, 73, 73)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dienThoaiBNLabel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18)
                            .addComponent(jLabel19))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ngayTaiKhamTextField)
                            .addComponent(ghiChuTextField)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(tenBacSiTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(maBacSiTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE))))
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(trieuChungTextField)
                            .addComponent(chanDoanTextField)))
                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(maBenhNhanTextField))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(maBATextField, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ngayKhamTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tenBNLabel)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(ngayKhamTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maBATextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(tenBNLabel)
                    .addComponent(maBenhNhanTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(gioiTinhBNLabel)
                    .addComponent(ngaySinhBNLabel)
                    .addComponent(dienThoaiBNLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(trieuChungTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(chanDoanTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(tenBacSiTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41)
                    .addComponent(maBacSiTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(ghiChuTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(ngayTaiKhamTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                .addContainerGap())
        );

        xoaBAButton.setBackground(new java.awt.Color(204, 204, 255));
        xoaBAButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        xoaBAButton.setForeground(new java.awt.Color(0, 51, 102));
        xoaBAButton.setText("Xóa bệnh án");
        xoaBAButton.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(102, 204, 255)));

        timKiemBAButton.setBackground(new java.awt.Color(204, 204, 255));
        timKiemBAButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        timKiemBAButton.setForeground(new java.awt.Color(0, 51, 102));
        timKiemBAButton.setText("Tìm kiếm bệnh án ");
        timKiemBAButton.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(51, 204, 255)));

        capNhatBAButton.setBackground(new java.awt.Color(204, 204, 255));
        capNhatBAButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        capNhatBAButton.setForeground(new java.awt.Color(0, 51, 102));
        capNhatBAButton.setText("Cập nhật bệnh án");
        capNhatBAButton.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(102, 204, 255)));

        themBAButton.setBackground(new java.awt.Color(204, 204, 255));
        themBAButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        themBAButton.setForeground(new java.awt.Color(0, 51, 102));
        themBAButton.setText("Thêm bệnh án");
        themBAButton.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(102, 204, 255)));

        benhAnTable.setBackground(new java.awt.Color(204, 204, 204));
        benhAnTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã bệnh án", "Tên bệnh nhân", "Ngày khám", "Bác sĩ"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        benhAnTable.setSelectionForeground(new java.awt.Color(255, 255, 255));
        benhAnTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        benhAnTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane13.setViewportView(benhAnTable);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(timKiemBAButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
                    .addComponent(xoaBAButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(capNhatBAButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(themBAButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(594, Short.MAX_VALUE)
                    .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(249, 318, Short.MAX_VALUE)
                .addComponent(timKiemBAButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(capNhatBAButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(xoaBAButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(themBAButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(216, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout hoSoBNPanelLayout = new javax.swing.GroupLayout(hoSoBNPanel);
        hoSoBNPanel.setLayout(hoSoBNPanelLayout);
        hoSoBNPanelLayout.setHorizontalGroup(
            hoSoBNPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        hoSoBNPanelLayout.setVerticalGroup(
            hoSoBNPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        dashboardTabPane.addTab("Hồ sơ bệnh nhân", hoSoBNPanel);

        jPanel3.setBackground(new java.awt.Color(0, 51, 102));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));

        bacSiTable.setBackground(new java.awt.Color(235, 240, 248));
        bacSiTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã bác sĩ", "Họ tên", "Ngày sinh", "Giới tính"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        bacSiTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        bacSiTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        bacSiTable.setShowGrid(false);
        jScrollPane3.setViewportView(bacSiTable);
        if (bacSiTable.getColumnModel().getColumnCount() > 0) {
            bacSiTable.getColumnModel().getColumn(0).setResizable(false);
            bacSiTable.getColumnModel().getColumn(1).setResizable(false);
            bacSiTable.getColumnModel().getColumn(2).setResizable(false);
            bacSiTable.getColumnModel().getColumn(3).setResizable(false);
        }

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Danh sách bác sĩ tại NewTeeth");

        jPanel4.setBackground(new java.awt.Color(0, 51, 102));
        jPanel4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 5, 0, 0, new java.awt.Color(255, 255, 255)));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("Thông tin bác sĩ ");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Mã bác sĩ : ");

        anhBacSiLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        anhBacSiLabel.setForeground(new java.awt.Color(255, 255, 255));
        anhBacSiLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("Họ tên : ");

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("Ngày sinh :");

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText("Giới tính :");

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText("Quê quán : ");

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("Số điện thoại :");

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("Chuyên môn : ");

        jLabel39.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("Chức vụ :");

        jLabel40.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("Lương tháng : ");

        tenBSTextField.setBackground(new java.awt.Color(237, 242, 248));

        maBSTextField.setBackground(new java.awt.Color(237, 242, 248));

        ngaySinhBSTextField.setBackground(new java.awt.Color(237, 242, 248));

        gioiTinhBSTextField.setBackground(new java.awt.Color(237, 242, 248));

        queQuanBSTextField.setBackground(new java.awt.Color(237, 242, 248));

        soDTTextField.setBackground(new java.awt.Color(237, 242, 248));

        chuyenMonTextField.setBackground(new java.awt.Color(237, 242, 248));

        chucVuTextField.setBackground(new java.awt.Color(237, 242, 248));

        luongThangTextField.setBackground(new java.awt.Color(237, 242, 248));

        themBacSiButton.setBackground(new java.awt.Color(0, 0, 0));
        themBacSiButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        themBacSiButton.setForeground(new java.awt.Color(255, 255, 255));
        themBacSiButton.setText("Thêm bác sĩ");
        themBacSiButton.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(255, 255, 255)));

        xoaBacSiButton.setBackground(new java.awt.Color(0, 0, 0));
        xoaBacSiButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        xoaBacSiButton.setForeground(new java.awt.Color(255, 255, 255));
        xoaBacSiButton.setText("Xóa bác sĩ này");
        xoaBacSiButton.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(255, 255, 255)));

        capNhatBSButton.setBackground(new java.awt.Color(0, 0, 0));
        capNhatBSButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        capNhatBSButton.setForeground(new java.awt.Color(255, 255, 255));
        capNhatBSButton.setText("Cập nhật bác sĩ");
        capNhatBSButton.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(255, 255, 255)));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel32)
                                    .addComponent(jLabel34)
                                    .addComponent(jLabel35)
                                    .addComponent(jLabel36)
                                    .addComponent(jLabel28)
                                    .addComponent(jLabel37))
                                .addGap(20, 64, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(tenBSTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(maBSTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(ngaySinhBSTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(gioiTinhBSTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(21, 21, 21))
                                    .addComponent(queQuanBSTextField)
                                    .addComponent(soDTTextField, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel38)
                                    .addComponent(jLabel39)
                                    .addComponent(jLabel40))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(chucVuTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(luongThangTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                                        .addComponent(chuyenMonTextField, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(9, 9, 9)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(capNhatBSButton, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(themBacSiButton, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(xoaBacSiButton, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(anhBacSiLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(anhBacSiLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28)
                            .addComponent(maBSTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tenBSTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel32))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel34)
                            .addComponent(ngaySinhBSTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel35)
                            .addComponent(gioiTinhBSTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(queQuanBSTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(soDTTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(chuyenMonTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(chucVuTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(luongThangTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(themBacSiButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(capNhatBSButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(xoaBacSiButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        timKiemBtn.setBackground(new java.awt.Color(0, 0, 0));
        timKiemBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        timKiemBtn.setForeground(new java.awt.Color(153, 204, 255));
        timKiemBtn.setText("Tìm kiếm");
        timKiemBtn.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 204, 255)));

        sapXepLuong.setBackground(new java.awt.Color(0, 0, 102));
        sapXepLuong.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        sapXepLuong.setForeground(new java.awt.Color(204, 204, 255));
        sapXepLuong.setText("Sắp xếp lương");
        sapXepLuong.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 255)));

        sapXepTenBtn.setBackground(new java.awt.Color(0, 0, 102));
        sapXepTenBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        sapXepTenBtn.setForeground(new java.awt.Color(204, 204, 255));
        sapXepTenBtn.setText("Sắp xếp tên");
        sapXepTenBtn.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 255)));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(timKiemBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(sapXepTenBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(sapXepLuong, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sapXepTenBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(timKiemBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sapXepLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout bacSiTabPanelLayout = new javax.swing.GroupLayout(bacSiTabPanel);
        bacSiTabPanel.setLayout(bacSiTabPanelLayout);
        bacSiTabPanelLayout.setHorizontalGroup(
            bacSiTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        bacSiTabPanelLayout.setVerticalGroup(
            bacSiTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        dashboardTabPane.addTab("Bác sĩ", bacSiTabPanel);

        jPanel5.setBackground(new java.awt.Color(0, 51, 102));

        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setText("Danh sách hóa đơn bán hàng");

        hoaDonTable.setBackground(new java.awt.Color(221, 229, 235));
        hoaDonTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Số hóa đơn", "Ngày khám", "Tên bệnh nhân", "Tổng tiền"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        hoaDonTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        hoaDonTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane6.setViewportView(hoaDonTable);
        if (hoaDonTable.getColumnModel().getColumnCount() > 0) {
            hoaDonTable.getColumnModel().getColumn(0).setResizable(false);
            hoaDonTable.getColumnModel().getColumn(1).setResizable(false);
            hoaDonTable.getColumnModel().getColumn(2).setResizable(false);
            hoaDonTable.getColumnModel().getColumn(3).setResizable(false);
        }

        jPanel6.setBackground(new java.awt.Color(153, 204, 255));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));

        jLabel46.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel46.setText("Thông tin hóa đơn");

        jLabel47.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel47.setText("Số hóa đơn :");

        jLabel48.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel48.setText("Ngày khám :");

        jLabel49.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel49.setText("Tên bệnh nhân :");

        jLabel50.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel50.setText("Mã Bn :");

        jLabel53.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel53.setText("Dịch vụ :");

        dichVuComboBox.setBackground(new java.awt.Color(221, 229, 235));
        dichVuComboBox.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        dichVuComboBox.setForeground(new java.awt.Color(0, 51, 102));

        dichVuTable.setBackground(new java.awt.Color(0, 51, 102));
        dichVuTable.setForeground(new java.awt.Color(255, 255, 255));
        dichVuTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Dịch vụ", "Số lượng", "Đơn giá", "Tổng tiền"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        dichVuTable.setSelectionBackground(new java.awt.Color(204, 204, 255));
        jScrollPane7.setViewportView(dichVuTable);
        if (dichVuTable.getColumnModel().getColumnCount() > 0) {
            dichVuTable.getColumnModel().getColumn(0).setResizable(false);
            dichVuTable.getColumnModel().getColumn(1).setResizable(false);
            dichVuTable.getColumnModel().getColumn(2).setResizable(false);
            dichVuTable.getColumnModel().getColumn(3).setResizable(false);
        }

        jLabel54.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel54.setText("Thuốc và vật phẩm y tế : ");

        thuocHDComboBox.setBackground(new java.awt.Color(221, 229, 235));
        thuocHDComboBox.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        thuocHDComboBox.setForeground(new java.awt.Color(0, 51, 102));

        thuocHDTable.setBackground(new java.awt.Color(0, 51, 102));
        thuocHDTable.setForeground(new java.awt.Color(255, 255, 255));
        thuocHDTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã thuốc", "Tên thuốc", "Đơn vị", "Số lượng", "Đơn giá", "Tổng tiền"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        thuocHDTable.setSelectionBackground(new java.awt.Color(204, 204, 255));
        jScrollPane8.setViewportView(thuocHDTable);
        if (thuocHDTable.getColumnModel().getColumnCount() > 0) {
            thuocHDTable.getColumnModel().getColumn(0).setResizable(false);
            thuocHDTable.getColumnModel().getColumn(1).setResizable(false);
            thuocHDTable.getColumnModel().getColumn(2).setResizable(false);
            thuocHDTable.getColumnModel().getColumn(3).setResizable(false);
            thuocHDTable.getColumnModel().getColumn(4).setResizable(false);
            thuocHDTable.getColumnModel().getColumn(5).setResizable(false);
        }

        jLabel55.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel55.setText("Tổng tiền thanh toán :");

        thanhTienLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        thanhTienLabel.setForeground(new java.awt.Color(102, 0, 255));
        thanhTienLabel.setText("100000");

        jLabel57.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel57.setText("VNĐ");

        soHDTextField.setBackground(new java.awt.Color(221, 229, 235));
        soHDTextField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        ngayKhamHDTextField.setBackground(new java.awt.Color(221, 229, 235));
        ngayKhamHDTextField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        capNhatHDButton.setBackground(new java.awt.Color(0, 0, 0));
        capNhatHDButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        capNhatHDButton.setForeground(new java.awt.Color(255, 255, 255));
        capNhatHDButton.setText("Cập nhật HĐ");
        capNhatHDButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));

        themHDButton.setBackground(new java.awt.Color(0, 0, 0));
        themHDButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        themHDButton.setForeground(new java.awt.Color(255, 255, 255));
        themHDButton.setText("Thêm hóa đơn");
        themHDButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));

        xoaHDButton.setBackground(new java.awt.Color(0, 0, 0));
        xoaHDButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        xoaHDButton.setForeground(new java.awt.Color(255, 255, 255));
        xoaHDButton.setText("Xóa hóa đơn");
        xoaHDButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));

        timKiemHDButton.setBackground(new java.awt.Color(0, 0, 0));
        timKiemHDButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        timKiemHDButton.setForeground(new java.awt.Color(255, 255, 255));
        timKiemHDButton.setText("Tìm kiếm HĐ");
        timKiemHDButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));

        tenBenhNhanComboBox.setBackground(new java.awt.Color(0, 51, 102));
        tenBenhNhanComboBox.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tenBenhNhanComboBox.setForeground(new java.awt.Color(255, 255, 255));
        tenBenhNhanComboBox.setMaximumRowCount(100);

        maBenhNhanComboBox.setBackground(new java.awt.Color(0, 51, 102));
        maBenhNhanComboBox.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        maBenhNhanComboBox.setForeground(new java.awt.Color(255, 255, 255));
        maBenhNhanComboBox.setMaximumRowCount(100);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel49)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tenBenhNhanComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel50)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(maBenhNhanComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel53)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dichVuComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel54)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(thuocHDComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel47)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(soHDTextField))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel48)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ngayKhamHDTextField))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel55)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(thanhTienLabel))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(xoaHDButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(themHDButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel57)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addGap(0, 41, Short.MAX_VALUE)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(timKiemHDButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(capNhatHDButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(40, 40, 40)))))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel46)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(soHDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(ngayKhamHDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(jLabel50)
                    .addComponent(tenBenhNhanComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maBenhNhanComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel53)
                    .addComponent(dichVuComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel54)
                    .addComponent(thuocHDComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel55)
                    .addComponent(thanhTienLabel)
                    .addComponent(jLabel57))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(capNhatHDButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(themHDButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(xoaHDButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timKiemHDButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 18, Short.MAX_VALUE))
        );

        boChonButton.setBackground(new java.awt.Color(153, 204, 255));
        boChonButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        boChonButton.setText("Bỏ lựa chọn");
        boChonButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(221, 229, 235), 2));

        donDepButton.setBackground(new java.awt.Color(153, 204, 255));
        donDepButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        donDepButton.setText("Dọn dẹp");
        donDepButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(221, 229, 235), 2));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(boChonButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(90, 90, 90)
                                .addComponent(donDepButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(126, 126, 126))))
                    .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boChonButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(donDepButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout hoaDonTabPaneLayout = new javax.swing.GroupLayout(hoaDonTabPane);
        hoaDonTabPane.setLayout(hoaDonTabPaneLayout);
        hoaDonTabPaneLayout.setHorizontalGroup(
            hoaDonTabPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        hoaDonTabPaneLayout.setVerticalGroup(
            hoaDonTabPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        dashboardTabPane.addTab("Hóa đơn", hoaDonTabPane);

        quanLyVatTu.setBackground(new java.awt.Color(0, 51, 102));

        jLabel51.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(255, 255, 255));
        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setText("Danh sách vật tư ");

        vatTuTable.setBackground(new java.awt.Color(223, 235, 243));
        vatTuTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã vật tư", "Tên vật tư", "Loại", "Tình trạng", "Giá nhập", "Ngày nhập", "Số lượng"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(vatTuTable);
        if (vatTuTable.getColumnModel().getColumnCount() > 0) {
            vatTuTable.getColumnModel().getColumn(0).setResizable(false);
            vatTuTable.getColumnModel().getColumn(1).setResizable(false);
            vatTuTable.getColumnModel().getColumn(2).setResizable(false);
            vatTuTable.getColumnModel().getColumn(3).setResizable(false);
            vatTuTable.getColumnModel().getColumn(4).setResizable(false);
            vatTuTable.getColumnModel().getColumn(5).setResizable(false);
        }

        jLabel58.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(255, 255, 255));
        jLabel58.setText("Loại :");

        jLabel59.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(255, 255, 255));
        jLabel59.setText("Tên vật tư :");

        jLabel61.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(255, 255, 255));
        jLabel61.setText("Tình trạng :");

        loaiVatTuTextField.setBackground(new java.awt.Color(204, 204, 255));
        loaiVatTuTextField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        loaiVatTuTextField.setForeground(new java.awt.Color(0, 51, 102));

        vatTuTextField.setBackground(new java.awt.Color(204, 204, 255));
        vatTuTextField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        vatTuTextField.setForeground(new java.awt.Color(0, 51, 102));

        tenVatTuTextField.setBackground(new java.awt.Color(204, 204, 255));
        tenVatTuTextField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tenVatTuTextField.setForeground(new java.awt.Color(0, 51, 102));

        tinhTrangVatTuTextField.setBackground(new java.awt.Color(204, 204, 255));
        tinhTrangVatTuTextField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tinhTrangVatTuTextField.setForeground(new java.awt.Color(0, 51, 102));

        jLabel52.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(255, 255, 255));
        jLabel52.setText("Giá nhập :");

        giaNhapVatTuTextField.setBackground(new java.awt.Color(204, 204, 255));
        giaNhapVatTuTextField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        giaNhapVatTuTextField.setForeground(new java.awt.Color(0, 51, 102));

        jLabel60.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(255, 255, 255));
        jLabel60.setText("Ngày nhập :");

        ngayNhapVatTuTextField.setBackground(new java.awt.Color(204, 204, 255));
        ngayNhapVatTuTextField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ngayNhapVatTuTextField.setForeground(new java.awt.Color(0, 51, 102));

        jLabel62.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(255, 255, 255));
        jLabel62.setText("Số lượng :");

        soLuongVatTuTextField.setBackground(new java.awt.Color(204, 204, 255));
        soLuongVatTuTextField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        soLuongVatTuTextField.setForeground(new java.awt.Color(0, 51, 102));

        themVTButton.setBackground(new java.awt.Color(225, 232, 239));
        themVTButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        themVTButton.setText("Thêm vật tư");
        themVTButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 204, 255), 2, true));

        capNhatVatTuButton.setBackground(new java.awt.Color(225, 232, 239));
        capNhatVatTuButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        capNhatVatTuButton.setText("Cập nhật TT VT");
        capNhatVatTuButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 204, 255), 2, true));

        timKiemVatTuButton.setBackground(new java.awt.Color(225, 232, 239));
        timKiemVatTuButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        timKiemVatTuButton.setText("Tìm kiếm vật tư");
        timKiemVatTuButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 204, 255), 2, true));

        xoaVatTuButton.setBackground(new java.awt.Color(225, 232, 239));
        xoaVatTuButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        xoaVatTuButton.setText("Xóa vật tư");
        xoaVatTuButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 204, 255), 2, true));

        jLabel69.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(255, 255, 255));
        jLabel69.setText("Mã vật tư :");

        javax.swing.GroupLayout quanLyVatTuLayout = new javax.swing.GroupLayout(quanLyVatTu);
        quanLyVatTu.setLayout(quanLyVatTuLayout);
        quanLyVatTuLayout.setHorizontalGroup(
            quanLyVatTuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(quanLyVatTuLayout.createSequentialGroup()
                .addGroup(quanLyVatTuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(quanLyVatTuLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane5))
                    .addGroup(quanLyVatTuLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(quanLyVatTuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(quanLyVatTuLayout.createSequentialGroup()
                                .addComponent(jLabel69)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                                .addComponent(vatTuTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(quanLyVatTuLayout.createSequentialGroup()
                                .addComponent(jLabel58)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(loaiVatTuTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(quanLyVatTuLayout.createSequentialGroup()
                                .addGroup(quanLyVatTuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel52)
                                    .addComponent(jLabel62))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(quanLyVatTuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(giaNhapVatTuTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(soLuongVatTuTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(quanLyVatTuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(quanLyVatTuLayout.createSequentialGroup()
                                .addComponent(jLabel59)
                                .addGap(18, 18, 18)
                                .addComponent(tenVatTuTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(quanLyVatTuLayout.createSequentialGroup()
                                .addComponent(jLabel61)
                                .addGap(18, 18, 18)
                                .addComponent(tinhTrangVatTuTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, quanLyVatTuLayout.createSequentialGroup()
                                .addComponent(jLabel60)
                                .addGap(18, 18, 18)
                                .addGroup(quanLyVatTuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ngayNhapVatTuTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(quanLyVatTuLayout.createSequentialGroup()
                                        .addComponent(themVTButton, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(27, 27, 27)
                                        .addComponent(xoaVatTuButton, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(7, 7, 7)
                        .addComponent(capNhatVatTuButton, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(timKiemVatTuButton, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)))
                .addContainerGap())
        );
        quanLyVatTuLayout.setVerticalGroup(
            quanLyVatTuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(quanLyVatTuLayout.createSequentialGroup()
                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(quanLyVatTuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(vatTuTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel59)
                    .addComponent(tenVatTuTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel69))
                .addGap(18, 18, 18)
                .addGroup(quanLyVatTuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(quanLyVatTuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel61)
                        .addComponent(tinhTrangVatTuTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(quanLyVatTuLayout.createSequentialGroup()
                        .addGroup(quanLyVatTuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(loaiVatTuTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel58))
                        .addGap(18, 18, 18)
                        .addGroup(quanLyVatTuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel52)
                            .addComponent(giaNhapVatTuTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel60)
                            .addComponent(ngayNhapVatTuTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(quanLyVatTuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(quanLyVatTuLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(quanLyVatTuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(themVTButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(capNhatVatTuButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(timKiemVatTuButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(xoaVatTuButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(quanLyVatTuLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(quanLyVatTuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(soLuongVatTuTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel62))))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        dashboardTabPane.addTab("Vật tư", quanLyVatTu);

        nhapXuatPane.setBackground(new java.awt.Color(0, 51, 102));

        jLabel63.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(255, 255, 255));
        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel63.setText("Lịch sử nhập xuất");

        nhapXuatTable.setAutoCreateRowSorter(true);
        nhapXuatTable.setBackground(new java.awt.Color(204, 204, 204));
        nhapXuatTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã đơn ", "Ngày giao dịch", "Loại giao dịch", "Nhà cung cấp", "Tổng tiền"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        nhapXuatTable.setColumnSelectionAllowed(true);
        jScrollPane9.setViewportView(nhapXuatTable);
        nhapXuatTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jPanel7.setBackground(new java.awt.Color(204, 204, 255));

        vatPhamGDTable.setAutoCreateRowSorter(true);
        vatPhamGDTable.setBackground(new java.awt.Color(0, 51, 102));
        vatPhamGDTable.setForeground(new java.awt.Color(255, 255, 255));
        vatPhamGDTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã vật phẩm", "Tên vật phẩm", "Loại ", "Đơn vị", "Số lượng", "Giá tiền", "Thành tiền"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, true, true, true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        vatPhamGDTable.setColumnSelectionAllowed(true);
        jScrollPane10.setViewportView(vatPhamGDTable);
        vatPhamGDTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jLabel75.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel75.setText("Chi tiết thông tin giao dịch");

        jPanel16.setBackground(new java.awt.Color(204, 204, 255));
        jPanel16.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 5, true));

        jLabel64.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel64.setText("Mã đơn :");

        jLabel65.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel65.setText("Ngày giao dịch :");

        jLabel66.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel66.setText("Loại giao dịch :");

        jLabel67.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel67.setText("Nhà cung cấp :");

        jLabel68.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel68.setText("Tổng tiền :");

        maDonTextField.setBackground(new java.awt.Color(204, 204, 204));

        ngayGDTextField.setBackground(new java.awt.Color(204, 204, 204));

        loaiGDTextField.setBackground(new java.awt.Color(204, 204, 204));

        nhaCCTextField.setBackground(new java.awt.Color(204, 204, 204));

        tongTienTextField.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel64)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(maDonTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel65)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addComponent(ngayGDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel66)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(loaiGDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel67)
                            .addComponent(jLabel68))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nhaCCTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                            .addComponent(tongTienTextField))))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel64)
                    .addComponent(maDonTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel65)
                    .addComponent(ngayGDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loaiGDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel66))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel67)
                    .addComponent(nhaCCTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel68)
                    .addComponent(tongTienTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        suaNhapXuatButton.setBackground(new java.awt.Color(0, 51, 102));
        suaNhapXuatButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        suaNhapXuatButton.setForeground(new java.awt.Color(204, 204, 204));
        suaNhapXuatButton.setText("Cập nhật");
        suaNhapXuatButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));

        themNhapXuatButton.setBackground(new java.awt.Color(0, 51, 102));
        themNhapXuatButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        themNhapXuatButton.setForeground(new java.awt.Color(204, 204, 204));
        themNhapXuatButton.setText("Thêm");
        themNhapXuatButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));

        xoaNhapXuatButton.setBackground(new java.awt.Color(0, 51, 102));
        xoaNhapXuatButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        xoaNhapXuatButton.setForeground(new java.awt.Color(204, 204, 204));
        xoaNhapXuatButton.setText("Xóa");
        xoaNhapXuatButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(themNhapXuatButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(xoaNhapXuatButton, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(suaNhapXuatButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel75, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 626, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(themNhapXuatButton, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                            .addComponent(xoaNhapXuatButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(suaNhapXuatButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel75, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout nhapXuatPaneLayout = new javax.swing.GroupLayout(nhapXuatPane);
        nhapXuatPane.setLayout(nhapXuatPaneLayout);
        nhapXuatPaneLayout.setHorizontalGroup(
            nhapXuatPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(nhapXuatPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9)
                .addContainerGap())
        );
        nhapXuatPaneLayout.setVerticalGroup(
            nhapXuatPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nhapXuatPaneLayout.createSequentialGroup()
                .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dashboardTabPane.addTab("Quản lý nhập xuất", nhapXuatPane);

        lichHenPane.setBackground(new java.awt.Color(0, 51, 102));

        jPanel17.setBackground(new java.awt.Color(0, 51, 102));

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 4, true), "Danh sách lịch hẹn", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel18.setForeground(new java.awt.Color(0, 51, 102));

        lichHenTable.setBackground(new java.awt.Color(233, 240, 248));
        lichHenTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã lịch hẹn", "Tên bệnh nhân ", "Lý do", "Ngày hẹn", "Số điện thoại"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane11.setViewportView(lichHenTable);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
        );

        jPanel19.setBackground(new java.awt.Color(233, 240, 248));
        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 4, true), "Chi tiết lịch hẹn", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 51, 102));
        jLabel26.setText("Mã lịch hẹn :");

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 51, 102));
        jLabel30.setText("Mã bệnh nhân :");

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 51, 102));
        jLabel31.setText("Tên bệnh nhân :");

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(0, 51, 102));
        jLabel33.setText("Số điện thoại :");

        jLabel56.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(0, 51, 102));
        jLabel56.setText("Bác sĩ : ");

        jLabel70.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(0, 51, 102));
        jLabel70.setText("Ngày hẹn :");

        jLabel71.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(0, 51, 102));
        jLabel71.setText("Mã bác sĩ :");

        jLabel72.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(0, 51, 102));
        jLabel72.setText("Dịch vụ :");

        jLabel73.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(0, 51, 102));
        jLabel73.setText("Lý do :");

        maLichHenTextField.setBackground(new java.awt.Color(0, 51, 102));
        maLichHenTextField.setForeground(new java.awt.Color(255, 255, 255));

        maBNHenTextField.setBackground(new java.awt.Color(0, 51, 102));
        maBNHenTextField.setForeground(new java.awt.Color(255, 255, 255));

        tenBNHenTextField.setBackground(new java.awt.Color(0, 51, 102));
        tenBNHenTextField.setForeground(new java.awt.Color(255, 255, 255));
        tenBNHenTextField.setEnabled(false);

        sdtHenTextField.setBackground(new java.awt.Color(0, 51, 102));
        sdtHenTextField.setForeground(new java.awt.Color(255, 255, 255));

        ngayHenTextField.setBackground(new java.awt.Color(0, 51, 102));
        ngayHenTextField.setForeground(new java.awt.Color(255, 255, 255));

        tenBSHenTextField.setBackground(new java.awt.Color(0, 51, 102));
        tenBSHenTextField.setForeground(new java.awt.Color(255, 255, 255));

        maBSHenTetxField.setBackground(new java.awt.Color(0, 51, 102));
        maBSHenTetxField.setForeground(new java.awt.Color(255, 255, 255));

        ghiChuHenTextField.setBackground(new java.awt.Color(0, 51, 102));
        ghiChuHenTextField.setColumns(20);
        ghiChuHenTextField.setForeground(new java.awt.Color(255, 255, 255));
        ghiChuHenTextField.setRows(5);
        jScrollPane4.setViewportView(ghiChuHenTextField);

        dichVuHenTable.setBackground(new java.awt.Color(0, 51, 102));
        dichVuHenTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã dịch vụ", "Tên dịch vụ", "Số lượng", "Đơn giá"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane14.setViewportView(dichVuHenTable);

        dichVuHenComboBox.setBackground(new java.awt.Color(0, 51, 102));
        dichVuHenComboBox.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        dichVuHenComboBox.setForeground(new java.awt.Color(255, 255, 255));
        dichVuHenComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addComponent(jLabel26)
                                .addGap(35, 35, 35)
                                .addComponent(maLichHenTextField))
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addComponent(jLabel30)
                                .addGap(19, 19, 19)
                                .addComponent(maBNHenTextField))
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addComponent(jLabel31)
                                .addGap(15, 15, 15)
                                .addComponent(tenBNHenTextField))
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addComponent(jLabel33)
                                .addGap(26, 26, 26)
                                .addComponent(sdtHenTextField))
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addComponent(jLabel70)
                                .addGap(45, 45, 45)
                                .addComponent(ngayHenTextField))
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addComponent(jLabel71)
                                .addGap(47, 47, 47)
                                .addComponent(maBSHenTetxField))
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addComponent(jLabel56)
                                .addGap(63, 63, 63)
                                .addComponent(tenBSHenTextField))
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel72)
                                    .addComponent(jLabel73))
                                .addGap(56, 56, 56)
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                                    .addComponent(dichVuHenComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(111, 111, 111)
                        .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(maLichHenTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(maBNHenTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(tenBNHenTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(sdtHenTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel70)
                    .addComponent(ngayHenTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56)
                    .addComponent(tenBSHenTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel71)
                    .addComponent(maBSHenTetxField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel72)
                    .addComponent(dichVuHenComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel73)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel8.setBackground(new java.awt.Color(0, 51, 102));
        jPanel8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 255), 5, true));

        xoaLichHenButton.setBackground(new java.awt.Color(0, 51, 102));
        xoaLichHenButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        xoaLichHenButton.setForeground(new java.awt.Color(255, 255, 255));
        xoaLichHenButton.setText("Xóa lịch hẹn");
        xoaLichHenButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));

        timKiemLichHenButton.setBackground(new java.awt.Color(0, 51, 102));
        timKiemLichHenButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        timKiemLichHenButton.setForeground(new java.awt.Color(255, 255, 255));
        timKiemLichHenButton.setText("Tìm kiếm");
        timKiemLichHenButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));

        themLichHenButton.setBackground(new java.awt.Color(0, 51, 102));
        themLichHenButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        themLichHenButton.setForeground(new java.awt.Color(255, 255, 255));
        themLichHenButton.setText("Tạo lịch hẹn");
        themLichHenButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));

        capNhatLichHenButton.setBackground(new java.awt.Color(0, 51, 102));
        capNhatLichHenButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        capNhatLichHenButton.setForeground(new java.awt.Color(255, 255, 255));
        capNhatLichHenButton.setText("Cập nhật lịch hẹn");
        capNhatLichHenButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(themLichHenButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(capNhatLichHenButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(timKiemLichHenButton, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(xoaLichHenButton, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(xoaLichHenButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(themLichHenButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timKiemLichHenButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(capNhatLichHenButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout lichHenPaneLayout = new javax.swing.GroupLayout(lichHenPane);
        lichHenPane.setLayout(lichHenPaneLayout);
        lichHenPaneLayout.setHorizontalGroup(
            lichHenPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        lichHenPaneLayout.setVerticalGroup(
            lichHenPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        dashboardTabPane.addTab("Lịch hẹn", lichHenPane);

        taiKhoanPane.setBackground(new java.awt.Color(0, 51, 102));
        taiKhoanPane.setLayout(null);

        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel42.setText("Danh sách tài khoản");
        taiKhoanPane.add(jLabel42);
        jLabel42.setBounds(0, 0, 961, 79);

        jLabel76.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel76.setForeground(new java.awt.Color(255, 255, 255));
        jLabel76.setText("Tài khoản :");
        taiKhoanPane.add(jLabel76);
        jLabel76.setBounds(614, 85, 269, 16);

        tiaKhoanTextField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        taiKhoanPane.add(tiaKhoanTextField);
        tiaKhoanTextField.setBounds(614, 107, 269, 35);

        jLabel77.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(255, 255, 255));
        jLabel77.setText("Mật khẩu");
        taiKhoanPane.add(jLabel77);
        jLabel77.setBounds(614, 160, 269, 16);

        ngayTaoTextField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ngayTaoTextField.setForeground(new java.awt.Color(153, 0, 0));
        ngayTaoTextField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        ngayTaoTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        ngayTaoTextField.setEnabled(false);
        taiKhoanPane.add(ngayTaoTextField);
        ngayTaoTextField.setBounds(614, 257, 269, 35);

        jLabel78.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(255, 255, 255));
        jLabel78.setText("Ngày tạo (không cần điền trường này) :");
        taiKhoanPane.add(jLabel78);
        jLabel78.setBounds(614, 235, 269, 16);

        matKhauTextField.setForeground(new java.awt.Color(0, 51, 102));
        matKhauTextField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        taiKhoanPane.add(matKhauTextField);
        matKhauTextField.setBounds(614, 182, 269, 35);

        xoaTKButton.setBackground(new java.awt.Color(0, 51, 102));
        xoaTKButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        xoaTKButton.setForeground(new java.awt.Color(255, 255, 255));
        xoaTKButton.setText("Xóa tài khoản");
        xoaTKButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        taiKhoanPane.add(xoaTKButton);
        xoaTKButton.setBounds(755, 312, 128, 40);

        chanTKButton.setBackground(new java.awt.Color(0, 51, 102));
        chanTKButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        chanTKButton.setForeground(new java.awt.Color(255, 255, 255));
        chanTKButton.setText("Chặn / Hủy Chặn");
        chanTKButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        taiKhoanPane.add(chanTKButton);
        chanTKButton.setBounds(755, 370, 128, 40);

        themTKButton.setBackground(new java.awt.Color(0, 51, 102));
        themTKButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        themTKButton.setForeground(new java.awt.Color(255, 255, 255));
        themTKButton.setText("Thêm tài khoản");
        themTKButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        taiKhoanPane.add(themTKButton);
        themTKButton.setBounds(614, 312, 129, 40);

        dangXuatButton.setBackground(new java.awt.Color(0, 51, 102));
        dangXuatButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        dangXuatButton.setForeground(new java.awt.Color(255, 255, 255));
        dangXuatButton.setText("Đăng xuất");
        dangXuatButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        taiKhoanPane.add(dangXuatButton);
        dangXuatButton.setBounds(614, 420, 269, 40);

        doiMKButton.setBackground(new java.awt.Color(0, 51, 102));
        doiMKButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        doiMKButton.setForeground(new java.awt.Color(255, 255, 255));
        doiMKButton.setText("Đổi mật khẩu");
        doiMKButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        taiKhoanPane.add(doiMKButton);
        doiMKButton.setBounds(614, 370, 129, 40);

        taiKhoanTable.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        taiKhoanTable.setForeground(new java.awt.Color(0, 51, 102));
        taiKhoanTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tài khoản", "Mật khẩu", "Ngày tạo", "Trạng thái"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        taiKhoanTable.setSelectionBackground(new java.awt.Color(153, 153, 255));
        taiKhoanTable.setSelectionForeground(new java.awt.Color(0, 51, 102));
        taiKhoanTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane12.setViewportView(taiKhoanTable);
        if (taiKhoanTable.getColumnModel().getColumnCount() > 0) {
            taiKhoanTable.getColumnModel().getColumn(0).setResizable(false);
            taiKhoanTable.getColumnModel().getColumn(1).setResizable(false);
            taiKhoanTable.getColumnModel().getColumn(2).setResizable(false);
        }

        taiKhoanPane.add(jScrollPane12);
        jScrollPane12.setBounds(50, 80, 452, 380);

        dashboardTabPane.addTab("Tài khoản", taiKhoanPane);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(dashboardTabPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(dashboardTabPane))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tenBacSiTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tenBacSiTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tenBacSiTextFieldActionPerformed

    public javax.swing.JTabbedPane getTabbedPane() {
        return dashboardTabPane;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel anhBacSiLabel;
    private javax.swing.JPanel bacSiTabPanel;
    private javax.swing.JTable bacSiTable;
    private javax.swing.JTable benhAnTable;
    private javax.swing.JPanel benhNhanPanel;
    private javax.swing.JTable benhNhanTable;
    private javax.swing.JButton boChonButton;
    private javax.swing.JButton capNhatBAButton;
    private javax.swing.JButton capNhatBSButton;
    private javax.swing.JButton capNhatHDButton;
    private javax.swing.JButton capNhatLichHenButton;
    private javax.swing.JButton capNhatVatTuButton;
    private javax.swing.JTextField chanDoanTextField;
    private javax.swing.JButton chanTKButton;
    private javax.swing.JTextField chucVuTextField;
    private javax.swing.JTextField chuyenMonTextField;
    private javax.swing.JButton dangXuatButton;
    private javax.swing.JTabbedPane dashboardTabPane;
    private javax.swing.JComboBox<String> dichVuComboBox;
    private javax.swing.JComboBox<String> dichVuHenComboBox;
    private javax.swing.JTable dichVuHenTable;
    private javax.swing.JTable dichVuTable;
    private javax.swing.JLabel dienThoaiBNLabel;
    private javax.swing.JTextField dienThoaiBNTextField;
    private javax.swing.JButton doiMKButton;
    private javax.swing.JButton donDepButton;
    private javax.swing.JTextArea ghiChuHenTextField;
    private javax.swing.JTextField ghiChuTextField;
    private javax.swing.JTextField giaNhapVatTuTextField;
    private javax.swing.JLabel gioiTinhBNLabel;
    private javax.swing.JTextField gioiTinhBSTextField;
    private javax.swing.JTextField gioiTinhBnTextField;
    private javax.swing.JPanel hoSoBNPanel;
    private javax.swing.JPanel hoaDonTabPane;
    private javax.swing.JTable hoaDonTable;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JPanel lichHenPane;
    private javax.swing.JTable lichHenTable;
    private javax.swing.JTextField loaiGDTextField;
    private javax.swing.JTextField loaiVatTuTextField;
    private javax.swing.JTextField luongThangTextField;
    private javax.swing.JTextField maBATextField;
    private javax.swing.JTextField maBNHenTextField;
    private javax.swing.JTextField maBNTextField;
    private javax.swing.JTextField maBSHenTetxField;
    private javax.swing.JTextField maBSTextField;
    private javax.swing.JTextField maBacSiTextField;
    private javax.swing.JComboBox<String> maBenhNhanComboBox;
    private javax.swing.JTextField maBenhNhanTextField;
    private javax.swing.JTextField maDonTextField;
    private javax.swing.JTextField maLichHenTextField;
    private javax.swing.JTextField matKhauTextField;
    private javax.swing.JTextField ngayGDTextField;
    private javax.swing.JTextField ngayHenTextField;
    private javax.swing.JTextField ngayKhamHDTextField;
    private javax.swing.JTextField ngayKhamTextField;
    private javax.swing.JTextField ngayNhapVatTuTextField;
    private javax.swing.JLabel ngaySinhBNLabel;
    private javax.swing.JTextField ngaySinhBNTextField;
    private javax.swing.JTextField ngaySinhBSTextField;
    private javax.swing.JTextField ngayTaiKhamTextField;
    private javax.swing.JTextField ngayTaoTextField;
    private javax.swing.JTextField nhaCCTextField;
    private javax.swing.JPanel nhapXuatPane;
    private javax.swing.JTable nhapXuatTable;
    private javax.swing.JPanel quanLyVatTu;
    private javax.swing.JTextField queQuanBNTextField;
    private javax.swing.JTextField queQuanBSTextField;
    private javax.swing.JButton sapXepLuong;
    private javax.swing.JButton sapXepTenBtn;
    private javax.swing.JTextField sdtHenTextField;
    private javax.swing.JTextField soDTTextField;
    private javax.swing.JTextField soHDTextField;
    private javax.swing.JTextField soLuongVatTuTextField;
    private javax.swing.JButton suaBNButton;
    private javax.swing.JButton suaNhapXuatButton;
    private javax.swing.JPanel taiKhoanPane;
    private javax.swing.JTable taiKhoanTable;
    private javax.swing.JTextField tenBNHenTextField;
    private javax.swing.JLabel tenBNLabel;
    private javax.swing.JTextField tenBNTextField;
    private javax.swing.JTextField tenBSHenTextField;
    private javax.swing.JTextField tenBSTextField;
    private javax.swing.JTextField tenBacSiTextField;
    private javax.swing.JComboBox<String> tenBenhNhanComboBox;
    private javax.swing.JTextField tenVatTuTextField;
    private javax.swing.JLabel thanhTienLabel;
    private javax.swing.JPanel thaoTacPanel;
    private javax.swing.JButton themBAButton;
    private javax.swing.JButton themBNButton;
    private javax.swing.JButton themBacSiButton;
    private javax.swing.JButton themHDButton;
    private javax.swing.JButton themLichHenButton;
    private javax.swing.JButton themNhapXuatButton;
    private javax.swing.JButton themTKButton;
    private javax.swing.JButton themVTButton;
    private javax.swing.JComboBox<String> thuocHDComboBox;
    private javax.swing.JTable thuocHDTable;
    private javax.swing.JTable thuocKeDonTable;
    private javax.swing.JTextField tiaKhoanTextField;
    private javax.swing.JButton timKiemBAButton;
    private javax.swing.JButton timKiemBtn;
    private javax.swing.JButton timKiemButton;
    private javax.swing.JButton timKiemHDButton;
    private javax.swing.JButton timKiemLichHenButton;
    private javax.swing.JButton timKiemVatTuButton;
    private javax.swing.JTextField tinhTrangVatTuTextField;
    private javax.swing.JTextField tongTienTextField;
    private javax.swing.JTextField trieuChungTextField;
    private javax.swing.JTable vatPhamGDTable;
    private javax.swing.JTable vatTuTable;
    private javax.swing.JTextField vatTuTextField;
    private javax.swing.JButton xoaBAButton;
    private javax.swing.JButton xoaBNButton;
    private javax.swing.JButton xoaBacSiButton;
    private javax.swing.JButton xoaHDButton;
    private javax.swing.JButton xoaLichHenButton;
    private javax.swing.JButton xoaNhapXuatButton;
    private javax.swing.JButton xoaTKButton;
    private javax.swing.JButton xoaVatTuButton;
    // End of variables declaration//GEN-END:variables
}
