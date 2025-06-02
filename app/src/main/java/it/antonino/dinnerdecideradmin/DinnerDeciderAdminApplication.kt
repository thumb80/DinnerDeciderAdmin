package it.antonino.dinnerdecideradmin

import android.app.Application
import it.antonino.dinnerdecideradmin.di.module
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DinnerDeciderAdminApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DinnerDeciderAdminApplication)
            modules(module)
        }
    }
}