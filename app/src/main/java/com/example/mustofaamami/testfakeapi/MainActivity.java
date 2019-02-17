package com.example.mustofaamami.testfakeapi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mustofaamami.testfakeapi.api.ApiRequestBiodata;
import com.example.mustofaamami.testfakeapi.api.RetrofitServer;
import com.example.mustofaamami.testfakeapi.model.ResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private EditText mInsFullName, mInsAge, mInsEmail;
    private Button mBtnInsert, mBtnRead, mBtnUpdate, mBtnDelete;
    private ProgressDialog progressDialog;
    private AlertDialog.Builder alertBuilder;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInsFullName = (EditText) findViewById(R.id.insFullName);
        mInsAge = (EditText) findViewById(R.id.insAge);
        mInsEmail = (EditText) findViewById(R.id.insEmail);
        mBtnInsert = (Button) findViewById(R.id.btn_insert);
        mBtnRead = (Button) findViewById(R.id.btn_read);
        mBtnUpdate = (Button) findViewById(R.id.btn_update);
        mBtnDelete = (Button) findViewById(R.id.btn_delete);
        progressDialog = new ProgressDialog(this);
        alertBuilder = new AlertDialog.Builder(this);

        Intent dataIntent = getIntent();
        final String emailIntent = dataIntent.getStringExtra("email");
        Log.d("KONTOL", "something");
        if (emailIntent != null) {
            mBtnInsert.setVisibility(View.GONE);
            mBtnRead.setVisibility(View.GONE);
            mBtnUpdate.setVisibility(View.VISIBLE);
            mBtnDelete.setVisibility(View.VISIBLE);

            mInsFullName.setText(dataIntent.getStringExtra("name"));
            mInsEmail.setText(dataIntent.getStringExtra("email"));
            mInsAge.setText(dataIntent.getStringExtra("age"));
        }

        final ApiRequestBiodata apiRequestBiodata = RetrofitServer.getClient().create(ApiRequestBiodata.class);

        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertBuilder.setMessage("Are you sure you want to delete this item ?");
                alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog.setMessage("Sending data...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        Call<ResponseModel> delete = apiRequestBiodata.deleteBiodata(emailIntent);
                        delete.enqueue(new Callback<ResponseModel>() {
                            @Override
                            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                progressDialog.hide();
                                Toast.makeText(MainActivity.this, "delete successfully", Toast.LENGTH_SHORT).show();
                                Intent readIntent = new Intent(MainActivity.this, DisplayData.class);
                                startActivity(readIntent);
                            }

                            @Override
                            public void onFailure(Call<ResponseModel> call, Throwable t) {
                                progressDialog.hide();
                                Toast.makeText(MainActivity.this, "delete failed", Toast.LENGTH_SHORT).show();
                                Intent readIntent = new Intent(MainActivity.this, DisplayData.class);
                                startActivity(readIntent);
                            }
                        });
                        finish();
                    }
                });
                alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = alertBuilder.create();
                alertDialog.setTitle("Confirmation");
                alertDialog.show();
            }
        });

        mBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Sending data...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                String name = mInsFullName.getText().toString();
                String age = mInsAge.getText().toString();
                String email = mInsEmail.getText().toString();
                Call<ResponseModel> update = apiRequestBiodata.updateBiodata(emailIntent, new ResponseModel(name, email, age));
                update.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        progressDialog.hide();
                        Toast.makeText(MainActivity.this,
                                "Update Success for: " + response.body().getName(), Toast.LENGTH_SHORT).show();
                        Intent readIntent = new Intent(MainActivity.this, DisplayData.class);
                        startActivity(readIntent);
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        progressDialog.hide();
                        Toast.makeText(MainActivity.this, "Update Failed", Toast.LENGTH_SHORT).show();
                        Intent readIntent = new Intent(MainActivity.this, DisplayData.class);
                        startActivity(readIntent);
                    }
                });
            }
        });

        mBtnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent readIntent = new Intent(MainActivity.this, DisplayData.class);
                startActivity(readIntent);
            }
        });

        mBtnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Sending data...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                String name = mInsFullName.getText().toString();
                String age = mInsAge.getText().toString();
                String email = mInsEmail.getText().toString();

                Call<ResponseModel> sendBiodata = apiRequestBiodata.sendBiodata(new ResponseModel(name, email, age));
                sendBiodata.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        progressDialog.hide();
                        String responseName = response.body().getName();

                        Toast.makeText(MainActivity.this,
                                "success insert person with this name: " + responseName, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        progressDialog.hide();
                        Toast.makeText(MainActivity.this, "insert failure", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
