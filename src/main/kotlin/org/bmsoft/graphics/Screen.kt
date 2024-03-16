package org.bmsoft.graphics

import java.util.*
import kotlin.random.Random

class Screen(private val width: Int, private val height: Int) {
    var pixels: IntArray = IntArray(this.width * this.height)

    fun render() {
        for (y in 0 until this.height) {
            for (x in 0 until this.width) {
                pixels[Random.nextInt(width) + Random.nextInt(height) * width] = 0xFFFFFF
            }
        }
    }

    fun clear() {
        for (i in pixels.indices) {
            pixels[i] = 0
        }
    }
}