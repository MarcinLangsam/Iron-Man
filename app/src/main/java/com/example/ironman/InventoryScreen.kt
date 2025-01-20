package com.example.ironman

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch


var activeRunesIndex = mutableListOf<Int>()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemSlotWeapon(
    item: Item,
) {
    val tooltipState = rememberTooltipState(isPersistent = true)
    val scope = rememberCoroutineScope()
    TooltipBox(
        positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider(),
        tooltip = {
            RichTooltip(
                action = {
                    TextButton(
                        onClick = { scope.launch { tooltipState.dismiss() } }
                    ) { Text(text = "Zamknij") }
                }
            ) {
                Text(text = item.description)
            }
        },
        state = tooltipState
    ) {
        Button(
            onClick = { player.equipWeapon(item) },
            modifier = Modifier
                .height(100.dp)
                .width(100.dp)
                .background(
                    color = Color.Transparent
                ),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = item.sprite.value),
                    contentDescription = "Image",
                    modifier = Modifier.size(100.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemSlotRune(
    item: Rune,
    i: Int,
) {
    val isGrayedOut = remember { mutableStateOf(player.inventoryRunes[i].isActive) }
    val tooltipState = rememberTooltipState(isPersistent = true)
    val scope = rememberCoroutineScope()
    TooltipBox(
        positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider(),
        tooltip = {
            RichTooltip(
                action = {
                    TextButton(
                        onClick = { scope.launch { tooltipState.dismiss() } }
                    ) { Text(text = "Zamknij") }
                }
            ) {
                Text(text = item.description)
            }
        },
        state = tooltipState
    ) {
        Button(
            onClick = { if(!player.inventoryRunes[i].isActive) {player.equipRune(item); player.inventoryRunes[i].isActive = !player.inventoryRunes[i].isActive; isGrayedOut.value = !isGrayedOut.value; activeRunesIndex.add(i)} },
            modifier = Modifier
                .height(100.dp)
                .width(100.dp)
                .background(
                    color = Color.Transparent
                ),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = item.sprite.value),
                    contentDescription = "Image",
                    modifier = Modifier
                        .size(100.dp)
                )
                if (player.inventoryRunes[i].isActive) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0x8000FF00))
                    )
                }
            }
        }
    }
}

@Composable
fun ListOfWeapons(items: MutableList<Item>){
    LazyVerticalGrid(
        columns = GridCells.Fixed(4)
    ) {
        items(items.size) { index ->
            val item by remember {
                mutableStateOf(items[index])
            }
            ItemSlotWeapon(item = item)
        }
    }
}


@Composable
fun ListOfRunes(items: MutableList<Rune>){
    LazyVerticalGrid(
        columns = GridCells.Fixed(4)
    ){
        items(items.size){
            val item by remember {
                mutableStateOf(items[it])
            }
            ItemSlotRune(item = item,it)
        }
    }
}

@Composable
fun ListOfStats(stat: List<Any>)
{
    LazyColumn(
        modifier = Modifier.padding(16.dp).padding(bottom = 50.dp)
    ) {
        itemsIndexed(stat) { _, info ->
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                text = info.toString(),
                fontSize = 16.sp,
                color = Color.White
            )
        }
    }

    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun InventoryScreen() {
    val maxRunes = remember { player.maxRunes }
    var currentCategory by remember { mutableStateOf("Bronie") }

    var currentWeapon by remember { player.item.sprite }
    var currentOff_hand by remember { player.off_hand.sprite }
    var currentArmor by remember { player.armor.sprite }
    var currentNecklace by remember { player.necklace.sprite }
    var currentRing by remember { player.ring.sprite }
    var currentHelmet by remember { player.helmet.sprite }
    var currentShoes by remember { player.shoes.sprite }
    var currentPants by remember { player.pants.sprite }

    var runesSlots = remember { player.runesActive }
    val invWeapon = remember { player.inventoryItems }
    var invRune = remember { player.inventoryRunes }

    val playerInfo by remember {
        derivedStateOf {
            listOf(
                "Rasa: " + player.rase,
                player.name,
                "Poziom: " + player.lv.toString(),
                "EXP: " + player.EXP.value.toString() + "/" + player.EXPtoLv.toString(),
                "HP: " + player.HP.value.toString() + "/" + player.MAX_HP.value.toString(),
                "AP: " + player.MAX_AP.value.toString(),
                "Odzyskiwanie AP: " + player.AP_recovery.toString(),
                "Siła: " + player.STR.toString(),
                "Witalność: " + player.VIT.toString(),
                "Zręczność: " + player.DEX.toString(),
                "Inteligencja: " + player.INT.toString(),
                "Obrażenia Broni: " + player.weaponDamage.toString(),
                "Maksymalna Ilość Run: " + player.maxRunes.value.toString(),
                "Złoto: " + player.gold.toString()
            )
        }
    }

    LaunchedEffect(player.inventoryRunes) {
        invRune = player.inventoryRunes
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF333333))
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Row(Modifier.fillMaxWidth()) {

            Column(
                modifier = Modifier.weight(0.5f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                ListOfStats(stat = playerInfo)
            }

            Column(
                modifier = Modifier.weight(0.4f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))


                Box(modifier = Modifier
                    .size(40.dp)
                    .background(Color.Gray)) {
                    LaunchedEffect(player.helmet.sprite) {currentHelmet = player.helmet.sprite.value }
                    Image(
                        painter = painterResource(id = currentHelmet),
                        contentDescription = "Image",
                        modifier = Modifier.size(40.dp)
                    )

                    Box(modifier = Modifier
                        .size(40.dp)
                        .background(Color.Gray)) {
                        LaunchedEffect(player.necklace.sprite) {currentNecklace = player.necklace.sprite.value }
                        Image(
                            painter = painterResource(id = currentNecklace),
                            contentDescription = "Image",
                            modifier = Modifier
                                .size(40.dp)
                                .offset(0.dp, 45.dp)
                        )
                    }

                    Box(modifier = Modifier
                        .size(40.dp)
                        .background(Color.Gray)) {
                        LaunchedEffect(player.armor.sprite) {currentArmor = player.armor.sprite.value }
                        Image(
                            painter = painterResource(id = currentArmor),
                            contentDescription = "Image",
                            modifier = Modifier
                                .size(40.dp)
                                .offset(0.dp, 90.dp)
                        )
                    }

                    Box(modifier = Modifier
                        .size(40.dp)
                        .background(Color.Gray)) {
                        LaunchedEffect(player.pants.sprite) {currentPants = player.pants.sprite.value }
                        Image(
                            painter = painterResource(id = currentPants),
                            contentDescription = "Image",
                            modifier = Modifier
                                .size(40.dp)
                                .offset(0.dp, 135.dp)
                        )
                    }

                    Box(modifier = Modifier
                        .size(40.dp)
                        .background(Color.Gray)) {
                        LaunchedEffect(player.shoes.sprite) {currentShoes = player.shoes.sprite.value }
                        Image(
                            painter = painterResource(id = currentShoes),
                            contentDescription = "Image",
                            modifier = Modifier
                                .size(40.dp)
                                .offset(0.dp, 180.dp)
                        )
                    }

                    Box(modifier = Modifier
                        .size(40.dp)
                        .background(Color.Gray)) {
                        LaunchedEffect(player.item.sprite) {
                            currentWeapon = player.item.sprite.value
                        }
                        Image(
                            painter = painterResource(id = currentWeapon),
                            contentDescription = "Image",
                            modifier = Modifier
                                .size(40.dp)
                                .offset((-45).dp, 105.dp)
                        )

                        repeat(maxRunes.value) { index ->
                            Box(
                                modifier = Modifier
                                    .size(25.dp)
                                    .offset(
                                        (-75).dp,
                                        getOffsetForSquareBetweenPluses_y(index)
                                    )
                                    .clickable { player.deequipRune() }
                            ) {
                                LaunchedEffect(player.runesActive) {
                                    runesSlots = player.runesActive
                                }
                                Image(
                                    painter = painterResource(id = runesSlots[index].sprite.value),
                                    contentDescription = "Image",
                                    modifier = Modifier.size(25.dp)
                                )
                            }
                        }
                    }

                    Box(modifier = Modifier
                        .size(40.dp)
                        .background(Color.Gray)) {
                        LaunchedEffect(player.off_hand.sprite) {currentOff_hand = player.off_hand.sprite.value }
                        Image(
                            painter = painterResource(id = currentOff_hand),
                            contentDescription = "Image",
                            modifier = Modifier
                                .size(40.dp)
                                .offset(45.dp, 105.dp)
                        )
                    }

                    Box(modifier = Modifier
                        .size(40.dp)
                        .background(Color.Gray)) {
                        LaunchedEffect(player.ring.sprite) {currentRing = player.ring.sprite.value }
                        Image(
                            painter = painterResource(id = currentRing),
                            contentDescription = "Image",
                            modifier = Modifier
                                .size(40.dp)
                                .offset(45.dp, 150.dp)
                        )
                    }
                }

            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.End
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Button(
                        modifier = Modifier
                            .height(60.dp)
                            .width(100.dp)
                            .padding(10.dp)
                        , onClick = { currentCategory = "Bronie" }) {
                        Text(text = "Bronie")
                    }
                    Button(
                        modifier = Modifier
                            .height(60.dp)
                            .width(100.dp)
                            .padding(10.dp)
                        , onClick = { currentCategory = "Runy" }) {
                        Text(text = "Runy")
                    }

                }
                Spacer(modifier = Modifier.height(15.dp))
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    when (currentCategory) {
                        "Bronie" -> ListOfWeapons(invWeapon)
                        "Runy" -> ListOfRunes(invRune)
                    }
                }
            }
        }
    }
}

private fun getOffsetForSquareBetweenPluses_y(index: Int): Dp {
    //val offset = 25.dp
    return when (index) {
        0 -> 50.dp
        1 -> 77.dp
        2 -> 104.dp
        3 -> 131.dp
        4 -> 158.dp
        else -> 50.dp
    }
}