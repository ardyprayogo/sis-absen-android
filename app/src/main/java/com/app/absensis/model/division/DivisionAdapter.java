package com.app.absensis.model.division;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.absensis.R;

import java.util.ArrayList;

public class DivisionAdapter extends RecyclerView.Adapter<DivisionAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Division> mList = new ArrayList<>();
    private DivisionAdapterInterface mInterface;

    public DivisionAdapter(Context mContext, DivisionAdapterInterface mInterface) {
        this.mContext = mContext;
        this.mInterface = mInterface;
    }

    @NonNull
    @Override
    public DivisionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_1item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DivisionAdapter.ViewHolder holder, int position) {
        holder.tvList.setText(mList.get(position).getDivisionName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterface.OnClick(mList.get(position));
            }
        });
    }

    public void setList(ArrayList<Division> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvList;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvList = itemView.findViewById(R.id.tv_list);
        }
    }

    public interface DivisionAdapterInterface {
        void OnClick(Division division);
    }
}
