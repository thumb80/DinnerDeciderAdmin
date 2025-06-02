package it.antonino.dinnerdecideradmin.di

import it.antonino.dinnerdecideradmin.net.Network
import it.antonino.dinnerdecideradmin.net.NetworkInterface
import it.antonino.dinnerdecideradmin.net.NetworkRepository
import it.antonino.dinnerdecideradmin.viewmodel.DinnerDeciderAdminViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val module = module {

    single {
        Network.getInstance(get())
    }

    single {
        NetworkRepository.getInstance(get())
    }

    single {
        get<Retrofit>()
            .create(NetworkInterface::class.java)
    }

    viewModel {
        DinnerDeciderAdminViewModel(get())
    }

}