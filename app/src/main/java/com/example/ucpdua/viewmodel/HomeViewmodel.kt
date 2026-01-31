package com.example.ucpdua.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucpdua.repositori.RepositoriProgramstudi
import com.example.ucpdua.room.perpustakaan
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repositoriProgramstudi: RepositoriProgramstudi
) : ViewModel() {

    var searchQuery by mutableStateOf("")
        private set

    fun updateSearchQuery(query: String) {
        searchQuery = query
    }
    val homeUiState: StateFlow<HomeUiState> = repositoriProgramstudi.getAllProgramstudiStream()
        .combine(snapshotFlow { searchQuery }) { listProdi, query ->
            val filteredList = if (query.isEmpty()) {
                listProdi
            } else {
                listProdi.filter {
                    it.namaProdi.contains(query, ignoreCase = true)
                }
            }
            HomeUiState(listProdi = filteredList)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState()
        )

    fun deleteProdi(prodi: perpustakaan) {
        viewModelScope.launch {
            repositoriProgramstudi.deleteProgramstudi(prodi)
        }
    }
}

data class HomeUiState(
    val listProdi: List<perpustakaan> = listOf()
)