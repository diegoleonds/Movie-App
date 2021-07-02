package com.example.moviesapi.ui.di

import com.example.moviesapi.ui.viewmodel.MovieInfoViewModel
import com.example.moviesapi.ui.viewmodel.MovieListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
     viewModel { MovieListViewModel(get()) }
     viewModel { MovieInfoViewModel() }
}