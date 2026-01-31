package com.example.ucpdua.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tblMatakuliah",
    foreignKeys = [
        ForeignKey(
            entity = perpustakaan::class,
            parentColumns = ["namaProdi"],
            childColumns = ["namaProdi"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["namaProdi"])]
)
data class Buku(
    @PrimaryKey
    val nim: String,
    val nama: String,
    val sks: String,
    val dosen: String,
    val namaProdi: String,
    val tanggalUpdate: String
)