package com.example.basit009.vaccs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.basit009.vaccs.ClientsJson.ChildrenUser;
import com.example.basit009.vaccs.ClientsJson.DoctorUser;

import java.util.ArrayList;

public class ChildrensAdapter extends RecyclerView.Adapter<ChildrensAdapter.RecyclerViewHolder> {

    private Context context;
    private ArrayList<ChildrenUser.Childrens> modalList;
    private int imagePath;
    private ChildrensAdapter.OnItemClickListener mItemClickListener;

    public ChildrensAdapter(Context context, ArrayList<ChildrenUser.Childrens> modalList, int imagePath) {
        this.context = context;
        this.modalList = modalList;
        this.imagePath=imagePath;
    }



    @NonNull
    @Override
    public ChildrensAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_children_list, null);
        ChildrensAdapter.RecyclerViewHolder rcv = new ChildrensAdapter.RecyclerViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull ChildrensAdapter.RecyclerViewHolder holder, int position) {
        holder.tvChildrenNames.setText(modalList.get(position).Name);
        holder.tvChildrenGender.setText(modalList.get(position).Gender);
        holder.tvChildrenDOB.setText(modalList.get(position).DOB);
        holder.tvChildrenCity.setText(modalList.get(position).City);
        holder.ivChildrenImage.setImageResource(imagePath);
    }

    @Override
    public int getItemCount() {
        return modalList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tvChildrenNames,tvChildrenGender,tvChildrenDOB,tvChildrenCity;
        public ImageView ivChildrenImage;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            tvChildrenNames = itemView.findViewById(R.id.tv_item_children_name);
            tvChildrenGender = itemView.findViewById(R.id.tv_item_children_gender);
            tvChildrenDOB = itemView.findViewById(R.id.tv_item_children_dob);
            tvChildrenCity = itemView.findViewById(R.id.tv_item_children_city);
            ivChildrenImage = itemView.findViewById(R.id.iv_item_children_img);
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
        public void onItemClick(View v, int position, ArrayList<ChildrenUser.Childrens> modelList);
    }

    public void SetOnItemClickListener(final ChildrensAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void upDateList(ArrayList<ChildrenUser.Childrens> newList){
        modalList=new ArrayList<>();
        modalList.addAll(newList);
        notifyDataSetChanged();
    }



}
