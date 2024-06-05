package com.kompor.controller;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kompor.action.kompetisi.KompetisiAction;
import com.kompor.api.model.ApiResponse;
import com.kompor.api.model.Kompetisi;

import java.util.ArrayList;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class KompetisiController extends Controller {
    public CompositeDisposable disposable = new CompositeDisposable();
    public KompetisiAction action = new KompetisiAction();

    private final MutableLiveData<ApiResponse<ArrayList<Kompetisi>>> listKompetisi = new MutableLiveData<>();
    private final MutableLiveData<ApiResponse<Kompetisi>> kompetisi = new MutableLiveData<>();

    public void getAllKompetisi(String pendaftaran_dari, String pendaftaran_sampai, String tingkat, Integer anggota_per_tim, String kategori) {
        disposable.add(action.getAllKompetisiAction(pendaftaran_dari, pendaftaran_sampai, tingkat, anggota_per_tim, kategori).subscribe(result -> {
            if (result.getSuccess())
                listKompetisi.postValue(new ApiResponse<>(result.getResult(), result.getRes_msg()));
            else listKompetisi.postValue(new ApiResponse<>(null, result.getRes_msg()));
        }));
    }

    public void getPaidKompetisi() {
        disposable.add(action.getAllPaidKompetisiAction().subscribe(result -> {
            if (result.getSuccess())
                listKompetisi.postValue(new ApiResponse<>(result.getResult(), result.getRes_msg()));
            else listKompetisi.postValue(new ApiResponse<>(null, result.getRes_msg()));
        }));
    }

    public void getKompetisiByKategori(String kategori) {
        disposable.add(action.getKompetisiByKategoriAction(kategori).subscribe(result -> {
            if (result.getSuccess())
                listKompetisi.postValue(new ApiResponse<>(result.getResult(), result.getRes_msg()));
            else listKompetisi.postValue(new ApiResponse<>(null, result.getRes_msg()));
        }));
    }

    public void createKompetisi(String id_penyelenggara, String nama_kompetisi, String pendaftaran_dari, String pendaftaran_sampai, String deskripsi, String tutup_pendaftaran, String tingkat, Integer anggota_per_tim, String kategori) {
        disposable.add(action.createKompetisiAction(id_penyelenggara, nama_kompetisi, pendaftaran_dari, pendaftaran_sampai, deskripsi, tutup_pendaftaran, tingkat, anggota_per_tim, kategori).subscribe(result -> {
            if (result.getSuccess())
                kompetisi.postValue(new ApiResponse<>(result.getResult(), result.getRes_msg()));
            else kompetisi.postValue(new ApiResponse<>(null, result.getRes_msg()));
        }));
    }

    public void getKompetisiDetails(String id_kompetisi) {
        disposable.add(action.getKompetisiDetailsAction(id_kompetisi).subscribe(result -> {
            if (result.getSuccess())
                kompetisi.postValue(new ApiResponse<>(result.getResult(), result.getRes_msg()));
            else kompetisi.postValue(new ApiResponse<>(null, result.getRes_msg()));
        }));
    }

    public void changeKompetisiDetails(String id_kompetisi, String nama_kompetisi, String pendaftaran_dari, String pendaftaran_sampai, String deskripsi, String tutup_pendaftaran, String tingkat, Integer anggota_per_tim, String kategori) {
        disposable.add(action.changeKompetisiDetailsAction(id_kompetisi, nama_kompetisi, pendaftaran_dari, pendaftaran_sampai, deskripsi, tutup_pendaftaran, tingkat, anggota_per_tim, kategori).subscribe(result -> {
            if (result.getSuccess())
                kompetisi.postValue(new ApiResponse<>(result.getResult(), result.getRes_msg()));
            else kompetisi.postValue(new ApiResponse<>(null, result.getRes_msg()));
        }));
    }

    public LiveData<ApiResponse<ArrayList<Kompetisi>>> getListKompetisi() {
        return listKompetisi;
    }

    public LiveData<ApiResponse<Kompetisi>> getKompetisi() {
        return kompetisi;
    }
}
