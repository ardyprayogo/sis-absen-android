package com.app.absensis.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.absensis.R;
import com.app.absensis.constant.MenuConst;
import com.app.absensis.model.percent.Percent;
import com.app.absensis.model.profile.Profile;
import com.app.absensis.network.ViewModelErrorListener;
import com.app.absensis.ui.BaseFragment;
import com.app.absensis.ui.attendance.AttendanceActivity;
import com.app.absensis.ui.division.DivisionActivity;
import com.app.absensis.ui.employee.EmployeeActivity;
import com.app.absensis.ui.history.HistoryActivity;
import com.app.absensis.ui.level.LevelActivity;
import com.app.absensis.ui.menu.MenuAdapter;
import com.app.absensis.ui.menu.MenuModel;
import com.app.absensis.ui.profile.ProfileViewModel;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment {

    private RecyclerView rvMainMenu;
    private TextView tvHeader, tvWorkDay, tvAttendance, tvPercent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        getProfile();
    }

    private void initUI(View view) {
        tvHeader = view.findViewById(R.id.tv_header);
        rvMainMenu = view.findViewById(R.id.rv_main_menu);
        tvWorkDay = view.findViewById(R.id.tv_work_day);
        tvAttendance = view.findViewById(R.id.tv_attendance);
        tvPercent = view.findViewById(R.id.tv_percent);
        rvMainMenu.setHasFixedSize(true);
        rvMainMenu.setLayoutManager(new GridLayoutManager(getContext(), 3));

        ArrayList<MenuModel> menuModels = MenuConst.getHomeMenu();
        MenuAdapter adapter = new MenuAdapter(getContext(), menuModels, new MenuAdapter.ManuAdapterListener() {
            @Override
            public void OnClick(MenuModel menu) {
                Intent i = null;
                switch (menu.getName()) {
                    case MenuConst.ATTENDANCE:
                        i = new Intent(getActivity(), AttendanceActivity.class);
                        break;
                    case MenuConst.DIVISION:
                        i = new Intent(getActivity(), DivisionActivity.class);
                        break;
                    case MenuConst.LEVEL:
                        i = new Intent(getActivity(), LevelActivity.class);
                        break;
                    case MenuConst.EMPLOYEE:
                        i = new Intent(getActivity(), EmployeeActivity.class);
                        break;
                    case MenuConst.HISTORY:
                        i = new Intent(getActivity(), HistoryActivity.class);
                        break;
                }
                if (i != null)
                    startActivity(i);
            }
        });
        rvMainMenu.setAdapter(adapter);
    }

    private void getProfile() {
        ProfileViewModel profileViewModel = new ProfileViewModel();
        ProfileViewModel viewModel = ViewModelProviders.of(this).get(profileViewModel.getClass());
        viewModel.getProfile(getContext(), new ViewModelErrorListener() {
            @Override
            public void OnErrorListener(String message) {
                showSimpleDialog("Error", message);
            }
        }).observe(getViewLifecycleOwner(), new Observer<Profile>() {
            @Override
            public void onChanged(Profile profile) {
                tvHeader.setText("Halo, "
                        +profile.getEmployeeName()
                        +"\n"
                        +profile.getDivisionName()
                        +" "
                        +profile.getLevelName()
                );
            }
        });
        viewModel.getPercent(getContext(), new ViewModelErrorListener() {
            @Override
            public void OnErrorListener(String message) {
                showSimpleDialog("Error", message);
            }
        }).observe(getViewLifecycleOwner(), new Observer<Percent>() {
            @Override
            public void onChanged(Percent percent) {
                tvAttendance.setText(Integer.toString(percent.getAttendance()));
                tvWorkDay.setText(Integer.toString(percent.getWork()));
                tvPercent.setText(percent.getPercentation());
            }
        });
    }



}
