package com.thesis.visageapp.activities;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.thesis.visageapp.R;
import com.thesis.visageapp.objects.Product;
import com.thesis.visageapp.objects.ProductListFragment;

public class ProductsActivity extends AppCompatActivity implements ProductListFragment.ProductListControllerCallback<Product>{

    private ProductListFragment productListFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        FragmentManager fragmentManager = getSupportFragmentManager();
        productListFragment = new ProductListFragment();

    }

    @Override
    public void onItemClicked(Product product) {
        //Need to swap fragments when this happens, passing the data from this item to it.
        Bundle bundle = new Bundle();
        bundle.putParcelable("item", (Parcelable) product);

        //productDetailsFragment.setArguments(bundle);

    }
}
