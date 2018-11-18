package com.example.krzemie.scannerproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ManageDatabase md = new ManageDatabase(this);
    private ZXingScannerView zXingScannerView;
    public String savedResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_activity);
    }

    public void scan(View view){

        zXingScannerView = new ZXingScannerView(getApplicationContext());
        setContentView(zXingScannerView);
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();

    }

    @Override
    protected void onPause() {
        super.onPause();
        zXingScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {


        Toast.makeText(getApplicationContext(),result.getText(),Toast.LENGTH_SHORT).show();
        savedResult =  result.getText();


        Intent intent = new Intent(ScanActivity.this,SaveCode.class);

        intent.putExtra("code",savedResult);
        System.out.println("SAVED RESULT "+savedResult);

        startActivity(intent);
        finish();


    }
}
