package app.codinguyy.stateflowexamples

import android.app.Application
import app.codinguyy.stateflowexamples.di.viewModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class StateFlowApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@StateFlowApplication)
            modules(viewModules)
        }
    }
}
