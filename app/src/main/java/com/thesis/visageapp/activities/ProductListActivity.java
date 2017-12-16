package com.thesis.visageapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import butterknife.ButterKnife;

public class ProductListActivity extends AppCompatActivity {
    private static final String TAG = "ProductListActivity";

    private List productListList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);

        this.productListList = new ArrayList();

/*
        if (extras.getString(RequestResponseStaticPartsHelper.LIST_FILTER_PRODUCT_URL).equals(RequestResponseStaticPartsHelper.LIST_FILTER_ALL)) {
            this.fillListWithAllAvailableProducts();
        } else {
            this.processFillingListWithResponseProducts(extras.getString(RequestResponseStaticPartsHelper.LIST_FILTER_PRODUCT_URL));
        }*/



    }




    private void fillListWithAllAvailableProducts() {
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlHelper.getGetAllProductUrl(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        processFillingListWithResponseProducts(response);
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

    private void processFillingListWithResponseProducts(String response) {
        this.productListList = RequestResponseStaticPartsHelper.proceessProductsStringJson(response);
        // tutaj Ewelina masz juz w liście productListLIst całą produktów z bazy.
        // teraz weź to zmapuj na productListView.
    }


}
