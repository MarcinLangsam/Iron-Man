package com.example.ironman

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import kotlinx.serialization.Serializable
import kotlin.math.max

public class Player (
    var name: String,
    var lv: Int,
    var STR: Int,
    var VIT: Int,
    var DEX: Int,
    var INT: Int
)
{
    var MAX_HP = mutableStateOf(VIT*10)
    var HP = mutableStateOf(VIT*10)
    var MAX_AP = mutableStateOf((DEX/5)+100)
    var AP = mutableStateOf((DEX/5)+100)
    var EXP = mutableStateOf(0)
    var EXPtoLv = 100 * lv
    var AP_recovery = 2
    var actionQueue = mutableStateListOf<Card>()
    var mainDeck = mutableListOf<Card>()
    var temporaryDeck = mutableListOf<Card>()
    var on_hand = mutableListOf<Card>()

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

    fun updateDeck()
    {
        temporaryDeck.clear()
        for (card in mainDeck)
        {
            if (card.isActive)
            {
                temporaryDeck.add(card)
            }
        }

        for (card in temporaryDeck)
        {
            Log.d("",card.name)
        }
    }

    fun rollFullHand()
    {
        on_hand.clear()
        for (i in 1..5)
        {
            on_hand.add(temporaryDeck.random())
        }
    }
    fun rollOneOnHandAt(index: Int)
    {
        on_hand[index] = temporaryDeck.random()
    }

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

    fun addCard(card: Card) {
        val existingCard = mainDeck.find { it.name == card.name }
        if (existingCard != null) {
            existingCard.number += 1
        } else {
            mainDeck.add(card)
            temporaryDeck.add(card)
        }
    }

    fun toSerializablePlayer(): SerializablePlayer {
        return SerializablePlayer(
            name, lv, STR, VIT, DEX, INT, MAX_HP.value, HP.value, MAX_AP.value, AP.value, EXP.value, EXPtoLv, AP_recovery,
            mainDeck.map { it.name },
            temporaryDeck.map { it.name },
            weapon.name,
            runesActive.map { it.name },
            maxRunes.value,
            inventoryWeapons.map { it.name },
            inventoryRunes.map { it.name },
            gold
        )
    }


        fun loadPlayer(serializablePlayer: SerializablePlayer) {
            player.inventoryRunes.clear()
            player.inventoryWeapons.clear()
            player.deequipAllRune()

            player.runesActive.clear()
            player.runesActive = MutableList(8){ Rune("Brak Runy", {},{}, mutableStateOf(R.drawable.empty_slot),"Brak Runy",false)}

            player.name = serializablePlayer.name
            player.lv = serializablePlayer.lv
            player.STR = serializablePlayer.STR
            player.DEX = serializablePlayer.DEX
            player.VIT = serializablePlayer.VIT
            player.INT = serializablePlayer.INT
            player.MAX_HP.value = serializablePlayer.MAX_HP
            player.HP.value = serializablePlayer.HP
            player.MAX_AP.value = serializablePlayer.MAX_AP
            player.AP.value = serializablePlayer.AP
            player.EXP.value = serializablePlayer.EXP
            player.EXPtoLv = serializablePlayer.EXPtoLv
            player.AP_recovery = serializablePlayer.AP_recovery
            player.mainDeck = serializablePlayer.mainDeck.map { getCardByName(it) }.toMutableList()
            player.temporaryDeck = serializablePlayer.temporaryDeck.map { getCardByName(it) }.toMutableList()
            player.weapon = getWeaponByName(serializablePlayer.weapon)
            player.runesActive = serializablePlayer.runesActive.map { getRuneByName(it) }.toMutableList()
            player.maxRunes.value = serializablePlayer.maxRunes
            player.inventoryWeapons = serializablePlayer.inventoryWeapons.map { getWeaponByName(it) }.toMutableList()
            player.inventoryRunes = serializablePlayer.inventoryRunes.map { getRuneByName(it) }.toMutableList()
            player.gold = serializablePlayer.gold

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
    val temporaryDeck: List<String>,
    val weapon: String,
    val runesActive: List<String>,
    val maxRunes: Int,
    val inventoryWeapons: List<String>,
    val inventoryRunes: List<String>,
    val gold: Int
)

fun getCardByName(name: String): Card {
    return cards[name] ?: Card(name, { 0 }, 0, "", 0, 0, "", "", 0, true)
}

fun getWeaponByName(name: String): Weapon {
    return WeaponsList[name] ?: Weapon(name, { _: Player -> }, { _: Player -> }, 0, mutableStateOf(R.drawable.empty_slot), "","empty")
}

fun getRuneByName(name: String): Rune {
    return RunesList[name] ?: Rune(name, {}, {}, mutableStateOf(R.drawable.empty_slot), "",false)
}

val player = Player("Gracz",1,20,30,10,10)