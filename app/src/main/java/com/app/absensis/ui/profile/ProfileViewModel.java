package com.app.absensis.ui.profile;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.absensis.model.profile.Profile;
import com.app.absensis.model.profile.ProfileRepository;
import com.app.absensis.network.ViewModelErrorListener;

public class ProfileViewModel extends ViewModel {

    private ProfileRepository repository;
    private MutableLiveData<Profile> mutableLiveData;

    public MutableLiveData<Profile> getProfile(Context context, ViewModelErrorListener listener) {
        if (repository == null) {
            repository = new ProfileRepository(listener);
            mutableLiveData = repository.requestProfile(context);
        }
        return mutableLiveData;
    }
}
