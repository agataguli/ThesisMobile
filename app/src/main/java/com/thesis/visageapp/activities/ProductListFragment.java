package com.thesis.visageapp.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.thesis.visageapp.R;
import com.thesis.visageapp.domain.Product;
import com.thesis.visageapp.helpers.RequestResponseStaticPartsHelper;
import com.thesis.visageapp.helpers.UrlHelper;
import com.thesis.visageapp.objects.ProductListAdapter;
import com.thesis.visageapp.processors.VolleySingleton;

import java.util.ArrayList;
import java.util.List;


public class ProductListFragment extends ListFragment {
    private ProductListControllerCallback callback;
    private ProductListAdapter listAdapter;
    private Bundle extras;
    private boolean filteredData = false;

    public interface ProductListControllerCallback<T> {
        void onItemClicked(T item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_layout, container, false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.extras = getActivity().getIntent().getExtras();
        if(this.extras.getString(RequestResponseStaticPartsHelper.LIST_FILTER_PRODUCT_URL).equals(UrlHelper.getFilterUrl())) {
            this.filteredData = true;
        }
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
        getData();
        listAdapter = new ProductListAdapter(getActivity(), R.layout.product_list_row, new ArrayList<Product>());
        setListAdapter(listAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        callback.onItemClicked(this.getListAdapter().getItem(position));
    }

    private void getData() {
        int method = Request.Method.GET;
        if(this.filteredData) method = Request.Method.POST;
        String url = this.extras.getString(RequestResponseStaticPartsHelper.LIST_FILTER_PRODUCT_URL);
        StringRequest request = new StringRequest(method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
        ){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                String requestBody = null;
                if(filteredData) {
                    requestBody = extras.getString(RequestResponseStaticPartsHelper.LIST_FILTER_PRODUCT_BODY);
                }
                return requestBody == null ? null : requestBody.getBytes();
            }
        };
        VolleySingleton.getInstance(getActivity().getBaseContext()).addToRequestQueue(request);
    }

}