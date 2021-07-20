package com.example.sportrecorder

import android.app.Application
import android.content.ContextWrapper
import com.example.sportrecorder.helpers.EncryptedPrefs
import com.jakewharton.threetenabp.AndroidThreeTen
import com.pixplicity.easyprefs.library.Prefs
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SportRecorderApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@SportRecorderApp)
            modules(appModule)
        }

        EncryptedPrefs.initEncryptedPrefs(this)
        AndroidThreeTen.init(this)

        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()
    }
}