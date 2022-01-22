package com.buendiasoftware.bancarota;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class help extends AppCompatActivity implements View.OnClickListener{
    EditText dateConsultaDB, dateConsultaDB2;
    private GastosDB gastosDatabase=GastosDB.getInstance(help.this);
    String selectedDate,fechainicial, fechafinal, empleadoConsultar;
    private TextView RtextAlimentos, RtextDiversion, RtextTransporte, RtextHogar, RtextEducacion, RtextOtros, RtextGastoTotal;
    CheckBox ch1, ch2, ch3, ch4, ch5, ch6, ch7, ch8,ch9,ch10,ch11,ch12,ch13, ch14;
    Date date1FechaInit, date2FechaFinal;
    SimpleDateFormat format3 = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Toolbar helpToolbar = (Toolbar) findViewById(R.id.help_toolbar);
        setSupportActionBar(helpToolbar);

        // Get a support ActionBar corresponding to this toolbar
        //  getActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar helpAb = getSupportActionBar();
        // Enable the Up button
        helpAb.setDisplayHomeAsUpEnabled(true);

        /*
         * Codigo para obtener el dia seleccionado por usuairo
         */
        dateConsultaDB = (EditText) findViewById(R.id.dateConsulta);
        dateConsultaDB.setOnClickListener(this);
        dateConsultaDB2 = (EditText) findViewById(R.id.dateConsulta2);
        dateConsultaDB2.setOnClickListener(this);
        // Finding CheckBox by its unique ID
        ch1=(CheckBox)findViewById(R.id.chkbox_empleado1);
        ch2=(CheckBox)findViewById(R.id.chkbox_empleado2);
        ch3=(CheckBox)findViewById(R.id.chkbox_empleado3);
        ch4=(CheckBox)findViewById(R.id.chkbox_empleado4);
        ch5=(CheckBox)findViewById(R.id.chkbox_empleado5);
        ch6=(CheckBox)findViewById(R.id.chkbox_empleado6);
        ch7=(CheckBox)findViewById(R.id.chkbox_empleado7);
        ch8=(CheckBox)findViewById(R.id.chkbox_empleado8);
        ch9=(CheckBox)findViewById(R.id.chkbox_empleado9);
        ch10=(CheckBox)findViewById(R.id.chkbox_empleado10);
        ch11=(CheckBox)findViewById(R.id.chkbox_empleado11);
        ch12=(CheckBox)findViewById(R.id.chkbox_empleado12);
        ch13=(CheckBox)findViewById(R.id.chkbox_empleado13);
        ch14=(CheckBox)findViewById(R.id.chkbox_empleado14);
    }
    public void onClick(View view) {

        if (ch1.isChecked()) {
            empleadoConsultar = "Rosy";
        }
        if (ch2.isChecked()) {
            empleadoConsultar = "Ingrid";
        }
        if (ch3.isChecked()) {
            empleadoConsultar = "Amelia";
        }
        if (ch4.isChecked()) {
            empleadoConsultar = "Armando";
        }
        if (ch5.isChecked()) {
            empleadoConsultar = "Tono";
        }
        if (ch6.isChecked()) {
            empleadoConsultar = "Daniela";
        }
        if (ch7.isChecked()) {
            empleadoConsultar = "Cajas6";
        }
        if (ch8.isChecked()) {
            empleadoConsultar = "Carniceria1";
        }
        if (ch9.isChecked()) {
            empleadoConsultar = "Franco";
        }
        if (ch10.isChecked()) {
            empleadoConsultar = "Verduras1";
        }
        if (ch11.isChecked()) {
            empleadoConsultar = "Cajas7";
        }
        if (ch12.isChecked()) {
            empleadoConsultar = "Verduras2";
        }
        if (ch13.isChecked()) {
            empleadoConsultar = "Verduras3";
        }
        if (ch14.isChecked()) {
            empleadoConsultar = "Wilfredo";
        }


        switch (view.getId()) {
            case R.id.dateConsulta:
                showDatePickerDialog(dateConsultaDB);
                break;
            case R.id.dateConsulta2:
                showDatePickerDialog(dateConsultaDB2);
                break;
        }

    }
        /*
         * metodo para mostrar el calendario en un dialog
         */
        private void showDatePickerDialog(final EditText editText){
            DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    // +1 because january is zero
                    selectedDate = twoDigits(day) + "-" + twoDigits(month+1) + "-" + year;
                    editText.setText(selectedDate);
                }
            });
            newFragment.show(getFragmentManager(), "pic");
        }
        private String twoDigits(int n) {
            return (n<=9) ? ("0"+n) : String.valueOf(n);
        }



    /**
     * Metodo para hacer la consula a la DB con el button
     */
    public void consultaDB(View view) throws ParseException {




        //imprimir lo que trae la consulta
        //realizar operaciones de SUMA con los tiempos


        fechainicial= dateConsultaDB.getText().toString();
        fechafinal= dateConsultaDB2.getText().toString();

        //convertir el tipo de dato (String) del picker en long o date para hacer la consulta
        // el siguiente parseo causa que truene el programa SOLUCION: agregar la exception de abajo
            //date1FechaInit=new SimpleDateFormat("dd/MM/yyyy").parse(fechainicial);
        try{

            date1FechaInit=new SimpleDateFormat("dd-MM-yyyy").parse(fechainicial);

        }catch (ParseException e) {
            e.printStackTrace();
        }
        try{

            date2FechaFinal=new SimpleDateFormat("dd-MM-yyyy").parse(fechafinal);

        }catch (ParseException e) {
            e.printStackTrace();
        }

        String diff, diff2, diff3, diff4;
        long mills6=0;
        //hacer la consulta

        Gastostb nominaEmpleado[] = null;
         nominaEmpleado = gastosDatabase.getGastosDao().consultaEmpleadoNomina(empleadoConsultar,fechainicial,fechafinal);
   /*     Gastostb nominaEmpleado2[]=null;

        for(int ji=0;ji<5;ji++){

            nominaEmpleado2[ji]=nominaEmpleado[ji];
        }
        */
      //  Gastostb nominaEmpleado[]= gastosDatabase.getGastosDao().obtenLinea(empleadoConsultar,fechafinal);
        //   Gastostb nominaEmpleado[] = gastosDatabase.getGastosDao().consultaFechasNomina(empleadoConsultar,date1FechaInit,date2FechaFinal);
        //revisar el formato con el que se INSERT la fecha en el MAIN,


      //creo que esta parte no hace nada
        String testEntrada="test";
        int longituddelNominaEmpleado = nominaEmpleado.length;
        int contador =0;
        for (int j=0;j<nominaEmpleado.length;j++){
            if(nominaEmpleado[j]==null||nominaEmpleado[j].toString()==""){
                testEntrada="toyFor";
                contador++;
            }
        }


        /**
         * Logica para corregir la impresion de fechas
         */
    /*    Gastostb nominaCorrectorEmpleado[]=null;
        int l=0;
        for (int k=0;k<nominaEmpleado.length;k++){
            if (fechainicial==nominaEmpleado[k].getFecha()){
              while(fechafinal!=nominaEmpleado[k].getSalida()){
               // for(int l=0;l<50;l++){
                nominaCorrectorEmpleado[l] = nominaEmpleado[k];
              //}
              }
              l++;
              }
        }
*/
        int IntgastoAlimentos,IntgastoDiversion,IntgastoTransporte,IntgastoHogar,IntgastoEducacion,IntgastoOtros;

        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        tv0.setText(" Fecha ");
        tv0.setTextColor(Color.WHITE);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" Entrada ");
        tv1.setTextColor(Color.WHITE);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText(" Break ");
        tv2.setTextColor(Color.WHITE);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText(" Break ");
        tv3.setTextColor(Color.WHITE);
        tbrow0.addView(tv3);

        TextView tv4 = new TextView(this);
        tv4.setText(" Salida ");
        tv4.setTextColor(Color.WHITE);
        tbrow0.addView(tv4);

        TextView tv5 = new TextView(this);
        tv5.setText(" TBreak ");
        tv5.setTextColor(Color.WHITE);
        tbrow0.addView(tv5);

        TextView tv6 = new TextView(this);
        tv6.setText(" TotSalida ");
        tv6.setTextColor(Color.WHITE);
        tbrow0.addView(tv6);


        TextView tv7 = new TextView(this);
        tv7.setText(" TmpoTot ");
        tv7.setTextColor(Color.WHITE);
        tbrow0.addView(tv7);
      //  stk.addView(tbrow0);

        TextView tv8 = new TextView(this);
        tv8.setText(" SUMA ");
        tv8.setTextColor(Color.WHITE);
        tbrow0.addView(tv8);
        stk.addView(tbrow0);


        //nominaempleado es el arreglo que se trae toda la info del empleado de la DB

       for (int i = 0; i < nominaEmpleado.length; i++) {

            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);


           /*
            problema: manejar los datos que vienen de la DB y son nulos o vacios y llegar al arreglo
            truena porque el arreglo tiene nulls, hay que darle la vuelta
            */

           /*
            Aqui se podria meter la logica par que solo imprima el rango elegido
            via una doble confirmacion de las fechas elegidas arriba
            */
             t1v.setText("" +(nominaEmpleado[i].getFecha()));

            t1v.setTextColor(Color.WHITE);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);
            TextView t2v = new TextView(this);

            t2v.setText(" | " + nominaEmpleado[i].getEntrada());
            t2v.setTextColor(Color.WHITE);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);
            TextView t3v = new TextView(this);
           // t3v.setText("" +longituddelNominaEmpleado);
            t3v.setText(" | " + nominaEmpleado[i].getBreak1());
            t3v.setTextColor(Color.WHITE);
            t3v.setGravity(Gravity.CENTER);
            tbrow.addView(t3v);
            TextView t4v = new TextView(this);
           // t4v.setText(" | "+fechainicial);
            t4v.setText(" | " + nominaEmpleado[i].getBreak2());
            t4v.setTextColor(Color.WHITE);
            t4v.setGravity(Gravity.CENTER);
            tbrow.addView(t4v);

            TextView t5v = new TextView(this);
          //  t5v.setText(" | " +fechafinal);
            t5v.setText(" | " + nominaEmpleado[i].getSalida());
            t5v.setTextColor(Color.WHITE);
            t5v.setGravity(Gravity.CENTER);
            tbrow.addView(t5v);

            TextView t6v = new TextView(this);
            //logica para la resta del break
            SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
            try {
                Date date3 = format2.parse(nominaEmpleado[i].getBreak1());
                Date date4 = format2.parse(nominaEmpleado[i].getBreak2());
                long mills2 = date4.getTime()-date3.getTime();

                int hours2 = (int)(mills2/(1000*60*60));
                int mins2 = (int) ((mills2/ (1000*60)) % 60);
                diff2= hours2 + ":" +mins2;
                t6v.setText(" | " +diff2);

            } catch (ParseException e) {
                e.printStackTrace();
            }

         //   t6v.setText(" | " + nominaEmpleado[i].getSalida());
            t6v.setTextColor(Color.RED);
            t6v.setGravity(Gravity.CENTER);
            tbrow.addView(t6v);

            TextView t7v = new TextView(this);
            //logica para la resta del tiempo total
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            try {
                Date date1 = format.parse(nominaEmpleado[i].getEntrada());
                Date date2 = format.parse(nominaEmpleado[i].getSalida());
                long mills = date2.getTime()-date1.getTime();
              // Log.v("Data1",""+date1.getTime());
               // Log.v("Date2",""+date2.getTime());
                int hours = (int)(mills/(1000*60*60));
                int mins = (int) ((mills/ (1000*60)) % 60);
                diff= hours + ":" +mins;
                t7v.setText(" | " +diff);
               // System.out.println(date);

             } catch (ParseException e) {
                e.printStackTrace();
            }
            t7v.setTextColor(Color.MAGENTA);
            t7v.setGravity(Gravity.CENTER);
            tbrow.addView(t7v);
         //   stk.addView(tbrow);

            // aqui va la logica para descontar los  minutos si se pasa de 20 min ded break
            TextView t8v = new TextView(this);
            TextView t9v = new TextView(this);
            SimpleDateFormat format3 = new SimpleDateFormat("HH:mm:ss");

            try {
                Date date5 = format3.parse(nominaEmpleado[i].getEntrada());
                Date date6 = format3.parse(nominaEmpleado[i].getSalida());
                Date date7 = format3.parse(nominaEmpleado[i].getBreak2());
                Date date8 = format3.parse(nominaEmpleado[i].getBreak1());
                //date8 = Thu Jan 01 21:29:49 PST 1970

                long mills5=0;
                int toleanciaBreak = 20;
                //tiempo total
                long mills3 = date6.getTime() - date5.getTime();
                //tiempo total de break
                long mills4 = date7.getTime() - date8.getTime();

                int hours4 = (int) (mills4 / (1000 * 60 * 60));
                int mins4 = (int) ((mills4 / (1000 * 60)) % 60);
                //el tiempo lo pone en  -8 horas por eso no me daba 00:20

                 String timeDiff = "-8:20:00";  //original
               //   String timeDiff = "-7:40:00";    //cambio de agosto 2021
             //      String cincoHoras = "-13:00:00";

                Date date10 = format3.parse(timeDiff);   //esta linea si funcion pero imprime toda la fecha desde el ano, mes dia, etc
             //   Date date11= format3.parse(cincoHoras);
                // date10 = Thu Jan 01 00:20:00 PST 1970
                //problema a resolver, tomar solo hh:mm:ss de una cadena de fecha larga
                //checar https://stackoverflow.com/questions/26969175/convert-string-to-hhmm-format
                //String time = new SimpleDateFormat("HH:mm").format(mCalendar.getTime());
                //el tiempo lo pone en  -8 horas por eso no me daba 00:20, YA FUNCIONA
                long mills7 = date10.getTime();
              //  long mills8 = date11.getTime();


                if (mills3<5){



                }

             //  if(mills3>mills8) { esta comparacion no funciona
                    if (mills4 > mills7) {
                        mills5 = mills3 - (mills4 - mills7);
                    } else {
                        mills5 = mills3;
                    }
             //   }


                   mills6=mills6+mills5;

                        /*
                if(toleanciaBreak<0) {
                    mills5= mills3-mills7;
                    //restar losminutos solamente
                    auxmins1=auxmins1  -toleanciaBreak2;
                    //  auxiliar="pas";
                }else if(toleanciaBreak>0){
                    mills5=mills3;
                    auxmins1=auxmins1;
                    // los minutos se quedan igual

                }
*/
                int hours3 = (int)(mills5/(1000*60*60));
                int mins3 = (int) ((mills5/ (1000*60)) % 60);
                int hours5 = (int)(mills6/(1000*60*60));
                int mins5 = (int) ((mills6/ (1000*60)) % 60);
                //zona de pruebas
                int auxhours5 = (int)(mills7/(1000*60*60));
                int auxmins5 = (int) ((mills7/ (1000*60)) % 60);

                 diff3= hours3 + ":" +mins3;
              //  diff4= auxhours5 + ":" +auxmins5;
                 diff4= hours5+":"+mins5;  //original
                  t8v.setText(" | "+diff3 );   //ORIGINAL imprime la dierencia de tiempo - break ORIGNAL
                //t8v.setText(" | "+date2FechaFinal );

                t9v.setText(" | "+diff4 );// trae ya la suma recursiva de todo el tiempo
              //  t9v.setText(" | --" );  //progoma;
             //  t9v.setText(" |"+date1FechaInit );
             // t9v.setText(" |"+longituddelNominaEmpleado );

            } catch (ParseException e) {
                e.printStackTrace();
            }

            t8v.setTextColor(Color.GREEN);
            t8v.setGravity(Gravity.CENTER);
            tbrow.addView(t8v);
           // stk.addView(tbrow);

            t9v.setTextColor(Color.WHITE);
            t9v.setGravity(Gravity.CENTER);
            tbrow.addView(t9v);
            stk.addView(tbrow);

            //nominaEmpleado.finalize(void);
        }



    }


}
