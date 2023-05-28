package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener{
    private var player = true
    private var turnCount = 0
    private var boardStatus = Array(3){IntArray(3)}
    private lateinit var binding: ActivityMainBinding
    private lateinit var board: Array<Array<Button>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val button = binding.button
        val button2 = binding.button2
        val button3 = binding.button3
        val button4 = binding.button4
        val button5 = binding.button5
        val button6 = binding.button6
        val button7 = binding.button7
        val button8 = binding.button8
        val button9 = binding.button9
        val resetBtn = binding.resetBtn

        board = arrayOf(
            arrayOf(button,button2,button3),
            arrayOf(button4,button5,button6),
            arrayOf(button7,button8,button9)
        )

        for(i in board){
            for(j:Button in i){
                j.setOnClickListener(this)
            }
        }

        initialiseBoardStatus()

        resetBtn.setOnClickListener {
            initialiseBoardStatus()
        }
    }

    private fun initialiseBoardStatus() {
        turnCount = 0
        player = true
        updateDisplay("Player X Turn")
        for(i in 0..2){
            for(j in 0..2){
                boardStatus[i][j] = -1
                board[i][j].isEnabled = true
                board[i][j].text = ""
            }
        }
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.button->{
                updateValue(row = 0, col = 0, player = player)
            }
            R.id.button2->{
                updateValue(row = 0, col = 1, player = player)
            }
            R.id.button3->{
                updateValue(row = 0, col = 2, player = player)
            }
            R.id.button4->{
                updateValue(row = 1, col = 0, player = player)
            }
            R.id.button5->{
                updateValue(row = 1, col = 1, player = player)
            }
            R.id.button6->{
                updateValue(row = 1, col = 2, player = player)
            }
            R.id.button7->{
                updateValue(row = 2, col = 0, player = player)
            }
            R.id.button8->{
                updateValue(row = 2, col = 1, player = player)
            }
            R.id.button9->{
                updateValue(row = 2, col = 2, player = player)
            }
        }
        checkWinner()
        if(!binding.displayTv.text.contains("Winner")) {
            player = !player
            if (player) {
                updateDisplay("Player X Turn")
            } else {
                updateDisplay("Player O Turn")
            }
            turnCount++
            if (turnCount == 9) {
                updateDisplay("Game Draw")
            }
        }
    }

    private fun checkWinner() {
        //Horizontal Rows
        for(i in 0 ..2){
            if(boardStatus[i][0] == boardStatus[i][1] && boardStatus[i][0] == boardStatus[i][2]){
                if(boardStatus[i][0] == 1){
                    updateDisplay("Player X Winner")
                    break
                }else if(boardStatus[i][0] == 0){
                    updateDisplay("Player O Winner")
                    break
                }
            }
        }


        //Vertical Columns
        for(j in 0..2){
            if(boardStatus[0][j] == boardStatus[1][j] && boardStatus[0][j] == boardStatus[2][j]){
                if(boardStatus[0][j] == 1){
                    updateDisplay("Player X Winner")
                    break
                }else if(boardStatus[0][j] == 0){
                    updateDisplay("Player O Winner")
                    break
                }
            }
        }

        //First Diagonal
        if(boardStatus[0][0] == boardStatus[1][1] && boardStatus[0][0] == boardStatus[2][2]){
            if(boardStatus[0][0] == 1){
                updateDisplay("Player X Winner")
            }else if(boardStatus[0][0] == 0){
                updateDisplay("Player O Winner")
            }
        }

        //Second Diagonal
        if(boardStatus[0][2] == boardStatus[1][1] && boardStatus[0][2] == boardStatus[2][0]){
            if(boardStatus[0][2] == 1){
                updateDisplay("Player X Winner")
            }else if(boardStatus[0][2] == 0){
                updateDisplay("Player O Winner")
            }
        }
    }

    private fun updateDisplay(s: String) {
        binding.displayTv.text = s
        if(binding.displayTv.text.contains("Winner")){
            disableButton()
        }
    }

    private fun disableButton() {
        for(i in board){
            for(j in i){
                j.isEnabled = false
            }
        }
    }

    private fun updateValue(row: Int, col: Int, player: Boolean) {
        val text = if(player) "X" else "O"
        val value = if(player) 1 else 0
        board[row][col].apply {
            isEnabled = false
            setText(text)
        }
        boardStatus[row][col] = value
    }
}