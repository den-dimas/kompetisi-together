package com.kompor.api.model.kelompok;


// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation
public class Kelompok {
    private String sekolah;
    private int angkatan;
    private int level;
    private boolean is_ketua;
    private String createdAt;
    private int kemenangan;
    private String id_kelompok;
    private String password;
    private int jumlah_pendaftaran;
    private String nama;
    private String updated_at;
    private int avg_rating;
    private String id_kompetisi;
    private String link_berkas;
    private String status_anggota;
    private String tanggal_lahir;
    private String email;
    private String id_participant;

    public Kelompok(String sekolah, int angkatan, int level, boolean is_ketua, String createdAt, int kemenangan, String id_kelompok, String password, int jumlah_pendaftaran, String nama, String updated_at, int avg_rating, String id_kompetisi, String link_berkas, String status_anggota, String tanggal_lahir, String email, String id_participant) {
        this.sekolah = sekolah;
        this.angkatan = angkatan;
        this.level = level;
        this.is_ketua = is_ketua;
        this.createdAt = createdAt;
        this.kemenangan = kemenangan;
        this.id_kelompok = id_kelompok;
        this.password = password;
        this.jumlah_pendaftaran = jumlah_pendaftaran;
        this.nama = nama;
        this.updated_at = updated_at;
        this.avg_rating = avg_rating;
        this.id_kompetisi = id_kompetisi;
        this.link_berkas = link_berkas;
        this.status_anggota = status_anggota;
        this.tanggal_lahir = tanggal_lahir;
        this.email = email;
        this.id_participant = id_participant;
    }

    public String getSekolah() { return sekolah; }
    public void setSekolah(String value) { this.sekolah = value; }

    public int getAngkatan() { return angkatan; }
    public void setAngkatan(int value) { this.angkatan = value; }

    public int getLevel() { return level; }
    public void setLevel(int value) { this.level = value; }

    public boolean getIsKetua() { return is_ketua; }
    public void setIsKetua(boolean value) { this.is_ketua = value; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String value) { this.createdAt = value; }

    public int getKemenangan() { return kemenangan; }
    public void setKemenangan(int value) { this.kemenangan = value; }

    public String getidKelompok() { return id_kelompok; }
    public void setidKelompok(String value) { this.id_kelompok = value; }

    public String getPassword() { return password; }
    public void setPassword(String value) { this.password = value; }

    public int getJumlah_pendaftaran() { return jumlah_pendaftaran; }
    public void setJumlah_pendaftaran(int value) { this.jumlah_pendaftaran = value; }

    public String getNama() { return nama; }
    public void setNama(String value) { this.nama = value; }

    public String getUpdated_at() { return updated_at; }
    public void setUpdated_at(String value) { this.updated_at = value; }

    public int getAvg_rating() { return avg_rating; }
    public void setAvg_rating(int value) { this.avg_rating = value; }

    public String getidKompetisi() { return id_kompetisi; }
    public void setidKompetisi(String value) { this.id_kompetisi = value; }

    public String getLink_berkas() { return link_berkas; }
    public void setLink_berkas(String value) { this.link_berkas = value; }

    public String getStatus_anggota() { return status_anggota; }
    public void setStatus_anggota(String value) { this.status_anggota = value; }

    public String getTanggal_lahir() { return tanggal_lahir; }
    public void setTanggal_lahir(String value) { this.tanggal_lahir = value; }

    public String getEmail() { return email; }
    public void setEmail(String value) { this.email = value; }

    public String getidParticipant() { return id_participant; }
    public void setidParticipant(String value) { this.id_participant = value; }
}
