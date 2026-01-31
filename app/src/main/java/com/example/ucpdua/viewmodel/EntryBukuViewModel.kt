package com.example.ucpdua.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.ucpdua.repositori.RepositoriBuku
import com.example.ucpdua.repositori.RepositoriPerpustakaan
import com.example.ucpdua.room.Buku
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EntryBukuViewModel(
    private val repositoriBuku: RepositoriBuku,
    private val repositoriPerpustakaan: RepositoriPerpustakaan
) : ViewModel() {

    var uiState by mutableStateOf(MkUiState())
        private set

    val prodiList: Flow<List<com.example.ucpdua.room.perpustakaan>> =
        repositoriPerpustakaan.getAllProgramstudiStream()

    fun updateUiState(mkUiState: MkUiState) {
        uiState = mkUiState.copy(isEntryValid = validateInput(mkUiState))
    }

    private fun validateInput(uiState: MkUiState): Boolean {
        return with(uiState) {
            nim.isNotBlank() && namaMk.isNotBlank() && sks.isNotBlank() &&
                    dosen.isNotBlank() && namaProdi.isNotBlank()
        }
    }
    suspend fun saveMk() {
        if (validateInput(uiState)) {
            val currentTimestamp = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale.getDefault()).format(Date())
            val finalUiState = uiState.copy(tanggalUpdate = currentTimestamp)
            repositoriBuku.insertMatakuliah(finalUiState.toMkEntity())
        }
    }
}

data class MkUiState(
    val nim: String = "",
    val namaMk: String = "",
    val sks: String = "",
    val dosen: String = "",
    val namaProdi: String = "",
    val tanggalUpdate: String = "",
    val isEntryValid: Boolean = false
)

fun MkUiState.toMkEntity(): Buku = Buku(
    nim = nim,
    nama = namaMk,
    sks = sks,
    dosen = dosen,
    namaProdi = namaProdi,
    tanggalUpdate = tanggalUpdate
)
fun Buku.toMkUiState(isEntryValid: Boolean = false): MkUiState = MkUiState(
    nim = nim,
    namaMk = nama,
    sks = sks,
    dosen = dosen,
    namaProdi = namaProdi,
    tanggalUpdate = tanggalUpdate,
    isEntryValid = isEntryValid
)