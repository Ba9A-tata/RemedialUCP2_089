package com.example.ucpdua.view.matakuliah

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucpdua.room.perpustakaan
import com.example.ucpdua.viewmodel.EntryBukuViewModel
import com.example.ucpdua.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryMkView(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EntryBukuViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState
    val prodiList by viewModel.prodiList.collectAsState(initial = emptyList())
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tambah Buku Baru") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = uiState.nim,
                onValueChange = { viewModel.updateUiState(uiState.copy(nim = it)) },
                label = { Text("ID Buku / ISBN") },
                placeholder = { Text("Contoh: pamremed089") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.namaMk,
                onValueChange = { viewModel.updateUiState(uiState.copy(namaMk = it)) },
                label = { Text("Judul Buku") },
                modifier = Modifier.fillMaxWidth()
            )

            ProdiDropdown(
                selectedProdi = uiState.namaProdi,
                onProdiSelected = { viewModel.updateUiState(uiState.copy(namaProdi = it)) },
                prodiList = prodiList
            )
            OutlinedTextField(
                value = uiState.sks,
                onValueChange = { viewModel.updateUiState(uiState.copy(sks = it)) },
                label = { Text("Penerbit") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.dosen,
                onValueChange = { viewModel.updateUiState(uiState.copy(dosen = it)) },
                label = { Text("Penulis / Pengarang") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveMk()
                        onNavigateBack()
                    }
                },
                enabled = uiState.isEntryValid,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Simpan Koleksi Buku")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProdiDropdown(
    selectedProdi: String,
    onProdiSelected: (String) -> Unit,
    prodiList: List<perpustakaan>
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedProdi,
            onValueChange = {},
            readOnly = true,
            label = { Text("Pilih Kategori / Perpustakaan") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            if (prodiList.isEmpty()) {
                DropdownMenuItem(
                    text = { Text("Kategori belum ada") },
                    onClick = { expanded = false }
                )
            } else {
                prodiList.forEach { prodi ->
                    DropdownMenuItem(
                        text = { Text(prodi.namaProdi) },
                        onClick = {
                            onProdiSelected(prodi.namaProdi)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}