package org.bmsoft

import org.bmsoft.game.Game
import javax.swing.JFrame

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        println("Game started")
        val game = Game()
        game.frame.isResizable = false
        game.frame.title = "Game of life"
        game.frame.add(game)
        game.frame.pack()
        game.frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        game.frame.setLocationRelativeTo(null)
        game.frame.isVisible = true

        game.start()
    }
}