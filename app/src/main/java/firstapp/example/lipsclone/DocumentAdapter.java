package firstapp.example.lipsclone;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import firstapp.example.lipsclone.api.Models.DocumentModel;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.DocumentViewHolder> {

    private Context context;
    private List<DocumentModel> documentList;

    public DocumentAdapter(Context context, List<DocumentModel> documentList) {
        this.context = context;
        this.documentList = documentList;
    }

    @NonNull
    @Override
    public DocumentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false);
        return new DocumentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentViewHolder holder, int position) {
        DocumentModel document = documentList.get(position);

        holder.docName.setText(document.getDocname());
        holder.docStatus.setText("Status: " + document.getStatus());

        holder.viewDocumentButton.setOnClickListener(v -> {
            String fileUrl = document.getFile();
            if (fileUrl != null && !fileUrl.trim().isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(fileUrl), "application/pdf"); // or "image/*" if image
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                try {
                    context.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(context, "No app found to open this file", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "No file available", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return documentList != null ? documentList.size() : 0;
    }

    public static class DocumentViewHolder extends RecyclerView.ViewHolder {
        TextView docName, docStatus;
        ImageButton viewDocumentButton;

        public DocumentViewHolder(@NonNull View itemView) {
            super(itemView);
            docName = itemView.findViewById(R.id.docName);

            viewDocumentButton = itemView.findViewById(R.id.viewDocumentButton);
        }
    }
}
