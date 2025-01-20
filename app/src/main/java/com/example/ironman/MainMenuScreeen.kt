package com.example.ironman

import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainMenuScreen(onCharacterCreationScreen: () -> Unit, onMapScreen: () -> Unit, context: Context) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.DarkGray, Color.Black)
                )
            )
    ) {
        // Zawartość ekranu
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Nagłówek tytułowy
            Text(
                text = "IRON MAN",
                fontSize = 60.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFFD4AF37), // Złoty odcień
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 48.dp)
            )

            // Przyciski
            MainMenuButton(
                text = "Nowa gra",
                onClick = onCharacterCreationScreen
            )

            Spacer(modifier = Modifier.height(16.dp))

            MainMenuButton(
                text = "Wczytaj grę",
                onClick = {
                    player = loadPlayerDataFromFile(context)
                    player.updateStats()
                    generatedMap.generateMap()
                    onMapScreen()
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            MainMenuButton(
                text = "Wyjście",
                onClick = { android.os.Process.killProcess(android.os.Process.myPid()) }
            )
        }
    }
}

// Funkcja stylizująca przyciski
@Composable
fun MainMenuButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color.Gray, Color.DarkGray)
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}
