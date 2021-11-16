package com.app.absensis.ui.division;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.absensis.model.division.Division;
import com.app.absensis.model.division.DivisionRepository;
import com.app.absensis.network.ViewModelErrorListener;

import java.util.ArrayList;

public class DivisionViewModel extends ViewModel {

    private DivisionRepository repository;
    private MutableLiveData<ArrayList<Division>> mutableLiveData;

    public MutableLiveData<ArrayList<Division>> getDivisions(Context context, String name, ViewModelErrorListener listener) {
        if (repository == null) {
            repository = new DivisionRepository(listener);
            mutableLiveData = repository.getDivisions(context, name);
        }
        return mutableLiveData;
    }
}
