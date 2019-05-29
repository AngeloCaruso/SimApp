package com.example.ademsimapp;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private ArrayList<User> users;
    private Resources res;
    private OnClickRecyclerListener mOnClickRecyclerListener;

    public UserAdapter(Context context, ArrayList<User> users, OnClickRecyclerListener onClickRecyclerListener){
        this.users = users;
        this.mOnClickRecyclerListener = onClickRecyclerListener;
        this.res = context.getResources();
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.users_card, viewGroup, false);
        return new UserViewHolder(view, mOnClickRecyclerListener);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int i) {
        final User user = users.get(i);
        holder.username.setText(user.getUsername());
        holder.email.setText(user.getEmail());
        holder.img.setImageDrawable(ResourcesCompat.getDrawable(res, user.getImg(), null));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView username, email;
        private ImageView img;
        private OnClickRecyclerListener onClickRecyclerListener;

        public UserViewHolder(View itemView, OnClickRecyclerListener onClickRecyclerListener) {
            super(itemView);
            username = (TextView)itemView.findViewById(R.id.txtUsername);
            email = (TextView)itemView.findViewById(R.id.txtEmail);
            img = (ImageView)itemView.findViewById(R.id.imageView);
            this.onClickRecyclerListener = onClickRecyclerListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickRecyclerListener.OnClick(getAdapterPosition());
        }
    }

    public interface OnClickRecyclerListener{
        void OnClick(int position);
    }
}
