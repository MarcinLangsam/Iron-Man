package com.example.ironman

import android.content.Context
import android.os.Process
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CharacterCreationScreen(onMainMapScreen: () -> Unit, context: Context){

    var currentCategory by remember { mutableStateOf("Rasa") }

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
            Button(modifier = Modifier.width(100.dp).height(100.dp) ,onClick = { currentCategory = "Rasa" }) {
                Text("Rasa")
            }
            Button(modifier = Modifier.width(100.dp).height(100.dp) ,onClick = { currentCategory = "Klasa" }) {
                Text("Klasa")
            }
            Button(modifier = Modifier.width(100.dp).height(100.dp) ,onClick = { currentCategory = "Atrybuty" }) {
                Text("Atrybuty")
            }
            Button(modifier = Modifier.width(100.dp).height(100.dp) ,onClick = { currentCategory = "Umiejetnosci" }) {
                Text("Umiejetnosci")
            }
            Button(modifier = Modifier.width(100.dp).height(100.dp) ,onClick = { currentCategory = "Portret" }) {
                Text("Portret")
            }
        }

        Column(modifier = Modifier
            .fillMaxWidth()
            .height(150.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            when (currentCategory) {

            }

            Button(modifier = Modifier.width(100.dp).height(100.dp) ,onClick = { onMainMapScreen() }) {
                Text("ROZPOCZNIJ GRE")
            }
        }


    }
}

@Composable
fun RaseComponent(){
    
}