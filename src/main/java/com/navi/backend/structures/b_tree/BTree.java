package com.navi.backend.structures.b_tree;

import com.navi.backend.structures.graph.Graph;
import com.navi.backend.structures.graph.GraphvizG;

import java.util.List;

public class BTree {
    Node root;
    int t;

    //Constructor
    public BTree(int t) {
        this.t = t;
        root = new Node(t);
    }

    public double buscarClaveMayor() {
        return getClaveMayor(this.root);
    }

    private double getClaveMayor(Node current) {
        if (current == null) {
            return 0;//Si es cero no existe
        }

        //Mientras no sea una hoja
        while (!current.leaf) {
            //Se accede al hijo mas a la derecha
            current = current.child[current.n];
        }

        return claveMayorPorNodo(current);
    }

    private double claveMayorPorNodo(Node current) {
        //Devuelve el valor mayor, el que esta mas a la derecha
        return current.key[current.n - 1];
    }

    public void mostrarClavesNodoMinimo() {
        Node temp = buscarNodoMinimo(root);

        if (temp == null) {
            System.out.println("Sin minimo");
        } else {
            temp.imprimir();
        }
    }

    public Node buscarNodoMinimo(Node nodoActual) {
        if (root == null) {
            return null;
        }

        Node aux = root;

        //Mientras no sea una hoja
        while (!aux.leaf) {
            //Se accede al primer hijo
            aux = aux.child[0];
        }

        //Devuelve el nodo menor, el que esta mas a la izquierda
        return aux;
    }

    //Busca el valor ingresado y muestra el contenido del nodo que contiene el valor
    public void buscarNodoPorClave(int num) {
        Node temp = search(root, num);

        if (temp == null) {
            System.out.println("No se ha encontrado un nodo con el valor ingresado");
        } else {
            print(temp);
        }
    }

    //Search
    private Node search(Node actual, int key) {
        int i = 0;//se empieza a buscar siempre en la primera posicion

        //Incrementa el indice mientras el valor de la clave del nodo sea menor
        while (i < actual.n && key > actual.key[i]) {
            i++;
        }

        //Si la clave es igual, entonces retornamos el nodo
        if (i < actual.n && key == actual.key[i]) {
            return actual;
        }

        //Si llegamos hasta aqui, entonces hay que buscar los hijos
        //Se revisa primero si tiene hijos
        if (actual.leaf) {
            return null;
        } else {
            //Si tiene hijos, hace una llamada recursiva
            return search(actual.child[i], key);
        }
    }

    public void insertar(double key, String name) {
        Node r = root;

        //Si el nodo esta lleno lo debe separar antes de insertar
        if (r.n == ((2 * t) - 1)) {
            Node s = new Node(t);
            root = s;
            s.leaf = false;
            s.n = 0;
            s.child[0] = r;
            split(s, 0, r);
            nonFullInsert(s, key, name);
        } else {
            nonFullInsert(r, key, name);
        }
    }

    // Caso cuando la raiz se divide
    // x =          | | | | | |
    //             /
    //      |10|20|30|40|50|
    // i = 0
    // y = |10|20|30|40|50|
    private void split(Node x, int i, Node y) {
        //Nodo temporal que sera el hijo i + 1 de x
        Node z = new Node(t);
        z.leaf = y.leaf;
        z.n = (t - 1);

        //Copia las ultimas (t - 1) claves del nodo y al inicio del nodo z      // z = |40|50| | | |
        for (int j = 0; j < (t - 1); j++) {
            z.key[j] = y.key[(j + t)];
            z.name[j] = y.name[(j+t)];
        }

        //Si no es hoja hay que reasignar los nodos hijos
        if (!y.leaf) {
            for (int k = 0; k < t; k++) {
                z.child[k] = y.child[(k + t)];
            }
        }

        //nuevo tamanio de y                                                    // x =            | | | | | |
        y.n = (t - 1);                                                          //               /   \
        //  |10|20| | | |
        //Mueve los hijos de x para darle espacio a z
        for (int j = x.n; j > i; j--) {
            x.child[(j + 1)] = x.child[j];
        }
        //Reasigna el hijo (i+1) de x                                           // x =            | | | | | |
        x.child[(i + 1)] = z;                                                   //               /   \
        //  |10|20| | | |     |40|50| | | |
        //Mueve las claves de x
        for (int j = x.n; j > i; j--) {
            x.key[(j + 1)] = x.key[j];
            x.name[(j + 1)] = x.name[j];
        }

        x.name[i] = y.name[(t - 1)];
        //Agrega la clave situada en la mediana                                 // x =            |30| | | | |
        x.key[i] = y.key[(t - 1)];                                              //               /    \
        x.n++;                                                                  //  |10|20| | | |      |40|50| | | |
    }

    private void nonFullInsert(Node x, double key, String name) {
        //Si es una hoja
        if (x.leaf) {
            int i = x.n; //cantidad de valores del nodo
            //busca la posicion i donde asignar el valor
            while (i >= 1 && key < x.key[i - 1]) {
                x.key[i] = x.key[i - 1];//Desplaza los valores mayores a key
                x.name[i] = x.name[i-1];
                i--;
            }

            x.name[i] = name;
            x.key[i] = key;//asigna el valor al nodo
            x.n++; //aumenta la cantidad de elementos del nodo
        } else {
            int j = 0;
            //Busca la posicion del hijo
            while (j < x.n && key > x.key[j]) {
                j++;
            }

            //Si el nodo hijo esta lleno lo separa
            if (x.child[j].n == (2 * t - 1)) {
                split(x, j, x.child[j]);

                if (key > x.key[j]) {
                    j++;
                }
            }

            nonFullInsert(x.child[j], key, name);
        }
    }

    public void showBTree() {
        print(root);
    }

    //Print en preorder
    private void print(Node n) {
        n.imprimir();
        //Si no es hoja
        if (!n.leaf) {
            //recorre los nodos para saber si tiene hijos
            for (int j = 0; j <= n.n; j++) {
                if (n.child[j] != null) {
                    n.imprimir();
                    System.out.print(" -> ");
                    print(n.child[j]);
                    System.out.println("");
                }
            }
        }
    }

    public String bodyDOT(Node n){
        String body = "";
        String node = "\""+n.print()+"\"";
        if(!n.leaf){
            for (int j = 0; j <= n.n; j++) {
                if (n.child[j] != null) {
                    body += node + " -> " + bodyDOT(n.child[j])+ "\n";
                }
            }
        }
        else body += node;

        return body;
    }

    public String createGraphviz(){
        String body = "digraph G {\nnode [style=filled, fillcolor=lightblue, shape=rect, peripheries=2, fixedsize=false, width=1, height=0.5, margin=0.1];\n";
        body += bodyDOT(root);
        body += "}";
        return body;
    }
    public void drawGraphviz(){
        try{
            String content = createGraphviz();
            GraphvizG.writeFile("bTree.dot",content);
            ProcessBuilder process;
            process = new ProcessBuilder("dot","-Tpng","-o","bTree.png","bTree.dot");

            process.redirectErrorStream(true);
            process.start();
        } catch (Exception e){
            e.printStackTrace();
        }

        String content = "<html>\n" +
                "<body>\n" +
                "    <img src=\"bTree.png\">\n" +
                "</body>\n" +
                "</html>";
        try{
            GraphvizG.writeFile("bTree.html", content);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
