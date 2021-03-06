package com.example.krzemie.scannerproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GeneralProductListAdapter extends ArrayAdapter<Product> {

    private Context mContext;
    int mResource;



    public GeneralProductListAdapter(Context context, int resource, ArrayList<Product> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView,ViewGroup parent) {

       // Long id = getItem(position).getProductId();
        String name = getItem(position).getProductName();
        String code = getItem(position).getProductCode();
        //Integer quantity = getItem(position).getQuantity();
      //  String date = getItem(position).getExpireDate();

       // Product product = new Product(id,name,code,quantity,date);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView tvName = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvQuantity = (TextView) convertView.findViewById(R.id.textView2);
        //TextView tvCode = (TextView) convertView.findViewById(R.id.textView2);
      //  TextView tvDate = (TextView) convertView.findViewById(R.id.textView4);
        ManageDatabase db = new ManageDatabase(getContext());
        Integer suma = db.countProducts(getItem(position).getProductCode());

       // tvCode.setText(code);
        tvName.setText(name+"\n["+code+"]");
        tvQuantity.setText("Quantity: "+suma.toString());
      //  tvDate.setText("Expiration Date: "+date);
        return  convertView;

    }
}
