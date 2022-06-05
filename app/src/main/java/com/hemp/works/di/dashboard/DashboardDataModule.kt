package com.hemp.works.di.dashboard

import com.hemp.works.dashboard.account.data.remote.AccountRemoteDataSource
import com.hemp.works.dashboard.account.data.remote.AccountService
import com.hemp.works.dashboard.address.data.remote.AddressRemoteDataSource
import com.hemp.works.dashboard.address.data.remote.AddressService
import com.hemp.works.dashboard.calculator.data.remote.CalculatorRemoteDataSource
import com.hemp.works.dashboard.calculator.data.remote.CalculatorService
import com.hemp.works.dashboard.cart.data.remote.CartRemoteDataSource
import com.hemp.works.dashboard.cart.data.remote.CartService
import com.hemp.works.dashboard.course.data.remote.CourseRemoteDataSource
import com.hemp.works.dashboard.course.data.remote.CourseService
import com.hemp.works.dashboard.credit.data.remote.CreditRemoteDataSource
import com.hemp.works.dashboard.credit.data.remote.CreditService
import com.hemp.works.dashboard.home.data.remote.HomeRemoteDataSource
import com.hemp.works.dashboard.home.data.remote.HomeService
import com.hemp.works.dashboard.ledger.data.remote.LedgerRemoteDataSource
import com.hemp.works.dashboard.ledger.data.remote.LedgerService
import com.hemp.works.dashboard.notification.data.remote.NotificationRemoteDataSource
import com.hemp.works.dashboard.notification.data.remote.NotificationService
import com.hemp.works.dashboard.offer.data.remote.OfferRemoteDataSource
import com.hemp.works.dashboard.offer.data.remote.OfferService
import com.hemp.works.dashboard.order.data.remote.OrderRemoteDataSource
import com.hemp.works.dashboard.order.data.remote.OrderService
import com.hemp.works.dashboard.payment.data.remote.PaymentRemoteDataSource
import com.hemp.works.dashboard.payment.data.remote.PaymentService
import com.hemp.works.dashboard.prescription.data.remote.PrescriptionRemoteDataSource
import com.hemp.works.dashboard.prescription.data.remote.PrescriptionService
import com.hemp.works.dashboard.product.data.remote.ProductRemoteDataSource
import com.hemp.works.dashboard.product.data.remote.ProductService
import com.hemp.works.dashboard.profile.data.remote.ProfileRemoteDataSource
import com.hemp.works.dashboard.profile.data.remote.ProfileService
import com.hemp.works.dashboard.search.data.remote.SearchRemoteDataSource
import com.hemp.works.dashboard.search.data.remote.SearchService
import com.hemp.works.dashboard.support.data.remote.SupportRemoteDataSource
import com.hemp.works.dashboard.support.data.remote.SupportService
import com.hemp.works.dashboard.tnl.data.remote.TNLRemoteDataSource
import com.hemp.works.dashboard.tnl.data.remote.TNLService
import com.hemp.works.dashboard.wallet.data.remote.WalletRemoteDataSource
import com.hemp.works.dashboard.wallet.data.remote.WalletService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Suppress("unused")
@Module
class DashboardDataModule {

    @Singleton
    @Provides
    fun provideHomeService(retrofit: Retrofit): HomeService = retrofit.create(HomeService::class.java)

    @Singleton
    @Provides
    fun provideHomeRemoteDataSource(service: HomeService)
            = HomeRemoteDataSource(service)

    @Singleton
    @Provides
    fun provideSearchService(retrofit: Retrofit): SearchService = retrofit.create(SearchService::class.java)

    @Singleton
    @Provides
    fun provideSearchRemoteDataSource(service: SearchService)
            = SearchRemoteDataSource(service)

    @Singleton
    @Provides
    fun provideProductService(retrofit: Retrofit): ProductService = retrofit.create(ProductService::class.java)

    @Singleton
    @Provides
    fun provideProductRemoteDataSource(service: ProductService)
            = ProductRemoteDataSource(service)

    @Singleton
    @Provides
    fun provideProfileService(retrofit: Retrofit): ProfileService = retrofit.create(ProfileService::class.java)

    @Singleton
    @Provides
    fun provideProfileRemoteDataSource(service: ProfileService)
            = ProfileRemoteDataSource(service)

    @Singleton
    @Provides
    fun providePrescriptionService(retrofit: Retrofit): PrescriptionService = retrofit.create(
        PrescriptionService::class.java)

    @Singleton
    @Provides
    fun providePrescriptionRemoteDataSource(service: PrescriptionService)
            = PrescriptionRemoteDataSource(service)

    @Singleton
    @Provides
    fun provideCalculatorService(retrofit: Retrofit): CalculatorService = retrofit.create(
        CalculatorService::class.java)

    @Singleton
    @Provides
    fun provideCalculatorRemoteDataSource(service: CalculatorService)
            = CalculatorRemoteDataSource(service)

    @Singleton
    @Provides
    fun provideTNLService(retrofit: Retrofit): TNLService = retrofit.create(
        TNLService::class.java)

    @Singleton
    @Provides
    fun provideTNLRemoteDataSource(service: TNLService)
            = TNLRemoteDataSource(service)

    @Singleton
    @Provides
    fun provideAddressService(retrofit: Retrofit): AddressService = retrofit.create(
        AddressService::class.java)

    @Singleton
    @Provides
    fun provideAddressRemoteDataSource(service: AddressService)
            = AddressRemoteDataSource(service)

    @Singleton
    @Provides
    fun provideCartService(retrofit: Retrofit): CartService = retrofit.create(
        CartService::class.java)

    @Singleton
    @Provides
    fun provideCartRemoteDataSource(service: CartService)
            = CartRemoteDataSource(service)

    @Singleton
    @Provides
    fun providePaymentService(retrofit: Retrofit): PaymentService = retrofit.create(
        PaymentService::class.java)

    @Provides
    fun providePaymentRemoteDataSource(service: PaymentService)
            = PaymentRemoteDataSource(service)

    @Singleton
    @Provides
    fun provideCreditService(retrofit: Retrofit): CreditService = retrofit.create(
        CreditService::class.java)

    @Singleton
    @Provides
    fun provideCreditRemoteDataSource(service: CreditService)
            = CreditRemoteDataSource(service)

    @Singleton
    @Provides
    fun provideOrderService(retrofit: Retrofit): OrderService = retrofit.create(
        OrderService::class.java)

    @Singleton
    @Provides
    fun provideOrderRemoteDataSource(service: OrderService)
            = OrderRemoteDataSource(service)

    @Singleton
    @Provides
    fun provideSupportService(retrofit: Retrofit): SupportService = retrofit.create(
        SupportService::class.java)

    @Provides
    fun provideSupportRemoteDataSource(service: SupportService)
            = SupportRemoteDataSource(service)

    @Singleton
    @Provides
    fun provideNotificationService(retrofit: Retrofit): NotificationService = retrofit.create(
        NotificationService::class.java)

    @Provides
    fun provideNotificationRemoteDataSource(service: NotificationService)
            = NotificationRemoteDataSource(service)

    @Singleton
    @Provides
    fun provideOfferService(retrofit: Retrofit): OfferService = retrofit.create(
        OfferService::class.java)

    @Provides
    fun provideOfferRemoteDataSource(service: OfferService)
            = OfferRemoteDataSource(service)

    @Singleton
    @Provides
    fun provideAccountService(retrofit: Retrofit): AccountService = retrofit.create(
        AccountService::class.java)

    @Provides
    fun provideAccountRemoteDataSource(service: AccountService)
            = AccountRemoteDataSource(service)

    @Singleton
    @Provides
    fun provideWalletService(retrofit: Retrofit): WalletService = retrofit.create(
        WalletService::class.java)

    @Provides
    fun provideWalletRemoteDataSource(service: WalletService)
            = WalletRemoteDataSource(service)

    @Singleton
    @Provides
    fun provideLedgerService(retrofit: Retrofit): LedgerService = retrofit.create(
        LedgerService::class.java)

    @Provides
    fun provideLedgerRemoteDataSource(service: LedgerService)
            = LedgerRemoteDataSource(service)

    @Singleton
    @Provides
    fun provideCourseService(retrofit: Retrofit): CourseService = retrofit.create(
        CourseService::class.java)

    @Provides
    fun provideCourseRemoteDataSource(service: CourseService)
            = CourseRemoteDataSource(service)




}