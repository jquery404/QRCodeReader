package com.jquery404.qrcodereader.helper;

/**
 * Created by Faisal on 8/27/17.
 */

public class MyForm {
    private String name, prName, mrName, dni, email, phone, shift, year, career, qrcode;

    public MyForm(String name, String prName, String mrName, String dni, String email, String phone, String shift, String year, String career, String qrcode) {
        this.name = name;
        this.prName = prName;
        this.mrName = mrName;
        this.dni = dni;
        this.email = email;
        this.phone = phone;
        this.shift = shift;
        this.year = year;
        this.career = career;
        this.qrcode = qrcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrName() {
        return prName;
    }

    public void setPrName(String prName) {
        this.prName = prName;
    }

    public String getMrName() {
        return mrName;
    }

    public void setMrName(String mrName) {
        this.mrName = mrName;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }
}
