package com.thesis.visageapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.thesis.visageapp.R;
import com.thesis.visageapp.domain.Cart;
import com.thesis.visageapp.domain.Product;
import com.thesis.visageapp.domain.User;
import com.thesis.visageapp.helpers.RequestResponseStaticPartsHelper;
import com.thesis.visageapp.processors.VolleyRequestProcessor;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity implements ProductListFragment.ProductListControllerCallback<Product>,
        ProductListItemFragment.ProductListItemControllerCallback {

    private ProductListFragment productListFragment;
    private ProductListItemFragment productListItemFragment;
    private CartListFragment cartListFragment;
    private Cart cart;
    private Bundle extras;
    private User user = new User();

    private Button cartButton;
    private Button orderButton;
    private Button filterButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        this.cart = new Cart();
        this.extras = getIntent().getExtras();
        this.user = RequestResponseStaticPartsHelper.processUserStringJSON(extras.getString(
                RequestResponseStaticPartsHelper.USER_BUNDLE));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<Product> products = (List<Product>) getLastCustomNonConfigurationInstance();

        if (products != null) {
            cart.setProducts(products);
        } else {
            cart.setProducts(new ArrayList<Product>());
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
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
        if (fragmentManager.findFragmentByTag("cart_list_fragment") != null) {
            cartListFragment = (CartListFragment) fragmentManager.findFragmentByTag("clf");
        } else {
            cartListFragment = new CartListFragment();
        }

        cartListFragment.setCart(cart);
        cartButton = (Button) findViewById(R.id.cart_button);
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View w) {
                cartButtonClicked();
            }
        });

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction().add(R.id.main_content, productListFragment, "product_list_fragment").commit();
        }

        this.orderButton = (Button) findViewById(R.id.order_button);


        this.filterButton = (Button) findViewById(R.id.button_filter_p);
        this.filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(getApplicationContext(), FilterProductsActivity.class);
                newIntent.putExtras(extras);
                startActivity(newIntent);
                finish();
            }
        });
    }

    private void processOrder() throws JSONException {
        List ids = cart.getOnlyIdsOfProducts();
        if (ids.isEmpty()) {
            Snackbar.make(orderButton, getString(R.string.cannotOrderEmptyCard), Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(orderButton, getString(R.string.orderProcessed), Snackbar.LENGTH_SHORT).show();
            VolleyRequestProcessor.orderProductsFromCart(ids, getBaseContext(), user.getUserId());
            Snackbar.make(orderButton, getString(R.string.orderProcessedComplete), Snackbar.LENGTH_LONG).show();
        }

    }

    private void cartButtonClicked() {
        if (!cartListFragment.isVisible()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("cart_list_fragment")
                    .replace(R.id.main_content, cartListFragment, "cart_list_fragment")
                    .commit();
        }
    }

    @Override
    public void onItemClicked(Product product) {
        // Need to swap fragments when this happens, passing the data from this item to it.
        Bundle bundle = new Bundle();
        // bundle.putParcelable("product", (Parcelable) product);
        bundle.putString("product", serializeToJson(product));

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
        cartButton.setText(getString(R.string.number_of_products) + " " + cart.getSize());
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
