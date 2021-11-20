package com.app.absensis.ui.profile;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.absensis.model.percent.Percent;
import com.app.absensis.model.percent.PercentRepository;
import com.app.absensis.model.profile.Profile;
import com.app.absensis.model.profile.ProfileRepository;
import com.app.absensis.network.ViewModelErrorListener;

public class ProfileViewModel extends ViewModel {

    private ProfileRepository repository;
    private PercentRepository percentRepository;
    private MutableLiveData<Profile> mutableLiveData;
    private MutableLiveData<Percent> percentLiveData;

    public MutableLiveData<Profile> getProfile(Context context, ViewModelErrorListener listener) {
        if (repository == null) {
            repository = new ProfileRepository(listener);
            mutableLiveData = repository.requestProfile(context);
        }
        return mutableLiveData;
    }

    public MutableLiveData<Percent> getPercent(Context context, ViewModelErrorListener listener) {
        if (percentRepository == null) {
            percentRepository = new PercentRepository(listener);
            percentLiveData = percentRepository.getPercent(context);
        }
        return percentLiveData;
    }
}
