package com.elsa.giantbomb

import android.app.Application
import com.elsa.giantbomb.modules.networkModule
import com.elsa.giantbomb.modules.repositoryModule
import com.elsa.giantbomb.modules.viewModelModule
import org.koin.core.context.startKoin


class GiantBombApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(networkModule, repositoryModule, viewModelModule)
        }
    }
}