package firstapp.example.lipsclone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import firstapp.example.lipsclone.api.Models.AppConfigResponse;
import firstapp.example.lipsclone.api.Models.DocumentModel;
import firstapp.example.lipsclone.api.Models.DocumentResponse;
import firstapp.example.lipsclone.api.Models.StudentDocumentRequest;
import firstapp.example.lipsclone.api.Models.StudentVerifyRequest;
import firstapp.example.lipsclone.api.apiServices;
import firstapp.example.lipsclone.api.apiclient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Document extends AppCompatActivity {

    TextView documentTitle;
    ImageButton showDocumentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);

        documentTitle = findViewById(R.id.documentTitle);
        showDocumentButton = findViewById(R.id.showDocumentButton);

        // Get data from intent

        String docname = getIntent().getStringExtra("docname");
        String file = getIntent().getStringExtra("file");

//        if ( docname == null || file == null) {
//            Toast.makeText(this, "Missing document info", Toast.LENGTH_SHORT).show();
//            finish();
//            return;
//        }

//        // Display the document name
//        documentTitle.setText(docname);
//
//        // Open the document file when button is clicked
//        showDocumentButton.setOnClickListener(v -> {
//            try {
//                String finalFile = file;
//                if (!finalFile.startsWith("http")) {
//                    finalFile = "https://lips-root.s3.ap-south-1.amazonaws.com/assets/document/LIPS/97279.pdf" + finalFile;
//                }
//
//                Uri documentUri = Uri.parse(finalFile);
//                Intent intent = new Intent(Intent.ACTION_VIEW, documentUri);
//                startActivity(intent);
//            } catch (Exception e) {
//                Toast.makeText(this, "Cannot open document", Toast.LENGTH_SHORT).show();
//            }
//        });

    }
}


