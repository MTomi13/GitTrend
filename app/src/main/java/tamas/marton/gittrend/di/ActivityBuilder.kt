package tamas.marton.gittrend.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import tamas.marton.gittrend.home.HomeActivity
import tamas.marton.gittrend.home.HomeActivityModule


@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [(HomeActivityModule::class)])
    abstract fun bindHomeActivity(): HomeActivity
}