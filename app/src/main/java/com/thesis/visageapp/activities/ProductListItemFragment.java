package com.thesis.visageapp.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.thesis.visageapp.R;
import com.thesis.visageapp.objects.Product;

import butterknife.Bind;


public class ProductListItemFragment extends Fragment {
    private ProductListItemControllerCallback callback;

    public interface ProductListItemControllerCallback {
        void addToCart(Product product);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (ProductListItemControllerCallback) getActivity();
        } catch (ClassCastException e) {
            throw new IllegalStateException("Hosts must implements ProductDetailsCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        callback = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_list_item, container, false);

        //Get the item for this detail screen.
        String productString = getArguments().getString("product");
        final Product product = deserializeFromJson(productString);

       // ImageView iconView = (ImageView) view.findViewById(R.id.icon_image_view_details);

        TextView nameView = (TextView) view.findViewById(R.id.name_text_view_details);

        TextView priceView = (TextView) view.findViewById(R.id.price_text_view_details);

        TextView descriptionView = (TextView) view.findViewById(R.id.description_text_view_details);

        //iconView.setImageDrawable(product.getImageLink();

        nameView.setText(product.getName());

        priceView.setText(String.valueOf(product.getGrossValue()));

        descriptionView.setText(product.getDescription());

        final Button addButton = (Button) view.findViewById(R.id.add_button_details);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.addToCart(product);

                Snackbar.make(addButton, getString( R.string.added_to_cart)+ "" + product.getName(), Snackbar.LENGTH_LONG).show();
            }
        });

        return view;
    }
    public Product deserializeFromJson(String jsonString) {
        Gson gson = new Gson();
        Product product = gson.fromJson(jsonString, Product.class);
        return product;
    }
}


