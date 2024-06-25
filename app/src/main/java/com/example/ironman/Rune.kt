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
    "Runa sily" to Rune("Runa sily", {player -> player.STR+=3;player.updateStats()},{player -> player.STR-=3;player.updateStats()}, mutableStateOf(R.drawable.runa_sily),"Runa Siły\n STR +3",false),
    "Runa inteligencji" to Rune("Runa inteligencji", {player -> player.INT+=3;player.updateStats()},{player -> player.INT-=3;player.updateStats()},mutableStateOf(R.drawable.runa_inteligencji),"Runa Inteligencji\n INT +3",false),
    "Runa zrecznosci" to Rune("Runa zrecznosci", {player -> player.DEX+=3;player.updateStats()},{player -> player.DEX-=3;player.updateStats()},mutableStateOf(R.drawable.runa_zrecznosi),"Runa Zręczności\n DEX +3",false),
    "Runa zdrowia" to Rune("Runa zdrowia", {player -> player.VIT+=3;player.updateStats()},{player -> player.VIT-=3;player.updateStats()},mutableStateOf(R.drawable.runa_zdrowia),"Runa Zdrowia\n VIT +3",false),
    "Runa akcji" to Rune("Runa akcji", {player -> player.MAX_AP.value+=1;player.AP_recovery+=1;player.updateStats()},{player -> player.MAX_AP.value-=1;player.AP_recovery-=1;player.updateStats()},mutableStateOf(R.drawable.runa_akcji),"Runa Akcji\n AP +1\nReneracja AP +1",false),
    "Wieksza runa sily" to Rune("Wieksza runa sily", {player -> player.STR+=6;player.updateStats()},{player -> player.STR-=6;player.updateStats()}, mutableStateOf(R.drawable.wieksza_runa_sily),"Wieksza runa Siły\n STR +6",false),
    "Wieksza runa inteligencji" to Rune("Wieksza runa inteligencji", {player -> player.INT+=6;player.updateStats()},{player -> player.INT-=6;player.updateStats()},mutableStateOf(R.drawable.wieksza_runa_inteligencji),"Wieksza runa Inteligencji\n INT +6",false),
    "Wieksza runa zrecznosci" to Rune("Wieksza runa zrecznosci", {player -> player.DEX+=6;player.updateStats()},{player -> player.DEX-=6;player.updateStats()},mutableStateOf(R.drawable.wieksza_runa_zrecznosi),"Wieksza runa Zręczności\n DEX +6",false),
    "Wieksza runa zdrowia" to Rune("Wieksza runa zdrowia", {player -> player.VIT+=6;player.updateStats()},{player -> player.VIT-=6;player.updateStats()},mutableStateOf(R.drawable.wieksza_runa_zdrowia),"Wieksza runa Zdrowia\n VIT +6",false),
    "Wieksza runa akcji" to Rune("Wieksza runa akcji", {player -> player.MAX_AP.value+=2;player.AP_recovery+=2;player.updateStats()},{player -> player.MAX_AP.value-=2;player.AP_recovery-=2;player.updateStats()},mutableStateOf(R.drawable.wieksza_runa_akcji),"Wieksza runa Akcji\n AP +2\nReneracja AP +2",false),
)