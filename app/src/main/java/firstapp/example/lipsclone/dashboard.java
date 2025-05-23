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

import java.util.ArrayList;

import firstapp.example.lipsclone.api.Models.AppConfigResponse;
import firstapp.example.lipsclone.api.Models.DocumentModel;
import firstapp.example.lipsclone.api.Models.StudentVerifyRequest;
import firstapp.example.lipsclone.api.apiServices;
import firstapp.example.lipsclone.api.apiclient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        CardView profile, Stuattendence,document;
        Button btn;

        document=findViewById(R.id.document);
        name = findViewById(R.id.studentName);
        Session = findViewById(R.id.Section);
        ProfilePic = findViewById(R.id.studentImage);

        btn = findViewById(R.id.btm);
        profile = findViewById(R.id.stuProfile);
        Stuattendence = findViewById(R.id.attendence);

        // Dashboard Data from Intent
        String Studentdetails = getIntent().getStringExtra("name");
        String class_name = getIntent().getStringExtra("class_name");
        String ImageUrl = getIntent().getStringExtra("image_url");
        String fname=getIntent().getStringExtra("fname");
        String admno=getIntent().getStringExtra("admno");
        String   mname =getIntent().getStringExtra("mname");
        String Fname = getIntent().getStringExtra("fname");
        String address2=getIntent().getStringExtra("address2");
        String mobile1=getIntent().getStringExtra("mobile1");
        String studentId = getIntent().getStringExtra("s_id");
        String sessionId = getIntent().getStringExtra("session");
        String collegeId = getIntent().getStringExtra("college");

        ;  // Pass this from dashboard



        name.setText(Studentdetails);
        Session.setText(class_name);

        if (ImageUrl != null && !ImageUrl.isEmpty()) {
            Glide.with(this).load(ImageUrl).into(ProfilePic);
        } else {
            ProfilePic.setImageResource(R.drawable.profile1);
        }

        btn.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Intent logout = new Intent(dashboard.this, MainActivity.class);
            startActivity(logout);
            finish();
        });
        document.setOnClickListener(v -> {
            Intent intent = new Intent(dashboard.this, Document.class);
            intent.putExtra("s_id", studentId);
            intent.putExtra("session", sessionId);
            intent.putExtra("college", collegeId);
            startActivity(intent);
        });



        // Profile Card Click
        profile.setOnClickListener(v -> {
            // Pass already available data to Profile_module
            Intent students = new Intent(dashboard.this, Profile_module.class);

            students.putExtra("name", Studentdetails);           // Already received from getIntent()
            students.putExtra("class_name", class_name);
            students.putExtra("image_url", ImageUrl);

            // If these are available from previous API response, pass them too
            students.putExtra("admno", admno);
            students.putExtra("fname",fname);
            students.putExtra("mname", mname);
            students.putExtra("mobile1", mobile1);
            students.putExtra("address2", address2);


            startActivity(students);
        });

    }
}
