package com.kompor.api.model.kelompok;

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

public class KelompokDetails {
    private String sekolah;
    private String nama;
    private int angkatan;
    private int level;
    private boolean is_ketua;
    private String link_berkas;
    private String tanggal_lahir;
    private String status_anggota;
    private String id_participant;

    public KelompokDetails(String sekolah, String nama, int angkatan, int level, boolean is_ketua, String link_berkas, String tanggal_lahir, String status_anggota, String id_participant) {
        this.sekolah = sekolah;
        this.nama = nama;
        this.angkatan = angkatan;
        this.level = level;
        this.is_ketua = is_ketua;
        this.link_berkas = link_berkas;
        this.tanggal_lahir = tanggal_lahir;
        this.status_anggota = status_anggota;
        this.id_participant = id_participant;
    }

    public String getSekolah() { return sekolah; }
    public void setSekolah(String value) { this.sekolah = value; }

    public String getNama() { return nama; }
    public void setNama(String value) { this.nama = value; }

    public int getAngkatan() { return angkatan; }
    public void setAngkatan(int value) { this.angkatan = value; }

    public int getLevel() { return level; }
    public void setLevel(int value) { this.level = value; }

    public boolean getIsKetua() { return is_ketua; }
    public void setIsKetua(boolean value) { this.is_ketua = value; }

    public String getLink_berkas() { return link_berkas; }
    public void setLink_berkas(String value) { this.link_berkas = value; }

    public String getTanggal_lahir() { return tanggal_lahir; }
    public void setTanggal_lahir(String value) { this.tanggal_lahir = value; }

    public String getStatus_anggota() { return status_anggota; }
    public void setStatus_anggota(String value) { this.status_anggota = value; }

    public String getidParticipant() { return id_participant; }
    public void setidParticipant(String value) { this.id_participant = value; }
}
