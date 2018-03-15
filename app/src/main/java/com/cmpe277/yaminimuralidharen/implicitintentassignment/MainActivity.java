package com.cmpe277.yaminimuralidharen.implicitintentassignment;

import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.net.Uri;
import android.telephony.PhoneNumberUtils;

import java.util.Locale;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
private EditText urlText, phoneText;
private Button webButton, callButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        urlText = findViewById(R.id.urledittext);
        phoneText = findViewById(R.id.phoneedittext);
        webButton = findViewById(R.id.urllaunchbutton);
        callButton = findViewById(R.id.callaunchbutton);
        webButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("url",urlText.getText().toString());
                Uri weburl;
                String url= urlText.getText().toString();
                if(url.contains("http")||url.contains("https"))
                 weburl = Uri.parse(url);
                else
                    weburl= Uri.parse("https://" + url);
                Intent webIntent= new Intent(Intent.ACTION_VIEW);
                webIntent.setData(weburl);
                startActivity(webIntent);
            }
        });

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String regExpr = "^[+]?[0-9]{10,13}$";
              if(!phoneText.getText().toString().matches(regExpr)){
                  Toast.makeText(MainActivity.this,"Phone number invalid",Toast.LENGTH_SHORT).show();
               //
              }
              else {


                String number;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    number= PhoneNumberUtils.formatNumber(phoneText.getText().toString(), Locale.getDefault().getCountry());
                    phoneText.setText(number);
                    Log.d("tag","formatted number" + number);
                } else {
                    number= PhoneNumberUtils.formatNumber(phoneText.getText().toString());
                    phoneText.setText(number);
                    Log.d("tag","formatted number" + number );
                }

                if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    Uri phone_number= Uri.parse("tel:" + number);
                    Intent callIntent= new Intent(Intent.ACTION_CALL);
                    callIntent.setData(phone_number);
                    startActivity(callIntent);
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Permission denied to make a call",Toast.LENGTH_SHORT).show();
                }
              }
           //     phoneText.setText("");
            }
        });
    }

    public void closeApp(View v)
    {
        MainActivity.this.finish();
    }
}
