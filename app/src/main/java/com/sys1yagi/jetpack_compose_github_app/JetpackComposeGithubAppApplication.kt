package com.sys1yagi.jetpack_compose_github_app

import androidx.multidex.MultiDexApplication
import com.sys1yagi.jetpack_compose_github_app.infrastructure.di.singleton
import com.sys1yagi.jetpack_compose_github_app.infrastructure.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class JetpackComposeGithubAppApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@JetpackComposeGithubAppApplication)
            modules(
                listOf(
                    singleton,
                    viewModelModule
                )
            )
        }
    }
}
