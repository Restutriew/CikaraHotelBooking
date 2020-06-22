package com.cikarastudio.cikarahotelbooking.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class TipeKamar implements Parcelable {

    private String tipe_id;
    private String nama_tipe;
    private String harga;
    private String kapasitas;
    private String deskripsi;
    private String link_gambar;

    public TipeKamar(String tipe_id, String nama_tipe, String harga, String kapasitas, String deskripsi, String link_gambar) {
        this.tipe_id = tipe_id;
        this.nama_tipe = nama_tipe;
        this.harga = harga;
        this.kapasitas = kapasitas;
        this.deskripsi = deskripsi;
        this.link_gambar = link_gambar;
    }

    protected TipeKamar(Parcel in) {
        tipe_id = in.readString();
        nama_tipe = in.readString();
        harga = in.readString();
        kapasitas = in.readString();
        deskripsi = in.readString();
        link_gambar = in.readString();
    }

    public static final Creator<TipeKamar> CREATOR = new Creator<TipeKamar>() {
        @Override
        public TipeKamar createFromParcel(Parcel in) {
            return new TipeKamar(in);
        }

        @Override
        public TipeKamar[] newArray(int size) {
            return new TipeKamar[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(tipe_id);
        parcel.writeString(nama_tipe);
        parcel.writeString(harga);
        parcel.writeString(kapasitas);
        parcel.writeString(deskripsi);
        parcel.writeString(link_gambar);
    }

    public String getTipe_id() {
        return tipe_id;
    }

    public void setTipe_id(String tipe_id) {
        this.tipe_id = tipe_id;
    }

    public String getNama_tipe() {
        return nama_tipe;
    }

    public void setNama_tipe(String nama_tipe) {
        this.nama_tipe = nama_tipe;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(String kapasitas) {
        this.kapasitas = kapasitas;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getLink_gambar() {
        return link_gambar;
    }

    public void setLink_gambar(String link_gambar) {
        this.link_gambar = link_gambar;
    }


}
