package com.navi.UI;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDarkerIJTheme;
import com.navi.backend.structures.b_tree.BTree;
import com.navi.backend.structures.graph.*;
import com.navi.backend.objs.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class DashBoard extends javax.swing.JFrame {
    private Graph graph = new Graph();

    public DashBoard() {
        initComponents();
        emptyAll();
        initTypeComboBox();
        Thread thread = new Thread(panelClock);
        thread.start();
    }
    private void emptyAll(){
        graph = new Graph();
        actualPlaceLabel.setText("-");
        destinationComboBox.removeAllItems();
        moveComboBox.removeAllItems();
        originComboBox.removeAllItems();
        textTravel.setText("");
    }

    private void initTypeComboBox(){
        typeComboBox.removeAllItems();
        typeComboBox.addItem("Distancia (Vehiculo)");
        typeComboBox.addItem("Distancia (Caminando)");
        typeComboBox.addItem("Gasolina (Vehiculo)");
        typeComboBox.addItem("Desgaste Fisico");
        typeComboBox.addItem("Distancia/Gasolina (Vehiculo)");
        typeComboBox.addItem("Distancia/Desgaste Fisico");
        typeComboBox.addItem("Rapidez (Vehiculo)");
        typeComboBox.addItem("Rapidez (Caminando)");
        moveButton.setEnabled(false);
    }

    private String loadFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            StringBuilder content = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");

                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return content.toString();
        }

        return "";
    }
    private void createRoutes(String routesText){
        ArrayList<String> places = new ArrayList<>();
        String[] lines = routesText.split("\n");
        for (String line : lines) {
            String[] attributes = line.split("\\|");
            String origin = attributes[0];
            String destination = attributes[1];
            int timeV = Integer.parseInt(attributes[2]);
            int timeW = Integer.parseInt(attributes[3]);
            int gas = Integer.parseInt(attributes[4]);
            int effort = Integer.parseInt(attributes[5]);
            int distance = Integer.parseInt(attributes[6]);


            Route route = new Route(origin, destination, timeV, timeW, gas, effort, distance, new ArrayList<>());
            graph.addEdge(route);
            if(!places.contains(origin)) places.add(origin);
            if(!places.contains(destination)) places.add(destination);
        }

        fillComboBoxTrip(places);
    }
    private void fillComboBoxTrip(ArrayList<String> places){
        for(var x: places){
            originComboBox.addItem(x);
            destinationComboBox.addItem(x);
        }
    }
    private void createTraffic(String text){
        String[] lines = text.split("\n");
        for (String line : lines) {
            String[] attributes = line.split("\\|");
            String origin = attributes[0];
            String destination = attributes[1];
            int start = Integer.parseInt(attributes[2]);
            int end = Integer.parseInt(attributes[3]);
            int traffic = Integer.parseInt(attributes[4]);

            var traffic1 = new Traffic(origin, destination, start, end, traffic);
            for(var x: graph.getEdges().get(origin)){
                if(x.getDestiny().equals(destination)) x.getTrafficSchedules().add(traffic1);
            }
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        panelTree = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        originComboBox = new javax.swing.JComboBox<>();
        destinationComboBox = new javax.swing.JComboBox<>();
        typeComboBox = new javax.swing.JComboBox<>();
        startTrip = new javax.swing.JButton();
        cancelTrip = new javax.swing.JButton();
        worstRouteButton = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        panelClock = new Clock();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        actualPlaceLabel = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        moveComboBox = new javax.swing.JComboBox<>();
        moveButton = new javax.swing.JButton();
        treeButton = new javax.swing.JButton();
        delayButton = new javax.swing.JButton();
        pauseButton = new javax.swing.JButton();
        advanceButton = new javax.swing.JButton();
        scroll = new javax.swing.JScrollPane();
        textTravel = new javax.swing.JTextPane();
        background = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        editorPane = new javax.swing.JEditorPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TravelMap GT");

        panelTree.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 204, 255), 1, true));

        jLabel1.setText("Origen:");

        jLabel2.setText("Destino:");

        jLabel3.setText("Tipo: ");

        originComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        destinationComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        typeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        startTrip.setBackground(new java.awt.Color(0, 102, 102));
        startTrip.setForeground(new java.awt.Color(255, 255, 255));
        startTrip.setText("Iniciar viaje");
        startTrip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startTripActionPerformed(evt);
            }
        });

        cancelTrip.setBackground(new java.awt.Color(126, 0, 0));
        cancelTrip.setForeground(new java.awt.Color(255, 255, 255));
        cancelTrip.setText("Cancelar");
        cancelTrip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelTripActionPerformed(evt);
            }
        });

        worstRouteButton.setText("Peor Ruta");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(18, 18, 18)
                                                .addComponent(originComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addGap(30, 30, 30)
                                                .addComponent(typeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(destinationComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(worstRouteButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(startTrip, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cancelTrip)))
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(originComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(destinationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(typeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(startTrip, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                        .addComponent(cancelTrip, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                        .addComponent(worstRouteButton)))
        );

        jLabel4.setFont(new java.awt.Font("Liberation Sans", 0, 24)); // NOI18N
        jLabel4.setText(" VIAJE");

        javax.swing.GroupLayout panelClockLayout = new javax.swing.GroupLayout(panelClock);
        panelClock.setLayout(panelClockLayout);
        panelClockLayout.setHorizontalGroup(
                panelClockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 178, Short.MAX_VALUE)
        );
        panelClockLayout.setVerticalGroup(
                panelClockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 40, Short.MAX_VALUE)
        );

        jLabel5.setFont(new java.awt.Font("Liberation Sans", 0, 24)); // NOI18N
        jLabel5.setText("UBICACIÓN ACTUAL:");

        actualPlaceLabel.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        actualPlaceLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        actualPlaceLabel.setText("Guatemala");

        jLabel7.setText("Mover:");

        moveComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        moveButton.setBackground(new java.awt.Color(0, 102, 102));
        moveButton.setForeground(new java.awt.Color(255, 255, 255));
        moveButton.setText("Moverse");
        moveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(actualPlaceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(moveComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(moveButton)))
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(actualPlaceLabel)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel7)
                                        .addComponent(moveComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(moveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        treeButton.setForeground(new java.awt.Color(255, 255, 255));
        treeButton.setText("Arbol");
        treeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                treeButtonActionPerformed(evt);
            }
        });

        delayButton.setForeground(new java.awt.Color(255, 255, 255));
        delayButton.setText("<");
        delayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delayButtonActionPerformed(evt);
            }
        });

        pauseButton.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        pauseButton.setForeground(new java.awt.Color(255, 255, 255));
        pauseButton.setText("■");
        pauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseButtonActionPerformed(evt);
            }
        });

        advanceButton.setForeground(new java.awt.Color(255, 255, 255));
        advanceButton.setText(">");
        advanceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                advanceButtonActionPerformed(evt);
            }
        });

        scroll.setViewportView(textTravel);

        javax.swing.GroupLayout panelTreeLayout = new javax.swing.GroupLayout(panelTree);
        panelTree.setLayout(panelTreeLayout);
        panelTreeLayout.setHorizontalGroup(
                panelTreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelTreeLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panelTreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panelTreeLayout.createSequentialGroup()
                                                .addGroup(panelTreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(panelTreeLayout.createSequentialGroup()
                                                                .addGroup(panelTreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGroup(panelTreeLayout.createSequentialGroup()
                                                                                .addComponent(panelClock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                .addComponent(delayButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(pauseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(advanceButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addGap(0, 0, Short.MAX_VALUE)))
                                                .addContainerGap())
                                        .addGroup(panelTreeLayout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addGroup(panelTreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(treeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        panelTreeLayout.setVerticalGroup(
                panelTreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelTreeLayout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(panelTreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panelTreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(delayButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(pauseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(advanceButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(panelTreeLayout.createSequentialGroup()
                                                .addComponent(panelClock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel4)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(36, 36, 36)
                                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(treeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        background.setBackground(new java.awt.Color(0, 153, 102));

        jScrollPane1.setViewportView(editorPane);

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
                backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
        );
        backgroundLayout.setVerticalGroup(
                backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1)
        );

        jMenu1.setText("  Archivo  ");

        jMenuItem1.setText("Cargar ubicaciones");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Cargar horario tráfico");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(panelTree, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panelTree, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {
        emptyAll();
        String routes = loadFile();
        assert routes != null;
        createRoutes(routes);
        GraphvizG.drawGraphviz(graph, true, TypeGraph.DISTANCE, new ArrayList<>());
        GraphvizG.showHTML(editorPane);
        SwingUtilities.updateComponentTreeUI(editorPane);
    }
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {
        String traffic = loadFile();
        assert traffic != null;
        for(var x: graph.getEdges().entrySet()){
            for(var y: x.getValue()){
                y.setTrafficSchedules(new ArrayList<>());
            }
        }
        for(var x: graph.getEdgesNotDirected().entrySet()){
            for(var y: x.getValue()){
                y.setTrafficSchedules(new ArrayList<>());
            }
        }
        createTraffic(traffic);
    }
    private void delayButtonActionPerformed(java.awt.event.ActionEvent evt) {
        panelClock.retrasarTiempo();
    }
    private void pauseButtonActionPerformed(java.awt.event.ActionEvent evt) {
        panelClock.paused = !panelClock.paused;
        repaint();
    }
    private void advanceButtonActionPerformed(java.awt.event.ActionEvent evt) {
        panelClock.adelantarTiempo();
    }

    int typeBox = 0;
    boolean vehicle;
    private void disableAll(){
        startTrip.setEnabled(false);
        originComboBox.setEnabled(false);
        destinationComboBox.setEnabled(false);
        typeComboBox.setEnabled(false);
        worstRouteButton.setEnabled(false);
        moveButton.setEnabled(true);
    }
    private void enableAll(){
        startTrip.setEnabled(true);
        originComboBox.setEnabled(true);
        destinationComboBox.setEnabled(true);
        typeComboBox.setEnabled(true);
        worstRouteButton.setEnabled(true);
        moveButton.setEnabled(false);
    }
    private void fillMovesComboBox(ArrayList<String> moves){
        moveComboBox.removeAllItems();
        for(var x: moves){
            moveComboBox.addItem(x);
        }
    }

    private void obtainTypeGraph(){
        typeBox = typeComboBox.getSelectedIndex();
        if(typeBox == 0) {
            typeBox = TypeGraph.DISTANCE;
            vehicle = true;
        }
        else if(typeBox == 1) typeBox = TypeGraph.DISTANCE;
        else if(typeBox == 2) {
            typeBox = TypeGraph.GAS;
            vehicle = true;
        }
        else if(typeBox == 3) typeBox = TypeGraph.EFFORT;
        else if(typeBox == 4) {
            typeBox = TypeGraph.GAS_DISTANCE;
            vehicle = true;
        }
        else if(typeBox == 5) typeBox = TypeGraph.EFFORT_DISTANCE;
        else if(typeBox == 6) {
            typeBox = TypeGraph.SPEED_V;
            vehicle = true;
        }
        else if(typeBox == 7) typeBox = TypeGraph.SPEED_W;
        else typeBox = 0;
    }
    private void startTripActionPerformed(java.awt.event.ActionEvent evt) {
        vehicle = false;
        obtainTypeGraph();
        String origin = (String) originComboBox.getSelectedItem();
        String destination = (String) destinationComboBox.getSelectedItem();
        findRoute(origin, destination);
        var moves = graph.possibleMoves(origin, destination, vehicle);
        disableAll();
        fillMovesComboBox(moves);

    }
    ArrayList<String> path = new ArrayList<>();
    private void findRoute(String origin, String destination){
        ArrayList<String> optimalRoute;
        if(!worstRouteButton.isSelected()) {
            /*if(type == TypeGraph.DISTANCE) {
                if(vehicle) {
                    optimalRoute = graph.findOptimalRouteDistanceV(origin, destination);
                }
                else optimalRoute = graph.findOptimalRouteDistanceW(origin, destination);
            }
            else if(type == TypeGraph.GAS) optimalRoute = graph.findOptimalRouteGas(origin, destination);
            else if(type == TypeGraph.GAS_DISTANCE) optimalRoute = graph.findOptimalRouteGasDistance(origin, destination);
            else if(type == TypeGraph.SPEED_V) optimalRoute = graph.findOptimalSpeedV(origin ,destination, panelClock.horas);
            else if(type == TypeGraph.EFFORT) optimalRoute = graph.findOptimalRouteEffort(origin, destination);
            else if(type == TypeGraph.EFFORT_DISTANCE) optimalRoute = graph.findOptimalRouteEffortDistance(origin, destination);
            else if(type == TypeGraph.SPEED_W) optimalRoute = graph.findOptimalSpeedW(origin, destination, panelClock.horas);
*/

            optimalRoute = graph.shortestPath(origin, destination, typeBox, panelClock.horas, vehicle);
            textTravel.setText("La mejor ruta a seguir es:\n" + optimalRoute);
        }
        else{
            optimalRoute = graph.longestPath(origin, destination, typeBox, panelClock.horas, vehicle);
            textTravel.setText("La peor ruta a seguir es:\n" + optimalRoute);
        }
        path = optimalRoute;
        actualPlaceLabel.setText(origin);
        if(optimalRoute.size() == 1){
            textTravel.setText("No existe ruta para llegar\n");
            GraphvizG.drawGraphviz(graph, vehicle, typeBox, new ArrayList<>());
        }
        else GraphvizG.drawGraphviz(graph, vehicle, typeBox, optimalRoute);

        GraphvizG.showHTML(editorPane);
        SwingUtilities.updateComponentTreeUI(editorPane);
    }

    private void cancelTripActionPerformed(java.awt.event.ActionEvent evt) {
        enableAll();
        textTravel.setText("");
    }

    private void moveButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String origin = (String) moveComboBox.getSelectedItem();
        String destination = (String) destinationComboBox.getSelectedItem();
        if(!origin.equals(destination)){
            findRoute(origin, destination);
            var moves = graph.possibleMoves(origin, destination, vehicle);
            fillMovesComboBox(moves);
        }
        else{
            textTravel.setText("HAS LLEGADO A TU DESTINO");
            actualPlaceLabel.setText(destination);
            moveComboBox.removeAllItems();
            enableAll();
            var x = new ArrayList<String>();
            x.add(destination);
            GraphvizG.drawGraphviz(graph, vehicle, typeBox, x);
            GraphvizG.showHTML(editorPane);
            SwingUtilities.updateComponentTreeUI(editorPane);
        }
    }

    private void treeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        BTree bTree = new BTree(3);
        if(!path.isEmpty()){
            String start = actualPlaceLabel.getText();
            String end = (String) destinationComboBox.getSelectedItem();

            bTree.insertar(0, start);
            for (ArrayList<String> path : graph.allPaths(start, end, vehicle)) {
                System.out.print("Ruta: ");
                for (int i = 0; i < path.size(); i++) {
                    System.out.print(path.get(i));
                    if (i < path.size() - 1) {
                        System.out.print(" -> ");
                    }
                    if(i>0){
                        var route = getDestinationW(path.get(i-1), path.get(i));
                        if(route != null) {
                            double distance = graph.getDistanceNode(typeBox, route, panelClock.horas);
                            if(distance < 0) distance = (distance * -1);
                            bTree.insertar(distance, route.getDestiny());
                        }
                    }
                }
                System.out.println();
            }
            bTree.drawGraphviz();
            bTree.showBTree();
            var arbolB = new JEditorPane();
            PNGFrame xd = new PNGFrame();
            xd.init(arbolB);
            SwingUtilities.updateComponentTreeUI(arbolB);
        }
    }
    private Route getDestinationW(String origin, String destination){
        ArrayList<Route> routes;
        if(vehicle) routes = graph.getEdges().get(origin);
        else routes = graph.getEdgesNotDirected().get(origin);
        for (var r: routes){
            if(r.getDestiny().equals(destination)){
                return r;
            }
        }
        return null;
    }

    // Variables declaration - do not modify
    private javax.swing.JLabel actualPlaceLabel;
    private javax.swing.JButton advanceButton;
    private javax.swing.JPanel background;
    private javax.swing.JButton cancelTrip;
    private javax.swing.JButton delayButton;
    private javax.swing.JComboBox<String> destinationComboBox;
    private javax.swing.JEditorPane editorPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton moveButton;
    private javax.swing.JComboBox<String> moveComboBox;
    private javax.swing.JComboBox<String> originComboBox;
    private Clock panelClock;
    private javax.swing.JPanel panelTree;
    private javax.swing.JButton pauseButton;
    private javax.swing.JScrollPane scroll;
    private javax.swing.JButton startTrip;
    private javax.swing.JTextPane textTravel;
    private javax.swing.JButton treeButton;
    private javax.swing.JComboBox<String> typeComboBox;
    private javax.swing.JRadioButton worstRouteButton;
    // End of variables declaration
}
