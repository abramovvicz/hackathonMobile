package com.harman.borsuki.carpooling.ui.passenger;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PassengerViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PassengerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is passenger fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}