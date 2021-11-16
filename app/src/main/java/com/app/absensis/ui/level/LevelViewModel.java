package com.app.absensis.ui.level;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.app.absensis.model.level.Level;
import com.app.absensis.model.level.LevelRepository;
import com.app.absensis.network.ViewModelErrorListener;

import java.util.ArrayList;

public class LevelViewModel {

    private LevelRepository repository;
    private MutableLiveData<ArrayList<Level>> liveData;

    public MutableLiveData<ArrayList<Level>> getLevels(Context context, String name, ViewModelErrorListener listener) {
        if (repository == null) {
            repository = new LevelRepository(listener);
            liveData = repository.getLevels(context, name);
        }
        return liveData;
    }

}
