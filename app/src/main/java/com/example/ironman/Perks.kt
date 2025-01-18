package com.example.ironman

class Perks(
    var name: String,
    var description: String,
    var effect: () -> Unit,
    var undoEffect: () -> Unit,

    ) {
}

var perks = mutableMapOf<String, Perks>(
    //Warior perks
    "Wojownik: Bonus zdrowia" to Perks("Wojownik: Bonus zdrowia", "Zwiększa zdobywane zdrowie co poziom o 1", { player.healthDiceBonus += 1 },{ player.healthDiceBonus -= 1 }),
    "Wojownik: Slot Umiejętności" to Perks("Wojownik: Slot Umiejętności", "Zwiększa ilość przypisywanych kart umiejętności o 1 ",{ player.cardsSlots += 1 },{ player.cardsSlots -= 1}),
    "Wojownik: Slot Umiejętności II" to Perks("Wojownik: Slot Umiejętności II", "Zwiększa ilość przypisywanych kart umiejętności o 1 ",{ player.cardsSlots += 1 },{ player.cardsSlots -= 1}),
    "Wojownik: Tężyzna" to Perks("Wojownik: Tężyzna", "Zwiększa pernamentnie zdrowie o 5 punktów ",{ player.HPbonus += 5; player.HP.value += 5 },{ player.HPbonus -= 5; player.HP.value -= 5 }),
    "Wojownik: Siła Olbrzyma" to Perks("Wojownik: Siła Olbrzyma", "Zwiększa pernamentnie siłę o 2 punkty",{ player.STR += 2 },{ player.STR -= 2}),
    "Wojownik: Slot Umiejętności II" to Perks("Wojownik: Slot Umiejętności II", "Zwiększa ilość przypisywanych kart umiejętności o 1 ",{ player.cardsSlots += 1 },{ player.cardsSlots -= 1}),
    //Hunter perks
    "Łowca: Regeneracja AP" to Perks("Regeneracja AP", "Pernamentnie zwiększa regeneracje AP o 1",{ player.AP_recovery += 1 }, { player.AP_recovery -= 1 }),
    "Łowca: Regeneracja AP II" to Perks("Łowca: Regeneracja AP II", "Pernamentnie zwiększa regeneracje AP o 1",{ player.AP_recovery += 1 }, { player.AP_recovery -= 1 }),
    "Łowca: Wieczne Łowy" to Perks("Łowca: Wieczne Łowy", "Pernamentnie zwiększa AP o 1 oraz regeneracje AP o 2",{ player.AP_recovery += 2; player.AP.value += 1; player.MAX_AP.value += 1 }, { player.AP_recovery -= 2; player.AP.value -= 1; player.MAX_AP.value -= 1 }),
    "Łowca: Szybkość Geparda" to Perks("Łowca: Szybkość Geparda", "Zwiększa pernamentnie zręczność o 2 punkty ",{ player.DEX += 2 },{ player.DEX -= 2}),
    "Łowca: Slot Umiejętności" to Perks("Łowca: Slot Umiejętności", "Zwiększa ilość przypisywanych kart umiejętności o 1 ",{ player.cardsSlots += 1 },{ player.cardsSlots -= 1}),
    "Łowca: Slot Umiejętności II" to Perks("Łowca: Slot Umiejętności II", "Zwiększa ilość przypisywanych kart umiejętności o 1 ",{ player.cardsSlots += 1 },{ player.cardsSlots -= 1}),
    //Mage perks
    "Mag: Mistyczne Wzmocnienie" to Perks("Mag: Mistyczne Wzmocnienie", "Zwiększa wszystkie statystyki o 1",{ player.STR += 1; player.DEX += 1; player.INT += 1; player.VIT += 1 },{ player.STR -= 1; player.DEX -= 1; player.INT -= 1; player.VIT -= 1 }),
    "Mag: Slot Umiejętności" to Perks("Mag: Slot Umiejętności", "Zwiększa ilość przypisywanych kart umiejętności o 1",{ player.cardsSlots += 1 },{ player.cardsSlots -= 1}),
    "Mag: Slot Umiejętności II" to Perks("Mag: Slot Umiejętności II", "Zwiększa ilość przypisywanych kart umiejętności o 1",{ player.cardsSlots += 1 },{ player.cardsSlots -= 1}),
    "Mag: Bystrość Sowy" to Perks("Mag: Bystrość Sowy", "Zwiększa pernamentnie inteligencje o 2 punkty ",{ player.INT += 2 },{ player.INT -= 2}),
    "Mag: KARTA Deszcz Ognia" to Perks("Karta: Deszcz Ognia", "Dodaje specjalną kartę Deszcz Ognia do aresenału bohatera   Zadaje: INT*5   Koszt AP: 4", { cards["Deszcz Ognia"]?.let { player.addCard(it) } },{ cards["Deszcz Ognia"]?.let { player.removeCard(it)}}) ,
    "Mag: Zwiększenie AP" to Perks("Mag: Zwiększenie AP", "Pernamentnie zwiększa AP o 2",{ player.APbonus += 2; player.AP.value += 2 },{ player.APbonus -= 2; player.AP.value -= 2 }),
    "Mag: Zwiększenie AP II" to Perks("Mag: Zwiększenie AP II", "Pernamentnie zwiększa AP o 2",{ player.APbonus += 2; player.AP.value += 2 },{ player.APbonus -= 2; player.AP.value -= 2 }),
    "Mag: Zwiększenie AP III" to Perks("Mag: Zwiększenie AP III", "Pernamentnie zwiększa AP o 2",{ player.APbonus += 2; player.AP.value += 2 },{ player.APbonus -= 2; player.AP.value -= 2 }),
    "Mag: MODYFIKATOR: Nasycenie" to Perks("Mag: MODYFIKATOR: Nasycenie", "Dodaje modyfikator NASYCENIE - dodaje wartośc INT do ataku",{ modifiers["Nasycenie"]?.let { player.addModifier(it) } },{ modifiers["Wzrost"]?.let { player.removeModifier(it) } }),

    )