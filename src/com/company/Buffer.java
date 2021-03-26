package com.company;
import java.util.concurrent.*;

//Sirve para guardar los datos temporalmente desde que son producidos hasta que son consumidos
public class Buffer {
    private int[] b;    //arreglo de datos
    private int i = 0,j = 0;
    private Semaphore mutex = new Semaphore(1,true);    //Para que haya exlcusion mutua
    private Semaphore hayDatos = new Semaphore(0,true); //Para saber si ahy datos dentro del buffer
    private Semaphore hayEspacio;   //Saber si hay espacio en el buffer

    public Buffer(int tam){
        b = new int [tam];  //Se crea buffer del tamano deseado
        hayEspacio = new Semaphore(tam, true);
    }

    public void poner(int dato) throws InterruptedException {   //Para insertar datos en el buffer
        hayEspacio.acquire();   //Para saber si el proceso esta disponible
        mutex.acquire();
        b[i] = dato;    //Se aloja el sato en la localidad
        i = (i+1)%b.length; //Se mueve la localidad en forma circular
        mutex.release();     //Libera el proceso
        hayDatos.release();
    }

    public int extraer() throws InterruptedException {
        hayDatos.acquire(); //Saber si el proceso esta disponible
        mutex.acquire();
        int actual = j;     //Guarda temporalmente el dato a extraer
        j = (j+1)%b.length; //Se mueve la localidad en forma circular
        mutex.release();
        hayEspacio.release();   //Libera los procesos
        return b[actual];   //regresa el dato que se extrayo
    }
}
