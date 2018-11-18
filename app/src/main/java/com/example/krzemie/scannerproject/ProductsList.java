package com.example.krzemie.scannerproject;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ProductsList extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);
        final ListView mListView = (ListView) findViewById(R.id.listView);



        final ManageDatabase md = new ManageDatabase(this);
        final ArrayList<Product> productList = new ArrayList<>();
        for(Product p: md.takeAllProducts()){

            productList.add(p);
        }

        ProductListAdapter adapter = new ProductListAdapter(this,R.layout.adapter_view_layout,productList);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Product p = (Product) parent.getAdapter().getItem(position);
                Toast.makeText(parent.getContext(), p.getProductName(), Toast.LENGTH_SHORT).show();

              //Toast.makeText(ProductsList.this,name,Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ProductsList.this,ProductInfo.class);
                intent.putExtra("productId",p.getProductId());
                startActivity(intent);





            }
        });





    }



}
