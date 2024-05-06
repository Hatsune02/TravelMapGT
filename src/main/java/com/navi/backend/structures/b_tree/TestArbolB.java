package com.navi.backend.structures.b_tree;

public class TestArbolB {

    public static void main(String[] args) {
        //grado minimo del Arbol B es t=3
        //Cada nodo soporta 2t hijos y 2t-1 claves
        int t = 2;
        //Se crea el arbol B segun t
        BTree arbolB = new BTree(t);

        //Valores a ingresar primera ronda
        int[] valoresUno = {20, 10, 50, 30, 40};
        String[] nombresUno = {"A", "B", "C", "D", "E"};
        System.out.println("-- INICIO --");
        System.out.println("INSERTANDO VALORES AL ARBOL B");
        for(int i=0; i<valoresUno.length; i++) {
            System.out.println("Insertando... valor " + valoresUno[i]);
            arbolB.insertar(valoresUno[i], nombresUno[i]);
        }

        //Mostrando arbol B por pantalla en preorder
        System.out.println("ESTADO ARBOL B");
        arbolB.showBTree();
        System.out.println("");

        //Valores a ingresar segunda ronda
        System.out.println("Insertando... valor 60");
        arbolB.insertar(60, "F");
        //Mostrando arbol B por pantalla en preorder
        System.out.println("ESTADO ARBOL B");
        arbolB.showBTree();
        System.out.println("");

        //Valores a ingresar tercera ronda
        System.out.println("Insertando... valor 80");
        arbolB.insertar(80, "G");
        System.out.println("Insertando... valor 70");
        arbolB.insertar(70, "H");
        System.out.println("Insertando... valor 90");
        arbolB.insertar(90, "I");
        //Mostrando arbol B por pantalla en preorder
        System.out.println("ESTADO ARBOL B");
        arbolB.showBTree();
        System.out.println();
        arbolB.createGraphviz();
        System.out.println("");

        //Buscar
        System.out.println("\nBuscando el nodo con el valor 80 en el arbol B:");
        arbolB.buscarNodoPorClave(80);

        //IMPLEMENTAR
        System.out.println("\nEl valor maximo del arbol B es : " + arbolB.buscarClaveMayor());

        System.out.print("El nodo minimo de la raiz del arbol B es :");
        arbolB.mostrarClavesNodoMinimo();

        System.out.println("");
        System.out.println("-- FIN --");
    }
}
