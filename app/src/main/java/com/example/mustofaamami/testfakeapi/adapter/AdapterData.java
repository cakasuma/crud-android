package com.example.mustofaamami.testfakeapi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.mustofaamami.testfakeapi.MainActivity;
import com.example.mustofaamami.testfakeapi.R;
import com.example.mustofaamami.testfakeapi.model.ResponseModel;

import java.util.List;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData> {
    private List<ResponseModel> mList;
    private Context ctx;

    public AdapterData(Context ctx, List<ResponseModel> mList) {
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holderData, int position) {
        ResponseModel dataModel = mList.get(position);
        holderData.name.setText(dataModel.getName());
        holderData.email.setText(dataModel.getEmail());
        holderData.age.setText(dataModel.getAge());
        holderData.responseModel = dataModel;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder {
        TextView name, email, age;
        ResponseModel responseModel;
        public HolderData(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.txtName);
            email = (TextView) view.findViewById(R.id.txtEmail);
            age = (TextView) view.findViewById(R.id.txtAge);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent updateIntent = new Intent(ctx, MainActivity.class);
                    updateIntent.putExtra("name", responseModel.getName());
                    updateIntent.putExtra("email", responseModel.getEmail());
                    updateIntent.putExtra("age", responseModel.getAge());

                    ctx.startActivity(updateIntent);
                }
            });
        }
    }
}