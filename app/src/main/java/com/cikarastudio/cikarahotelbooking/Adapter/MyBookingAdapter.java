package com.cikarastudio.cikarahotelbooking.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cikarastudio.cikarahotelbooking.Model.Booking;
import com.cikarastudio.cikarahotelbooking.R;

import java.util.ArrayList;

public class MyBookingAdapter extends RecyclerView.Adapter<MyBookingAdapter.MyBookingViewHolder> {

    private Context mContext;
    private ArrayList<Booking> mBookingList;

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public interface OnItemClickCallback {
        void onItemClicked(Booking data);
    }

    public MyBookingAdapter(Context context, ArrayList<Booking> bookingList) {
        mContext = context;
        mBookingList = bookingList;
    }

    @NonNull
    @Override
    public MyBookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_booking, parent, false);
        return new MyBookingAdapter.MyBookingViewHolder(v);
    }

    //convertext
    private String convertText(String text) {
        StringBuilder stringBuilder = new StringBuilder(text);
        for (int i = stringBuilder.length() - 3; i > 0; i -= 3) {
            stringBuilder.insert(i, ".");
        }
        return stringBuilder.toString();
    }

    //CONVERTUPPERCASE
    private String convertUpperCase(String text){
        String output = "";

        String[] textArray = text.trim().split("\\s");
        for (int i= 0; i<textArray.length;i++){
            textArray[i] = textArray[i].substring(0,1).toUpperCase() + textArray[i].substring(1);
        }

        for (int i=0; i<textArray.length;i++){
            output = output+textArray[i]+" ";
        }

        return output.trim();
    }

    @Override
    public void onBindViewHolder(@NonNull final MyBookingViewHolder holder, int position) {
        Booking currentItem = mBookingList.get(position);

        String id_booking = currentItem.getId_booking();
        String tipe_kamar = currentItem.getTipe_kamar();
        String tgl_checkin = currentItem.getTgl_checkin();
        String lama_hari = currentItem.getLama_hari();
        String total_harga = currentItem.getTotal_harga();
        String status = currentItem.getStatus();



        holder.id_booking.setText(id_booking);
        holder.nama_tipe.setText(convertUpperCase(tipe_kamar));
        holder.tgl_checkin.setText("Tanggal Checkin : "+tgl_checkin);
        holder.lama_hari.setText("Lama Hari : "+lama_hari+" hari");
        holder.total_harga.setText("Total : Rp."+convertText(total_harga));
        holder.status.setText(status);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(mBookingList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBookingList.size();
    }

    class MyBookingViewHolder extends RecyclerView.ViewHolder {
        public TextView id_booking;
        public TextView nama_tipe;
        public TextView tgl_checkin;
        public TextView lama_hari;
        public TextView total_harga;
        public TextView status;
        MyBookingViewHolder(@NonNull View itemView) {
            super(itemView);
            id_booking = itemView.findViewById(R.id.id_booking);
            nama_tipe = itemView.findViewById(R.id.booking_tipeKamar);
            tgl_checkin = itemView.findViewById(R.id.booking_tanggalCheckin);
            lama_hari = itemView.findViewById(R.id.booking_lamaHari);
            total_harga = itemView.findViewById(R.id.booking_totalHarga);
            status = itemView.findViewById(R.id.booking_statusBooking);
        }
    }
}
