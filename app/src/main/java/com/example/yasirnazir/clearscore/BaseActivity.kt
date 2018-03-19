package com.example.yasirnazir.clearscore

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.yasirnazir.clearscore.dependency_injection.DaggerDi
import com.example.yasirnazir.clearscore.dependency_injection.Di
import com.example.yasirnazir.clearscore.networking.NetworkModule
import java.io.File

/**
 * Created by yasirnazir on 3/19/18.
 */
open class BaseActivity : AppCompatActivity() {
    lateinit var deps: Di
        internal set

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cacheFile = File(cacheDir, getString(R.string.responses))
        deps = DaggerDi.builder().networkModule(NetworkModule(cacheFile)).build()

    }
}
