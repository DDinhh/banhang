package com.example.banhang.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.banhang.R;
import com.example.banhang.activity.ChiTietSanPham;
import com.example.banhang.model.Sanpham;
import com.example.banhang.ultil.CheckConnection;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanphamkhacAdapter  extends RecyclerView.Adapter<SanphamkhacAdapter.ItemHolder> {
    Context context;
    ArrayList<Sanpham> arraysanpham;

    public SanphamkhacAdapter(Context context, ArrayList<Sanpham> arraysanpham) {
        this.context = context;
        this.arraysanpham = arraysanpham;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_laptop,null);
        ItemHolder itemHolder = new ItemHolder(v);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Sanpham sanpham =  arraysanpham.get(position);
        holder.txttenlaptop.setText(sanpham.getTensanpham().trim());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgialaptop.setText(decimalFormat.format(sanpham.getGiasanpham()) + " đ");
        holder.txtmotalaptop.setMaxLines(2);
        holder.txtmotalaptop.setEllipsize(TextUtils.TruncateAt.END);
        holder.txtmotalaptop.setText(sanpham.getMotasanpham());
        Glide.with(context).load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.cho)
                .into(holder.imglaptop);
        //  Picasso.with(context).load(sanpham.getHinhanhsanpham()).placeholder(R.drawable.cho).error(R.drawable.loi).into(holder.imghinhsanpham);
    }

    @Override
    public int getItemCount() {
        return arraysanpham.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public TextView txttenlaptop,txtgialaptop,txtmotalaptop;
        public ImageView imglaptop;

        public ItemHolder( View itemView) {
            super(itemView);
            txttenlaptop = itemView.findViewById(R.id.textviewlaptop);
            txtgialaptop = itemView.findViewById(R.id.textviewgialaptop);
            txtmotalaptop = itemView.findViewById(R.id.textviewmotalaptop);
            imglaptop = itemView.findViewById(R.id.imageviewlaptop);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(context, ChiTietSanPham.class);
                    intent.putExtra("thongtinsanpham",arraysanpham.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CheckConnection.ShowToast_Short(context,arraysanpham.get(getPosition()).getTensanpham());
                    context.startActivity(intent);
                }
            });
        }
    }
}