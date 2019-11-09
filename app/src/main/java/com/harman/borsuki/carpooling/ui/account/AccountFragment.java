package com.harman.borsuki.carpooling.ui.account;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.harman.borsuki.carpooling.AfterLogin;
import com.harman.borsuki.carpooling.DriverActivity;
import com.harman.borsuki.carpooling.MainActivity;
import com.harman.borsuki.carpooling.R;
import com.harman.borsuki.carpooling.UserHomepageActivity;

import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class AccountFragment extends Fragment {
    private static final String TAG = AccountFragment.class.getName();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_account, container, false);


        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Button buttonSignOut = root.findViewById(R.id.btn_sign_out);
        if (currentUser == null) {
            ViewGroup viewGroup = (ViewGroup) buttonSignOut.getParent();
            viewGroup.removeView(buttonSignOut);
        }
        buttonSignOut.setOnClickListener(getSignOut());

        return root;
    }

    private View.OnClickListener getSignOut() {
        return v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this.getContext(), MainActivity.class));
        };
    }


}