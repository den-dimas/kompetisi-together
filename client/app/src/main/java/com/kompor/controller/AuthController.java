package com.kompor.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kompor.action.authentication.AuthAction;
import com.kompor.api.model.AuthApiResponse;
import com.kompor.api.model.Participant;
import com.kompor.api.model.Penyelenggara;

import java.util.ArrayList;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

//@AndroidViewMo
public class AuthController extends Controller {
    public CompositeDisposable disposables = new CompositeDisposable();
    public AuthAction action = new AuthAction();

    private final MutableLiveData<AuthApiResponse<Penyelenggara>> penyelenggaraInfo = new MutableLiveData<>();
    private final MutableLiveData<AuthApiResponse<Participant>> participantInfo = new MutableLiveData<>();

    public void loginPenyelenggara(String email, String password) {
        disposables.add(action.loginPenyelenggaraAction(email, password).subscribe(result -> {
            if (result.getSuccess()) {
                penyelenggaraInfo.postValue(new AuthApiResponse<>(result.getResult(), result.getRes_msg(), result.getToken()));
            } else {
                penyelenggaraInfo.postValue(new AuthApiResponse<>(null, result.getRes_msg(), null));
            }
        }));
    }

    public void registerPenyelenggara(String email, String password, String nama, String deskripsi) {
        disposables.add(action.registerPenyelenggaraAction(email, password, nama, deskripsi).subscribe(result -> {
            if (result.getSuccess()) {
                penyelenggaraInfo.postValue(new AuthApiResponse<>(result.getResult(), result.getRes_msg(), null));
            } else {
                penyelenggaraInfo.postValue(new AuthApiResponse<>(null, result.getRes_msg(), null));
            }
        }));
    }

    public void loginParticipant(String email, String password) {
        disposables.add(action.loginParticipantAction(email, password).subscribe(result -> {
            if (result.getSuccess()) {
                participantInfo.postValue(new AuthApiResponse<>(result.getResult(), result.getRes_msg(), result.getToken()));
            } else {
                participantInfo.postValue(new AuthApiResponse<>(null, result.getRes_msg(), null));
            }
        }));
    }

    public void registerParticipant(String email, String password, String nama, String tanggalLahir, String sekolah) {
        disposables.add(action.registerParticipantAction(email, password, nama, tanggalLahir, sekolah).subscribe(result -> {
            if (result.getSuccess()) {
                participantInfo.postValue(new AuthApiResponse<>(result.getResult(), result.getRes_msg(), null));
            } else {
                participantInfo.postValue(new AuthApiResponse<>(null, result.getRes_msg(), null));
            }
        }));
    }

    public void setLoginInfo(Context context, String id, String role, String token) {
        SharedPreferences sp = context.getSharedPreferences("AUTH", Context.MODE_PRIVATE);

        sp.edit().putString("id", id).apply();
        sp.edit().putString("role", role).apply();
        sp.edit().putString("token", token).apply();
    }

    public ArrayList<String> getLoginInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences("AUTH", Context.MODE_PRIVATE);

        ArrayList<String> data = new ArrayList<>();

        String id = sp.getString("id", null);
        String token = sp.getString("token", null);

        if (id == null || token == null)
            return null;

        data.add(id);
        data.add(sp.getString("role", null));
        data.add(token);

        return data;
    }

    public LiveData<AuthApiResponse<Penyelenggara>> getPenyelenggaraInfo() {
        return penyelenggaraInfo;
    }

    public LiveData<AuthApiResponse<Participant>> getParticipantInfo() {
        return participantInfo;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
