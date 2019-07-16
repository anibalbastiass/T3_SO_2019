# Tarea 3 - Sistemas Operativos

``` 
1 Semestre 2019
Autor:      Anibal Bastias Soto
ROL:        2604215-1
Carrera:    Ingenieria de Ejecucion en Informatica
```

### Introduccion

El objetivo de la tarea es analizar algortimos de asignacion de valores 
y ordenamiento mediante hebras para utilizar sincronizacion de acciones
mediante hebras paralelas.

En esta tarea, se analiza mediante archivos de entrada, un algoritmo de
sistema de ecuaciones donde en cada hebra analiza cada linea si es un
polinomio y asigna valores segun si contiene el valor inicial de x
y funcion de otras funciones compartiendo valores entre hebras.

La segunda pregunta es un algoritmo de ordenamiento (utilizando QuickSort)
donde mediante un pivote e indices finales e inciales recorre inverasamente
el arreglo dado y los ordena en multiples hebras

## Supuestos 

Para los archivos de entrada de la pregunta 1, se asume que 'x' es la variable
a reemplazar inicialmente y debe contener los otros valores en los polinomios.
Ejemplo de archivo:

```
3
f(x)=g(x) * h(x)
g(x)=( h(x) + 1 ) / 2
h(x)=2 * x + 1
``` 

Como condicion, los polinomios **deben** estar separados por un espacio
para que logre diferenciar los simbolos.

En la pregunta 2, se da un archivo con una lista de numeros tambien separados 
por un espacio para recorrer cada linea. Cada linea es un arreglo a analizar
Ejemplo de archivo:

```
60 89 7 70 26 86 80 5 95 71 49 36 84 74 63
28 85 87 2 18 93 41 62 42 4 32 85 45 49 27
11 49 12 98 12 57 26 6 7 75 84 90 100 85 42
```

## Ambiente de pruebas

Esta tarea fue desarrollada con el IDE IntelliJ IDEA Community 2017.3.5 
en el sistema operativo **Mac OS X Mojave 10.14.5**.
Se utilizo el SDK de Java 8. Se entrega el detalle:

```
IntelliJ IDEA 2017.3.5 (Community Edition)
JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
```

Se trabajo en el siguiente Hardware:

```
MacBook Pro (Retina, 15-inch, Mid 2015)
2.2 GHz Intel Core i7 (4 nucleos fisicos, 8 logicos)
16 GB 1600 MHz DDR3
```

Dado para efectos de la tarea, en tareas anteriores se solicito hacer 
pruebas para sistema operativo Linux Ubuntu. Se hicieron pruebas tambien
con este sistema operativo.

Dado que cualquier IDE como IntelliJ, Eclipse, Netbeans da las facilidades
de compilacion y ejecucion, para la practicidad de nuestra tarea se utilizo
el compilador **ANT** para que se ejecute en cualquier computador.

### Modo de compilacion

Para esto, necesitamos los siguientes comandos en terminal:

* Instalacion de SDK Java 8
```
sudo apt install openjdk-8-jre-headless
```
 
* Descarga de ANT en 
https://www-eu.apache.org/dist/ant/binaries/apache-ant-1.10.6-bin.tar.gz

* Descomprimir ANT
```
tar vxf apache-ant-1.9.4-bin.tar.gz
```

Suponiendo que estamos en el directorio `/Users/anibalbastias/` y Tarea3
esta en el directorio `/Users/anibalbastias/tarea3-2604215-1`, para
compilar nuestro proyecto ejecutamos:

* Compilacion de Proyecto con ANT en directorio `/Users/anibalbastias/tarea3-2604215-1`:

```
/Users/anibalbastias/apache-ant-1.10.6/bin/ant -buildfile build.xml
```

Archivo `build.xml` esta en el directorio de la tarea 3

### Modo de ejecucion

Luego de esto, podemos ejecutar la tarea en el directorio.

```
/Users/anibalbastias/tarea3-2604215-1/dist/lib
```

Este directorio contiene la carpeta `in` que contiene `question1` y `question2` 
con los archivos de entrada. Entonces, para ejecutar:

```
java -jar Tarea3-260215-1.jar
```

o 

```
java -jar dist/lib/Tarea3-260215-1.jar
```


*by @anibalbastiass*

PD: Si desea ver este README con estilos, puede acceder a un lector Markdown Online :)