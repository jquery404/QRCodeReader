package com.jquery404.qrcodereader;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.jquery404.qrcodereader.helper.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseCompatActivity {

    @BindView(R.id.buttonScan)
    Button buttonScan;

    @BindView(R.id.results)
    TextView tvResults;

    private IntentIntegrator qrScan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        qrScan = new IntentIntegrator(this);
    }


    @OnClick(R.id.buttonScan)
    public void onClickScan() {
        qrScan.initiateScan();
    }

    @OnClick(R.id.buttonRegister)
    public void onClickRegister() {
        RegistrationActivity.start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                new ServerCall().execute(Utils.BASEURL + Utils.REGISTER + result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }


    public static void start(Context context) {
        Intent launchIntent = new Intent(context, MainActivity.class);
        launchIntent.putExtra("flag", context.getClass().getSimpleName());
        context.startActivity(launchIntent);
    }


    private class ServerCall extends AsyncTask<String, String, String> {

        ProgressDialog progressDialog;
        String jsonString;

        @Override
        protected String doInBackground(String... params) {
            try {
                jsonString = Utils.getJsonFromServer(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            try {
                JSONObject obj = new JSONObject(jsonString);
                tvResults.setText(obj.getString("full_name") + "\n"
                        + obj.getString("email") + "\n"
                        + obj.getString("career") + "\n"
                        + obj.getString("event_shift") + "\n"
                        + obj.getString("event_code") + "\n");
            } catch (JSONException e) {
                e.printStackTrace();
                tvResults.setText("No result found");
            }
            //tvResults.setText(jsonString);
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MainActivity.this,
                    "ProgressDialog",
                    "Wait for seconds");
        }

        @Override
        protected void onProgressUpdate(String... values) {
        }
    }

}
