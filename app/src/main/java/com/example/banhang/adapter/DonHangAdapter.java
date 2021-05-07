package com.example.banhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banhang.R;
import com.example.banhang.model.Donhang;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.ViewHolder>  {
    List<Donhang> list;

    Context context;

    public DonHangAdapter(List<Donhang> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_donhang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Donhang oder=list.get(position);
        if(oder.getNote().isEmpty())
        {
            holder.linearLayout.setVisibility(View.GONE);
        }else{
            holder.linearLayout.setVisibility(View.VISIBLE);

        }
        holder.tv_note.setText(oder.getNote());
        holder.tv_date.setText(oder.getDate());
        holder.tv_address.setText(oder.getAddress());
        DecimalFormat df = new DecimalFormat("###,###,###");
        long x= Long.parseLong(oder.getPrice());
        holder.tv_price.setText(""+df.format(x)+" đ");
        if(oder.getStt()==0)
        {
            holder.background.setBackgroundResource(R.drawable.cst_edittext);
        }else if(oder.getStt()==1)
        {
            holder.background.setBackgroundResource(R.drawable.cst_bg_true);
        }else {
            holder.background.setBackgroundResource(R.drawable.cst_bg_false);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout background,linearLayout;
        TextView tv_date,tv_note,tv_address,tv_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date=itemView.findViewById(R.id.tv_date);
            linearLayout=itemView.findViewById(R.id.layout_note);
            tv_address=itemView.findViewById(R.id.tv_address);
            tv_note=itemView.findViewById(R.id.tv_note);
            tv_price=itemView.findViewById(R.id.tv_price);
            background=itemView.findViewById(R.id.background_user);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(getAdapterPosition(), view);
        }
    }

    private ClickListener listener;

    public void setItemClickListener(ClickListener clickListener) {
        listener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }
}
