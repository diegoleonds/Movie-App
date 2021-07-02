package com.example.moviesapi.data.di

import com.example.moviesapi.data.api.RetrofitInstance
import com.example.moviesapi.data.error.GeneralErrorHandlerImpl
import com.example.moviesapi.data.repository.MovieRepositoryImpl
import org.koin.dsl.module

val dataModule = module {
    single { MovieRepositoryImpl(RetrofitInstance.api, get()) }
    factory { GeneralErrorHandlerImpl() }
}