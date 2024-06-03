package com.kompor.api.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;

@Entity(tableName = "penyelenggara")
public class Penyelenggara {
    @PrimaryKey
    String idPenyelenggara;
    String logo;
    String name;
    String deskripsi;
    String jumlahKompetisi;
    String email;
    String password;
    Timestamp createdAt;
    Timestamp updatedAt;

    @Ignore
    public Penyelenggara(String idPenyelenggara, String logo, String name, String deskripsi, String jumlahKompetisi, String email, String password, Timestamp createdAt, Timestamp updatedAt) {
        this.idPenyelenggara = idPenyelenggara;
        this.logo = logo;
        this.name = name;
        this.deskripsi = deskripsi;
        this.jumlahKompetisi = jumlahKompetisi;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Penyelenggara(Penyelenggara penyelenggara) {
        this.idPenyelenggara = penyelenggara.getIdPenyelenggara();
        this.logo = penyelenggara.getLogo();
        this.name = penyelenggara.getName();
        this.deskripsi = penyelenggara.getDeskripsi();
        this.jumlahKompetisi = penyelenggara.getJumlahKompetisi();
        this.email = penyelenggara.getEmail();
        this.password = penyelenggara.getPassword();
        this.createdAt = penyelenggara.getCreatedAt();
        this.updatedAt = penyelenggara.getUpdatedAt();
    }

    public String getIdPenyelenggara() {
        return idPenyelenggara;
    }

    public void setIdPenyelenggara(String idPenyelenggara) {
        this.idPenyelenggara = idPenyelenggara;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getJumlahKompetisi() {
        return jumlahKompetisi;
    }

    public void setJumlahKompetisi(String jumlahKompetisi) {
        this.jumlahKompetisi = jumlahKompetisi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}

