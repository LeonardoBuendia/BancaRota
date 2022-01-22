package com.buendiasoftware.bancarota;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Feedback extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Toolbar feedbackToolbar = (Toolbar) findViewById(R.id.feedback_toolbar);
        setSupportActionBar(feedbackToolbar);

        // Get a support ActionBar corresponding to this toolbar
        //  getActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar feedbackAb = getSupportActionBar();
        // Enable the Up button
        feedbackAb.setDisplayHomeAsUpEnabled(true);
    }
    public void sendFeedback(View view) {
        // Do something in response to button
        EditText feedbackText =(EditText) findViewById(R.id.feedbackEditText);
        String feedbackTextString = feedbackText.getText().toString();
        EditText nameFeedback =(EditText) findViewById(R.id.editNombre);
        String nameFeedbackString = nameFeedback.getText().toString();
        String [] correoApp = new String[]{"buendia.software@gmail.com"};

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
         //emailIntent.putExtra(Intent.EXTRA_EMAIL , new String[]{"leonardo.buendia@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_EMAIL , correoApp);
         emailIntent.putExtra(Intent.EXTRA_SUBJECT, nameFeedbackString);
         emailIntent.putExtra(Intent.EXTRA_TEXT, feedbackTextString);

      //  if (emailIntent.resolveActivity(getPackageManager()) != null) {
        //    startActivity(emailIntent);
       // }

        try {
           // startActivity(Intent.createChooser(emailIntent, "Send mail..."));
              startActivity(emailIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Feedback.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
        //limpiar pantalla
        feedbackText.setText(null);
        nameFeedback.setText(null);
        //regresa a menu principal
    }
}

