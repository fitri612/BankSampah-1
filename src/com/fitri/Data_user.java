package com.fitri;

public class Data_user implements Nilai{

    public String nik;
    public String nama;

    public String jenis_sampah;
    public String alamat;
    int vharga = 5000;
    int vjumlah_berat;
    int vtotal_harga;
    char nHuruf;
    String predikat;

    public Data_user(String nik, String nama, String jenis_sampah, String alamat, int vharga , int vjumlah_berat, int vtotal_harga) {
        this.nik = nik;
        this.nama = nama;
        this.jenis_sampah = jenis_sampah;
        this.alamat = alamat;
        this.vharga = vharga;
        this.vjumlah_berat = vjumlah_berat;
        this.vtotal_harga = vtotal_harga;
    }

    public double totalharga() {
        return vharga * vjumlah_berat;
    }

    @Override
    public char GetNilaiHuruf(int jumlah_berat) {
        if (jumlah_berat >= 1000) {
            nHuruf = 'A';
        } else if (jumlah_berat >= 500) {
            nHuruf = 'A';
        } else if (jumlah_berat >= 100) {
            nHuruf = 'A';
        } else if (jumlah_berat >= 25) {
            nHuruf = 'A';
        } else {
            nHuruf = 'A';
        }
        return nHuruf;
    }

    @Override
    public String getPredikat(char nhuruf) {
        switch (nhuruf) {
            case 'A':
                predikat = "Sangat Baik, Terimakasih";
                break;
            case 'B':
                predikat = "Baik, Tetap Jaga Kabersihan";
                break;
            case 'C':
                predikat = "Cukup, Tingkatkan Lagi";
                break;
            case 'D':
                predikat = "Kurang, Yuk Mulai Tingkatkan Lagi";
                break;
            default:
                predikat = "Kurang Baik, Yuk Mulai Tingkatkan Lagi";
        }
        return predikat;
    }
}
