package com.hemp.works.di.dashboard

import com.hemp.works.dashboard.account.ui.AccountFragment
import com.hemp.works.dashboard.address.ui.AddressFragment
import com.hemp.works.dashboard.address.ui.CreateAddressFragment
import com.hemp.works.dashboard.calculator.ui.DosageCalculatorFragment
import com.hemp.works.dashboard.cart.ui.CartFragment
import com.hemp.works.dashboard.cart.ui.CouponFragment
import com.hemp.works.dashboard.home.ui.AllProductListFragment
import com.hemp.works.dashboard.home.ui.HomeFragment
import com.hemp.works.dashboard.home.ui.ProductListFragment
import com.hemp.works.dashboard.notification.ui.NotificationFragment
import com.hemp.works.dashboard.offer.ui.OfferFragment
import com.hemp.works.dashboard.order.ui.OrderFragment
import com.hemp.works.dashboard.payment.ui.PaymentFragment
import com.hemp.works.dashboard.payment.ui.PaymentHistoryFragment
import com.hemp.works.dashboard.prescription.ui.PrescriptionFragment
import com.hemp.works.dashboard.prescription.ui.UploadPrescriptionFragment
import com.hemp.works.dashboard.product.ui.ProductFragment
import com.hemp.works.dashboard.product.ui.ProductImageFragment
import com.hemp.works.dashboard.profile.ui.ProfileFragment
import com.hemp.works.dashboard.search.ui.SearchFragment
import com.hemp.works.dashboard.support.ui.AllSupportFragment
import com.hemp.works.dashboard.support.ui.AllSupportFragmentDirections
import com.hemp.works.dashboard.support.ui.SupportFragment
import com.hemp.works.dashboard.tnl.ui.TNLFragment
import com.hemp.works.dashboard.wallet.ui.WalletFragment
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
    abstract fun contributeAllProductListFragment(): AllProductListFragment

    @ContributesAndroidInjector
    abstract fun contributeProductFragment(): ProductFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    abstract fun contributeProductImageFragment(): ProductImageFragment

    @ContributesAndroidInjector
    abstract fun contributeProfileFragment(): ProfileFragment

    @ContributesAndroidInjector
    abstract fun contributePrescriptionFragment(): PrescriptionFragment

    @ContributesAndroidInjector
    abstract fun contributeUploadPrescriptionFragment(): UploadPrescriptionFragment

    @ContributesAndroidInjector
    abstract fun contributeDosageCalculatorFragment(): DosageCalculatorFragment

    @ContributesAndroidInjector
    abstract fun contributeTNLFragment(): TNLFragment

    @ContributesAndroidInjector
    abstract fun contributeAddressFragment(): AddressFragment

    @ContributesAndroidInjector
    abstract fun contributeCreateAddressFragment(): CreateAddressFragment

    @ContributesAndroidInjector
    abstract fun contributeCartFragment(): CartFragment

    @ContributesAndroidInjector
    abstract fun contributePaymentFragment(): PaymentFragment

//    @ContributesAndroidInjector
//    abstract fun contributeCreditFragment(): CreditFragment

    @ContributesAndroidInjector
    abstract fun contributeCouponFragment(): CouponFragment

    @ContributesAndroidInjector
    abstract fun contributeOrderFragment(): OrderFragment

    @ContributesAndroidInjector
    abstract fun contributeSupportFragment(): SupportFragment

    @ContributesAndroidInjector
    abstract fun contributeNotificationFragment(): NotificationFragment

    @ContributesAndroidInjector
    abstract fun contributeOfferFragment(): OfferFragment

    @ContributesAndroidInjector
    abstract fun contributeAccountFragment(): AccountFragment

    @ContributesAndroidInjector
    abstract fun contributeWalletFragment(): WalletFragment

    @ContributesAndroidInjector
    abstract fun contributePaymentHistoryFragment(): PaymentHistoryFragment

    @ContributesAndroidInjector
    abstract fun contributeAllSupportFragment(): AllSupportFragment

}