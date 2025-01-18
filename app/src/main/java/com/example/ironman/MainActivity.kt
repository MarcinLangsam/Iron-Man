package com.example.ironman

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.ironman.ui.theme.IronManTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IronManTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()


                    /*ItemsList["Siewca Smierci"]?.let { player.inventoryItems.add(it) }
                    ItemsList["Grzech Kaplana"]?.let { player.inventoryItems.add(it) }
                    ItemsList["Topor wojownika"]?.let { player.inventoryItems.add(it) }
                    ItemsList["Skorzany Pancerz"]?.let { player.inventoryItems.add(it) }
                    ItemsList["Srebrny Pierscien"]?.let { player.inventoryItems.add(it) }
                    ItemsList["Amulet Precyzji"]?.let { player.inventoryItems.add(it) }
                    ItemsList["Drewniana Tarcza"]?.let { player.inventoryItems.add(it) }
                    ItemsList["Helm"]?.let { player.inventoryItems.add(it) }
                    ItemsList["Buty"]?.let { player.inventoryItems.add(it) }

                    RunesList["Runa sily"]?.let { player.inventoryRunes.add(it) }
                    RunesList["Runa inteligencji"]?.let { player.inventoryRunes.add(it) }
                    RunesList["Runa zrecznosci"]?.let { player.inventoryRunes.add(it) }
                    RunesList["Runa many"]?.let { player.inventoryRunes.add(it) }
                    RunesList["Runa akcji"]?.let { player.inventoryRunes.add(it) }*/

                    //generatedMap.generateMap()
                    //revealFullMap()
                    //generatedMap.lookForMapError(generatedMap.map)
                }
            }
        }
    }
}