package firstapp.example.lipsclone.Documents;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
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

    private TextView noDataText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_document);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        noDataText = findViewById(R.id.noDataText);

        findViewById(R.id.headerRow).setVisibility(View.GONE);

        String s_id = getIntent().getStringExtra("s_id");
        String sessionId = getIntent().getStringExtra("session");
        String college = "gdcol1";

        StudentDocument request = new StudentDocument("api", "student_document", s_id, sessionId, college);

        apiServices api = apiclient.getClient().create(apiServices.class);
        Call<StudentDocumentResponse> call = api.getDocuments(request);

        call.enqueue(new Callback<StudentDocumentResponse>() {
            @Override
            public void onResponse(Call<StudentDocumentResponse> call, Response<StudentDocumentResponse> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {

                        // Log raw payload for debugging
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        String jsonResponse = gson.toJson(response.body());
                        Log.d(TAG, "Full API Response:\n" + jsonResponse);

                        List<DocumentModel> documents = response.body().getResponse();

                        if (documents != null && !documents.isEmpty()) {
                            recyclerView.setVisibility(View.VISIBLE);
                            noDataText.setVisibility(View.GONE);

                            DocumentAdapter adapter = new DocumentAdapter(documents, Document.this, s_id);
                            recyclerView.setAdapter(adapter);
                        } else {
                            showNoDataMessage();
                        }
                    } else {
                        Log.w(TAG, "API returned error: " + response.code() + " - " + response.message());
                        showNoDataMessage();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Exception in onResponse: " + e.getMessage(), e);
                    showNoDataMessage();
                }
            }

            @Override
            public void onFailure(Call<StudentDocumentResponse> call, Throwable t) {
                Log.e(TAG, "Network failure or error: " + t.getMessage(), t);
                showNoDataMessage();
            }
        });

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void showNoDataMessage() {
        recyclerView.setVisibility(View.GONE);
        noDataText.setVisibility(View.VISIBLE);

        findViewById(R.id.headerRow).setVisibility(View.GONE);

        findViewById(R.id.divider).setVisibility(View.GONE);
    }
}
