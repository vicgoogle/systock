package com.br.systock.view;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.br.systock.R;
import com.br.systock.model.Notifications;


import java.util.ArrayList;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.MyViewholder> {

    private Context adapter_context;
    private ArrayList<Notifications> notification_profiles;

    public NotificationsAdapter(Context a , ArrayList<Notifications> b)
    {
        adapter_context = a;
        notification_profiles = b;

    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewNotifications) {
        return new MyViewholder(LayoutInflater.from(adapter_context).inflate(R.layout.notification_holder,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {
        holder.Title.setText(notification_profiles.get(position).getTitle());
        holder.Body.setText(notification_profiles.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        return notification_profiles.size();
    }

    class MyViewholder extends RecyclerView.ViewHolder
    {
        TextView Title,Body;

        public MyViewholder(View itemView)  {
            super(itemView);
            Title = (TextView) itemView.findViewById(R.id.body_tv);
            Body = (TextView) itemView.findViewById(R.id.text_tv);
        }
    }

}

