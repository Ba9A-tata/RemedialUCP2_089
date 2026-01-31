package com.example.ucpdua.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucpdua.repositori.RepositoriBuku
import com.example.ucpdua.repositori.RepositoriPerpustakaan
import com.example.ucpdua.room.perpustakaan
import com.example.ucpdua.view.route.DestinasiEditMatakuliah
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EditBukuVIewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoriBuku: RepositoriBuku,
    private val repositoriPerpustakaan: RepositoriPerpustakaan
) : ViewModel() {

    var uiState by mutableStateOf(MkUiState())
        private set

    private val _nim: String = checkNotNull(savedStateHandle[DestinasiEditMatakuliah.NIM])

    val prodiList: Flow<List<perpustakaan>> = repositoriPerpustakaan.getAllProgramstudiStream()

    init {
        viewModelScope.launch {
            uiState = repositoriBuku.getMatakuliahStream(_nim)
                .filterNotNull()
                .first()
                .toMkUiState(isEntryValid = true)
        }
    }

    fun updateUiState(newUiState: MkUiState) {
        uiState = newUiState.copy(isEntryValid = validateInput(newUiState))
    }

    suspend fun updateMatakuliah() {
        if (validateInput(uiState)) {
            val currentTimestamp = getTimestampHelper()
            val finalUiState = uiState.copy(tanggalUpdate = currentTimestamp)
            repositoriBuku.updateMatakuliah(finalUiState.toMkEntity())
        }
    }

    private fun validateInput(state: MkUiState = uiState): Boolean {
        return with(state) {
            nim.isNotBlank() && namaMk.isNotBlank() && sks.isNotBlank() &&
                    dosen.isNotBlank() && namaProdi.isNotBlank()
        }
    }
}

fun getTimestampHelper(): String {
    return SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale.getDefault()).format(Date())
}