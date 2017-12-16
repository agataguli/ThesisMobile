package com.thesis.visageapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.thesis.visageapp.R;
import com.thesis.visageapp.objects.Product;
import com.thesis.visageapp.objects.ProductListFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProductsActivity extends AppCompatActivity implements ProductListFragment.ProductListControllerCallback<Product>{

    @Bind(R.id.button_back_l)
    Button backButton;
    @Bind(R.id.button_filter_l)
    Button filterButton;

    private ProductListFragment productListFragment;
    private Bundle extras;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        ButterKnife.bind(this);
        this.extras = getIntent().getExtras();

        FragmentManager fragmentManager = getSupportFragmentManager();
        productListFragment = new ProductListFragment();

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

    @Override
    public void onItemClicked(Product product) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("item", (Parcelable) product);
    }

    private void startProductListActivityWithFilter() {
        Intent intent = new Intent(this, FilterProductsActivity.class);
        intent.putExtras(this.extras);
        startActivity(intent);
        finish();
    }

    private void backToMenuActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtras(this.extras);
        startActivity(intent);
        finish();
    }
}
