package com.example.ucpdua.viewmodel.provider

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucpdua.PerpustakaanApp
import com.example.ucpdua.viewmodel.DetailBukuViewModel
import com.example.ucpdua.viewmodel.EditBukuVIewModel
import com.example.ucpdua.viewmodel.EntryBukuViewModel
import com.example.ucpdua.viewmodel.EntryPerpustakaanViewModel
import com.example.ucpdua.viewmodel.HomeViewModel
import com.example.ucpdua.viewmodel.DetailProdiViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                aplikasiMatakuliah().container.repositoriPerpustakaan
            )
        }
        initializer {
            DetailProdiViewModel(
                this.createSavedStateHandle(),
                aplikasiMatakuliah().container.repositoriBuku
            )
        }

        initializer {
            EntryPerpustakaanViewModel(
                aplikasiMatakuliah().container.repositoriPerpustakaan
            )
        }

        initializer {
            EntryBukuViewModel(
                aplikasiMatakuliah().container.repositoriBuku,
                aplikasiMatakuliah().container.repositoriPerpustakaan
            )
        }

        initializer {
            DetailBukuViewModel(
                this.createSavedStateHandle(),
                aplikasiMatakuliah().container.repositoriBuku
            )
        }

        initializer {
            EditBukuVIewModel(
                this.createSavedStateHandle(),
                aplikasiMatakuliah().container.repositoriBuku,
                aplikasiMatakuliah().container.repositoriPerpustakaan
            )
        }
    }
}

fun CreationExtras.aplikasiMatakuliah(): PerpustakaanApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PerpustakaanApp)