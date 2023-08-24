package com.emmar.emmar_assingment

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex


class EmmarApp : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        lateinit var context: Context
    }

}