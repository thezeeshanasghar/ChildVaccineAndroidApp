package com.example.basit009.vaccs;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.basit009.vaccs.ClientsJson.DossesVaccines;

import java.util.ArrayList;

public class DosesAdapter extends RecyclerView.Adapter<DosesAdapter.RecyclerViewHolder> {

    private Context context;
    private ArrayList<DossesVaccines.Dosses> modalList;
    private int imagePath;
    private DosesAdapter.OnItemClickListener mItemClickListener;
    private ItemLongClickListener itemLongClickListener;
    private View layoutViewDoses;
    public ArrayList<View> viewColorDoses = new ArrayList<>();
    public View oldViewDoses;
    public boolean isSelectedDoses;


    public DosesAdapter(Context context, ArrayList<DossesVaccines.Dosses> modalList, int imagePath) {
        this.context = context;
        this.modalList = modalList;
        this.imagePath = imagePath;
    }


    @NonNull
    @Override
    public DosesAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutViewDoses = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_dosses_list, null);
        DosesAdapter.RecyclerViewHolder rcv = new DosesAdapter.RecyclerViewHolder(layoutViewDoses);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull DosesAdapter.RecyclerViewHolder holder, int position) {
        holder.tvDosesName.setText(modalList.get(position).Name);
        holder.ivDosesImage.setImageResource(imagePath);
    }

    @Override
    public int getItemCount() {
        return modalList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public TextView tvDosesName;
        public ImageView ivDosesImage;


        public RecyclerViewHolder(View itemView) {
            super(itemView);
            tvDosesName = itemView.findViewById(R.id.tv_item_dosses_name);
            ivDosesImage = itemView.findViewById(R.id.iv_item_dosses_img);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition(), modalList);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            unCheckItems();

            if (v == oldViewDoses) {
                v.setBackgroundColor(Color.TRANSPARENT);
                oldViewDoses = null;
                isSelectedDoses = false;
                return true;
            }

            itemLongClickListener.onItemLongClick(v, getLayoutPosition(), modalList);
            v.setBackgroundColor(Color.parseColor("#338091"));

            isSelectedDoses = true;

            viewColorDoses.add(v);
            oldViewDoses = v;

            return false;
        }
    }

    public void unCheckItems() {
        if (viewColorDoses != null) {
            for (int i = 0; i < viewColorDoses.size(); i++)
                viewColorDoses.get(i).setBackgroundColor(Color.TRANSPARENT);
            isSelectedDoses = false;
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View v, int position, ArrayList<DossesVaccines.Dosses> modelList);
    }

    public void SetOnItemClickListener(final DosesAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface ItemLongClickListener {
        void onItemLongClick(View v, int pos, ArrayList<DossesVaccines.Dosses> modelList);
    }

    public void setItemLongClickListener(DosesAdapter.ItemLongClickListener ic) {
        this.itemLongClickListener = ic;
    }


}
