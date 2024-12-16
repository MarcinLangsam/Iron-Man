package com.example.ironman

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ModifierScreen(onDispose: () -> Unit) {
    val playerModifiers = remember { player.mainDeckModifiers }
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

    Box(
        Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFACD)),
    ) {
        FlowRow(
            modifier = Modifier
                .verticalScroll(verticalScrollState)
                .padding(8.dp),

            ) {
            for (index in playerModifiers.indices) {
                ModiferRecord(
                    playerModifiers[index],
                    index
                )
            }
        }
    }
}

@Composable
fun ModiferRecord(modfier: com.example.ironman.Modifier, index: Int) {
    var isGrayedOut = remember { mutableStateOf(modfier.isActive) }
    val cardSprite = modfier.spriteHold
    val cardDescription = modfier.descriptionSkill

    Box(
        modifier = Modifier
            .height(230.dp)
            .width(100.dp)
            .padding(3.dp)
            .clickable { isGrayedOut.value = !isGrayedOut.value; player.mainDeck[index].isActive = !player.mainDeck[index].isActive }
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
