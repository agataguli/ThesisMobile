package com.thesis.visageapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.thesis.visageapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    private static final int splashTime = 3000;

    @Bind(R.id.image_login_link_main_activity)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ActivityStarter starter = new ActivityStarter();
        starter.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    private class ActivityStarter extends Thread {
        private boolean loginActivityStarted = false;
        @Override
        public void run() {
            try {
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startLoginActivity();
                    }
                });
                Thread.sleep(splashTime);
            } catch (Exception e) {
                Log.d(getResources().getString(R.string.splashScreen), e.getMessage());
            }
            if(!loginActivityStarted) startLoginActivity();
        }

        private void startLoginActivity() {
            this.loginActivityStarted = true;
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }
    }
}
