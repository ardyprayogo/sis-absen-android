package com.app.absensis.ui.employee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.absensis.R;
import com.app.absensis.model.employee.Employee;

import java.util.ArrayList;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {

    private ArrayList<Employee> mList = new ArrayList<>();
    private Context mContext;
    private EmployeeAdapterInterface mInterface;

    public EmployeeAdapter(Context mContext, EmployeeAdapterInterface mInterface) {
        this.mContext = mContext;
        this.mInterface = mInterface;
    }

    @NonNull
    @Override
    public EmployeeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_employee, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeAdapter.ViewHolder holder, int position) {
        Employee employee = mList.get(position);
        holder.tvName.setText(employee.getEmployeeName());
        holder.tvDivisionLevel.setText(employee.getDivisionName()
                +" ("
                +employee.getLevelName()
                +")");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterface.OnClickListener(employee);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(ArrayList<Employee> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvDivisionLevel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDivisionLevel = itemView.findViewById(R.id.tv_division_level);
        }
    }

    public interface EmployeeAdapterInterface {
        void OnClickListener(Employee employee);
    }
}
