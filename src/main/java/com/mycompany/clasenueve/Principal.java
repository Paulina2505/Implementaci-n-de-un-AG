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
    private static int individuos = 2;
    private static int genes = 4;
    
    public static void main(String[] args) {
        generarPoblacionInicial();
        fitness = new double[individuos];
        for (int j = 0; j < fitness.length; j++){ fitness[j] = fitness(decimales[j]); System.out.println(fitness[j]);}
        for (int i = 0; i < genes*individuos; i++) {
            int papa = papaMenor();
            int mama = mamaMenor(papa);
            cruza(papa,mama);
            mutacion(papa,mama);
            for (int j = 0; j < individuos; j++) decimales[j] = binarioToDecimal(binarios[j]);
            for (int j = 0; j < fitness.length; j++){ fitness[j] = fitness(decimales[j]); System.out.println(fitness[j]);}
        }
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
            int punto = (int) Math.floor(Math.random()*genes);
            char[] binarioPapa = binarios[posicionPapa].toCharArray();
            char[] binarioMama = binarios[posicionMama].toCharArray();
            String cadena = "";
            for (int i = 0; i < genes; i++) {
                if (i == punto) {
                    cadena += binarioMama[punto];
                } else {
                    cadena += binarioPapa[i];
                }
            }
            binarios[posicionPapa] = cadena;
            cadena = "";
            for (int i = 0; i < genes; i++) {
                if (i == punto) {
                    cadena += binarioPapa[punto];
                } else {
                    cadena += binarioMama[i];
                }
            }
            binarios[posicionMama] = cadena;
        }

    }
    
    public static void mutacion(int posicionPapa, int posicionMama) {
        char[] binarioPapa = binarios[posicionPapa].toCharArray();
        char[] binarioMama = binarios[posicionMama].toCharArray();
        String cadena = "";
        //papa
        for (int i = 0; i < genes; i++) {
            if (Math.random() < probabilidadMutacion) {
                if (binarioPapa[i] == '0') cadena += '1'; else cadena += '0';
            } else cadena += binarioPapa[i];
        }
        binarios[posicionPapa] = cadena;
        //mama
        cadena = "";
        for (int i = 0; i < genes; i++) {
            if (Math.random() < probabilidadMutacion) {
                if (binarioMama[i] == '0') cadena += '1'; else cadena += '0';
            } else cadena += binarioMama[i];
        }
        binarios[posicionMama] = cadena;
    }
}
