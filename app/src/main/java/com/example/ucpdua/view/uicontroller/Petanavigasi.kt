package com.example.ucpdua.view.uicontroller

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucpdua.view.home.HomeScreen
import com.example.ucpdua.view.matakuliah.DetailMkView
import com.example.ucpdua.view.matakuliah.EditMkView
import com.example.ucpdua.view.matakuliah.EntryMkView
import com.example.ucpdua.view.programstudi.DetailProdiView
import com.example.ucpdua.view.programstudi.EntryProdiView
import com.example.ucpdua.view.route.*

@Composable
fun AkademikApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    HostNavigasi(navController = navController, modifier = modifier)
}

@Composable
fun HostNavigasi(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = modifier
    ) {
        composable(DestinasiHome.route) {
            HomeScreen(
                onProdiClick = { namaProdi ->
                    navController.navigate("detail_prodi/$namaProdi")
                },
                onAddProdiClick = {
                    navController.navigate(DestinasiEntryProgramstudi.route)
                }
            )
        }
        composable(DestinasiEntryProgramstudi.route) {
            EntryProdiView(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(
            route = "detail_prodi/{namaProdi}",
            arguments = listOf(navArgument("namaProdi") { type = NavType.StringType })
        ) {
            DetailProdiView(
                onNavigateBack = { navController.popBackStack() },
                onMkClick = { nim ->
                    navController.navigate("${DestinasiDetailMatakuliah.route}/$nim")
                },
                onAddMkClick = {
                    navController.navigate(DestinasiEntryMatakuliah.route)
                }
            )
        }
        composable(DestinasiEntryMatakuliah.route) {
            EntryMkView(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(
            route = DestinasiDetailMatakuliah.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetailMatakuliah.NIM) {
                type = NavType.StringType
            })
        ) {
            DetailMkView(
                onNavigateBack = { navController.popBackStack() },
                onEditClick = { nim ->
                    navController.navigate("${DestinasiEditMatakuliah.route}/$nim")
                },
                onDeleteClick = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = DestinasiEditMatakuliah.routeWithArgs,
            arguments = listOf(navArgument(DestinasiEditMatakuliah.NIM) {
                type = NavType.StringType
            })
        ) {
            EditMkView(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}