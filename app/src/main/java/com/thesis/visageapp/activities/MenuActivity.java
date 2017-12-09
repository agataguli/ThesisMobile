package com.thesis.visageapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.thesis.visageapp.R;
import com.thesis.visageapp.helpers.RequestResponseHelper;
import com.thesis.visageapp.objects.User;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MenuActivity extends AppCompatActivity {
    User user = new User();

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

        if (savedInstanceState != null) {
            Log.d("o", "User on board");
            user = RequestResponseHelper.processUserStringJSON(savedInstanceState.getString(
                    RequestResponseHelper.USER_BUNDLE));
        }

        this.userAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                intent.putExtra(RequestResponseHelper.USER_BUNDLE, new Gson().toJson(user));
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
