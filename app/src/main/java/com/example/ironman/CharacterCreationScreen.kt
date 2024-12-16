package com.example.ironman

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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

@Composable
fun CharacterCreationScreen(onMainMapScreen: () -> Unit, context: Context){

    var currentCategory by remember { mutableStateOf("Rase") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFACD))
    ) {}

    Column {
        Row(
            modifier = Modifier.fillMaxWidth().height(100.dp).padding(10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(modifier = Modifier.width(150.dp).height(70.dp) ,onClick = { currentCategory = "Rase" }) {
                Text("Rasa")
            }
            Button(modifier = Modifier.width(150.dp).height(70.dp) ,onClick = { currentCategory = "Profesion" }) {
                Text("Klasa")
            }
            Button(modifier = Modifier.width(150.dp).height(70.dp) ,onClick = { currentCategory = "Attributes" }) {
                Text("Atrybuty")
            }
            Button(modifier = Modifier.width(150.dp).height(70.dp) ,onClick = { currentCategory = "Skills" }) {
                Text("Umiejetnosci")
            }
            Button(modifier = Modifier.width(150.dp).height(70.dp) ,onClick = { currentCategory = "Portret" }) {
                Text("Portret")
            }

            if(player.rase != "" && player.profesions.isNotEmpty())
            {
                Button(modifier = Modifier.width(150.dp).height(70.dp) ,onClick = { onMainMapScreen() }) {
                    Text("START")
                }
            }
        }



        Column(modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(150.dp), horizontalAlignment = Alignment.Start) {
            when (currentCategory) {
                "Rase" -> RaseComponent()
                "Profesion" -> ProfesionComponent()
                "Attributes" -> AttributeComponent()
                "Skills" -> SkillsComponent()
                "Portret" -> PortretComponent()
            }

        }
    }



}

fun setPlayerRase(rase: String, rasebonus: () -> Unit){
    player.resetPlayer()
    player.rase = rase
    rasebonus()
}


@Composable
fun RaseComponent(){
    var rase by remember { mutableStateOf(player.rase) }
    var raseDescription by remember { mutableStateOf("Dodatkowy punkt Umiejętności") }

    Row(modifier = Modifier.fillMaxSize()){
        Column {
            Text("Człowiek", modifier = Modifier.padding(5.dp).clickable {
                rase = "Człowiek";
                raseDescription = "Dodatkowy punkt Umiejętności";
                setPlayerRase("Człowiek",{ player.skillPoint += 1 })
            }.background(
                if (rase.contains("Człowiek")) Color.Gray else Color.Transparent)
            )
            Text("Elf", modifier = Modifier.padding(5.dp).clickable {
                rase = "Elf";
                raseDescription = "Bonus do Zręczności +2" ;
                setPlayerRase("Elf",{ player.DEX += 2 })
            }.background(
                if (rase.contains("Elf")) Color.Gray else Color.Transparent)
            )
            Text("Krasnolud", modifier = Modifier.padding(5.dp).clickable {
                rase = "Krasnolud";
                raseDescription = "Bonus do Kondycji +2" ;
                setPlayerRase("Krsnolud",{ player.VIT += 2 })
            }.background(
                if (rase.contains("Krasnolud")) Color.Gray else Color.Transparent)
            )
            Text("Ork", modifier = Modifier.padding(5.dp).clickable {
                rase = "Ork";
                raseDescription = "Bonus do Siły +2" ;
                setPlayerRase("Ork",{ player.STR += 2 })
            }.background(
                if (rase.contains("Ork")) Color.Gray else Color.Transparent)
            )
            Text("Smoczędziecię", modifier = Modifier.padding(5.dp).clickable {
                rase = "Smoczędziecię";
                raseDescription = "Bonus do Kondycji +1\nBonus do Inteligencji +1" ;
                setPlayerRase("Smoczędziecię",{ player.VIT += 1; player.INT += 1 })
            }.background(
                if (rase.contains("Smoczędziecię")) Color.Gray else Color.Transparent)
            )
            Text("Boski", modifier = Modifier.padding(5.dp).clickable {
                rase = "Boski";
                raseDescription = "Bonus do Inteligencji +2" ;
                setPlayerRase("Boski",{ player.INT += 2 })
            }.background(
                if (rase.contains("Boski")) Color.Gray else Color.Transparent)
            )
        }

        Text(text= raseDescription)
    }
}

fun setPlayerProfesion(profesions: Profesions){
    player.profesions.clear()
    player.profesions.add(profesions)
}
@Composable
fun ProfesionComponent(){
    var profesion by remember { mutableStateOf("") }
    var profesionDescription by remember { mutableStateOf("") }

    Row(modifier = Modifier.fillMaxSize()) {
        Column {
            Text("Wojownik", modifier = Modifier.padding(5.dp).clickable {
                profesion = "Warrior";
                profesionDescription = "Kostka zdrowia d10";
                player.healthDice = 10;
                setPlayerProfesion(profesionWarrior)
            }.background(
                if (profesion.contains("Warrior")) Color.Gray else Color.Transparent)
            )
            Text("Łowca", modifier = Modifier.padding(5.dp).clickable {
                profesion = "Hunter";
                profesionDescription = "Kostka zdrowia d8";
                player.healthDice = 8;
                setPlayerProfesion(profesionHunter)
            }.background(
                if (profesion.contains("Hunter")) Color.Gray else Color.Transparent)
            )
            Text("Mag", modifier = Modifier.padding(5.dp).clickable {
                profesion = "Mage";
                profesionDescription = "Kostka zdrowia d6";
                player.healthDice = 6;
                setPlayerProfesion(profesionMage)
            }.background(
                if (profesion.contains("Mage")) Color.Gray else Color.Transparent)
            )

            Text(text= profesionDescription)
        }


    }
}



@Composable
fun AttributeComponent() {
    var attributesPoits by remember { mutableStateOf(5) }
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
            Button(onClick = { if(updateAttribute({ pSTR.value = it }, pSTR, +1)){ player.STR += 1} }, modifier = Modifier.width(50.dp).height(35.dp)) {
                Text("+")
            }
            Button(onClick = { if(updateAttribute({ pSTR.value = it }, pSTR, -1)){ player.STR -= 1} }, modifier = Modifier.width(50.dp).height(35.dp)) {
                Text("-")
            }
        }
        Row {
            Text("Kondycja ${pVIT.value}")
            Button(onClick = { if(updateAttribute({ pVIT.value = it }, pVIT, +1)){ player.VIT += 1} }, modifier = Modifier.width(50.dp).height(35.dp)) {
                Text("+")
            }
            Button(onClick = { if(updateAttribute({ pVIT.value = it }, pVIT, -1)){ player.VIT -= 1} }, modifier = Modifier.width(50.dp).height(35.dp)) {
                Text("-")
            }
        }
        Row {
            Text("Zręczność ${pDEX.value}")
            Button(onClick = { if(updateAttribute({ pDEX.value = it }, pDEX, +1)){ player.DEX += 1} }, modifier = Modifier.width(50.dp).height(35.dp)) {
                Text("+")
            }
            Button(onClick = { if(updateAttribute({ pDEX.value = it }, pDEX, -1)){ player.DEX -= 1} }, modifier = Modifier.width(50.dp).height(35.dp)) {
                Text("-")
            }
        }
        Row {
            Text("Inteligencja ${pINT.value}")
            Button(onClick = { if(updateAttribute({ pINT.value = it }, pINT, +1)){ player.INT += 1} }, modifier = Modifier.width(50.dp).height(35.dp)) {
                Text("+")
            }
            Button(onClick = { if(updateAttribute({ pINT.value = it }, pINT, -1)){ player.INT -= 1} }, modifier = Modifier.width(50.dp).height(35.dp)) {
                Text("-")
            }
        }
    }
}


@Composable
fun SkillsComponent(){
    var perkPoints by remember { mutableStateOf(1) }

    val selectedPerks = remember { mutableSetOf<Perks>() }

    Column(modifier = Modifier.fillMaxSize().padding(2.dp)) {
        Text("Punkty atutów: $perkPoints")

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
                                        it.effect.invoke()
                                        perkPoints++
                                    } else if (perkPoints > 0) {
                                        selectedPerks.add(it)
                                        it.undoEffect.invoke()
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
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun PortretComponent(){
    var currentPortrait by remember { mutableStateOf(1) }
    val maxPortraits = 3

    Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center) {
        Button(
            onClick = {
                currentPortrait = if (currentPortrait > 1) currentPortrait - 1 else maxPortraits
                player.portrait = getDrawableResourceForPortrait(currentPortrait)
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("←")
        }

        Image(
            painter = painterResource(id = getDrawableResourceForPortrait(currentPortrait)),
            contentDescription = "Portret gracza",
            modifier = Modifier.size(150.dp)
        )

        Button(
            onClick = {
                currentPortrait = if (currentPortrait < maxPortraits) currentPortrait + 1 else 1
                player.portrait = getDrawableResourceForPortrait(currentPortrait)
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("→")
        }
    }
}

fun getDrawableResourceForPortrait(portraitIndex: Int): Int {
    return when (portraitIndex) {
        1 -> R.drawable.portrait1
        2 -> R.drawable.portrait2
        3 -> R.drawable.portrait3
        else -> R.drawable.portrait1
    }
}