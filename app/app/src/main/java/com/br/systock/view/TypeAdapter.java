package com.br.systock.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.br.systock.R;
import com.br.systock.model.Type;

import java.util.ArrayList;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.MyViewholder> {

    private Context adapter_context;
    private ArrayList<Type> type_profiles;

    public TypeAdapter(Context c , ArrayList<Type> p)
    {
        adapter_context = c;
        type_profiles = p;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewholder(LayoutInflater.from(adapter_context).inflate(R.layout.type_adapter_cardview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {
        holder.descricao.setText(type_profiles.get(position).getdescricao());
        holder.status.setText(type_profiles.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return type_profiles.size();
    }

    class MyViewholder extends RecyclerView.ViewHolder
    {
        TextView descricao,status;

        public MyViewholder(View itemView)  {
            super(itemView);
            descricao = (TextView) itemView.findViewById(R.id.descricao);
            status = (TextView) itemView.findViewById(R.id.status);
        }
    }

}
