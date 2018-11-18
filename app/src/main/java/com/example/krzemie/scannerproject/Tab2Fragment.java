package com.example.krzemie.scannerproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Tab2Fragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_products_list,container,false);

        ListView mListView = (ListView) view.findViewById(R.id.listView);



        ManageDatabase md = new ManageDatabase(getActivity());
        ArrayList<Product> productCodes = new ArrayList<>();
        for(Product p: md.takeAllProductsCodeOnly()){

            productCodes.add(p);
        }

        GeneralProductListAdapter adapter = new GeneralProductListAdapter(getActivity(),R.layout.adapter_general_products_view_layout,productCodes);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Product p = (Product) parent.getAdapter().getItem(position);
                ManageDatabase db = new ManageDatabase(getActivity());
                Integer suma = db.countProducts(p.getProductCode());
                Toast.makeText(parent.getContext(),"Quantity: "+ suma.toString(), Toast.LENGTH_SHORT).show();


//                Intent intent = new Intent(getActivity(),ProductInfo.class);
//                intent.putExtra("productCode",p.getProductCode());
//                startActivity(intent);





            }
        });


        return view;
    }
}
