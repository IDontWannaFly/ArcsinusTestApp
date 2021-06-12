package com.mark.arcsinustestapp

import androidx.multidex.MultiDexApplication
import com.mark.arcsinustestapp.realm.AppRealmMigration
import io.realm.Realm
import io.realm.RealmConfiguration
import timber.log.Timber

class Application : MultiDexApplication() {

    lateinit var instance: Application
        private set

    override fun onCreate() {
        super.onCreate()

        instance = this
        Timber.plant(Timber.DebugTree())

        initRealm()
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