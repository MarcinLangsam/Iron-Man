package com.example.ironman

import kotlinx.serialization.Serializable

@Serializable
data class Card (
    val name: String,
    val effect: () -> Int,
    val AP_required: Int,
    val type: String,
    val modifiersSlots: Int,
    val sprite: Int,
    val spriteHold: Int,
    val descriptionFight: String,
    val descriptionSkill: String,
    var number: Int,
    var isActive: Boolean
)

val cards = mutableMapOf<String,Card>(
    "Zamach" to Card("Zamach", {player.damage},1,"attack",1,R.drawable.card_zamach,R.drawable.card_zamach_full,"Zadaje ${player.damage}","Zadaje obrażenia broni",1,true),
    "Rozpłatanie" to Card("Rozpłatanie", {player.STR*2+player.damage},2,"attack",1,R.drawable.card_rozplatanie,R.drawable.card_rozplatanie_full,"Zadaje ${player.STR*2+player.damage}","Zadaje obrażenia broni+STR*2",1,true),
    "Grzmot" to Card("Grzmot", {player.damage + currentEnemy.HP.value/4},3,"attack",1,R.drawable.card_grzmot,R.drawable.card_grzmot_full,"Zadaje ${player.damage + currentEnemy.HP.value/4}","Zadaje obrażenia broni+1/4 zdrowia przeciwnika",1,true),
    "Trafienie Krytyczne" to Card("Trafienie Krytyczne", {player.STR*3},2,"attack",1,R.drawable.card_trafienie_krytyczne,R.drawable.card_trafienie_krytyczne_full,"Zadaje ${player.STR*3}","Zadaje STR*3 obrażeń",1,true),
    "Leczenie" to Card("Leczenie", {player.INT*2},3,"heal",1,R.drawable.card_leczenie,R.drawable.card_leczenie_full,"Leczy ${player.INT*2}","Leczy INT*2 zdrowia",1,true),
    "Kula Ognia" to Card("Kula Ognia", {player.damage+player.INT},1,"attack",1,R.drawable.card_kula_ognia,R.drawable.card_kula_ognia_full,"Zadaje ${player.damage+player.INT}","Zadaje obrażenia broni+INT",1,true),
    "Deszcz Ognia" to Card("Deszcz Ognia", {player.INT*5},4,"attack",1,R.drawable.card_deszcz_ognia,R.drawable.card_deszcz_ognia_full,"Zadaje ${player.INT*5}","Zadaje INT*5 obrażeń",1,true),
    "Odsapka" to Card("Odsapka", {2},0,"rest",1,R.drawable.card_odsapka,R.drawable.card_odsapka_full,"Odnawia 2 AP","Odnawia 2 punkty akcji",1,true),
    "Głębokie Rany" to Card("Głębokie Rany", { player.damage+ player.DEX*3},2,"attack",1,R.drawable.card_glebokie_rany,R.drawable.card_glebokie_rany_full,"Zadaje ${player.damage+ player.DEX*3}","Zadaje obrażenia broni+DEX*3",1,true),
    "Nakładanie Rąk" to Card("Nakładanie Rąk", {30},0,"heal",1,R.drawable.card_nakladanie_rak,R.drawable.card_nakladanie_rak_full,"Leczy 30","Leczy stałe 30 zdrowia",1,true),
    "Piorun" to Card("Piorun", { player.DEX*4},3,"attack",1,R.drawable.card_piorun,R.drawable.card_piorun_full,"Zadaje ${player.DEX*4}","Zadaje DEX*4 obrażeń",1,true),
    )