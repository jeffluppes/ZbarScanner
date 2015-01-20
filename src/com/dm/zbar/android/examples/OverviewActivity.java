package com.dm.zbar.android.examples;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dm.zbar.android.scanner.ZBarConstants;
import com.dm.zbar.android.scanner.ZBarScannerActivity;

import net.sourceforge.zbar.Symbol;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class OverviewActivity extends Activity {

    private static final int ZBAR_SCANNER_REQUEST = 0;
    private static final int ZBAR_QR_SCANNER_REQUEST = 1;

    public List<String> shoppingList = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TextView results = (TextView) findViewById(R.id.result);
        results.setMovementMethod(new ScrollingMovementMethod());
    }

    public void launchScanner(View v)
    {
        if (isCameraAvailable())
        {
            Intent intent = new Intent(this, ZBarScannerActivity.class);
            startActivityForResult(intent, ZBAR_SCANNER_REQUEST);
        }
        else
        {
            Toast.makeText(this, "Camera is niet beschikbaar!", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isCameraAvailable()
    {
        PackageManager pm = getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode) {
            case ZBAR_SCANNER_REQUEST:
            case ZBAR_QR_SCANNER_REQUEST:
                if (resultCode == RESULT_OK)
                {
                    MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.beep);
                    mp.start();

                    //TODO: Controleer of producten bestaan in database, voeg anders product niet toe.
                    //TODO: API Call controleer of product in database staat.


                    //TODO: Aantal gescande producten bij dubbel gescande producten.
                    TextView text1 = (TextView) findViewById(R.id.result);

                    shoppingList.add(data.getStringExtra(ZBarConstants.SCAN_RESULT));
                    String result = "";

                    for(int y=0; y<=shoppingList.size()-1; y++){
                        result+= shoppingList.get(y) + "\n";
                    }
                    text1.setText(result);
                }
                else if(resultCode == RESULT_CANCELED && data != null)
                {
                    String error = data.getStringExtra(ZBarConstants.ERROR_INFO);
                    if(!TextUtils.isEmpty(error))
                    {
                        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    public void saveShoppingList(View view){

        new AlertDialog.Builder(this)
            .setTitle("Afrekenen")
            .setMessage("Uw boodschappenlijst zal worden \nopgeslagen in de database. \nU kunt afrekenen bij de kassa!")
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //TODO: API STORE shopped list in external database.
                }
            })
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show();
    }
}
