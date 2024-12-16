package com.example.ironman

class Perks(
    var name: String,
    var description: String,
    var effect: () -> Unit,
    var undoEffect: () -> Unit,

    ) {
}

var perks = mutableMapOf<String, Perks>(
    "Zwiększenie kości zdrowia" to Perks("Zwiększenie kości zdrowia", "Atut pernamentnie zwiększający kostkę zdroiwa bochatera o 1", { player.healthDice += 1 },{ player.healthDice -=1 }),
    "Dodatkowy atak na rundę" to Perks("Dodatkowy atak na rundę", "Atut ten pozwala na wykonanie dodatkowego ataku podczas rundy bochatera",{ player.cardsSlots += 1 },{ player.cardsSlots -= 1}) ,
    "Karta: Trafienie krytyczne" to Perks("Karta: Trafienie krytyczne", "Atut ten dodaje specjalną kartę Trafienie krytyczne do aresenału bohatera  Zadaje: STR*3   Koszt AP: 2",{ cards["Trafienie krytyczne"]?.let { player.addCard(it) }}, { cards["Trafienie Krytyczne"]?.let { player.removeCard(it)}}),
    "Karta: Deszcz Ognia" to Perks("Karta: Deszcz Ognia", "Atut ten dodaje specjalną kartę Deszcz Ognia do aresenału bohatera   Zadaje: INT*5   Koszt AP: 4", { cards["Deszcz Ognia"]?.let { player.addCard(it) } },{ cards["Deszcz Ognia"]?.let { player.removeCard(it)}}) ,
    "Zwiększenie AP" to Perks("Zwiększenie AP", "Atut pernamentnie zwiększa AP o 2",{ player.AP.value += 2; player.MAX_AP.value += 2 },{player.AP.value -= 2; player.MAX_AP.value -= 2}) ,
    "Regeneracja AP" to Perks("Regeneracja AP", "Atut pernamentnie zwiększa regeneracje AP o 1",{ player.AP_recovery += 1 }, { player.AP_recovery -= 1 }) ,
)