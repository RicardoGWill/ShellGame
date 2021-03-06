package com.ricardogwill.shellgame

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat

class MainActivity : AppCompatActivity(), View.OnClickListener, View.OnLongClickListener {

    var wins = 0.0
    var losses = 0.0
    var winRate = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpOnClickListeners()
        firstInitialize()
    }

    fun shuffleButton() {

        shuffleInitialize()

        val randomInteger = (1..3).shuffled().first()

        when (randomInteger) {
            1 -> choice1B.tag = "win"
            2 -> choice2B.tag = "win"
            3 -> choice3B.tag = "win"
            else -> explanationTV.setText("Shuffle Button Problem.")
        }
    }

    fun setUpOnClickListeners() {
        choice1B.setOnClickListener(this)
        choice2B.setOnClickListener(this)
        choice3B.setOnClickListener(this)
        shuffleB.setOnClickListener(this)

        shuffleB.setOnLongClickListener(this)

        startB.setOnLongClickListener(this)
    }

    override fun onLongClick(v: View?): Boolean {
        firstInitialize()
        return true
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.choice1B ->
                if (choice1B.tag.equals("win")) {
                    choice1B.setText("★")
                    choice2B.setText("")
                    choice3B.setText("")
                    hasWon()
                } else {
                    choice1B.setText("X")
                    hasLost()
                }
            R.id.choice2B ->
                if (choice2B.tag.equals("win")) {
                    choice1B.setText("")
                    choice2B.setText("★")
                    choice3B.setText("")
                    hasWon()
                } else {
                    choice2B.setText("X")
                    hasLost()
                }
            R.id.choice3B ->
                if (choice3B.tag.equals("win")) {
                    choice1B.setText("")
                    choice2B.setText("")
                    choice3B.setText("★")
                    hasWon()
                } else {
                    choice3B.setText("X")
                    hasLost()
                }
            R.id.shuffleB -> shuffleButton()
            else -> explanationTV.setText("OnClick Problem.")
        }

    }

    fun hasWon() {
        tallyTV.append("★")
        disallowClicks()
        wins++
        countWinsVSLosses()
    }

    fun hasLost() {
        tallyTV.append("X")
        disallowClicks()
        losses++
        countWinsVSLosses()
    }

    fun countWinsVSLosses() {
        var winLossDF = DecimalFormat("0")
        var percentDF = DecimalFormat("0.00")
        winRate = wins*100/(wins + losses)
        winsVSLossesTV.setText("${winLossDF.format(wins)} Wins and ${winLossDF.format(losses)} Losses (${percentDF.format(winRate)}% Win Rate)")
        if (winRate > 33.4) {
            oddsTV.setText("You are beating the odds!")
        } else if (winRate < 33.3) {
            oddsTV.setText("You are a bit behind the odds...")
        } else {
            oddsTV.setText("You are on par with the odds...")
        }

        if (wins + losses >= 10) {
            oddsTV.setTextSize(40.0f)
            endGame()

            if (winRate > 33.4) {
                oddsTV.setTextColor(Color.parseColor("#000099"))
                oddsTV.setText("★You beat the odds!★")
            } else if (winRate < 33.3) {
                oddsTV.setTextColor(Color.parseColor("#990000"))
                oddsTV.setText("You lost to the odds...")
            } else {
                oddsTV.setTextColor(Color.parseColor("#770099"))
                oddsTV.setText("You tied the odds.")
            }
        }

    }

    fun firstInitialize() {
        disallowClicks()
        shuffleB.visibility = Button.VISIBLE
        startB.visibility = Button.INVISIBLE
        choice1B.tag = ""
        choice1B.setText("")
        choice2B.tag = ""
        choice2B.setText("")
        choice3B.tag = ""
        choice3B.setText("")
        wins = 0.0
        losses = 0.0
        explanationTV.setText("Instructions: Press \"Shuffle\", then try to choose the one button with a star hidng in it! Long-press \"Shuffle\" to re-start!")
        explanationTV.setTextSize(24.0f)
        winsVSLossesTV.setText("")
        oddsTV.setTextSize(24.0f)
        oddsTV.setTextColor(Color.parseColor("#808080"))
        oddsTV.setText("")
        tallyTV.setText("")
    }

    fun shuffleInitialize() {
        allowClicks()
        choice1B.tag = ""
        choice1B.setText("?")
        choice2B.tag = ""
        choice2B.setText("?")
        choice3B.tag = ""
        choice3B.setText("?")
    }

    fun endGame() {
        shuffleB.visibility = Button.INVISIBLE
        startB.visibility = Button.VISIBLE
        explanationTV.setTextSize(40.0f)
        explanationTV.setText("LONG-PRESS \"START\" BUTTON TO START A NEW GAME.")
        disallowClicks()
        shuffleB.isClickable = false
    }

    fun allowClicks() {
        choice1B.isClickable = true
        choice2B.isClickable = true
        choice3B.isClickable = true
    }

    fun disallowClicks() {
        choice1B.isClickable = false
        choice2B.isClickable = false
        choice3B.isClickable = false
    }


}
