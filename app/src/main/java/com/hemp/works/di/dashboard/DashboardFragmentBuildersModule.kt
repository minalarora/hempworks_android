package com.hemp.works.di.dashboard

import com.hemp.works.dashboard.home.ui.HomeFragment
import com.hemp.works.dashboard.home.ui.ProductListFragment
import com.hemp.works.dashboard.product.ui.ProductFragment
import com.hemp.works.dashboard.product.ui.ProductImageFragment
import com.hemp.works.dashboard.search.ui.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class DashboardFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeProductListFragment(): ProductListFragment

    @ContributesAndroidInjector
    abstract fun contributeProductFragment(): ProductFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    abstract fun contributeProductImageFragment(): ProductImageFragment
}