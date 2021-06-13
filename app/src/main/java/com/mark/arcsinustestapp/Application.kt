package com.mark.arcsinustestapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.mark.arcsinustestapp.di.DaggerAppComponent
import com.mark.arcsinustestapp.realm.AppRealmMigration
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.realm.Realm
import io.realm.RealmConfiguration
import timber.log.Timber


class Application : DaggerApplication() {

    companion object {

        lateinit var instance: Application
            private set

    }

    var isNetworkAvailable: Boolean = false
        private set

    override fun onCreate() {
        super.onCreate()

        instance = this
        Timber.plant(Timber.DebugTree())

        initRealm()
        initNetworkListener()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }

    private fun initNetworkListener() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build(), object : ConnectivityManager.NetworkCallback(){

            private val availableNetworks = hashSetOf<Network>()

            override fun onAvailable(network: Network) {
                availableNetworks.add(network)
                isNetworkAvailable = availableNetworks.size > 0
            }

            override fun onUnavailable() {
                isNetworkAvailable = false
            }

            override fun onLost(network: Network) {
                availableNetworks.remove(network)
                isNetworkAvailable = availableNetworks.size > 0
            }
        })
    }

    private fun initRealm() {
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .schemaVersion(1)
            .migration(AppRealmMigration())
            .build()
        Realm.setDefaultConfiguration(config)
    }

}