package firstapp.example.lipsclone.complaint;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import firstapp.example.lipsclone.R;
import firstapp.example.lipsclone.api.Models.complaint.Complaint;
import firstapp.example.lipsclone.api.Models.complaint.ComplaintRequest;
import firstapp.example.lipsclone.api.Models.complaint.ComplaintResponse;
import firstapp.example.lipsclone.api.Network.apiServices;
import firstapp.example.lipsclone.api.Network.apiclient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackComplaintActivity extends AppCompatActivity {

    private static final String TAG = "ComplaintLog11";

    ListView complaintListView;
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_complaint);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        complaintListView = findViewById(R.id.complaintListView);

        fetchComplaints();
    }

    private void fetchComplaints() {
        String s_id,sessionId,f_id,college,semester;
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

        ComplaintRequest request = new ComplaintRequest(s_id, sessionId, college);
        Log.d(TAG, "Complaint Fetch - API Request:\n" + gson.toJson(request));

        apiServices api = apiclient.getClient().create(apiServices.class);
        api.getComplaints(request).enqueue(new Callback<ComplaintResponse>() {
            @Override
            public void onResponse(Call<ComplaintResponse> call, Response<ComplaintResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Complaint> complaintList = response.body().getResponse();
                    ComplaintAdapter adapter = new ComplaintAdapter(TrackComplaintActivity.this, complaintList);
                    complaintListView.setAdapter(adapter);
                    Log.d(TAG, "Complaint Fetch - API Response:\n" + gson.toJson(response.body()));
                } else {
                    Toast.makeText(TrackComplaintActivity.this, "No complaints found", Toast.LENGTH_SHORT).show();
                    try {
                        if (response.errorBody() != null) {
                            Log.e(TAG, "Complaint Fetch - Error Body:\n" + response.errorBody().string());
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Complaint Fetch - Error parsing body", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ComplaintResponse> call, Throwable t) {
                Log.e(TAG, "Complaint Fetch - API Failure: " + t.getMessage(), t);
            }
        });
    }
}
