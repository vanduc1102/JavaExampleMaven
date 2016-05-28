package com.zetcode;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ShearingEx extends JFrame {

    private static final long serialVersionUID = 1L;

    public ShearingEx() {

        initUI();
    }

    private void initUI() {

        add(new Surface());

        setTitle("Shearing");
        setSize(800, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        final AffineTransform rotate = AffineTransform.getRotateInstance(Math.toRadians(270), 0, 100);
        double[] matrix = new double[6];
        rotate.getMatrix(matrix);
        for (int i = 0; i < 6; i++) {
            System.out.println(matrix[i]);
        }
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                ShearingEx ex = new ShearingEx();
                ex.setVisible(true);
            }
        });
    }
}

class Surface extends JPanel {

    private static final long serialVersionUID = 1L;

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g.create();

//        AffineTransform tx1 = new AffineTransform();
//        tx1.translate(0, 0);
//
//        g2d.setTransform(tx1);
//        g2d.setPaint(Color.green);
//        g2d.drawRect(0, 0, 160, 50);
//
//        AffineTransform tx2 = new AffineTransform();
//        tx2.translate(50, 90);
//        //tx2.shear(0, 1);
//
//        g2d.setTransform(tx2);
//        g2d.setPaint(Color.blue);
//
//        g2d.draw(new Rectangle(0, 0, 80, 50));
        AffineTransform tx3 = new AffineTransform(0, 1, -1, 0, 100, 100);
//        AffineTransform tx3 = new AffineTransform();
//        tx3.translate(0, 0);
//        tx3.shear(1,-2);

        g2d.setTransform(tx3);
        g2d.setPaint(Color.red);
        g2d.drawRect(0, 0, 100, 50);
        g2d.drawString("Hi Love", 25, 25);
        g2d.dispose();
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }
}
