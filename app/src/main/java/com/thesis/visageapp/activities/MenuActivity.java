package com.thesis.visageapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.thesis.visageapp.R;
import com.thesis.visageapp.domain.User;
import com.thesis.visageapp.helpers.RequestResponseStaticPartsHelper;
import com.thesis.visageapp.helpers.UrlHelper;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MenuActivity extends AppCompatActivity {
    private Bundle extras = new Bundle();
    User user = new User();

    @Bind(R.id.button_products_m)
    Button productsButton;
    @Bind(R.id.button_fav_m)
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

        this.extras = getIntent().getExtras();
        this.user = RequestResponseStaticPartsHelper.processUserStringJSON(extras.getString
                (RequestResponseStaticPartsHelper.USER_BUNDLE));

        this.userAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(getApplicationContext(), UserProfileActivity.class);
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

        this.productsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(getApplicationContext(), ProductsActivity.class);
                newIntent.putExtras(extras);
                newIntent.putExtra(RequestResponseStaticPartsHelper.LIST_FILTER_PRODUCT_URL, UrlHelper.getGetAllProductUrl());
                startActivity(newIntent);
                finish();
            }
        });

        this.favoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(getApplicationContext(), ProductsActivity.class);
                newIntent.putExtras(extras);
                newIntent.putExtra(RequestResponseStaticPartsHelper.LIST_FILTER_PRODUCT_URL,
                        UrlHelper.getUserFavProductsUrl(user.getUserId()));
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
