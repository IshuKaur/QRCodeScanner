package com.example.ishpreetkaur.qrcodescanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Button btnScan;
    private TextView tvName, tvProfession;

    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        qrScan = new IntentIntegrator(this);

        btnScan = (Button) findViewById(R.id.btn_scan);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvProfession = (TextView) findViewById(R.id.tv_profession);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrScan.initiateScan();

            }
        });
    }
    

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {

                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());

                    //   Toast.makeText(this, obj.getString("name") + " " + obj.getString("profession") , Toast.LENGTH_LONG).show();

                    tvName.setText(obj.getString("name") + "");
                    tvProfession.setText(obj.getString("profession") + "");

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();  // if it throws an exception you can see here..
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
