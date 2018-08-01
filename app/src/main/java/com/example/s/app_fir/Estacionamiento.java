package com.example.s.app_fir;

public class Estacionamiento {


    private String LugaresDisponibles;
    private String LugaresTotales;
    private String Ubicacion;
    private String Nombre;

    public Estacionamiento() {
    }

    public Estacionamiento(String lugaresDisponibles, String lugaresTotales, String ubicacion, String nombre) {
        LugaresDisponibles = lugaresDisponibles;
        LugaresTotales = lugaresTotales;
        Ubicacion = ubicacion;
        Nombre = nombre;
    }



    public String getNombre() {
        return Nombre;
    }

    public String getLugaresDisponibles() {
        return LugaresDisponibles;
    }

    public String getLugaresTotales() {
        return LugaresTotales;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public void setLugaresDisponibles(String lugaresDisponibles) {
        LugaresDisponibles = lugaresDisponibles;
    }

    public void setLugaresTotales(String lugaresTotales) {
        LugaresTotales = lugaresTotales;
    }

    public void setUbicacion(String ubicacion) {
        Ubicacion = ubicacion;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
