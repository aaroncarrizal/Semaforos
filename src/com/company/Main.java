package com.company;

public class Main {


    public static void main(String[] args) {
	    Buffer b = new Buffer(5);	//Se determina cuantas localidades existen en el buffer
	    Productor p1 = new Productor(b,10);	//Cuantos datos se van a generar
	    Consumidor c1 = new Consumidor(b,10);	//Cuantos datos se van a consumir
	    p1.start();	//Empieza el productor
	    c1.start();	//Empieza el consumidor
    }
}
