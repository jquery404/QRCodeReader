package com.jquery404.qrcodereader.database;

import com.jquery404.qrcodereader.helper.MyForm;

import java.util.List;

/**
 * Created by Faisal on 8/26/17.
 */

public interface IDatabaseRepository {

    Boolean addInfo(String name, String nickParent, String nickMarry, String dni, String email,
                    String phone, String shift, String year, String career, String qrcode);

    List<MyForm> getInfo();

    void clearInfo();
}
