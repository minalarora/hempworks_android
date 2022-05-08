package com.hemp.works.di.dashboard

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hemp.works.dashboard.DashboardSharedViewModel
import com.hemp.works.dashboard.account.ui.AccountViewModel
import com.hemp.works.dashboard.address.ui.AddressViewModel
import com.hemp.works.dashboard.address.ui.CreateAddressViewModel
import com.hemp.works.dashboard.calculator.ui.DosageCalculatorViewModel
import com.hemp.works.dashboard.cart.ui.CartViewModel
import com.hemp.works.dashboard.cart.ui.CouponViewModel
import com.hemp.works.dashboard.home.ui.AllProductListViewModel
import com.hemp.works.dashboard.home.ui.HomeViewModel
import com.hemp.works.dashboard.home.ui.ProductListViewModel
import com.hemp.works.dashboard.notification.ui.NotificationViewModel
import com.hemp.works.dashboard.offer.ui.OfferViewModel
import com.hemp.works.dashboard.order.ui.OrderViewModel
import com.hemp.works.dashboard.payment.ui.PaymentHistoryViewModel
import com.hemp.works.dashboard.payment.ui.PaymentViewModel
import com.hemp.works.dashboard.prescription.ui.PrescriptionViewModel
import com.hemp.works.dashboard.prescription.ui.UploadPrescriptionViewModel
import com.hemp.works.dashboard.product.ui.ProductViewModel
import com.hemp.works.dashboard.profile.ui.ProfileViewModel
import com.hemp.works.dashboard.search.ui.SearchViewModel
import com.hemp.works.dashboard.support.ui.SupportViewModel
import com.hemp.works.dashboard.tnl.ui.TNLViewModel
import com.hemp.works.dashboard.wallet.ui.WalletViewModel
import com.hemp.works.di.ViewModelKey
import com.hemp.works.login.LoginSharedViewModel
import com.hemp.works.login.ui.viewmodel.*

@Suppress("unused")
@Module
abstract class DashboardViewModelModule {

    //DASHBOARD
    @Binds
    @IntoMap
    @ViewModelKey(DashboardSharedViewModel::class)
    abstract fun bindDashboardSharedViewModel(viewModel: DashboardSharedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductListViewModel::class)
    abstract fun bindProductListViewModel(viewModel: ProductListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AllProductListViewModel::class)
    abstract fun bindAllProductListViewModel(viewModel: AllProductListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductViewModel::class)
    abstract fun bindProductViewModel(viewModel: ProductViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PrescriptionViewModel::class)
    abstract fun bindPrescriptionViewModel(viewModel: PrescriptionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UploadPrescriptionViewModel::class)
    abstract fun bindUploadPrescriptionViewModel(viewModel: UploadPrescriptionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DosageCalculatorViewModel::class)
    abstract fun bindDosageCalculatorViewModel(viewModel: DosageCalculatorViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TNLViewModel::class)
    abstract fun bindTNLViewModel(viewModel: TNLViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddressViewModel::class)
    abstract fun bindAddressViewModel(viewModel: AddressViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateAddressViewModel::class)
    abstract fun bindCreateAddressViewModel(viewModel: CreateAddressViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CartViewModel::class)
    abstract fun bindCartViewModel(viewModel: CartViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PaymentViewModel::class)
    abstract fun bindPaymentViewModel(viewModel: PaymentViewModel): ViewModel

//    @Binds
//    @IntoMap
//    @ViewModelKey(CreditViewModel::class)
//    abstract fun bindCreditViewModel(viewModel: CreditViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CouponViewModel::class)
    abstract fun bindCouponViewModel(viewModel: CouponViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrderViewModel::class)
    abstract fun bindOrderViewModel(viewModel: OrderViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SupportViewModel::class)
    abstract fun bindSupportViewModel(viewModel: SupportViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NotificationViewModel::class)
    abstract fun bindNotificationViewModel(viewModel: NotificationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OfferViewModel::class)
    abstract fun bindOfferViewModel(viewModel: OfferViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    abstract fun bindAccountViewModel(viewModel: AccountViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WalletViewModel::class)
    abstract fun bindWalletViewModel(viewModel: WalletViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PaymentHistoryViewModel::class)
    abstract fun bindPaymentHistoryViewModel(viewModel: PaymentHistoryViewModel): ViewModel

}