package com.jquery404.qrcodereader.database;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by Faisal on 8/26/17.
 */


@Table(database = AppDatabase.class)
public class StudentForm extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    int id;

    @Column
    String name;
    @Column
    String nicknameParent;
    @Column
    String nickMarry;
    @Column
    String dni;
    @Column
    String email;
    @Column
    String phone;
    @Column
    String shift;
    @Column
    String year;
    @Column
    String career;
    @Column
    String qrcode;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNicknameParent() {
        return nicknameParent;
    }

    public String getNickMarry() {
        return nickMarry;
    }

    public String getDni() {
        return dni;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getShift() {
        return shift;
    }

    public String getYear() {
        return year;
    }

    public String getCareer() {
        return career;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNicknameParent(String nicknameParent) {
        this.nicknameParent = nicknameParent;
    }

    public void setNickMarry(String nickMarry) {
        this.nickMarry = nickMarry;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }
}
