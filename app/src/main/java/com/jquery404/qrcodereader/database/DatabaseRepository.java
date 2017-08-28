package com.jquery404.qrcodereader.database;

import com.jquery404.qrcodereader.helper.MyForm;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Faisal on 8/26/17.
 */

public class DatabaseRepository implements IDatabaseRepository {
    @Override
    public Boolean addInfo(String name, String nickParent, String nickMarry, String dni, String email,
                           String phone, String shift, String year, String career, String qrcode) {
        StudentForm newStudent = new StudentForm();
        newStudent.setName(name);
        newStudent.setNicknameParent(nickParent);
        newStudent.setNickMarry(nickMarry);
        newStudent.setCareer(career);
        newStudent.setDni(dni);
        newStudent.setEmail(email);
        newStudent.setPhone(phone);
        newStudent.setQrcode(qrcode);
        newStudent.setShift(shift);
        newStudent.setYear(year);

        return newStudent.save();
    }

    @Override
    public List<MyForm> getInfo() {
        List<MyForm> results = new ArrayList<>();
        List<StudentForm> serviceDirLists = SQLite.select()
                .from(StudentForm.class)
                .queryList();

        for (StudentForm serviceDir : serviceDirLists) {
            MyForm myForm = new MyForm(serviceDir.getName(),
                    serviceDir.getNicknameParent(),
                    serviceDir.getNickMarry(),
                    serviceDir.getDni(),
                    serviceDir.getEmail(),
                    serviceDir.getPhone(),
                    serviceDir.getShift(),
                    serviceDir.getYear(),
                    serviceDir.getCareer(),
                    serviceDir.getQrcode());
            results.add(myForm);
        }
        return results;
    }

    @Override
    public void clearInfo() {
        Delete.table(StudentForm.class);
    }
}
