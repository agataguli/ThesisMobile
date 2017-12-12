package com.thesis.visageapp.activities;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.thesis.visageapp.R;
import com.thesis.visageapp.objects.Cart;
import com.thesis.visageapp.objects.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity implements ProductListFragment.ProductListControllerCallback<Product>,
        ProductListItemFragment.ProductListItemControllerCallback{

    private ProductListFragment productListFragment;
    private ProductListItemFragment productListItemFragment;
    private CartListFragment cartListFragment;
    private Cart cart;

    private Button cartButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cart = new Cart();
        //jesli istnieja jakies zapisane produkty w koszyku, pobierz je
        List<Product> products = (List<Product>) getLastCustomNonConfigurationInstance();

        if(products != null) {
            cart.setProducts(products);
        } else {
            cart.setProducts(new ArrayList<Product>());
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        //tworzenie lub odtwarzanie istniejacych juz fragmentow
        if (fragmentManager.findFragmentByTag("product_list_fragment") != null) {
            productListFragment = (ProductListFragment) fragmentManager.findFragmentByTag("plf");
        } else {
            productListFragment = new ProductListFragment();
        }
        if (fragmentManager.findFragmentByTag("product_list_item_fragment") != null) {
            productListItemFragment = (ProductListItemFragment) fragmentManager.findFragmentByTag("pdf");
        } else {
            productListItemFragment = new ProductListItemFragment();
        }
        if (fragmentManager.findFragmentByTag("cart_list_fragment") !=null) {
            cartListFragment = (CartListFragment) fragmentManager.findFragmentByTag("clf");
        } else {
            cartListFragment = new CartListFragment();
        }

        cartListFragment.setCart(cart);
        cartButton = (Button) findViewById(R.id.cart_button);
        cartButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View w) {
                cartButtonClicked();
            }
        });

        //jesli to pierwsze uruchomienie to pierwszym ekranem ktory sie pojawia jest lista produktow
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction().add(R.id.main_content,productListFragment, "product_list_fragment").commit();
        }


    }
    private void cartButtonClicked() {
        if(!cartListFragment.isVisible()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("cart_list_fragment")
                    .replace(R.id.main_content,cartListFragment,"cart_list_fragment")
                    .commit();
        }
    }

    @Override
    public void onItemClicked(Product product) {
        //Need to swap fragments when this happens, passing the data from this item to it.
        Bundle bundle = new Bundle();
       // bundle.putParcelable("product", (Parcelable) product);
        bundle.putString("product",serializeToJson(product));

        productListItemFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("product_list_item_fragment")
                .replace(R.id.main_content, productListItemFragment, "product_list_item_fragment")
                .commit();

    }

    @Override
    public void addToCart(Product product) {
        cart.addProduct(product);
        cartButton.setText("" + getString(R.string.number_of_products) + "" + cart.getSize());
    }

    public String serializeToJson(Product product) {
        Gson gson = new Gson();
        String j = gson.toJson(product);
        return j;
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return cart.getAllProducts();
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        cartButton.setText("" + getString(R.string.number_of_products) + "" + cart.getSize());
    }
}
