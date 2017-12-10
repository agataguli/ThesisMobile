package com.thesis.visageapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.thesis.visageapp.R;
import com.thesis.visageapp.helpers.RequestResponseStaticPartsHelper;
import com.thesis.visageapp.helpers.UrlHelper;
import com.thesis.visageapp.processors.VolleySingleton;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProductListActivity extends AppCompatActivity {
    private static final String TAG = "ProductListActivity";
    private Bundle extras;
    private List productListList;

    @Bind(R.id.button_back_l)
    Button backButton;
    @Bind(R.id.button_filter_l)
    Button filterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);

        this.productListList = new ArrayList();
        this.extras = getIntent().getExtras();

        if (extras.getString(RequestResponseStaticPartsHelper.LIST_FILTER_PRODUCT_FILTER).equals(RequestResponseStaticPartsHelper.LIST_FILTER_ALL)) {
            this.fillProductList();
        } else {
            this.processFilledList(extras.getString(RequestResponseStaticPartsHelper.LIST_FILTER_PRODUCT_FILTER));
        }

        this.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMenuActivity();
            }
        });

        this.filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProductListActivityWithFilter();
            }
        });

    }

    private void startProductListActivityWithFilter() {
        Intent intent = new Intent(this, FilterProductsActivity.class);
        intent.putExtras(this.extras);
        startActivity(intent);
        finish();
    }


    private void fillProductList() {
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlHelper.getGetAllProductUrl(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        processFilledList(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
                Toast.makeText(getBaseContext(), getResources().getString(R.string.productAllRequestError),
                        Toast.LENGTH_SHORT).show();
            }
        });
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    ;

    private void processFilledList(String response) {
        this.productListList = RequestResponseStaticPartsHelper.proceessProductsStringJson(response);
        // tutaj Ewelina masz juz w liście productListLIst całą produktów z bazy.
        // teraz weź to zmapuj na productListView.
    }

    private void backToMenuActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtras(this.extras);
        startActivity(intent);
        finish();
    }
}
