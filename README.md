# Programa en Java para abordar el problema de Productor-Consumidor usando semáforos

## Aarón Misahel Carrizal Méndez    |    180816
Todas las clses utilizadas se encuentran en la carpeta src, a continuación se presentan sólo como texto con estilos

### Código fuente
### Clase Main

```java
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
```
### Clase Buffer
```java
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
```
### Clase Productor
```java
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
```
### Clase Consumidor
```java
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
```
