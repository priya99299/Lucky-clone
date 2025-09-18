package firstapp.example.lipsclone.complaint;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import firstapp.example.lipsclone.R;
import firstapp.example.lipsclone.api.Models.complaint.ComplaintRequest;
import firstapp.example.lipsclone.api.Models.complaint.ComplaintResponse;
import firstapp.example.lipsclone.api.Models.complaint.ComplaintSubmitRequest;
import firstapp.example.lipsclone.api.Models.complaint.ComplaintSubmitResponse;
import firstapp.example.lipsclone.api.Network.apiServices;
import firstapp.example.lipsclone.api.Network.apiclient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComplaintSection extends AppCompatActivity {

    private static final String TAG = "ComplaintLog";

    AutoCompleteTextView categoryDropdown;
    TextInputEditText subjectEditText, descriptionEditText;
    MaterialButton submitButton, viewComplaintsButton;

    String s_id, sessionId, f_id, college, semester;
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_section);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Intent Data
        s_id = getIntent().getStringExtra("s_id");
        sessionId = getIntent().getStringExtra("session");
        f_id = getIntent().getStringExtra("f_id");
        college = getIntent().getStringExtra("college");
        semester = getIntent().getStringExtra("sem");

        if (college == null) college = "gdcol1";
        if (semester != null) {
            getSharedPreferences("MyPrefs", MODE_PRIVATE).edit().putString("sem", semester).apply();
        }

        Log.d(TAG, "Intent Data: " +
                "\ns_id: " + s_id +
                "\nsession: " + sessionId +
                "\nf_id: " + f_id +
                "\ncollege: " + college +
                "\nsemester: " + semester);

        categoryDropdown = findViewById(R.id.categoryDropdown);
        subjectEditText = findViewById(R.id.subjectEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        submitButton = findViewById(R.id.submitButton);
        viewComplaintsButton = findViewById(R.id.viewComplaintsButton);

        setupCategoryDropdown();

        submitButton.setOnClickListener(v -> submitComplaint());

        viewComplaintsButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, TrackComplaintActivity.class);
            intent.putExtra("s_id", s_id);
            intent.putExtra("session", sessionId);
            intent.putExtra("college", college);
            startActivity(intent);
        });
    }

    private void setupCategoryDropdown() {
        String[] categories = {
                "ADMISSION", "COLLEGE FACILITIES", "FOOD / CANTEEN",
                "HOSTEL", "LECTURE(S) AND CLASS ROOM(S)", "MOBILE APPLICATION",
                "RESULT DELAY ", "OTHER", "HOSTEL"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categories);
        categoryDropdown.setAdapter(adapter);
        categoryDropdown.setInputType(InputType.TYPE_NULL);
        categoryDropdown.setKeyListener(null);
        categoryDropdown.setOnClickListener(v -> categoryDropdown.showDropDown());
    }

    private void submitComplaint() {
        String category = categoryDropdown.getText().toString().trim();
        String subject = subjectEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        semester = getSharedPreferences("MyPrefs", MODE_PRIVATE).getString("sem", "");

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

        // Use the category as title
        ComplaintSubmitRequest request = new ComplaintSubmitRequest(
                s_id,
                sessionId,
                college,
                subject, // Title of complaint
                description,
                ""       // If no image, leave as empty string
        );

        Log.d(TAG, "Complaint Submit Request:\n" + gson.toJson(request));

        apiServices api = apiclient.getClient().create(apiServices.class);
        api.submitComplaint(request).enqueue(new Callback<ComplaintSubmitResponse>() {
            @Override
            public void onResponse(Call<ComplaintSubmitResponse> call, Response<ComplaintSubmitResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ComplaintSubmitResponse resp = response.body();
                    Log.d(TAG, "Complaint Submit Response:\n" + gson.toJson(resp));
                    Toast.makeText(ComplaintSection.this, resp.getMessage(), Toast.LENGTH_SHORT).show();

                    // Clear fields
                    categoryDropdown.setText("");
                    subjectEditText.setText("");
                    descriptionEditText.setText("");
                } else {
                    Log.e(TAG, "Complaint Submit Error: " + response.code());
                    Toast.makeText(ComplaintSection.this, "Submission failed. Try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ComplaintSubmitResponse> call, Throwable t) {
                Log.e(TAG, "Complaint Submit Failed: " + t.getMessage(), t);
//                Toast.makeText(ComplaintSection.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
