package com.company;
import java.util.Random;

public class Productor extends Thread {
    private Random r = new Random();
    private Buffer b;
    private int size;

    public Productor(Buffer b, int size){   //Se crea dado un tamano
        this.b = b;
        this.size = size;
    }

    public void run(){
        for (int i = 0; i<size;i++){    //Por cada localidad disponible
            int aux = r.nextInt(100);   //Genera un numero aleatorio
            try {
                b.poner(aux);       //Ingresa los datos al buffer
                System.out.println("Produce "+aux);
            } catch (InterruptedException e) {  //Si no se puede
                e.printStackTrace();
            }
        }
    }

}
