package com.example.moviesapi.domain.di

import com.example.moviesapi.domain.transform.MovieTransform
import com.example.moviesapi.domain.usecase.GetTopRatedMoviesUseCase
import com.example.moviesapi.domain.usecase.SearchMoviesUseCase
import org.koin.dsl.module

val domainModule = module{
    factory { MovieTransform() }
    factory { GetTopRatedMoviesUseCase(get(), get()) }
    factory { SearchMoviesUseCase(get(), get()) }
}