package firstapp.example.lipsclone;

import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class Time_table extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_time_table);

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        // Toolbar setup
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        RecyclerView rvSchedule = findViewById(R.id.rvSchedule);
        rvSchedule.setLayoutManager(new LinearLayoutManager(this));

        // Create sample data manually since ScheduleUtils is not available
        List<Object> scheduleItems = new ArrayList<>();
        scheduleItems.add("Monday");
        scheduleItems.add(new ScheduleItem("DSA", "Dr. Smith", "Room 101", "09:00 - 10:00", 0xFFE6DCE9));
        scheduleItems.add(new ScheduleItem("COMPUTER NETWORKD", "Prof. Johnson", "Lab 201", "10:00 - 11:00", 0xFFD4EAD4));
        scheduleItems.add(new ScheduleItem("Chemistry", "Dr. Wilson", "Lab 102", "11:00 - 12:00", 0xFFE6DCE9));
        scheduleItems.add("Tuesday");
        scheduleItems.add(new ScheduleItem("CLIENT SERVER   ", "Prof. Johnson", "Lab 201", "09:00 - 10:00", 0xFFD4EAD4));
        scheduleItems.add(new ScheduleItem("Chemistry", "Dr. Wilson", "Lab 102", "10:00 - 11:00", 0xFFE6DCE9));
        scheduleItems.add("Wednesday");
        scheduleItems.add(new ScheduleItem("Chemistry", "Dr. Wilson", "Lab 102", "09:00 - 10:00", 0xFFE6DCE9));

        rvSchedule.setAdapter(new ScheduleAdapter(scheduleItems));
    }
}
