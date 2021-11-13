package com.app.absensis.ui.level;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.absensis.R;
import com.app.absensis.model.level.Level;

import java.util.ArrayList;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.ViewHolder> {

    private ArrayList<Level> mList = new ArrayList<>();
    private Context mContext;
    private LevelAdapterInterface mInterface;

    public LevelAdapter(Context mContext, LevelAdapterInterface mInterface) {
        this.mContext = mContext;
        this.mInterface = mInterface;
    }

    @NonNull
    @Override
    public LevelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_1item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LevelAdapter.ViewHolder holder, int position) {
        Level level = mList.get(position);
        holder.tvList.setText(level.getLevelName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterface.OnClickListener(level);
            }
        });
    }

    public void setmList(ArrayList<Level> mList) {
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

    public interface LevelAdapterInterface {
        void OnClickListener(Level level);
    }
}
