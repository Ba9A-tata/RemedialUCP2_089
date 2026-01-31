package com.example.ucpdua.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucpdua.repositori.RepositoriBuku
import com.example.ucpdua.room.Buku
import kotlinx.coroutines.flow.*

class DetailProdiViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoriBuku: RepositoriBuku
) : ViewModel() {

    private val _namaProdi: String = checkNotNull(savedStateHandle["namaProdi"])

    val detailProdiUiState: StateFlow<DetailProdiUiState> =
        repositoriBuku.getMatakuliahByProdiStream(_namaProdi)
            .filterNotNull()
            .map { DetailProdiUiState(listMk = it, prodiSekarang = _namaProdi) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = DetailProdiUiState()
            )
}

data class DetailProdiUiState(
    val listMk: List<Buku> = listOf(),
    val prodiSekarang: String = ""
)