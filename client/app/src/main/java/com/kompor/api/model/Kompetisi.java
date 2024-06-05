package com.kompor.api.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "kompetisi")
public class Kompetisi {
    @PrimaryKey
    String id_kompetisi;
    String foto;
    String nama_kompetisi;
    String id_penyelenggara;

    String pendaftaran_dari;
    String pendaftaran_sampai;
    String deskripsi;
    Boolean tutup_pendaftaran;
    String tingkat;
    Integer anggota_per_tim;
    String kategori;
    Boolean is_paid_ad;
    String created_at;
    String updated_at;

    @Ignore
    public Kompetisi(String id_kompetisi, String foto, String nama_kompetisi, String id_penyelenggara, String pendaftaran_dari, String pendaftaran_sampai, String deskripsi, Boolean tutup_pendaftaran, String tingkat, Integer anggota_per_tim, String kategori, Boolean is_paid_ad, String created_at, String updated_at) {
        this.id_kompetisi = id_kompetisi;
        this.foto = foto;
        this.nama_kompetisi = nama_kompetisi;
        this.id_penyelenggara = id_penyelenggara;
        this.pendaftaran_dari = pendaftaran_dari;
        this.pendaftaran_sampai = pendaftaran_sampai;
        this.deskripsi = deskripsi;
        this.tutup_pendaftaran = tutup_pendaftaran;
        this.tingkat = tingkat;
        this.anggota_per_tim = anggota_per_tim;
        this.kategori = kategori;
        this.is_paid_ad = is_paid_ad;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Kompetisi(Kompetisi kompetisi) {
        id_kompetisi = kompetisi.id_kompetisi;
        foto = kompetisi.foto;
        nama_kompetisi = kompetisi.nama_kompetisi;
        id_penyelenggara = kompetisi.id_penyelenggara;
        pendaftaran_dari = kompetisi.pendaftaran_dari;
        pendaftaran_sampai = kompetisi.pendaftaran_sampai;
        deskripsi = kompetisi.deskripsi;
        tutup_pendaftaran = kompetisi.tutup_pendaftaran;
        tingkat = kompetisi.tingkat;
        anggota_per_tim = kompetisi.anggota_per_tim;
        kategori = kompetisi.kategori;
        is_paid_ad = kompetisi.is_paid_ad;
        created_at = kompetisi.created_at;
        updated_at = kompetisi.updated_at;
    }

    public String getId_kompetisi() {
        return id_kompetisi;
    }

    public void setId_kompetisi(String id_kompetisi) {
        this.id_kompetisi = id_kompetisi;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNama_kompetisi() {
        return nama_kompetisi;
    }

    public void setNama_kompetisi(String nama_kompetisi) {
        this.nama_kompetisi = nama_kompetisi;
    }

    public String getId_penyelenggara() {
        return id_penyelenggara;
    }

    public void setId_penyelenggara(String id_penyelenggara) {
        this.id_penyelenggara = id_penyelenggara;
    }

    public String getPendaftaran_dari() {
        return pendaftaran_dari;
    }

    public void setPendaftaran_dari(String pendaftaran_dari) {
        this.pendaftaran_dari = pendaftaran_dari;
    }

    public String getPendaftaran_sampai() {
        return pendaftaran_sampai;
    }

    public void setPendaftaran_sampai(String pendaftaran_sampai) {
        this.pendaftaran_sampai = pendaftaran_sampai;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public Boolean getTutup_pendaftaran() {
        return tutup_pendaftaran;
    }

    public void setTutup_pendaftaran(Boolean tutup_pendaftaran) {
        this.tutup_pendaftaran = tutup_pendaftaran;
    }

    public String getTingkat() {
        return tingkat;
    }

    public void setTingkat(String tingkat) {
        this.tingkat = tingkat;
    }

    public Integer getAnggota_per_tim() {
        return anggota_per_tim;
    }

    public void setAnggota_per_tim(Integer anggota_per_tim) {
        this.anggota_per_tim = anggota_per_tim;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public Boolean getIs_paid_ad() {
        return is_paid_ad;
    }

    public void setIs_paid_ad(Boolean is_paid_ad) {
        this.is_paid_ad = is_paid_ad;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
