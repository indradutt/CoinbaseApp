package com.indra.coinbaseapp.di

import android.app.Application
import com.indra.coinbaseapp.coinbase.view.CoinbaseActivity
import dagger.Binds
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(activity: CoinbaseActivity)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
        @BindsInstance
        fun application(application: Application): Builder
        @BindsInstance
        fun appUrl(url: String): Builder
    }
}