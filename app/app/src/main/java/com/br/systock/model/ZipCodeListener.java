package com.br.systock.model;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

import com.br.systock.view.ZipCodeActivity;

public class ZipCodeListener implements TextWatcher {

    private Context zip_code_listener_context;

    public ZipCodeListener( Context zip_code_listener_context){
        this.zip_code_listener_context = zip_code_listener_context;
    }

    @Override
    public void afterTextChanged(Editable editable) {
        String zipCode = editable.toString();

        if( zipCode.length() == 8 ){
            new AddressRequest( (ZipCodeActivity) zip_code_listener_context).execute();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
}
