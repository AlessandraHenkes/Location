package com.henkes.location.ui.navigation

sealed class Route(val path: String) {

    object Splash : Route("splash")

    object Home : Route("home")

    object Locations : Route("locations")

}
