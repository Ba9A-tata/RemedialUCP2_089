package com.example.ucpdua.view.route

import com.example.ucpdua.R

object DestinasiDetailMatakuliah : DestinasiNavigasi {
    override val route = "detail_matakuliah"
    override val titleRes = R.string.detail_matakuliah

    const val NIM = "nim"
    val routeWithArgs = "$route/{$NIM}"
}