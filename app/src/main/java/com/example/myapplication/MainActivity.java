package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class   MainActivity extends AppCompatActivity {

    private EditText editTextURL;
    private WebView webView;
    private static final String PREFS_NAME = "ManoNustatymai";
    private static final String KEY_LAST_URL = "paskutinis_adresas";
    private long backPressTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        editTextURL = findViewById(R.id.editTextURL);
        Button buttonLoad = findViewById(R.id.buttonLoad);
        webView = findViewById(R.id.webView);

        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String savedAddress = preferences.getString(KEY_LAST_URL, null);
        if(savedAddress != null && !savedAddress.isEmpty()){
            editTextURL.setText(savedAddress);
            webView.loadUrl(savedAddress);
        } else {
            String primaryText = "<html><body style='font-size:18px; padding:16px;'>" + "Paspauskite du kartus ant įvedimo lango jeigu reikalingas https:\\www" + "</body></html>";
            webView.loadDataWithBaseURL(null, primaryText, "text/html", "UTF-8", null);
        }

        GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(@NonNull MotionEvent e) {
                editTextURL.setText("https://www");
                editTextURL.setSelection(editTextURL.getText().length());
                return true;
            }
        });

        editTextURL.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return false;
            }
        });

        buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = editTextURL.getText().toString().trim();

                if(!url.isEmpty()){
                    if(!url.startsWith("http://") && !url.startsWith("https://")){
                        url = "https://" + url;
                    }
                    webView.loadUrl(url);
                }
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getOnBackPressedDispatcher().addCallback(this, new androidx.activity.OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(webView.canGoBack()) {
                    webView.goBack();
                } else {
                    if (backPressTime + 2000 > System.currentTimeMillis()) {
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Kitą kartą paspaudus ATGAL bus uždarytas naršyklės langas", Toast.LENGTH_SHORT).show();
                    }
                    backPressTime = System.currentTimeMillis();
                }
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();

        String dabartinisAdresas = webView.getUrl();
        if (dabartinisAdresas == null || dabartinisAdresas.startsWith("data:")) {
            dabartinisAdresas = editTextURL.getText().toString().trim();
        }
        if(!dabartinisAdresas.isEmpty()) {
            SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(KEY_LAST_URL, dabartinisAdresas);
            editor.apply();
        }
    }

}