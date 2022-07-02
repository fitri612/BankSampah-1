package com.fitri;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FormPengolahanData extends JFrame {

    DefaultTableModel table1;
    Object[] list_data = new Object[10];
    int x = 0;
    int harga_perkg = 5000;
    DBConnection dbCon = new DBConnection();
    Connection con = dbCon.getConnection();

    private JPanel FormNilaiMhsPanel;
    private JTextField InputNama;
    private JTextField InputJenisSampah;
    private JTextField InputAlamat;
    private JTextField InputjumlahBerat;
    private JButton btnProses;
    private JButton tambahBtn;
    private JButton simpanBtn;
    private JTextField totalhrg;
    private JTextField nPredikat;
    private JTextField nHuruf;
    private JButton prosesBtn1;
    private JButton hapusBtn;
    private JButton updateBtn;
    private JTable table;
    private JButton keluar;
    private JButton cetak;
    private JTextField inputNIK;
    private JLabel Nama;
    private JLabel Jenis_sampah;
    private JLabel Alamat;
    private JLabel Jumlah_berat;
    private JLabel Harga;
    private JLabel hargaSampah;
    JPanel PanelAtas;
//    private JPanel PanelAtas;
    //    private JTextField InputNIK;

    public FormPengolahanData() throws SQLException{
        table1 = new DefaultTableModel();
        table.setModel(table1);
        for(String s : Arrays.asList("No ID", "NIK", "Nama", "Jenis Sampah", "Alamat", "Jumlah Berat", "Harga", "Total Harga", "Predikat", "Nilai Huruf")){
            table1.addColumn(s);
        }

        setResizable(false);

//        totalhrg.setEnabled(false);
        nPredikat.setEnabled(false);
        nHuruf.setEnabled(false);
//        prosesBtn1.setEnabled(false);
//        hapusBtn.setEnabled(false);
        updateBtn.setEnabled(false);

        tampilkanData();
        btnProses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prosesActionPerformed();

            }
        });
        simpanBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simpanActionPerformed();
            }
        });
        tambahBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tambahActionPerformed();

            }
        });
        prosesBtn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                proses1ActionPerformed();


            }
        });
        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateActionPerformed();

            }
        });
        hapusBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hapusActionPerformed();

            }
        });
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int index = table.getSelectedRow();
                try {
                    terpilih(index);
                } catch (SQLException ex) {
                    java.util.logging.Logger.getLogger(FormPengolahanData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }

                prosesBtn1.setEnabled(true);
                hapusBtn.setEnabled(true);
            }
        });
        keluar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);

            }
        });
        cetak.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cetakActionPerformed();
                } catch (JRException ex) {
                    throw new RuntimeException(ex);
                }
//                try {
//                    cetakActionPerformed();
//                } catch (JRException ex) {
//                    ex.getMessage();
//                }

            }
        });
    }

    private void cetakActionPerformed() throws JRException {
        JasperReport reports;
        String path=".\\src\\report\\BankSampah.jasper";
        try {
            reports = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint jprint = JasperFillManager.fillReport(path, null, con);
            JasperViewer jviewer = new JasperViewer(jprint, false);
            jviewer.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jviewer.setVisible(true);
            System.out.println("Masuk try");
        } catch (JRException ex) {
            Logger.getLogger(FormPengolahanData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void tampilkanData() throws SQLException{
        ArrayList[] list = getDataList();
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        Object[] row = new Object[10];
        for (int i = 0; i < list.length; i++){
            row[0] = i+1;
            row[1] = list[i].get(0);
            row[2] = list[i].get(1);
            row[3] = list[i].get(2);
            row[4] = list[i].get(3);
            row[5] = list[i].get(4);
            row[6] = list[i].get(5);
            row[7] = list[i].get(6);
            row[8] = list[i].get(7);
            row[9] = list[i].get(8);
            model.addRow(row);
        }
    }

    public void kosongkanText() {
        InputNama.setText("");
        InputJenisSampah.setText("");
        InputAlamat.setText("");
        InputjumlahBerat.setText("");


        totalhrg.setText("");
        nPredikat.setText("");
        nHuruf.setText("");
    }

    // proses hitung total harga
    public void ProsesHitung() {
        try{
            String nik = inputNIK.getText();
            String nama = InputNama.getText();
            String jenis_sampah = InputJenisSampah.getText();
            String alamat = InputAlamat.getText();
            int jumlah_berat = Integer.parseInt(InputjumlahBerat.getText());
            int harga = harga_perkg;
            int total_harga = jumlah_berat * harga;
            Data_user data_user = new Data_user(nik, nama, jenis_sampah, alamat, jumlah_berat, harga, total_harga);
            totalhrg.setText(String.valueOf(total_harga));
            nHuruf.setText(""+data_user.GetNilaiHuruf(jumlah_berat));
            nPredikat.setText(""+data_user.getPredikat(data_user.GetNilaiHuruf(jumlah_berat)));
        }
        catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Inputan Anda Kosong", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void kosongkanTabel() {
        DefaultTableModel model = (DefaultTableModel) this.table.getModel();
        model.setRowCount(0);
    }

    public void terpilih(int index) throws SQLException {
        ArrayList[] list = getDataList();
        inputNIK.setText((String) list[index].get(0));
        InputNama.setText((String) list[index].get(1));
        InputJenisSampah.setText((String) list[index].get(2));
        InputAlamat.setText((String) list[index].get(3));
        InputjumlahBerat.setText(String.valueOf(list[index].get(4)));
        harga_perkg = Integer.parseInt(String.valueOf(list[index].get(5)));

        btnProses.setEnabled(true);
        simpanBtn.setEnabled(true);
    }

    private void prosesActionPerformed() {
        ProsesHitung();
    }

    private void simpanActionPerformed() {
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO data(nik, nama, jenis_sampah, alamat, jumlah_berat, harga, total_harga, predikat, nilai_huruf) VALUES(?,?,?,?,?,?,?,?,?) ");
            ps.setString(1, inputNIK.getText());
            ps.setString(2, InputNama.getText());
            ps.setString(3, InputJenisSampah.getText());
            ps.setString(4, InputAlamat.getText());

            ps.setString(5, InputjumlahBerat.getText());
            ps.setString(6, String.valueOf(harga_perkg));
            ps.setString(7, totalhrg.getText());
            ps.setString(8, nPredikat.getText());
            ps.setString(9, nHuruf.getText());

//            System.out.println("before excute: " +InputNama.getText() + InputJenisSampah.getText() + InputAlamat.getText() + InputjumlahBerat.getText());
            ps.executeUpdate();
//            System.out.println("after excute: " + InputNama.getText() + InputJenisSampah.getText() + InputAlamat.getText() + InputjumlahBerat.getText());
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            SQLException ec = (SQLException) ex;
            JOptionPane.showMessageDialog(null, "Data Gagal Disimpan", "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println(ec.getMessage());
        }
        try {
            kosongkanText();
            kosongkanTabel();
            tampilkanData();
        } catch (SQLException ec) {
            java.util.logging.Logger.getLogger(FormPengolahanData.class.getName()).log(java.util.logging.Level.SEVERE, null, ec);
        }

//        System.out.println(InputNama.getText() + InputJenisSampah.getText() + InputAlamat.getText() + InputjumlahBerat.getText() + harga_perkg + totalhrg.getText() + nPredikat.getText() + nHuruf.getText());
    }

    private void tambahActionPerformed() {
        kosongkanText();
        btnProses.setEnabled(true);

        // tidak bisa diketik
        totalhrg.setEditable(false);
        nPredikat.setEditable(false);
        nHuruf.setEditable(false);

        // tidak bisa diklik
        simpanBtn.setEnabled(false);
        updateBtn.setEnabled(false);
        hapusBtn.setEnabled(false);
        prosesBtn1.setEnabled(false);
    }

    private void proses1ActionPerformed() {
        ProsesHitung();
        updateBtn.setEnabled(true);
        simpanBtn.setEnabled(false);
    }

    private void updateActionPerformed() {
        String updateQuery = null;
        PreparedStatement ps = null;
        updateQuery = "UPDATE data SET nik = ?, nama = ?, jenis_sampah = ?, alamat = ?, jumlah_berat = ?, harga = ?, total_harga = ?, predikat = ?, nilai_huruf = ? WHERE nik = ? ";
        try {
            ps = con.prepareStatement(updateQuery);
            ps.setString(1, inputNIK.getText());
            ps.setString(2, InputNama.getText());
            ps.setString(3, InputJenisSampah.getText());
            ps.setString(4, InputAlamat.getText());
            // input jumlah berat
            ps.setString(5, InputjumlahBerat.getText());
            ps.setString(6, String.valueOf(harga_perkg));
            ps.setString(7, totalhrg.getText());
            ps.setString(8, nPredikat.getText());
            ps.setString(9, nHuruf.getText());
            ps.setString(10, inputNIK.getText());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Diupdate", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Diupdate", "Error", JOptionPane.ERROR_MESSAGE);
        }
        try {
            kosongkanText();
            kosongkanTabel();
            tampilkanData();
        } catch (SQLException e) {
            java.util.logging.Logger.getLogger(FormPengolahanData.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
        }
    }

    private void hapusActionPerformed() {//GEN-FIRST:event_hapusActionPerformed
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM data WHERE nik = ?");
            ps.setString(1, inputNIK.getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Dihapus", "Error", JOptionPane.ERROR_MESSAGE);
        }

        try {
            kosongkanText();
            kosongkanTabel();
            tampilkanData();
        } catch (SQLException e) {
            java.util.logging.Logger.getLogger(FormPengolahanData.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
        }
    }
    public ArrayList[] getDataList() throws SQLException{
        String queryCount = "SELECT COUNT(*) as c FROM data";
        Statement state;
        ResultSet rsCount, result;
        state = con.createStatement();
        rsCount = state.executeQuery(queryCount);
        int sizeTable = 0;

        while (rsCount.next()) sizeTable = rsCount.getInt("c");
        ArrayList[] dataList = new ArrayList[sizeTable];
        String query = "SELECT * FROM data";
        result = state.executeQuery(query);
        int i = 0;
        while (result.next()) {
            dataList[i] = new ArrayList();
            dataList[i].add(result.getString("nik"));
            dataList[i].add(result.getString("nama"));
            dataList[i].add(result.getString("jenis_sampah"));
            dataList[i].add(result.getString("alamat"));
            dataList[i].add(result.getString("jumlah_berat"));
            dataList[i].add(result.getString("harga"));
            dataList[i].add(result.getString("total_harga"));
            dataList[i].add(result.getString("predikat"));
            dataList[i].add(result.getString("nilai_huruf"));
            i++;
        }
        return dataList;

    }


    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("Form Pengolahan Data");
        frame.setContentPane(new FormPengolahanData().FormNilaiMhsPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
