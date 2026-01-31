package com.example.ucpdua.view.route

import com.example.ucpdua.R
import com.example.ucpdua.view.route.DestinasiNavigasi

object DestinasiEditMatakuliah : DestinasiNavigasi {
    override val route = "edit_matakuliah" //
    override val titleRes = R.string.edit_matakuliah

    const val NIM = "nim"
    val routeWithArgs = "$route/{$NIM}"
}