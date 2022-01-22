package com.buendiasoftware.bancarota;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by leonardo on 5/03/18.
 */


@Entity
public class Gastostb {


    @PrimaryKey (autoGenerate = true)
   // @PrimaryKey
    //@NonNull
     private int empleadoId;

    @ColumnInfo
    private String empleado;

   // @ColumnInfo(name="date")
   @ColumnInfo
   private String fecha;

   @ColumnInfo
    private String entrada;

   @ColumnInfo
    private String break1;

   @ColumnInfo
    private String break2;

    @ColumnInfo
    private String salida;

    @ColumnInfo
    private int empleadoPosicion;

    @ColumnInfo
   private Date fechaTrabajada;



   //geters and setters

    public int getEmpleadoId(){
        return empleadoId;
    }
    public void setEmpleadoId(int empleadoId){
        this.empleadoId=empleadoId;
    }
    public String getEmpleado(){
       return empleado;
    }
    public void setEmpleado(String empleado){
        this.empleado=empleado;
    }
    public String getFecha(){
        return fecha;
    }
    public void setFecha(String fecha){
        this.fecha=fecha;
    }
    public String getEntrada() {
        return entrada;
    }
    public void setEntrada(String entrada){
        this.entrada=entrada;
    }
    public String getBreak1(){
        return break1;
    }
    public void setBreak1(String break1){
        this.break1=break1;
    }
    public String getBreak2(){
        return break2;
    }
    public void setBreak2(String break2){
        this.break2=break2;
    }
    public String getSalida(){ return salida;   }
    public void setSalida(String salida){
        this.salida=salida;
    }
    public int getEmpleadoPosicion(){return empleadoPosicion;}
    public void setEmpleadoPosicion(int empleadoPosicion){this.empleadoPosicion=empleadoPosicion;}
   public Date getFechaTrabajada(){return fechaTrabajada;}
   public void setFechaTrabajada(Date fechaTrabajada){this.fechaTrabajada=fechaTrabajada;}

    @Ignore
    Bitmap picture;

}
