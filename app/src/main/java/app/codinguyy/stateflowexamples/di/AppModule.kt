package app.codinguyy.stateflowexamples.di

import app.codinguyy.stateflowexamples.FirstFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModules = module {
    viewModel { FirstFragmentViewModel() }
}