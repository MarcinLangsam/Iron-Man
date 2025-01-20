package com.example.ironman

import android.content.Context
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

@Composable
fun CharacterCreationScreen(onMainMapScreen: () -> Unit, context: Context) {
    var currentCategory by remember { mutableStateOf("Rase") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray) // Tło w odcieniach szarości
    ) {
        Column {
            // Górna sekcja z przyciskami
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color(0xFF333333)), // Ciemniejszy pasek tła dla przycisków
                contentAlignment = Alignment.Center
            ) {
                LazyRow(
                    modifier = Modifier.padding(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp) // Odstępy między przyciskami
                ) {
                    val buttons = listOf(
                        "Rasa" to { currentCategory = "Rase" },
                        "Klasa" to { currentCategory = "Profesion" },
                        "Atrybuty" to { currentCategory = "Attributes" },
                        "Umiejętności" to { currentCategory = "Skills" },
                        "Imię" to { currentCategory = "Name" },
                        "START" to {
                            player.MAX_HP.value += (Random.nextInt(1, player.profesions.first().healthDice) + player.healthDiceBonus)
                            player.updateStats()
                            generatedMap.generateMap()
                            onMainMapScreen()
                        }
                    )

                    items(buttons.size) { index -> // Iterujemy po indeksach listy
                        val (label, onClick) = buttons[index] // Rozpakowujemy element listy na label i onClick
                        Button(
                            modifier = Modifier
                                .width(150.dp)
                                .height(70.dp)
                                .background(Color(0xFF444444), shape = RectangleShape) // Tło i brak zaokrąglenia
                                .border(1.dp, Color.White, shape = RectangleShape), // Cienka ramka w kolorze białym
                            onClick = onClick,
                            shape = RectangleShape // Ustalamy, że przycisk ma prostokątne kształty
                        ) {
                            Text(
                                text = label,
                                color = Color.White // Kolor tekstu
                            )
                        }

                    }
                }
            }

            // Dodaj wskaźnik przewijania
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .background(Brush.horizontalGradient(listOf(Color.Transparent, Color.White, Color.Transparent)))
            )

            // Sekcja główna z dynamiczną zawartością
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize()
                    .background(Color(0xFF222222), shape = RoundedCornerShape(8.dp)), // Zaokrąglone tło dla zawartości
                contentAlignment = Alignment.TopStart
            ) {
                when (currentCategory) {
                    "Rase" -> RaseComponent()
                    "Profesion" -> ProfesionComponent()
                    "Attributes" -> AttributeComponent()
                    "Skills" -> SkillsComponent()
                    "Name" -> NameComponent()
                }
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
fun RaseComponent() {
    var rase by remember { mutableStateOf(player.rase) }
    var raseDescription by remember { mutableStateOf("Dodatkowy punkt Umiejętności") }

    Row(modifier = Modifier.fillMaxSize().padding(5.dp)) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(end = 16.dp) // Odstęp między kolumną a opisem
        ) {
            Text(
                "Człowiek",
                color = Color.White,
                modifier = Modifier
                    .padding(5.dp)
                    .clickable {
                        rase = "Człowiek"
                        raseDescription = "Ludzie swą liczebnością zdecydowanie dominują nad innymi rasami, zamieszkują niemalżę każdy zakątek świata. Są bystrzy i pomysłowi, szybko uczą się nowych rzeczy i dążą do ciągłego rozwoju.\n\nBonus:\nDodatkowy punkt talentów"
                        setPlayerRase("Człowiek") { player.skillPoint += 1 }
                    }
                    .background(
                        if (rase.contains("Człowiek")) Color(0x80FFD700) else Color.Transparent // Złoty przezroczysty kolor
                    )

            )
            Text(
                "Elf",
                color = Color.White,
                modifier = Modifier
                    .padding(5.dp)
                    .clickable {
                        rase = "Elf"
                        raseDescription = "Elfy głównie zamieszkują lasy, budując ozdobne i schludne domostwa. Cenią sobie sztukę, piękno oraz jedność z naturą. Mają predyspozycje do bycia wyśmienitymi łowcami\n\nBonus:\n+2 Zręczności"
                        setPlayerRase("Elf") { player.DEX += 2 }
                    }
                    .background(
                        if (rase.contains("Elf")) Color(0x80FFD700) else Color.Transparent
                    )
            )
            Text(
                "Krasnolud",
                color = Color.White,
                modifier = Modifier
                    .padding(5.dp)
                    .clickable {
                        rase = "Krasnolud"
                        raseDescription = "Krasnoludy wolą żyć w odosobnieniu. Zbudowali sowje własne podziemne imperium gdzie nie mają zbyt wielu gości. Każdy z nich jednak jest doskonałym inżynierem bądź ma nosa do interesów. Dodatkowo odznaczają się bezkonkurencyjną witalnością.\n\nBonus:\n+2 Kondycji"
                        setPlayerRase("Krasnolud") { player.VIT += 2 }
                    }
                    .background(
                        if (rase.contains("Krasnolud")) Color(0x80FFD700) else Color.Transparent
                    )
            )
            Text(
                "Ork",
                color = Color.White,
                modifier = Modifier
                    .padding(5.dp)
                    .clickable {
                        rase = "Ork"
                        raseDescription = "Orkowie to silna rasa ceniąca sobie odwagę, honor i męstwo w walce. Choć dawniej toczyli z ludźmi zaciekłe boje, teraz są w stanie się tolerować. Najsilniejszymi wojownikami są zdecydowanie orkowie\n\nBonus:\n+2 Siła"
                        setPlayerRase("Ork") { player.STR += 2 }
                    }
                    .background(
                        if (rase.contains("Ork")) Color(0x80FFD700) else Color.Transparent
                    )
            )
            Text(
                "Smoczędziecię",
                color = Color.White,
                modifier = Modifier
                    .padding(5.dp)
                    .clickable {
                        rase = "Smoczędziecię"
                        raseDescription = "Smoczedziecięta są wysłannikami smoków, dbają o równowagę na świecie, wiele razy pomogli przezwyciężyć zagrożenia i zażegnać konflity na świecie. Są postawnymi humanoidalnymi storzeniami, porośniętymi mieniącymi się łuskami\n\nBonus:\nSpecjalna karta ODDECH SMOKA"
                        setPlayerRase("Smoczędziecię") { player.VIT += 1; player.INT += 1 }
                    }
                    .background(
                        if (rase.contains("Smoczędziecię")) Color(0x80FFD700) else Color.Transparent
                    )
            )
            Text(
                "Boski",
                color = Color.White,
                modifier = Modifier
                    .padding(5.dp)
                    .clickable {
                        rase = "Boski"
                        raseDescription = "Nie do końca wiadomo skąd wzieli się boscy. Wyglądają jak ludzie lecz mają nienaturalnie bladą skórę, szkarłatne oczy a nieliczni, którzy ich widzieli twierdzą, ze ich włosy płoną. Odznaczają się nienaturalną inteligencją.\n\nBonus:\n+2 Inteligencji"
                        setPlayerRase("Boski") { player.INT += 2 }
                    }
                    .background(
                        if (rase.contains("Boski")) Color(0x80FFD700) else Color.Transparent
                    )
            )
        }

        Spacer(modifier = Modifier.width(16.dp)) // Odstęp pomiędzy kolumną wyboru rasy a opisem

        Text(
            text = raseDescription,
            color = Color.White, // Biała czcionka
            modifier = Modifier
                .padding(5.dp)
                .fillMaxHeight()
        )
    }
}


fun setPlayerProfesion(profesions: Profesions){
    player.profesions.clear()
    player.mainDeck.clear()
    player.mainDeckModifiers.clear()
    player.HPbonus = 0
    player.levelUpPlayer(profesions)
}
@Composable
fun ProfesionComponent() {
    var profesion by remember { mutableStateOf("") }
    var profesionDescription by remember { mutableStateOf("") }

    Row(modifier = Modifier.fillMaxSize().padding(5.dp)) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(end = 16.dp) // Odstęp między kolumną a opisem
        ) {
            Text(
                "Wojownik",
                color = Color.White,
                modifier = Modifier
                    .padding(5.dp)
                    .clickable {
                        profesion = "Warrior"
                        profesionDescription = "WOJOWNIK: Odznacza się wyśmienitymi umiejętnościami walki z zwarciu. Polega na sile i wytrzymałej zbroi, jednak szybko się męczy i potrzebuje niedużego wytchienia między walkami.\n\nKostka zdrowia d10"
                        setPlayerProfesion(profesionWarrior)
                    }
                    .background(
                        if (profesion.contains("Warrior")) Color(0x80FFD700) else Color.Transparent // Złoty przezroczysty kolor
                    )
            )
            Text(
                "Łowca",
                color = Color.White,
                modifier = Modifier
                    .padding(5.dp)
                    .clickable {
                        profesion = "Hunter"
                        profesionDescription = "ŁOWCA: Łowcy to przebiegli adwersarze, polegają na zręczności i szybkiej regeneracji. Często używają bomb lub trucizn.\n\nKostka zdrowia d8"
                        setPlayerProfesion(profesionHunter)
                    }
                    .background(
                        if (profesion.contains("Hunter")) Color(0x80FFD700) else Color.Transparent
                    )
            )
            Text(
                "Mag",
                color = Color.White,
                modifier = Modifier
                    .padding(5.dp)
                    .clickable {
                        profesion = "Mage"
                        profesionDescription = "MAG: Magowie nadrabiają słabą wytrzymałość potężnymi zaklęciami. Potrafią wyrządzić nie porównywalną szkodę choć lepiej aby trzymali się z tyłu. Szkolą się w powiększaniu zasobów energi ale nie ich regeneracji.\n\nKostka zdrowia d6"
                        setPlayerProfesion(profesionMage)
                    }
                    .background(
                        if (profesion.contains("Mage")) Color(0x80FFD700) else Color.Transparent
                    )
            )
        }

        Spacer(modifier = Modifier.width(16.dp)) // Odstęp pomiędzy kolumną wyboru profesji a opisem

        Text(
            text = profesionDescription,
            color = Color.White, // Biała czcionka
            modifier = Modifier
                .padding(5.dp)
                .fillMaxHeight()
        )
    }
}




@Composable
fun AttributeComponent() {
    var attributesPoits by remember { mutableStateOf(5) }
    var pSTR = remember { mutableStateOf(player.STR) }
    var pVIT = remember { mutableStateOf(player.VIT) }
    var pDEX = remember { mutableStateOf(player.DEX) }
    var pINT = remember { mutableStateOf(player.INT) }
    var attributeDescription by remember { mutableStateOf("") }

    fun updateAttribute(attribute: (Int) -> Unit, currentValue: MutableState<Int>, value: Int): Boolean {
        if (value == -1) {
            if (currentValue.value > 1) {
                attribute(currentValue.value + value)
                attributesPoits += 1
                return true
            }
        } else {
            if (attributesPoits > 0) {
                attribute(currentValue.value + value)
                attributesPoits -= 1
                return true
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
                    },
                    modifier = Modifier
                        .width(60.dp)
                        .height(45.dp)
                        .background(Color(0xFF444444))
                        .border(1.dp, Color.White)
                        .padding(start = 3.dp)
                ) {
                    Text("+", color = Color.White, fontSize = 20.sp)
                }
                // Przycisk -
                Button(
                    onClick = {
                        if (updateAttribute({ pSTR.value = it }, pSTR, -1)) {
                            player.STR -= 1
                        }
                        attributeDescription =
                            "Jeden punkt siły przekłada się na jeden punkt obrażeń ataku bronią. Ulepsza też karty oparte na sile."
                    },
                    modifier = Modifier
                        .width(60.dp)
                        .height(45.dp)
                        .background(Color(0xFF444444))
                        .border(1.dp, Color.White)
                        .padding(start = 3.dp)
                ) {
                    Text("-", color = Color.White, fontSize = 20.sp)
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
                    },
                    modifier = Modifier
                        .width(60.dp)
                        .height(45.dp)
                        .background(Color(0xFF444444))
                        .border(1.dp, Color.White)
                        .padding(start = 3.dp)
                ) {
                    Text("+", color = Color.White, fontSize = 20.sp)
                }
                Button(
                    onClick = {
                        if (updateAttribute({ pVIT.value = it }, pVIT, -1)) {
                            player.VIT -= 1
                        }
                        attributeDescription =
                            "Jeden punkt kodycji przekłada się na jeden punkt maksymalnego zdrowia. Każde 10 punktów zwiększa bonus zdrowia na poziom o jeden."
                    },
                    modifier = Modifier
                        .width(60.dp)
                        .height(45.dp)
                        .background(Color(0xFF444444))
                        .border(1.dp, Color.White)
                        .padding(start = 3.dp)
                ) {
                    Text("-", color = Color.White, fontSize = 20.sp)
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
                    },
                    modifier = Modifier
                        .width(60.dp)
                        .height(45.dp)
                        .background(Color(0xFF444444))
                        .border(1.dp, Color.White)
                        .padding(start = 3.dp)
                ) {
                    Text("+", color = Color.White, fontSize = 20.sp)
                }
                Button(
                    onClick = {
                        if (updateAttribute({ pDEX.value = it }, pDEX, -1)) {
                            player.DEX -= 1
                        }
                        attributeDescription =
                            "Dwa punkty zręczności przekładają się na jeden Punkt Akcji. Punkty akcji są wykorzystywane do kart oraz modyfikatorów"
                    },
                    modifier = Modifier
                        .width(60.dp)
                        .height(45.dp)
                        .background(Color(0xFF444444))
                        .border(1.dp, Color.White)
                        .padding(start = 3.dp)
                ) {
                    Text("-", color = Color.White, fontSize = 20.sp)
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
                    },
                    modifier = Modifier
                        .width(60.dp)
                        .height(45.dp)
                        .background(Color(0xFF444444))
                        .border(1.dp, Color.White)
                        .padding(start = 3.dp)
                ) {
                    Text("+", color = Color.White, fontSize = 20.sp)
                }
                Button(
                    onClick = {
                        if (updateAttribute({ pINT.value = it }, pINT, -1)) {
                            player.INT -= 1
                        }
                        attributeDescription = "Inteligencja ulepsza karty oparte na inteligencji"
                    },
                    modifier = Modifier
                        .width(60.dp)
                        .height(45.dp)
                        .background(Color(0xFF444444))
                        .border(1.dp, Color.White)
                        .padding(start = 3.dp)
                ) {
                    Text("-", color = Color.White, fontSize = 20.sp)
                }
            }
        }

        Spacer(modifier = Modifier.width(16.dp)) // Odstęp między kolumną a opisem

        // Opis
        Text(attributeDescription, color = Color.White)
    }
}



@Composable
fun SkillsComponent() {
    var perkPoints by remember { mutableStateOf(player.skillPoint) }

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
fun NameComponent() {
    var inputName by remember { mutableStateOf("") }
    val maxLength = 15

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Wpisz imię bohatera:", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = inputName,
            onValueChange = {
                if (it.length <= maxLength) {
                    inputName = it
                }
            },
            label = { Text("Imię bohatera") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                player.name = inputName
            },
            enabled = inputName.isNotBlank(),
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Zatwierdź")
        }
    }
}
