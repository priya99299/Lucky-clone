package firstapp.example.lipsclone.Downloads;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

import firstapp.example.lipsclone.R;
import firstapp.example.lipsclone.api.Models.Downloads.DownloadItem;
import firstapp.example.lipsclone.api.Models.Downloads.StudentDownloadRequest;
import firstapp.example.lipsclone.api.Models.Downloads.StudentDownloadResponse;
import firstapp.example.lipsclone.api.Network.apiServices;
import firstapp.example.lipsclone.api.Network.apiclient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Downloads extends AppCompatActivity {

    private static final String TAG = "DownloadAPI";
    private RecyclerView downloadRecyclerView;
    private TextView noFileText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloads);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        // Toolbar Back Button
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // RecyclerView init
        downloadRecyclerView = findViewById(R.id.downloadRecyclerView);
        downloadRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // No File TextView
        noFileText = findViewById(R.id.noFileText);

        // Get data from Intent
        String s_id = getIntent().getStringExtra("s_id");
        String sessionId = getIntent().getStringExtra("session");
        String class_id = "18"; //  You can make this dynamic if needed
        String college = "gdcol1";

        Log.d(TAG, "Received s_id: " + s_id + ", sessionId: " + sessionId);

        fetchDownloads(s_id, class_id, sessionId, college);
    }

    private void fetchDownloads(String s_id, String class_id, String sessionId, String college) {
        StudentDownloadRequest request = new StudentDownloadRequest(
                "api", "student_download", s_id, class_id, sessionId, college
        );

        Log.d(TAG, "Request Payload: " + new com.google.gson.Gson().toJson(request));

        apiServices api = apiclient.getClient().create(apiServices.class);
        Call<StudentDownloadResponse> call = api.getStudentDownloads(request);

        call.enqueue(new Callback<StudentDownloadResponse>() {
            @Override
            public void onResponse(Call<StudentDownloadResponse> call, Response<StudentDownloadResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<DownloadItem> downloads = response.body().getResponse();

                    Log.d(TAG, "API Response: " + new com.google.gson.Gson().toJson(response.body()));

                    if (downloads != null && !downloads.isEmpty()) {
                        DownloadAdapter adapter = new DownloadAdapter(downloads, Downloads.this);
                        downloadRecyclerView.setAdapter(adapter);
                        noFileText.setVisibility(View.GONE);
                    } else {
                        noFileText.setVisibility(View.VISIBLE);
                    }

                    Log.d(TAG, "Downloads fetched: " + downloads.size());
                } else {
                    noFileText.setVisibility(View.VISIBLE);
                    Log.e(TAG, "Response unsuccessful or empty body");
                    try {
                        Log.e(TAG, "Error body: " + response.errorBody().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<StudentDownloadResponse> call, Throwable t) {
                noFileText.setVisibility(View.VISIBLE);
                Log.e(TAG, "API Failure: " + t.getMessage(), t);
            }
        });
    }
}