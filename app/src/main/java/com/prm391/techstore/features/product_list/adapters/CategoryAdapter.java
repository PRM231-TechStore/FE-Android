package com.prm391.techstore.features.product_list.adapters;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.prm391.techstore.R;

public class CategoryAdapter extends BaseAdapter {
    private Context context;
    private String[] categories;
    public CategoryAdapter(Context context, String[] categories){

        this.context = context;
        this.categories = categories;
    }
    @Override
    public int getCount() {
        return categories.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.spinner_item_layout,parent,false);
        TextView categoryTxt = rootView.findViewById(R.id.filterByCategory_TextView);
        categoryTxt.setText(categories[position]);
        return rootView;
    }
}
