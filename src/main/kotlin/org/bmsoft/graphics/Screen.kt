package org.bmsoft.graphics

import java.util.*

class Screen(private val width: Int, private val height: Int) {
    var pixels: IntArray = IntArray(this.width * this.height)

    fun render() {
        for (y in 0 until this.height) {
            for (x in 0 until this.width) {
                pixels[x + y * width] = Random().nextInt()
            }
        }
    }

    fun clear() {
        for (i in pixels.indices) {
            pixels[i] = 0x000000
        }
    }
}