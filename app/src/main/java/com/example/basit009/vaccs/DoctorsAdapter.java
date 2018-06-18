package com.example.basit009.vaccs;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.basit009.vaccs.ClientsJson.DoctorUser;

import java.util.ArrayList;

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.RecyclerViewHolder> {

    private Context context;
    private ArrayList<DoctorUser.Doctors> modalList;
    private int imagePath;
    private OnItemClickListener mItemClickListener;
    private int rowIndex=-1;

    public DoctorsAdapter(Context context, ArrayList<DoctorUser.Doctors> modalList,int imagePath) {
        this.context = context;
        this.modalList = modalList;
        this.imagePath=imagePath;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_doctors_list, null);
        RecyclerViewHolder rcv = new RecyclerViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, final int position) {
        holder.tvDoctorNames.setText(modalList.get(position).FirstName+" "+modalList.get(position).LastName);
        holder.tvDoctorPhoneNo.setText(modalList.get(position).MobileNumber);
        holder.tvDoctorImage.setImageResource(imagePath);

    }

    @Override
    public int getItemCount() {
        return modalList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tvDoctorNames,tvDoctorPhoneNo;
        public ImageView tvDoctorImage;
        private Toolbar toolbar;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            tvDoctorNames = itemView.findViewById(R.id.tv_item_doctor_name);
            tvDoctorImage=itemView.findViewById(R.id.iv_item_doctor_img);
            tvDoctorPhoneNo=itemView.findViewById(R.id.tv_item_doctor_phone);
            itemView.setOnClickListener(this);
           }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition(), modalList);
            }
        }


    }

    public interface OnItemClickListener {
        public void onItemClick(View v, int position, ArrayList<DoctorUser.Doctors> modelList);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }



    public void upDateList(ArrayList<DoctorUser.Doctors> newList){
        modalList=new ArrayList<>();
        modalList.addAll(newList);
        notifyDataSetChanged();
    }


}


