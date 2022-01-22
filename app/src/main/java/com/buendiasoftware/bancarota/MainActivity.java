package com.buendiasoftware.bancarota;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;



public class MainActivity<i> extends AppCompatActivity {
    public static final String DATE_FORMAT_1 = "hh:mm a";
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public String date, fechaPonchada, empleado, hEntrada, hBreak1, hBreak2, hsalida;
    public Date fechaTrabajada;
    private GastosDB gastosDatabase=GastosDB.getInstance(MainActivity.this);
    private TextView tvEmpleado, tvEntrada,tvBreakSalida,tvBreakEntrada,tvSalida,tvFecha;
    int estadoEmpleado=0, contadorParaBorrar=0;
    int[] empleados = new int[14],controlParaBorar=new int[14];
    String[] estadoEmpleadoArray = new String[14];
    boolean borrar=false;
    boolean[] borrarRegistros = new boolean[14];
    public int counter;
    //MODIDIFACTIO
    Spinner spinner;
    private TextClock tClock;

    //button ponchador
    private Button buttonEntrada;


  //  DateFormat dateFecha = new SimpleDateFormat("yyyy-MM-dd");
  DateFormat dateFecha = new SimpleDateFormat("dd-MM-yyyy");

    //comentado ago.10 DateFormat hourFormat = new SimpleDateFormat("kk:mm");
    Date fecha = new Date();

     @Override
    /*
    * Method to create the Toolbar where the Menu will be inserted
    * */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //prueba para igualar las fechas
        // date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        // date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        //borra todo lo de hoy
        // gastosDatabase.getGastosDao().deleteGastos(date);
      //MOD  getValuesAndWrite();
         spinner = findViewById(R.id.spinnerEmpleados);
// Create an ArrayAdapter using the string array and a default spinner layout
         ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this ,R.array.empleados_Temp, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
         adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
         spinner.setAdapter(adapter);
         addListenerOnSpinnerItemSelection();
         addListenerOnButton();

     }
    public void addListenerOnSpinnerItemSelection() {

        // Ligando localmente elementos de texto Java con HTML de la vista
        tvEmpleado= findViewById(R.id.nombreEmpleado);
        tvEntrada=findViewById(R.id.entradaEmpleado);
        tvBreakEntrada= findViewById(R.id.break1Empleado);
        tvBreakSalida=findViewById(R.id.break2Empleado);
        tvSalida=findViewById(R.id.salidaEmpleado);
        tvFecha=findViewById(R.id.fecha2);

        spinner = findViewById(R.id.spinnerEmpleados);
        spinner.setOnItemSelectedListener(new OnSpinnerItemSelectedListener());

    }

    public void addListenerOnButton() {
        Calendar cal2 = Calendar.getInstance();
        spinner = findViewById(R.id.spinnerEmpleados);
        buttonEntrada = findViewById(R.id.buttonPonchar);
        tClock = findViewById(R.id.reloj);
           //inicializar valores para insertar en DB
        //Oct 1, movimos fecha para dentro del lick listener para ver si corrige error de fechas en la tablet
         fechaPonchada=dateFecha.format(fecha);
        fechaTrabajada=cal2.getTime();


        buttonEntrada.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               hEntrada= getCurrentTime();
                fechaPonchada=dateFecha.format(fecha);
               // fechaTrabajada=cal2.getTime();
                empleado=String.valueOf(spinner.getSelectedItem());
               // String yaHabiaPonchado = existeAlgunRegistro(empleado,fechaPonchada);
                String yaHabiaPonchado =existeAlgunRegistro(empleado,fechaPonchada);

                estadoEmpleado= checaEstadoEmpleado(spinner.getSelectedItemPosition(),empleado,fechaPonchada);
                String statusEmpleado = estadoEmpleadoArray[spinner.getSelectedItemPosition()];
               // testEstado= checaEstadoEmpleado(spinner.getSelectedItemPosition(),spinner.getSelectedItem());
                Toast.makeText(MainActivity.this,
                        "   ::: " +
                                " "+ spinner.getSelectedItem()
                                +" ::: "
                              //  +   "\nEmployee: "+empleado
                               // +"\nposicion: " + spinner.getSelectedItemPosition()
                               // +"\nisEmpleadoActivo" +empleados[spinner.getSelectedItemPosition()] imprime lo mismo que estadoDelEmpleado
                                   +"\nFecha:" +fechaPonchada
                                 //  +"\nEstadoDelEmpleado" +estadoEmpleado
                                //  +"\nFecha de ponchada " +fechaTrabajada
                                 +"\nPonchada de   >    " +statusEmpleado
                            //    +"\nHora system:"+ tClock.getText()
                               +"\nHora system:"+ hEntrada,

                        Toast.LENGTH_SHORT).show();
                //imprimir en el textview de la pagina principal los valores de la DB
              //estos se tienen que quitar de aqui, lo que va controlar el el checaEstadoEmpleado
              // no mover el getReport de aqui, porque truena
                getReport();
            //agregado agosto 13 PARA borrar va aqui y si funciona, falta la logica

            //comentado para que borrer todo el registro entero cuando se ha comletado el ciclo con la salida
                Gastostb[] consultaEmpleadoHoy5 = gastosDatabase.getGastosDao().obtenLinea(empleado, fechaPonchada);

                if(  consultaEmpleadoHoy5[0].getEmpleadoPosicion()==0){
                    //llamar al timer
                    new CountDownTimer(10000, 1000){
                        public void onTick(long millisUntilFinished){
                             //tvBreakSalida.setText(String.valueOf(counter));
                             //counter++;
                        }
                        public  void onFinish(){
                           // textView.setText("FINISH!!");
                            limpiaTextView();
                        }
                    }.start();


                   // borrarRegistros[spinner.getSelectedItemPosition()]=false;
                    //borrar=false;
                }
            }
        });

    }
       public void escribeEstadoEmpleado(String empleadoConsul, String entradaConsul, String break1Consul, String break2Consul, String salidaConsul,String fechaConsul){
     //   public void escribeEstadoEmpleado(String entradaConsul){
     //esto es el manejador de los texviews para imprimir abajo del boton de la fachada
        tvEmpleado.setText(empleadoConsul);
        tvEntrada.setText(entradaConsul);
        tvBreakEntrada.setText(break1Consul);
        tvBreakSalida.setText(break2Consul);
        tvSalida.setText(salidaConsul);
        tvFecha.setText(fechaConsul);
    }
    public static String getCurrentTime() {
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
         return sdf.format(c1.getTime());
    }

    public int checaEstadoEmpleado (int posicionEmpleado,String empleadoSpinner, String hoyFecha){
      //la hora de entrada se tiene que pedir cada vez que se ponche, sino toma una hora vieja
        hEntrada= getCurrentTime();
        //esta es una ponchada de entrada
        //hacer una consulta si es nula writeToDB

        //traer el estado del empleado
        Gastostb[] consultaEmpleadoHoy4 = gastosDatabase.getGastosDao().obtenLinea(empleadoSpinner, hoyFecha);
      //  String statusEmpleado = estadoDelEmpleado(empleadoSpinner,hoyFecha);
        if(consultaEmpleadoHoy4.length==0){
            estadoEmpleadoArray[posicionEmpleado]= "Entrada";
            writeToDB();
           // borrarRegistros[posicionEmpleado]=false;
        }else if(consultaEmpleadoHoy4[0].getEmpleadoPosicion()==1){
            estadoEmpleadoArray[posicionEmpleado]= "Break1";
            updateBreak1DB();
        } else if (consultaEmpleadoHoy4[0].getEmpleadoPosicion()==2) {
            estadoEmpleadoArray[posicionEmpleado] = "Break2";
            updateBreak2DB();
        } else if (consultaEmpleadoHoy4[0].getEmpleadoPosicion()==3){
            estadoEmpleadoArray[posicionEmpleado] = "Salida";
            updateSalidaDB();
           // borrarRegistros[posicionEmpleado] = true;
        }

        /*
        if (empleados[posicionEmpleado]==0){
            estadoEmpleadoArray[posicionEmpleado]= "Entrada";
            //esto incrementa el valor dento del arreglo
            empleados[posicionEmpleado]++;
            writeToDB();
            borrarRegistros[posicionEmpleado]=false;
            //getReport(); //con el report aqui truena ago.10
            //esto  es un break

           else if ((empleados[posicionEmpleado]==1)){
            empleados[posicionEmpleado]++;
            estadoEmpleadoArray[posicionEmpleado]= "Break1";
            updateBreak1DB();


        else if (empleados[posicionEmpleado] == 2) {
            empleados[posicionEmpleado]++;
            estadoEmpleadoArray[posicionEmpleado] = "Break2";
            updateBreak2DB();

        } else if (empleados[posicionEmpleado] == 3) {
            //esto es una salida
            empleados[posicionEmpleado] = 0;
            estadoEmpleadoArray[posicionEmpleado] = "Salida";
            updateSalidaDB();
            borrarRegistros[posicionEmpleado] = true;
            //  borrar=true;

        }//else if (empleados[posicionEmpleado]==4){
            //esto es una salida
           // empleados[posicionEmpleado]=0;
           // estadoEmpleadoArray[posicionEmpleado]= "Salida";
           // updateSalidaDB();
        //}
      */
        // oCT 12, ESTe metodo no regresa nada, se puede cambia a void sin pedos
        return  empleados[posicionEmpleado];
    }

    public void getReport(){
     // Esta era una prueba con Cursor, pero no me salio  Cursor cursor= gastosDatabase.getGastosDao().consultaFila("Leonardo",fechaPonchada);
        //el insert y la consulta estan pensados para que nadamas se ponche una vez al dia, si el empleado poncha dos vecces
        //en un dia el programa puede no funcionar bien.
        //consultaFila
        Gastostb[] objetoEmpleado = gastosDatabase.getGastosDao().obtenLinea(empleado, fechaPonchada);
        String empleaadoConsultado=objetoEmpleado[0].getEmpleado();
        String entradaConsultada= objetoEmpleado[0].getEntrada();
        String break1Consultado=objetoEmpleado[0].getBreak1();
        String break2Consultado=objetoEmpleado[0].getBreak2();
        String salidaConsultada=objetoEmpleado[0].getSalida();
        String fechaConsultada=objetoEmpleado[0].getFecha();
       escribeEstadoEmpleado(empleaadoConsultado,entradaConsultada,break1Consultado,break2Consultado,salidaConsultada,fechaConsultada);
    }
    //este metodo limpia la fachada que tenia los valores de las horas capturadas y ahora las pone en 0
    public void limpiaTextView(){
        tvEmpleado.setText("0");
        tvEntrada.setText("0");
        tvBreakEntrada.setText("0");
        tvBreakSalida.setText("0");
        tvSalida.setText("0");
        tvFecha.setText("0");

    }
    /*
     * Method to insert time, date, name to DB
     * */
    public void writeToDB(){
        //el insert y la consulta estan pensados para que nadamas se ponche una vez al dia, si el empleado poncha dos vecces
        //en un dia el programa puede no funcionar bien.

        Gastostb tablaEmpleados = new Gastostb();
     //   tablaEmpleados.setEmpleadoId(1);
        String empleadoInsertado= String.valueOf(spinner.getSelectedItem());
        tablaEmpleados.setEmpleado(empleadoInsertado);
        tablaEmpleados.setEntrada(hEntrada);
        tablaEmpleados.setBreak1("0:0");
        tablaEmpleados.setBreak2("0:0");
        tablaEmpleados.setSalida("0:0");
        tablaEmpleados.setFecha(fechaPonchada);
        tablaEmpleados.setEmpleadoPosicion(1);
        tablaEmpleados.setFechaTrabajada(fechaTrabajada);
        //        tablaEmpleados.setBorrarRegistros("false");
        gastosDatabase.getGastosDao().insert(tablaEmpleados);
    }

    public void updateBreak1DB(){
        //el insert, update y la consulta estan pensados para que nadamas se ponche una vez al dia, si el empleado poncha dos vecces
        //en un dia el programa puede no funcionar bien.
        Gastostb tablaEmpleados = new Gastostb();
        String empleadoInsertado= String.valueOf(spinner.getSelectedItem());
        String fechaUpdated = fechaPonchada;
        String horaBreak1 = hEntrada;
        int setEmpleadoPosicionBreak1 = 2;
      //  gastosDatabase.getGastosDao().updateBreak1(empleadoInsertado,horaBreak1,fechaPonchada);
        gastosDatabase.getGastosDao().updateBreak1(empleadoInsertado,horaBreak1,fechaPonchada,setEmpleadoPosicionBreak1);

    }
    public void updateBreak2DB(){
        Gastostb tablaEmpleados = new Gastostb();
        String empleadoInsertado= String.valueOf(spinner.getSelectedItem());
        String fechaUpdated = fechaPonchada;
        String horaBreak2 = hEntrada;
        int setEmpleadoPosicionBreak2 = 3;
        gastosDatabase.getGastosDao().updateBreak2(empleadoInsertado,horaBreak2,fechaPonchada,setEmpleadoPosicionBreak2);
    }
    public void updateSalidaDB(){
        Gastostb tablaEmpleados = new Gastostb();
        String empleadoInsertado= String.valueOf(spinner.getSelectedItem());
        String fechaUpdated = fechaPonchada;
        String horaSalida = hEntrada;
        int setEmpleadoPosicionBreak1 = 0;

        gastosDatabase.getGastosDao().updateSalida(empleadoInsertado,horaSalida,fechaPonchada,setEmpleadoPosicionBreak1);
        //limpiaTextView();
    }

    /*
    * Method to add the MENU (inflate) to the Toolbar
    * */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /*
    * Method to control the Menu inserted in the Toolbar
    * */
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intentConfig = new Intent (this,config.class);
        Intent intentReport = new Intent (this,report.class);
        Intent intentHelp = new Intent (this,help.class);
        Intent intentFeedback = new Intent (this,Feedback.class);

        switch (item.getItemId()) {
            case R.id.config:
                // User chose the "Settings" item, show the app settings UI...
                //String messageConfig = "menu de Configuracion";
                //intentConfig.putExtra(EXTRA_MESSAGE, messageConfig);
                startActivity(intentConfig);
                return true;

            case R.id.report:
                String messageReport = "<Aqui van los reportes>";
                intentReport.putExtra(EXTRA_MESSAGE, messageReport);
                startActivity(intentReport);
                return true;
                // User chose the "Favorite" action, mark the current item
                // as a favorite...

            case R.id.help:
                String messageHelp = " AYUDA ";
                intentHelp.putExtra(EXTRA_MESSAGE, messageHelp);
                startActivity(intentHelp);
                return true;

            case R.id.feedback:
                String messageFeedback = " Feedback";
                intentFeedback.putExtra(EXTRA_MESSAGE, messageFeedback);
                startActivity(intentFeedback);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    /** Called when the user taps the Send button */
   /*
   * Method to do something after button is press
   * */
    public void cargar(View view) {
        // Do something in response to button
        String empleadoCargar = String.valueOf(spinner.getSelectedItem());
        String auxiliar="";
        String empleaadoConsultadoCarga=String.valueOf(spinner.getSelectedItem());
        String entradaConsultadaCarga="";
        String break1ConsultadoCarga="";
        String break2ConsultadoCarga= "";
        String salidaConsultadaCarga="";
        String fechaConsultadaCarga=fechaPonchada;

        Gastostb[] consultaEmpleadoHoy = gastosDatabase.getGastosDao().obtenLinea(empleadoCargar, fechaPonchada);
       if(consultaEmpleadoHoy.length!=0){
          entradaConsultadaCarga= consultaEmpleadoHoy[0].getEntrada();
          break1ConsultadoCarga=consultaEmpleadoHoy[0].getBreak1();
          break2ConsultadoCarga=consultaEmpleadoHoy[0].getBreak2();
          salidaConsultadaCarga=consultaEmpleadoHoy[0].getSalida();
        }

        tvEmpleado.setText(empleaadoConsultadoCarga);
        tvEntrada.setText(entradaConsultadaCarga);
        tvBreakEntrada.setText(break1ConsultadoCarga);
        tvBreakSalida.setText(break2ConsultadoCarga);
        tvSalida.setText(salidaConsultadaCarga);
        tvFecha.setText(fechaPonchada);

                }
        public String existeAlgunRegistro(String empleado, String fechaPalRegistro){
            Gastostb[] consultaEmpleadoHoy2 = gastosDatabase.getGastosDao().obtenLinea(empleado, fechaPalRegistro);

                if(consultaEmpleadoHoy2.length!=0){
                    return "verdadero";
                }else{
                    return "falso";
                }

        }
        //este metodo es para que regrese todas las horas del empleado y checar solo la que sea necesaria.

        public Gastostb[] estadoDelEmpleado(String empleado, String fechaPalRegistro){
            Gastostb[] consultaEmpleadoHoy3 = gastosDatabase.getGastosDao().obtenLinea(empleado, fechaPalRegistro);
            return consultaEmpleadoHoy3;
        }

    }

