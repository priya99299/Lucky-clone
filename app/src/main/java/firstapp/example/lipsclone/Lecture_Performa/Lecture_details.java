package firstapp.example.lipsclone.Lecture_Performa;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.TextView;

import firstapp.example.lipsclone.R;

public class Lecture_details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lecture_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView subject = findViewById(R.id.subjectValue);
        TextView faculty = findViewById(R.id.facultyValue);
      /*  TextView duration = findViewById(R.id.totalLectureValue);*/

        subject.setText(getIntent().getStringExtra("subject"));
        faculty.setText(getIntent().getStringExtra("faculty"));
//        duration.setText(getIntent().getStringExtra("duration"));
    }
}