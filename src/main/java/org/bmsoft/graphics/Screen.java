package org.bmsoft.graphics;

import java.util.Random;

public class Screen {
    private final int width;
    private final int height;

    public int[] pixels;

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[this.width * this.height];
    }

    public void render() {
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                pixels[x + y * width] = new Random().nextInt();
            }
        }
    }

    public void clear() {
        for(int i = 0; i < pixels.length; i++) {
            pixels[i] = 0x000000;
        }
    }
}
