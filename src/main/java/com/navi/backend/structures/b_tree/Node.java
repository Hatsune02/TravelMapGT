package com.navi.backend.structures.b_tree;

public class Node {

    int n; //numero de claves almacenadas en el nodo
    boolean leaf; //Si el nodo es hoja (nodo hoja=true; nodo interno=false)
    double[] key;  //almacena las claves en el nodo
    String[] name;
    Node[] child; //arreglo con referencias a los hijos

    //Constructores
    public Node(int t) {
        n = 0;
        leaf = true;
        key = new double[((2 * t) - 1)];
        name = new String[((2 * t) - 1)];
        child = new Node[(2 * t)];
    }

    public void imprimir() {
        System.out.print("[");
        for (int i = 0; i < n; i++) {
            if (i < n - 1) {
                System.out.print(key[i] +" "+ name[i] + " | ");

            } else {
                System.out.print(key[i]);
                System.out.print(" "+name[i]);
            }
        }
        System.out.print("]");
    }

    public String print(){
        StringBuilder node = new StringBuilder("[");
        for (int i = 0; i < n; i++) {
            if (i < n - 1) {
                node.append(name[i]).append(" | ");
            } else {
                node.append(name[i]);
            }
        }
        node.append("]");
        return node.toString();
    }

    public int find(int k) {
        for (int i = 0; i < n; i++) {
            if (key[i] == k) {
                return i;
            }
        }
        return -1;
    }
}
