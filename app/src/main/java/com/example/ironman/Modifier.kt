package com.example.ironman

class Modifier(
    val name: String,
    val effect: () -> Int,
    val sprite: Int,
    val spriteHold: Int,
    val descriptionFight: String,
    val descriptionSkill: String,
    var number: Int,
    var isActive: Boolean
)

val modifiers = mutableMapOf(
    "Wzmocnienie" to Modifier("Wzmocnienie",{10},R.drawable.card_zamach,R.drawable.card_zamach_full,"Dodaje 10 punktów obrażeń do karty","Dodaje 10 punktów obrażeń do karty",1,true)
)