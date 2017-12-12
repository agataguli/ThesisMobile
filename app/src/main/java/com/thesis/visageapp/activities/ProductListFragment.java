package com.thesis.visageapp.activities;

import android.app.ListFragment;
import android.content.ClipData;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.thesis.visageapp.R;
import com.thesis.visageapp.helpers.RequestResponseStaticPartsHelper;
import com.thesis.visageapp.helpers.UrlHelper;
import com.thesis.visageapp.objects.Product;
import com.thesis.visageapp.objects.ProductListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ProductListFragment extends android.support.v4.app.ListFragment {
    private ProductListControllerCallback callback;

    private ProductListAdapter listAdapter;

    public interface ProductListControllerCallback<T> {
        void onItemClicked(T item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_layout, container, false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (ProductListControllerCallback) getActivity();

        } catch (ClassCastException e) {
            throw new IllegalStateException("Hosts must implement ProductListControllerCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        callback = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.i("activity created", "called");

        listAdapter = new ProductListAdapter(getActivity(), R.layout.product_list_row, new ArrayList<Product>());

        setListAdapter(listAdapter);

        getData();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        callback.onItemClicked(this.getListAdapter().getItem(position));
    }

    //Uses volley to asynchronously download data from the rest source and fills the list of items.
    private void getData() {
        Log.i("getData", "called");

        RequestQueue que = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            que = Volley.newRequestQueue(getContext());
        }

        StringRequest request = new StringRequest(
                Request.Method.GET,
                UrlHelper.getGetAllProductUrl(),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.i("onResponse", response);

                        List<Product> temp = RequestResponseStaticPartsHelper.proceessProductsStringJson(response);

                        listAdapter.addAll(temp);
                        listAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley error", error.getMessage());
                    }
                }
        );

        que.add(request);
    }

}
