package com.example.informasikaryawan;

public class Karyawan {

    private int id;
    private String nama;
    private String password;
    private double gaji;

    public Karyawan() {
    }

    public Karyawan(int id, String nama, String password, double gaji) {
        this.id = id;
        this.nama = nama;
        this.password = password;
        this.gaji = gaji;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getGaji() {
        return gaji;
    }

    public void setGaji(double gaji) {
        this.gaji = gaji;
    }
}
