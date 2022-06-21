package com.minal.admin.di_coin


import com.minal.admin.base.BaseRepository
import com.minal.admin.data.repo.AdminRepository
import org.koin.dsl.module

/**
 * Created at 30/03/2020.
 */

val repoModule = module {
   single { BaseRepository() }
//   single { AdminRepository() }


}