package com.example.ironman

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.io.File
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

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

    else
        return R.drawable.empty_slot
}


data class Room(
    val type: String,
    val width: Int,
    val height: Int,
    var x: Int = 0,
    var y: Int = 0
)



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

    /*val roomMap = List(generatedMap.rows) { row ->
        List(generatedMap.columns) { column ->
            val type = roomTypes[row][column]
            val (width, height) = getRoomDimensions(type)
            Room(type = type, width = width, height = height)
        }
    }

    roomMap.forEachIndexed { rowIndex, row ->
        row.forEachIndexed { columnIndex, room ->
            val (x, y) = calculatePosition(rowIndex, columnIndex, roomMap)
            room.x = x
            room.y = y
        }
    }*/


    if (showDialog.value) {
        LootDialog(
            items = rollLoot()
        ) { showDialog.value = false }
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ){}

    Row(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(verticalScrollState),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ){
        Column {
            Image(
                painter = painterResource(id = player.portrait),
                contentDescription = "Portret gracza",
                modifier = Modifier.size(50.dp)
            )
            StatusBar(status = pHP, max = player.MAX_HP.value.toFloat(), barColor = Color.Red)
            StatusBar(status = pEXP, max = player.EXPtoLv.toFloat(), barColor = Color.Cyan)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(verticalScrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            /*Button(
                onClick =
                {
                    if (player.levelUp()) { onLevelUpScreen() }
                },
                modifier = Modifier.size(width = 130.dp, height = 80.dp)
            ) {
                Text("LEVEL UP")
            }*/
            Button(
                onClick =
                {
                    generatedMap.generateMap()
                    revealFullMap()
                },
                modifier = Modifier.size(width = 130.dp, height = 80.dp)
            ) {
                Text("Generuj")
            }
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
                                CorridorHorizontal(mapTileIcon = tile, {  rollEnemy(); onFightScreen.invoke() }, i, j)
                            }
                            R.drawable.map_icon_corridor_exit -> {
                                CorridorHorizontal(mapTileIcon = tile, {  generatedMap.generateMap();revealFullMap() }, i, j)
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
                                SmallRoom(mapTileIcon = tile, {  rollEnemy(); onFightScreen.invoke() }, i, j)
                            }
                            R.drawable.map_icon_small_exit  -> {
                                SmallRoom(mapTileIcon = tile, { generatedMap.generateMap();revealFullMap() }, i, j)
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
                                BigRoom(mapTileIcon = tile, {  rollEnemy(); onFightScreen.invoke() }, i, j)
                            }
                            R.drawable.map_icon_big_exit -> {
                                BigRoom(mapTileIcon = tile, {  generatedMap.generateMap();revealFullMap() }, i, j)
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
                                LongVerticalRoom(mapTileIcon = tile, {rollEnemy(); onFightScreen.invoke() }, i, j)
                            }
                            R.drawable.map_icon_long_vertical_exit -> {
                                LongVerticalRoom(mapTileIcon = tile, { generatedMap.generateMap();revealFullMap() }, i, j)
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
                                LongHorizontalRoom(mapTileIcon = tile, { rollEnemy(); onFightScreen.invoke() }, i, j)
                            }
                            R.drawable.map_icon_long_horizontal_exit -> {
                                LongHorizontalRoom(mapTileIcon = tile, { generatedMap.generateMap();revealFullMap() }, i, j)

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

fun revealRoom(i: Int, j: Int)
{
    if(generatedMap.map[i+1][j].value.isNotEmpty()) {
        if (i + 1 < generatedMap.rows && generatedMap.map[i + 1][j].value.last() == 'K' && (generatedMap.map[i + 1][j].value.contains("B") || generatedMap.map[i + 1][j].value.contains("LV"))) generatedMap.map[i + 1][j].value += "A"
    }
    if(generatedMap.map[i-1][j].value.isNotEmpty()) {
        if (i - 1 >= 0 && generatedMap.map[i - 1][j].value.last() == 'K' && (generatedMap.map[i - 1][j].value.contains("B") || generatedMap.map[i + 1][j].value.contains("LV"))) generatedMap.map[i - 1][j].value += "A"
    }

    if(generatedMap.map[i][j+1].value.isNotEmpty()) {
        if (j + 1 < generatedMap.columns && generatedMap.map[i][j + 1].value.last() == 'K') generatedMap.map[i][j + 1].value += "A"
    }
    if(generatedMap.map[i][j-1].value.isNotEmpty()) {
        if(j-1 >= 0 && generatedMap.map[i][j-1].value.last() == 'K') generatedMap.map[i][j-1].value += "A"
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

fun getRoomDimensions(type: String): Pair<Int, Int> {
    return when {
        type.contains("C") -> Pair(65, 55)
        type.contains("S") -> Pair(65, 65)
        type.contains("LH") -> Pair(120, 65)
        type.contains("LV") -> Pair(65, 110)
        type.contains("B") -> Pair(110, 110)
        else -> Pair(0, 0)
    }
}

fun calculatePosition(i: Int, j: Int): Pair<Int, Int> {
    var x = 0
    var y = 0

    for (collumn in 0 until j)
    {
        val type = generatedMap.map[i][collumn].value
        x += getRoomDimensions(type).first
    }


    for (row in 0 until i)
    {
        var max = 0
        for (collumn in 0 until generatedMap.columns)
        {
            //val type = generatedMap.map[i][collumn].value
            val type = getRoomDimensions(generatedMap.map[i][collumn].value).second
            if(type>max)
            {
                max = type
            }
        }
        y+=max
    }

    return Pair(x, y)
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
        'K' -> 0.0f
        else -> 0.0f
    }

    val clickableModifier = when (generatedMap.map[i][j].value.last()) {
        'U','A' -> Modifier.clickable {
            onClickAction.invoke()
            generatedMap.map[i][j].value = "#CU"
            Log.d("",calculatePosition(i,j).toString())
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
                //.clickable { onClickAction.invoke(); generatedMap.map[i][j].value = "#CU" }
        )
    }
}

@Composable
fun SmallRoom(mapTileIcon: Int, onClickAction: () -> Unit, i: Int, j: Int)
{
    val alpha = when (generatedMap.map[i][j].value.last()) {
        'U' -> 1f
        'A' -> 0.5f
        'K' -> 0.0f
        else -> 0.0f
    }

    val clickableModifier = when (generatedMap.map[i][j].value.last()) {
        'U','A' -> Modifier.clickable {
            onClickAction.invoke()
            generatedMap.map[i][j].value = "#SU"
            Log.d("",calculatePosition(i,j).toString())
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
                //.clickable { onClickAction.invoke(); generatedMap.map[i][j].value = "#SU" }
        )
    }
}

@Composable
fun LongHorizontalRoom(mapTileIcon: Int, onClickAction: () -> Unit, i: Int, j: Int)
{
    val alpha = when (generatedMap.map[i][j].value.last()) {
        'U' -> 1f
        'A' -> 0.5f
        'K' -> 0.0f
        else -> 0.0f
    }

    val clickableModifier = when (generatedMap.map[i][j].value.last()) {
        'U','A' -> Modifier.clickable {
            onClickAction.invoke()
            generatedMap.map[i][j].value = "#LHU"
            Log.d("",calculatePosition(i,j).toString())
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
                //.clickable { onClickAction.invoke(); generatedMap.map[i][j].value = "#LHU" }
        )
    }
}

@Composable
fun LongVerticalRoom(mapTileIcon: Int, onClickAction: () -> Unit, i: Int, j: Int)
{
    val alpha = when (generatedMap.map[i][j].value.last()) {
        'U' -> 1f
        'A' -> 0.5f
        'K' -> 0.0f
        else -> 0.0f
    }

    val clickableModifier = when (generatedMap.map[i][j].value.last()) {
        'U','A' -> Modifier.clickable {
            onClickAction.invoke()
            generatedMap.map[i][j].value = "#LVU"
            Log.d("",calculatePosition(i,j).toString())
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
                //.clickable { onClickAction.invoke(); generatedMap.map[i][j].value = "#LVU" }
        )
    }
}


@Composable
fun BigRoom(mapTileIcon: Int, onClickAction: () -> Unit, i: Int, j: Int)
{
    val alpha = when (generatedMap.map[i][j].value.last()) {
        'U' -> 1f
        'A' -> 0.5f
        'K' -> 0.0f
        else -> 0.0f
    }

    val clickableModifier = when (generatedMap.map[i][j].value.last()) {
        'U','A' -> Modifier.clickable {
            onClickAction.invoke()
            generatedMap.map[i][j].value = "#BU"
            Log.d("",calculatePosition(i,j).toString())
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
                //.clickable { onClickAction.invoke(); generatedMap.map[i][j].value = "#BU" }
        )
    }
}



fun saveGame(player: Player, context: Context) {
    val serializablePlayer = player.toSerializablePlayer()
    val jsonString = Json.encodeToString(serializablePlayer)
    val file = File(context.filesDir, "savefile.txt")
    file.writeText(jsonString)
}

fun loadGame(context: Context) {
    val file = File(context.filesDir, "savefile.txt")
    val jsonString = file.readText()
    val serializablePlayer = Json.decodeFromString<SerializablePlayer>(jsonString)
    player.loadPlayer(serializablePlayer)
}

