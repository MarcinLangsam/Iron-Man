package com.example.ironman

import android.annotation.SuppressLint
import android.util.Log
import android.view.MotionEvent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

var activeCard = mutableListOf<Card>()
var maxModifier = 0
var queuedModifierIndex = mutableListOf<Int>()
var currentEnemy = szkielet
var moveToTarget = false
var bottomBarCheck = true

fun checkForExceedingHPorAP(entity: Any)
{
    if(entity is Player)
    {
        if (player.HP.value > player.MAX_HP.value) player.HP.value = player.MAX_HP.value
        if (player.AP.value > player.MAX_AP.value) player.AP.value = player.MAX_AP.value

    }
    else
    {
        if(currentEnemy.HP.value > currentEnemy.MAX_HP.value) currentEnemy.HP.value = currentEnemy.MAX_HP.value
    }
}

fun endFight(enemy: Enemy): Boolean {
    return player.HP.value <= 0 || enemy.HP.value <=0
}

fun useCard(card: Card): Int {
    if(card.type == "attack")
    {
        currentEnemy.HP.value -= card.effect.invoke()
        player.AP.value -= card.AP_required
    }
    if(card.type == "heal")
    {
        player.HP.value += card.effect.invoke()
        player.AP.value -= card.AP_required
        checkForExceedingHPorAP(player)
    }
    if(card.type == "rest")
    {
        player.AP.value += card.effect.invoke()
        checkForExceedingHPorAP(player)
    }
    return card.effect.invoke()
}
fun useCardEnemy(card: EnemyCards) : Int
{
    if(card.type == "attack")
    {
        player.HP.value -= card.effect.invoke(currentEnemy)
    }
    if(card.type == "heal")
    {
        currentEnemy.HP.value += card.effect.invoke(currentEnemy)
        checkForExceedingHPorAP(currentEnemy)
    }

    return card.effect.invoke(currentEnemy)
}

fun addModifierToCard(modifier: com.example.ironman.Modifier)
{
    if(maxModifier > 0)
    {
        if(player.modiferQueue.size < activeCard[0].modifiersSlots){ player.modiferQueue.add(modifier) }
    }
}

fun popModifierFromCard()
{
    if (player.modiferQueue.isNotEmpty()) {
        player.modiferQueue.removeFirst()
        //player.AP.value += popedModifier.AP_required
    }

}

fun setActiveCard(card: Card)
{
    activeCard.clear()
    activeCard.add(card)
    maxModifier = card.modifiersSlots
}

fun resetActiveCard()
{
    if(player.modiferQueue.isNotEmpty()){
        while(player.modiferQueue.isEmpty())
        {
            popModifierFromCard()
        }
    }
    activeCard.clear()
    maxModifier = 0

}

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun FightScreen(onMap: () -> Unit){

    val isModifierVisible = remember { mutableStateListOf(true, true, true, true, true) }
    val modifiersOnClickFunction = remember {
        listOf(
            { addModifierToCard(player.modifiersOnHand[0]); if(queuedModifierIndex.size < maxModifier){isModifierVisible[0] = false; queuedModifierIndex.add(0)} },
            { addModifierToCard(player.modifiersOnHand[1]); if(queuedModifierIndex.size < maxModifier){isModifierVisible[1] = false; queuedModifierIndex.add(1)} },
            { addModifierToCard(player.modifiersOnHand[2]); if(queuedModifierIndex.size < maxModifier){isModifierVisible[2] = false; queuedModifierIndex.add(2)} },
            { addModifierToCard(player.modifiersOnHand[3]); if(queuedModifierIndex.size < maxModifier){isModifierVisible[3] = false; queuedModifierIndex.add(3)} },
            { addModifierToCard(player.modifiersOnHand[4]); if(queuedModifierIndex.size < maxModifier){isModifierVisible[4] = false; queuedModifierIndex.add(4)} },
        )
    }

    val modifierQueue = remember { player.modiferQueue }
    val pHP = remember { player.HP }
    val pAP = remember { player.AP }
    val eHP = remember { currentEnemy.HP }
    val activeCard = remember { activeCard }


    var moveToTarget by remember { mutableStateOf(moveToTarget) }
    var hideBeginTurn by remember { mutableStateOf(false) }

    val xOffsetPlayer = remember { androidx.compose.animation.core.Animatable(0f) }
    val yOffsetPlayer = remember { androidx.compose.animation.core.Animatable(0f) }
    val xOffsetEnemy = remember { androidx.compose.animation.core.Animatable(0f) }
    val yOffsetEnemy = remember { androidx.compose.animation.core.Animatable(0f) }

    val targetXPlayer = 250f
    val targetYPlayer = -30f
    val targetXEnemy = -250f
    val targetYEnemy = -30f

    var damageText by remember { mutableStateOf("") }
    var damageVisible by remember { mutableStateOf(false) }
    var damagePosition by remember { mutableStateOf(390.dp) }
    var damageColor by remember { mutableStateOf(Color.Black) }



    LaunchedEffect(moveToTarget) {
        if (moveToTarget) {
            hideBeginTurn = true
            for(x in player.actionQueue)
            {
                if (x.type == "attack")
                {
                    xOffsetPlayer.animateTo(targetXPlayer, animationSpec = tween(500))
                    //useCard(x)
                    damageText = useCard(x).toString()
                    damagePosition = 390.dp
                    damageColor = Color.Black
                    damageVisible = true
                    delay(1000)

                    damageVisible = false
                    xOffsetPlayer.animateTo(0f, animationSpec = tween(500))

                }
                else
                {
                    yOffsetPlayer.animateTo(targetYPlayer, animationSpec = tween(100))
                    //useCard(x)

                    damageText = useCard(x).toString()
                    damagePosition = 100.dp
                    damageColor = Color.Green
                    damageVisible = true
                    delay(1000)

                    damageVisible = false
                    yOffsetPlayer.animateTo(0f, animationSpec = tween(100))
                }

            }

            if(!endFight(currentEnemy))
            {
                fillActionsReady(currentEnemy)
                val enemyAction = choseAction(currentEnemy)
                if(enemyAction.type == "attack") {
                    xOffsetEnemy.animateTo(targetXEnemy, animationSpec = tween(500))
                    //useCardEnemy(enemyAction)

                    damageText = useCardEnemy(enemyAction).toString()
                    damagePosition = 100.dp
                    damageColor = Color.Black
                    damageVisible = true
                    delay(1000)

                    damageVisible = false
                    xOffsetEnemy.animateTo(0f, animationSpec = tween(500))
                }
                else
                {
                    yOffsetEnemy.animateTo(targetYEnemy, animationSpec = tween(100))
                    //useCardEnemy(enemyAction)

                    damageText = useCardEnemy(enemyAction).toString()
                    damagePosition = 390.dp
                    damageColor = Color.Green
                    damageVisible = true
                    delay(1000)

                    damageVisible = false
                    yOffsetEnemy.animateTo(0f, animationSpec = tween(100))
                }
            }

            for (i in 0..4 )
            {
                if(!isModifierVisible[i])
                {
                    player.rollOneOnHandAtModifiers(i)
                    isModifierVisible[i] = true
                }
            }
            player.AP.value += player.AP_recovery
            player.actionQueue.clear()
            queuedModifierIndex.clear()
            moveToTarget = false
            hideBeginTurn = false
        }
    }


    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.battle_background),
            contentDescription = "Image",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillWidth
        )
    }
    Column(
        Modifier.fillMaxWidth(0.5f),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {

        Row {
            Column {
                StatusBar(status = pHP, max = player.MAX_HP.value.toFloat(), barColor = Color.Red)
                StatusBar(status = pAP, max = player.MAX_AP.value.toFloat(), barColor = Color.Green)
            }

            ActiveCardButton(if (activeCard.size>0) activeCard[0].sprite else R.drawable.empty_slot)
            if(activeCard.isNotEmpty()) {
                repeat(activeCard[0].modifiersSlots) { index ->
                    QueuedModifierButton(modifierQueue, index, isModifierVisible)
                }
            }

            DamagePopUp(damageVisible = damageVisible, damagePosition = damagePosition, damageText = damageText, color = damageColor)
        }
        Column {
            repeat(isModifierVisible.size) { index ->
                if (isModifierVisible[index]) {
                    Modifier_Button(onClickAction = modifiersOnClickFunction[index], skillDescription = player.modifiersOnHand[index].descriptionFight, sprite = player.modifiersOnHand[index].sprite, spriteHold = player.modifiersOnHand[index].spriteHold)
                }
            }
        }
        BattleSprite(xOffset = xOffsetPlayer.value.dp+150.dp, yOffset = yOffsetPlayer.value.dp-50.dp, sprite = R.drawable.player)


    }


    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Top
    ) {
        LaunchedEffect(eHP.value)
        {
            if(endFight(currentEnemy)) {
                player.HP = player.MAX_HP
                player.AP = player.MAX_AP
                player.EXP.value += currentEnemy.EXPGiven
                player.gold += currentEnemy.goldGiven
                player.actionQueue.clear()
                bottomBarCheck = true

                currentEnemy.HP = currentEnemy.MAX_HP
                onMap()
            }

        }
        StatusBar(status = eHP, max = currentEnemy.MAX_HP.value.toFloat(), barColor = Color.Red)
        Text(text = "Przeciwnik", fontSize = 25.sp)
        BattleSprite(xOffset = xOffsetEnemy.value.dp-120.dp, yOffset = yOffsetEnemy.value.dp, sprite = R.drawable.skeleton)

    }

    Row(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    )
    {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                BeginTurn(activeCard = activeCard, hideBeginTurn = hideBeginTurn) { moveToTarget = !moveToTarget }
            }
            Row {
                repeat(player.cardsOnHand.size) { index ->
                    Card_Button(onClickAction = {setActiveCard(player.cardsOnHand[index])}, skillDescription = player.cardsOnHand[index].descriptionFight, sprite = player.cardsOnHand[index].sprite, spriteHold = player.cardsOnHand[index].spriteHold)
                }
            }
            //Row {

            //}
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Modifier_Button(onClickAction: () -> Unit, skillDescription: String, sprite: Int, spriteHold: Int) {
    var isHeld by remember { mutableStateOf(false) }
    var isLongPress by remember { mutableStateOf(false) }
    var currentWidth by remember { mutableStateOf(50.dp) }
    var currentDescription by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .height(50.dp)
            .width(currentWidth)
            .padding(3.dp)
            .pointerInteropFilter { event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        isHeld = true
                        isLongPress = false
                        scope.launch {
                            currentWidth= 250.dp
                            currentDescription = skillDescription
                            delay(500)
                            if (isHeld) {
                                isLongPress = true
                            }
                        }
                        true
                    }

                    MotionEvent.ACTION_UP -> {
                        if (!isLongPress) {
                            onClickAction.invoke()
                        }
                        isHeld = false
                        currentWidth = 50.dp
                        currentDescription = ""
                        true
                    }

                    else -> false
                }
            }
    ) {
        Image(
            painter = painterResource(id = if (isHeld) sprite else sprite),
            contentDescription = "Image",
            modifier = Modifier.fillMaxSize()
        )
        Text(
            text = currentDescription,
            modifier = Modifier.offset(60.dp,0.dp),
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Card_Button(onClickAction: () -> Unit, skillDescription: String, sprite: Int, spriteHold: Int) {
    var isHeld by remember { mutableStateOf(false) }
    var isLongPress by remember { mutableStateOf(false) }
    var currentHeight by remember { mutableStateOf(100.dp) }
    var currentDescription by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .height(currentHeight)
            .width(100.dp)
            .padding(3.dp)
            .pointerInteropFilter { event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        isHeld = true
                        isLongPress = false
                        scope.launch {
                            currentHeight = 200.dp
                            currentDescription = skillDescription
                            delay(500)
                            if (isHeld) {
                                isLongPress = true
                            }
                        }
                        true
                    }

                    MotionEvent.ACTION_UP -> {
                        if (!isLongPress) {
                            onClickAction.invoke()
                        }
                        isHeld = false
                        currentHeight = 100.dp
                        currentDescription = ""
                        true
                    }

                    else -> false
                }
            }
    ) {
        Image(
            painter = painterResource(id = if (isHeld) spriteHold else sprite),
            contentDescription = "Image",
            modifier = Modifier.fillMaxSize()
        )
        Text(
            text = currentDescription,
            modifier = Modifier.offset(10.dp,120.dp),
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
        )
    }
}


@Composable
fun ActiveCardButton(sprite: Int)
{
    Box(
        modifier = Modifier
            .width(55.dp)
            .height(55.dp)
            .padding(2.dp)
            .clickable { if (activeCard.isNotEmpty())
                { resetActiveCard() }
            },
    ) {
        Image(
            painter = painterResource(id = sprite),
            contentDescription = "Image",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillHeight
        )
    }
}

@Composable
fun QueuedModifierButton(modifierQueue: MutableList<com.example.ironman.Modifier>, index: Int, visibleButtons: SnapshotStateList<Boolean>)
{
    val buttonSprite = if (modifierQueue.size > index) modifierQueue[index].sprite else R.drawable.empty_slot

    Box(
        modifier = Modifier
            .width(40.dp)
            .height(40.dp)
            .padding(2.dp)
            .clickable {
                if (modifierQueue.isNotEmpty()) {
                    popModifierFromCard()
                    visibleButtons[queuedModifierIndex.first()] = true
                    queuedModifierIndex.removeFirst()
                }
            },
    ) {
        Image(
            painter = painterResource(id = buttonSprite),
            contentDescription = "Image",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillHeight
        )
    }
}

@Composable
fun BeginTurn(activeCard: MutableList<Card>, hideBeginTurn: Boolean, moveToTarget: () -> Unit) {
    val isVisible = activeCard.size > 0 && !hideBeginTurn
    val darkRed = Color(0xFF8B0000)

    if (isVisible) {
        Button(
            onClick = moveToTarget,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier
                .size(100.dp)
                .offset(y = (-25).dp)
                .border(
                    BorderStroke(4.dp, darkRed),
                    shape = RoundedCornerShape(50.dp)
                ),
        ) {
            Text(text = "Walka")
        }
    }
}



@Composable
fun CustomSquareProgressBar(progress: Float, color: Color, modifier: Modifier = Modifier, borderColor: Color = Color.DarkGray) {
    Box(
        modifier = modifier
            .background(Color.Gray)
            .border(2.dp, borderColor)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width * progress
            // Draw the background
            drawRect(
                color = Color.White,
                size = size,
                style = Fill
            )
            // Draw the progress
            drawRect(
                color = color,
                size = androidx.compose.ui.geometry.Size(width, size.height),
                style = Fill
            )
        }
    }
}

@Composable
fun StatusBar(status: MutableState<Int>, max: Float, barColor: Color, borderColor: Color = Color(0xFFCCCC00)) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .width(200.dp)
            .height(20.dp),
        contentAlignment = Alignment.Center
    ) {
        CustomSquareProgressBar(
            progress = status.value / max,
            color = barColor,
            modifier = Modifier.fillMaxSize(),
            borderColor = borderColor
        )
        Text(text = status.value.toString()+"/"+max.toInt().toString(), fontSize = 15.sp)
    }
}


@Composable
fun BattleSprite(xOffset: Dp, yOffset: Dp, sprite: Int)
{
    Box(
        modifier = Modifier
            .width(150.dp)
            .height(250.dp)
            .offset(xOffset.value.dp, yOffset.value.dp)
    ){
        Image(
            painter = painterResource(id = sprite),
            contentDescription = "Image",
            modifier = Modifier
                .width(150.dp)
                .height(250.dp)
        )
    }
}

@Composable
fun DamagePopUp(damageVisible: Boolean, damagePosition: Dp, damageText: String, color: Color)
{
    Box {
        AnimatedVisibility(
            visible = damageVisible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Text(text = damageText, color = color, fontSize = 30.sp,
                modifier = Modifier.offset(x = damagePosition)
            )
        }
    }
}



