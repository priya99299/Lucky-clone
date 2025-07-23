package firstapp.example.lipsclone.Attendence;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import firstapp.example.lipsclone.R;

public class Attendence_module extends AppCompatActivity {

    private static final String TAG = "Attendance";
    private Button markAttendanceBtn;
    private String s_id, sessionId, f_id, liveStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_attendence_module);

        // Handle status bar insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Toolbar back button
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Get intent extras
        s_id = getIntent().getStringExtra("s_id");
        sessionId = getIntent().getStringExtra("session");
        f_id = getIntent().getStringExtra("f_id");
        liveStatus = getIntent().getStringExtra("live_status");

        Log.d(TAG, "Intent Extras --> s_id: " + s_id + ", sessionId: " + sessionId + ", f_id: " + f_id + ", live_status: " + liveStatus);

        // Monthly Attendance CardView click
        CardView monthlyCard = findViewById(R.id.Monthly);
        monthlyCard.setOnClickListener(v -> {
            Intent monthly = new Intent(Attendence_module.this, Monthly_Attendence.class);
            monthly.putExtra("s_id", s_id);
            monthly.putExtra("session", sessionId);
            monthly.putExtra("f_id", f_id);
            startActivity(monthly);
        });

        // Lecture-wise Attendance CardView click
        CardView lecture = findViewById(R.id.Lecture);
        lecture.setOnClickListener(v -> {
            Intent lectureIntent = new Intent(Attendence_module.this, Lecture_wise_Attendence.class);
            lectureIntent.putExtra("s_id", s_id);
            lectureIntent.putExtra("session", sessionId);
            lectureIntent.putExtra("f_id", f_id);
            startActivity(lectureIntent);
        });

        // Mark Attendance button setup
        markAttendanceBtn = findViewById(R.id.markAttendanceBtn);
        updateButton(); // initial status set

        // Refresh button click (FloatingActionButton)
        FloatingActionButton refreshFab = findViewById(R.id.refreshFab);
        refreshFab.setOnClickListener(v -> {
            if (markAttendanceBtn.isEnabled()) {
                markAttendanceBtn.setEnabled(false);
                markAttendanceBtn.setAlpha(0.5f);
            } else {
                markAttendanceBtn.setEnabled(true);
                markAttendanceBtn.setAlpha(1f);
            }
        });

    }

    /**
     * Updates the markAttendanceBtn based on liveStatus value
     */
    private void updateButton() {
        Log.d(TAG, "Updating button. Current live_status: " + liveStatus);
        if ("1".equals(liveStatus)) {
            markAttendanceBtn.setEnabled(true);
            markAttendanceBtn.setAlpha(1f);
        } else {
            markAttendanceBtn.setEnabled(false);
            markAttendanceBtn.setAlpha(0.5f);
        }
    }
}
