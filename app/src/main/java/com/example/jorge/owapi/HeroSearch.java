package com.example.jorge.owapi;

public class HeroSearch {

    public String rotulo;
    public String direccion;
    public String precioProducto;
    public Double latitud;
    public Double longitud;

    public HeroSearch(String rotulo, String direccion, String precioProducto, Double numLatitud, Double numLongitud) {
        this.rotulo = rotulo;
        this.direccion = direccion;
        this.precioProducto = precioProducto;
        this.latitud = numLatitud;
        this.longitud = numLongitud;
    }


}
