package com.example.ironman

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Rune(
    var name: String,
    @Transient var equipEffect: (Player) -> Unit ={},
    @Transient var deequipEffect: (Player) -> Unit ={},
    var sprite: MutableState<Int>,
    var description: String,
    var isActive: Boolean

)

var RunesList = mutableMapOf<String, Rune>(
    "Brak Runy" to Rune("Brak Runy", {},{}, mutableStateOf(R.drawable.empty_slot),"Brak Runy",false),
    "Runa Siły" to Rune("Runa Sily", {player -> player.STR+=1;player.updateStats()},{player -> player.STR-=1;player.updateStats()}, mutableStateOf(R.drawable.runa_sily),"Runa Siły\n +1 STR",false),
    "Runa Inteligencji" to Rune("Runa Inteligencji", {player -> player.INT+=1;player.updateStats()},{player -> player.INT-=1;player.updateStats()},mutableStateOf(R.drawable.runa_inteligencji),"Runa Inteligencji\n+1 INT",false),
    "Runa Zręcznosci" to Rune("Runa Zręcznosci", {player -> player.DEX+=1;player.updateStats()},{player -> player.DEX-=1;player.updateStats()},mutableStateOf(R.drawable.runa_zrecznosi),"Runa Zręczności\n+1 DEX",false),
    "Runa Zdrowia" to Rune("Runa Zdrowia", {player -> player.VIT+=1;player.updateStats()},{player -> player.VIT-=1;player.updateStats()},mutableStateOf(R.drawable.runa_zdrowia),"Runa Zdrowia\n+1 VIT",false),
    "Runa Akcji" to Rune("Runa Akcji", {player -> player.APbonus+=1;player.updateStats()},{player -> player.APbonus-=1;player.updateStats()},mutableStateOf(R.drawable.runa_akcji),"Runa Akcji\n+1 AP",false),
    "Większa Runa Siły" to Rune("Większa Runa Sily", {player -> player.STR+=2;player.updateStats()},{player -> player.STR-=2;player.updateStats()}, mutableStateOf(R.drawable.wieksza_runa_sily),"Większa Runa Siły\n+2 STR",false),
    "Większa Runa Inteligencji" to Rune("Większa Runa Inteligencji", {player -> player.INT+=2;player.updateStats()},{player -> player.INT-=2;player.updateStats()},mutableStateOf(R.drawable.wieksza_runa_inteligencji),"Większa Runa Inteligencji\n+2 INT",false),
    "Większa Runa Zręcznosci" to Rune("Większa Runa Zręcznosci", {player -> player.DEX+=2;player.updateStats()},{player -> player.DEX-=2;player.updateStats()},mutableStateOf(R.drawable.wieksza_runa_zrecznosi),"Większa Runa Zręczności\n+2 DEX",false),
    "Większa Runa Zdrowia" to Rune("Większa Runa Zdrowia", {player -> player.VIT+=2;player.updateStats()},{player -> player.VIT-=2;player.updateStats()},mutableStateOf(R.drawable.wieksza_runa_zdrowia),"Większa Runa Zdrowia\n+2 VIT",false),
    "Większa Runa Akcji" to Rune("Większa Runa Akcji", {player -> player.APbonus+=2;player.updateStats()},{player -> player.APbonus-=2;player.updateStats()},mutableStateOf(R.drawable.wieksza_runa_akcji),"Większa Runa Akcji\n+2 AP",false),
)