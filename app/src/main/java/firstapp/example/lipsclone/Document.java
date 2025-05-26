package firstapp.example.lipsclone;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import firstapp.example.lipsclone.api.Models.DocumentModel;
import firstapp.example.lipsclone.api.Models.StudentDocumentResponse;
import firstapp.example.lipsclone.api.apiServices;
import firstapp.example.lipsclone.api.apiclient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Document extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);

        listView = findViewById(R.id.documentsListView);

        apiServices api = apiclient.getClient().create(apiServices.class);

        Call<StudentDocumentResponse> call = api.getDocuments(
                "api",
                "student_document",
                "5552",
                "2024_25",
                "gdcol1"
        );


        call.enqueue(new Callback<StudentDocumentResponse>() {
            @Override
            public void onResponse(Call<StudentDocumentResponse> call, Response<StudentDocumentResponse> response) {
                if (!response.isSuccessful()) {
                    try {
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "null";
                        Log.e("API_ERROR", "Code: " + response.code() + ", Error Body: " + errorBody);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }

                try {
                    // Log raw JSON response as string before parsing
                    String rawJson = response.raw().toString();  // Not the best, use response.body() with converter disabled
                    Log.d("API_RESPONSE_RAW", rawJson);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                StudentDocumentResponse docResponse = response.body();
                if (docResponse != null && docResponse.isSuccess() && !docResponse.isError()) {
                    List<DocumentModel> documents = docResponse.getResponse();
                    if (documents != null && !documents.isEmpty()) {
                        DocumentsAdapter adapter = new DocumentsAdapter(Document.this, documents);
                        listView.setAdapter(adapter);
                    } else {
                        Log.e("API_ERROR", "Document list is empty");
                    }
                } else {
                    Log.e("API_ERROR", "API returned success=false or error=true");
                }
            }

            @Override
            public void onFailure(Call<StudentDocumentResponse> call, Throwable t) {
                Log.e("API_FAIL", "Error: " + t.getMessage());
            }
        });
    }
}
