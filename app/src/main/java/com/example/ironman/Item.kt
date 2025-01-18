package com.example.ironman

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Item(
    var name: String,
    @Transient var equipEffect: (Player) -> Unit = {},
    @Transient var deequipEffect: (Player) -> Unit = {},
    var runeSlot: Int,
    var sprite: MutableState<Int>,
    var description: String,
    var type: String
)


var ItemsList = mutableMapOf<String, Item>(
    "Brak" to Item("Brak", {player.weaponDamage+=0;player.updateStats()},{player.weaponDamage-=0;player.updateStats()},0, mutableStateOf(R.drawable.empty_slot),"","empty"),

    //Weapons
    "Miecz Z Brazu" to Item("Miecz Z Brazu", {player.weaponDamage+=1;player.updateStats()},{player.weaponDamage-=1;player.updateStats()},0,mutableStateOf(R.drawable.miecz_z_brazu),"Miecz Z Brazu | BROŃ\nObrażenia +1\nSloty na runy: 0","weapon"),
    "Stalowy Miecz" to Item("Stalowy Miecz", {player.weaponDamage+=2;player.updateStats()},{player.weaponDamage-=2;player.updateStats()},1,mutableStateOf(R.drawable.stalowy_miecz),"Stalowy Miecz\nObrażenia +2\nSloty na runy: 1","weapon"),
    "Miecz Półtoraręczny" to Item("Miecz Półtoraręczny", {player.weaponDamage+=3;player.updateStats()},{player.weaponDamage-=3;player.updateStats()},2,mutableStateOf(R.drawable.miecz_poltorareczny),"Miecz Półtoraręczny\nObrażenia +3\nSloty na runy: 2","weapon"),
    "Miecz Rycerski" to Item("Miecz Rycerski", {player.weaponDamage+=5;player.VIT+=1;player.updateStats()},{player.weaponDamage-=5;player.VIT-=1;player.updateStats()},0,mutableStateOf(R.drawable.miecz_rycerski),"Miecz Rycerski\nObrażenia +5\nVIT +1\nSloty na runy: 0","weapon"),
    "Gladius" to Item("Gladius", {player.weaponDamage+=4;player.INT+=1;player.DEX+=1;player.updateStats()},{player.weaponDamage-=4;player.INT-=1;player.DEX-=1;player.updateStats()},3,mutableStateOf(R.drawable.gladius),"Gladius\nObrażenia +4\n+1 DEX\nINT +1\nSloty na runy: 3","weapon"),
    "Siewca Śmierci" to Item("Siewca Smierci", {player.weaponDamage+=10;player.AP_recovery+=1;player.updateStats()},{player.weaponDamage-=10;player.AP_recovery-=1;player.updateStats()}, 5,mutableStateOf(R.drawable.siewca_smierci),"Siewca Śmierci\nObrażenia +10\n+1 Regeneracja AP\nSloty na runy: 5","weapon"),
    "Rytualny Sztylet" to Item("Rytualny Sztylet", {player.weaponDamage+=2;player.DEX+=3;player.updateStats()},{player.weaponDamage-=2;player.DEX-=3;player.updateStats()}, 2,mutableStateOf(R.drawable.rytualny_sztylet),"Rytualny Sztylet\nObrażenia +10\nDEX +3\n Sloty na runy: 2","weapon"),
    "Topór Rzeźnika" to Item("Topór Rzeźnika", {player.weaponDamage+=6;player.HPbonus+=1;player.updateStats()},{player.weaponDamage-=6;player.HPbonus-=1;player.updateStats()},1,mutableStateOf(R.drawable.dwureczny_topor_rzeznika),"Topór Rzeźnika\nObrażenia _6\n+1 HP\nSloty na runy: 1","weapon"),
    "Młot Bojowy" to Item("Mlot Bojowy", {player.weaponDamage+=7;player.updateStats()},{player.weaponDamage-=7;player.updateStats()},3,mutableStateOf(R.drawable.mlot_bojowy),"Młot Bojowy\nObrażenia +7\nSloty na runy: 3","weapon"),
    "Maczuga Żołnierska" to Item("Maczuga Zolneirska", {player.weaponDamage+=4;player.INT+=2;player.updateStats()},{player.weaponDamage-=4;player.INT-=2;player.updateStats()},2,mutableStateOf(R.drawable.maczuga_zolnierska),"Maczuga Żołnierska\nObrażenia +4\n+2 INT\nSloty na runy: 2","weapon"),
    "Topor Wojownika" to Item("Topór Wojownika", {player.weaponDamage+=6;player.INT+=2;player.DEX+=2;player.updateStats()},{player.weaponDamage-=6;player.INT-=2;player.DEX-=2;player.updateStats()},3,mutableStateOf(R.drawable.topor_wojownika),"Topór Wojownika\nObrażenia +6\n+2 DEX\n+2 INT\nSloty na runy: 3","weapon"),
    "Pika" to Item("Pika", {player.weaponDamage+=2;player.DEX+=2;player.updateStats()},{player.weaponDamage+=2;player.DEX-=2;player.updateStats()},1,mutableStateOf(R.drawable.pika),"Pika\nObrażenia +2\n+2 DEX\nSloty na runy: 1","weapon"),
    "Włócznia Strażnicza" to Item("Włócznia Strażnicza", {player.weaponDamage+=5;player.DEX+=3;player.updateStats()},{player.weaponDamage-=5;player.DEX-=3;player.updateStats()},2,mutableStateOf(R.drawable.wlocznia_straznicza),"Włócznia Strażnicza\nObrażenia + 5\n+2 DEX\nSloty na runy: 2","weapon"),
    "Kostur Maga" to Item("Kostur maga", {player.INT+=2;player.updateStats()},{player.INT-=2;player.updateStats()},3,mutableStateOf(R.drawable.kostur_maga),"Kostur Maga\nObrażenia +0\n+2 INT\nSloty na runt: 3","weapon"),
    "Spaczony Kostur" to Item("Spaczony Kostur", {player.INT+=4;player.updateStats()},{player.INT-=4;player.updateStats()},4,mutableStateOf(R.drawable.grzech_kaplana),"Spaczony Kostur\nObrażenia +0\n+4 INT\nSloty na runt: 4","weapon"),

    //Off Hands
    "Drewniana Tarcza" to Item("Drewniana Tarcza", {player.HPbonus+=1;player.updateStats()},{player.HPbonus-=1;player.updateStats()},0,mutableStateOf(R.drawable.drewniana_tarcza),"Drewniana Tarcza | DRUGA RĘKA\n+1 HP","off_hand"),
    "Stalowa Tarcza" to Item("Stalowa Tarcza", {player.HPbonus+=2;player.updateStats()},{player.HPbonus-=2;player.updateStats()},0,mutableStateOf(R.drawable.stalowa_tarcza),"Stalowa Tarcza | DRUGA RĘKA\n+2 HP","off_hand"),
    "Zapomniana Tarcza" to Item("Zapomniana Tarcza", {player.VIT+=3;player.AP_recovery+=1;player.updateStats()},{player.VIT-=3;player.AP_recovery-=1;player.updateStats()},0,mutableStateOf(R.drawable.zapomniana_tarcza),"Zapomniana Tarcza | DRUGA RĘKA\n+3 VIT\n+1 Regeneracja AP","off_hand"),
    "Magiczna Księga" to Item("Magiczna Księga", {player.INT+=1;player.updateStats()},{player.INT-=1;player.updateStats()},0,mutableStateOf(R.drawable.magicza_ksiega),"Magiczna Księga | DRUGA RĘKA\n+1 INT","off_hand"),
    "Podstępny Majcher" to Item("Podstępny Majcher", {player.DEX+=1;player.updateStats()},{player.DEX-=1;player.updateStats()},0,mutableStateOf(R.drawable.podstepny_majcher),"Podstępny Majcher\n+1 DEX","off_hand"),

    //Helmets
    "Stalowy Hełm" to Item("Stalowy Hełm", {player.VIT+=1;player.updateStats()},{player.VIT-=1;player.updateStats()},0,mutableStateOf(R.drawable.helm),"Stalowy Hełm | GŁOWA\n+1 VIT","helmet"),
    "Hełm Szału" to Item("Hełm Szału", {player.STR+=2;player.updateStats()},{player.STR-=2;player.updateStats()},0,mutableStateOf(R.drawable.helm_szalu),"Hełm Szału | GŁOWA\n+2 STR","helmet"),
    "Kaptur" to Item("Kaptur", {player.DEX+=1;player.updateStats()},{player.DEX-=1;player.updateStats()},0,mutableStateOf(R.drawable.kaptur),"Kaptur | GŁOWA\n+1 DEX","helmet"),
    "Runiczny Kaptur" to Item("Runiczny Kaptur", {player.INT+=1;player.updateStats()},{player.INT-=1;player.updateStats()},0,mutableStateOf(R.drawable.runiczny_kaptur),"Runiczny Kaptur | GŁOWA\n+1 INT","helmet"),
    //Armors
    "Skórzany Pancerz" to Item("Skórzany Pancerz", {player.HPbonus+=1;player.updateStats()},{player.HPbonus-=1;player.updateStats()},0,mutableStateOf(R.drawable.skorzany_pancerz),"Skórzany Pancerz | KLATKA\n+1 HP","armor"),
    "Pikowany Pancerz" to Item("Pikowany Pancerz", {player.HPbonus+=2;player.updateStats()},{player.HPbonus-=2;player.updateStats()},0,mutableStateOf(R.drawable.pikowany_pancerz),"Pikowany Pancerz | KLATKA\n+2 HP","armor"),
    "Przyszywanica" to Item("Przyszywanica", {player.HPbonus+=1;player.DEX+=2;player.updateStats()},{player.HPbonus-=1;player.DEX-=2;player.updateStats()},0,mutableStateOf(R.drawable.przyszywanica),"Przyszywanica | KLATKA\n+1 HP\n+2 DEX","armor"),
    "Kolczuga" to Item("Kolczuga", {player.HPbonus+=3;player.STR+=1;player.updateStats()},{player.HPbonus-=3;player.STR-=1;player.updateStats()},0,mutableStateOf(R.drawable.kolczuga),"Kolczuga | KLATKA\n+3 HP\n+1 STR","armor"),
    "Szata Maga" to Item("Szata Maga", {player.APbonus+=1;player.INT+=2;player.updateStats()},{player.APbonus-=1;player.INT-=2;player.updateStats()},0,mutableStateOf(R.drawable.szata_maga),"Szata Maga | KLATKA\n+1 AP\n+2 INT","armor"),

    //Pants
    "Skórzane Spodnie" to Item("Skórzane Spodnie", {player.VIT+=1;player.updateStats()},{player.VIT-=1;player.updateStats()},0,mutableStateOf(R.drawable.skorzane_spodnie),"Skórzane Spodnie | NOGI\n+1 VIT","pants"),
    "Pikowane Spodnie" to Item("Pikowane Spodnie", {player.VIT+=2;player.updateStats()},{player.VIT-=2;player.updateStats()},0,mutableStateOf(R.drawable.pikowane_spodnie),"Pikowane Spodnie | NOGI\n+2 VIT","pants"),
    "Przeszywane Spodnie" to Item("Przeszywane Spodnie", {player.AP_recovery+=1;player.updateStats()},{player.AP_recovery-=1;player.updateStats()},0,mutableStateOf(R.drawable.przeszywane_spodnie),"Przeszywane Spodnie | NOGI\n+1 Regeneracji AP","pants"),
    "Spodnie Kolcze" to Item("Spodnie Kolcze", {player.VIT+=1;player.updateStats()},{player.VIT-=1;player.updateStats()},0,mutableStateOf(R.drawable.spodnie_kolcze),"Spodnie Kolcze | NOGI\n+1 VIT","pants"),
    "Szarfa Maga" to Item("Szarfa Maga", {player.INT+=1;player.APbonus+=1;player.updateStats()},{player.INT-=1;player.APbonus-=1;player.updateStats()},0,mutableStateOf(R.drawable.szarfa_maga),"Szarfa Maga | NOGI\n+1 AP\n+1 INT","pants"),
    "Spodnie Z Torbami" to Item("Spodnie Z Torbami", {player.cardsSlots+=1;player.updateStats()},{player.cardsSlots-=1;player.updateStats()},0,mutableStateOf(R.drawable.spodnie_z_torbami),"Spodnie Z Torbami | NOGI\n+1 Przygotowanych Kart","pants"),

    //shoes
    "Obtarte Buty" to Item("Obtarte Buty", {player.APbonus+=1;player.updateStats()},{player.APbonus-=1;player.updateStats()},0,mutableStateOf(R.drawable.buty),"Obtarte Buty | STOPY\n+1 AP","shoes"),
    "Wysokie Buty" to Item("Wysokie Buty", {player.APbonus+=1;player.HPbonus+=1;player.updateStats()},{player.APbonus-=1;player.HPbonus-=1;player.updateStats()},0,mutableStateOf(R.drawable.wysokie_buty),"Wysokie Buty | STOPY\n+1 AP\n+1 HP","shoes"),
    "Stalowe Buty" to Item("Stalowe Buty", {player.HPbonus+=2;player.VIT+=1;player.updateStats()},{player.HPbonus-=2;player.VIT-=1;player.updateStats()},0,mutableStateOf(R.drawable.stalowe_buty),"Stalowe Buty | STOPY\n+1 HP\n1+ VIT","shoes"),
    "Hermeski" to Item("Obtarte Buty", {player.APbonus+=2;player.AP_recovery+=1;player.updateStats()},{player.APbonus-=2;player.AP_recovery-=1;player.updateStats()},0,mutableStateOf(R.drawable.hermeski),"Hermeski | STOPY\n+2 AP\n+1 Regeneracji AP","shoes"),

    //Necklase
    "Amulet Precyzji" to Item("Amulet Precyzji", {player.DEX+=2;player.updateStats()},{player.DEX-=2;player.updateStats()},0,mutableStateOf(R.drawable.amulet_precyzji),"Amulet Precyzji | SZYJA\n+2 DEX","necklace"),
    "Amulet Magi" to Item("Amulet Precyzji", {player.INT+=2;player.updateStats()},{player.INT-=2;player.updateStats()},0,mutableStateOf(R.drawable.amulet_magi),"Amulet Magi | SZYJA\n+2 INT","necklace"),

    //Rings
    "Srebrny Pierścień" to Item("Srebrny Pierścień", {player.HPbonus+=1;player.updateStats()},{player.HPbonus-=1;player.updateStats()},0,mutableStateOf(R.drawable.srebrny_pierscien),"Srebrny Pierścień | PALEC\n+1 HP","ring"),
    "Złoty Pierścień" to Item("Złoty Pierścień", {player.APbonus+=1;player.updateStats()},{player.APbonus-=1;player.updateStats()},0,mutableStateOf(R.drawable.zloty_pierscien),"Złoty Pierścień | PALEC\n+1 AP","ring"),
    "Pierścien Zdrowia" to Item("Pierścien Zdrowia", {player.VIT+=1;player.updateStats()},{player.VIT-=1;player.updateStats()},0,mutableStateOf(R.drawable.pierscien_zdrowia),"Pierścień Zdrowia | PALEC\n+1 VIT","ring"),
    "Pierścien Siły" to Item("Pierścien Siły", {player.STR+=1;player.updateStats()},{player.STR-=1;player.updateStats()},0,mutableStateOf(R.drawable.pierscien_sily),"Pierścień Siły | PALEC\n+1 STR","ring"),
    "Pierścien Zręczności" to Item("Pierścien Zręczności", {player.DEX+=1;player.updateStats()},{player.DEX-=1;player.updateStats()},0,mutableStateOf(R.drawable.pierscien_zrecznosci),"Pierścień Zręczności | PALEC\n+1 DEX","ring"),
    "Pierścien Inteligencji" to Item("Pierścien Inteligencji", {player.INT+=1;player.updateStats()},{player.INT-=1;player.updateStats()},0,mutableStateOf(R.drawable.pierscien_inteligencji),"Pierścień Inteligencji | PALEC\n+1 INT","ring"),
    "Większy Pierścien Zdrowia" to Item("Większy Pierścien Zdrowia", {player.VIT+=2;player.updateStats()},{player.VIT-=2;player.updateStats()},0,mutableStateOf(R.drawable.wiekszy_pierscien_zdrowia),"Większy Pierścień Zdrowia | PALEC\n+2 VIT","ring"),
    "Większy Pierścien Siły" to Item("Większy Pierścien Siły", {player.STR+=2;player.updateStats()},{player.STR-=2;player.updateStats()},0,mutableStateOf(R.drawable.wiekszy_pierscien_sily),"Większy Pierścień Siły | PALEC\n+2 STR","ring"),
    "Większy Pierścien Zręczności" to Item("Większy Pierścien Zręczności", {player.DEX+=2;player.updateStats()},{player.DEX-=2;player.updateStats()},0,mutableStateOf(R.drawable.wiekszy_pierscien_zrecznosci),"Większy Pierścień Zręczności | PALEC\n+2 DEX","ring"),
    "Większy Pierścien Inteligencji" to Item("Większy Pierścien Inteligencji", {player.INT+=2;player.updateStats()},{player.INT-=2;player.updateStats()},0,mutableStateOf(R.drawable.wiekszy_pierscien_inteligencji),"Większy Pierścień Inteligencji | PALEC\n+2 INT","ring"),
    "Pierścien Potęgi" to Item("Pierścien Potęgi", {player.VIT+=1;player.APbonus+=1;player.STR+=1;player.DEX+=1;player.INT+=1;player.updateStats()},{player.VIT-=1;player.APbonus-=1;player.STR-=1;player.DEX-=1;player.INT-=1;player.updateStats()},0,mutableStateOf(R.drawable.pierscien_potegi),"Pierścień Potęgi | PALEC\n+1 AP\n+1 STR\n+1 VIT\n+1 DEX\n+1 INT","ring"),

    )