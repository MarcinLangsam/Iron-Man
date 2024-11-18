package com.example.ironman

class Profesions(
    var name: String,
    var profesionLv: Int,
    var cards: MutableMap<Int, List<Card?>>,
    var perks: MutableMap<Int, List<Perks?>>,
) {

}

var profesionWarrior = Profesions("Wojownik", 1,
    mutableMapOf(
        1 to listOf(cards["Grzmot"], cards["Rozpłatanie"])
    ),
    mutableMapOf(
        1 to listOf(perks["Zwiększenie kości zdrowia"], perks["Dodatkowy atak na rundę"])
    )
)

var profesionHunter = Profesions("Łowca", 1,
    mutableMapOf(
        1 to listOf(cards["Głębokie Rany"], cards["Odsapka"])
    ),
    mutableMapOf(
        1 to listOf(perks["Karta: Trafienie krytyczne"], perks["Regeneracja AP"])
    ))


var profesionMage = Profesions("Mag", 1,
    mutableMapOf(
        1 to listOf(cards["Leczenie"], cards["Piorun"])
    ),
    mutableMapOf(
        1 to listOf(perks["Zwiększenie AP"], perks["Karta: Deszcz Ognia"])
    ))