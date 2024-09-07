package com.practicum.mymovies.di

import com.practicum.mymovies.core.impl.RouterImpl
import com.practicum.mymovies.core.navigation.Router
import org.koin.dsl.module

val navigationModule = module {
    val router = RouterImpl()

    single<Router> { router }

    single { router.navigatorHolder }
}