package com.kompor.api.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;

@Entity(tableName = "penyelenggara")
public class Penyelenggara {
    @PrimaryKey
    String id_penyelenggara;
    String logo;
    String name;
    String deskripsi;
    String jumlah_kompetisi;
    String email;
    String password;
    String token;
    Timestamp created_at;
    Timestamp updated_at;

    @Ignore
    public Penyelenggara(String id_penyelenggara, String logo, String name, String deskripsi, String jumlah_kompetisi, String email, String password, Timestamp created_at, Timestamp updated_at) {
        this.id_penyelenggara = id_penyelenggara;
        this.logo = logo;
        this.name = name;
        this.deskripsi = deskripsi;
        this.jumlah_kompetisi = jumlah_kompetisi;
        this.email = email;
        this.password = password;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Penyelenggara(Penyelenggara penyelenggara) {
        this.id_penyelenggara = penyelenggara.getId_penyelenggara();
        this.logo = penyelenggara.getLogo();
        this.name = penyelenggara.getName();
        this.deskripsi = penyelenggara.getDeskripsi();
        this.jumlah_kompetisi = penyelenggara.getJumlah_kompetisi();
        this.email = penyelenggara.getEmail();
        this.password = penyelenggara.getPassword();
        this.created_at = penyelenggara.getCreated_at();
        this.updated_at = penyelenggara.getUpdated_at();
    }

    public String getId_penyelenggara() {
        return id_penyelenggara;
    }

    public void setId_penyelenggara(String id_penyelenggara) {
        this.id_penyelenggara = id_penyelenggara;
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

    public String getJumlah_kompetisi() {
        return jumlah_kompetisi;
    }

    public void setJumlah_kompetisi(String jumlah_kompetisi) {
        this.jumlah_kompetisi = jumlah_kompetisi;
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

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

