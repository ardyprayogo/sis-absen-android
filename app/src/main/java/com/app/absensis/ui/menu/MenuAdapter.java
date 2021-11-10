package com.app.absensis.ui.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.absensis.R;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<MenuModel> mList;
    private ManuAdapterListener listener;

    public MenuAdapter(Context mContext, ArrayList<MenuModel> mList, ManuAdapterListener listener) {
        this.mContext = mContext;
        this.mList = mList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_menu, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.ViewHolder holder, int position) {
        MenuModel menuModel = mList.get(position);
        holder.tvMenu.setText(menuModel.getName());
        holder.ivMenu.setImageResource(menuModel.getIcon());
        holder.llMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnClick(menuModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvMenu;
        private ImageView ivMenu;
        private LinearLayout llMenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMenu = itemView.findViewById(R.id.tv_menu);
            ivMenu = itemView.findViewById(R.id.iv_menu);
            llMenu = itemView.findViewById(R.id.ll_menu);
        }
    }

    public interface ManuAdapterListener {
        void OnClick(MenuModel menu);
    }
}
