package com.kompor.action.kompetisi;

import android.content.Context;

import com.kompor.action.BaseAction;
import com.kompor.api.model.Kompetisi;
import com.kompor.api.service.KompetisiService;
import com.kompor.api.utils.Resource;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Single;

public class KompetisiAction extends BaseAction {

    private static final KompetisiService kompetisiService = getServiceWithAuth(KompetisiService.class);

    public Single<Resource<ArrayList<Kompetisi>>> getAllKompetisiAction(String pendaftaran_dari, String pendaftaran_sampai, String tingkat, Integer anggota_per_tim, String kategori) {
        return apiRequest(kompetisiService.getAllKompetisi(pendaftaran_dari, pendaftaran_sampai, tingkat, anggota_per_tim, kategori));
    }

    public Single<Resource<ArrayList<Kompetisi>>> getAllPaidKompetisiAction() {
        return apiRequest(kompetisiService.getAllPaidKompetisi());
    }

    public Single<Resource<ArrayList<Kompetisi>>> getKompetisiByKategoriAction(String kategori) {
        return apiRequest(kompetisiService.getKompetisiByKategori(kategori.toUpperCase()));
    }

    public Single<Resource<Kompetisi>> createKompetisiAction(String id_penyelenggara, String nama_kompetisi, String pendaftaran_dari, String pendaftaran_sampai, String deskripsi, String tutup_pendaftaran, String tingkat, Integer anggota_per_tim, String kategori) {
        return apiRequest(kompetisiService.createKompetisi(id_penyelenggara, nama_kompetisi, pendaftaran_dari, pendaftaran_sampai, deskripsi, tutup_pendaftaran, tingkat, anggota_per_tim, kategori));
    }

    public Single<Resource<Kompetisi>> getKompetisiDetailsAction(String id_kompetisi) {
        return apiRequest(kompetisiService.getKompetisiDetails(id_kompetisi));
    }

    public Single<Resource<Kompetisi>> changeKompetisiDetailsAction(String id_kompetisi, String nama_kompetisi, String pendaftaran_dari, String pendaftaran_sampai, String deskripsi, String tutup_pendaftaran, String tingkat, Integer anggota_per_tim, String kategori) {
        return apiRequest(kompetisiService.changeKompetisiDetails(id_kompetisi, nama_kompetisi, pendaftaran_dari, pendaftaran_sampai, deskripsi, tutup_pendaftaran, tingkat, anggota_per_tim, kategori));
    }
}
