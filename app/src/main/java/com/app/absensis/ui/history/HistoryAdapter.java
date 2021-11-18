package com.app.absensis.ui.history;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.app.absensis.R;
import com.app.absensis.model.attendance.Attendance;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private ArrayList<Attendance> mList = new ArrayList<>();
    private Context mContext;
    private HistoryAdapterInterface mInterface;

    public HistoryAdapter(Context mContext, HistoryAdapterInterface mInterface) {
        this.mContext = mContext;
        this.mInterface = mInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_attendance, parent, false);
        return new ViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Attendance attendance = mList.get(position);

        holder.tvName.setText(attendance.getEmployeeName());
        holder.tvDate.setText(attendance.getDate());
        holder.tvCi.setText(attendance.getCheckIn());
        holder.tvCo.setText(attendance.getCheckOut());
        holder.tvWh.setText(attendance.getWorkHour());

        if (attendance.isLate()) {
            holder.clAttendance.setBackgroundColor(mContext.getColor(R.color.flat_red_20));
        } else {
            holder.clAttendance.setBackgroundColor(mContext.getColor(R.color.white));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterface.OnClickListener(attendance);
            }
        });
    }

    public void setList(ArrayList<Attendance> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface HistoryAdapterInterface {
        void OnClickListener(Attendance attendance);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName, tvDate, tvCi, tvCo, tvWh;
        private ConstraintLayout clAttendance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvCi = itemView.findViewById(R.id.tv_ci);
            tvCo = itemView.findViewById(R.id.tv_co);
            tvWh = itemView.findViewById(R.id.tv_wh);

            clAttendance = itemView.findViewById(R.id.cl_attendance);
        }
    }
}
