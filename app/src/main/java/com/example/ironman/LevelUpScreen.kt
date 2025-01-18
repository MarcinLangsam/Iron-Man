package com.example.ironman

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlin.random.Random

lateinit var chosenProfesion: Profesions
var hpLvUp = 0
var perkLvUp = ""
var attributeLvUp = ""

@Composable
fun LevelUpScreen(onMap: () -> Unit) {
    var currentScreen by remember { mutableStateOf(0) }
    val professionText = player.profesions.joinToString(separator = "\n") {
        "${it.name} (${it.profesionLv})"
    }

    fun nextScreen() {
        if (currentScreen < 3) currentScreen++
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFACD))
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFFACD))
                .padding(16.dp)
        ) {
            // Pierwsza kolumna
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Top
            ) {
                Image(
                    painter = painterResource(id = player.portrait),
                    contentDescription = "Portret gracza",
                    modifier = Modifier.size(150.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(professionText)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxHeight()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    when (currentScreen) {
                        0 -> ProfesionComponentLvUp()
                        1 -> AttributeComponentLvUp()
                        2 -> SkillsComponentLvUp()
                        3 -> SummaryComponentLvUp()
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    when {
                        currentScreen == 1 -> {
                            Button(onClick = { com.example.ironman.player.levelUpPlayer(
                                chosenProfesion); nextScreen() }) {
                                Text("DALEJ")
                            }
                        }
                        currentScreen < 3 -> {
                            Button(onClick = { nextScreen() }) {
                                Text("DALEJ")
                            }
                        }
                        else -> {
                            Button(onClick = { onMap() }) {
                                Text("ZAKOŃCZ")
                            }
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun ProfesionComponentLvUp(){
    var profesion by remember { mutableStateOf("") }

    Row(modifier = Modifier.fillMaxSize()) {
        Column {
            Text("Wojownik", modifier = Modifier.padding(5.dp).clickable {
                chosenProfesion = profesionWarrior
                profesion = "Wojownik"

            }.background(
                if (profesion.contains("Wojownik")) Color.Gray else Color.Transparent)
            )
            Text("Łowca", modifier = Modifier.padding(5.dp).clickable {
                chosenProfesion = profesionHunter
                profesion = "Łowca"
            }.background(
                if (profesion.contains("Łowca")) Color.Gray else Color.Transparent)
            )
            Text("Mag", modifier = Modifier.padding(5.dp).clickable {
                chosenProfesion = profesionMage
                profesion = "Mag"
            }.background(
                if (profesion.contains("Mag")) Color.Gray else Color.Transparent)
            )
        }
    }
}


@Composable
fun AttributeComponentLvUp() {
    var attributesPoits by remember { mutableStateOf(1) }
    var pSTR = remember { mutableStateOf(player.STR) }
    var pVIT = remember { mutableStateOf(player.VIT) }
    var pDEX = remember { mutableStateOf(player.DEX) }
    var pINT = remember { mutableStateOf(player.INT) }

    fun updateAttribute(attribute: (Int) -> Unit, currentValue: MutableState<Int>, value: Int) : Boolean
    {
        if(value == -1)
        {
            if(currentValue.value > 10)
            {
                attribute(currentValue.value + value)
                attributesPoits += 1
                return true
            }
        }
        else {
            if (attributesPoits > 0) {
                attribute(currentValue.value + value)
                attributesPoits -= 1
                return true;
            }
        }
        return false
    }

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.Start) {
        Text("Punkty do wydania: $attributesPoits")
        Row(modifier = Modifier.padding(3.dp)) {
            Text("Siła ${pSTR.value}")
            Button(onClick = { if(updateAttribute({ pSTR.value = it }, pSTR, +1)){ player.STR += 1 ; attributeLvUp = "Siła +1"} }, modifier = Modifier.width(50.dp).height(35.dp)) {
                Text("+")
            }
            Button(onClick = { if(updateAttribute({ pSTR.value = it }, pSTR, -1)){ player.STR -= 1 ; attributeLvUp = ""} }, modifier = Modifier.width(50.dp).height(35.dp)) {
                Text("-")
            }
        }
        Row {
            Text("Kondycja ${pVIT.value}")
            Button(onClick = { if(updateAttribute({ pVIT.value = it }, pVIT, +1)){ player.VIT += 1 ; attributeLvUp = "Kondycja +1"} }, modifier = Modifier.width(50.dp).height(35.dp)) {
                Text("+")
            }
            Button(onClick = { if(updateAttribute({ pVIT.value = it }, pVIT, -1)){ player.VIT -= 1 ; attributeLvUp = ""} }, modifier = Modifier.width(50.dp).height(35.dp)) {
                Text("-")
            }
        }
        Row {
            Text("Zręczność ${pDEX.value}")
            Button(onClick = { if(updateAttribute({ pDEX.value = it }, pDEX, +1)){ player.DEX += 1 ; attributeLvUp = "Zręczność +1"} }, modifier = Modifier.width(50.dp).height(35.dp)) {
                Text("+")
            }
            Button(onClick = { if(updateAttribute({ pDEX.value = it }, pDEX, -1)){ player.DEX -= 1 ; attributeLvUp = ""} }, modifier = Modifier.width(50.dp).height(35.dp)) {
                Text("-")
            }
        }
        Row {
            Text("Inteligencja ${pINT.value}")
            Button(onClick = { if(updateAttribute({ pINT.value = it }, pINT, +1)){ player.INT += 1; attributeLvUp = "Inteligencja +1"} }, modifier = Modifier.width(50.dp).height(35.dp)) {
                Text("+")
            }
            Button(onClick = { if(updateAttribute({ pINT.value = it }, pINT, -1)){ player.INT -= 1 ; attributeLvUp = ""} }, modifier = Modifier.width(50.dp).height(35.dp)) {
                Text("-")
            }
        }
    }
}


@Composable
fun SkillsComponentLvUp(){
    var perkPoints by remember { mutableStateOf(1) }
    val selectedPerks = remember { mutableSetOf<Perks>() }

    Column(modifier = Modifier.fillMaxSize().padding(2.dp)) {
        Text("Punkty atutów: $perkPoints")
            player.profesions.forEach { profession ->
                val perksForProfession = profession.perks[profession.profesionLv]

                perksForProfession?.forEach { perk ->
                    perk?.let {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(1.dp)
                                .clickable {
                                    if (selectedPerks.contains(it)) {
                                        selectedPerks.remove(it)
                                        it.effect.invoke()
                                        perkLvUp = it.name
                                        perkPoints++
                                    } else if (perkPoints > 0) {
                                        selectedPerks.add(it)
                                        it.undoEffect.invoke()
                                        perkLvUp = ""
                                        perkPoints--
                                    }
                                }
                                .background(
                                    if (selectedPerks.contains(it)) Color.Gray else Color.Transparent
                                )
                                .padding(2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${profession.name}: ${it.name}",
                                modifier = Modifier.weight(1f),
                            )
                            Text(
                                it.description
                            )
                        }
                    }
                }
            }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun SummaryComponentLvUp() {
    Column {
        Text("Klasa: " + chosenProfesion.name)
        Text("Punkty zdrowia: $hpLvUp")
        Text("Atut: $perkLvUp")
        Text(attributeLvUp)
    }
}
