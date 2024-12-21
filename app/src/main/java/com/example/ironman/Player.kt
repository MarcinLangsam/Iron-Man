package com.example.ironman

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class Player (
    var name: String,
    var lv: Int,
    var STR: Int,
    var VIT: Int,
    var DEX: Int,
    var INT: Int,

)
{
    var healthDice = 10
    var skillPoint = 1
    var rase = ""
    var profesions = mutableListOf<Profesions>()
    var portrait = R.drawable.portrait1
    var MAX_HP = mutableStateOf(VIT*10)
    var HP = mutableStateOf(VIT*10)
    var MAX_AP = mutableStateOf((DEX/5)+100)
    var AP = mutableStateOf((DEX/5)+100)
    var EXP = mutableStateOf(0)
    var EXPtoLv = 100 * lv
    var AP_recovery = 2
    var cardsSlots = 4

    var actionQueue = mutableStateListOf<Card>()
    var modiferQueue = mutableStateListOf<Modifier>()
    var mainDeck = mutableStateListOf<Card>()
    //var temporaryDeck = mutableListOf<Card>()
    var cardsOnHand = mutableStateListOf<Card>()

    var mainDeckModifiers = mutableListOf<Modifier>()
    var temporaryDeckModifiers = mutableListOf<Modifier>()
    var modifiersOnHand = mutableListOf<Modifier>()



    var weapon = Weapon("Brak", {player.weaponDamage+=0},{player.weaponDamage-=0},0,mutableStateOf(R.drawable.empty_slot),"","empty")
    var weaponSprite = weapon.sprite
    var armor = Weapon("Brak", {player.weaponDamage+=0},{player.weaponDamage-=0},0,mutableStateOf(R.drawable.empty_slot),"","empty")
    var armorSprite = armor.sprite
    var necklace = Weapon("Brak", {player.weaponDamage+=0},{player.weaponDamage-=0},0,mutableStateOf(R.drawable.empty_slot),"","empty")
    var necklaceSprite = necklace.sprite
    var ring = Weapon("Brak", {player.weaponDamage+=0},{player.weaponDamage-=0},0,mutableStateOf(R.drawable.empty_slot),"","empty")
    var ringSprite = ring.sprite
    var off_hand = Weapon("Brak", {player.weaponDamage+=0},{player.weaponDamage-=0},0,mutableStateOf(R.drawable.empty_slot),"","empty")
    var off_handSprite = off_hand.sprite
    var helmet = Weapon("Brak", {player.weaponDamage+=0},{player.weaponDamage-=0},0,mutableStateOf(R.drawable.empty_slot),"","empty")
    var helmetSprite = helmet.sprite
    var pants = Weapon("Brak", {player.weaponDamage+=0},{player.weaponDamage-=0},0,mutableStateOf(R.drawable.empty_slot),"","empty")
    var pantsSprite = pants.sprite
    var shoes = Weapon("Brak", {player.weaponDamage+=0},{player.weaponDamage-=0},0,mutableStateOf(R.drawable.empty_slot),"","empty")
    var shoesSprite = shoes.sprite

    var maxRunes = mutableStateOf(0)
    var weaponDamage = 0
    var damage = weaponDamage+STR
    var runesActive: MutableList<Rune> = MutableList(8){ Rune("Brak Runy", {},{}, mutableStateOf(R.drawable.empty_slot),"Brak Runy",false)}

    var inventoryWeapons = mutableListOf<Weapon>()
    var inventoryRunes = mutableListOf<Rune>()

    var gold = 0

    fun levelUp(): Boolean {
        if (player.EXP.value >= player.EXPtoLv)
        {
            return true
        }

        return false
    }


    fun updateStats()
    {
        MAX_HP.value = VIT*10
        HP.value = MAX_HP.value
        MAX_AP.value = DEX/5+100
        AP.value = MAX_AP.value
        damage = weaponDamage+STR
    }

    fun addCard(card: Card) {
        val existingCard = mainDeck.find { it.name == card.name }
        if (existingCard != null) {
            existingCard.number += 1
        } else {
            mainDeck.add(card)
            //temporaryDeck.add(card)
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
    fun equipWeapon(equipWeapon: Weapon)
    {
        if (equipWeapon.type == "weapon")
        {
            //old weapon
            deequipAllRune()
            weapon.deequipEffect.invoke(player)
            //new weapon
            weapon.sprite = equipWeapon.sprite
            weapon.name = equipWeapon.name
            weapon.equipEffect = equipWeapon.equipEffect
            weapon.deequipEffect = equipWeapon.deequipEffect
            weapon.description = equipWeapon.description
            weapon.runeSlot = equipWeapon.runeSlot
            weapon.equipEffect.invoke(player)
            weaponSprite = weapon.sprite
            maxRunes.value = weapon.runeSlot
        }
        else if (equipWeapon.type == "off_hand")
        {
            //old off_hand
            off_hand.deequipEffect.invoke(player)
            //new off_hand
            off_hand.sprite = equipWeapon.sprite
            off_hand.name = equipWeapon.name
            off_hand.equipEffect = equipWeapon.equipEffect
            off_hand.deequipEffect = equipWeapon.deequipEffect
            off_hand.description = equipWeapon.description
            off_hand.runeSlot = equipWeapon.runeSlot
            off_hand.equipEffect.invoke(player)
            off_handSprite = off_hand.sprite
        }
        else if (equipWeapon.type == "armor")
        {
            //old armor
            armor.deequipEffect.invoke(player)
            //new armor
            armor.sprite = equipWeapon.sprite
            armor.name = equipWeapon.name
            armor.equipEffect = equipWeapon.equipEffect
            armor.deequipEffect = equipWeapon.deequipEffect
            armor.description = equipWeapon.description
            armor.runeSlot = equipWeapon.runeSlot
            armor.equipEffect.invoke(player)
            armorSprite = armor.sprite
        }
        else if (equipWeapon.type == "necklace")
        {
            //old necklace
            necklace.deequipEffect.invoke(player)
            //new necklace
            necklace.sprite = equipWeapon.sprite
            necklace.name = equipWeapon.name
            necklace.equipEffect = equipWeapon.equipEffect
            necklace.deequipEffect = equipWeapon.deequipEffect
            necklace.description = equipWeapon.description
            necklace.runeSlot = equipWeapon.runeSlot
            necklace.equipEffect.invoke(player)
            necklaceSprite = necklace.sprite
        }
        else if (equipWeapon.type == "ring")
        {
            //old ring
            ring.deequipEffect.invoke(player)
            //new ring
            ring.sprite = equipWeapon.sprite
            ring.name = equipWeapon.name
            ring.equipEffect = equipWeapon.equipEffect
            ring.deequipEffect = equipWeapon.deequipEffect
            ring.description = equipWeapon.description
            ring.runeSlot = equipWeapon.runeSlot
            ring.equipEffect.invoke(player)
            ringSprite = ring.sprite
        }
        else if (equipWeapon.type == "helmet")
        {
            //old helmet
            helmet.deequipEffect.invoke(player)
            //new helmet
            helmet.sprite = equipWeapon.sprite
            helmet.name = equipWeapon.name
            helmet.equipEffect = equipWeapon.equipEffect
            helmet.deequipEffect = equipWeapon.deequipEffect
            helmet.description = equipWeapon.description
            helmet.runeSlot = equipWeapon.runeSlot
            helmet.equipEffect.invoke(player)
            helmetSprite = helmet.sprite
        }
        else if (equipWeapon.type == "shoes")
        {
            //old shoes
            shoes.deequipEffect.invoke(player)
            //new shoes
            shoes.sprite = equipWeapon.sprite
            shoes.name = equipWeapon.name
            shoes.equipEffect = equipWeapon.equipEffect
            shoes.deequipEffect = equipWeapon.deequipEffect
            shoes.description = equipWeapon.description
            shoes.runeSlot = equipWeapon.runeSlot
            shoes.equipEffect.invoke(player)
            shoesSprite = shoes.sprite
        }
        else if (equipWeapon.type == "pants")
        {
            //old pants
            pants.deequipEffect.invoke(player)
            //new pants
            pants.sprite = equipWeapon.sprite
            pants.name = equipWeapon.name
            pants.equipEffect = equipWeapon.equipEffect
            pants.deequipEffect = equipWeapon.deequipEffect
            pants.description = equipWeapon.description
            pants.runeSlot = equipWeapon.runeSlot
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
                activeRunesIndex.removeLast()
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
        player.STR = 10
        player.VIT = 10
        player.DEX = 10
        player.INT = 10
    }
}

@Serializable
data class SerializablePlayer(
    val name: String,
    val lv: Int,
    val STR: Int,
    val VIT: Int,
    val DEX: Int,
    val INT: Int,
    val MAX_HP: Int,
    val HP: Int,
    val MAX_AP: Int,
    val AP: Int,
    val EXP: Int,
    val EXPtoLv: Int,
    val AP_recovery: Int,
    val mainDeck: List<String>,
    val cardsOnHand: List<String>,
    val weapon: String,
    val runesActive: List<String>,
    val maxRunes: Int,
    val inventoryWeapons: List<String>,
    val inventoryRunes: List<String>,
    val gold: Int
)

fun getCardByName(name: String): Card {
    return cards[name] ?: Card(name, { 0 }, 0, "", 0, 0, 0, "", "", 0, true)
}

fun getWeaponByName(name: String): Weapon {
    return WeaponsList[name] ?: Weapon(name, { _: Player -> }, { _: Player -> }, 0, mutableStateOf(R.drawable.empty_slot), "","empty")
}

fun getRuneByName(name: String): Rune {
    return RunesList[name] ?: Rune(name, {}, {}, mutableStateOf(R.drawable.empty_slot), "",false)
}

@Serializable
data class PlayerSerializable(
    var name: String,
    var lv: Int,
    var STR: Int,
    var VIT: Int,
    var DEX: Int,
    var INT: Int,
    var healthDice: Int,
    var skillPoint: Int,
    var rase: String,
    var profesions: List<String>,
    var portrait: Int,
    var maxHP: Int,
    var currentHP: Int,
    var maxAP: Int,
    var currentAP: Int,
    var exp: Int,
    var expToLv: Int,
    var apRecovery: Int,
    var cardsSlots: Int,
    var actionQueue: List<String>,
    var modifierQueue: List<String>,
    var mainDeck: List<String>,
    var cardsOnHand: List<String>,
    var weaponName: String,
    var armorName: String,
    var inventoryWeapons: List<String>,
    var inventoryRunes: List<String>
)

fun toSerializable(player: Player): PlayerSerializable {
    return PlayerSerializable(
        name = player.name,
        lv = player.lv,
        STR = player.STR,
        VIT = player.VIT,
        DEX = player.DEX,
        INT = player.INT,
        healthDice = player.healthDice,
        skillPoint = player.skillPoint,
        rase = player.rase,
        profesions = player.profesions.map { it.name }, // Nazwy profesji
        portrait = player.portrait,
        maxHP = player.MAX_HP.value,
        currentHP = player.HP.value,
        maxAP = player.MAX_AP.value,
        currentAP = player.AP.value,
        exp = player.EXP.value,
        expToLv = player.EXPtoLv,
        apRecovery = player.AP_recovery,
        cardsSlots = player.cardsSlots,
        actionQueue = player.actionQueue.map { it.name },
        modifierQueue = player.modiferQueue.map { it.name },
        mainDeck = player.mainDeck.map { it.name },
        cardsOnHand = player.cardsOnHand.map { it.name },
        weaponName = player.weapon.name,
        armorName = player.armor.name,
        inventoryWeapons = player.inventoryWeapons.map { it.name },
        inventoryRunes = player.inventoryRunes.map { it.name }
    )
}



val player = Player("Gracz",1,20,30,10,10)