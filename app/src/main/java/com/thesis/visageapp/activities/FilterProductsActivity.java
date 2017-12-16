package com.thesis.visageapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.thesis.visageapp.R;
import com.thesis.visageapp.helpers.RequestResponseStaticPartsHelper;
import com.thesis.visageapp.helpers.UrlHelper;
import com.thesis.visageapp.helpers.ValidateHelper;
import com.thesis.visageapp.objects.ProductFilter;
import com.thesis.visageapp.objects.User;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FilterProductsActivity extends AppCompatActivity {
    private static final String TAG = "FilterProductsActivity";
    private Bundle extras = new Bundle();
    private User user = new User();
    private ProductFilter productFilter;
    boolean commonFiltering = true;

    @Bind(R.id.input_filter_phrase_f)
    EditText productName;
    @Bind(R.id.input_filter_brand_f)
    EditText productBrand;
    @Bind(R.id.input_filter_price_min_f)
    EditText productPriceMin;
    @Bind(R.id.input_filter_price_max_f)
    EditText productPriceMax;
    @Bind(R.id.input_filter_category_brushes_f)
    CheckBox productCatBrushes;
    @Bind(R.id.input_filter_category_furniture_f)
    CheckBox productCatFurniture;
    @Bind(R.id.input_filter_category_accessories_f)
    CheckBox productCatAccessories;
    @Bind(R.id.button_back_f)
    Button backButton;
    @Bind(R.id.button_filter_f)
    Button filterButton;
    @Bind(R.id.button_fav_f)
    Button showFavoritesButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_products);
        ButterKnife.bind(this);

        this.extras = getIntent().getExtras();
        if (this.extras != null) {
            this.user = RequestResponseStaticPartsHelper.processUserStringJSON(extras.getString(
                    RequestResponseStaticPartsHelper.USER_BUNDLE));
        }

        this.productFilter = new ProductFilter();
        this.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToProductListActivity();
            }
        });

        this.filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    filterProducts();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        this.showFavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    commonFiltering = false;
                    filterProducts();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void filterProducts() throws JSONException {
        if (this.commonFiltering) {
            Log.d(TAG, "Filtering processed");
            this.setEnableButtons(false);

            if (!ValidateHelper.validateProductFilterData(productName, productBrand, productPriceMin,
                    productPriceMax, productCatBrushes, productCatFurniture, productCatAccessories, this)) {
                this.onFilterFailed();
                return;
            }
        }
        this.processFilterData();

    }

    private void onFilterFailed() {
        Toast.makeText(getBaseContext(), getResources().getString(R.string.filterError),
                Toast.LENGTH_SHORT).show();
        this.setEnableButtons(true);
    }

    private void processFilterData() throws JSONException {
        if (!this.commonFiltering) {
            this.extras.putString(RequestResponseStaticPartsHelper.LIST_FILTER_PRODUCT_URL,
                    UrlHelper.getUserFavProductsUrl(this.user.getUserId()));
        } else {
            this.prepareFormProductFilter();
            JSONObject jsonBody = new JSONObject(new Gson().toJson(this.productFilter));
            final String requestBody = jsonBody.toString();
            this.extras.putString(RequestResponseStaticPartsHelper.LIST_FILTER_PRODUCT_BODY, requestBody);
            this.extras.putString(RequestResponseStaticPartsHelper.LIST_FILTER_PRODUCT_URL,
                    UrlHelper.getFilterUrl());
        }
        Intent intent = new Intent(this, ProductsActivity.class);
        intent.putExtras(this.extras);
        startActivity(intent);
        finish();
    }

    private void prepareFormProductFilter() {
        productFilter.setProductName(this.productName.getText().toString());
        productFilter.setProductBrand(this.productBrand.getText().toString());
        productFilter.setProductPriceMin(this.productPriceMin.getText().toString());
        productFilter.setProductPriceMax(this.productPriceMax.getText().toString());
        productFilter.setProductCategoryAccessories(this.productCatAccessories.isChecked());
        productFilter.setProductCategoryBrushes(this.productCatBrushes.isChecked());
        productFilter.setProductCategoryFurniture(this.productCatFurniture.isChecked());
    }

    private void setEnableButtons(boolean b) {
        this.filterButton.setEnabled(b);
        this.backButton.setEnabled(b);
    }

    private void backToProductListActivity() {
        Intent intent = new Intent(this, ProductsActivity.class);
        intent.putExtras(extras);
        startActivity(intent);
        finish();
    }
}
