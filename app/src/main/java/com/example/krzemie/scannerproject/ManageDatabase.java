package com.example.krzemie.scannerproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class ManageDatabase extends SQLiteOpenHelper {

    public ManageDatabase(Context context){
        super(context,"test1.db",null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table products("+
                        "productId integer primary key autoincrement,"+
                        "productCode text,"+
                        "productName text,"+
                        "quantity integer,"+
                        "expireDate text);"+
                "");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    public boolean addProduct(Product product){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        System.out.println("code "+product.getProductCode()+" name: "+product.getProductName());
            if(!TextUtils.isEmpty(product.getProductName()) || !TextUtils.isEmpty(product.getProductCode()) ) {
                values.put("productCode", product.getProductCode());
                values.put("productName", product.getProductName());
                values.put("quantity", product.getQuantity());
                values.put("expireDate", product.getExpireDate());
                db.insertOrThrow("products", null, values);
                return true;
            }else{

                return false;
            }

    }

    public void deleteProduct(Long id){
        SQLiteDatabase db = getWritableDatabase();
        String[] arguments = {"" + id};
        db.delete("products","productId=?",arguments);
    }
    public void deleteAllProducts(){
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("delete from products");
    }

    public void updateProduct(Product product){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("productCode",product.getProductCode());
        values.put("productName",product.getProductName());
        values.put("quantity", product.getQuantity());
        values.put("expireDate", product.getExpireDate());
        String args[] = {product.getProductId()+""};
        db.update("products",values,"productId=?",args);
    }

    public List<Product> takeAllProducts(){
        List<Product> products = new LinkedList<Product>();
        String[] columns = {"productId","productCode","productName","quantity","expireDate"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("products", columns, null, null, "productId", null, "productId desc");

        while(cursor.moveToNext()){
            Product product = new Product();
            product.setProductId(cursor.getLong(0));
            product.setProductCode(cursor.getString(1));
            product.setProductName(cursor.getString(2));
            product.setQuantity(cursor.getInt(3));
            product.setExpireDate(cursor.getString(4));
            products.add(product);
        }
        return products;
    }

    public Product takeProduct(Long id){
        Product product = new Product();
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {"productId","productCode","productName","quantity","expireDate"};
        String args[] = {id+""};
        Cursor cursor = db.query("products", columns, "productId=?",args, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
            product.setProductId(cursor.getLong(0));
            product.setProductCode(cursor.getString(1));
            product.setProductName(cursor.getString(2));
            product.setQuantity(cursor.getInt(3));
            product.setExpireDate(cursor.getString(4));
        }
        return product;
    }

    public Product takeProductbyCode(String code){
        Product product = new Product();
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {"productId","productCode","productName","quantity","expireDate"};
        String args[] = {code+""};
        Cursor cursor = db.query("products", columns, "productCode=?",args, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
            product.setProductId(cursor.getLong(0));
            product.setProductCode(cursor.getString(1));
            product.setProductName(cursor.getString(2));
            product.setQuantity(cursor.getInt(3));
            product.setExpireDate(cursor.getString(4));
        }
        return product;
    }

    public boolean checkIfExists(String code){
        Product product = new Product();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT productId FROM products WHERE productCode='"+code+"'",null);
        System.out.println("Liczba ROWS "+cursor.getCount());
        if(cursor.getCount() ==0){
            return false;
        }
        return true;

    }
    public List<Product> takeAllProductsCodeOnly(){
        List<Product> productsCodes = new LinkedList<Product>();
       // String[] columns = {"productId","productCode","productName","quantity","expireDate"};
        SQLiteDatabase db = getReadableDatabase();
        //Cursor cursor = db.query("products", columns, null, null, "productId", null, "productId desc");
        Cursor cursor = db.rawQuery("SELECT  DISTINCT productCode, productName  from products GROUP BY productCode",null);
        while(cursor.moveToNext()){
            Product product = new Product();
            //product.setProductId(cursor.getLong(0));
            product.setProductCode(cursor.getString(0));
            product.setProductName(cursor.getString(1));
          //  product.setQuantity(cursor.getInt(3));
            //product.setExpireDate(cursor.getString(4));
            productsCodes.add(product);
        }

        for(Product p: productsCodes){

            System.out.println(p.getProductCode()+" "+p.getProductName());

        }
        return productsCodes;
    }

    public Integer countProducts(String code){
        Integer total=0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM (quantity) as Total FROM products WHERE productCode='"+code+"'", null);

        if (cursor.moveToFirst()) {

            total = cursor.getInt(cursor.getColumnIndex("Total"));// get final total

        }
        return total;
    }

}
