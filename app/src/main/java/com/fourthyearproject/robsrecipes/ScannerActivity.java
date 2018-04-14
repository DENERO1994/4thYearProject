package com.fourthyearproject.robsrecipes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

// Scanner imports
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import static android.Manifest.permission.CAMERA;

import java.util.HashMap;

public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if(!checkPermission())
            {
                requestPermission();
            }
        }
    }

    private boolean checkPermission()
    {
        return (ContextCompat.checkSelfPermission(ScannerActivity.this, CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if(checkPermission())
            {
                if(scannerView == null)
                {
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            }
            else
            {
                requestPermission();
            }
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result)
    {
        final String scanResult = result.getText();
        boolean found = false;

        outerloop:
        for (final HashMap<String, String> item : HomeActivity.listIngredients)
        {
            for (String key : item.keySet())
            {
                if(key.matches(DownloadIngredients.KEY_BARCODE))
                {
                    if(item.get(key).matches(scanResult)) {
                        found = true;
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Scan Result");
                        builder.setPositiveButton("View", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Context context = scannerView.getContext();
                                Intent intent = new Intent(context, IngredientDetailsActivity.class);
                                intent.putExtra(DownloadIngredients.KEY_ID, item.get(DownloadIngredients.KEY_ID));
                                intent.putExtra(DownloadIngredients.KEY_BRAND, item.get(DownloadIngredients.KEY_BRAND));
                                intent.putExtra(DownloadIngredients.KEY_NAME, item.get(DownloadIngredients.KEY_NAME));
                                intent.putExtra(DownloadIngredients.KEY_WEIGHT, item.get(DownloadIngredients.KEY_WEIGHT));
                                intent.putExtra(DownloadIngredients.KEY_BARCODE, item.get(DownloadIngredients.KEY_BARCODE));
                                intent.putExtra(DownloadIngredients.KEY_IMAGE, item.get(DownloadIngredients.KEY_IMAGE));
                                context.startActivity(intent);
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                scannerView.resumeCameraPreview(ScannerActivity.this);
                            }
                        });
                        builder.setMessage(item.get(DownloadIngredients.KEY_NAME));
                        AlertDialog alert = builder.create();
                        alert.show();
                        break outerloop;
                    }
                    else
                    {
                        found = false;
                        break;
                    }
                }
            }
        }

        if(!found)
        {
            Toast.makeText(ScannerActivity.this, "Item not found", Toast.LENGTH_LONG).show();
            scannerView.resumeCameraPreview(ScannerActivity.this);
        }
    }
}