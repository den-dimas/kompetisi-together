package com.kompor.action;

import com.kompor.api.model.kelompok.Kelompok;
import com.kompor.api.model.kelompok.KelompokDetails;
import com.kompor.api.service.KelompokService;
import com.kompor.api.utils.Resource;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Single;

public class KelompokAction extends BaseAction {
    private static final KelompokService service = getServiceWithAuth(KelompokService.class);

    public Single<Resource<ArrayList<Kelompok>>> getAllKelompokAction(String id) {
        return apiRequest(service.getAllKelompok(id));
    }

    public Single<Resource<Kelompok>> createKelompokAction(String idKompetisi, String idParticipant) {
        return apiRequest(service.postCreateKelompok(idKompetisi, idParticipant));
    }

    public Single<Resource<ArrayList<KelompokDetails>>> getKelompokDetailsAction(String idKelompok) {
        return apiRequest(service.getkelompokDetail(idKelompok));
    }

    public Single<Resource<ArrayList<KelompokDetails>>> changePendaftaranKelompokAction(String idKelompok, String idKetua, String idParticipant, String status) {
        return apiRequest(service.postChangePendaftaranKelompok(idKelompok, idKetua, idParticipant, status));
    }

    public Single<Resource<ArrayList<KelompokDetails>>> daftarKelompokAction(String idKelompok, String idParticipant) {
        return apiRequest(service.postDaftarKelompok(idKelompok, idParticipant));
    }
}
