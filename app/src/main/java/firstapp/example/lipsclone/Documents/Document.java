package firstapp.example.lipsclone.Documents;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import firstapp.example.lipsclone.R;
import firstapp.example.lipsclone.api.Models.Downloads.DocumentModel;
import firstapp.example.lipsclone.api.Models.Documents.StudentDocument;
import firstapp.example.lipsclone.api.Models.Downloads.StudentDocumentResponse;
import firstapp.example.lipsclone.api.Network.apiServices;
import firstapp.example.lipsclone.api.Network.apiclient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Document extends AppCompatActivity {

    private static final String TAG = "DocumentActivity";

    private TextView studentIdText, session1;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add this to catch any uncaught exceptions anywhere in this thread (main UI thread)
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable e) {
                Log.e(TAG, "Uncaught exception: ", e);
            }

        });


        Log.d(TAG, "onCreate started");
        setContentView(R.layout.activity_document);

//        studentIdText = findViewById(R.id.apiResponseTextView);
//        session1 = findViewById(R.id.Session);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String s_id = getIntent().getStringExtra("s_id");
        String sessionId = getIntent().getStringExtra("session");
        String college = "gdcol1";

        Log.d(TAG, "Received s_id: " + s_id + ", sessionId: " + sessionId);

//        studentIdText.setText("Student ID: " + s_id);
//        session1.setText("Session: " + sessionId);

        StudentDocument request = new StudentDocument("api", "student_document", s_id, sessionId, college);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String requestJson = gson.toJson(request);
        Log.d(TAG, "Request JSON: " + requestJson);

        apiServices api = apiclient.getClient().create(apiServices.class);
        Call<StudentDocumentResponse> call = api.getDocuments(request);

        call.enqueue(new Callback<StudentDocumentResponse>() {
            @Override
            public void onResponse(Call<StudentDocumentResponse> call, Response<StudentDocumentResponse> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        String jsonResponse = gson.toJson(response.body());
                        Log.d(TAG, jsonResponse);

                        List<DocumentModel> documents = response.body().getResponse();
                        Log.d(TAG, "Documents count: " + documents.size());

                        for (DocumentModel doc : documents) {
                            Log.d(TAG, "DocName: " + doc.getDocname() + ", FileURL: " + doc.getFile());
                        }

                        DocumentAdapter adapter = new DocumentAdapter(documents, Document.this);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Log.e(TAG, "Request failed. Code: " + response.code());
                        if (response.errorBody() != null) {
                            Log.e(TAG, "Error body: " + response.errorBody().string());
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Exception in onResponse: " + e.getMessage(), e);
                }
            }

            @Override
            public void onFailure(Call<StudentDocumentResponse> call, Throwable t) {
                Log.e(TAG, "Network failure or error: " + t.getMessage(), t);
            }
        });


        Log.d(TAG, "onCreate finished");
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }
}
