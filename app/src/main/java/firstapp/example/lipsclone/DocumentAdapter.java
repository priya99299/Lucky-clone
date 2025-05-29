package firstapp.example.lipsclone.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import firstapp.example.lipsclone.R;
import firstapp.example.lipsclone.api.Models.DocumentModel;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.DocumentViewHolder> {

    private final List<DocumentModel> documentList;
    private final Context context;

    public DocumentAdapter(List<DocumentModel> documentList, Context context) {
        this.documentList = documentList;
        this.context = context;
    }

    @NonNull
    @Override
    public DocumentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_document, parent, false);
        return new DocumentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentViewHolder holder, int position) {
        DocumentModel document = documentList.get(position);
        holder.documentTitle.setText(document.getDocname());

        holder.showDocumentButton.setOnClickListener(v -> {
            String fileUrl = document.getFile();
            if (fileUrl != null && !fileUrl.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(fileUrl));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }

    public static class DocumentViewHolder extends RecyclerView.ViewHolder {
        TextView documentTitle;
        ImageButton showDocumentButton;

        public DocumentViewHolder(@NonNull View itemView) {
            super(itemView);
            documentTitle = itemView.findViewById(R.id.documentTitle);
            showDocumentButton = itemView.findViewById(R.id.showDocumentButton);
        }
    }
}
