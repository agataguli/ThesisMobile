package com.thesis.visageapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.thesis.visageapp.R;
import com.thesis.visageapp.helpers.RequestResponseStaticPartsHelper;

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
                newIntent.putExtras(getIntent().getExtras());
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

        this.productsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(getApplicationContext(), ProductListActivity.class);
                newIntent.putExtras(getIntent().getExtras());
                newIntent.putExtra(RequestResponseStaticPartsHelper.LIST_FILTER_PRODUCT_FILTER, RequestResponseStaticPartsHelper.LIST_FILTER_ALL);
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
