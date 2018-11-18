package com.example.krzemie.scannerproject;

import android.app.DatePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class SaveCode extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());

        TextView datePicked = (TextView) findViewById(R.id.datePicked);
        datePicked.setText(currentDateString);




    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_code);
        final EditText inProductCode = (EditText) findViewById(R.id.inputProductCode);
        final EditText inProductName = (EditText) findViewById(R.id.inputProductName);
        final EditText inQuantity = (EditText) findViewById(R.id.inputQuantity);
        final TextView inDatePicked = (TextView) findViewById(R.id.datePicked);
        final ManageDatabase md = new ManageDatabase(this);

        Button pickDate = (Button) findViewById(R.id.pickDate);
        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");

            }
        });

        Bundle b = getIntent().getExtras();
        String code = b.getString("code");
        inProductCode.setText(code);

        //CHECK if code exists in system


        if(md.checkIfExists(code)==true){
            Product p = md.takeProductbyCode(code);

            inProductName.setText(p.getProductName());
        }else{
            Toast.makeText(SaveCode.this,"No found product", Toast.LENGTH_SHORT).show();
        }


        System.out.println("DEBUG CODE: " + code);
        Button buttonInsert = (Button) findViewById(R.id.insertProduct);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(inProductCode.getText().toString())) {
                    inProductCode.setError("Please provide barcode");
                    return;
                }
                if (TextUtils.isEmpty(inProductName.getText().toString())) {
                    inProductName.setError("Please provide name");
                    return;
                }
                Product product = new Product();
                product.setProductCode(inProductCode.getText().toString());
                product.setProductName(inProductName.getText().toString());
                String value = inQuantity.getText().toString();
                int finalValue = Integer.parseInt(value);
                product.setQuantity(finalValue);
                product.setExpireDate(inDatePicked.getText().toString());

                toastMe(md.addProduct(product));

                inProductCode.setText("");
                inProductName.setText("");
                inQuantity.setText("1");
                inDatePicked.setText("");

            }
        });
    }

    public void toastMe(boolean success) {
        if (success) {
            Toast myToast = Toast.makeText(this, "RECORD INSERTED", Toast.LENGTH_SHORT);
            myToast.show();
        } else {
            Toast myToast = Toast.makeText(this, "FAILED TO INSERT", Toast.LENGTH_SHORT);
            myToast.show();
        }

    }


}
