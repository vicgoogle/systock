package com.br.systock.model;

public class Notifications {


    private String Title;
    private String Body;

    public Notifications(String Title, String Body){

        this.Title = Title;
        this.Body = Body;

    }

    public Notifications(){

    }

    public String getTitle() {
        return this.Title;
    }


    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getBody() {
        return this.Body;
    }


    public void setBody(String Body) {
        this.Body = Body;
    }



}
