package com.kompor.action;

import com.kompor.api.model.Kompetisi;
import com.kompor.api.model.Participant;
import com.kompor.api.utils.AuthResource;
import com.kompor.api.utils.Resource;
import com.kompor.api.service.AuthService;
import com.kompor.api.model.Penyelenggara;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Single;

public class AuthAction extends BaseAction {
    private static final AuthService authService = getService(AuthService.class);
    private static final AuthService authServiceWithToken = getServiceWithAuth(AuthService.class);

    public Single<AuthResource<Penyelenggara>> loginPenyelenggaraAction(String email, String password) {
        return authApiRequest(authService.loginPenyelenggara(email, password));
    }

    public Single<AuthResource<Penyelenggara>> registerPenyelenggaraAction(String email, String password, String nama, String deskripsi) {
        return authApiRequest(authService.registerPenyelenggara(email, password, "", nama, deskripsi));
    }

    public Single<Resource<Penyelenggara>> getPenyelenggaraAction(String id) {
        return apiRequest(authServiceWithToken.penyelenggaraById(id));
    }

    public Single<Resource<ArrayList<Kompetisi>>> getPenyelenggaraKompetisiAction(String id) {
        return apiRequest(authServiceWithToken.getPenyelenggaraKompetisi(id));
    }

    public Single<Resource<Penyelenggara>> changePenyelenggaraAction(String id, String email, String logo, String nama, String deskripsi) {
        return apiRequest(authServiceWithToken.putPenyelenggara(id, email, logo, nama, deskripsi));
    }

// ==============================================================================================================================================
// ==============================================================================================================================================
// ==============================================================================================================================================

    public Single<AuthResource<Participant>> loginParticipantAction(String email, String password) {
        return authApiRequest(authService.loginParticipant(email, password));
    }

    public Single<AuthResource<Participant>> registerParticipantAction(String email, String password, String nama, String tanggalLahir, String sekolah) {
        return authApiRequest(authService.registerParticipant(email, password, nama, tanggalLahir, 2022, sekolah));
    }

    public Single<Resource<Participant>> getParticipantAction(String id) {
        return apiRequest(authServiceWithToken.participantById(id));
    }

    public Single<Resource<ArrayList<Kompetisi>>> getParticipantKompetisiAction(String id) {
        return apiRequest(authServiceWithToken.getParticipantKompetisi(id));
    }

    public Single<Resource<Participant>> changeParticipantAction(String id, String nama, String email, String sekolah) {
        return apiRequest(authServiceWithToken.putParticipant(id, email, nama, sekolah));
    }
}
