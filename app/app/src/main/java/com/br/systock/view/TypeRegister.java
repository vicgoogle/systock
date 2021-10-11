package com.br.systock.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.br.systock.R;
import com.br.systock.model.Type;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TypeRegister extends AppCompatActivity {

    private Button proceed;
    private EditText description;
    private Spinner status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_type);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this,
                R.array.status_active_inactive,
                R.layout.selected_spinner_item_layout
        );
        adapter.setDropDownViewResource(R.layout.spinner_list_layout);

        status = (Spinner) findViewById(R.id.sp_status);
        status.setAdapter(adapter);



        proceed = (Button) findViewById(R.id.proceed_bt);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description = (EditText) findViewById(R.id.et_description);
                status = (Spinner) findViewById(R.id.sp_status);

                if(description.getText().toString().matches("") || status.getSelectedItemPosition()==0) {
                    emptyFieldDialog();
                } else {
                    Type type = new Type(description.getText().toString(), status.getSelectedItem().toString());
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Type");
                    myRef.push().setValue(type);
                    confirmDialog(description,status);
                }
            }

        });

    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

    private void emptyFieldDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage(R.string.fill_fields);
        dialog.setTitle(R.string.error);
        dialog.setNeutralButton(R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                    }
                });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

    public void returnToThirdActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }



    private void confirmDialog(EditText desc, Spinner st) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(R.string.anotherproduct);
        dialog.setTitle(R.string.types);
        dialog.setNeutralButton(R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        desc.setText("");
                        st.setSelection(0);
                    }
                });
        dialog.setPositiveButton(R.string.no,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        returnToThirdActivity();
                        Toast.makeText(getApplicationContext(), R.string.registered, Toast.LENGTH_LONG).show();
                    }
                });

        AlertDialog confirmDialog = dialog.create();
        confirmDialog.show();

    }

}
