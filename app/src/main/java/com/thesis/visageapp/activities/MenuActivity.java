package com.thesis.visageapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.thesis.visageapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MenuActivity extends AppCompatActivity {

    @Bind(R.id.button_products_m)
    Button productsButton;
    @Bind(R.id.button_user_favorite_products_m)
    Button favoritesButton;
    @Bind(R.id.button_user_cart_m)
    Button cartButton;
    @Bind(R.id.button_user_order_history_m)
    Button orderHistoryButton;
    @Bind(R.id.button_user_account_m)
    Button userAccountButton;
    @Bind(R.id.button_logout_m)
    Button logoutButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);

        this.userAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(getApplicationContext(), UserProfileActivity.class);
                Bundle extras = getIntent().getExtras();
                newIntent.putExtras(extras);
                startActivity(newIntent);
                finish();
            }
        });

        this.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(newIntent);
                finish();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
