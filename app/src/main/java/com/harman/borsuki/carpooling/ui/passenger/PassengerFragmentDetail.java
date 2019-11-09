package com.harman.borsuki.carpooling.ui.passenger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.harman.borsuki.carpooling.R;

public class PassengerFragmentDetail extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_passenger_detail, container, false);
        Bundle bundle = getArguments();
        String obj= (String) bundle.getSerializable("temp");
        System.out.println(obj);


        return root;
    }


}
