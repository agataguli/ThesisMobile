package com.thesis.visageapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.thesis.visageapp.R;
import com.thesis.visageapp.domain.Cart;
import com.thesis.visageapp.objects.ProductListAdapter;

import java.util.List;

public class CartListFragment extends android.support.v4.app.Fragment {
    private CartDetailsFragmentCallback callback;
    private Cart cart;

    /*Decided to not implement this. */
    public interface CartDetailsFragmentCallback<T> {
        List<T> getCartIetms();
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_list_layout, container, false);

        TextView empty = (TextView) view.findViewById(R.id.empty);

        ListView list = (ListView) view.findViewById(R.id.list);
        list.setAdapter(new ProductListAdapter(getActivity(), R.layout.product_list_row, cart.getAllProducts()));
        list.setEmptyView(empty);

        return view;
    }
}