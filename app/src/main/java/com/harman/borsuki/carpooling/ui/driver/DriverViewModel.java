package com.harman.borsuki.carpooling.ui.driver;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DriverViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DriverViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is driver fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}