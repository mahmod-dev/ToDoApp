package com.mahmoud.todoapp


import android.app.Application
import com.yariksoffice.lingver.Lingver
import com.yariksoffice.lingver.store.PreferenceLocaleStore
import java.util.*


class App : Application() {

    @Suppress("UNUSED_VARIABLE")
    override fun onCreate() {
        super.onCreate()
//        val store = PreferenceLocaleStore(this, Locale(LANGUAGE_ENGLISH))
//        // you can use this instance for DI or get it via Lingver.getInstance() later on
//        val lingver = Lingver.init(this, store)
    }

    companion object {
        const val LANGUAGE_ENGLISH = "en"
        const val LANGUAGE_ENGLISH_COUNTRY = "US"
        const val LANGUAGE_UKRAINIAN = "uk"
        const val LANGUAGE_UKRAINIAN_COUNTRY = "UA"
        const val LANGUAGE_RUSSIAN = "ru"
        const val LANGUAGE_RUSSIAN_COUNTRY = "RU"
    }
}