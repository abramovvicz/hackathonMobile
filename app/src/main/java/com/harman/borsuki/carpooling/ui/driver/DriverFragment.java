package com.harman.borsuki.carpooling.ui.driver;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.harman.borsuki.carpooling.AfterLogin;
import com.harman.borsuki.carpooling.DriverActivity;
import com.harman.borsuki.carpooling.R;

public class DriverFragment extends Fragment {

    private DriverViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DriverViewModel.class);
        View root = inflater.inflate(R.layout.fragment_driver, container, false);

        startActivity(new Intent(this.getContext(), DriverActivity.class));
        return root;
    }
}