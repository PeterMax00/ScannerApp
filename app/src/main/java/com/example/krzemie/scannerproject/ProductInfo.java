package com.example.krzemie.scannerproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ProductInfo extends AppCompatActivity {

    public static  Button deleteButton;
    public static Button updateButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);

        final EditText inProductCode = (EditText) findViewById(R.id.inputProductCode);
        final EditText inProductName = (EditText) findViewById(R.id.inputProductName);
        final EditText inQuantity = (EditText) findViewById(R.id.inputQuantity);
        final TextView inDatePicked = (TextView) findViewById(R.id.datePicked);

        ManageDatabase md = new ManageDatabase(this);

        Bundle b = getIntent().getExtras();
        Long productId = b.getLong("productId");

        final Product p = md.takeProduct(productId);




        inProductCode.setText(p.getProductCode());
        inProductName.setText(p.getProductName());
        inQuantity.setText(p.getQuantity().toString());
        inDatePicked.setText(p.getExpireDate());


    }
    public void dialogeventDelete(View view){

        deleteButton  = (Button) findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder altDelete = new AlertDialog.Builder(ProductInfo.this);
                altDelete.setMessage("Do you want to delete this record?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //ManageDatabase db = new ManageDatabase(getApplicationContext());
                                ManageDatabase md = new ManageDatabase(getApplicationContext());

                                Bundle b = getIntent().getExtras();
                                Long productId = b.getLong("productId");
                                System.out.print("DEBUG NR ID:"+productId);
                                Product p = md.takeProduct(productId);
                                System.out.println("DEBUG NAZWA:"+p.getProductName());
                                md.deleteProduct(p.getProductId());
                                Intent intent = new Intent(getApplicationContext(),tabs.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        });

                AlertDialog alert = altDelete.create();
                alert.setTitle("IMPORTANT");
                alert.show();
            }
        });

    }

    public void dialogeventUpdate (View view){

        updateButton  = (Button) findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder altDelete = new AlertDialog.Builder(ProductInfo.this);
                altDelete.setMessage("Do you want to update this record?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //ManageDatabase db = new ManageDatabase(getApplicationContext());
                                ManageDatabase md = new ManageDatabase(getApplicationContext());
                                final EditText inProductCode = (EditText) findViewById(R.id.inputProductCode);
                                final EditText inProductName = (EditText) findViewById(R.id.inputProductName);
                                final EditText inQuantity = (EditText) findViewById(R.id.inputQuantity);
                                final TextView inDatePicked = (TextView) findViewById(R.id.datePicked);

                                Bundle b = getIntent().getExtras();
                                Long productId = b.getLong("productId");
                                System.out.print("DEBUG NR ID:"+productId);
                                Product product = md.takeProduct(productId);

                                if (TextUtils.isEmpty(inProductCode.getText().toString())) {
                                    inProductCode.setError("Please provide barcode");
                                    return;
                                }
                                if (TextUtils.isEmpty(inProductName.getText().toString())) {
                                    inProductName.setError("Please provide name");
                                    return;
                                }

                                product.setProductCode(inProductCode.getText().toString());
                                product.setProductName(inProductName.getText().toString());
                                String value = inQuantity.getText().toString();
                                int finalValue = Integer.parseInt(value);
                                product.setQuantity(finalValue);
                                product.setExpireDate(inDatePicked.getText().toString());

                                md.updateProduct(product);



                                Intent intent = new Intent(getApplicationContext(),ProductInfo.class);
                                intent.putExtra("productId",product.getProductId());
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        });

                AlertDialog alert = altDelete.create();
                alert.setTitle("IMPORTANT");
                alert.show();
            }
        });

    }
}
