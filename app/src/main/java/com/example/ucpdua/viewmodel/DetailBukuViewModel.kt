package com.example.ucpdua.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucpdua.repositori.RepositoriBuku
import com.example.ucpdua.view.route.DestinasiDetailMatakuliah
import kotlinx.coroutines.flow.*

class DetailBukuViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoriBuku: RepositoriBuku
) : ViewModel() {

    private val nim: String = checkNotNull(savedStateHandle[DestinasiDetailMatakuliah.NIM])

    val uiState: StateFlow<DetailUiState> =
        repositoriBuku.getMatakuliahStream(nim)
            .filterNotNull()
            .map { DetailUiState(detailMk = it.toMkUiState()) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = DetailUiState()
            )

    suspend fun deleteMk() {
        repositoriBuku.getMatakuliahStream(nim).firstOrNull()?.let {
            repositoriBuku.deleteMatakuliah(it)
        }
    }
}

data class DetailUiState(
    val detailMk: MkUiState = MkUiState()
)

fun com.example.ucpdua.room.Buku.toMkUiState(): MkUiState = MkUiState(
    nim = nim,
    namaMk = nama,
    sks = sks,
    dosen = dosen,
    namaProdi = namaProdi,
    tanggalUpdate = tanggalUpdate
)