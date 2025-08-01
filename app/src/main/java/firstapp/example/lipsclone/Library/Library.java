package firstapp.example.lipsclone.Library;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;

import java.util.List;

import firstapp.example.lipsclone.R;
import firstapp.example.lipsclone.api.Models.Library.IssuedBook;
import firstapp.example.lipsclone.api.Models.Library.LibraryResponse;
import firstapp.example.lipsclone.api.Models.Library.StudentLibraryRequest;
import firstapp.example.lipsclone.api.Network.apiServices;
import firstapp.example.lipsclone.api.Network.apiclient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Library extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView logText;
    private static final String TAG = "LibraryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        recyclerView = findViewById(R.id.issuedBooksRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        String s_id = getIntent().getStringExtra("s_id");
        String sessionId = getIntent().getStringExtra("session");
        String college = getIntent().getStringExtra("college");
        if (college == null) college = "gdcol1";

        log("s_id: " + s_id + ", session: " + sessionId + ", college: " + college);
        fetchLibraryData(s_id, sessionId, college);
    }

    private void fetchLibraryData(String s_id, String sessionId, String college) {
        StudentLibraryRequest request = new StudentLibraryRequest("api", "student_library", s_id, sessionId, college);
        log("Request: " + new Gson().toJson(request));

        apiServices api = apiclient.getClient().create(apiServices.class);
        api.getStudentLibrary(request).enqueue(new Callback<LibraryResponse>() {
            @Override
            public void onResponse(Call<LibraryResponse> call, Response<LibraryResponse> response) {
                try {
                    if (response.isSuccessful() && response.body() != null && response.body().response != null) {
                        List<IssuedBook> issuedBooks = response.body().response;
                        log("Received " + issuedBooks.size() + " issued books");
                        recyclerView.setAdapter(new IssuedBookAdapter(Library.this, issuedBooks));
                    } else {
                        log("Error: Unexpected response or empty body");
                    }
                } catch (Exception e) {
                    log("Exception during parsing: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<LibraryResponse> call, Throwable t) {
                log("API Failure: " + t.getMessage());
            }
        });
    }

    private void log(String msg) {
        Log.d(TAG, msg);
        if (logText != null) logText.append(msg + "\n\n");
    }
}
