package com.example.project_tictactoe

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState!=null)
        {
            findViewById<TextView>(R.id.text_view_p1).text=savedInstanceState.getString("Player1")
            findViewById<TextView>(R.id.text_view_p2).text=savedInstanceState.getString("Player2")
            p1Score=savedInstanceState.getInt("Player1Score",0)
            p2Score=savedInstanceState.getInt("Player2Score",0)
        }
        var reset: Button = findViewById(R.id.button_reset)
        reset.setOnClickListener { reset() }
    }
    

    fun reset() {
        p1Score = 0
        p2Score = 0
        findViewById<TextView>(R.id.text_view_p1).setText("Player 1 : $p1Score")
        findViewById<TextView>(R.id.text_view_p2).setText("Computer : $p2Score")
        initial()
    }

    fun buClick(view: View) {
        val buSelected = view as Button
        var cellID = 0
        when (buSelected.id) {
            R.id.button_00 -> cellID = 1
            R.id.button_01 -> cellID = 2
            R.id.button_02 -> cellID = 3
            R.id.button_10 -> cellID = 4
            R.id.button_11 -> cellID = 5
            R.id.button_12 -> cellID = 6
            R.id.button_20 -> cellID = 7
            R.id.button_21 -> cellID = 8
            R.id.button_22 -> cellID = 9

        }

        playGame(cellID, buSelected)
    }

    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()
    var p1Score = 0
    var p2Score = 0
    var roundCount=0

    var activePlayer = 1

    private fun playGame(cellID: Int, buSelected: Button) {

        if (activePlayer == 1) {
            roundCount++
            buSelected.text = "X"
            buSelected.setBackgroundColor(Color.parseColor("#009193"))
            player1.add(cellID)
            activePlayer = 2
            if(roundCount==9)
            {
                   Toast.makeText(this," Draw ",Toast.LENGTH_SHORT)
                    initial()

            }
            AutoPlay()
        } else {
            roundCount++
            buSelected.text = "o"
            buSelected.setBackgroundColor(Color.parseColor("#FF9300"))
            player2.add(cellID)
            activePlayer = 1
            if(roundCount==9)
            {
                Toast.makeText(this," Draw ",Toast.LENGTH_SHORT)
                initial()

            }
        }

        buSelected.isEnabled = false;
        checkWinner()
    }

    private fun checkWinner() {
        var winner: Int
        winner = (if ((player1.contains(1) && player1.contains(2) && player1.contains(3)) ||
            (player1.contains(4) && player1.contains(5) && player1.contains(6)) ||
            (player1.contains(7) && player1.contains(8) && player1.contains(9)) ||
            (player1.contains(1) && player1.contains(4) && player1.contains(7)) ||
            (player1.contains(2) && player1.contains(5) && player1.contains(8)) ||
            (player1.contains(3) && player1.contains(6) && player1.contains(9)) ||
            (player1.contains(1) && player1.contains(5) && player1.contains(9)) ||
            (player1.contains(3) && player1.contains(5) && player1.contains(7))
        ) {
            1
        } else if ((player2.contains(1) && player2.contains(2) && player2.contains(3)) ||
            (player2.contains(4) && player2.contains(5) && player2.contains(6)) ||
            (player2.contains(7) && player2.contains(8) && player2.contains(9)) ||
            (player2.contains(1) && player2.contains(4) && player2.contains(7)) ||
            (player2.contains(2) && player2.contains(5) && player2.contains(8)) ||
            (player2.contains(3) && player2.contains(6) && player2.contains(9)) ||
            (player2.contains(1) && player2.contains(5) && player2.contains(9)) ||
            (player2.contains(3) && player2.contains(5) && player2.contains(7))
        ) {
            2
        } else {
            -1
        })
        if (winner != -1) {
            var textview: TextView
            if (winner == 1) {
                textview = findViewById(R.id.text_view_p1)
                p1Score++
                textview.setText("Player 1 : $p1Score")
                Toast.makeText(this, " Player 1  win the game", Toast.LENGTH_LONG).show()
                initial()
            } else {
                textview = findViewById(R.id.text_view_p2)
                p2Score++
                textview.setText("Computer : $p2Score")
                Toast.makeText(this, " Player 2  win the game", Toast.LENGTH_LONG).show()
                initial()

            }

        }
    }

    private fun initial() {
        var button = ArrayList<Button>()
        button.add(findViewById<Button>(R.id.button_00))
        button.add(findViewById<Button>(R.id.button_01))
        button.add(findViewById<Button>(R.id.button_02))
        button.add(findViewById<Button>(R.id.button_10))
        button.add(findViewById<Button>(R.id.button_11))
        button.add(findViewById<Button>(R.id.button_12))
        button.add(findViewById<Button>(R.id.button_20))
        button.add(findViewById<Button>(R.id.button_21))
        button.add(findViewById<Button>(R.id.button_22))
        for (i in button) {
            i.isEnabled = true
            i.setBackgroundColor(Color.parseColor("#FFFFFF"))
            i.setText("")
        }
        player1.clear()
        player2.clear()
        roundCount=0

    }

    private fun AutoPlay() {
        val emptyCells = ArrayList<Int>()
        for (cellID in 1..9) {
            if (!(player1.contains(cellID) || player2.contains(cellID))) {
                emptyCells.add(cellID)
            }
        }

        val r = Random()
        val randIndex = r.nextInt(emptyCells.size - 0) + 0
        val cellID = emptyCells[randIndex]

        var buSelected: Button
        when (cellID) {
            1 -> buSelected = button_00
            2 -> buSelected = button_01
            3 -> buSelected = button_02
            4 -> buSelected = button_10
            5 -> buSelected = button_11
            6 -> buSelected = button_12
            7 -> buSelected = button_20
            8 -> buSelected = button_21
            9 -> buSelected = button_22
            else -> buSelected = button_00

        }

        playGame(cellID, buSelected)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("Player1",findViewById<TextView>(R.id.text_view_p1).text.toString())
        outState.putString("Player2",findViewById<TextView>(R.id.text_view_p2).text.toString())
        outState.putInt("Player1Score",p1Score)
        outState.putInt("Player2Score",p2Score)
    }
}