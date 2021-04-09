package com.example.toucan_remake.dto;

import com.example.toucan_remake.validation.ValidPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class DtoUserReg {

    @NotBlank(message = "email is required")
    @Email(message = "email is invalid")
    private String email;

    @NotBlank(message = "password is required")
    //already written in PasswordConstraintValidator
    /*@Size(min = 8, max = 40, message
            = "password be between 8 and 40 characters")*/
    //@ValidPassword
    private String password;

    @NotBlank(message = "repeat password is required")
    //already written in PasswordConstraintValidator
    /*@Size(min = 8, max = 40, message
            = "password must be between 8 and 40 characters")*/
    private String rePassword;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRePassword() {
        return rePassword;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    @Override
    public String toString() {
        return "DtoUser{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", rePassword='" + rePassword + '\'' +
                '}';
    }
}
