package org.bmsoft.game;

import org.bmsoft.graphics.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.util.Random;

public class Game extends Canvas implements Runnable {
    private static final long serializedVersionUID = 1L;

    public static int scale = 3;
    public static int width = 1440 / scale;
    public static int height = width / 16 * 9;
    private Thread thread;
    public JFrame frame;
    private boolean running = false;
    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

    private Screen screen;

    public Game() {
        Dimension size = new Dimension(width * scale, height * scale);
        setPreferredSize(size);
        frame = new JFrame();
        screen = new Screen(width, height);
    }

    public synchronized void start() {
        running = true;
        thread = new Thread(this, "Display");
        thread.start();
    }

    public synchronized void stop() {
        running = false;

        try {
            thread.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 120.0;
        double deltaTime = 0.0;
        double updates = 0.0;
        double frames = 0.0;
        while (running) {
            long now = System.nanoTime();
            deltaTime += (now - lastTime) / ns;
            lastTime = now;

            while (deltaTime >= 1) {
                update();
                updates++;
                deltaTime--;
            }

            render();
            frames++;

            if((System.currentTimeMillis() - timer) > 1000) {
                timer += 1000;
                System.out.println("UPS: " + updates + ", FPS: " + frames);
                frames = 0;
                updates = 0;
            }
        }
    }

    public void update() {}

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        screen.clear();
        screen.render();
        for(int i = 0; i < pixels.length; i++) {
            pixels[i] = screen.pixels[i];
        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
        bs.show();
    }
}
