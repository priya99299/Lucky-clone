package firstapp.example.lipsclone.Attendence;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;

import firstapp.example.lipsclone.R;

public class Attendence_module extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_attendence_module);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        CardView  Monthly;

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        String s_id = getIntent().getStringExtra("s_id");
        String sessionId = getIntent().getStringExtra("session");
        final String TAG = "Attendance";
        Log.d(TAG, "Intent Extras Check --> s_id: " + s_id + ", sessionId: " + sessionId);

        //Monthly Module
        Monthly =findViewById(R.id.Monthly);
        Monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent monthly = new Intent(Attendence_module.this, Monthly_Attendence.class);
                monthly.putExtra("s_id", getIntent().getStringExtra("s_id"));
                monthly.putExtra("session", getIntent().getStringExtra("session"));
                startActivity(monthly);
            }
        });

    }
}