
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.sql.*;

/**
 *
 * @author HANIF FAISHAL HILMI
 */
public class TokoKomputer extends javax.swing.JFrame {

    private Connection conn;
    private TableRowSorter<TableModel> sorter; // TableRowSorter untuk filter pencarian

    public TokoKomputer() {
        initComponents();
        connectToDatabase();
        loadBarangData();
        searchTextField();
        loadRevenue();
        setColumnAlignmentCenter();
    }

    // Step 1: Database connection logic
    private void connectToDatabase() {
        if (conn == null) {
            try {
                // Database URL, user, and password
                String url = "jdbc:mysql://localhost:3306/toko_komputer";
                String user = "root"; // Username for MySQL
                String password = ""; // Password for MySQL (default is empty for root)

                // Ensure MySQL JDBC driver is loaded
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establish the connection
                conn = DriverManager.getConnection(url, user, password);
                System.out.println("Database connected successfully.");

            } catch (SQLException e) {
                // Specific SQLException for database-related errors
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to connect to the database. Error: " + e.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);

            } catch (ClassNotFoundException e) {
                // Error for missing JDBC driver
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "JDBC Driver not found. Please include the MySQL JDBC driver.",
                        "Driver Error", JOptionPane.ERROR_MESSAGE);

            } catch (Exception e) {
                // Catch any other exceptions
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "An unexpected error occurred.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Fungsi untuk mengatur alignment center untuk semua kolom di JTable
    private void setColumnAlignmentCenter() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Menetapkan renderer untuk setiap kolom di tabel
        for (int i = 0; i < jTable3.getColumnCount(); i++) {
            jTable3.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    // Step 2: Load data from the database
    private void loadBarangData() {
        // Ensure the connection is not null
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Database connection is not established.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String query = "SELECT id_barang,nama_barang,merek,id_kategori,stok_tersedia,harga FROM stock";
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement(); // Create statement
            rs = stmt.executeQuery(query); // Execute the query

            // Get the table model and clear any existing data
            DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
            model.setRowCount(0); // Clear existing rows

            // Iterate through the result set and populate the table
            while (rs.next()) {
                model.addRow(new Object[] {
                        rs.getInt("id_barang"),
                        rs.getString("nama_barang"),
                        rs.getString("merek"),
                        rs.getString("id_kategori"),
                        rs.getInt("stok_tersedia"),
                        rs.getDouble("harga")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load barang data. Error: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            // Close resources in the finally block to ensure they are closed even if an
            // exception occurs
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadRevenue() {
        // Ensure the connection is not null
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Database connection is not established.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String query = "SELECT id_penjualan,tanggal,total_harian FROM penjualan";
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement(); // Create statement
            rs = stmt.executeQuery(query); // Execute the query

            // Get the table model and clear any existing data
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0); // Clear existing rows

            // Iterate through the result set and populate the table
            while (rs.next()) {
                model.addRow(new Object[] {
                        rs.getInt("id_penjualan"),
                        rs.getDate("tanggal"),
                        rs.getDouble("total_harian"),
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load revenue. Error: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            // Close resources in the finally block to ensure they are closed even if an
            // exception occurs
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadPenjualanDetail() {
        // Ensure the connection is not null
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Database connection is not established.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String query = "SELECT id_detail, id_barang, qty, subtotal FROM penjualan_detail";
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement(); // Create statement
            rs = stmt.executeQuery(query); // Execute the query

            // Get the table model and clear any existing data
            DefaultTableModel model = (DefaultTableModel) tabel.getModel();
            model.setRowCount(0); // Clear existing rows

            // Iterate through the result set and populate the table
            while (rs.next()) {
                String getNamaBarang = "SELECT nama_barang from stock where id_barang = id_barang";
                ResultSet rsgetNamaBarang = stmt.executeQuery(getNamaBarang);
                model.addRow(new Object[] {
                        rs.getInt("id_detail"),
                        rs.getInt("id_barang"),
                        rsgetNamaBarang.getString("nama_barang"),
                        rs.getInt("qty"),
                        rs.getInt("subtotal"),
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load revenue. Error: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            // Close resources in the finally block to ensure they are closed even if an
            // exception occurs
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void searchTextField() {
        // Menambahkan TableRowSorter ke JTable
        DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
        sorter = new TableRowSorter<>(model);
        jTable3.setRowSorter(sorter);

        // Menambahkan listener pada JTextField untuk pencarian
        jTextField3.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search(jTextField3.getText()); // Pencarian saat teks ditambah
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search(jTextField3.getText()); // Pencarian saat teks dihapus
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                search(jTextField3.getText()); // Pencarian saat teks berubah
            }

            // Fungsi pencarian untuk menyaring data di JTable
            private void search(String str) {
                if (str.length() == 0) {
                    sorter.setRowFilter(null); // Tidak ada filter jika input kosong
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + str)); // Regex pencarian (case-insensitive)
                }
            }
        });
    }

    private void initComponents() {
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jTextField3 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel = new javax.swing.JTable();
        bayar = new javax.swing.JButton();
        batal = new javax.swing.JButton();
        Penjualan = new javax.swing.JLabel();
        item = new javax.swing.JLabel();
        inputitem = new javax.swing.JTextField();
        Nama = new javax.swing.JLabel();
        qty = new javax.swing.JLabel();
        inputqty = new javax.swing.JTextField();
        total = new javax.swing.JLabel();
        total1 = new javax.swing.JLabel();
        Merk = new javax.swing.JLabel();
        harga = new javax.swing.JLabel();
        tambahitem = new javax.swing.JButton();
        totalBelanja = new javax.swing.JLabel();
        total2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setText("Daftar Barang Tersedia");

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id_barang", "Nama Barang", "Merk", "id_kategori", "Stok", "Harga"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable3);

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel8.setText("Cari Barang");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 664, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Barang", jPanel1);

        tabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Item_id", "Item", "Qty", "Subtotal"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tabel);

        bayar.setText("Bayar");
        bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bayarActionPerformed(evt);
            }
        });

        batal.setText("Batal");
        batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batalActionPerformed(evt);
            }
        });

        Penjualan.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        Penjualan.setText("Penjualan");

        item.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        item.setText("Item:");

        inputitem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputitemActionPerformed(evt);
            }
        });

        Nama.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        Nama.setText("Nama Barang");

        qty.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        qty.setText("Qty:");

        inputqty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputqtyActionPerformed(evt);
            }
        });

        total.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        total.setText("Total:");

        total1.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        total1.setText("Uang");

        Merk.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        Merk.setText("Merk : ");

        harga.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        harga.setText("Harga : ");

        tambahitem.setText("Tambahkan Item");
        tambahitem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahitemActionPerformed(evt);
            }
        });

        totalBelanja.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        totalBelanja.setText("Total Belanja:");

        total2.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        total2.setText("0"
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tambahitem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(item)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputitem))
                    .addComponent(Nama)
                    .addComponent(Penjualan)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(total)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(total1))
                    .addComponent(Merk)
                    .addComponent(harga)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(qty)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(inputqty, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(bayar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(batal, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(totalBelanja)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(total2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(Penjualan)
                        .addGap(17, 17, 17)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(item)
                            .addComponent(inputitem, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addComponent(Nama)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Merk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(harga)
                        .addGap(33, 33, 33)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(qty)
                            .addComponent(inputqty, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(total)
                            .addComponent(total1)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalBelanja)
                    .addComponent(total2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tambahitem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(batal, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(36, 36, 36))
        );

        jTabbedPane1.addTab("Keranjang", jPanel2);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "revenueID", "Tanggal", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class
            };

    public Class getColumnClass(int columnIndex) {
        return types[columnIndex];
    }});jScrollPane1.setViewportView(jTable1);

    javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(
            jPanel3);jPanel3.setLayout(jPanel3Layout);jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jScrollPane1,javax.swing.GroupLayout.DEFAULT_SIZE,867,Short.MAX_VALUE));jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addComponent(jScrollPane1,javax.swing.GroupLayout.DEFAULT_SIZE,493,Short.MAX_VALUE).addContainerGap()));

    jTabbedPane1.addTab("Revenue",jPanel3);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());

    getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public int hitungSubTotal() {
        return (Integer.parseInt(inputqty.getText()) * Integer.parseInt(harga.getText()));
    }

    public int hitungTotal() {
        int total = 0;
        for (int i = 0; i < tabel.getRowCount(); i++) {
            total += Integer.parseInt(tabel.getValueAt(i, 5).toString());
        }
        return total;
    }

    private void batalActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_batalActionPerformed
        // TODO add your handling code here:
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Database connection is not established.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String query = "DELETE * FROM penjualan_detail";
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement(); // Create statement
            rs = stmt.executeQuery(query); // Execute the query
            baris = 0;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load revenue. Error: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            // Close resources in the finally block to ensure they are closed even if an
            // exception occurs
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }// GEN-LAST:event_batalActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jTextField3ActionPerformed

    private void inputitemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_inputitemActionPerformed
        // TODO add your handling code here:
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Database connection is not established.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String query = "SELECT id_barang,nama_barang,merek,id_kategori,stok_tersedia,harga FROM stock WHERE id_barang = '"
                + inputitem.getText() + "'";
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement(); // Create statement
            rs = stmt.executeQuery(query); // Execute the query

            Nama.setText(rs.getString("nama_barang"));
            Merk.setText(rs.getString("merek"));
            harga.setText(rs.getString("harga"));

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load revenue. Error: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            // Close resources in the finally block to ensure they are closed even if an
            // exception occurs
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }// GEN-LAST:event_inputitemActionPerformed

    private void inputqtyActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_inputqtyActionPerformed
        // TODO add your handling code here:
        total1.setText(String.valueOf(hitungSubTotal()));
    }// GEN-LAST:event_inputqtyActionPerformed

    int baris = 0;

    private void tambahitemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tambahitemActionPerformed
        // TODO add your handling code here:
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Database connection is not established.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String query = "INSERT INTO penjualan_detail (id_detail, id_barang, qty, subtotal) VALUES ("+ (baris+1)+", '"
                + inputitem.getText() + "', '" + inputqty.getText() + "', '" + total1.getText() + "')";
        Nama.setText("");
        Merk.setText("");
        harga.setText("");
        inputqty.setText("");
        total1.setText("");
        inputitem.setText("");
    }// GEN-LAST:event_tambahitemActionPerformed

    private void bayarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bayarActionPerformed
        // TODO add your handling code here:
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Database connection is not established.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String query = "DELETE * FROM penjualan_detail";
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement(); // Create statement
            rs = stmt.executeQuery(query); // Execute the query
            baris = 0;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load revenue. Error: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            // Close resources in the finally block to ensure they are closed even if an
            // exception occurs
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }// GEN-LAST:event_bayarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new TokoKomputer().setVisible(true));
    }

    // Variables declaration

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Merk;
    private javax.swing.JLabel Nama;
    private javax.swing.JLabel Penjualan;
    private javax.swing.JButton batal;
    private javax.swing.JButton bayar;
    private javax.swing.JLabel harga;
    private javax.swing.JTextField inputitem;
    private javax.swing.JTextField inputqty;
    private javax.swing.JLabel item;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JLabel qty;
    private javax.swing.JTable tabel;
    private javax.swing.JButton tambahitem;
    private javax.swing.JLabel total;
    private javax.swing.JLabel total1;
    private javax.swing.JLabel total2;
    private javax.swing.JLabel totalBelanja;
    // End of variables declaration//GEN-END:variables
}
