package com.example.ucpdua.view.programstudi

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucpdua.view.uicontroller.TopAppBar
import com.example.ucpdua.viewmodel.DetailProdiViewModel
import com.example.ucpdua.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailProdiView(
    onNavigateBack: () -> Unit,
    onMkClick: (String) -> Unit,
    onAddMkClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailProdiViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState by viewModel.detailProdiUiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = "Daftar Buku: ${uiState.prodiSekarang}",
                canNavigateBack = true,
                navigateUp = onNavigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onAddMkClick(uiState.prodiSekarang) }) {
                Icon(Icons.Default.Add, contentDescription = "Tambah MK")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            items(uiState.listMk) { mk ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { onMkClick(mk.nim) }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = mk.nama, style = MaterialTheme.typography.titleMedium)
                        Text(text = "Dosen: ${mk.dosen}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}