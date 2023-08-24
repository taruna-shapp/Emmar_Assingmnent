package com.emmar.emmar_assingment.ui.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.emmar.emmar_assingment.R
import com.emmar.emmar_assingment.database.entity.User
import com.emmar.emmar_assingment.databinding.ActivityUserDetailsBinding
import com.emmar.emmar_assingment.utils.AppConstants
import com.google.gson.Gson

class UserDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_details)
        binding.user = getBundleData()
    }

    private fun getBundleData(): User {
        val data = intent.getStringExtra(AppConstants.USER_DETAILS)
        return Gson().fromJson(data, User::class.java)
    }

    fun onBackBtn(v: View) {
        finish()
    }
}