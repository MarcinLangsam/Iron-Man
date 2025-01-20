package com.example.ironman

class Modifier(
    val name: String,
    val effect: () -> Int,
    val AP: Int,
    val type: String,
    val sprite: Int,
    val spriteHold: Int,
    val descriptionFight: String,
    val descriptionSkill: String,
    var number: Int,
    var isActive: Boolean
)

val modifiers = mutableMapOf(
    //"Infuzja Ataku" to Modifier("Infuzja Atak",{0}, 3,"switchToAtack",R.drawable.modifier_infuzja_ataku,R.drawable.modifier_infuzja_ataku_fulll,"Wymusza na karcie zadanie obrażeń","Wymusza zadanie obrażeń jako końcowy efekt",1,true),
    "Infuzja Zdrowia" to Modifier("Infuzja Zdrowia",{0},3, "switchToHeal" ,R.drawable.modifier_infuzja_zdrowia,R.drawable.modifier_infuzja_zdrowia_fulll,"Wymusza na karcie uzdrowienie | KOSZT: 3","Wymusza uzdrowienie jako końcowy efekt",1,true),
    "Nasycenie" to Modifier("Nasycenie",{ player.INT }, 2,"damage",R.drawable.modifier_nasycenie,R.drawable.modifier_nasycenie_fulll,"Dodaje wartośc ${player.INT} do karty | KOSZT: 2","Dodaje wartośc INT bohatera do karty",1,true),
    "Precyzja" to Modifier("Precyzja",{6}, 3,"damage",R.drawable.modifier_precyzja,R.drawable.modifier_precyzja_fulll,"Dodaje 6 punktów obrażeń do karty","Dodaje 6 punktów obrażeń do karty | KOSZT: 3",1,true),
    "Ulga" to Modifier("Ulga",{1}, 0,"reduction",R.drawable.modifier_ulga,R.drawable.modifier_ulga_fulll,"Obniża koszt AP o 1","Obniża ostateczny koszt modyfikatorów o 1 AP | KOSZT: 0",1,true),
    "Wzrost" to Modifier("Wzrost",{3}, 1,"damage",R.drawable.modifier_wzrost,R.drawable.modifier_wzrost_full,"Dodaje 3 punkty obrażeń do karty","Dodaje 3 punkty obrażeń do karty | KOSZT: 1",1,true),
)