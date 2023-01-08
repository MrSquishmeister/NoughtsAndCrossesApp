package com.lee.noughtsandcrosses

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.lee.noughtsandcrosses.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {

    enum class Turn{
        NOUGHT,
        CROSS
    }

    private var noughtsScore = 0
    private var crossesScore = 0

    private var firstTurn = Turn.NOUGHT
    private var currentTurn = Turn.NOUGHT

    private var boardList = mutableListOf<Button>()

    private lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBoard()

        binding.mainButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initBoard()
    {
        boardList.add(binding.a1)
        boardList.add(binding.a2)
        boardList.add(binding.a3)
        boardList.add(binding.b1)
        boardList.add(binding.b2)
        boardList.add(binding.b3)
        boardList.add(binding.c1)
        boardList.add(binding.c2)
        boardList.add(binding.c3)
    }

    fun boardTapped(view: View){
        if(view !is Button){
            return
        }
        addToBoard(view)

        if(checkWin(NOUGHT)){
            noughtsScore++
            result("Noughts Won")
        }
        if(checkWin(CROSS)){
            crossesScore++
            result("Crosses Won")
        }

        if(fullBoard()){
            result("Draw")
        }

    }

    private fun checkWin(s: String): Boolean {
        //Horizontal Win
        if(match(binding.a1, s) && match(binding.a2, s) && match(binding.a3, s) ) {
            return true
        }
        if(match(binding.b1, s) && match(binding.b2, s) && match(binding.b3, s) ) {
            return true
        }
        if(match(binding.c1, s) && match(binding.c2, s) && match(binding.c3, s) ) {
            return true
        }

        //Vertical Win
        if(match(binding.a1, s) && match(binding.b1, s) && match(binding.c1, s) ) {
            return true
        }
        if(match(binding.a2, s) && match(binding.b2, s) && match(binding.c2, s) ) {
            return true
        }
        if(match(binding.a3, s) && match(binding.b3, s) && match(binding.c3, s) ) {
            return true
        }

        //Diagonal Win
        if(match(binding.a1, s) && match(binding.b2, s) && match(binding.c3, s) ) {
            return true
        }
        if(match(binding.a3, s) && match(binding.b2, s) && match(binding.c1, s) ) {
            return true
        }

        return false
    }

    private fun match(button: Button, symbol: String) = button.text == symbol

    private fun result(title: String) {

        val scores = "\nNoughts $noughtsScore \nCrosses $crossesScore"

        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(scores)
            .setPositiveButton("Reset"){
                _, _->
                resetBoard()
            }
            .setCancelable(false)
            .show()
    }

    private fun resetBoard() {
        for(button in boardList){
            button.text = ""
        }

        if(firstTurn == Turn.NOUGHT){
            firstTurn = Turn.CROSS
        }
        else if (firstTurn == Turn.CROSS){
            firstTurn = Turn.NOUGHT
        }
        currentTurn = firstTurn
        setTurnLabel()
    }

    private fun fullBoard(): Boolean {
        for (button in boardList){
            if(button.text == ""){
                return false
            }
        }
        return true
    }

    private fun addToBoard(button: Button) {
        if(button.text != ""){
            return
        }

        if(currentTurn == Turn.NOUGHT){
            button.text = NOUGHT
            currentTurn = Turn.CROSS
        }
        else if(currentTurn == Turn.CROSS){
            button.text = CROSS
            currentTurn = Turn.NOUGHT
        }
        setTurnLabel()
    }

    private fun setTurnLabel() {
        var turnText = ""
        if(currentTurn == Turn.CROSS){
            turnText = "Turn $CROSS"
        }
        else if (currentTurn == Turn.NOUGHT){
            turnText = "Turn $NOUGHT"
        }
        binding.turnLabel.text = turnText
    }

    companion object{
        const val NOUGHT = "O"
        const val CROSS = "X"
    }


}