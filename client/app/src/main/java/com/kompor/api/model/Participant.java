package com.kompor.api.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "participant")
public class Participant {
    @PrimaryKey
    String id_participant;
    String nama;
    int angkatan;
    String tanggal_lahir;
    String sekolah;
    int level;
    int kemenangan;
    int jumlah_pendaftaran;
    float rating;
    String email;
    String password;
    String createdAt;
    String updatedAt;


    @Ignore
    public Participant(String id_participant, String nama, int angkatan, String tanggal_lahir, String sekolah, String email, String password, String createdAt, String updatedAt, int level, int kemenangan, int jumlah_pendaftaran, float rating) {
        this.id_participant = id_participant;
        this.nama = nama;
        this.angkatan = angkatan;
        this.tanggal_lahir = tanggal_lahir;
        this.sekolah = sekolah;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.level = level;
        this.kemenangan = kemenangan;
        this.jumlah_pendaftaran = jumlah_pendaftaran;
        this.rating = rating;
    }

    public Participant(Participant participant) {
        this.id_participant = participant.getId_participant();
        this.nama = participant.getNama();
        this.angkatan = participant.getAngkatan();
        this.tanggal_lahir = participant.getTanggal_lahir();
        this.sekolah = participant.getSekolah();
        this.email = participant.getEmail();
        this.password = participant.getPassword();
        this.createdAt = participant.getCreatedAt();
        this.updatedAt = participant.getUpdatedAt();
        this.level = participant.getLevel();
        this.kemenangan = participant.getkemenangan();
        this.jumlah_pendaftaran = participant.getJumlah_pendaftaran();
        this.rating = participant.getRating();
    }

    public String getId_participant() {
        return id_participant;
    }

    public void setId_participant(String id_participant) {
        this.id_participant = id_participant;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getAngkatan() {
        return angkatan;
    }

    public void setAngkatan(int angkatan) {
        this.angkatan = angkatan;
    }

    public String getTanggal_lahir() {
        return tanggal_lahir;
    }

    public void setTanggal_lahir(String tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
    }

    public String getSekolah() {
        return sekolah;
    }

    public void setSekolah(String sekolah) {
        this.sekolah = sekolah;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getLevel() {
        return level;
    }

    public int getkemenangan() {
        return this.kemenangan;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setKemenangan(int kemenangan) {
        this.kemenangan = kemenangan;
    }

    public int getJumlah_pendaftaran() {
        return jumlah_pendaftaran;
    }

    public void setJumlah_pendaftaran(int jumlah_pendaftaran) {
        this.jumlah_pendaftaran = jumlah_pendaftaran;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
