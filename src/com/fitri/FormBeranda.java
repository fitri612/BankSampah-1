package com.fitri;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;

public class FormBeranda extends JFrame {
    private JPanel FormBerandaPanel;
    private JMenuBar menuBar;
    private JMenu menuBeranda;
    private JMenu menuLaporan;
    private JMenuItem menuDataMhs;
    private JMenuItem menuDataDosen;
    private JMenuItem menuKeluar;

    public FormBeranda() {


        menuDataMhs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuMHS();

            }

        });
    }

    public void menuMHS() {
//        FormPengolahanData frame = null;
//        try {
//            frame = new FormPengolahanData();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        try {
//            frame.setContentPane(new FormPengolahanData().FormPengolahanDataPanel);
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        frame.pack();
//        frame.setVisible(true);
//        dispose();
//        this.setVisible(false);
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setContentPane(new FormBeranda().FormBerandaPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
