package com.br.systock.view;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.br.systock.R;
import com.br.systock.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.lang.reflect.Member;
import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity {
    private TextView login;
    private Button mbregister;
    private EditText etname;
    private EditText etemail;
    private EditText etpassword;
    private EditText etconfirmpassword;
    private AwesomeValidation validationtoregister;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private static final String User = "User";
    private static final String TAG = "RegisterActivity";
    private com.br.systock.model.User user;
    private Member member;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializeComponents();
        eventButton();
        logintext();
        validationtoregister = new AwesomeValidation(ValidationStyle.BASIC);
        ValidationToRegister();
        member = new Member() {
            @NonNull
            @Override
            public Class<?> getDeclaringClass() {
                return null;
            }

            @NonNull
            @Override
            public String getName() {
                member.getName().trim();
                return null;
            }

            @Override
            public int getModifiers() {
                return 0;
            }

            @Override
            public boolean isSynthetic() {
                return false;
            }
        };
    }

    private void ValidationToRegister() {
        validationtoregister.addValidation(this, R.id.editTextTextPersonName2, RegexTemplate.NOT_EMPTY, R.string.nameerror);
        validationtoregister.addValidation(this, R.id.editTextTextEmailAddress, Patterns.EMAIL_ADDRESS, R.string.emailerror);


        String regexPassword = ".{8,}";
        validationtoregister.addValidation(this, R.id.editTextTextPassword, regexPassword, R.string.passworderror);
        Pattern patternPassword = null;
        validationtoregister.addValidation(this, R.id.editTextTextPassword2, patternPassword, R.string.passworderror);

    }

    public void initializeComponents() {
        mbregister = findViewById(R.id.registerbutton);
        login = findViewById(R.id.textView11);
        etname = findViewById(R.id.editTextTextPersonName2);
        etemail = findViewById(R.id.editTextTextEmailAddress);
        etpassword = findViewById(R.id.editTextTextPassword);
        etconfirmpassword = findViewById(R.id.editTextTextPassword2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(User);
    }

    private void logintext() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(myIntent);
            }
        });
    }

    public void eventButton() {
        mbregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = etname.getText().toString();
                String email = etemail.getText().toString();
                String password = etpassword.getText().toString();
                String confirmpassword = etconfirmpassword.getText().toString();
                submitForm(email, password);
                String token = FirebaseMessagingService.getToken(getApplicationContext());


                user = new User(name, email, password, confirmpassword,"","","","","",token);

            }
        });

    }

    public void submitForm (String email, String password){
        if (validationtoregister.validate())
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful())
                            if (etpassword.getText().toString().equals(etconfirmpassword.getText().toString())) {
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                              } else {
                                Toast.makeText(RegisterActivity.this, R.string.reg_error,
                                        Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(getIntent());
                            }
                    });
    }

    public void updateUI (FirebaseUser currentUser){

            String keyId = mDatabase.push().getKey();
            mDatabase.child(keyId).setValue(user);
            Gson gson = new Gson();
            Intent Details = new Intent(RegisterActivity.this, ZipCodeActivity.class);
            Details.putExtra("user", gson.toJson(user));
            startActivity(Details);
        }
}


