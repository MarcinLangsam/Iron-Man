package com.example.ironman

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

lateinit var chosenProfesion: Profesions
var hpLvUp = 0
var perkLvUp = "brak"
var attributeLvUp = "brak"

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
            .background(Color(0xFF222222)) // Ciemne tło dla całego ekranu
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Pierwsza kolumna
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "NOWY POZIOM",
                    color = Color(0xFFFFD700), // Złoty kolor
                    fontSize = 24.sp, // Duży rozmiar czcionki
                    fontWeight = FontWeight.Bold, // Gruba czcionka
                    textAlign = TextAlign.Center, // Wyśrodkowanie tekstu
                    modifier = Modifier
                        .padding(8.dp) // Opcjonalne odstępy
                        .shadow(4.dp, shape = RectangleShape), // Cień dla ozdoby
                    style = TextStyle(
                        fontFamily = FontFamily.Serif, // Ozdobna czcionka (np. Serif)
                        letterSpacing = 2.sp // Rozstrzelenie liter
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = professionText,
                    color = Color.White, // Tekst w kolorze białym
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Druga kolumna
            Column(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxHeight()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .background(Color(0xFF333333)) // Tło dla ekranu opcji
                        .padding(5.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    when (currentScreen) {
                        0 -> ProfesionComponentLvUp()
                        1 -> AttributeComponentLvUp()
                        2 -> SkillsComponentLvUp()
                        3 -> SummaryComponentLvUp()
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    when {
                        currentScreen == 1 -> {
                            Button(
                                onClick = {
                                    com.example.ironman.player.levelUpPlayer(chosenProfesion)
                                    nextScreen()
                                },
                                modifier = Modifier
                                    .width(130.dp)
                                    .height(50.dp),
                                shape = RectangleShape // Proste krawędzie
                            ) {
                                Text("DALEJ")
                            }
                        }
                        currentScreen < 3 -> {
                            Button(
                                onClick = { nextScreen() },
                                modifier = Modifier
                                    .width(130.dp)
                                    .height(50.dp),
                                shape = RectangleShape
                            ) {
                                Text("DALEJ")
                            }
                        }
                        else -> {
                            Button(
                                onClick = { player.EXP.value = 0; onMap() },
                                modifier = Modifier
                                    .width(130.dp)
                                    .height(50.dp),
                                shape = RectangleShape
                            ) {
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
fun ProfesionComponentLvUp() {
    var profesion by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(Color(0xFF333333)).padding(5.dp) // Tło dla kolumny
        ) {
            listOf(
                "Wojownik" to profesionWarrior,
                "Łowca" to profesionHunter,
                "Mag" to profesionMage
            ).forEach { (name, profesionValue) ->
                Text(
                    text = name,
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                        .fillMaxWidth()
                        .clickable {
                            chosenProfesion = profesionValue
                            profesion = name
                        }
                        .background(
                            if (profesion == name) Color.Gray else Color.Transparent,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp),
                    color = if (profesion == name) Color.White else Color.LightGray, // Zmiana koloru zaznaczenia
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
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
    var attributeDescription by remember { mutableStateOf("") }

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

    Row(modifier = Modifier.fillMaxSize().padding(5.dp)) {
        Column(horizontalAlignment = Alignment.Start) {
            Text("Punkty do wydania: $attributesPoits", color = Color.White)

            // Siła
            Row(modifier = Modifier.padding(3.dp)) {
                Text("Siła ${pSTR.value}", color = Color.White)
                // Przycisk +
                Button(
                    onClick = {
                        if (updateAttribute({ pSTR.value = it }, pSTR, +1)) {
                            player.STR += 1
                        }
                        attributeDescription =
                            "Jeden punkt siły przekłada się na jeden punkt obrażeń ataku bronią. Ulepsza też karty oparte na sile."
                        attributeLvUp = "+1 Siły"
                    },
                    modifier = Modifier
                        .width(60.dp)
                        .height(45.dp)
                        .background(Color(0xFF444444))
                        .border(1.dp, Color.White)
                ) {
                    Text("+", color = Color.White, fontSize = 20.sp)
                }
            }

            // Kondycja
            Row(modifier = Modifier.padding(3.dp)) {
                Text("Kondycja ${pVIT.value}", color = Color.White)
                Button(
                    onClick = {
                        if (updateAttribute({ pVIT.value = it }, pVIT, +1)) {
                            player.VIT += 1
                        }
                        attributeDescription =
                            "Jeden punkt kodycji przekłada się na jeden punkt maksymalnego zdrowia. Każde 10 punktów zwiększa bonus zdrowia na poziom o jeden."
                        attributeLvUp = "+1 Kondycji"
                    },
                    modifier = Modifier
                        .width(60.dp)
                        .height(45.dp)
                        .background(Color(0xFF444444))
                        .border(1.dp, Color.White)
                ) {
                    Text("+", color = Color.White, fontSize = 20.sp)
                }

            }

            // Zręczność
            Row(modifier = Modifier.padding(3.dp)) {
                Text("Zręczność ${pDEX.value}", color = Color.White)
                Button(
                    onClick = {
                        if (updateAttribute({ pDEX.value = it }, pDEX, +1)) {
                            player.DEX += 1
                        }
                        attributeDescription =
                            "Dwa punkty zręczności przekładają się na jeden Punkt Akcji. Punkty akcji są wykorzystywane do kart oraz modyfikatorów"
                        attributeLvUp = "+1 Zręczności"
                    },
                    modifier = Modifier
                        .width(60.dp)
                        .height(45.dp)
                        .background(Color(0xFF444444))
                        .border(1.dp, Color.White)
                ) {
                    Text("+", color = Color.White, fontSize = 20.sp)
                }
            }

            // Inteligencja
            Row(modifier = Modifier.padding(3.dp)) {
                Text("Inteligencja ${pINT.value}", color = Color.White)
                Button(
                    onClick = {
                        if (updateAttribute({ pINT.value = it }, pINT, +1)) {
                            player.INT += 1
                        }
                        attributeDescription = "Inteligencja ulepsza karty oparte na inteligencji"
                        attributeLvUp = "+1 Inteligencji"
                    },
                    modifier = Modifier
                        .width(60.dp)
                        .height(45.dp)
                        .background(Color(0xFF444444))
                        .border(1.dp, Color.White)
                ) {
                    Text("+", color = Color.White, fontSize = 20.sp)
                }

            }
        }

        Spacer(modifier = Modifier.width(16.dp)) // Odstęp między kolumną a opisem

        Text(attributeDescription, color = Color.White)
    }
}


@Composable
fun SkillsComponentLvUp(){
    var perkPoints by remember { mutableStateOf(1) }
    val selectedPerks = remember { mutableSetOf<Perks>() }

    Column(modifier = Modifier.fillMaxSize().padding(5.dp)) {
        Text("Punkty atutów: $perkPoints", color = Color.White)

        if (player.profesions.isEmpty()) {
            Text(
                text = "Najpierw wybierz profesję, aby zobaczyć dostępne atuty.",
                color = Color.Red
            )
        } else {
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
                                        it.undoEffect.invoke()
                                        perkPoints++
                                    } else if (perkPoints > 0) {
                                        selectedPerks.add(it)
                                        it.effect.invoke()
                                        perkPoints--
                                    }
                                }
                                .background(
                                    if (selectedPerks.contains(it)) Color(0x80FFD700) else Color.Transparent
                                )
                                .border(1.dp, Color.White)
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${profession.name}: ${it.name}",
                                modifier = Modifier.weight(1f),
                                color = Color.White
                            )
                            Text(
                                text = it.description,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SummaryComponentLvUp() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFF333333), shape = RoundedCornerShape(12.dp))
            .border(2.dp, Color(0xFFFFD700), shape = RoundedCornerShape(12.dp)) // Złota ramka
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(3.dp) // Odstępy między elementami
    ) {
        Text(
            text = "Podsumowanie Poziomu",
            style = MaterialTheme.typography.headlineSmall,
            color = Color(0xFFFFD700), // Złoty kolor tekstu
            modifier = Modifier.padding(bottom = 5.dp)
        )

        Divider(color = Color(0xFFFFD700), thickness = 2.dp, modifier = Modifier.padding(bottom = 8.dp)) // Dekoracyjny separator

        Text(
            text = "Awans w profesji: ${chosenProfesion.name}",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Punkty zdrowia na poziom: $hpLvUp",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Atut: $perkLvUp",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp),
            textAlign = TextAlign.Center
        )

        Text(
            text = attributeLvUp,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp),
            textAlign = TextAlign.Center
        )
    }
}

