package com.br.systock.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.br.systock.R;
import com.br.systock.model.Address;
import com.br.systock.model.User;
import com.br.systock.model.Util;
import com.br.systock.model.ZipCodeListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class ZipCodeActivity extends AppCompatActivity {

    private EditText etZipCode;
    private Util util;
    private EditText etstreet;
    private EditText etcity;
    private EditText etnumber;
    private EditText etneighbor;
    private EditText etzipcode;
    private AwesomeValidation awesomeValidation;
    private Button finishbutton;
    private User userPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zip_code);

        Intent intent = getIntent();
        Gson gson = new Gson();
        userPrincipal = gson.fromJson(intent.getStringExtra("user"), User.class);

        initializeComponents();
        eventButton();

        etZipCode = (EditText) findViewById(R.id.et_zip_code);
        etZipCode.addTextChangedListener( new ZipCodeListener(this) );
        Spinner spStates = (Spinner) findViewById(R.id.sp_state);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this,
                        R.array.states,
                        R.layout.white_text_spinner);
        spStates.setAdapter(adapter);

        util = new Util(this,
                R.id.et_street,
                R.id.et_neighbor,
                R.id.et_city,
                R.id.sp_state,
                R.id.et_number);


        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        addValidationToViews();



    }

    public void addValidationToViews() {
        String regexZipCode = ".{8,}";
        awesomeValidation.addValidation(this,R.id.et_zip_code, regexZipCode, R.string.zipcodeerror );
    }


    public void initializeComponents() {
        etstreet = findViewById(R.id.et_street);
        etcity = findViewById(R.id.et_city);
        etneighbor = findViewById(R.id.et_neighbor);
        etzipcode = findViewById(R.id.et_zip_code);
        etnumber = findViewById(R.id.et_number);
        finishbutton = findViewById(R.id.finishbutton);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void eventButton() {
        finishbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String street = etstreet.getText().toString();
                String city = etcity.getText().toString();
                String neighbor = etneighbor.getText().toString();
                String number = etnumber.getText().toString();
                String zipcode = etzipcode.getText().toString();
                String name = ZipCodeActivity.this.userPrincipal.getName();
                String email = ZipCodeActivity.this.userPrincipal.getEmail();
                String password = ZipCodeActivity.this.userPrincipal.getPassword();
                String confirmpassword = ZipCodeActivity.this.userPrincipal.getConfirmpassword();
                String token = FirebaseMessagingService.getToken(getApplicationContext());

                if (street.matches("") || city.matches("") || number.matches("")){
                    invalidZipCodeAndNumberDialogue();
                } else {

                    User userPrincipal = new User(name, email, password, confirmpassword, street, neighbor, city, zipcode, number, token);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("User");
                    myRef.push().setValue(userPrincipal);

                    submitForm();
                }
            }
        });
    }

    public String getZipCode(){
        return etZipCode.getText().toString();
    }

    public String getUriRequest(){
        return "https://viacep.com.br/ws/"+getZipCode()+"/json/";
    }

    public void lockFields( boolean isToLock ){
        util.lockFields( isToLock );
    }
    public void setAddressFields( Address address){
        setField( R.id.et_street, address.getLogradouro() );
        setField( R.id.et_neighbor, address.getBairro() );
        setField( R.id.et_city, address.getLocalidade() );
        setSpinner( R.id.sp_state, R.array.states, address.getUf() );
        setField(R.id.et_number, address.getComplemento());
    }

    public void setField( int fieldId, String data ){
        ((EditText) findViewById( fieldId )).setText( data );
    }

    public void setSpinner( int fieldId, int arrayId, String uf ){
        Spinner spinner = (Spinner) findViewById( fieldId );
        String[] states = getResources().getStringArray(arrayId);

        for( int i = 0; i < states.length; i++ ){
            if( states[i].endsWith("("+uf+")") ){
                spinner.setSelection( i );
                break;
            }
        }
    }

    public void submitForm() {

        if (awesomeValidation.validate()) {
            Toast.makeText(this, R.string.register_succesful, Toast.LENGTH_LONG).show();
            Intent i = new Intent(ZipCodeActivity.this, LoginActivity.class);
            startActivity(i);



        }

    }

    private void invalidZipCodeAndNumberDialogue() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage(R.string.invalid_zipcode);
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
