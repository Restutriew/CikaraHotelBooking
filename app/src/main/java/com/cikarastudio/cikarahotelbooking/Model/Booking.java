package com.cikarastudio.cikarahotelbooking.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Booking implements Parcelable {

    String id_booking;
    String tipe_kamar;
    String tgl_checkin;
    String lama_hari;
    String total_harga;
    String status;

    public Booking(String id_booking, String tipe_kamar, String tgl_checkin, String lama_hari, String total_harga, String status) {
        this.id_booking = id_booking;
        this.tipe_kamar = tipe_kamar;
        this.tgl_checkin = tgl_checkin;
        this.lama_hari = lama_hari;
        this.total_harga = total_harga;
        this.status = status;
    }

    public String getId_booking() {
        return id_booking;
    }

    public void setId_booking(String id_booking) {
        this.id_booking = id_booking;
    }

    public String getTipe_kamar() {
        return tipe_kamar;
    }

    public void setTipe_kamar(String tipe_kamar) {
        this.tipe_kamar = tipe_kamar;
    }

    public String getTgl_checkin() {
        return tgl_checkin;
    }

    public void setTgl_checkin(String tgl_checkin) {
        this.tgl_checkin = tgl_checkin;
    }

    public String getLama_hari() {
        return lama_hari;
    }

    public void setLama_hari(String lama_hari) {
        this.lama_hari = lama_hari;
    }

    public String getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(String total_harga) {
        this.total_harga = total_harga;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static Creator<Booking> getCREATOR() {
        return CREATOR;
    }

    public Booking(Parcel in) {
        id_booking = in.readString();
        tipe_kamar = in.readString();
        tgl_checkin = in.readString();
        lama_hari = in.readString();
        total_harga = in.readString();
        status = in.readString();
    }

    public static final Creator<Booking> CREATOR = new Creator<Booking>() {
        @Override
        public Booking createFromParcel(Parcel in) {
            return new Booking(in);
        }

        @Override
        public Booking[] newArray(int size) {
            return new Booking[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id_booking);
        parcel.writeString(tipe_kamar);
        parcel.writeString(tgl_checkin);
        parcel.writeString(lama_hari);
        parcel.writeString(total_harga);
        parcel.writeString(status);
    }
}
