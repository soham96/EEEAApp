package eeea.eeeaapp;

/**
 * Created by Soham on 08-02-2017.
 */

public class user_page {

    private String Email, Image, Name, Reg;

    public user_page(){}

    public user_page(String email, String image, String name, String reg) {
        this.Email = email;
        this.Image = image;
        this.Name = name;
        this.Reg = reg;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        this.Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getReg() {
        return Reg;
    }

    public void setReg(String reg) {
        this.Reg = reg;
    }




}
