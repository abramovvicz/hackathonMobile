package com.harman.borsuki.carpooling.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("ACCOUNT SETTINGS");
    }

    public LiveData<String> getText() {
        return mText;
    }
}