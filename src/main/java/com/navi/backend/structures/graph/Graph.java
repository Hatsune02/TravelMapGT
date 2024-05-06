package com.navi.backend.structures.graph;
import com.navi.backend.objs.Route;
import lombok.*;
import java.util.*;

@Getter @Setter
public class Graph {
    private HashMap<String, ArrayList<Route>> edges;
    private HashMap<String, ArrayList<Route>> edgesNotDirected;
    public Graph() {
        this.edges = new HashMap<>();
        this.edgesNotDirected = new HashMap<>();
    }
    public void addEdge(Route edge) {
        addEdgeDirected(edge);
        addEdgeNotDirected(edge);
    }
    public void addEdgeDirected(Route edge) {
        String origin = edge.getOrigin();
        String destiny = edge.getDestiny();
        edges.putIfAbsent(origin, new ArrayList<>());
        edges.putIfAbsent(destiny, new ArrayList<>());
        edges.get(origin).add(edge);
    }
    public void addEdgeNotDirected(Route edge) {
        String origin = edge.getOrigin();
        String destiny = edge.getDestiny();
        edgesNotDirected.putIfAbsent(origin, new ArrayList<>());
        edgesNotDirected.putIfAbsent(destiny, new ArrayList<>());

        boolean existEdge = edgesNotDirected.get(origin).stream().anyMatch(ed -> ed.getDestiny().equals(edge.getDestiny()));
        if(!existEdge) {
            Route invertedEdge = new Route(destiny, origin, edge.getVehicleTime(), edge.getWalkTime(), edge.getGas(), edge.getPersonEffort(), edge.getDistance(), new ArrayList<>());
            edgesNotDirected.get(origin).add(edge);
            edgesNotDirected.get(destiny).add(invertedEdge);
        }
    }
    public ArrayList<String> shortestPath(String start, String end, int type, int hour, boolean vehicle) {
        HashMap<String, Double> distance = new HashMap<>();
        HashMap<String, String> previous = new HashMap<>();
        LinkedList<String> unvisited = new LinkedList<>();

        HashMap<String, ArrayList<Route>> edgesNow;
        if(vehicle) edgesNow = edges;
        else edgesNow = edgesNotDirected;

        // Inicializar la distancia de inicio como 0 y el resto como infinito
        for (String node : edgesNow.keySet()) {
            if (node.equals(start)) {
                distance.put(node, 0.0);
            } else {
                distance.put(node, Double.POSITIVE_INFINITY);
            }
            previous.put(node, null);
            unvisited.add(node);
        }

        while (!unvisited.isEmpty()) {
            // Encontrar el nodo no visitado con la distancia mínima
            String current = null;
            for (String node : unvisited) {
                if (current == null || distance.get(node) < distance.get(current)) {
                    current = node;
                }
            }
            // Si el nodo actual es el destino, terminar
            if (current.equals(end)) {
                break;
            }
            // Actualizar las distancias de los vecinos no visitados del nodo actual
            for (Route edge : edgesNow.get(current)) {
                if (unvisited.contains(edge.getDestiny())) {
                    //HALLAR PESO MEDIANTE EL TIPO CON EL METODO getDistanceNode
                    double newDist = distance.get(current) + getDistanceNode(type, edge, hour);
                    if (newDist < distance.get(edge.getDestiny())) {
                        distance.put(edge.getDestiny(), newDist);
                        previous.put(edge.getDestiny(), current);
                    }
                }
            }
            // Marcar el nodo actual como visitado
            unvisited.remove(current);
        }

        // Construir el camino más corto desde el nodo de inicio hasta el nodo de destino
        ArrayList<String> path = new ArrayList<>();
        for (String node = end; node != null; node = previous.get(node)) {
            path.add(node);
        }
        Collections.reverse(path);

        return path;
    }

    public ArrayList<String> longestPath(String start, String end, int type, int hour, boolean vehicle) {
        HashMap<String, Double> distance = new HashMap<>();
        HashMap<String, String> previous = new HashMap<>();
        LinkedList<String> unvisited = new LinkedList<>();

        HashMap<String, ArrayList<Route>> edgesNow;
        if(vehicle) edgesNow = edges;
        else edgesNow = edgesNotDirected;

        // Inicializar la distancia de inicio como 0 y el resto como infinito
        for (String node : edgesNow.keySet()) {
            if (node.equals(start)) {
                distance.put(node, 0.0);
            } else {
                distance.put(node, Double.POSITIVE_INFINITY);
            }
            previous.put(node, null);
            unvisited.add(node);
        }

        while (!unvisited.isEmpty()) {
            // Encontrar el nodo no visitado con la distancia mínima
            String current = null;
            for (String node : unvisited) {
                if (current == null || distance.get(node) < distance.get(current)) {
                    current = node;
                }
            }
            // Si el nodo actual es el destino, terminar
            if (current.equals(end)) {
                break;
            }
            // Actualizar las distancias de los vecinos no visitados del nodo actual
            for (Route edge : edgesNow.get(current)) {
                if (unvisited.contains(edge.getDestiny())) {
                    //HALLAR PESO MEDIANTE EL TIPO CON EL METODO getDistanceNode
                    double newDist = distance.get(current) + (getDistanceNode(type, edge, hour) * -1);
                    System.out.println("Nodo " + current + " " + newDist + " fin "+edge.getDestiny());
                    System.out.println("wataf "+distance.get(current));
                    if (newDist < distance.get(edge.getDestiny())) {
                        distance.put(edge.getDestiny(), newDist);
                        previous.put(edge.getDestiny(), current);
                    }
                }
            }
            // Marcar el nodo actual como visitado
            unvisited.remove(current);
        }

        // Construir el camino más corto desde el nodo de inicio hasta el nodo de destino
        ArrayList<String> path = new ArrayList<>();
        for (String node = end; node != null; node = previous.get(node)) {
            path.add(node);
        }
        Collections.reverse(path);

        return path;
    }

    public double getDistanceNode(int type, Route edge, int hour){
        double routeWeight = 0;
        //ESCOGER EN BASE A QUE SE MIDE EL PESO ENTRE ARISTAS
        if(type == TypeGraph.GAS) routeWeight = edge.getGas();

        else if(type == TypeGraph.GAS_DISTANCE) routeWeight = edge.getGasDistance();

        else if(type == TypeGraph.DISTANCE) routeWeight = edge.getDistance();

        else if(type == TypeGraph.SPEED_V) routeWeight = (edge.getSpeedVehicle(hour) * -1);

        else if(type == TypeGraph.EFFORT) routeWeight = edge.getPersonEffort();

        else if(type == TypeGraph.EFFORT_DISTANCE) routeWeight = edge.getEffortDistance();

        else if(type == TypeGraph.SPEED_W) routeWeight = (edge.getSpeedWalk(hour)* -1);

        return Math.round(routeWeight * 100.0) / 100.0;
    }
    public ArrayList<ArrayList<String>> allPaths(String start, String end, boolean vehicle) {
        ArrayList<ArrayList<String>> paths = new ArrayList<>();
        HashSet<String> visited = new HashSet<>();
        ArrayList<String> path = new ArrayList<>();
        path.add(start);
        findAllPaths(start, end, vehicle, visited, path, paths);
        return paths;
    }
    private void findAllPaths(String current, String end, boolean vehicle, HashSet<String> visited, ArrayList<String> path, ArrayList<ArrayList<String>> paths) {
        visited.add(current);
        if (current.equals(end)) {
            paths.add(new ArrayList<>(path));
        } else {
            HashMap<String, ArrayList<Route>> edgesNow;
            if(vehicle) edgesNow = edges;
            else edgesNow = edgesNotDirected;
            for (Route edge : edgesNow.get(current)) {
                if (!visited.contains(edge.getDestiny())) {
                    path.add(edge.getDestiny());
                    findAllPaths(edge.getDestiny(), end, vehicle, visited, path, paths);
                    path.remove(path.size() - 1);
                }
            }
        }
        visited.remove(current);
    }
    public ArrayList<String> possibleMoves(String start, String end, boolean vehicle){
        ArrayList<String> moves = new ArrayList<>();
        for (ArrayList<String> path : allPaths(start, end, vehicle)) {
            for (int i = 0; i < path.size(); i++) {
                if(i==1) {
                    moves.add(path.get(1));
                    break;
                }
            }
        }
        return moves;
    }

}