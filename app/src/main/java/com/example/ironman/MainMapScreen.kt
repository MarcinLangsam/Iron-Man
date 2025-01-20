package com.example.ironman

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

var dungeonLevel = 1

fun getMapTileIcon(type: String): Int {
    if (type == "#")
        return R.drawable.map_icon
    else if (type.contains("#CE"))
        return R.drawable.map_icon_corridor_exit
    else if (type.contains("#CF"))
        return R.drawable.map_icon_corridor_fight
    else if (type.contains("#C"))
        return R.drawable.map_icon_corridor

    else if (type.contains("#SE"))
        return R.drawable.map_icon_small_exit
    else if (type.contains("#ST"))
        return R.drawable.map_icon_small_tresure
    else if (type.contains("#SF"))
        return R.drawable.map_icon_small_fight
    else if (type.contains("#S"))
        return R.drawable.map_icon_small

    else if (type.contains("#BE"))
        return R.drawable.map_icon_big_exit
    else if (type.contains("#BT"))
        return R.drawable.map_icon_big_tresure
    else if (type.contains("#BF"))
        return R.drawable.map_icon_big_fight
    else if (type.contains("#B"))
        return R.drawable.map_icon_big

    else if (type.contains("#LVE"))
        return R.drawable.map_icon_long_vertical_exit
    else if (type.contains("#LVT"))
        return R.drawable.map_icon_long_vertical_tresure
    else if (type.contains("#LVF"))
        return R.drawable.map_icon_long_vertical_fight
    else if (type.contains("#LV"))
        return R.drawable.map_icon_long_vertical

    else if (type.contains("#LHE"))
        return R.drawable.map_icon_long_horizontal_exit
    else if (type.contains("#LHT"))
        return R.drawable.map_icon_long_horizontal_tresure
    else if (type.contains("#LHF"))
        return R.drawable.map_icon_long_horizontal_fight
    else if (type.contains("#LH"))
        return R.drawable.map_icon_long_horizontal

    else if(type.contains("#LVH"))
        return R.drawable.map_icon_long_vertical_heal
    else if(type.contains("#LHH"))
        return R.drawable.map_icon_long_horizontal_heal
    else if(type.contains("#BH"))
        return R.drawable.map_icon_big_heal
    else if(type.contains("#SH"))
        return R.drawable.map_icon_small_heal
    else if(type.contains("#CH"))
        return R.drawable.map_icon_corridor_heal

    else
        return R.drawable.empty_slot
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun MainMapScreen(onFightScreen: () -> Unit, onLevelUpScreen: () -> Unit) {
    val map = remember { generatedMap.map }
    val showDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current

    val pHP = remember { player.HP }
    val pEXP = remember { player.EXP }

    val horizontalScrollState = rememberScrollState()
    val verticalScrollState = rememberScrollState()

    if (showDialog.value) {
        LootDialog(
            items = rollLoot()
        ) { showDialog.value = false }
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {}
    ) {

    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(verticalScrollState),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ){
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(verticalScrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(horizontalAlignment = Alignment.Start) {
            if (player.checkForLvUp()) {
                Button(
                    onClick = { onLevelUpScreen.invoke() },
                    modifier = Modifier
                        .size(240.dp) // Ustaw kwadratowy rozmiar
                        .border(2.dp, Color.White, shape = RectangleShape), // Dodaj białą ramkę
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent) // Przezroczyste tło przycisku
                ) {
                    Text("NOWY POZIOM", color = Color.White) // Tekst w białym kolorze
                }
            }
            StatusBar(status = pHP, max = player.MAX_HP.value.toFloat(), barColor = Color.Red)
            StatusBar(status = pEXP, max = player.EXPtoLv.toFloat(), barColor = Color.Cyan)
        }

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(20.dp))
        for (i in 0 until generatedMap.rows) {
            Row (modifier = Modifier.horizontalScroll(horizontalScrollState)){
                for (j in 0 until generatedMap.columns) {
                    when (val tile = getMapTileIcon(map[i][j].value)) {
                            R.drawable.map_icon_corridor -> {
                                CorridorHorizontal(mapTileIcon = tile,{  },i,j)
                            }
                            R.drawable.map_icon_corridor_fight -> {
                                CorridorHorizontal(mapTileIcon = tile, {  rollEnemy(); player.rollFullHandModifiers(); onFightScreen.invoke() }, i, j)
                            }
                            R.drawable.map_icon_corridor_exit -> {
                                CorridorHorizontal(mapTileIcon = tile, {  generatedMap.generateMap(); }, i, j)
                            }



                            R.drawable.map_icon_small  -> {
                                SmallRoom(mapTileIcon = tile,{  },i,j)
                            }
                            R.drawable.map_icon_small_tresure  -> {
                                SmallRoom(
                                    mapTileIcon = tile,
                                    {  showDialog.value = !showDialog.value }, i, j
                                )
                            }
                            R.drawable.map_icon_small_fight  -> {
                                SmallRoom(mapTileIcon = tile, {  rollEnemy(); player.rollFullHandModifiers(); onFightScreen.invoke() }, i, j)
                            }
                            R.drawable.map_icon_small_exit  -> {
                                SmallRoom(mapTileIcon = tile, { generatedMap.generateMap();revealRooms(i,j) }, i, j)
                            }
                            R.drawable.map_icon_small_heal -> {
                                SmallRoom(mapTileIcon = tile, { player.healRoom() ;revealRooms(i,j) }, i, j)
                            }

                            R.drawable.map_icon_big -> {
                                BigRoom(mapTileIcon = tile,{  },i,j)
                            }
                            R.drawable.map_icon_big_tresure -> {
                                BigRoom(
                                    mapTileIcon = tile,
                                    {  showDialog.value = !showDialog.value }, i, j
                                )
                            }
                            R.drawable.map_icon_big_fight -> {
                                BigRoom(mapTileIcon = tile, {  rollEnemy(); player.rollFullHandModifiers(); onFightScreen.invoke() }, i, j)
                            }
                            R.drawable.map_icon_big_exit -> {
                                BigRoom(mapTileIcon = tile, {  generatedMap.generateMap();revealRooms(i,j) }, i, j)
                            }

                            R.drawable.map_icon_big_heal -> {
                                BigRoom(mapTileIcon = tile, {  player.healRoom() ;revealRooms(i,j) }, i, j)
                            }

                            R.drawable.map_icon_long_vertical -> {
                                LongVerticalRoom(mapTileIcon = tile,{ },i,j)
                            }
                            R.drawable.map_icon_long_vertical_tresure -> {
                                LongVerticalRoom(
                                    mapTileIcon = tile,
                                    { showDialog.value = !showDialog.value }, i, j
                                )
                            }
                            R.drawable.map_icon_long_vertical_fight -> {
                                LongVerticalRoom(mapTileIcon = tile, {rollEnemy(); player.rollFullHandModifiers(); onFightScreen.invoke() }, i, j)
                            }
                            R.drawable.map_icon_long_vertical_exit -> {
                                LongVerticalRoom(mapTileIcon = tile, { generatedMap.generateMap();revealRooms(i,j) }, i, j)
                            }
                            R.drawable.map_icon_long_vertical_heal -> {
                                LongVerticalRoom(mapTileIcon = tile, { player.healRoom() ;revealRooms(i,j) }, i, j)
                            }


                            R.drawable.map_icon_long_horizontal -> {
                                LongHorizontalRoom(mapTileIcon = tile,{ },i,j)
                            }
                            R.drawable.map_icon_long_horizontal_tresure -> {
                                LongHorizontalRoom(
                                    mapTileIcon = tile,
                                    { showDialog.value = !showDialog.value }, i, j
                                )
                            }
                            R.drawable.map_icon_long_horizontal_fight -> {
                                LongHorizontalRoom(mapTileIcon = tile, { rollEnemy(); player.rollFullHandModifiers(); onFightScreen.invoke() }, i, j)
                            }
                            R.drawable.map_icon_long_horizontal_exit -> {
                                LongHorizontalRoom(mapTileIcon = tile, { generatedMap.generateMap();revealRooms(i,j) }, i, j)

                            }
                            R.drawable.map_icon_long_horizontal_heal -> {
                                LongHorizontalRoom(mapTileIcon = tile, { player.healRoom(); revealRooms(i,j) }, i, j)

                            }
                    }
                }
            }
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(90.dp))
    }
}

fun revealFullMap()
{
    for (i in 0 until generatedMap.rows)
        for (j in 0 until generatedMap.columns)
            if(generatedMap.map[i][j].value.isNotEmpty()) {
                 generatedMap.map[i][j].value += "A"
            }
}

fun revealRooms(i: Int, j: Int) {
    val rows = generatedMap.rows
    val columns = generatedMap.columns

    if (j + 1 < columns && generatedMap.map[i][j + 1].value.isNotEmpty() &&
        generatedMap.map[i][j + 1].value.last() == 'K'
    ) {
        generatedMap.map[i][j + 1].value =
            generatedMap.map[i][j + 1].value.dropLast(1) + "A"
    }

    if (j - 1 >= 0 && generatedMap.map[i][j - 1].value.isNotEmpty() &&
        generatedMap.map[i][j - 1].value.last() == 'K'
    ) {
        generatedMap.map[i][j - 1].value =
            generatedMap.map[i][j - 1].value.dropLast(1) + "A"
    }

    if (i + 1 < rows && generatedMap.map[i + 1][j].value.isNotEmpty() &&
        generatedMap.map[i + 1][j].value.last() == 'K'
    ) {
        generatedMap.map[i + 1][j].value =
            generatedMap.map[i + 1][j].value.dropLast(1) + "A"
    }

    if (i - 1 >= 0 && generatedMap.map[i - 1][j].value.isNotEmpty() &&
        generatedMap.map[i - 1][j].value.last() == 'K'
    ) {
        generatedMap.map[i - 1][j].value =
            generatedMap.map[i - 1][j].value.dropLast(1) + "A"
    }
}


@Composable
fun LootDialog(items: MutableList<String>, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            modifier = Modifier
                .padding(16.dp)
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.medium,
            color = Color.White,
            contentColor = Color.Black
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Lupy:", style = MaterialTheme.typography.titleSmall)
                items.forEach { item ->
                    Text(text = item, style = MaterialTheme.typography.bodySmall)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onDismiss) {
                    Text("OK")
                }
            }
        }
    }
}

@Composable
fun CorridorHorizontal(mapTileIcon: Int, onClickAction: () -> Unit, i: Int, j: Int)
{
    val alpha = when (generatedMap.map[i][j].value.last()) {
        'U' -> 1f
        'A' -> 0.5f
        'K' -> 0.1f
        else -> 0.0f
    }

    val clickableModifier = when (generatedMap.map[i][j].value.last()) {
        'U','A' -> Modifier.clickable {
            onClickAction.invoke()
            revealRooms(i,j)
            generatedMap.map[i][j].value = "#CU"
        }
        else -> Modifier
    }

    Box(
        modifier = Modifier
            .height(55.dp)
            .background(Color.Gray)
            .width(65.dp)
            .alpha(alpha)
            .then(clickableModifier),

    ) {
        Image(
            painter = painterResource(id = mapTileIcon),
            contentDescription = "Image",
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun SmallRoom(mapTileIcon: Int, onClickAction: () -> Unit, i: Int, j: Int)
{
    val alpha = when (generatedMap.map[i][j].value.last()) {
        'U' -> 1f
        'A' -> 0.5f
        'K' -> 0.1f
        else -> 0.0f
    }

    val clickableModifier = when (generatedMap.map[i][j].value.last()) {
        'U','A' -> Modifier.clickable {
            onClickAction.invoke()
            generatedMap.map[i][j].value = "#SU"
            revealRooms(i,j)
        }
        else -> Modifier
    }

    Box(
        modifier = Modifier
            .size(65.dp)
            .alpha(alpha)
            .then(clickableModifier),
    ) {
        Image(
            painter = painterResource(id = mapTileIcon),
            contentDescription = "Image",
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun LongHorizontalRoom(mapTileIcon: Int, onClickAction: () -> Unit, i: Int, j: Int)
{
    val alpha = when (generatedMap.map[i][j].value.last()) {
        'U' -> 1f
        'A' -> 0.5f
        'K' -> 0.1f
        else -> 0.0f
    }

    val clickableModifier = when (generatedMap.map[i][j].value.last()) {
        'U','A' -> Modifier.clickable {
            onClickAction.invoke()
            revealRooms(i,j)
            generatedMap.map[i][j].value = "#LHU"
        }
        else -> Modifier
    }

    Box(
        modifier = Modifier
            .width(120.dp)
            .height(65.dp)
            .alpha(alpha)
            .then(clickableModifier),
    ) {
        Image(
            painter = painterResource(id = mapTileIcon),
            contentDescription = "Image",
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun LongVerticalRoom(mapTileIcon: Int, onClickAction: () -> Unit, i: Int, j: Int)
{
    val alpha = when (generatedMap.map[i][j].value.last()) {
        'U' -> 1f
        'A' -> 0.5f
        'K' -> 0.1f
        else -> 0.0f
    }

    val clickableModifier = when (generatedMap.map[i][j].value.last()) {
        'U','A' -> Modifier.clickable {
            onClickAction.invoke()
            revealRooms(i,j)
            generatedMap.map[i][j].value = "#LVU"
        }
        else -> Modifier
    }

    Box(
        modifier = Modifier
            .width(65.dp)
            .height(110.dp)
            .alpha(alpha)
            .then(clickableModifier),
    ) {
        Image(
            painter = painterResource(id = mapTileIcon),
            contentDescription = "Image",
            modifier = Modifier
                .fillMaxSize()
        )
    }
}


@Composable
fun BigRoom(mapTileIcon: Int, onClickAction: () -> Unit, i: Int, j: Int)
{
    val alpha = when (generatedMap.map[i][j].value.last()) {
        'U' -> 1f
        'A' -> 0.5f
        'K' -> 0.1f
        else -> 0.0f
    }

    val clickableModifier = when (generatedMap.map[i][j].value.last()) {
        'U','A' -> Modifier.clickable {
            onClickAction.invoke()
            revealRooms(i,j)
            generatedMap.map[i][j].value = "#BU"
        }
        else -> Modifier
    }

    Box(
        modifier = Modifier
            .size(110.dp)
            .alpha(alpha)
            .then(clickableModifier),
    ) {
        Image(
            painter = painterResource(id = mapTileIcon),
            contentDescription = "Image",
            modifier = Modifier
                .fillMaxSize()
        )
    }
}



