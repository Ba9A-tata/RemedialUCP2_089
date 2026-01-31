package com.example.ucpdua.view.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucpdua.room.perpustakaan
import com.example.ucpdua.viewmodel.HomeViewModel
import com.example.ucpdua.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onProdiClick: (String) -> Unit,
    onAddProdiClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    var prodiYangAkanDihapus by remember { mutableStateOf<perpustakaan?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Daftar Perpustakaan") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddProdiClick,
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Kategori")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = viewModel.searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                label = { Text("Cari Kategori Buku...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = MaterialTheme.shapes.medium,
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = Color.Gray
                )
            )
            if (homeUiState.listProdi.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (viewModel.searchQuery.isEmpty()) "Belum ada data Kategori" else "Kategori tidak ditemukan",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    items(homeUiState.listProdi) { prodi ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable { onProdiClick(prodi.namaProdi) },
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = prodi.namaProdi,
                                    style = MaterialTheme.typography.titleLarge
                                )

                                IconButton(onClick = { prodiYangAkanDihapus = prodi }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Hapus Kategori",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        prodiYangAkanDihapus?.let { prodi ->
            AlertDialog(
                onDismissRequest = { prodiYangAkanDihapus = null },

                title = { Text("Hapus Kategori") },
                text = { Text("Apakah Anda yakin ingin menghapus kategori ${prodi.namaProdi}? Semua koleksi buku di dalamnya akan hilang.") },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.deleteProdi(prodi)
                        prodiYangAkanDihapus = null
                    }) { Text("Hapus", color = Color.Red) }
                },
                dismissButton = {
                    TextButton(onClick = { prodiYangAkanDihapus = null }) { Text("Batal") }
                }
            )
        }
    }
}