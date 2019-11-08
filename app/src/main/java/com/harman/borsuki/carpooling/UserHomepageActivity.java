
package com.harman.borsuki.carpooling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.logging.Logger;

public class UserHomepageActivity extends AppCompatActivity {
    private static final Logger LOGGER = Logger.getLogger(UserHomepageActivity.class.getName());
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_homepage);

        initialize();
        if(!user.isEmailVerified() && user.getEmail() != null) {
            Toast.makeText(this, "Email not verified, please check your mailbox and verify your email", Toast.LENGTH_LONG).show();
            user.sendEmailVerification();
        }

    }

    private void initialize() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        Button buttonChangeEmail = findViewById(R.id.btn_change_email);
        Button buttonChangePassword = findViewById(R.id.btn_change_password);
        Button buttonSignOut = findViewById(R.id.btn_sign_out);
        Button buttonGoToMap = findViewById(R.id.btn_go_to_map);

        buttonSignOut.setOnClickListener(getSigning_out());
        buttonChangePassword.setOnClickListener(getChangePassword());
        buttonChangeEmail.setOnClickListener(getChangeEmail());
        buttonGoToMap.setOnClickListener(getGoToMap());
    }

    private View.OnClickListener getGoToMap() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHomepageActivity.this, MapsActivity.class));
            }
        };
    }

    private View.OnClickListener getChangeEmail() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LOGGER.info("changing email to borsuki@harman.com");
                user.updateEmail("borsuki@harman.com");
            }
        };
    }

    private View.OnClickListener getChangePassword() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LOGGER.info("changing password to borsuki123");
                user.updatePassword("borsuki123");
            }
        };
    }

    private View.OnClickListener getSigning_out() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LOGGER.info("signing out");
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(UserHomepageActivity.this, MainActivity.class));
                finish();
            }
        };
    }

}
