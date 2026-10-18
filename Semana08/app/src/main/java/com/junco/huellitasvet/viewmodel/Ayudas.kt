package com.junco.huellitasvet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

/**
 * Convierte un Flow de Room en StateFlow ligado al ViewModel.
 * Se mantiene activo 5 s despues de que la pantalla deja de observarlo (rotaciones, etc.).
 */
fun <T> Flow<T>.comoEstado(vm: ViewModel, inicial: T): StateFlow<T> =
    stateIn(vm.viewModelScope, SharingStarted.WhileSubscribed(5_000), inicial)
