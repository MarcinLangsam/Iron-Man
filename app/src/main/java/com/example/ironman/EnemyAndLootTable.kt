package com.example.ironman

import kotlin.random.Random

fun rollLoot(): MutableList<String> {
    val finalLoot = mutableListOf<String>()
    val itemType = mutableListOf("","","","R","R","R","R","W","W","C")
    for (x in 0 until LootTables[dungeonLevel]?.TresureSize!!)
    {
        if (itemType.random() == "R")
        {
            val loot = LootTables[dungeonLevel]?.Runes?.random()
            if (loot != null) {
                player.inventoryRunes.add(loot)
            }
            if (loot != null) {
                finalLoot.add(loot.name)
            }
            itemType.add("")
        }
        if (itemType.random() == "W")
        {
            val loot = LootTables[dungeonLevel]?.Weapons?.random()
            if (loot != null) {
                player.inventoryWeapons.add(loot)
            }
            if (loot != null) {
                finalLoot.add(loot.name)
            }
            itemType.add("")
        }

        /*if (itemType.random() == "C")
        {
            val loot = LootTables[dungeonLevel]?.Cards?.random()
            if (loot != null) {
                player.mainDeck.add(loot)
            }
            if (loot != null) {
                finalLoot.add(loot.nazwa)
            }
            itemType.add("")
        }*/
    }
    val gold = Random.nextInt(10,50)
    finalLoot.add("Zloto: $gold")
    return finalLoot
}

fun rollEnemy() {
    bottomBarCheck = false
    currentEnemy = EnemyTables[dungeonLevel]?.Enemys?.random()!!
}

class LootTable(
    val DungeonLv: Int,
    Cards: List<Card?>,
    Weapons: List<Weapon?>,
    Runes: List<Rune?>,
    val TresureSize: Int
    )
{
    val Cards = Cards.toMutableList()
    val Weapons = Weapons.toMutableList()
    val Runes = Runes.toMutableList()
}


var LootTables = mutableMapOf<Int, LootTable>(
    1 to LootTable(
        1,
        listOf(cards["Leczenie"]),
        listOf(WeaponsList["Wlucznia straznicza"], WeaponsList["Miecz Rycerski"], WeaponsList["Siewca Smierci"],
            WeaponsList["Maczuga Zolneirska"], WeaponsList["Stalowy miecz"]),
        listOf(RunesList["Runa sily"],RunesList["Runa inteligencji"],RunesList["Runa zrecznosci"],RunesList["Runa zdrowia"],RunesList["Runa akcji"]),
        4
    )
)

class EnemyTable (
    val DungeonLv: Int,
    Enemys: List<Enemy>
)
{
    val Enemys = Enemys.toMutableList()
}

var EnemyTables = mutableMapOf<Int, EnemyTable>(
    1 to EnemyTable(
        1,
        listOf(szkielet, zombie, zjawa, )
    )
)