package com.br.systock.model;

import android.os.AsyncTask;

import com.br.systock.view.ZipCodeActivity;
import com.google.gson.Gson;

import java.lang.ref.WeakReference;

public class AddressRequest extends AsyncTask<Void, Void, Address> {
        private WeakReference<ZipCodeActivity> activity;

    public AddressRequest(ZipCodeActivity activity ){
            this.activity = new WeakReference<>( activity );
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.get().lockFields( true );
        }

        @Override
        protected Address doInBackground(Void... voids) {

            try{
                String jsonString = JsonRequest.request( activity.get().getUriRequest() );
                Gson gson = new Gson();

                return gson.fromJson(jsonString, Address.class);
            }
            catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Address address) {
            super.onPostExecute(address);

            if( activity.get() != null ){
                activity.get().lockFields( false );

                if( address != null ){
                    activity.get().setAddressFields(address);
                }
            }
        }
    }

