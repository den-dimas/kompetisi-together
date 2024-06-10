package com.kompor.controller;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kompor.action.AuthAction;
import com.kompor.api.model.Kompetisi;
import com.kompor.api.model.response.ApiResponse;
import com.kompor.api.model.response.AuthApiResponse;
import com.kompor.api.model.LoginInfo;
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
    private final MutableLiveData<ApiResponse<ArrayList<Kompetisi>>> kompetisiList = new MutableLiveData<>();
    private final MutableLiveData<String> image = new MutableLiveData<>();


    /*========================================================================================================================*/
    /* ========================================= PENYELENGGARA CONTROLLER =================================================== */
    /*========================================================================================================================*/

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

    public void getPenyelenggaraById(String id) {
        disposables.add(action.getPenyelenggaraAction(id).subscribe(result -> {
            if (result.getSuccess()) {
                penyelenggaraInfo.postValue(new AuthApiResponse<>(result.getResult(), result.getRes_msg(), null));
            } else {
                penyelenggaraInfo.postValue(new AuthApiResponse<>(null, result.getRes_msg(), null));
            }
        }));
    }

    public void getPenyelenggaraKompetisi(String id) {
        disposables.add(action.getPenyelenggaraKompetisiAction(id).subscribe(res -> {
            if (res.getSuccess())
                kompetisiList.postValue(new ApiResponse<>(res.getResult(), res.getRes_msg()));
            else kompetisiList.postValue(new ApiResponse<>(null, res.getRes_msg()));
        }));
    }

    public void changePenyelenggara(String id, String email, String logo, String nama, String deskripsi) {
        disposables.add(action.changePenyelenggaraAction(id, email, logo, nama, deskripsi).subscribe(result -> {
            if (result.getSuccess()) {
                penyelenggaraInfo.postValue(new AuthApiResponse<>(result.getResult(), result.getRes_msg(), null));
            } else {
                penyelenggaraInfo.postValue(new AuthApiResponse<>(null, result.getRes_msg(), null));
            }
        }));
    }

    /*========================================================================================================================*/
    /* ========================================= PARTICIPANT CONTROLLER ===================================================== */
    /*========================================================================================================================*/

    public void getParticipantById(String id) {
        disposables.add(action.getParticipantAction(id).subscribe(result -> {
            if (result.getSuccess()) {
                participantInfo.postValue(new AuthApiResponse<>(result.getResult(), result.getRes_msg(), null));
            } else {
                participantInfo.postValue(new AuthApiResponse<>(null, result.getRes_msg(), null));
            }
        }));
    }

    public void getParticipantKompetisi(String id) {
        disposables.add(action.getParticipantKompetisiAction(id).subscribe(res -> {
            if (res.getSuccess())
                kompetisiList.postValue(new ApiResponse<>(res.getResult(), res.getRes_msg()));
            else kompetisiList.postValue(new ApiResponse<>(null, res.getRes_msg()));
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

    public void changeParticipant(String id, String nama, String email, String sekolah) {
        disposables.add(action.changeParticipantAction(id, nama, email, sekolah).subscribe(result -> {
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



    /*========================================================================================================================*/
    /* ===============================================       DATA       ===================================================== */
    /*========================================================================================================================*/

    public static LoginInfo getLoginInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences("AUTH", Context.MODE_PRIVATE);

        String id = sp.getString("id", null);
        String token = sp.getString("token", null);

        if (id == null || token == null) return null;

        return new LoginInfo(id, token, sp.getString("role", null));
    }

    public LiveData<AuthApiResponse<Penyelenggara>> getPenyelenggaraInfo() {
        return penyelenggaraInfo;
    }

    public LiveData<AuthApiResponse<Participant>> getParticipantInfo() {
        return participantInfo;
    }

    public LiveData<ApiResponse<ArrayList<Kompetisi>>> getKompetisiList() {
        return kompetisiList;
    }

    public void setImage(String image) {
        this.image.setValue(image);
    }
    public LiveData<String> getImage() {
        return image;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
