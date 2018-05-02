package android.com.traineeshare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ProgressDialog mPr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final EditText et_email = findViewById(R.id.et_email);
        final EditText et_pass = findViewById(R.id.et_password);
        final Button btn_Login = findViewById(R.id.btn_Login);
        mAuth = FirebaseAuth.getInstance();
        mPr = new ProgressDialog(this);

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et_email.getText().toString().trim();
                String pass = et_pass.getText().toString().trim();
                checkLogin(email,pass);
            }
        });

    }

    public void checkLogin(String email, final String pass) {
        mPr.setMessage("Signing in...");
        mPr.show();
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("Login Successful", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            mPr.dismiss();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            Log.w("Login Fail", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            mPr.dismiss();
                        }
                    }
                });
    }
}
