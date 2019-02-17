package com.example.mustofaamami.testfakeapi;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.mustofaamami.testfakeapi.adapter.AdapterData;
import com.example.mustofaamami.testfakeapi.api.ApiRequestBiodata;
import com.example.mustofaamami.testfakeapi.api.RetrofitServer;
import com.example.mustofaamami.testfakeapi.model.ResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayData extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        pd = new ProgressDialog(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.dataRecyclerView);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager );

        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();

        ApiRequestBiodata api = RetrofitServer.getClient().create(ApiRequestBiodata.class);
        Call<List<ResponseModel>> getData = api.getBiodata();
        getData.enqueue(new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                pd.hide();
                List<ResponseModel> mItems = response.body();
                mAdapter = new AdapterData(DisplayData.this, mItems);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                pd.hide();
                Log.d("TestAPI",t.getMessage());
            }
        });
    }
}
