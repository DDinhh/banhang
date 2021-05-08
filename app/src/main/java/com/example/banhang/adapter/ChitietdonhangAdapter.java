package com.example.banhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.banhang.R;
import com.example.banhang.model.Chitietdonhang;
import com.example.banhang.model.Donhang;

import java.text.DecimalFormat;
import java.util.List;

public class ChitietdonhangAdapter extends RecyclerView.Adapter<ChitietdonhangAdapter.ViewHolder> {
    List<Chitietdonhang> list;

    Context context;

    public ChitietdonhangAdapter(List<Chitietdonhang> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_chi_tiet_don_hang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chitietdonhang oder = list.get(position);

        holder.tv_product.setText(oder.getTen());
        holder.tv_sz_color.setText("Số lượng: "+oder.getSoluong());

        Glide.with(context).load(oder.getHinhanh())
                .error(R.drawable.cho)
                .placeholder(R.drawable.cho)
                .into(holder.iv_product);
        DecimalFormat df = new DecimalFormat("###,###,###");
        long x = Long.parseLong(oder.getGia());
        holder.tv_price.setText("" + df.format(x) + " đ");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder  {

        TextView tv_product, tv_sz_color, tv_price;
        ImageView iv_product;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_product = itemView.findViewById(R.id.tv_product);
            tv_sz_color = itemView.findViewById(R.id.tv_sz_color);
            iv_product = itemView.findViewById(R.id.iv_product);
            tv_price = itemView.findViewById(R.id.tv_price);

        }


    }


}
