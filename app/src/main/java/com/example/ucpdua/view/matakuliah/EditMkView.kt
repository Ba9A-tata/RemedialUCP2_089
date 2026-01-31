package com.example.ucpdua.view.matakuliah

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucpdua.view.uicontroller.TopAppBar
import com.example.ucpdua.viewmodel.EditViewModel
import com.example.ucpdua.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditMkView(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = "Edit Mata Kuliah",
                canNavigateBack = true,
                navigateUp = onNavigateBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = uiState.namaMk,
                onValueChange = { viewModel.updateUiState(uiState.copy(namaMk = it)) },
                label = { Text("Nama Mata Kuliah") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.sks,
                onValueChange = { viewModel.updateUiState(uiState.copy(sks = it)) },
                label = { Text("SKS") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.dosen,
                onValueChange = { viewModel.updateUiState(uiState.copy(dosen = it)) },
                label = { Text("Dosen") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.updateMatakuliah()
                        onNavigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Simpan Perubahan")
            }
        }
    }
}