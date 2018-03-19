package com.example.yasirnazir.clearscore.dependency_injection

import com.example.yasirnazir.clearscore.features.home.HomeActivity
import com.example.yasirnazir.clearscore.networking.NetworkModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by yasirnazir on 3/19/18.
 */
@Singleton
@Component(modules = arrayOf(NetworkModule::class))
interface Di {
    fun inject(homeActivity: HomeActivity)
}
