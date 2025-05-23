package firstapp.example.lipsclone;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import firstapp.example.lipsclone.api.Models.DocumentModel;
import firstapp.example.lipsclone.api.Models.DocumentResponse;
import firstapp.example.lipsclone.api.Models.StudentDocumentRequest;
import firstapp.example.lipsclone.api.apiServices;
import firstapp.example.lipsclone.api.apiclient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Document extends AppCompatActivity {
    RecyclerView recyclerView;
    DocumentAdapter adapter;
    List<DocumentModel> documentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new DocumentAdapter(this, documentList);
        recyclerView.setAdapter(adapter);

        // Receive student details from intent
        String s_id = getIntent().getStringExtra("s_id");
        String session = getIntent().getStringExtra("session");
        String college = getIntent().getStringExtra("college");

        if (s_id == null || session == null || college == null) {
            Toast.makeText(this, "Missing student information", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        fetchDocuments(s_id, session, college);
    }

    private void fetchDocuments(String s_id, String session, String college) {
        Log.d("Document", "Fetching documents for s_id: " + s_id + ", session: " + session + ", college: " + college);

        apiServices api = apiclient.getClient().create(apiServices.class);
        StudentDocumentRequest request = new StudentDocumentRequest(s_id, session, college);

        Call<DocumentResponse> call = api.getDocuments(request);
        call.enqueue(new Callback<DocumentResponse>() {
            @Override
            public void onResponse(Call<DocumentResponse> call, Response<DocumentResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().success) {
                    Log.d("Document", "Documents fetched: " + response.body().response.size());
                    documentList.clear();
                    documentList.addAll(response.body().response);
                    adapter.notifyDataSetChanged();
                } else {
                    Log.d("Document", "Failed to fetch documents or empty response");
                    Toast.makeText(Document.this, "No documents or error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DocumentResponse> call, Throwable t) {
                Log.e("Document", "API failure: " + t.getMessage());
                Toast.makeText(Document.this, "Failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}

