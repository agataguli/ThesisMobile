package com.thesis.visageapp.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.thesis.visageapp.R;
import com.thesis.visageapp.helpers.RequestResponseStaticPartsHelper;
import com.thesis.visageapp.helpers.UrlHelper;
import com.thesis.visageapp.objects.Product;
import com.thesis.visageapp.processors.VolleySingleton;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProductListActivity extends ListActivity{
    private static final String TAG = "ProductListActivity";
    private Bundle extras;
    private List productListList;

   @Bind(R.id.button_back_l)
    Button backButton;
    @Bind(R.id.button_filter_l)
    Button filterButton;
    @Bind(R.id.text_empty_l)
    TextView emptyText;
    @Bind(R.id.products_list)
    ListView productListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);

        this.productListList = new ArrayList();
        this.fillProductList();
        this.extras = getIntent().getExtras();

        this.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMenuActivity();
            }
        });

        this.setListAdapter(new ArrayAdapter<Product>(this,R.layout.list_item,productListList));
        this.productListView.setTextFilterEnabled(true);

        this.productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Intent intent = new Intent(getApplicationContext(), ProductDetailsActivity.class);
                //intent.putExtras(extras);
                //intent.putExtra(RequestResponseStaticPartsHelper.PRODUCT_ID, id);
                //startActivity(intent);
                Toast.makeText(getApplicationContext(),
                        "Click ListItem Number " + position, Toast.LENGTH_LONG)
                        .show();
            }
        });
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
    };

    private void processFilledList(String response) {
        this.productListList = RequestResponseStaticPartsHelper.proceessProductsStringJson(response);
        // tutaj Ewelina masz juz w liście productListLIst całą produktów z bazy.
        // teraz weź to zmapuj na productListView.
    }

    private void backToMenuActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtras(extras);
        startActivity(intent);
        finish();
    }
}
