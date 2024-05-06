package com.navi.UI;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PNGFrame {
    JFrame frame;
    JScrollPane scroll;

    public void init(JEditorPane png){
        frame = new JFrame();

        frame.setLayout(new BorderLayout());
        frame.setSize(500,300);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Arbol-B");
        scroll = new JScrollPane(png);
        frame.add(BorderLayout.CENTER, scroll);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        showHTML(png);
    }

    public void showHTML(JEditorPane panel){
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        File rec = new File("bTree.html");
        panel.setEditable(false);
        try {
            panel.setPage(rec.toURI().toURL());
        }catch (Exception e){}
    }
}
