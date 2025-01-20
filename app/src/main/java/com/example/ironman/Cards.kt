package com.example.ironman

import kotlinx.serialization.Serializable

@Serializable
data class Card (
    val name: String,
    val effect: () -> Int,
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
    //Warior cards
    "Zamach" to Card("Zamach", {player.damage},"attack",1,R.drawable.card_zamach,R.drawable.card_zamach_full,"Zadaje ${player.damage}","Zadaje obrażenia broni | 1 MOD",1,true),
    "Rozpłatanie" to Card("Rozpłatanie", {player.STR + player.damage},"attack",2,R.drawable.card_rozplatanie,R.drawable.card_rozplatanie_full,"Zadaje ${player.STR+player.damage}","Zadaje obrażenia broni+STR | 2 MOD",1,true),
    "Grzmot" to Card("Grzmot", {player.damage + player.VIT},"attack",3,R.drawable.card_grzmot,R.drawable.card_grzmot_full,"Zadaje ${player.damage + player.VIT}","Zadaje obrażenia broni + VIT | 3 MOD",1,true),
    "Trafienie Krytyczne" to Card("Trafienie Krytyczne", { player.damage + 5},"attack",1,R.drawable.card_trafienie_krytyczne,R.drawable.card_trafienie_krytyczne_full,"Zadaje ${player.damage + 5}","Zadaje obrażenia broni + 5 | 1 MOD",1,true),
    "Oprawca" to Card("Oprawca", {player.STR + player.DEX},"attack",2,R.drawable.card_oprawca,R.drawable.card_oprawca_full,"Zadaje ${player.STR+ player.DEX}","Zadaje STR + DEX obrażeń | 2 MOD",1,true),
    "Szał" to Card("Szał", {3},"trade",0,R.drawable.card_szal,R.drawable.card_szal_full,"Odnawia 3 AP kosztem 3 HP","Odnawia 3 AP kosztem 3 HP | 0 MOD",1,true),

    //Hunter cards
    "Odsapka" to Card("Odsapka", {2},"rest",1,R.drawable.card_odsapka,R.drawable.card_odsapka_full,"Odnawia 2 AP","Odnawia 2 punkty akcji | 1 MODYFIKATORY",1,true),
    "Głębokie Rany" to Card("Głębokie Rany", { player.damage + 5},"attack",1,R.drawable.card_glebokie_rany,R.drawable.card_glebokie_rany_full,"Zadaje ${player.damage + 5}","Zadaje obrażenia broni+5 | 1 MOD",1,true),
    "Bomba" to Card("Bomba", { 30 },"attack",3,R.drawable.card_bomba,R.drawable.card_bomba_full,"Zadaje 30","Zadaje 30 obrażeń | 3 MOD",3,true),
    "Krwiopijca" to Card("Krwiopijca", {player.damage},"lifesteal",0,R.drawable.card_krwiopijca,R.drawable.card_krwiopijca_full,"Zadaje i leczy ${player.damage}","Zadaje obrażenia broni i leczy o tyle samo | 0 MOD",1,true),
    "Trutka" to Card("Trutka", {player.DEX},"attack",0,R.drawable.card_trutka,R.drawable.card_trutka_full,"Zadaje ${player.DEX}","Zadaje DEX | 0 MOD",1,true),

    //Mage cards
    //"Leczenie" to Card("Leczenie", {player.INT*2},"heal",1,R.drawable.card_leczenie,R.drawable.card_leczenie_full,"Leczy ${player.INT*2}","Leczy INT*2 zdrowia",1,true),
    "Magiczny Pocisk" to Card("Magiczny Pocisk", {10},"attack",1,R.drawable.card_magiczny_pocisk,R.drawable.card_magiczny_pocisk_full,"Zadaje 10","Zadaje 10 obrażeń | 1 MOD",1,true),
    "Kula Ognia" to Card("Kula Ognia", {player.INT},"attack",2,R.drawable.card_kula_ognia,R.drawable.card_kula_ognia_full,"Zadaje ${player.INT}","Zadaje INT | 2 MOD",1,true),
    "Deszcz Ognia" to Card("Deszcz Ognia", {player.INT*2},"attack",3,R.drawable.card_deszcz_ognia,R.drawable.card_deszcz_ognia_full,"Zadaje ${player.INT*2}","Zadaje INT*2 obrażeń | 3 MOD",1,true),
    "Gejzer" to Card("Gejzer", {player.INT+ player.damage},"attack",2,R.drawable.card_gejzer,R.drawable.card_gejzer_full,"Zadaje ${player.INT+ player.damage}","Zadaje INT + oobrażenia broni | 2 MOD",1,true),
    "Syfon" to Card("Syfon", {1},"rest",3,R.drawable.card_syfon,R.drawable.card_syfon_full,"Odnaia 1 AP}","Odnawia 1 punkt akcji | 3 MOD",1,true),
    "Piorun" to Card("Piorun", { player.damage + player.INT/2},"attack",4,R.drawable.card_piorun,R.drawable.card_piorun_full,"Zadaje ${player.damage + player.INT/2}","Zadaje obrażenia broni + INT/2 | 4 MOD",1,true),

    )