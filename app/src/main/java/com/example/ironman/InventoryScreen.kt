package com.example.ironman

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
import kotlinx.coroutines.launch


var activeRunesIndex = mutableListOf<Int>()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemSlotWeapon(
    item: Weapon,
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
                .height(150.dp)
                .width(150.dp)
                .padding(10.dp)
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
                    modifier = Modifier.size(150.dp)
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
                .height(150.dp)
                .width(150.dp)
                .padding(10.dp)
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
                        .size(150.dp)
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
fun ListOfWeapons(items: MutableList<Weapon>){
    LazyColumn{
        items(items.size){
            val item by remember {
                mutableStateOf(items[it])
            }
                ItemSlotWeapon(item = item)
        }
    }
}


@Composable
fun ListOfRunes(items: MutableList<Rune>){
    LazyColumn{
        items(items.size){
            val item by remember {
                mutableStateOf(items[it])
            }
            ItemSlotRune(item = item,it)
        }
    }
}

@Composable
fun InventoryScreen() {
    val maxRunes = remember { player.maxRunes }
    var currentCategory by remember { mutableStateOf("Bronie") }
    var currentWeapon by remember { player.weapon.sprite }
    var runesSlots = remember { player.runesActive }
    val invWeapon = remember { player.inventoryWeapons }
    var invRune = remember { player.inventoryRunes }

    LaunchedEffect(player.inventoryRunes) {
        invRune = player.inventoryRunes
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFACD))
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Row(Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(80.dp))
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(Color.Gray),
                ) {
                    LaunchedEffect(player.weapon.sprite) {
                        currentWeapon = player.weapon.sprite.value
                    }
                    Image(
                        painter = painterResource(id = currentWeapon),
                        contentDescription = "Image",
                        modifier = Modifier.size(150.dp)
                    )

                    repeat(maxRunes.value) { index ->
                        Box(
                            modifier = Modifier
                                .size(35.dp)
                                .offset(
                                    getOffsetForSquareBetweenPluses_x(index),
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
                                modifier = Modifier.size(150.dp)
                            )
                        }
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

private fun getOffsetForSquareBetweenPluses_x(index: Int): Dp {
    val offset = 60.dp
    return when (index) {
        0 -> 25.dp
        1 -> offset+30.dp
        2 -> offset+50.dp
        3 -> offset+30.dp
        4 -> 25.dp
        5 -> (-40).dp
        6 -> -offset-5.dp
        7 -> (-45).dp
        else -> 0.dp
    }
}

private fun getOffsetForSquareBetweenPluses_y(index: Int): Dp {
    val offset = 60.dp
    return when (index) {
        0 -> -offset
        1 -> -offset+30.dp
        2 ->  30.dp
        3 -> offset+30.dp
        4 -> offset+45.dp
        5 -> offset+30.dp
        6 -> 30.dp
        7 -> -offset+30.dp
        else -> 0.dp
    }
}