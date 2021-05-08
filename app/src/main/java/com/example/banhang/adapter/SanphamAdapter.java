package com.example.banhang.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import androidx.annotation.NonNull;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.banhang.R;
import com.example.banhang.activity.ChiTietSanPham;
import com.example.banhang.model.Sanpham;
import com.example.banhang.ultil.CheckConnection;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanphamAdapter extends RecyclerView.Adapter<SanphamAdapter.ItemHolder> {
    Context context;
    ArrayList<Sanpham> arraysanpham;

    public SanphamAdapter(Context context, ArrayList<Sanpham> arraysanpham) {
        this.context = context;
        this.arraysanpham = arraysanpham;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanphammoinhat,null);
        ItemHolder itemHolder = new ItemHolder(v);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Sanpham sanpham = arraysanpham.get(position);
        holder.txttensanpham.setText(sanpham.getTensanpham().trim());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgiasanpham.setText(decimalFormat.format(sanpham.getGiasanpham()) + " đ");



        Glide.with(context).load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.cho)
                .into(holder.imghinhsanpham);
        if(sanpham.getGiagoc()==null)
        {
            holder.txtgiagoc.setVisibility(View.GONE);
        }else{
            holder.txtgiagoc.setVisibility(View.VISIBLE);
            long x= Long.parseLong(sanpham.getGiagoc());
            String str=decimalFormat.format(x)+ " đ";
            StrikethroughSpan strikethroughSpan=new StrikethroughSpan();
            SpannableString ss=new SpannableString(str);
            ss.setSpan(strikethroughSpan,0,str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.txtgiagoc.setText(ss);

        }

    }

    @Override
    public int getItemCount() {
        return arraysanpham.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imghinhsanpham;
        public TextView txttensanpham,txtgiasanpham,txtgiagoc;

        public ItemHolder( View itemView) {
            super(itemView);
            imghinhsanpham = itemView.findViewById(R.id.imageviewsanpham);
            txtgiasanpham = itemView.findViewById(R.id.textviewgiasanpham);
            txttensanpham = itemView.findViewById(R.id.textviewtensanpham);
            txtgiagoc=itemView.findViewById(R.id.textviewgiagocsanpham);
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
