package com.jquery404.qrcodereader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jquery404.qrcodereader.adapter.StudentListAdapter;
import com.jquery404.qrcodereader.database.IDatabaseRepository;
import com.jquery404.qrcodereader.helper.MyForm;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Faisal on 8/27/17.
 */

public class ViewListActivity extends BaseCompatActivity {

    @BindView(R.id.recycler_playlist)
    RecyclerView recycler_playlist;

    IDatabaseRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdb);

        ButterKnife.bind(this);

        initViews();

    }

    public void initViews() {
        QRCodeReaderApplication app = (QRCodeReaderApplication) getApplication();
        repository = app.getDatabase();

        List<MyForm> songsList = repository.getInfo();

        if (songsList != null) {
            recycler_playlist.setLayoutManager(new LinearLayoutManager(this));
            RecyclerView.Adapter adapter = new StudentListAdapter(this, songsList);
            recycler_playlist.setAdapter(adapter);
            recycler_playlist.setNestedScrollingEnabled(false);
        }

    }


    public static void start(Context context) {
        Intent launchIntent = new Intent(context, ViewListActivity.class);
        launchIntent.putExtra("flag", context.getClass().getSimpleName());
        context.startActivity(launchIntent);
    }

}
