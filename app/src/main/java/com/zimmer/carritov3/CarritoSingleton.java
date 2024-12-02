package com.zimmer.carritov3;

import java.util.ArrayList;

public class CarritoSingleton {
    private static CarritoSingleton instance;
    private ArrayList<String> productos;

    private CarritoSingleton(){
        productos = new ArrayList<>();
    }

    public static CarritoSingleton getInstance() {
        if (instance == null) {
            instance = new CarritoSingleton();
        }
        return instance;
    }

    public ArrayList<String> getProductos(){
        return productos;
    }

    public void agregarProducto(String producto) {
        productos.add(producto);
    }

    public void clear(){
        productos.clear();
    }
}


