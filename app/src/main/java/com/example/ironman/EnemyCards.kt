package com.example.ironman

class EnemyCards(
    val name: String,
    val effect: (Enemy) -> Int,
    val stat: String,
    val type: String,
    val multiplayer: Float,
    val target: String
)

var enemyCards = mutableMapOf<String, EnemyCards>(
    "Atak" to EnemyCards("Atak", {enemy -> enemy.STR.value}, "none" ,"attack",1f,"player"),
    "Leczenie" to EnemyCards("Leczenie", {enemy -> enemy.INT.value*3 }, "HP" ,"heal",0.5f,"self"),
    "Walniecie" to EnemyCards("Walniecie", {enemy -> enemy.STR.value*2 }, "none" ,"attack",0.9f,"player"),
)