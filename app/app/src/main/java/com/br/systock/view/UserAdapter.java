package com.br.systock.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.br.systock.R;
import com.br.systock.model.User;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewholder> {

    Context context;
    ArrayList<User> profiles;

    public UserAdapter(Context c , ArrayList<User> r)
    {
        context = c;
        profiles = r;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewUser) {
        return new MyViewholder(LayoutInflater.from(context).inflate(R.layout.user_list_holder,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {
        holder.email.setText(profiles.get(position).getEmail());
        holder.name.setText(profiles.get(position).getName());
        holder.password.setText(profiles.get(position).getPassword());
        holder.confirmpassword.setText(profiles.get(position).getConfirmpassword());
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class MyViewholder extends RecyclerView.ViewHolder
    {
        TextView email, name, password, confirmpassword;

        public MyViewholder(View itemView)  {
            super(itemView);
            email = (TextView) itemView.findViewById(R.id.emaillist);
            name = (TextView) itemView.findViewById(R.id.namelist);
            password = (TextView) itemView.findViewById(R.id.passwordlist);
            confirmpassword = (TextView) itemView.findViewById(R.id.confirmpasswordlist);
        }
    }

}

