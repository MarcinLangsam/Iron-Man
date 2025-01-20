package com.example.ironman

import kotlin.random.Random

fun rollLoot(): MutableList<String> {
    val finalLoot = mutableListOf<String>()
    val itemType = mutableListOf("","","","R","R","W","W","W","W")
    for (x in 0 until LootTables[dungeonLevel]?.TresureSize!!)
    {
        if (itemType.random() == "R" && LootTables[dungeonLevel]?.runes?.isNotEmpty() == true)
        {
            val loot = LootTables[dungeonLevel]?.runes?.random()
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
            val loot = LootTables[dungeonLevel]?.items?.random()
            if (loot != null) {
                player.inventoryItems.add(loot)
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
    cards: List<Card?>,
    modifier: List<Modifier?>,
    items: List<Item?>,
    runes: List<Rune?>,
    val TresureSize: Int
    )
{
    val cards = cards.toMutableList()
    val modifier = modifier.toMutableList()
    val items = items.toMutableList()
    val runes = runes.toMutableList()
}


var LootTables = mutableMapOf<Int, LootTable>(
    1 to LootTable(
        1,
        listOf(),
        listOf(),
        listOf(
            ItemsList["Miecz Z Brazu"], ItemsList["Stalowy Miecz"], ItemsList["Pika"], ItemsList["Kostur Maga"],
            ItemsList["Drewniana Tarcza"],
            ItemsList["Skórzany Pancerz"],
            ItemsList["Skórzane Spodnie"], ItemsList["Spodnie Z Torbami"],
            ItemsList["Obtarte Buty"],
            ItemsList["Srebrny Pierścień"], ItemsList["Złoty Pierścień"],
        ),
        listOf(),
        3
    ),
    2 to LootTable(
        1,
        listOf(),
        listOf(),
        listOf(
            ItemsList["Miecz Półtoraręczny"], ItemsList["Stalowy Miecz"], ItemsList["Pika"], ItemsList["Kostur Maga"], ItemsList["Rytualny Sztylet"],
            ItemsList["Drewniana Tarcza"], ItemsList["Stalowa Tarcza"],
            ItemsList["Skórzany Pancerz"], ItemsList["Pikowany Pancerz"],
            ItemsList["Skórzane Spodnie"], ItemsList["Spodnie Z Torbami"], ItemsList["Pikowane Spodnie"],
            ItemsList["Obtarte Buty"],
            ItemsList["Srebrny Pierścień"], ItemsList["Złoty Pierścień"],
        ),
        listOf(RunesList["Runa Siły"], RunesList["Runa Inteligencji"], RunesList["Runa Zręcznosci"], RunesList["Runa Zdrowia"], RunesList["Runa Akcji"], ),
        3
    ),
    3 to LootTable(
        1,
        listOf(),
        listOf(),
        listOf(
            ItemsList["Miecz Półtoraręczny"], ItemsList["Stalowy Miecz"], ItemsList["Pika"], ItemsList["Kostur Maga"], ItemsList["Rytualny Sztylet"],
            ItemsList["Drewniana Tarcza"], ItemsList["Stalowa Tarcza"], ItemsList["Magiczna Księga"], ItemsList["Podstępny Majcher"],
            ItemsList["Stalowy Hełm"],
            ItemsList["Skórzany Pancerz"], ItemsList["Pikowany Pancerz"],
            ItemsList["Skórzane Spodnie"], ItemsList["Spodnie Z Torbami"], ItemsList["Pikowane Spodnie"],
            ItemsList["Obtarte Buty"], ItemsList["Wysokie Buty"],
            ItemsList["Srebrny Pierścień"], ItemsList["Złoty Pierścień"],
        ),
        listOf(RunesList["Runa Siły"], RunesList["Runa Inteligencji"], RunesList["Runa Zręcznosci"], RunesList["Runa Zdrowia"], RunesList["Runa Akcji"], ),
        4
    ),
    4 to LootTable(
        1,
        listOf(),
        listOf(),
        listOf(
            ItemsList["Miecz Półtoraręczny"], ItemsList["Kostur Maga"], ItemsList["Rytualny Sztylet"], ItemsList["Miecz Rycerski"], ItemsList["Topór Rzeźnika"], ItemsList["Młot Bojowy"],
            ItemsList["Drewniana Tarcza"], ItemsList["Stalowa Tarcza"], ItemsList["Magiczna Księga"], ItemsList["Podstępny Majcher"],
            ItemsList["Stalowy Hełm"], ItemsList["Kaptur"], ItemsList["Runiczny Kaptur"],
            ItemsList["Pikowany Pancerz"],
            ItemsList["Spodnie Z Torbami"], ItemsList["Pikowane Spodnie"],
            ItemsList["Obtarte Buty"], ItemsList["Wysokie Buty"], ItemsList["Stalowe Buty"],
            ItemsList["Srebrny Pierścień"], ItemsList["Złoty Pierścień"], ItemsList["Pierścien Zdrowia"], ItemsList["Pierścien Siły"],ItemsList["Pierścien Zręczności"], ItemsList["Pierścien Inteligencji"],
        ),
        listOf(RunesList["Runa Siły"], RunesList["Runa Inteligencji"], RunesList["Runa Zręcznosci"], RunesList["Runa Zdrowia"], RunesList["Runa Akcji"], ),
        4
    ),
    5 to LootTable(
        1,
        listOf(),
        listOf(),
        listOf(
            ItemsList["Miecz Półtoraręczny"], ItemsList["Kostur Maga"], ItemsList["Rytualny Sztylet"], ItemsList["Miecz Rycerski"], ItemsList["Topór Rzeźnika"], ItemsList["Młot Bojowy"], ItemsList["Maczuga Żołnierska"],
            ItemsList["Drewniana Tarcza"], ItemsList["Stalowa Tarcza"], ItemsList["Magiczna Księga"], ItemsList["Podstępny Majcher"],
            ItemsList["Stalowy Hełm"], ItemsList["Kaptur"], ItemsList["Runiczny Kaptur"],
            ItemsList["Przyszywanica"], ItemsList["Kolczuga"], ItemsList["Szata Maga"],
            ItemsList["Spodnie Z Torbami"], ItemsList["Przeszywane Spodnie"], ItemsList["Spodnie Kolcze"], ItemsList["Szarfa Maga"],
            ItemsList["Wysokie Buty"], ItemsList["Stalowe Buty"],
            ItemsList["Amulet Precyzji"], ItemsList["Amulet Magi"],
            ItemsList["Srebrny Pierścień"], ItemsList["Złoty Pierścień"], ItemsList["Pierścien Zdrowia"], ItemsList["Pierścien Siły"],ItemsList["Pierścien Zręczności"], ItemsList["Pierścien Inteligencji"],
        ),
        listOf(RunesList["Runa Siły"], RunesList["Runa Inteligencji"], RunesList["Runa Zręcznosci"], RunesList["Runa Zdrowia"], RunesList["Runa Akcji"], ),
        5
    ),
    6 to LootTable(
        1,
        listOf(),
        listOf(),
        listOf(
            ItemsList["Topor Wojownika"], ItemsList["Włócznia Strażnicza"], ItemsList["Gladius"], ItemsList["Miecz Rycerski"], ItemsList["Topór Rzeźnika"], ItemsList["Spaczony Kostur"], ItemsList["Maczuga Żołnierska"], ItemsList["Siewca Śmierci"],
            ItemsList["Stalowa Tarcza"], ItemsList["Magiczna Księga"], ItemsList["Podstępny Majcher"],
            ItemsList["Stalowy Hełm"], ItemsList["Kaptur"], ItemsList["Runiczny Kaptur"], ItemsList["Hełm Szału"],
            ItemsList["Przyszywanica"], ItemsList["Kolczuga"], ItemsList["Szata Maga"],
            ItemsList["Spodnie Z Torbami"], ItemsList["Przeszywane Spodnie"], ItemsList["Spodnie Kolcze"], ItemsList["Szarfa Maga"],
            ItemsList["Wysokie Buty"], ItemsList["Stalowe Buty"], ItemsList["Hermeski"],
            ItemsList["Amulet Precyzji"], ItemsList["Amulet Magi"],
            ItemsList["Większy Pierścien Zdrowia"], ItemsList["Większy Pierścien Siły"],ItemsList["Większy Pierścien Zręczności"], ItemsList["Większy Pierścien Inteligencji"],
        ),
        listOf(RunesList["Większa Runa Siły"], RunesList["Większa Runa Inteligencji"], RunesList["Większa Runa Zręcznosci"], RunesList["WiększaRuna Zdrowia"], RunesList["WiększaRuna Akcji"], ),
        5
    ),
    7 to LootTable(
        1,
        listOf(),
        listOf(),
        listOf(
            ItemsList["Topor Wojownika"], ItemsList["Włócznia Strażnicza"], ItemsList["Gladius"], ItemsList["Topór Rzeźnika"], ItemsList["Spaczony Kostur"], ItemsList["Siewca Śmierci"],
            ItemsList["Zapomniana Tarcza"],
            ItemsList["Stalowy Hełm"], ItemsList["Kaptur"], ItemsList["Runiczny Kaptur"], ItemsList["Hełm Szału"],
            ItemsList["Przyszywanica"], ItemsList["Kolczuga"], ItemsList["Szata Maga"],
            ItemsList["Spodnie Z Torbami"], ItemsList["Przeszywane Spodnie"], ItemsList["Spodnie Kolcze"], ItemsList["Szarfa Maga"],
            ItemsList["Wysokie Buty"], ItemsList["Stalowe Buty"], ItemsList["Hermeski"],
            ItemsList["Amulet Precyzji"], ItemsList["Amulet Magi"],
            ItemsList["Większy Pierścien Zdrowia"], ItemsList["Większy Pierścien Siły"],ItemsList["Większy Pierścien Zręczności"], ItemsList["Większy Pierścien Inteligencji"],
        ),
        listOf(RunesList["Większa Runa Siły"], RunesList["Większa Runa Inteligencji"], RunesList["Większa Runa Zręcznosci"], RunesList["WiększaRuna Zdrowia"], RunesList["WiększaRuna Akcji"], ),
        5
    ),
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
        listOf(szkielet)
    ),
    2 to EnemyTable(
        1,
        listOf(szkielet)
    ),
    3 to EnemyTable(
        1,
        listOf(szkielet)
    ),
    4 to EnemyTable(
        1,
        listOf(szkielet)
    ),
    5 to EnemyTable(
        1,
        listOf(szkielet)
    ),
    6 to EnemyTable(
        1,
        listOf(szkielet)
    ),
    7 to EnemyTable(
        1,
        listOf(szkielet)
    ),
)