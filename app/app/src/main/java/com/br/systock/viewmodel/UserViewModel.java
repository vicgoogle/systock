package com.br.systock.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.br.systock.model.User;

public class UserViewModel extends ViewModel {
    private MutableLiveData<User> user;

    public LiveData<User> getUser() {
        if (user == null) {
            user = new MutableLiveData<User>();
            loadUser();
        }
        return user;
    }
    


    public void setUser(User userPrincipal){
        this.user.setValue(userPrincipal);
    }

    private void loadUser() {
        // Do an asynchronous operation to fetch users.
    }
}
