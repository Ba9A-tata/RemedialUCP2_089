package com.example.ucpdua

import android.app.Application
import com.example.ucpdua.repositori.AppContainer
import com.example.ucpdua.repositori.ContainerDataApp

class PerpustakaanApp : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = ContainerDataApp(this)
    }
}