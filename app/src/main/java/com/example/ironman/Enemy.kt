package com.example.ironman

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

fun addToActionsReady(skill: EnemyCards, enemy: Enemy)
{
    if(skill.stat == "none"){enemy.actionsReady.add(skill)}
    else if (skill.target == "self")
    {
        if (skill.stat == "HP")
        {
            if (enemy.MAX_HP.value * skill.multiplayer >= enemy.HP.value){enemy.actionsReady.add(skill)}
        }
    }
    else if(skill.target == "player")
    {
        if (skill.stat == "HP")
        {
            if (player.MAX_HP.value * skill.multiplayer >= player.HP.value){enemy.actionsReady.add(skill)}
        }
    }

}
fun fillActionsReady(enemy: Enemy)
{
    enemy.actionsReady.clear()
    for (skill in enemy.mainDeck)
        skill?.let { addToActionsReady(it, enemy) }
    Log.d("",enemy.actionsReady.toString())
    Log.d("",enemy.actionsReady.size.toString())
}

fun choseAction(enemy: Enemy): EnemyCards {
    Log.d("",enemy.actionsReady.random().toString())
    return enemy.actionsReady.random()
}

public class Enemy(
    var name: String,
    var lv: MutableState<Int>,
    var MAX_HP: MutableState<Int>,
    var HP: MutableState<Int>,
    var STR: MutableState<Int>,
    var VIT: MutableState<Int>,
    var AGI: MutableState<Int>,
    var INT: MutableState<Int>,
    var weaponDamage: Int,
    var EXPGiven: Int,
    var goldGiven: Int,
    var sprite: Int,
    mainDeck: List<EnemyCards?>
)
{
    val mainDeck = mainDeck.toMutableList()
    val actionQueue = mutableListOf<EnemyCards>()
    val actionsReady = mutableListOf<EnemyCards>()
}

var szkielet = Enemy("Szkielet", mutableStateOf(1), mutableStateOf(20),
    mutableStateOf(20),mutableStateOf(4),mutableStateOf(10),mutableStateOf(10),mutableStateOf(10),10, 100,10,R.drawable.skeleton,
    listOf(enemyCards["Atak"], enemyCards["Leczenie"], enemyCards["Walniecie"])
)

var szkielet_wojownik = Enemy("Szkielet Wojownik", mutableStateOf(1), mutableStateOf(30),mutableStateOf(30),mutableStateOf(6),mutableStateOf(100),mutableStateOf(100),mutableStateOf(100),10, 20,10,R.drawable.szkielet_wojownik,
    listOf(enemyCards["Atak"])
)

var rzeznik = Enemy("Rzeznik", mutableStateOf(1), mutableStateOf(30),mutableStateOf(30),mutableStateOf(11),mutableStateOf(100),mutableStateOf(100),mutableStateOf(100),10, 20,10,R.drawable.rzeznik,
    listOf(enemyCards["Atak"])
)

var upadly_kaplan = Enemy("Upadly Kaplan", mutableStateOf(1), mutableStateOf(20),mutableStateOf(20),mutableStateOf(12),mutableStateOf(100),mutableStateOf(100),mutableStateOf(100),10, 20,10,R.drawable.upadly_kaplan,
    listOf(enemyCards["Atak"])
)

var zagubiona_dusza = Enemy("Zagubiona Dusza", mutableStateOf(1), mutableStateOf(40),mutableStateOf(40),mutableStateOf(10),mutableStateOf(100),mutableStateOf(100),mutableStateOf(100),10, 20,10,R.drawable.zagubiona_dusza,
    listOf(enemyCards["Atak"])
)

var zjawa = Enemy("Zjawa", mutableStateOf(1), mutableStateOf(40),mutableStateOf(40),mutableStateOf(5),mutableStateOf(100),mutableStateOf(100),mutableStateOf(100),10, 20,10,R.drawable.zjawa,
    listOf(enemyCards["Atak"])
)

var zombie = Enemy("Szkielet", mutableStateOf(1), mutableStateOf(50),mutableStateOf(50),mutableStateOf(15),mutableStateOf(100),mutableStateOf(100),mutableStateOf(100),10, 20,10,R.drawable.zombie,
    listOf(enemyCards["Atak"])
)

