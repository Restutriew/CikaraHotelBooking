package com.cikarastudio.cikarahotelbooking.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cikarastudio.cikarahotelbooking.Model.TipeKamar;
import com.cikarastudio.cikarahotelbooking.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TipeKamarAdapter extends RecyclerView.Adapter<TipeKamarAdapter.TipeKamarViewHolder> {
    private Context mContext;
    private ArrayList<TipeKamar> mTipeKamarList;

    public TipeKamarAdapter(Context context, ArrayList<TipeKamar> tipeKamarList) {
        mContext = context;
        mTipeKamarList = tipeKamarList;
    }

    @NonNull
    @Override
    public TipeKamarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_kamar, parent, false);
        return new TipeKamarViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TipeKamarViewHolder holder, int position) {
        TipeKamar currentItem = mTipeKamarList.get(position);

        String tipekamar = currentItem.getNama_tipe();
        String harga = currentItem.getHarga();
        String kapasitas = currentItem.getKapasitas();
        String deskripsi = currentItem.getDeskripsi();
        String img_kamar = "http://kingdom.chatomz.com/img/hotel/"+currentItem.getLink_gambar();

        holder.tipeKamar.setText(tipekamar);
        holder.harga.setText(harga);
        holder.kapasitas.setText(kapasitas+" orang/kamar");
        holder.deskripsi.setText(deskripsi);
        Picasso.with(mContext).load(img_kamar).fit().centerCrop().into(holder.img_kamar);
    }

    @Override
    public int getItemCount() {
        return mTipeKamarList.size();
    }

    public class TipeKamarViewHolder extends RecyclerView.ViewHolder {
        public TextView tipeKamar;
        public TextView harga;
        public TextView kapasitas;
        public TextView deskripsi;
        public ImageView img_kamar;

        public TipeKamarViewHolder(@NonNull View itemView) {
            super(itemView);
            tipeKamar = itemView.findViewById(R.id.tv_tipeKamar);
            harga = itemView.findViewById(R.id.tv_Harga);
            kapasitas = itemView.findViewById(R.id.tv_kapasitasKamar);
            deskripsi = itemView.findViewById(R.id.tv_deskripsiKamar);
            img_kamar = itemView.findViewById(R.id.img_Kamar);
        }
    }
}
