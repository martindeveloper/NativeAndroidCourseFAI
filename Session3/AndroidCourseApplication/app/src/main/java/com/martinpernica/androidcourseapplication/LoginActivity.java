package com.martinpernica.androidcourseapplication;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private ProgressBar mLoginProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginProgressBar = (ProgressBar)findViewById(R.id.login_progressbar);
    }

    public void onBtnTap(View view) {
        mLoginProgressBar.setVisibility(View.VISIBLE);

        // TODO: Connect login to API
        // Simulate login delay
        final Handler loginDelayHandler = new Handler();
        loginDelayHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Hide progressbar
                mLoginProgressBar.setVisibility(View.INVISIBLE);

                // Navigate to news activity
                Intent navigationIntent = new Intent(LoginActivity.this, NewsActivity.class);
                startActivity(navigationIntent);

                // Show toast message
                Toast loginToast = Toast.makeText(LoginActivity.this, "Login was successful, welcome!", Toast.LENGTH_SHORT);
                loginToast.show();
            }
        }, 1 * 1000);
    }
}
