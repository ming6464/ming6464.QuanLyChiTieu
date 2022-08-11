package com.ming6464.quanlychitieu.DTO;

public class Khoan {
    private int STT,khoanTien;
    private String maLoai;

    public Khoan(int STT, int khoanTien, String maLoai) {
        this.STT = STT;
        this.khoanTien = khoanTien;
        this.maLoai = maLoai;
    }

    public int getSTT() {
        return STT;
    }

    public void setSTT(int STT) {
        this.STT = STT;
    }

    public int getKhoanTien() {
        return khoanTien;
    }

    public void setKhoanTien(int khoanTien) {
        this.khoanTien = khoanTien;
    }

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }
}
