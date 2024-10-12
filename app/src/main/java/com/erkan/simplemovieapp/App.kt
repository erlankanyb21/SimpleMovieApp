package com.erkan.simplemovieapp

import android.app.Application
import com.erkan.simplemovieapp.data.di.dataModule
import com.erkan.simplemovieapp.domain.di.domainModule
import com.erkan.simplemovieapp.presentation.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                module {
                    includes(
                        dataModule,
                        domainModule,
                        uiModule
                    )
                }
            )
        }
    }
}