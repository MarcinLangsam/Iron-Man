package com.example.ironman

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CharacterScreen(){

    val playerInfo = remember {
        listOf(
            player.name,
            player.lv,
            player.MAX_HP.value,
            player.HP.value,
            player.MAX_AP.value,
            player.AP_recovery,
            player.STR,
            player.VIT,
            player.DEX,
            player.INT,
            player.weapon.name,
            player.weaponDamage,
            player.maxRunes.value,
            player.gold
        )
    }

    Row(
        Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFACD))
            .padding(10.dp),
    ) {
        Column {
            Text(
                modifier = Modifier.padding(5.dp),
                text = playerInfo[0].toString(), //nazwa
                fontSize = 20.sp
            )
            Text(
                modifier = Modifier.padding(5.dp),
                text = "Poziom: "+playerInfo[1].toString(), //lv
                fontSize = 20.sp
            )
            Text(
                modifier = Modifier.padding(5.dp),
                text = "HP: "+playerInfo[3].toString()+"/"+playerInfo[2].toString(), //HP
                fontSize = 20.sp
            )
            Text(
                modifier = Modifier.padding(5.dp),
                text = "Punkty akcji: "+playerInfo[4].toString(), //AP
                fontSize = 20.sp
            )
            Text(
                modifier = Modifier.padding(5.dp),
                text = "Odnowa punktów akcji: "+playerInfo[5].toString(), //AP
                fontSize = 20.sp
            )
        }
        Column {
            Text(
                modifier = Modifier.padding(5.dp),
                text = "Siła: "+playerInfo[6].toString(), //STR
                fontSize = 20.sp
            )
            Text(
                modifier = Modifier.padding(5.dp),
                text = "Witalność: "+playerInfo[7].toString(), //VIT
                fontSize = 20.sp
            )
            Text(
                modifier = Modifier.padding(5.dp),
                text = "Zręczność: "+playerInfo[8].toString(), //DEX
                fontSize = 20.sp
            )
            Text(
                modifier = Modifier.padding(5.dp),
                text = "Inteligencja: "+playerInfo[9].toString(), //INT
                fontSize = 20.sp
            )
            Text(
                modifier = Modifier.padding(5.dp),
                text = "Broń: "+playerInfo[10].toString(), //weapon
                fontSize = 20.sp
            )
            Text(
                modifier = Modifier.padding(5.dp),
                text = "Obrażenia: "+playerInfo[11].toString(), //weaponDamage
                fontSize = 20.sp
            )
            Text(
                modifier = Modifier.padding(5.dp),
                text = "Ilość Run na Broni: "+playerInfo[12].toString(), //maxRunes
                fontSize = 20.sp
            )
        }
        Column {
            Text(
                modifier = Modifier.padding(5.dp),
                text = "Zloto: "+playerInfo[13].toString(), //zloto
                fontSize = 20.sp
            )
        }
    }
}
