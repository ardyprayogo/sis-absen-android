package com.app.absensis.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.absensis.R;
import com.app.absensis.constant.MenuConst;
import com.app.absensis.ui.BaseFragment;
import com.app.absensis.ui.attendance.AttendanceActivity;
import com.app.absensis.ui.division.DivisionActivity;
import com.app.absensis.ui.employee.EmployeeActivity;
import com.app.absensis.ui.level.LevelActivity;
import com.app.absensis.ui.menu.MenuAdapter;
import com.app.absensis.ui.menu.MenuModel;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment {

    private RecyclerView rvMainMenu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
    }

    private void initUI(View view) {
        rvMainMenu = view.findViewById(R.id.rv_main_menu);
        rvMainMenu.setHasFixedSize(true);
        rvMainMenu.setLayoutManager(new GridLayoutManager(getContext(), 4));

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
                }
                if (i != null)
                    startActivity(i);
            }
        });
        rvMainMenu.setAdapter(adapter);
    }

}
