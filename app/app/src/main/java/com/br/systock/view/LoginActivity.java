package com.br.systock.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.br.systock.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;


public class LoginActivity extends AppCompatActivity {

    private Button button_to_register_of_types;
    private TextView register;
    private EditText email;
    private EditText password;
    private FirebaseAuth auth;
    private String token;
    private SharedPreferences prefs = null;
    private FirebaseAuth mAuth;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    private String TAG;
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        String token = task.getResult().getToken();
                    }
                });
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        register = (TextView) findViewById((R.id.register_txt));
        email = (EditText) findViewById(R.id.edit_login);
        password = (EditText) findViewById(R.id.edit_password);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenSecondActivity();
            }

        });
        prefs = getSharedPreferences("com.mycompany.myAppName", MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) {
            startActivity(new Intent(getApplicationContext(), TutorialActivity.class));
            // using the following line to edit/commit prefs
            prefs.edit().putBoolean("firstrun", false).commit();
        }

        button_to_register_of_types = (Button) findViewById(R.id.button_to_register_of_types);
        button_to_register_of_types.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txt_email = email.getText().toString();

                String txt_password = password.getText().toString();
                OpenThirdActivity(txt_email, txt_password);

            }
        });

    }


    public void OpenSecondActivity() {

        Intent register_screen = new Intent(this, RegisterActivity.class);
        startActivity(register_screen);
    }
    public void AbrirTutorialActivity() {
        Intent tutorial = new Intent(this, TutorialActivity.class);
        startActivity(tutorial);
    }



    private void OpenThirdActivity(String email, String password) {


        if (email.matches("") || password.matches("")){
            emptyFieldDialog();
        } else {
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {



                FirebaseUser user = mAuth.getCurrentUser();
                userId = user.getUid();
                Toast.makeText(getApplicationContext(), R.string.login_succesful, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
        });
            auth.signInWithEmailAndPassword(email,password).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    invalidFieldDialog();
                }
            });
    }
}


    private void emptyFieldDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage(R.string.fill_fields);
        dialog.setTitle(R.string.error);
        dialog.setNeutralButton(R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Toast.makeText(getApplicationContext(),R.string.error,Toast.LENGTH_LONG).show();
                    }
                });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

    private void invalidFieldDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage(R.string.invalid_email_password);
        dialog.setTitle(R.string.error);
        dialog.setNeutralButton(R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Toast.makeText(getApplicationContext(),R.string.error,Toast.LENGTH_LONG).show();
                    }
                });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }
}

