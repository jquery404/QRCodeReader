package com.jquery404.qrcodereader;

import android.app.Application;

import com.jquery404.qrcodereader.database.DatabaseRepository;
import com.jquery404.qrcodereader.database.IDatabaseRepository;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by Faisal on 8/26/17.
 */

public class QRCodeReaderApplication extends Application {

    private IDatabaseRepository repository;


    @Override
    public void onCreate() {
        super.onCreate();
        initDatabase();
    }

    private void initDatabase() {
        FlowManager.init(new FlowConfig.Builder(this)
                .openDatabasesOnInit(true).build());

        repository = new DatabaseRepository();
    }

    public IDatabaseRepository getDatabase() {
        return repository;
    }

}
