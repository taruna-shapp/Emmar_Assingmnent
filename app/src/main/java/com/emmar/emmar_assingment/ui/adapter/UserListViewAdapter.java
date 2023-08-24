package com.emmar.emmar_assingment.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.emmar.emmar_assingment.R;
import com.emmar.emmar_assingment.database.entity.User;
import com.emmar.emmar_assingment.databinding.RowListItemBinding;

import java.util.ArrayList;

public class UserListViewAdapter extends RecyclerView.Adapter<UserListViewAdapter.UserViewHolder> {

    private ArrayList<User> userList;
    private final OnItemClickListener listener;

    public UserListViewAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RowListItemBinding mDeveloperListItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.row_list_item, viewGroup, false);
        return new UserViewHolder(mDeveloperListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder mUserViewHolder, int i) {
        User user = userList.get(i);
        mUserViewHolder.mDeveloperListItemBinding.setUser(user);
        mUserViewHolder.bind(user);
    }

    @Override
    public int getItemCount() {
        if (userList != null) {
            return userList.size();
        } else {
            return 0;
        }
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        RowListItemBinding mDeveloperListItemBinding;

        public UserViewHolder(@NonNull RowListItemBinding mDeveloperListItemBinding) {
            super(mDeveloperListItemBinding.getRoot());
            this.mDeveloperListItemBinding = mDeveloperListItemBinding;
        }

        public void bind(User user) {
            mDeveloperListItemBinding.clMain.setOnClickListener(view -> listener.onItemClick(user));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(User user);
    }
}