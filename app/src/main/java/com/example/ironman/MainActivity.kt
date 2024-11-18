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

                    cards["Zamach"]?.let { player.addCard(it) }
                    cards["Rozpłatanie"]?.let { player.addCard(it) }
                    cards["Leczenie"]?.let { player.addCard(it) }
                    cards["Odsapka"]?.let { player.addCard(it) }
                    cards["Nakładanie Rąk"]?.let { player.addCard(it) }
                    cards["Grzmot"]?.let { player.addCard(it) }

                    player.updateDeck()
                    player.rollFullHand()


                    WeaponsList["Siewca Smierci"]?.let { player.inventoryWeapons.add(it) }
                    WeaponsList["Grzech Kaplana"]?.let { player.inventoryWeapons.add(it) }
                    WeaponsList["Topor wojownika"]?.let { player.inventoryWeapons.add(it) }
                    WeaponsList["Skorzany Pancerz"]?.let { player.inventoryWeapons.add(it) }
                    WeaponsList["Srebrny Pierscien"]?.let { player.inventoryWeapons.add(it) }
                    WeaponsList["Amulet Precyzji"]?.let { player.inventoryWeapons.add(it) }
                    WeaponsList["Drewniana Tarcza"]?.let { player.inventoryWeapons.add(it) }
                    WeaponsList["Helm"]?.let { player.inventoryWeapons.add(it) }
                    WeaponsList["Buty"]?.let { player.inventoryWeapons.add(it) }

                    RunesList["Runa sily"]?.let { player.inventoryRunes.add(it) }
                    RunesList["Runa inteligencji"]?.let { player.inventoryRunes.add(it) }
                    RunesList["Runa zrecznosci"]?.let { player.inventoryRunes.add(it) }
                    RunesList["Runa many"]?.let { player.inventoryRunes.add(it) }
                    RunesList["Runa akcji"]?.let { player.inventoryRunes.add(it) }

                    //generatedMap.generateMap()
                    //revealFullMap()
                    //generatedMap.lookForMapError(generatedMap.map)
                }
            }
        }
    }
}