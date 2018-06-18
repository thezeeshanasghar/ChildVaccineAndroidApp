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

import com.example.basit009.vaccs.ClientsJson.BrandVaccines;
import com.example.basit009.vaccs.ClientsJson.ChildrenUser;

import java.util.ArrayList;

public class BrandsAdapter extends RecyclerView.Adapter<BrandsAdapter.RecyclerViewHolder>{

    private Context context;
    private ArrayList<BrandVaccines.Brands> modalList;
    private int imagePath;
    private BrandsAdapter.OnItemClickListener mItemClickListener;
    private ItemLongClickListener itemLongClickListener;
    private View layoutViewBrands;
    public ArrayList<View> viewColorBrands = new ArrayList<>();
    public View oldViewBrands;
    public boolean isSelectedBrands;



    public BrandsAdapter(Context context, ArrayList<BrandVaccines.Brands> modalList, int imagePath) {
        this.context = context;
        this.modalList = modalList;
        this.imagePath = imagePath;
    }


    @NonNull
    @Override
    public BrandsAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutViewBrands= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_brands_list, null);
        BrandsAdapter.RecyclerViewHolder rcv = new BrandsAdapter.RecyclerViewHolder(layoutViewBrands);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull BrandsAdapter.RecyclerViewHolder holder, int position) {
        holder.tvBrandName.setText(modalList.get(position).Name);
        holder.ivBrandImage.setImageResource(imagePath);
    }

    @Override
    public int getItemCount() {
        return modalList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {

        public TextView tvBrandName;
        public ImageView ivBrandImage;


        public RecyclerViewHolder(View itemView) {
            super(itemView);
            tvBrandName = itemView.findViewById(R.id.tv_item_brand_name);
            ivBrandImage = itemView.findViewById(R.id.iv_item_brand_img);
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

            if (v == oldViewBrands) {
                v.setBackgroundColor(Color.TRANSPARENT);
                oldViewBrands = null;
                isSelectedBrands=false;
                return true;
            }

            itemLongClickListener.onItemLongClick(v, getLayoutPosition(), modalList);
            v.setBackgroundColor(Color.parseColor("#338091"));
            isSelectedBrands=true;
            viewColorBrands.add(v);
            oldViewBrands = v;

            return false;
        }
    }


    public void unCheckItems() {
        if (viewColorBrands != null) {
            for (int i = 0; i < viewColorBrands.size(); i++)
                viewColorBrands.get(i).setBackgroundColor(Color.TRANSPARENT);
            isSelectedBrands=false;
        }
    }


    public interface OnItemClickListener {
        public void onItemClick(View v, int position, ArrayList<BrandVaccines.Brands> modelList);
    }

    public void SetOnItemClickListener(final BrandsAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface ItemLongClickListener {
        void onItemLongClick(View v, int pos, ArrayList<BrandVaccines.Brands> modelList);
    }

    public void setItemLongClickListener(BrandsAdapter.ItemLongClickListener ic) {
        this.itemLongClickListener = ic;
    }

    
}
