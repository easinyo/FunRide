package com.dubinostech.rideshareapp


import com.wabadaba.dziennik.di.ApplicationModule
import com.wabadaba.dziennik.di.MainComponent
import it.cosenonjaviste.daggermock.DaggerMockRule

class EspressoDaggerMockRule : DaggerMockRule<MainComponent>(
    MainComponent::class.java,
    ApplicationModule(BaseInstrumentedTest.getApp())) {
    init {
        set { component -> MainApplication.mainComponent = component }
    }
}