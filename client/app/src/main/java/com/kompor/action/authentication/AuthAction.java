package com.kompor.action.authentication;

import androidx.lifecycle.MutableLiveData;

import com.kompor.action.BaseAction;
import com.kompor.api.model.Participant;
import com.kompor.api.utils.AuthResource;
import com.kompor.api.utils.Resource;
import com.kompor.api.service.AuthService;
import com.kompor.api.model.Penyelenggara;

import java.util.Date;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import retrofit2.Retrofit;

public class AuthAction extends BaseAction {
    private static final AuthService authService = getService(AuthService.class);

    public Single<AuthResource<Penyelenggara>> loginPenyelenggaraAction(String email, String password) {
        return authApiRequest(authService.loginPenyelenggara(email, password));
    }

    public Single<AuthResource<Penyelenggara>> registerPenyelenggaraAction(String email, String password, String nama, String deskripsi) {
        return authApiRequest(authService.registerPenyelenggara(email, password, "", nama, deskripsi));
    }

    public Single<AuthResource<Participant>> loginParticipantAction(String email, String password) {
        return authApiRequest(authService.loginParticipant(email, password));
    }

    public Single<AuthResource<Participant>> registerParticipantAction(String email, String password, String nama, String tanggalLahir, String sekolah) {
        return authApiRequest(authService.registerParticipant(email, password, nama, tanggalLahir, 2022, sekolah));
    }
}
