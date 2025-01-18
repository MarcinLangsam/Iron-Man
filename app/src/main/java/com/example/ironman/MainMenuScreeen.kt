package com.example.ironman

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


import android.content.Context
import androidx.compose.runtime.remember
import java.io.File

@Composable
fun MainMenuScreen(onCharacterCreationScreen: () -> Unit, onMapScreen: () -> Unit , context: Context) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFACD))
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "IRON MAN",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Nowa gra",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clickable { onCharacterCreationScreen() }
                )

                Text(
                    text = "Wczytaj grę",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clickable { player = loadPlayerDataFromFile(context); player.updateStats(); onMapScreen() }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Wyjście",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clickable { android.os.Process.killProcess(android.os.Process.myPid()) }
                )
            }
        }
    }
}
