package main;

import javax.swing.JFrame;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Frame extends JFrame implements KeyListener{
    Panel panel;
    Handler h;

    // constructors
    public Frame(Handler h) {
        this.h = h;

        panel = new Panel();
        panel.setFocusable(false);

        this.setTitle("sokoban");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setFocusable(true);

        this.addKeyListener(this);

        this.setContentPane(panel);
        
        this.pack();

        this.setBounds(0, 0, 500, 500);
        this.setVisible(true);

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                h.draw();
            }
        });
    }

    public Panel getPanel() {
        return panel;
    }

    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {
        h.keyPressed(e);
    }
    public void keyReleased(KeyEvent e) {}
}
