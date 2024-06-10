package com.kompor.api.service;

import com.kompor.api.model.Kompetisi;
import com.kompor.api.utils.Resource;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface KompetisiService {
    @FormUrlEncoded
    @POST("/kompetisi/")
    Single<Response<Resource<ArrayList<Kompetisi>>>> getAllKompetisi(
        @Field("pendaftaran_dari") String pendaftaran_dari,
        @Field("pendaftaran_sampai") String pendaftaran_sampai,
        @Field("tingkat") String tingkat,
        @Field("anggota_per_tim") Integer anggota_per_tim,
        @Field("kategori") String kategori
    );

    @GET("/kompetisi/paid")
    Single<Response<Resource<ArrayList<Kompetisi>>>> getAllPaidKompetisi(

    );

    @FormUrlEncoded
    @POST("/kompetisi/kategori")
    Single<Response<Resource<ArrayList<Kompetisi>>>> getKompetisiByKategori(
        @Field("kategori") String kategori
    );

    @FormUrlEncoded
    @POST("/kompetisi/create")
    Single<Response<Resource<Kompetisi>>> createKompetisi(
        @Field("id_penyelenggara") String id_penyelenggara,
        @Field("nama_kompetisi") String nama_kompetisi,
        @Field("pendaftaran_dari") String pendaftaran_dari,
        @Field("pendaftaran_sampai") String pendaftaran_sampai,
        @Field("deskripsi") String deskripsi,
        @Field("tutup_pendaftaran") boolean tutup_pendaftaran,
        @Field("tingkat") String tingkat,
        @Field("anggota_per_tim") Integer anggota_per_tim,
        @Field("kategori") String kategori,
        @Field("foto") String foto
    );

    @GET("/kompetisi/{id}")
    Single<Response<Resource<Kompetisi>>> getKompetisiDetails(
        @Path("id") String id
    );

    @FormUrlEncoded
    @PUT("/kompetisi/{id}")
    Single<Response<Resource<Kompetisi>>> changeKompetisiDetails(
        @Path("id") String id,
        @Field("nama_kompetisi") String nama_kompetisi,
        @Field("pendaftaran_dari") String pendaftaran_dari,
        @Field("pendaftaran_sampai") String pendaftaran_sampai,
        @Field("deskripsi") String deskripsi,
        @Field("tutup_pendaftaran") boolean tutup_pendaftaran,
        @Field("tingkat") String tingkat,
        @Field("anggota_per_tim") Integer anggota_per_tim,
        @Field("kategori") String kategori,
        @Field("foto") String foto
    );

    @GET("/kompetisi/{id}/list-pendaftaran")
    Single<Response<Resource<Kompetisi>>> getAllPendaftaran(
        @Path("id") String id
    );

    @FormUrlEncoded
    @POST("/kompetisi/{id}/list-pendaftaran/")
    Single<Response<Resource<Kompetisi>>> createPendaftaran(
        @Path("id") String id,
        @Field("id_kelompok") String id_kelompok,
        @Field("id_participant") String id_participant
    );

    @GET("/kompetisi/{id}/list-pendaftaran/{id_pendaftaran}")
    Single<Response<Resource<Kompetisi>>> getPendaftaranDetails(
        @Path("id") String id,
        @Path("id_pendaftaran") String id_pendaftaran
    );

    @FormUrlEncoded
    @POST("/kompetisi/{id}/list-pendaftaran/{id_pendaftaran}")
    Single<Response<Resource<Kompetisi>>> changePendaftaranDetails(
        @Path("id") String id,
        @Path("id_pendaftaran") String id_pendaftaran,
        @Field("status_pembayaran") String status_pembayaran,
        @Field("status_pendaftaran") String status_pendaftaran,
        @Field("note") String note
    );
}
