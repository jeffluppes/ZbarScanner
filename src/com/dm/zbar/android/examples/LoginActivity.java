package com.dm.zbar.android.examples;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;


public class LoginActivity extends Activity {
    //Boolean to set if input is valid.
    //TODO: Must be set to false finally
    boolean isValid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /*
     * Method to open the overview page when user is signed in correctly.
     */
    public void openOverview(View view){

        //TODO: Connect with server/databse
        //TODO: Check if username and password are valid

        if(isValid) {
            Intent myIntent = new Intent(LoginActivity.this, OverviewActivity.class);
            LoginActivity.this.startActivity(myIntent);
        }
        else{
            new AlertDialog.Builder(this)
                .setTitle("Foutmelding")
                .setMessage("Uw gegevens zijn onjuist.\nProbeer opnieuw in te loggen!")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO: Clear input fields (username/password) after wrong input.
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

        }
    }
}
