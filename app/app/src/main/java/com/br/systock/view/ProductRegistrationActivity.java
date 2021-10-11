package com.br.systock.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.br.systock.R;
import com.br.systock.model.Products;
import com.br.systock.model.Type;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductRegistrationActivity extends AppCompatActivity {

    Spinner spinner;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    ValueEventListener listener;
    ArrayAdapter<String> adapter;
    ArrayList<String> typeList;
    Button confirm;
    EditText category;
    EditText product;
    EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_registration);
        spinner = (Spinner) findViewById(R.id.mySpinner);

        databaseReference = FirebaseDatabase.getInstance().getReference("Type");
        databaseReference1 = FirebaseDatabase.getInstance().getReference("Products");

        typeList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(ProductRegistrationActivity.this,
                R.layout.white_text_spinner,typeList);

        adapter.setDropDownViewResource(R.layout.spinner_list_layout);

        spinner.setAdapter(adapter);

        retrieveData();

        category = (EditText) findViewById(R.id.editText3);
        product = (EditText) findViewById(R.id.editText1);
        description = (EditText) findViewById(R.id.editText2);


        confirm = (Button) findViewById(R.id.button2);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Products products = new Products(product.getText().toString(), category.getText().toString(), spinner.getSelectedItem().toString(), description.getText().toString());
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Produtos");
                    myRef.push().setValue(products);
                }

        });
    }

    public void retrieveData() {

        listener = databaseReference.addValueEventListener((new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot item:dataSnapshot.getChildren()) {

                    Type type = item.getValue(Type.class);

                    String typeshow = type.getdescricao() + " (" + type.getStatus() + ")";

                    typeList.add(typeshow);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }));
    }
}