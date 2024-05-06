package com.navi.backend.structures.graph;

import com.navi.backend.objs.Route;

import java.io.*;
import java.util.*;
import javax.swing.*;

public class GraphvizG {
    public static String generateGraphViz(Graph graph, boolean vehicle, int type, List<String> optimalRoute) {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph G {\n").append("  layout=\"neato\";\n");

        HashMap<String, ArrayList<Route>> edges;
        if(vehicle) edges = graph.getEdges();
        else edges = graph.getEdgesNotDirected();

        // Agregar nodos
        for (String node : edges.keySet()) {
            if(optimalRoute.contains(node)) {
                sb.append("  \"").append(node).append("\" [color=blue];\n");
            }
            else sb.append("  \"").append(node).append("\";\n");
        }

        // Agregar aristas con pesos
        for (String origin : edges.keySet()) {
            for (Route route : edges.get(origin)) {
                double routeWeight = 0;
                //ESCOGER EN BASE A QUE SE MIDE EL PESO ENTRE ARISTAS
                if(type == TypeGraph.GAS) routeWeight = route.getGas();
                else if(type == TypeGraph.GAS_DISTANCE) routeWeight = route.getGasDistance();
                else if(type == TypeGraph.DISTANCE) routeWeight = route.getDistance();
                else if(type == TypeGraph.SPEED_V) routeWeight = route.getSpeedVehicle(0);
                else if(type == TypeGraph.EFFORT) routeWeight = route.getPersonEffort();
                else if(type == TypeGraph.EFFORT_DISTANCE) routeWeight = route.getEffortDistance();
                else if(type == TypeGraph.SPEED_W) routeWeight = route.getSpeedWalk(0);

                double length = routeWeight / 10.0 ;
                boolean valid = false;
                for(int i = 0; i<optimalRoute.size(); i++){
                    if(i+1 == optimalRoute.size()) break;
                    if(optimalRoute.get(i).equals(origin) && optimalRoute.get(i+1).equals(route.getDestiny())){
                        sb.append("  \"")
                                .append(route.getOrigin())
                                .append("\" -> \"")
                                .append(route.getDestiny())
                                .append("\" [label=\"")
                                .append(routeWeight)
                                .append("\", len= 3")
                                //.append(length)
                                .append(" color=green];\n");
                        valid = true;
                        break;
                    }
                }
                if(!valid) {
                    sb.append("  \"")
                            .append(route.getOrigin())
                            .append("\" -> \"")
                            .append(route.getDestiny())
                            .append("\" [label=\"")
                            .append(routeWeight)
                            .append("\", len= 3")
                            //.append(length)
                            .append("];\n");
                }
            }
        }

        sb.append("}");
        return sb.toString();
    }
    public static void writeFile(String dir, String content){
        FileWriter file = null;
        PrintWriter pw = null;

        try{
            file = new FileWriter(dir);
            pw = new PrintWriter(file);
            pw.write(content);
            pw.close();
            file.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            if(pw!=null) pw.close();
        }
    }
    public static void drawGraphviz(Graph graph, boolean vehicle, int type, List<String> optimalRoute){
        try{
            String content = generateGraphViz(graph, vehicle, type, optimalRoute);
            writeFile("file.dot",content);
            ProcessBuilder process;
            process = new ProcessBuilder("dot","-Tpng","-o","grafo.png","file.dot");

            process.redirectErrorStream(true);
            process.start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void createFile(){
        String content = "<html>\n" +
                "<body>\n" +
                "    <img src=\"grafo.png\">\n" +
                "</body>\n" +
                "</html>";
        try{
            writeFile("graph.html", content);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void showHTML(JEditorPane panel){
        createFile();
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        File rec = new File("graph.html");
        //panel.setEditable(false);
        try {
            panel.setPage(rec.toURI().toURL());
        }catch (Exception e){e.printStackTrace();}
    }
}
