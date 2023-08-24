package com.emmar.emmar_assingment.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.emmar.emmar_assingment.database.EmmarAppDatabase;
import com.emmar.emmar_assingment.database.dao.UserDao;
import com.emmar.emmar_assingment.database.entity.User;
import com.emmar.emmar_assingment.models.ResultDetails;
import com.emmar.emmar_assingment.models.UserList;
import com.emmar.emmar_assingment.network.ApiDataService;
import com.emmar.emmar_assingment.network.RetrofitClient;
import com.emmar.emmar_assingment.utils.AppExecutors;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {

    private final EmmarAppDatabase database;

    private final LiveData<List<User>> getAllLocalUsers;

    public MainRepository(Application application) {
        database = EmmarAppDatabase.getInstance(application);
        getAllLocalUsers = database.userDao().getAllUsers();
    }

    public LiveData<List<User>> getGetAllLocalUsers() {
        return getAllLocalUsers;
    }

    public void getUserData(int pageIndex) {
        final ApiDataService userDataService = RetrofitClient.getService();

        userDataService.getApiUserListing(pageIndex).enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(@NonNull Call<UserList> call, @NonNull Response<UserList> response) {
                System.out.println("Response " +response.toString());
                if (response.body() != null) {
                    saveUserData(response.body().users);
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserList> call, @NonNull Throwable t) {
                Log.e("error in get api","error----"+t.getMessage().toString());
            }
        });
    }

    private void saveUserData(List<ResultDetails> userList) {
        ArrayList<User> localUsers = new ArrayList<>();
        for (ResultDetails userData : userList) {
            User user = new User(userData.getName().getFirst(), userData.getPicture().getLarge(), userData.getEmail(), userData.getLocation().getCountry(), userData.getRegistered().getDate().toString(), userData.getDob().getDate().toString(), userData.getLocation().getCity(), userData.getLocation().getState(), userData.getLocation().getPostcode(), String.valueOf(userData.getDob().getAge()));
            localUsers.add(user);
        }

        new AppExecutors().diskIO().execute(() -> {
            UserDao userDao = database.userDao();
            userDao.insert(localUsers);
        });
    }
}