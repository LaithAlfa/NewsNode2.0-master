package com.newsapp.newsapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText eEmail, ePassword;
    private Button LoginIn;
    private TextView registrar;
    private String email;
    private String password;
    private Cursor check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        eEmail = (EditText)findViewById(R.id.Enter);
        ePassword = (EditText)findViewById(R.id.etpass);
        LoginIn = (Button)findViewById(R.id.button);
        registrar = (TextView)findViewById(R.id.signup);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),UserRegistro.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        LoginIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Start();
            }
        });
    }

    private void Start() {

        if (validate()) return;

        email = eEmail.getText().toString();
        password = ePassword.getText().toString();

        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("starting...");
        progressDialog.show();

        final DatabaseManagerUser databaseManager = new DatabaseManagerUser(getApplicationContext());

        eEmail.getText().clear();
        ePassword.getText().clear();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        if (databaseManager.checkRegister (email)){
                            check = databaseManager.getDb().rawQuery("SELECT EMAIL, password FROM demmo" + " WHERE EMAIL='"+email+"' AND password='"+password+"'",null);
                            if(check.moveToFirst()){
                                Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                                intent.putExtra("IDENT",email);
                                startActivity(intent);
                                finish();
                                progressDialog.dismiss();
                            }else{
                                eEmail.setText(email);
                                progressDialog.dismiss();
                                String mesg = String.format("Incorrect password", (Class<?>) null);
                                Toast.makeText(getApplicationContext(),mesg, Toast.LENGTH_LONG).show();
                            }
                        }else{
                            progressDialog.dismiss();
                            String mesg = String.format("The e-mail you have entered does not match any account", (Class<?>) null);
                            Toast.makeText(getApplicationContext(),mesg, Toast.LENGTH_LONG).show();
                        }
                    }
                }, 3000);

    }

    private boolean validate() {
        boolean valid = true;

        String email = eEmail.getText().toString();
        String password = ePassword.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            eEmail.setError("Enter a valid email address");
            valid = false;
        } else {
            eEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            ePassword.setError("Between 4 and 10 characters");
            valid = false;
        } else {
            ePassword.setError(null);
        }

        return valid;
    }
}

