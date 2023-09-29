package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Panel extends JPanel{

    private BufferedImage lastFrame;

    public Panel() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(lastFrame, 0, 0, null);
    }

    public void setLastFrame(BufferedImage b) {
        this.lastFrame = b;
    }
}

