package com.newsapp.newsapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.newsapp.newsapplication.LoginActivity;

import java.io.ByteArrayOutputStream;


public class UserRegistro extends AppCompatActivity {

    private TextView loginLink;
    private ImageView imageView;
    private EditText password;
    private EditText name;
    private EditText email;
    private Button registrar;
    private DatabaseManagerUser managerUsuario;
    private String sPassword, sName, sEmail;
    private int request_code = 1;
    private Bitmap bitmap_photo;
    private RoundedBitmapDrawable roundedBitmapDrawable;
    private byte[] bytes;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_user);

        imageView = (ImageView) findViewById(R.id.user_image_registry);
        loginLink = (TextView)findViewById(R.id.link_login);
        email = (EditText)findViewById(R.id.registration_mail);
        password = (EditText)findViewById(R.id.password_registration);
        name = (EditText)findViewById(R.id.registration_name);
        registrar = (Button)findViewById(R.id.btn_user_registration);
        bitmap_photo = BitmapFactory.decodeResource(getResources(),R.drawable.imagen);
        roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap_photo);
        roundedBitmapDrawable.setCircular(true);
        imageView.setImageDrawable(roundedBitmapDrawable);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrar();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = null;
                // verification of the platform version
                if(Build.VERSION.SDK_INT < 19){
                    //android 4.3
                    i = new Intent();
                    i.setAction(Intent.ACTION_GET_CONTENT);
                }else {
                    //android 4.4
                    i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                }
                i.setType("image/*");
                startActivityForResult(i, request_code);
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void registrar(){

        if (validate()) return;

        sEmail = email.getText().toString();
        sPassword = password.getText().toString();
        sName = name.getText().toString();

        final ProgressDialog progressDialog = new ProgressDialog(UserRegistro.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating account ...");
        progressDialog.show();

        managerUsuario = new DatabaseManagerUser(this);

        email.getText().clear();
        password.getText().clear();
        name.getText().clear();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if(managerUsuario.checkRegister(sEmail)){
                            progressDialog.dismiss();
                            password.setText(sPassword);
                            name.setText(sName);
                            String mesg = String.format("The email you have entered is already registered", (Class<?>) null);
                            Toast.makeText(getApplicationContext(),mesg, Toast.LENGTH_LONG).show();
                        }else {
                            managerUsuario.insert_parameters(null, sEmail, sPassword, bytes, sName);
                            String mesg = String.format("has been saved in the BBDD", sName);
                            Toast.makeText(getBaseContext(),mesg, Toast.LENGTH_LONG).show();
                            Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                            intent.putExtra("IDENT",sEmail);
                            startActivity(intent);
                            finish();
                            progressDialog.dismiss();
                        }
                    }
                }, 3000);
    }

    private boolean validate() {
        boolean valid = true;

        String sName = name.getText().toString();
        String sPassword = password.getText().toString();
        String sEmail = email.getText().toString();

        if (sName.isEmpty() || sName.length() < 3) {
            name.setError("Enter at least 3 characters");
            valid = false;
        } else {
            name.setError(null);
        }

        if (sEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(sEmail).matches()) {
            email.setError("invalid email address");
            valid = false;
        } else {
            email.setError(null);
        }

        if (sPassword.isEmpty() || password.length() < 4 || password.length() > 10) {
            password.setError("Enter between 4 to 10 characters");
            valid = false;
        } else {
            password.setError(null);
        }

        return valid;
    }

    private byte[] imageToByte(ImageView image) {
        Bitmap bitmaphoto = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmaphoto.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == request_code){
            imageView.setImageURI(data.getData());
            bytes = imageToByte(imageView);

            // to see the image in a circle
            Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
            roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
            roundedBitmapDrawable.setCircular(true);
            imageView.setImageDrawable(roundedBitmapDrawable);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}


