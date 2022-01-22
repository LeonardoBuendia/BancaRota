package com.buendiasoftware.bancarota;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;


public class report extends AppCompatActivity implements View.OnClickListener {

    EditText dateConsultaDB, dateConsultaDB2;
    private GastosDB gastosDatabase=GastosDB.getInstance(report.this);
    String selectedDate,fechainicial, fechafinal, empleadoConsultar;
    private TextView RtextAlimentos, RtextDiversion, RtextTransporte, RtextHogar, RtextEducacion, RtextOtros, RtextGastoTotal;
    CheckBox  ch1, ch2, ch3, ch4, ch5, ch6, ch7, ch8,ch9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        Toolbar reportesToolbar = (Toolbar) findViewById(R.id.reportes_toolbar);
        setSupportActionBar(reportesToolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar reportesAb = getSupportActionBar();
        // Enable the Up button
        reportesAb.setDisplayHomeAsUpEnabled(true);

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

    }

    // This function is invoked when the button is pressed.
    public void onClick(View view){

        if (ch1.isChecked()) {
            empleadoConsultar = "Leonardo";
        }
        if (ch2.isChecked()) {
            empleadoConsultar = "Nancy";
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
            empleadoConsultar = "Iray";
        }
        if (ch7.isChecked()) {
            empleadoConsultar = "Roxana";
        }
        if (ch8.isChecked()) {
            empleadoConsultar = "Carniceria";
        }
        if (ch9.isChecked()) {
            empleadoConsultar = "Flotante";
        }



            switch (view.getId()){
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
    public void consultaDB(View view){

        fechainicial= dateConsultaDB.getText().toString();
        fechafinal= dateConsultaDB2.getText().toString();

        Gastostb nominaEmpleado[] = gastosDatabase.getGastosDao().consultaEmpleadoNomina(empleadoConsultar,fechainicial,fechafinal);

      //  String testEntrada = gastosDatabase.getGastosDao().entradaConsul(empleadoConsultar,fechafinal);
        String testEntrada="test";

       int contador =0;
        for (int j=0;j<nominaEmpleado.length;j++){
            if(nominaEmpleado[j]==null||nominaEmpleado[j].toString()==""){
               testEntrada="toyFor";
               contador++;
            }
        }

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
        stk.addView(tbrow0);
        for (int i = 0; i < nominaEmpleado.length; i++) {
            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            /* if(nominaEmpleado[i].getFecha()=="")
            {
                //nominaEmpleado[i].getFecha()="0";
                t1v.setText("0");
            }else {
                t1v.setText("" + nominaEmpleado[i].getFecha());
           }*/
            //t1v.setText("" + obtenLinea[i].getEntrada());

           /*
            problema: manejar los datos que vienen de la DB y son nulos o vacios y llegar al arreglo
            truena porque el arreglo tiene nulls, hay que darle la vuelta
            */

            t1v.setText("" +(nominaEmpleado[i].getFecha()));

            t1v.setTextColor(Color.WHITE);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);
            TextView t2v = new TextView(this);
          //  t2v.setText("Product " + nominaEmpleado.length);
            t2v.setText(" | " + nominaEmpleado[i].getEntrada());
            t2v.setTextColor(Color.WHITE);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);
            TextView t3v = new TextView(this);
           // t3v.setText("Rs." + fechainicial);
            t3v.setText(" | " + nominaEmpleado[i].getBreak1());
            t3v.setTextColor(Color.WHITE);
            t3v.setGravity(Gravity.CENTER);
            tbrow.addView(t3v);
            TextView t4v = new TextView(this);
           // t4v.setText("" + fechafinal);
            t4v.setText(" | " + nominaEmpleado[i].getBreak2());
            t4v.setTextColor(Color.WHITE);
            t4v.setGravity(Gravity.CENTER);
            tbrow.addView(t4v);

            TextView t5v = new TextView(this);

            t5v.setText(" | " + nominaEmpleado[i].getSalida());
            //t5v.setText("" + empleadoConsultar);
            t5v.setTextColor(Color.WHITE);
            t5v.setGravity(Gravity.CENTER);
            tbrow.addView(t5v);
            stk.addView(tbrow);
        }



      }
    }

