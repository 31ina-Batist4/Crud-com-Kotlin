package com.elina.app.crudkotlin.di

import androidx.lifecycle.ViewModelProvider
import com.elina.app.crudkotlin.vmfactory.StudentViewModelFactory


abstract class ViewModelFactoryModule {


    internal abstract fun bindViewModelFactory(vMFactory : StudentViewModelFactory) : ViewModelProvider.Factory
}