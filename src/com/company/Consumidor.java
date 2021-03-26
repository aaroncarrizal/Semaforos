package com.company;

public class Consumidor extends Thread{
    private Buffer b;
    private int size;

    public Consumidor(Buffer b, int size){  //Se crea dado un tamano
        this.b = b;
        this.size = size;
    }

    public void run(){
        for (int i = 0; i<size;i++){//Por cada localidad
            try {
                int aux = b.extraer();  //Extraer el dato
                System.out.println("Consume "+aux);
            } catch (InterruptedException e) {//Si no se puede
                e.printStackTrace();
            }
        }
    }
}
