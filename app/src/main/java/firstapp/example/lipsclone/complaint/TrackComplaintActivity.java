package firstapp.example.lipsclone.complaint;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

import firstapp.example.lipsclone.R;

public class TrackComplaintActivity extends AppCompatActivity {

    ListView complaintListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_complaint);

        // Toolbar setup
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Initialize ListView
        complaintListView = findViewById(R.id.complaintListView);

        // Get complaints from intent
        ArrayList<String> complaints = getIntent().getStringArrayListExtra("complaints");
        if (complaints == null) {
            complaints = new ArrayList<>(); // Avoid null pointer
        }

        // Set custom ComplaintAdapter
        ComplaintAdapter adapter = new ComplaintAdapter(this, complaints);
        complaintListView.setAdapter(adapter);
    }
}
