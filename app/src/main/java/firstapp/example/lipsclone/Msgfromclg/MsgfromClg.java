package firstapp.example.lipsclone.Msgfromclg;
import firstapp.example.lipsclone.R;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;

public class MsgfromClg extends AppCompatActivity {
    private static final String TAG = "Message";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_msgfrom_clg);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Intent data
        String s_id = getIntent().getStringExtra("s_id");
        String sessionId = getIntent().getStringExtra("session");
        String college = getIntent().getStringExtra("college");
        if (college == null) college = "gdcol1";

        Log.d(TAG, "Received s_id: " + s_id + ", sessionId: " + sessionId + ", college: " + college);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        LinearLayout infoCard,Director;
        Director = findViewById(R.id.Director);
//        infoCard = findViewById(R.id.Gerneral);


        Director.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent DirectorMsg= new Intent(MsgfromClg.this,DirectorMsg.class);
                DirectorMsg.putExtra("s_id", s_id);
                DirectorMsg.putExtra("session", sessionId);
                startActivity(DirectorMsg);
            }
        });

//        infoCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent GernalMsg= new Intent(MsgfromClg.this, GenralMSG.class);
//                GernalMsg.putExtra("s_id", s_id);
//                GernalMsg.putExtra("session", sessionId);
//                startActivity(GernalMsg);
//            }
//        });



    }
}