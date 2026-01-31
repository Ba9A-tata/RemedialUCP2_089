package com.example.ucpdua.viewmodel.provider

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucpdua.MatakuliahApp
import com.example.ucpdua.viewmodel.DetailViewModel
import com.example.ucpdua.viewmodel.EditViewModel
import com.example.ucpdua.viewmodel.EntryViewMatakuliah
import com.example.ucpdua.viewmodel.EntryViewProgramstudi
import com.example.ucpdua.viewmodel.HomeViewModel
import com.example.ucpdua.viewmodel.DetailProdiViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                aplikasiMatakuliah().container.repositoriProgramstudi
            )
        }
        initializer {
            DetailProdiViewModel(
                this.createSavedStateHandle(),
                aplikasiMatakuliah().container.repositoriMatakuliah
            )
        }

        initializer {
            EntryViewProgramstudi(
                aplikasiMatakuliah().container.repositoriProgramstudi
            )
        }

        initializer {
            EntryViewMatakuliah(
                aplikasiMatakuliah().container.repositoriMatakuliah,
                aplikasiMatakuliah().container.repositoriProgramstudi
            )
        }

        initializer {
            DetailViewModel(
                this.createSavedStateHandle(),
                aplikasiMatakuliah().container.repositoriMatakuliah
            )
        }

        initializer {
            EditViewModel(
                this.createSavedStateHandle(),
                aplikasiMatakuliah().container.repositoriMatakuliah,
                aplikasiMatakuliah().container.repositoriProgramstudi
            )
        }
    }
}

fun CreationExtras.aplikasiMatakuliah(): MatakuliahApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MatakuliahApp)