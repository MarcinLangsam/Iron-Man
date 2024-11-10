package com.example.ironman

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Weapon(
    var name: String,
    @Transient var equipEffect: (Player) -> Unit = {},
    @Transient var deequipEffect: (Player) -> Unit = {},
    var runeSlot: Int,
    var sprite: MutableState<Int>,
    var description: String,
    var type: String
)


var WeaponsList = mutableMapOf<String, Weapon>(
    "Brak" to Weapon("Brak", {player.weaponDamage+=0;player.updateStats()},{player.weaponDamage-=0;player.updateStats()},0, mutableStateOf(R.drawable.empty_slot),"","empty"),
    "Miecz z brazu" to Weapon("Miecz z brazu", {player.weaponDamage+=5;player.updateStats()},{player.weaponDamage-=5;player.updateStats()},1,mutableStateOf(R.drawable.miecz_z_brazu),"Miecz z brazu\nobrażenia +5\nSloty na runy: 1","weapon"),
    "Stalowy miecz" to Weapon("Stalowy miecz", {player.weaponDamage+=8;player.updateStats()},{player.weaponDamage-=8;player.updateStats()},2,mutableStateOf(R.drawable.stalowy_miecz),"Stalowy miecz\nobrażenia +8\nSloty na runy: 2","weapon"),
    "Miecz Poltorareczny" to Weapon("Miecz Poltorareczny", {player.weaponDamage+=10;player.DEX+=5;player.updateStats()},{player.weaponDamage-=10;player.DEX+=5;player.updateStats()},3,mutableStateOf(R.drawable.miecz_poltorareczny),"Miecz Poltorareczny\nobrażenia +10\nDEX +5\nSloty na runy: 3","weapon"),
    "Miecz Rycerski" to Weapon("Miecz Rycerski", {player.weaponDamage+=23;player.VIT+=2;player.updateStats()},{player.weaponDamage-=23;player.VIT-=2;player.updateStats()},0,mutableStateOf(R.drawable.miecz_rycerski),"Miecz Rycerski\nobrażenia +23\nVIT +2\nSloty na runy: 0","weapon"),
    "Gladius" to Weapon("Gladius", {player.weaponDamage+=13;player.INT+=4;player.updateStats()},{player.weaponDamage-=13;player.INT-=4;player.updateStats()},4,mutableStateOf(R.drawable.miecz_rycerski),"Gladius\nobrażenia +13\nINT +4\nSloty na runy: 5","weapon"),
    "Siewca Smierci" to Weapon("Siewca Smierci", {player.weaponDamage+=30;player.STR+=3;player.DEX+=3;player.INT+=3;player.VIT+=3;player.updateStats()},{player.weaponDamage-=30;player.STR-=3;player.DEX-=3;player.INT-=3;player.VIT-=3;player.updateStats()},
        5,mutableStateOf(R.drawable.siewca_smierci),"Siewca Smierci\nobrażenia +30\nSTR +3\nDEX +3\nINT +3\nVIT +3\nSloty na runy: 6","weapon"),
    "Rytualny sztylet" to Weapon("Rytualny sztylet", {player.weaponDamage+=10;player.DEX+=5;player.MAX_AP.value+=1;player.updateStats()},{player.weaponDamage-=10;player.DEX-=5;player.MAX_AP.value-=1;player.updateStats()},
        4,mutableStateOf(R.drawable.rytualny_sztylet),"Rytualny sztylet\n+10 obrażeń\nDEX +5\nAP +1\n Sloty na runy: 5","weapon"),
    "Topor Rzeznika" to Weapon("Topor Rzeznika", {player.weaponDamage+=20;player.VIT+=1;player.updateStats()},{player.weaponDamage-=20;player.VIT-=1;player.updateStats()},2,mutableStateOf(R.drawable.dwureczny_topor_rzeznika),"Topor Rzeznika\n+20 obrażeń\nVIT +1\nSloty na runy: 2","weapon"),
    "Mlot Bojowy" to Weapon("Mlot Bojowy", {player.weaponDamage+=25;player.VIT+=2;player.STR+=2;player.updateStats()},{player.weaponDamage-=25;player.VIT-=2;player.STR-=2;player.updateStats()},3,mutableStateOf(R.drawable.mlot_bojowy),"Mlot Bojowy\n+25 obrażeń\nVIT +2\nSTR +2\nSloty na runy: 3","weapon"),
    "Maczuga Zolneirska" to Weapon("Maczuga Zolneirska", {player.weaponDamage+=22;player.VIT+=6;player.updateStats()},{player.weaponDamage-=22;player.VIT-=6;player.updateStats()},4,mutableStateOf(R.drawable.maczuga_zolnierska),"Maczuga Zolneirska\n+22 obrażeń\nVIT +6\nSloty na runy: 4","weapon"),
    "Topor wojownika" to Weapon("Topor wojownika", {player.weaponDamage+=30;player.VIT+=2;player.STR+=4;player.MAX_AP.value+=1;player.updateStats()},{player.weaponDamage-=30;player.VIT-=2;player.STR-=4;player.MAX_AP.value-=1;player.updateStats()},4,mutableStateOf(R.drawable.topor_wojownika),"Topor wojownika\n+30 obrażeń\nVIT +2\nSTR +4\nAP +1\nSloty na runy: 4","weapon"),
    "Pika" to Weapon("Pika", {player.weaponDamage+=7;player.updateStats()},{player.weaponDamage-=7;player.updateStats()},3,mutableStateOf(R.drawable.pika),"Pika\n+7 obrażeń\nSloty na runy: 3","weapon"),
    "Wlucznia straznicza" to Weapon("Wlucznia straznicza", {player.weaponDamage+=13;player.INT+=3;player.DEX+=1;player.updateStats()},{player.weaponDamage-=13;player.INT-=3;player.DEX-=1;player.updateStats()},3,mutableStateOf(R.drawable.wlocznia_straznicza),"Wlucznia straznicza\n+13 obrażeń\nINT +3\nDEX +1\nSloty na runy: 3","weapon"),
    "Kostur maga" to Weapon("Kostur maga", {player.weaponDamage+=8;player.INT+=5;player.MAX_AP.value+=1;player.updateStats()},{player.weaponDamage-=8;player.INT-=5;player.MAX_AP.value-=1;player.updateStats()},4,mutableStateOf(R.drawable.kostur_maga),"Kostur Maga\n+8 obrażeń\nINT +5\nAP +1\nSloty na runt: 4","weapon"),
    "Grzech Kaplana" to Weapon("Grzech Kaplana", {player.weaponDamage+=13;player.INT+=10;player.VIT+=2;player.MAX_AP.value+=1;player.updateStats()},{player.weaponDamage-=13;player.INT-=10;player.VIT-=2;player.MAX_AP.value-=1;player.updateStats()},4,mutableStateOf(R.drawable.grzech_kaplana),"Grzech Kaplana\n+13 obrażeń\nINT +10\nVIT +2\nAP +1\nSloty na runt: 7","weapon"),
    "Skorzany Pancerz" to Weapon("Skorzany Pancerz", {player.VIT+=5;player.updateStats()},{player.VIT-=5;player.updateStats()},0,mutableStateOf(R.drawable.skorzany_pancerz),"Skorzany Pancerz\n+5 VIT","armor"),
    "Srebrny Pierscien" to Weapon("Srebrny Pierscien", {player.VIT+=1;player.updateStats()},{player.VIT-=5;player.updateStats()},0,mutableStateOf(R.drawable.srebrny_pierscien),"Srebrny Pierscien\n+1 VIT","ring"),
    "Amulet Precyzji" to Weapon("Amulet Precyzji", {player.DEX+=3;player.updateStats()},{player.DEX-=3;player.updateStats()},0,mutableStateOf(R.drawable.amulet_precyzji),"Amulet Precyzji\n+3 DEX","necklace"),
    "Drewniana Tarcza" to Weapon("Drewniana Tarcza", {player.VIT+=3;player.updateStats()},{player.VIT-=3;player.updateStats()},0,mutableStateOf(R.drawable.drewniana_tarcza),"Drewniana Tarcza\n+3 VIT","off_hand"),
    "Helm" to Weapon("Helm", {player.VIT+=1;player.updateStats()},{player.VIT-=1;player.updateStats()},0,mutableStateOf(R.drawable.helm),"Helm\n+1 VIT","helmet"),
    "Buty" to Weapon("Buty", {player.VIT+=1;player.updateStats()},{player.VIT-=1;player.updateStats()},0,mutableStateOf(R.drawable.buty),"Buty\n+1 VIT","shoes"),
    )