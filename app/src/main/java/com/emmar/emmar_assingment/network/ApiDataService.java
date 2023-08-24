package com.emmar.emmar_assingment.network;


import com.emmar.emmar_assingment.models.UserList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiDataService {
    // end point to fetch user data
//    https://randomuser.me/api/?page=1&results=10
    @GET("?results=100")
    Call<UserList> getApiUserListing( @Query("page") int pageIndex);
}
