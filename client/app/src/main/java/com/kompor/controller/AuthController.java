package com.kompor.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kompor.action.authentication.AuthAction;
import com.kompor.api.model.ApiResponse;
import com.kompor.api.model.Participant;
import com.kompor.api.model.Penyelenggara;

import java.util.Date;
import java.util.Objects;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

//@AndroidViewMo
public class AuthController extends Controller {
    public CompositeDisposable disposables = new CompositeDisposable();
    public AuthAction action = new AuthAction();

    private final MutableLiveData<ApiResponse<Penyelenggara>> penyelenggaraInfo = new MutableLiveData<>();
    private final MutableLiveData<ApiResponse<Participant>> participantInfo = new MutableLiveData<>();

    public void loginPenyelenggara(String email, String password) {
        disposables.add(action.loginPenyelenggaraAction(email, password).subscribe(result -> {
            if (result.getSuccess()) {
                penyelenggaraInfo.postValue(new ApiResponse<>(result.getResult(), result.getRes_msg()));
            } else {
                penyelenggaraInfo.postValue(new ApiResponse<>(null, result.getRes_msg()));
            }
        }));
    }

    public void registerPenyelenggara(String email, String password, String nama, String deskripsi) {
        disposables.add(action.registerPenyelenggaraAction(email, password, nama, deskripsi).subscribe(result -> {
            if (result.getSuccess()) {
                penyelenggaraInfo.postValue(new ApiResponse<>(result.getResult(), result.getRes_msg()));
            } else {
                penyelenggaraInfo.postValue(new ApiResponse<>(null, result.getRes_msg()));
            }
        }));
    }

    public void loginParticipant(String email, String password) {
        disposables.add(action.loginParticipantAction(email, password).subscribe(result -> {
            if (result.getSuccess()) {
                participantInfo.postValue(new ApiResponse<>(result.getResult(), result.getRes_msg()));
            } else {
                participantInfo.postValue(new ApiResponse<>(null, result.getRes_msg()));
            }
        }));
    }

    public void registerParticipant(String email, String password, String nama, String tanggalLahir, String sekolah) {
        disposables.add(action.registerParticipantAction(email, password, nama, tanggalLahir, sekolah).subscribe(result -> {
            if (result.getSuccess()) {
                participantInfo.postValue(new ApiResponse<>(result.getResult(), result.getRes_msg()));
            } else {
                participantInfo.postValue(new ApiResponse<>(null, result.getRes_msg()));
            }
        }));
    }

    public LiveData<ApiResponse<Penyelenggara>> getPenyelenggaraInfo() {
        return penyelenggaraInfo;
    }

    public LiveData<ApiResponse<Participant>> getParticipantInfo() {
        return participantInfo;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
