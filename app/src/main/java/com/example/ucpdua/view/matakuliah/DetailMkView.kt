package com.example.ucpdua.view.matakuliah

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucpdua.view.uicontroller.TopAppBar
import com.example.ucpdua.viewmodel.DetailBukuViewModel
import com.example.ucpdua.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMkView(
    onNavigateBack: () -> Unit,
    onEditClick: (String) -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailBukuViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var deleteConfirmationRequired by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = "Detail Buku",
                canNavigateBack = true,
                navigateUp = onNavigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(uiState.detailMk.nim) },
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Edit Buku")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    ItemDetail(label = "ID Buku / ISBN", value = uiState.detailMk.nim)
                    ItemDetail(label = "Judul Buku", value = uiState.detailMk.namaMk)
                    ItemDetail(label = "Penerbit", value = uiState.detailMk.sks)
                    ItemDetail(label = "Penulis", value = uiState.detailMk.dosen)
                    ItemDetail(label = "Kategori", value = uiState.detailMk.namaProdi)

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Terakhir diperbarui: ${uiState.detailMk.tanggalUpdate.ifEmpty { "-" }}",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            }

            OutlinedButton(
                onClick = { deleteConfirmationRequired = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(Icons.Default.Delete, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Hapus Buku")
            }

            if (deleteConfirmationRequired) {
                AlertDialog(
                    onDismissRequest = { deleteConfirmationRequired = false },
                    title = { Text("Hapus Data Buku") },
                    text = { Text("Apakah anda yakin ingin menghapus buku ini dari koleksi?") },
                    confirmButton = {
                        TextButton(onClick = {
                            coroutineScope.launch {
                                viewModel.deleteMk()
                                deleteConfirmationRequired = false
                                onDeleteClick()
                            }
                        }) { Text("Ya, Hapus", color = Color.Red) }
                    },
                    dismissButton = {
                        TextButton(onClick = { deleteConfirmationRequired = false }) { Text("Tidak") }
                    }
                )
            }
        }
    }
}

@Composable
fun ItemDetail(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
        HorizontalDivider(modifier = Modifier.padding(top = 4.dp))
    }
}