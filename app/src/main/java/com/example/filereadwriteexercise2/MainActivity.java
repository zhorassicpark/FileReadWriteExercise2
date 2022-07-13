package com.example.filereadwriteexercise2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnsignin, btnlogin;
    EditText inputid, inputpw;
    TextView label;
    Boolean userListExists;
    JSONObject jo = null;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //check if file exists
        System.out.println("GOES IN,,,,,,,");
        try {
            File path = getApplicationContext().getFilesDir();
            File file1 = new File(path + "/userlist.json");
            if (file1.exists()) {
                if (file1.isDirectory()) {
                    System.out.println("file1: dir exits");
                } else {
                    System.out.println("file1: file exits");
                    userListExists = true;
                    jo = getJsonObjectFromFile("userlist.json");
                }
            } else {
                System.out.println("file1: does not exits");
                userListExists = false;
                jo = createNewJsonObject();
            }
        } catch (Exception e) {
            System.out.println("SERIOUS ERROR OCCURRED!!!!");
        }finally {
            System.out.println("FINALLLYU!!!!!");
        }

        btnsignin = findViewById(R.id.btnsignin);
        btnlogin = findViewById(R.id.btnlogin);
        inputid = findViewById(R.id.inputid);
        inputpw = findViewById(R.id.inputpw);
        label = findViewById(R.id.errmsg);

        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Sign In
                if(!validateUserId(inputid.getText().toString())){
                    //user register...
                    try {
                        System.out.println(inputid.getText().toString());
                        System.out.println(inputpw.getText().toString());
                        jo.put(inputid.getText().toString(), inputpw.getText().toString());
                        label.setText("Signed In!");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        label.setText(e.getMessage());
                    }

                }else{
                    label.setText("User Already Exists!");
                }
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateUserId(inputid.getText().toString())){
                    if(validateUserPw(inputid.getText().toString(), inputpw.getText().toString())){
                        //user login...
                        Intent intent = new Intent(MainActivity.this, SubActivity.class);
                        intent.putExtra("username", inputid.getText().toString());
                        startActivity(intent);
                    }else{
                        label.setText("Wrong Password");
                    }

                }else{
                    label.setText("User Doesn't Exist");
                }
            }
        });
    }

    private boolean validateUserId(String inputid) {
        if (jo.has(inputid)) return true;
        return false;
    }

    private boolean validateUserPw(String inputid, String inputpw) {
        try {
//            if ((jo.getJSONObject(inputid)).getString("password") == inputpw) return true;
            System.out.println("ID is :" + jo.getString(inputid));
            System.out.println("PW is : " + inputpw);
            if (jo.getString(inputid).equals(inputpw)) return true;
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    private JSONObject createNewJsonObject(){
        return new JSONObject();
    }

    private JSONObject getJsonObjectFromFile(String fileName){
        FileInputStream fileInputStream = null;
        JSONObject jo = null;
        try {
            fileInputStream = openFileInput(fileName);
            int read = -1;
            StringBuffer buffer = new StringBuffer();
            while((read = fileInputStream.read())!=  -1){
                buffer.append((char)read);
            }
            Log.d("FILEREAD", buffer.toString());
             jo = new JSONObject(buffer.toString());
            System.out.println(jo);
            System.out.println("HERE WE GO!!!");
//            for(int i = 0;i<readja.length();i++){
//                System.out.println(readja.getJSONObject(i));
//            }
            System.out.println(jo);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jo;
    }

    private ArrayList convertJsonArrayToArrayList(ArrayList arrayList, JSONArray jsonArray){
        if (jsonArray != null) {

            //Iterating JSON array
            for (int i=0;i<jsonArray.length();i++){

                //Adding each element of JSON array into ArrayList
                try {
                    arrayList.add(jsonArray.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        //Iterating ArrayList to print each element

        System.out.println("Each element of ArrayList");
        for(int i=0; i<arrayList.size(); i++) {
            //Printing each element of ArrayList
            System.out.println(arrayList.get(i));
        }
        return arrayList;
    }


}