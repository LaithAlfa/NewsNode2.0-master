package com.newsapp.newsapplication;



public class User {

    private byte[] bytes;
    private String id, Email, password, FIRSTNAME;
    private boolean active;

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }


    public User() {
    }

    public User(boolean active) {
        this.active = active;
    }

    public User(String id, String Email, String password, String FIRSTNAME) {
        this.id = id;
        this.Email = Email;
        this.password = password;
        this.FIRSTNAME = FIRSTNAME;
    }

    public User(String id, String Email, String password, byte[] bytes, String FIRSTNAME) {
        this.bytes = bytes;
        this.id = id;
        this.Email = Email;
        this.password = password;
        this.FIRSTNAME = FIRSTNAME;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return password;
    }

    public String getFIRSTNAME() {
        return FIRSTNAME;
    }

    public void setFIRSTNAME(String FIRSTNAME) {
        this.FIRSTNAME = FIRSTNAME;
    }

}
