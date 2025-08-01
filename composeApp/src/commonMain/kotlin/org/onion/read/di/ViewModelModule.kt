package org.onion.read.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.onion.read.viewmodel.BookSourceViewModel

val viewModelModule  = module {
    viewModelOf(::BookSourceViewModel)
}