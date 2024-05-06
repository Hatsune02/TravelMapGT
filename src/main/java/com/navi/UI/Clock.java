package com.navi.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Clock extends JPanel implements Runnable, ActionListener {
    public int horas = 0;
    public int minutos = 0;
    public int segundos = 0;
    public boolean running = true;
    public boolean paused = false;
    public boolean advancing = false;
    public boolean delaying = false;

    public Clock() {
        Timer timer = new Timer(1000, this);
        timer.start();

        /*JButton btnAdelantarHora = new JButton(">");
        btnAdelantarHora.addActionListener(e -> {
            horas++;
            if (horas >= 24) {
                horas = 0;
            }
            repaint();
        });

        JButton btnRetrasarHora = new JButton("<");
        btnRetrasarHora.addActionListener(e -> {
            horas--;
            if (horas < 0) {
                horas = 23;
            }
            repaint();
        });
        JButton btnPausar = new JButton("||");
        btnPausar.setSize(30,30);
        btnPausar.addActionListener(e -> {
            paused = !paused;
            repaint();
        });

        add(btnRetrasarHora);
        add(btnPausar);
        add(btnAdelantarHora);*/
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1000);
                if (!paused) {
                    if (advancing) {
                        adelantarTiempo();
                    } else if (delaying) {
                        retrasarTiempo();
                    } else {
                        avanzarTiempo();
                    }
                    repaint();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void avanzarTiempo() {
        segundos++;
        if (segundos >= 60) {
            segundos = 0;
            minutos++;
            if (minutos >= 60) {
                minutos = 0;
                horas++;
                if (horas >= 24) {
                    horas = 0;
                }
            }
        }
        repaint();
    }

    public void adelantarTiempo() {
        minutos +=10;
        if (minutos >= 60) {
            minutos = 0;
            horas++;
            if (horas >= 24) {
                horas = 0;
            }
        }
        repaint();
    }

    public void retrasarTiempo() {
        minutos -= 10;
        if (minutos < 0) {
            minutos = 59;
            horas--;
            if (horas < 0) {
                horas = 23;
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        String time = String.format("%02d:%02d:%02d", horas, minutos, segundos);
        g.setFont(new Font("Arial", Font.PLAIN, 28));
        g.drawString(time, 20, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

}
