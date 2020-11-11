/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clasenueve;

/**
 *
 * @author Paulina
 */
public class Principal {
    private static double[] poblacion;
    private static String[] binarios;
    private static int[] decimales;
    private static double[] fitness;
    private static double probabilidadCruza = 0.7;
    private static double probabilidadMutacion = 0.3;
    private static int individuos = 100;
    private static int genes = 4;
    
    public static void main(String[] args) {
        generarPoblacionInicial();
        fitness = new double[individuos];
        for (int j = 0; j < fitness.length; j++){ fitness[j] = fitness(decimales[j]); /*System.out.println(fitness[j]);*/}
        for (int i = 0; i < genes*individuos; i++) {
            int papa = papaMenor();
            int mama = mamaMenor(papa);
            cruza(papa,mama);
            mutacion(papa,mama);
            for (int j = 0; j < individuos; j++) decimales[j] = binarioToDecimal(binarios[j]);
            for (int j = 0; j < fitness.length; j++){ fitness[j] = fitness(decimales[j]); /*System.out.println(fitness[j]);*/}
        }
        System.out.println("RESULTADO");
        System.out.println("x = "+decimales[papaMenor()]);
        System.out.println("f(x) = "+fitness(decimales[papaMenor()]));
    }
    
    public static void generarPoblacionInicial() {
        int espacios = individuos*genes, cont = 0;
        poblacion = new double[espacios];
        decimales = new int[individuos];
        binarios = new String[individuos];
        for (int i = 0; i < espacios; i++) {
            poblacion[i] = Math.random();
        }
        
        for (int i = 0; i < individuos; i++) {
            String binario = "";
            for (int j = 0; j < genes; j++) {
                binario += (int) Math.round(poblacion[cont]);
                cont ++;
                binarios[i] = binario;
            }
            decimales[i] = binarioToDecimal(binario);
        }
    }
    
    public static int binarioToDecimal(String binario) {
        char[] cadenaa = binario.toCharArray();
        char[] cadena = new char[cadenaa.length];
        for (int i = 0; i < cadenaa.length; i++) cadena[cadenaa.length-i-1] = cadenaa[i];
        int valor = 0, resultado = 0, aux = 0;
        for (int i = 0; i < cadena.length; i++) {
            valor = aux * 2;
            if (valor == 0) valor = 1;
            if (cadena[i] == '1') {
                resultado += valor;
            }
            aux = valor;
        }
        return resultado;
    }
    
    public static double fitness(int x) {return (5*x*x)-(20*x)+3;}
    
    public static int papaMayor() {
        double mayor = fitness[0];
        int posicion = 0;
        for (int i = 1; i < fitness.length; i++) {
            if (fitness[i] > mayor){
                mayor = fitness[i];
                posicion = i;
            }
        }
        return posicion;
    }
    
    public static int mamaMayor(double papa) {
        double mayor = 0; int posicion = 0;
        if (papa == 0) mayor = fitness[1];
        else mayor = fitness[0];
        for (int i = 0; i < fitness.length; i++) {
            if (fitness[i] > mayor && i != papa) {
                mayor = fitness[i];
                posicion = i;
            }
        }
        return posicion;
    }
    
    public static int papaMenor() {
        double menor = fitness[0];
        int posicion = 0;
        for (int i = 1; i < fitness.length; i++) {
            if (fitness[i] < menor){
                menor = fitness[i];
                posicion = i;
            }
        }
        return posicion;
    }
    
    public static int mamaMenor(double papa) {
        double menor = 0; int posicion = 0;
        if (papa == 0) menor = fitness[1];
        else menor = fitness[0];
        for (int i = 0; i < fitness.length; i++) {
            if (fitness[i] < menor && i != papa) {
                menor = fitness[i];
                posicion = i;
            }
        }
        return posicion;
    }
    
    public static void cruza(int posicionPapa, int posicionMama) {
        if (Math.random() < probabilidadCruza) {
            char[] binarioPapa = binarios[posicionPapa].toCharArray();
            char[] binarioMama = binarios[posicionMama].toCharArray();
            String cadena = "";
            int cruza = Math.round(binarioPapa.length/2);
            for (int i = 0; i < cruza; i++) cadena += binarioPapa[i];
            cruza = binarioMama.length - cruza;
            for (int i = cruza; i < binarioMama.length; i++) cadena += binarioMama[i];
            //System.out.println("papa: "+binarios[posicionPapa]+"  mama: "+binarios[posicionMama]+"  nuevo fitness de cruza "+cadena);
            //System.out.println("papa: "+fitness(decimales[posicionPapa])+"  mama: "+fitness(decimales[posicionMama])+"  nuevo fitness de cruza "+fitness(binarioToDecimal(cadena)));
            binarios[0] = cadena;
        }

    }
    
    public static void mutacion(int posicionPapa, int posicionMama) {
        if (Math.random() < probabilidadMutacion) {
            //System.out.println("papa antes: "+binarios[posicionPapa]+" "+fitness(decimales[posicionPapa]));
            char[] binarioPapa = binarios[posicionPapa].toCharArray();
            int a = (binarioPapa.length) -1;
            int posicion = (int) Math.round(Math.random() * a);
            if (binarioPapa[posicion] == '0') binarioPapa[posicion] = '1'; else binarioPapa[posicion] = '0';
            binarios[1] = String.valueOf(binarioPapa);
            //System.out.println("papa después: "+binarios[1]+" "+fitness(binarioToDecimal(binarios[1])));
            }
        if (Math.random() < probabilidadMutacion) {
            //System.out.println("mama antes: "+binarios[posicionMama]+" "+fitness(decimales[posicionMama]));
            char[] binarioMama = binarios[posicionMama].toCharArray();
            int a = (binarioMama.length) -1;
            int posicion = (int) Math.round(Math.random() * a);
            if (binarioMama[posicion] == '0') binarioMama[posicion] = '1'; else binarioMama[posicion] = '0';
            binarios[2] = String.valueOf(binarioMama);
            //System.out.println("mama despues: "+binarios[2]+" "+fitness(binarioToDecimal(binarios[2])));
            
        }
        
    }
}
