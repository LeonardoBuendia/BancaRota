package com.buendiasoftware.bancarota;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import java.util.Date;
import java.util.List;

/**
 * Created by leonardo on 5/03/18.
 */
@Dao
public interface GastosDao {

   // @Query("SELECT empleado FROM Gastostb WHERE empleado=:empleadoConsul")
     //       String getEmpleadoConsul (String empleadoConsul);

    @Query("SELECT  * FROM Gastostb WHERE empleado=:empleado AND fecha BETWEEN :fechaInit AND :fechaFinal")
    List<Gastostb> consultaNomina(String empleado, String fechaInit, String fechaFinal);

    @Query("SELECT * FROM Gastostb WHERE empleado=:empleado AND fecha BETWEEN (:fechaInit) AND (:fechaFinal)")
    public Gastostb[] consultaEmpleadoNomina(String empleado, String fechaInit, String fechaFinal);


  //  @Query("SELECT * FROM Gastostb WHERE empleado=:empleado AND fecha BETWEEN :fechaInit AND :fechaFinal")
  //  public Gastostb[] consultaFechasNomina(String empleado, Date fechaInit, Date fechaFinal);


    @Query("SELECT * FROM Gastostb WHERE empleado=:empleadoConsulta AND fecha=:today")
        public Gastostb[] obtenLinea (String empleadoConsulta, String today );

    @Query("UPDATE Gastostb SET break1= :break1Updated, empleadoPosicion= :posicionEmpleado1 WHERE empleado = :empleadoUpdate AND fecha =:today")
    void updateBreak1 (String empleadoUpdate, String break1Updated, String today, int posicionEmpleado1);

    @Query("UPDATE Gastostb SET break2= :break2Updated,empleadoPosicion= :posicionEmpleado2  WHERE empleado = :empleadoUpdate AND fecha =:today")
    void updateBreak2 (String empleadoUpdate, String break2Updated, String today, int posicionEmpleado2);

    @Query("UPDATE Gastostb SET salida= :salidaUpdated,empleadoPosicion= :posicionEmpleadoSal WHERE empleado = :empleadoUpdate AND fecha =:today")
    void updateSalida (String empleadoUpdate, String salidaUpdated, String today,int posicionEmpleadoSal);

    @Query("SELECT fecha From Gastostb WHERE empleado=:empleadoConsulta")
           String fechaConsul (String empleadoConsulta);

    @Query("SELECT entrada FROM Gastostb WHERE empleado=:empleadoConsulta AND fecha=:today")
           String entradaConsul (String empleadoConsulta, String today);

    //@Query("SELECT SUM(gasto) FROM Gastostb WHERE categoria_num=:numCategoria AND date BETWEEN :init AND :end")
    //int getGastosBetween(String numCategoria, String init, String end);

    @Insert
    void insert(Gastostb gastostb);

   @Query("DELETE FROM Gastostb WHERE fecha=:date")
    void deleteGastos(String date);
}
