package com.emmar.emmar_assingment.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.emmar.emmar_assingment.database.entity.User;
import com.emmar.emmar_assingment.repository.MainRepository;
import com.emmar.emmar_assingment.utils.AppUtils;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private final MainRepository mainRepository;
    private final LiveData<List<User>> getAllUsers;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mainRepository = new MainRepository(application);
        getAllUsers = mainRepository.getGetAllLocalUsers();
    }

    public void FetchAndSaveUserData(int pageIndex) {
        mainRepository.getUserData(pageIndex);
    }

    public LiveData<List<User>> getGetAllUsers() {
        return getAllUsers;
    }
}