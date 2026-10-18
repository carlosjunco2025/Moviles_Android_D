package com.junco.huellitasvet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Factory generica: recibe una lambda que arma el ViewModel con sus dependencias.
 *
 *     viewModel(factory = VetViewModelFactory { MiViewModel(repo) })
 */
class VetViewModelFactory<VM : ViewModel>(private val armar: () -> VM) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = armar() as T
}
