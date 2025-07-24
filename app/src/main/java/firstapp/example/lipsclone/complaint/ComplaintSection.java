package firstapp.example.lipsclone.complaint;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import firstapp.example.lipsclone.R;

public class ComplaintSection extends AppCompatActivity {

    AutoCompleteTextView categoryDropdown;
    TextInputEditText subjectEditText, descriptionEditText;
    MaterialButton submitButton, viewComplaintsButton;

    ArrayList<String> complaintList = new ArrayList<>(); // Local list for example

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_complaint_section);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Initialize views
        categoryDropdown = findViewById(R.id.categoryDropdown);
        subjectEditText = findViewById(R.id.subjectEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        submitButton = findViewById(R.id.submitButton);
        viewComplaintsButton = findViewById(R.id.viewComplaintsButton);

        // Setup category dropdown
        setupCategoryDropdown();

        // Submit button click
        submitButton.setOnClickListener(v -> submitComplaint());

        // View complaints button click
        viewComplaintsButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, TrackComplaintActivity.class);
            intent.putStringArrayListExtra("complaints", complaintList);
            startActivity(intent);
        });
    }

    private void setupCategoryDropdown() {
        String[] categories = {
                "Admission",
                "College Facility",
                "Food / Canteen",
                "Hostel",
                "Lectures and Teachers",
                "Mobile App Issues",
                "Payment / Fees",
                "Transport",
                "Others"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                categories
        );
        categoryDropdown.setAdapter(adapter);

        // Disable typing, only allow selection
        categoryDropdown.setInputType(InputType.TYPE_NULL);
        categoryDropdown.setKeyListener(null);

        categoryDropdown.setOnClickListener(v -> categoryDropdown.showDropDown());
    }

    private void submitComplaint() {
        String category = categoryDropdown.getText().toString().trim();
        String subject = subjectEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();

        if (category.isEmpty()) {
            categoryDropdown.setError("Select a category");
            return;
        }
        if (subject.isEmpty()) {
            subjectEditText.setError("Enter subject");
            return;
        }
        if (description.isEmpty()) {
            descriptionEditText.setError("Enter description");
            return;
        }

        // Save complaint as simple string for example (you can use model class)
        String complaint = "Category: " + category + "\nSubject: " + subject + "\nDescription: " + description;
        complaintList.add(complaint);

        Toast.makeText(this, "Complaint submitted", Toast.LENGTH_SHORT).show();

        // Clear inputs
        categoryDropdown.setText("");
        subjectEditText.setText("");
        descriptionEditText.setText("");
    }
}
