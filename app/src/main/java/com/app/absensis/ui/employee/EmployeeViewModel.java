package com.app.absensis.ui.employee;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.absensis.model.division.Division;
import com.app.absensis.model.division.DivisionRepository;
import com.app.absensis.model.employee.Employee;
import com.app.absensis.model.employee.EmployeeRepository;
import com.app.absensis.model.level.Level;
import com.app.absensis.model.level.LevelRepository;
import com.app.absensis.network.ViewModelErrorListener;

import java.util.ArrayList;

public class EmployeeViewModel extends ViewModel {

    private EmployeeRepository repository;
    private DivisionRepository divRepository;
    private LevelRepository levRepository;
    private MutableLiveData<ArrayList<Employee>> mutableLiveData;
    private MutableLiveData<ArrayList<Division>> mutableLiveDataDiv;
    private MutableLiveData<ArrayList<Level>> mutableLiveDataDLev;

    public MutableLiveData<ArrayList<Employee>> getEmployees(Context context, ViewModelErrorListener listener) {
        if (repository == null) {
            repository = new EmployeeRepository(listener);
            mutableLiveData = repository.getEmployees(context);
        }
        return mutableLiveData;
    }

    public MutableLiveData<ArrayList<Division>> getDivisions(Context context, ViewModelErrorListener listener) {
        if (divRepository == null) {
            divRepository = new DivisionRepository(listener);
            mutableLiveDataDiv = divRepository.getDivisions(context);
        }
        return mutableLiveDataDiv;
    }

    public MutableLiveData<ArrayList<Level>> getLevels(Context context, ViewModelErrorListener listener) {
        if (levRepository == null) {
            levRepository = new LevelRepository(listener);
            mutableLiveDataDLev = levRepository.getLevels(context);
        }
        return mutableLiveDataDLev;
    }

}
