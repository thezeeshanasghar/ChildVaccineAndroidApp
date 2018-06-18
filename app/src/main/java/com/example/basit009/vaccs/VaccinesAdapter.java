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
import android.widget.Toast;

import com.example.basit009.vaccs.ClientsJson.DoctorUser;
import com.example.basit009.vaccs.ClientsJson.VaccinesUser;

import java.util.ArrayList;
import java.util.HashSet;

public class VaccinesAdapter extends RecyclerView.Adapter<VaccinesAdapter.RecyclerViewHolder> {

    private HashSet<Integer> mSelected = null;
    private Context context;
    private ArrayList<VaccinesUser.Vaccines> modalList;
    private int imagePath;
    private VaccinesAdapter.OnItemClickListener mItemClickListener;
    private ItemLongClickListener itemLongClickListener;
    private View layoutView;
    public ArrayList<View> viewColor = new ArrayList<>();
    public View oldView;
    public boolean isSelected;


    public VaccinesAdapter(Context context, ArrayList<VaccinesUser.Vaccines> modalList, int imagePath) {
        this.context = context;
        this.modalList = modalList;
        this.imagePath = imagePath;
    }

    @NonNull
    @Override
    public VaccinesAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_vaccines_list, null);
        VaccinesAdapter.RecyclerViewHolder rcv = new VaccinesAdapter.RecyclerViewHolder(layoutView);

        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull VaccinesAdapter.RecyclerViewHolder holder, int position) {
        holder.tvVaccineNames.setText(modalList.get(position).Name);
        holder.tvVaccineImage.setImageResource(imagePath);
    }


    @Override
    public int getItemCount() {
        return modalList.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public TextView tvVaccineNames;
        public ImageView tvVaccineImage, ivEditVaccines;


        public RecyclerViewHolder(View itemView) {
            super(itemView);

            ivEditVaccines = itemView.findViewById(R.id.iv_item_edit_vaccine_img);
            tvVaccineNames = itemView.findViewById(R.id.tv_item_vaccine_name);
            tvVaccineImage = itemView.findViewById(R.id.iv_item_vaccine_img);
            ivEditVaccines.setOnClickListener(this);
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

            if (v == oldView) {
                v.setBackgroundColor(Color.TRANSPARENT);
                oldView = null;
                isSelected=false;
                return true;
            }

            itemLongClickListener.onItemLongClick(v, getLayoutPosition(), modalList);
            v.setBackgroundColor(Color.parseColor("#338091"));
            isSelected=true;
            viewColor.add(v);
            oldView = v;

            return false;
        }
    }

    public void unCheckItems() {
        if (viewColor != null) {
            for (int i = 0; i < viewColor.size(); i++)
                viewColor.get(i).setBackgroundColor(Color.TRANSPARENT);
            isSelected=false;
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View v, int position, ArrayList<VaccinesUser.Vaccines> modelList);
    }

    public void SetOnItemClickListener(final VaccinesAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface ItemLongClickListener {
        void onItemLongClick(View v, int pos, ArrayList<VaccinesUser.Vaccines> modelList);
    }

    public void setItemLongClickListener(VaccinesAdapter.ItemLongClickListener ic) {
        this.itemLongClickListener = ic;
    }


    public void upDateList(ArrayList<VaccinesUser.Vaccines> newList) {
        modalList = new ArrayList<>();
        modalList.addAll(newList);
        notifyDataSetChanged();
    }

}



