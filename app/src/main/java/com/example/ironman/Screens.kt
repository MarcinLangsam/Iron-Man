package com.example.ironman

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

sealed class Screens(val route: String) {
    object MainMenuScreen : Screens("MainMenuScreen")
    object MainMapScreen : Screens("MainMap")
    object  InventoryScreen : Screens("InventoryScreen")
    object  SkillScreen : Screens("SkillScreen")
    object  ModifierScreen : Screens("ModifierScreen")
    object  SettingsScreen : Screens("SettingsScreen")
    object  FightScreen : Screens("FightScreen")
    object  LevelUpScreen : Screens("LevelUpScreen")
    object  CharacterCreationScreen : Screens("CharacterCreationScreen")
    object  EndScreen : Screens("EndScreen")
}

sealed class BottomBar(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object MainMapScreen : BottomBar(Screens.MainMapScreen.route, "Mapa", Icons.Default.Home)
    object InventoryScreen : BottomBar(Screens.InventoryScreen.route, "Ekwipunek", Icons.Default.MailOutline)
    object SkillScreen : BottomBar(Screens.SkillScreen.route, "Karty", Icons.Default.Share)
    object ModifierScreen : BottomBar(Screens.ModifierScreen.route, "Modyfikatory", Icons.Default.Build)
    object SettingsScreen : BottomBar(Screens.SettingsScreen.route, "Ustawienia", Icons.Default.Settings)
}


@Composable
fun SettingsScreen(){
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Red),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Settings",
            fontSize = 40.sp
        )
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Navigation() {
    val navController = rememberNavController()

    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    bottomBarState.value = true

    when (navBackStackEntry?.destination?.route) {
        "FightScreen" -> {
            bottomBarState.value = false
        }
    }

    when (navBackStackEntry?.destination?.route) {
        "CharacterCreationScreen" -> {
            bottomBarState.value = false
        }
    }

    when (navBackStackEntry?.destination?.route) {
        "MainMenuScreen" -> {
            bottomBarState.value = false
        }
    }

    when (navBackStackEntry?.destination?.route) {
        "LevelUpScreen" -> {
            bottomBarState.value = false
        }
    }

    when (navBackStackEntry?.destination?.route) {
        "SettingsScreen" -> {
            bottomBarState.value = false
        }
    }


    Scaffold(
        bottomBar = { BottomMenu(navController = navController, bottomBarState = bottomBarState)},
        content = { BottomNavGraph(navController = navController) }
    )


}

@Composable
fun BottomNavGraph(navController: NavHostController){

    NavHost(
        navController = navController,
        startDestination = Screens.MainMenuScreen.route
    ) {
        composable(route = Screens.MainMapScreen.route){ MainMapScreen({ navController.navigate(Screens.FightScreen.route) },
            { navController.navigate(Screens.FightScreen.route) }) }
        composable(route = Screens.MainMenuScreen.route){
            MainMenuScreen(onCharacterCreationScreen = { navController.navigate(Screens.CharacterCreationScreen.route) },
                context = LocalContext.current,
                onMapScreen = { navController.navigate(Screens.MainMapScreen.route) }
            )
        }
        composable(route = Screens.CharacterCreationScreen.route){ CharacterCreationScreen(onMainMapScreen = { navController.navigate(Screens.MainMapScreen.route) }, context = LocalContext.current) }
        composable(route = Screens.InventoryScreen.route){ InventoryScreen() }
        composable(route = Screens.SkillScreen.route){ SkillScreen(){
            //player.updateDeck()
        } }
        composable(route = Screens.ModifierScreen.route){ ModifierScreen(){
            player.updateDeckModfiers()
        } }
        composable(route = Screens.MainMapScreen.route){
            MainMapScreen(
                {navController.navigate(Screens.FightScreen.route) },
                {navController.navigate(Screens.LevelUpScreen.route)})
        }
        composable(route = Screens.SettingsScreen.route){ SettingsScreen() }
        composable(route = Screens.FightScreen.route){ FightScreen(
            onMap = { navController.popBackStack() },
        ) }
        composable(route = Screens.LevelUpScreen.route) {
            LevelUpScreen(
                onMap = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun BottomMenu(navController: NavHostController, bottomBarState: MutableState<Boolean>) {
    val screens = listOf(
        BottomBar.MainMapScreen, BottomBar.InventoryScreen, BottomBar.SkillScreen, BottomBar.ModifierScreen, BottomBar.SettingsScreen
    )

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomStart
    ) {
        AnimatedVisibility(
            modifier = Modifier
                .width(380.dp)
                .height(55.dp)
                .background(Color.DarkGray, shape = RectangleShape),
            visible = bottomBarState.value,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it }),
            content = {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                NavigationBar(
                    modifier = Modifier
                        .background(Color.DarkGray, shape = RectangleShape) // Tło ciemnoszare
                        .border(
                            width = 1.dp,
                            color = Color(0xFFFFD700), // Złota ramka
                            shape = RectangleShape
                        )
                ) {
                    screens.forEach { screen ->
                        NavigationBarItem(
                            modifier = Modifier.background(Color.Black, shape = RectangleShape),
                            label = { Text(text = screen.title, fontSize = 8.sp) },
                            icon = {
                                Icon(
                                    imageVector = screen.icon,
                                    contentDescription = "icon",
                                    modifier = Modifier.size(15.dp)
                                )
                            },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = { navController.navigate(screen.route) }
                        )
                    }
                }
            }
        )
    }
}
