package com.example.krzemie.scannerproject;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ManageDatabase m = new ManageDatabase(this);
        m.takeAllProductsCodeOnly();

        Button buttonScan = (Button) findViewById(R.id.scan_button);
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ScanActivity.class);
                startActivity(intent);

            }
        });


        Button buttonAdd = (Button) findViewById(R.id.add_button);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),createNewData.class);
                startActivity(intent);
            }
        });

        Button buttonList = (Button) findViewById(R.id.list_button);
        buttonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),tabs.class);
                startActivity(intent);
            }
        });



    }

}
