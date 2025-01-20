package com.example.ironman

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import java.io.File
import kotlin.math.floor
import kotlin.random.Random

class Player (
    var name: String,
    var lv: Int,
    var STR: Int,
    var VIT: Int,
    var DEX: Int,
    var INT: Int,
)
{
    //traits
    var rase = ""
    var profesions = mutableListOf<Profesions>()
    var EXP = mutableStateOf(0)
    var EXPtoLv = 5 * lv
    //stats
    var MAX_HP = mutableStateOf(VIT*10)
    var HPbonus = 0
    var HP = mutableStateOf(VIT*10)
    var MAX_AP = mutableStateOf((DEX/5))
    var APbonus = 3
    var AP = mutableStateOf((DEX/5))
    var AP_recovery = 2
    var cardsSlots = 2
    var healthDiceBonus = 0
    var weaponDamage = 0
    var damage = weaponDamage+STR
    //deck
    var actionQueue = mutableStateListOf<Card>()
    var modiferQueue = mutableStateListOf<Modifier>()
    var mainDeck = mutableStateListOf<Card>()
    var cardsOnHand = mutableStateListOf<Card>()
    var mainDeckModifiers = mutableListOf<Modifier>()
    var temporaryDeckModifiers = mutableListOf<Modifier>()
    var modifiersOnHand = mutableListOf<Modifier>()
    //inventory
    var item = Item("Brak", {player.weaponDamage+=0},{player.weaponDamage-=0},0,mutableStateOf(R.drawable.empty_slot),"","empty")
    var weaponSprite = item.sprite
    var armor = Item("Brak", {player.weaponDamage+=0},{player.weaponDamage-=0},0,mutableStateOf(R.drawable.empty_slot),"","empty")
    var armorSprite = armor.sprite
    var necklace = Item("Brak", {player.weaponDamage+=0},{player.weaponDamage-=0},0,mutableStateOf(R.drawable.empty_slot),"","empty")
    var necklaceSprite = necklace.sprite
    var ring = Item("Brak", {player.weaponDamage+=0},{player.weaponDamage-=0},0,mutableStateOf(R.drawable.empty_slot),"","empty")
    var ringSprite = ring.sprite
    var off_hand = Item("Brak", {player.weaponDamage+=0},{player.weaponDamage-=0},0,mutableStateOf(R.drawable.empty_slot),"","empty")
    var off_handSprite = off_hand.sprite
    var helmet = Item("Brak", {player.weaponDamage+=0},{player.weaponDamage-=0},0,mutableStateOf(R.drawable.empty_slot),"","empty")
    var helmetSprite = helmet.sprite
    var pants = Item("Brak", {player.weaponDamage+=0},{player.weaponDamage-=0},0,mutableStateOf(R.drawable.empty_slot),"","empty")
    var pantsSprite = pants.sprite
    var shoes = Item("Brak", {player.weaponDamage+=0},{player.weaponDamage-=0},0,mutableStateOf(R.drawable.empty_slot),"","empty")
    var shoesSprite = shoes.sprite
    var maxRunes = mutableStateOf(0)
    var runesActive: MutableList<Rune> = MutableList(8){ Rune("Brak Runy", {},{}, mutableStateOf(R.drawable.empty_slot),"Brak Runy",false)}
    var inventoryItems = mutableListOf<Item>()
    var inventoryRunes = mutableListOf<Rune>()
    //other
    var gold = 0
    var skillPoint = 1

    fun checkForLvUp(): Boolean {
        if (EXP.value >= EXPtoLv)
        {
            return true
        }
        return false
    }

    fun updateStats()
    {
        MAX_HP.value = VIT + HPbonus
        HP.value = MAX_HP.value
        MAX_AP.value = DEX/2 + APbonus
        AP.value = MAX_AP.value
        damage = weaponDamage+STR
    }

    fun healRoom()
    {
        HP.value = player.MAX_HP.value
        AP.value = player.MAX_AP.value
    }

    fun levelUpPlayer(profession: Profesions){
        handleProfesionSelection(profession)

        HPbonus += profession.healthDice + healthDiceBonus + floor((VIT/10).toDouble()).toInt()
        updateStats()
        HP.value = player.MAX_HP.value
        AP.value = player.MAX_AP.value

    }

    fun handleProfesionSelection(profesion: Profesions) {
        val existingProfesion = profesions.find { it.name == profesion.name }

        if (existingProfesion != null) {
            existingProfesion.profesionLv++

            val newLevel = existingProfesion.profesionLv

            existingProfesion.cards[newLevel]?.forEach { card ->
                card?.name?.let { cardName ->
                    cards[cardName]?.let { addCard(it) }
                }
            }

            existingProfesion.modifier[newLevel]?.forEach { modifier ->
                modifier?.name?.let { modifierName ->
                    modifiers[modifierName]?.let { addModifier(it) }
                }
            }
        } else {
            profesions.add(profesion)

            val newLevel = 1

            profesion.cards[newLevel]?.forEach { card ->
                card?.name?.let { cardName ->
                    cards[cardName]?.let { addCard(it) }
                }
            }

            profesion.modifier[newLevel]?.forEach { modifier ->
                modifier?.name?.let { modifierName ->
                    modifiers[modifierName]?.let { addModifier(it) }
                }
            }
        }


    }

    fun addCard(card: Card) {
        val existingCard = mainDeck.find { it.name == card.name }
        if (existingCard != null) {
            existingCard.number += 1
        } else {
            mainDeck.add(card)
        }
    }
    fun removeCard(card: Card) {
        mainDeck.remove(card)
    }

    //handle modifiers cards functions
    fun updateDeckModfiers()
    {
        temporaryDeckModifiers.clear()
        for (modifier in mainDeckModifiers)
        {
            if (modifier.isActive)
            {
                temporaryDeckModifiers.add(modifier)
            }
        }
    }
    fun rollFullHandModifiers()
    {
        modifiersOnHand.clear()
        for (i in 1..5)
        {
            modifiersOnHand.add(temporaryDeckModifiers.random())
        }
    }
    fun rollOneOnHandAtModifiers(index: Int)
    {
        modifiersOnHand[index] = temporaryDeckModifiers.random()
    }
    fun addModifier(modifier: Modifier) {
        val existingCard = mainDeckModifiers.find { it.name == modifier.name }
        if (existingCard != null) {
            existingCard.number += 1
        } else {
            mainDeckModifiers.add(modifier)
            temporaryDeckModifiers.add(modifier)
        }
    }
    fun removeModifier(modifier: Modifier) {
        mainDeckModifiers.remove(modifier)
        temporaryDeckModifiers.remove(modifier)
    }

    //handle equipment
    fun equipWeapon(equipItem: Item)
    {
        if (equipItem.type == "weapon")
        {
            //old weapon
            deequipAllRune()
            item.deequipEffect.invoke(player)
            //new weapon
            item.sprite = equipItem.sprite
            item.name = equipItem.name
            item.equipEffect = equipItem.equipEffect
            item.deequipEffect = equipItem.deequipEffect
            item.description = equipItem.description
            item.runeSlot = equipItem.runeSlot
            item.equipEffect.invoke(player)
            weaponSprite = item.sprite
            maxRunes.value = item.runeSlot
        }
        else if (equipItem.type == "off_hand")
        {
            //old off_hand
            off_hand.deequipEffect.invoke(player)
            //new off_hand
            off_hand.sprite = equipItem.sprite
            off_hand.name = equipItem.name
            off_hand.equipEffect = equipItem.equipEffect
            off_hand.deequipEffect = equipItem.deequipEffect
            off_hand.description = equipItem.description
            off_hand.runeSlot = equipItem.runeSlot
            off_hand.equipEffect.invoke(player)
            off_handSprite = off_hand.sprite
        }
        else if (equipItem.type == "armor")
        {
            //old armor
            armor.deequipEffect.invoke(player)
            //new armor
            armor.sprite = equipItem.sprite
            armor.name = equipItem.name
            armor.equipEffect = equipItem.equipEffect
            armor.deequipEffect = equipItem.deequipEffect
            armor.description = equipItem.description
            armor.runeSlot = equipItem.runeSlot
            armor.equipEffect.invoke(player)
            armorSprite = armor.sprite
        }
        else if (equipItem.type == "necklace")
        {
            //old necklace
            necklace.deequipEffect.invoke(player)
            //new necklace
            necklace.sprite = equipItem.sprite
            necklace.name = equipItem.name
            necklace.equipEffect = equipItem.equipEffect
            necklace.deequipEffect = equipItem.deequipEffect
            necklace.description = equipItem.description
            necklace.runeSlot = equipItem.runeSlot
            necklace.equipEffect.invoke(player)
            necklaceSprite = necklace.sprite
        }
        else if (equipItem.type == "ring")
        {
            //old ring
            ring.deequipEffect.invoke(player)
            //new ring
            ring.sprite = equipItem.sprite
            ring.name = equipItem.name
            ring.equipEffect = equipItem.equipEffect
            ring.deequipEffect = equipItem.deequipEffect
            ring.description = equipItem.description
            ring.runeSlot = equipItem.runeSlot
            ring.equipEffect.invoke(player)
            ringSprite = ring.sprite
        }
        else if (equipItem.type == "helmet")
        {
            //old helmet
            helmet.deequipEffect.invoke(player)
            //new helmet
            helmet.sprite = equipItem.sprite
            helmet.name = equipItem.name
            helmet.equipEffect = equipItem.equipEffect
            helmet.deequipEffect = equipItem.deequipEffect
            helmet.description = equipItem.description
            helmet.runeSlot = equipItem.runeSlot
            helmet.equipEffect.invoke(player)
            helmetSprite = helmet.sprite
        }
        else if (equipItem.type == "shoes")
        {
            //old shoes
            shoes.deequipEffect.invoke(player)
            //new shoes
            shoes.sprite = equipItem.sprite
            shoes.name = equipItem.name
            shoes.equipEffect = equipItem.equipEffect
            shoes.deequipEffect = equipItem.deequipEffect
            shoes.description = equipItem.description
            shoes.runeSlot = equipItem.runeSlot
            shoes.equipEffect.invoke(player)
            shoesSprite = shoes.sprite
        }
        else if (equipItem.type == "pants")
        {
            //old pants
            pants.deequipEffect.invoke(player)
            //new pants
            pants.sprite = equipItem.sprite
            pants.name = equipItem.name
            pants.equipEffect = equipItem.equipEffect
            pants.deequipEffect = equipItem.deequipEffect
            pants.description = equipItem.description
            pants.runeSlot = equipItem.runeSlot
            pants.equipEffect.invoke(player)
            pantsSprite = pants.sprite
        }

    }

    fun equipRune(equipRune: Rune)
    {
        for (i in 0 until maxRunes.value)
        {
            if(runesActive[i].sprite.value == R.drawable.empty_slot)
            {
                runesActive[i].sprite.value = equipRune.sprite.value
                runesActive[i].name = equipRune.name
                runesActive[i].equipEffect = equipRune.equipEffect
                runesActive[i].deequipEffect = equipRune.deequipEffect
                runesActive[i].description = equipRune.description
                equipRune.equipEffect.invoke(player)
                //inventoryRunes.remove(equipRune)
                break
            }
        }
    }
    fun deequipRune()
    {
        for (i in maxRunes.value downTo 0)
        {
            if(runesActive[i].name != "Brak Runy")
            {
                runesActive[i].deequipEffect.invoke(player)
                runesActive[i].sprite.value = R.drawable.empty_slot
                runesActive[i].name = "Brak Runy"
                runesActive[i].equipEffect = {}
                runesActive[i].deequipEffect = {}
                runesActive[i].description = "Brak Runy"
                inventoryRunes[activeRunesIndex.last()].isActive = !inventoryRunes[activeRunesIndex.last()].isActive
                activeRunesIndex.removeAt(activeRunesIndex.lastIndex)
                break
            }
        }
    }
    fun deequipAllRune()
    {
        for (i in 0 until maxRunes.value)
        {
            runesActive[i].deequipEffect.invoke(player)
            runesActive[i].sprite.value = R.drawable.empty_slot
            runesActive[i].name = "Brak Runy"
            runesActive[i].equipEffect = {}
            runesActive[i].deequipEffect = {}
            runesActive[i].description = "Brak Runy"
            activeRunesIndex.clear()
        }

        for(rune in inventoryRunes)
        {
            rune.isActive = false
        }
    }

    fun resetPlayer(){
        player.skillPoint = 1
        player.STR = 1
        player.VIT = 1
        player.DEX = 1
        player.INT = 1
    }
}

fun savePlayerDataToFile(player: Player, context: Context) {
    val fileName = "save.txt"
    val fileContent = buildString {
        appendLine("name=${player.name}")
        appendLine("lv=${player.lv}")
        appendLine("STR=${player.STR}")
        appendLine("VIT=${player.VIT}")
        appendLine("DEX=${player.DEX}")
        appendLine("INT=${player.INT}")
        appendLine("EXP=${player.EXP.value}")
        appendLine("EXPtoLv=${player.EXPtoLv}")
        appendLine("AP_recovery=${player.AP_recovery}")
        appendLine("cardsSlots=${player.cardsSlots}")
        appendLine("healthDiceBonus=${player.healthDiceBonus}")
        appendLine("rase=${player.rase}")
        appendLine("gold=${player.gold}")
        appendLine("maxRunes=${player.maxRunes.value}")
        appendLine("runesActive=${player.runesActive.joinToString(";") { it.name }}")
        appendLine("inventoryWeapons=${player.inventoryItems.joinToString(";") { it.name }}")
        appendLine("inventoryRunes=${player.inventoryRunes.joinToString(";") { it.name }}")
        appendLine("profesions=${player.profesions.joinToString(";") { "${it.name} (${it.profesionLv})" }}")
        appendLine("actionQueue=${player.actionQueue.joinToString(";") { it.name }}")
        appendLine("modifierQueue=${player.modiferQueue.joinToString(";") { it.name }}")
        appendLine("mainDeck=${player.mainDeck.joinToString(";") { it.name }}")
        appendLine("cardsOnHand=${player.cardsOnHand.joinToString(";") { it.name }}")
        appendLine("mainDeckModifiers=${player.mainDeckModifiers.joinToString(";") { it.name }}")
        appendLine("temporaryDeckModifiers=${player.temporaryDeckModifiers.joinToString(";") { it.name }}")
        appendLine("modifiersOnHand=${player.modifiersOnHand.joinToString(";") { it.name }}")
        appendLine("weapon=${player.item.name}")
        appendLine("armor=${player.armor.name}")
        appendLine("necklace=${player.necklace.name}")
        appendLine("ring=${player.ring.name}")
        appendLine("off_hand=${player.off_hand.name}")
        appendLine("helmet=${player.helmet.name}")
        appendLine("pants=${player.pants.name}")
        appendLine("shoes=${player.shoes.name}")
    }
    context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
        it.write(fileContent.toByteArray())
    }
}


fun loadPlayerDataFromFile(context: Context, fileName: String = "save.txt"): Player {
    val file = File(context.filesDir, fileName)
    val lines = file.readLines()

    val map = lines.associate {
        val (key, value) = it.split(" = ", limit = 2)
        key to value.replace("\"", "").trim()
    }

    val profesionsList = lines.filter { it.startsWith("Profesions") }.map {
        val (_, data) = it.split(" = ")
        val (name, profesionLv) = data.split(": ").map { part -> part.trim() }
        Profesions(
            name = name,
            profesionLv = profesionLv.toInt(),
            cards = mutableMapOf(),
            perks = mutableMapOf(),
            healthDice = 0,
            modifier = mutableMapOf()
        )
    }.toMutableList()

    val runesActiveList = map["runesActive"]
        ?.split(";")
        ?.mapNotNull { findRuneByName(it) }
        ?.toMutableList()
        ?: MutableList(8) { Rune("Brak Runy", {}, {}, mutableStateOf(R.drawable.empty_slot), "Brak Runy", false) }

    val inventoryWeaponsList = map["inventoryWeapons"]
        ?.split(";")
        ?.map { findWeaponByName(it) }
        ?.toMutableList()
        ?: mutableListOf()

    val inventoryRunesList = map["inventoryRunes"]
        ?.split(";")
        ?.mapNotNull { findRuneByName(it) }
        ?.toMutableList()
        ?: mutableListOf()

    fun parseCardList(key: String): MutableList<Card> {
        return map[key]?.split(", ")?.mapNotNull { cardName ->
            findCardByName(cardName)
        }?.toMutableList() ?: mutableListOf()
    }

    fun parseModifierList(key: String): MutableList<Modifier> {
        return map[key]?.split(", ")?.mapNotNull { modifierName ->
            findModifierByName(modifierName)
        }?.toMutableList() ?: mutableListOf()
    }

    fun parseRuneList(runesString: String?): List<Rune> {
        return runesString?.split(";")?.mapNotNull { findRuneByName(it) } ?: emptyList()
    }

    val player = Player(
        name = map["name"] ?: "Unknown",
        lv = map["lv"]?.toInt() ?: 1,
        STR = map["STR"]?.toInt() ?: 0,
        VIT = map["VIT"]?.toInt() ?: 0,
        DEX = map["DEX"]?.toInt() ?: 0,
        INT = map["INT"]?.toInt() ?: 0
    )

    player.rase = map["rase"] ?: "Human"
    player.profesions.addAll(profesionsList)

    player.EXP.value = map["EXP"]?.toInt() ?: 0
    player.EXPtoLv = map["EXPtoLv"]?.toInt() ?: 100
    player.AP_recovery = map["AP_recovery"]?.toInt() ?: 2
    player.cardsSlots = map["cardsSlots"]?.toInt() ?: 2
    player.healthDiceBonus = map["healthDiceBonus"]?.toInt() ?: 0

    player.mainDeck.addAll(parseCardList("mainDeck"))
    player.mainDeckModifiers.addAll(parseModifierList("mainDeckModifiers"))
    player.temporaryDeckModifiers.addAll(parseModifierList("temporaryDeckModifiers"))

    player.item = findWeaponByName(map["weapon"] ?: "Brak")
    player.armor = findWeaponByName(map["armor"] ?: "Brak")
    player.necklace = findWeaponByName(map["necklace"] ?: "Brak")
    player.ring = findWeaponByName(map["ring"] ?: "Brak")
    player.off_hand = findWeaponByName(map["off_hand"] ?: "Brak")
    player.helmet = findWeaponByName(map["helmet"] ?: "Brak")
    player.pants = findWeaponByName(map["pants"] ?: "Brak")
    player.shoes = findWeaponByName(map["shoes"] ?: "Brak")

    player.maxRunes.value = map["maxRunes"]?.toInt() ?: 0
    player.runesActive = runesActiveList
    player.inventoryItems = inventoryWeaponsList
    player.inventoryRunes = inventoryRunesList

    player.gold = map["gold"]?.toInt() ?: 0

    return player
}

fun findRuneByName(name: String): Rune? {
    return RunesList[name]
}

fun findCardByName(name: String): Card? {
    return cards[name]
}

fun findModifierByName(name: String): Modifier? {
    return modifiers[name]
}

fun findWeaponByName(name: String): Item {
    return ItemsList[name]!!
}


var player = Player("Gracz",1,1,1,1,1)