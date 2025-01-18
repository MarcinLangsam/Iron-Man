package com.example.ironman

class Profesions(
    var name: String,
    var profesionLv: Int,
    var cards: MutableMap<Int, List<Card?>>,
    var modifier: MutableMap<Int, List<Modifier?>>,
    var perks: MutableMap<Int, List<Perks?>>,
    var healthDice: Int,
)

var profesionWarrior = Profesions("Wojownik", 1,
    mutableMapOf(
        1 to listOf(cards["Zamach"]),
        2 to listOf(cards["Szał"]),
        3 to listOf(cards["Rozpłatanie"])
    ),
    mutableMapOf(
        1 to listOf(modifiers["Wzrost"]),
        2 to listOf(),
        3 to listOf(),
    ),
    mutableMapOf(
        1 to listOf(perks["Wojownik: Bonus zdrowia"], perks["Wojownik: Tężyzna"]),
        2 to listOf(),
        3 to listOf(perks["Wojownik: Slot Umiejętności"]),
    ),
    10
)

var profesionHunter = Profesions("Łowca", 1,
    mutableMapOf(
        1 to listOf(cards["Trutka"], cards["Odsapka"]),
        2 to listOf(),
        3 to listOf(cards["Bomba"])
    ),
    mutableMapOf(
        1 to listOf(),
        2 to listOf(modifiers["Precyzja"]),
        3 to listOf(),
    ),

    mutableMapOf(
        1 to listOf(perks["Łowca: Regeneracja AP"], perks["Łowca: Slot Umiejętności"]),
        2 to listOf(),
        3 to listOf(perks["Łowca: Szybkość Geparda"], perks["Łowca: Slot Umiejętności II"]),
    ),
    8)


var profesionMage = Profesions("Mag", 1,
    mutableMapOf(
        1 to listOf(cards["Magiczny Pocisk"]),
        2 to listOf(cards["Kula Ognia"]),
        3 to listOf(),
    ),
    mutableMapOf(
        1 to listOf(modifiers["Infuzja Zdrowia"]),
        2 to listOf(),
        3 to listOf(),
    ),
    mutableMapOf(
        1 to listOf(perks["Mag: Zwiększenie AP"], perks["Mag: MODYFIKATOR: Nasycenie"]),
        2 to listOf(),
        3 to listOf(perks["Mag: Zwiększenie AP II"]),
    ),
    6)