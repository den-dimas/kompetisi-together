package com.kompor.controller;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kompor.action.KelompokAction;
import com.kompor.api.model.kelompok.Kelompok;
import com.kompor.api.model.kelompok.KelompokDetails;
import com.kompor.api.model.response.ApiResponse;

import java.util.ArrayList;
import java.util.Objects;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class KelompokController extends Controller {
    public CompositeDisposable disposables = new CompositeDisposable();
    public KelompokAction action = new KelompokAction();

    private final MutableLiveData<ApiResponse<ArrayList<Kelompok>>> listKelompok = new MutableLiveData<>();
    private final MutableLiveData<ApiResponse<ArrayList<KelompokDetails>>> listAnggota = new MutableLiveData<>();

    public void getListKelompok(String idKompetisi) {
        disposables.add(action.getAllKelompokAction(idKompetisi).subscribe(res -> {
            if (res.getSuccess())
                listKelompok.postValue(new ApiResponse<>(res.getResult(), res.getRes_msg()));
            else listKelompok.postValue(new ApiResponse<>(null, res.getRes_msg()));
        }));
    }

    public void createKelompok(String idKompetisi, String idParticipant) {
        disposables.add(action.createKelompokAction(idKompetisi, idParticipant).subscribe(res -> {
            if (res.getSuccess() && res.getResult() != null) {
                ArrayList<Kelompok> newList = Objects.requireNonNull(listKelompok.getValue()).getResult();

                newList.add(res.getResult());

                listKelompok.postValue(new ApiResponse<>(newList, res.getRes_msg()));
            } else listKelompok.postValue(new ApiResponse<>(null, res.getRes_msg()));
        }));
    }

    public void getAnggotaKelompok(String idKelompok) {
        disposables.add(action.getKelompokDetailsAction(idKelompok).subscribe(res -> {
            if (res.getSuccess())
                listAnggota.postValue(new ApiResponse<>(res.getResult(), res.getRes_msg()));
            else listAnggota.postValue(new ApiResponse<>(null, res.getRes_msg()));
        }));
    }

    public void changeStatusAnggota(String idKelompok, String idKetua, String idParticipant, String status) {
        disposables.add(action.changePendaftaranKelompokAction(idKelompok, idKetua, idParticipant, status).subscribe(res -> {
            if (res.getSuccess())
                listAnggota.postValue(new ApiResponse<>(res.getResult(), res.getRes_msg()));
            else listAnggota.postValue(new ApiResponse<>(null, res.getRes_msg()));
        }));
    }

    public void daftarKelompok(String idKelompok, String idPendaftar) {
        disposables.add(action.daftarKelompokAction(idKelompok, idPendaftar).subscribe(res -> {
            if (res.getSuccess())
                listAnggota.postValue(new ApiResponse<>(res.getResult(), res.getRes_msg()));
            else listAnggota.postValue(new ApiResponse<>(null, res.getRes_msg()));
        }));
    }

    public LiveData<ApiResponse<ArrayList<Kelompok>>> getListKelompok() {
        return listKelompok;
    }

    public LiveData<ApiResponse<ArrayList<KelompokDetails>>> getListAnggota() {
        return listAnggota;
    }
}
