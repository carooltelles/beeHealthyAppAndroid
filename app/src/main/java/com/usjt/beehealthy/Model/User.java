package com.usjt.beehealthy.Model;
import java.io.Serializable;
import java.util.Date;
public class User implements Serializable {

    public Long iduser;
    public String email;
    public String password;
    public String fullname;
    public String birthday;
    public String type;


    public User(Long iduser, String email, String password, String fullname, String birthday, String type) {
        this.iduser = iduser;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.birthday = birthday;
        this.type = type;
    }

    public User(){

    }

    public Long getIduser() {
        return iduser;
    }

    public void setIduser(Long iduser) {
        this.iduser = iduser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
