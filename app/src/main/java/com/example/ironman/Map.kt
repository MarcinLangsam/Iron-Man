package com.example.ironman

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlin.random.Random

class Map(
    val rows: Int,
    val columns: Int
){

    fun rollRoomContent(roomType: String) :String
    {
        // F - fight, T - tresure, H - heal, B- buff "" - empty room
        val contentType = mutableListOf("F","F","F","T","T","","","","","","","H","H")
        var roll = contentType.random()
        if(roomType == "C" && roll == "T") roll = ""
        return roll
    }

    fun addNextRooms(): String {
        // 1 - UP, 2 - DOWN, 3 - LEFT, 4 - RIGHT
        val directions = mutableListOf<String>("1","2","3","4")
        // CH - corridor horizontal, S - small room, B - big room, LV - Long vertical room, LH - Long horizontal room
        val roomTypes = mutableListOf<String>("S","S","S","S","B","B","B","LH","LH","LH","LV")
        val direction = directions.random()
        var roomType = ""

        directions.add(direction)
        directions.add(direction)
        directions.add(direction)
        directions.add(direction)
        directions.add(direction)
        directions.add(direction)
        directions.add(direction)
        directions.add(direction)

        if(direction.contains("1"))
        {
            roomType = roomTypes.random()
            return "#$roomType"+rollRoomContent(roomType)+"K"
        }
        if(direction.contains("2"))
        {
            roomType = roomTypes.random()
            return "#$roomType"+rollRoomContent(roomType)+"K"
        }
        if(direction.contains("3"))
        {
            roomType = roomTypes.random()
            return "#$roomType"+rollRoomContent(roomType)+"K"
        }
        if(direction.contains("4"))
        {
            roomType = roomTypes.random()
            return "#$roomType"+rollRoomContent(roomType)+"K"
        }
        return "#SK"
}

    val map = Array(rows) { Array(columns) { mutableStateOf("") } }

    @SuppressLint("SuspiciousIndentation")
    fun generateMap()
    {
        for(i in 0 until rows)
        {
            for (j in 0 until columns)
            {
                map[i][j].value = ""
            }
        }

        val numberOfSnakes = Random.nextInt(4,7+1)
        val endPoints = mutableListOf<Pair<Int,Int>>()
        var lengthOfSnakes = Random.nextInt(4, 6 + 1)
        val start = Random.nextInt(1,3)
        for (k in 1..numberOfSnakes)
        {
            lengthOfSnakes = Random.nextInt(4, 6 + 1)
            for (j in start..lengthOfSnakes+start)
            {
                map[k][j].value = addNextRooms()
            }
            endPoints.add(Pair(k,start))
            endPoints.add(Pair(k,lengthOfSnakes+start))
        }

        val endPoint = endPoints.random()
        map[endPoint.first][endPoint.second].value = "#SE"

        var newEndPoint: Pair<Int, Int>
        do {
            newEndPoint = endPoints.random()
        } while (newEndPoint == endPoint)

        map[newEndPoint.first][newEndPoint.second].value = "#SU"
        revealRooms(newEndPoint.first,newEndPoint.second)
    }
}

var generatedMap = Map(20,20)

