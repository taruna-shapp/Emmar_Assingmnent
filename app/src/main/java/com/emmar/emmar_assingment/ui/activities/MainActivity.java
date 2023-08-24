package com.emmar.emmar_assingment.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emmar.emmar_assingment.R;
import com.emmar.emmar_assingment.database.entity.User;
import com.emmar.emmar_assingment.databinding.ActivityMainBinding;
import com.emmar.emmar_assingment.adapter.UserListViewAdapter;
import com.emmar.emmar_assingment.utils.AppConstants;
import com.emmar.emmar_assingment.utils.AppUtils;
import com.emmar.emmar_assingment.utils.PaginationListener;
import com.emmar.emmar_assingment.viewmodel.MainViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding activityMainBinding;
    private MainViewModel mainViewModel;
    private UserListViewAdapter adapter;
    private int pageIndex = 1;
    private boolean isLastPage = false;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        hitApt();
        Observers();
    }

    private void initView() {
        RecyclerView recyclerView = activityMainBinding.rvUser;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new UserListViewAdapter(user -> startActivity(new Intent(this, UserDetailsActivity.class)
                .putExtra(AppConstants.USER_DETAILS, new Gson().toJson(user))));

        recyclerView.setAdapter(adapter);
        //used for the purpose of pagination
        recyclerView.addOnScrollListener(new PaginationListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                pageIndex++;
                hitApt();
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    /**
     * This method is used ti fetch user list data from the api
     **/
    private void hitApt() {
        if (AppUtils.isNetworkAvailable(MainActivity.this)) {
            activityMainBinding.idPBLoading.setVisibility(View.VISIBLE);
            if (pageIndex == 1) {
                showProgressDialog();
            }
            mainViewModel.FetchAndSaveUserData(pageIndex);
        } else {
            Toast.makeText(MainActivity.this, "No Network connection", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Observer to fetch api response and display data
     **/
    private void Observers() {
        mainViewModel.getGetAllUsers().observe(this, users -> {
            hideProgressDialog();
            activityMainBinding.idPBLoading.setVisibility(View.GONE);
            isLoading = false;
            adapter.setUserList((ArrayList<User>) users);
        });

    }

    public void onBackPress(View v) {
        finish();
    }

    @Override
    public void initDataBinding() {
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

    }
}