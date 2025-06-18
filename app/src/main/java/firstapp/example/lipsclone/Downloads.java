package firstapp.example.lipsclone;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

import firstapp.example.lipsclone.adapters.DownloadAdapter;
import firstapp.example.lipsclone.api.Models.DownloadItem;
import firstapp.example.lipsclone.api.Models.StudentDownloadRequest;
import firstapp.example.lipsclone.api.Models.StudentDownloadResponse;
import firstapp.example.lipsclone.api.apiServices;
import firstapp.example.lipsclone.api.apiclient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Downloads extends AppCompatActivity {

    private static final String TAG = "DownloadAPI";
    private RecyclerView downloadRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloads);

        // Toolbar Back Button
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // RecyclerView init
        downloadRecyclerView = findViewById(R.id.downloadRecyclerView);
        downloadRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get data from Intent
        String s_id = getIntent().getStringExtra("s_id");
        String sessionId = getIntent().getStringExtra("session");
        String class_id = "18"; // 🔁 You can make this dynamic if needed
        String college = "gdcol1";

        Log.d(TAG, "Received s_id: " + s_id + ", sessionId: " + sessionId);

        fetchDownloads(s_id, class_id, sessionId, college);
    }

    private void fetchDownloads(String s_id, String class_id, String sessionId, String college) {
        StudentDownloadRequest request = new StudentDownloadRequest(
                "api", "student_download", s_id, class_id, sessionId, college
        );

        apiServices api = apiclient.getClient().create(apiServices.class);
        Call<StudentDownloadResponse> call = api.getStudentDownloads(request);

        call.enqueue(new Callback<StudentDownloadResponse>() {
            @Override
            public void onResponse(Call<StudentDownloadResponse> call, Response<StudentDownloadResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<DownloadItem> downloads = response.body().getResponse();

                    // Bind to adapter
                    DownloadAdapter adapter = new DownloadAdapter(downloads, Downloads.this);
                    downloadRecyclerView.setAdapter(adapter);
                } else {
                    Log.e(TAG, "Response failed or body is null");
                }
            }

            @Override
            public void onFailure(Call<StudentDownloadResponse> call, Throwable t) {
                Log.e(TAG, "API Error: " + t.getMessage(), t);
            }
        });
    }
}
