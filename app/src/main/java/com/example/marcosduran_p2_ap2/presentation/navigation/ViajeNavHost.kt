package com.example.marcosduran_p2_ap2.presentation.navigation

import com.example.marcosduran_p2_ap2.presentation.viajes.ViajeListScreen
import com.example.marcosduran_p2_ap2.presentation.viajes.ViajeScreen

@Composable
fun ViajeNavHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.ViajeList
    ) {
        composable <Screen.ViajeList> {
            ViajeListScreen { }(
                goToViaje = {
                    navHostController.navigate(Screen.viaje(it))
                },
                createViaje = {
                    navHostController.navigate(Screen.viaje(0))
                }
            )
        }

        composable<Screen.viaj> {
            val args = it.toRoute<Screen.viaje>()
            ViajeScreen  (
                viajeId = args.viajeId,
                goBack = {
                    navHostController.navigateUp()
                }
            )
        }
    }
}
