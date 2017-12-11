package com.thesis.visageapp.objects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.thesis.visageapp.R;

import java.util.List;

public class ProductListAdapter extends ArrayAdapter<Product> {
    private List<Product> items;

    public ProductListAdapter(Context context, int resource, List<Product> objects) {
        super(context, resource, objects);

        items = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public Product getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.product_list_row, parent, false);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.productList_name);
            holder.price = (TextView) convertView.findViewById(R.id.productList_price);
            holder.icon = (ImageView) convertView.findViewById(R.id.productList_image);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //bind data to views.
        //holder.icon.setImageDrawable(getItem(position).getImageLink());
        holder.name.setText(getItem(position).getName());
        holder.price.setText(String.valueOf(getItem(position).getGrossValue()));

        return convertView;
    }

    private static class ViewHolder {
        TextView name, price;
        ImageView icon;
    }


}