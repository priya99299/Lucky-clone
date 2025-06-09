package firstapp.example.lipsclone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // UI Components
        ImageView ProfilePic;
        TextView name, Session;
        CardView profile, Stuattendence, document, notes, canteenn, feesDetails, Notice;
        Button btn;

        document = findViewById(R.id.document);
        name = findViewById(R.id.studentName);
        Session = findViewById(R.id.Section);
        ProfilePic = findViewById(R.id.studentImage);
        notes = findViewById(R.id.notes);
        canteenn = findViewById(R.id.canteen);
        Notice = findViewById(R.id.Notice);
        btn = findViewById(R.id.btm);
        profile = findViewById(R.id.stuProfile);
        Stuattendence = findViewById(R.id.attendence);
        feesDetails = findViewById(R.id.fees);

        // SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);

        // Check if user is logged in
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (!isLoggedIn) {
            Intent intent = new Intent(dashboard.this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // Fetch data from SharedPreferences
        String Studentdetails = sharedPreferences.getString("name", "No Name");
        String class_name = sharedPreferences.getString("class_name", "Unknown Class");
        String ImageUrl = sharedPreferences.getString("image_url", "");
        String fname = sharedPreferences.getString("fname", "");
        String admno = sharedPreferences.getString("admno", "");
        String mname = sharedPreferences.getString("mname", "");
        String address2 = sharedPreferences.getString("address2", "");
        String mobile1 = sharedPreferences.getString("mobile1", "");
        String studentId = sharedPreferences.getString("s_id", "");
        String sessionId = sharedPreferences.getString("session", "");
        String collegeId = sharedPreferences.getString("college", "");

        // Update UI
        name.setText(Studentdetails);
        Session.setText(class_name);

        if (ImageUrl != null && !ImageUrl.isEmpty()) {
            Glide.with(this).load(ImageUrl).into(ProfilePic);
        } else {
            ProfilePic.setImageResource(R.drawable.profile1);
        }

        // Logout button click: clear session and go to login
        btn.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Intent logout = new Intent(dashboard.this, MainActivity.class);
            startActivity(logout);
            finish();
        });

        // Document click - pass s_id and session from SharedPreferences
        document.setOnClickListener(v -> {
            Intent docIntent = new Intent(dashboard.this, Document.class);
            docIntent.putExtra("s_id", studentId);
            docIntent.putExtra("session", sessionId);
            startActivity(docIntent);
        });

        // Profile click - pass all details to Profile_module
        profile.setOnClickListener(v -> {
            Intent profileIntent = new Intent(dashboard.this, Profile_module.class);
            profileIntent.putExtra("name", Studentdetails);
            profileIntent.putExtra("class_name", class_name);
            profileIntent.putExtra("image_url", ImageUrl);
            profileIntent.putExtra("admno", admno);
            profileIntent.putExtra("fname", fname);
            profileIntent.putExtra("mname", mname);
            profileIntent.putExtra("mobile1", mobile1);
            profileIntent.putExtra("address2", address2);
            startActivity(profileIntent);
        });

        // Notes click
        notes.setOnClickListener(v -> {
            Intent notesIntent = new Intent(dashboard.this, Downloads.class);
            startActivity(notesIntent);
        });

        // Canteen click
        canteenn.setOnClickListener(v -> {
            Intent canteenIntent = new Intent(dashboard.this, Canteen.class);
            startActivity(canteenIntent);
        });

        // Fees details click
        feesDetails.setOnClickListener(v -> {
            Intent feesIntent = new Intent(dashboard.this, fees_Details.class);
            startActivity(feesIntent);
        });

        // Notice section click - pass s_id and session
        Notice.setOnClickListener(v -> {
            Intent noticeIntent = new Intent(dashboard.this, Notice_Section.class);
            noticeIntent.putExtra("s_id", studentId);
            noticeIntent.putExtra("session", sessionId);
            startActivity(noticeIntent);
        });
    }
}
