package com.example.ironman

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.sign

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SkillScreen(onDispose: () -> Unit) {
    val playerCards = remember { player.mainDeck }
    //val cardsOnHand = remember { player.cardsOnHand }

    val horizontalScrollState = rememberScrollState()
    val verticalScrollState = rememberScrollState()

    LaunchedEffect(horizontalScrollState.value, verticalScrollState.value) {
        Log.d("ScrollState", "Horizontal: ${horizontalScrollState.value}, Vertical: ${verticalScrollState.value}")
    }

    DisposableEffect(Unit) {
        onDispose
        onDispose {
            onDispose()
        }
    }
        Row {
            Box(
                Modifier
                    //.fillMaxWidth()
                    .fillMaxSize()
                    .background(Color(0xFFFFFACD)),
            ) {
                Column {
                    FlowRow(
                        modifier = Modifier
                            .verticalScroll(verticalScrollState)
                            .padding(3.dp),

                        ) {
                        for (index in playerCards.indices) {
                            CardRecord(
                                playerCards[index],
                                index,
                                onClick = { addToSlot(playerCards[index]) }
                            )
                        }
                    }

                    Row {
                        repeat(player.cardsSlots) { index ->
                            Box(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                CardSlot() //pierwsza warsta
                                if (index < player.cardsOnHand.size) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                    ) {
                                        CardSlotFull(player.cardsOnHand[index].sprite, onClick = { removeFromSlot() }) //druga warstwa
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
}

fun addToSlot(card: Card)
{
    val existingCard = player.cardsOnHand.find { it.name == card.name }
    if (existingCard == null && player.cardsOnHand.size < player.cardsSlots)
    {
        player.cardsOnHand.add(card)
    }
}


fun removeFromSlot() {
    if (player.cardsOnHand.isNotEmpty())
    {
        player.cardsOnHand.removeAt(player.cardsOnHand.lastIndex)
    }

}

@Composable
fun CardRecord(card: Card, index: Int, onClick: () -> Unit) {
    var isGrayedOut = remember { mutableStateOf(card.isActive) }
    val cardSprite = card.spriteHold
    val cardDescription = card.descriptionSkill

    Box(
        modifier = Modifier
            .height(230.dp)
            .width(100.dp)
            .padding(1.dp)
            //.clickable { isGrayedOut.value = !isGrayedOut.value; player.mainDeck[index].isActive = !player.mainDeck[index].isActive }
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = cardSprite),
            contentDescription = "Image",
            modifier = Modifier
                .height(200.dp)
                .width(100.dp)
                .then(if (!isGrayedOut.value) Modifier.alpha(0.5f) else Modifier.alpha(1f))
        )
        Text(
            text = cardDescription,
            modifier = Modifier
                .offset(10.dp, 120.dp)
                .width(80.dp),
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 12.sp)
        )
    }
}

@Composable
fun CardSlot()
{
    Box(
        modifier = Modifier
            .height(80.dp)
            .width(80.dp)
            .padding(1.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.empty_slot),
            contentDescription = "Image",
            modifier = Modifier
                .height(80.dp)
                .width(80.dp)
        )
    }
}

@Composable
fun CardSlotFull(sprite : Int, onClick: () -> Unit)
{
    Box(
        modifier = Modifier
            .height(80.dp)
            .width(80.dp)
            .padding(1.dp)
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = sprite),
            contentDescription = "Image",
            modifier = Modifier
                .height(80.dp)
                .width(80.dp)
        )
    }
}

