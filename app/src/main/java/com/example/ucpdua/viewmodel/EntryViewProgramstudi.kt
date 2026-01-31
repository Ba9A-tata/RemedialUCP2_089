package com.example.ucpdua.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.ucpdua.repositori.RepositoriProgramstudi
import com.example.ucpdua.room.perpustakaan

class EntryViewProgramstudi(
    private val repositoriProgramstudi: RepositoriProgramstudi
) : ViewModel() {

    var uiState by mutableStateOf(PstUiState())
        private set

    fun updateUiState(pstUiState: PstUiState) {
        uiState = pstUiState
    }

    suspend fun saveProdi() {
        if (validateFields()) {
            repositoriProgramstudi.insertProgramstudi(uiState.toPstEntity())
        }
    }

    private fun validateFields(): Boolean {
        return uiState.namaProdi.isNotEmpty()
    }
}

data class PstUiState(
    val namaProdi: String = ""
)

fun PstUiState.toPstEntity(): perpustakaan = perpustakaan(
    namaProdi = namaProdi
)