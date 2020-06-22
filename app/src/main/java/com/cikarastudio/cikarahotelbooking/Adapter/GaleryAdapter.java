package com.cikarastudio.cikarahotelbooking.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cikarastudio.cikarahotelbooking.Model.TipeKamar;
import com.cikarastudio.cikarahotelbooking.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GaleryAdapter extends RecyclerView.Adapter<GaleryAdapter.GaleryViewHolder> {


    private Context mContext;
    private ArrayList<TipeKamar> mTipeKamarList;
    public GaleryAdapter(Context context, ArrayList<TipeKamar> tipeKamarList) {
        mContext = context;
        mTipeKamarList = tipeKamarList;
    }

    @NonNull
    @Override
    public GaleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_gallery, parent, false);
        return new GaleryAdapter.GaleryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final GaleryViewHolder holder, int position) {

        TipeKamar currentItem = mTipeKamarList.get(position);
        String tipekamar = currentItem.getNama_tipe();
        String harga = "Rp. " +currentItem.getHarga();
        String kapasitas = currentItem.getKapasitas();
        String deskripsi = currentItem.getDeskripsi();
        String img_kamar = "http://kingdom.chatomz.com/img/hotel/" + currentItem.getLink_gambar();

//        holder.tipeKamar.setText(convertUpperCase(tipekamar));
//        holder.harga.setText(harga);
//        holder.kapasitas.setText(kapasitas + " orang/kamar");
//        holder.deskripsi.setText(deskripsi);
        Picasso.with(mContext).load(img_kamar).fit().centerCrop().into(holder.img_kamar);

    }

    @Override
    public int getItemCount() {
        return mTipeKamarList.size();
    }

    public class GaleryViewHolder extends RecyclerView.ViewHolder {
        public ImageView img_kamar;

        public GaleryViewHolder(@NonNull View itemView) {
            super(itemView);
            img_kamar = itemView.findViewById(R.id.galery_img);

        }
    }
}
