package firstapp.example.lipsclone.events;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;

import firstapp.example.lipsclone.R;

public class Events extends AppCompatActivity {
    private static final String TAG = "EventsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_events);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Toolbar back button
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());



        // Setup WebView
        WebView webView = findViewById(R.id.webViewEvents);
        webView.setWebViewClient(new WebViewClient()); // open inside app
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        String s_id = getIntent().getStringExtra("s_id");
        if (s_id == null || s_id.isEmpty()) {
            Log.e(TAG, "s_id is null! Cannot load WebView.");
        } else {
            String url = "https://erp.luckyinstitute.org/api/mobileApp/moreMenu/index.php?sid=" + s_id;
            webView.loadUrl(url);
            Log.d(TAG, "Loading URL: " + url);

        }


    }
}
