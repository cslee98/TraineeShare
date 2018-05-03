package android.com.traineeshare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import util.Password_Validator;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ProgressDialog mPr;
    private Password_Validator password_validator;

    private EditText et_email, et_pass;
    private Button btn_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        et_email = findViewById(R.id.et_email);
        et_pass = findViewById(R.id.et_password);
        btn_Login = findViewById(R.id.btn_Login);
        mAuth = FirebaseAuth.getInstance();
        mPr = new ProgressDialog(this);
        password_validator = new Password_Validator();

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et_email.getText().toString().trim();
                String pass = et_pass.getText().toString().trim();
                checkLogin(email,pass);
            }
        });

    }



    public void checkLogin(final String email, final String pass) {
        if (TextUtils.isEmpty(email)) {
            et_email.setError("Email cannot be empty");
        } else if(!email.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")){
            et_email.setError("Email invalid");
        } else if(TextUtils.isEmpty(pass)){
            et_pass.setError("Password cannot be empty");
        } else if(!password_validator.validate(pass)){
            et_pass.setError("Password invalid");
        }
        else {
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
                                et_pass.setText("");
                            }
                        }
                    });
        }
    }
}
