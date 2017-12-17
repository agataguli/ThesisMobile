package com.thesis.visageapp.objects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.thesis.visageapp.R;
import com.thesis.visageapp.domain.Product;

import java.util.List;

public class ProductListAdapter extends ArrayAdapter<Product> {
    private List<Product> items;
    private RemoveButtonListener removeButtonListener;
    private String fragmentName;
    private int layoutRow;

    public ProductListAdapter(Context context, int resource, List<Product> objects, String tag) {
        super(context, resource, objects);
        fragmentName = tag;
        items = objects;
        layoutRow = resource;
    }
    public ProductListAdapter(Context context, int resource, List<Product> objects) {
        super(context, resource, objects);
        items = objects;
    }

    public interface RemoveButtonListener {
        public void onButtonClickListener(int position);
    }
    public void setRemoveButtonListener(RemoveButtonListener listener){
        this.removeButtonListener = listener;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(layoutRow, parent, false);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.productList_name);
            holder.price = (TextView) convertView.findViewById(R.id.productList_price);
            holder.icon = (ImageView) convertView.findViewById(R.id.productList_image);
            if(fragmentName.equals("cart_list_fragment")){
                holder.removeCartItem = (Button) convertView.findViewById(R.id.removeCartItem);
                holder.removeCartItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeButtonListener.onButtonClickListener(position);
                        notifyDataSetChanged();
                    }
                });
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //bind data to views.
        //TODO ICON
        //holder.icon.setImageDrawable(getItem(position).getImageLink());
        holder.name.setText(getItem(position).getName());
        holder.price.setText(String.valueOf(getItem(position).getGrossValue()));

        return convertView;
    }

    private static class ViewHolder {
        TextView name, price;
        ImageView icon;
        Button removeCartItem;
    }


}