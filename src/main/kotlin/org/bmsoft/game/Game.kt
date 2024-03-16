package org.bmsoft.game

import org.bmsoft.graphics.Screen
import java.awt.Canvas
import java.awt.Dimension
import java.awt.image.BufferedImage
import java.awt.image.DataBufferInt
import javax.swing.JFrame

class Game : Canvas(), Runnable {
    private var thread: Thread? = null
    var frame: JFrame
    private var running = false
    private val image = BufferedImage(Companion.width, Companion.height, BufferedImage.TYPE_INT_RGB)
    private val pixels: IntArray = (image.raster.dataBuffer as DataBufferInt).data

    private val screen: Screen

    init {
        val size = Dimension(Companion.width * scale, Companion.height * scale)
        preferredSize = size
        frame = JFrame()
        screen = Screen(Companion.width, Companion.height)
    }

    @Synchronized
    fun start() {
        running = true
        thread = Thread(this, "Display")
        thread!!.start()
    }

    @Synchronized
    fun stop() {
        running = false

        try {
            thread!!.join()
        } catch (ex: InterruptedException) {
            ex.printStackTrace()
        }
    }

    override fun run() {
        var lastTime = System.nanoTime()
        var timer = System.currentTimeMillis()
        val ns = 1000000000.0 / 60.0
        var deltaTime = 0.0
        var updates = 0.0
        var frames = 0.0
        while (running) {
            val now = System.nanoTime()
            deltaTime += (now - lastTime) / ns
            lastTime = now

            while (deltaTime >= 1) {
                update()
                updates++
                deltaTime--
            }

            render()
            frames++

            if ((System.currentTimeMillis() - timer) > 1000) {
                timer += 1000
                println("UPS: $updates, FPS: $frames")
                frames = 0.0
                updates = 0.0
            }
        }
    }

    fun update() {}

    fun render() {
        val bs = bufferStrategy
        if (bs == null) {
            createBufferStrategy(3)
            return
        }

        screen.clear()
        screen.render()
        for (i in pixels.indices) {
            pixels[i] = screen.pixels.get(i)
        }

        val g = bs.drawGraphics
        g.drawImage(image, 0, 0, width, height, null)
        g.dispose()
        bs.show()
    }

    companion object {
        private const val serializedVersionUID = 1L

        var scale: Int = 3
        var width: Int = 300
        var height: Int = width / 16 * 9
    }
}