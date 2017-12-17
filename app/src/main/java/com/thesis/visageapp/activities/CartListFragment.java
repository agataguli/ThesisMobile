package com.thesis.visageapp.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.thesis.visageapp.R;
import com.thesis.visageapp.domain.Cart;
import com.thesis.visageapp.domain.Product;
import com.thesis.visageapp.objects.ProductListAdapter;

public class CartListFragment extends Fragment implements ProductListAdapter.RemoveButtonListener {
    private Cart cart;
    private ProductListAdapter listAdapter;

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_list_layout, container, false);

        TextView empty = (TextView) view.findViewById(R.id.empty);
//pobierac liste koszyka z tindyD

        ListView list = (ListView) view.findViewById(R.id.list);
        listAdapter = new ProductListAdapter(getActivity(), R.layout.product_list_cart_row, cart.getAllProducts(),this.getTag());
        listAdapter.setRemoveButtonListener(this);
        list.setAdapter(listAdapter);
        list.setEmptyView(empty);


        return view;
    }


   @Override
    public void onButtonClickListener(int position) {
        Toast.makeText(this.getContext(),getResources().getString(R.string.deletedCartItem),
                Toast.LENGTH_SHORT).show();
        Product product = (Product) cart.getAllProducts().get(position);
        cart.removeProduct(product);
    }
}