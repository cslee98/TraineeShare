package android.com.traineeshare;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import util.Connection_Detector;

public class SplashActivity extends AppCompatActivity {

    Connection_Detector connection_detector;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        connection_detector = new Connection_Detector(this);
        mAuth = FirebaseAuth.getInstance();
        if(connection_detector.isConnected()==true){
            Toast.makeText(this,"Connected",Toast.LENGTH_SHORT).show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    if(currentUser == null) {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();
                    }else{
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }
                }
            },3000);
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Not Connected. Go to Settings?")
                    .setCancelable(false);
            builder.setNegativeButton("Settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    startActivity(new Intent(Settings.ACTION_SETTINGS));
                }
            });
            builder.setNeutralButton("Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivity(new Intent(SplashActivity.this,SplashActivity.class));
                    finish();
                }
            });
            builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    System.exit(0);
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}
