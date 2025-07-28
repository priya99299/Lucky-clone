package firstapp.example.lipsclone.Downloads;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import firstapp.example.lipsclone.Documents.DownloadAndOpenPDF;
import firstapp.example.lipsclone.R;

import firstapp.example.lipsclone.api.Models.Downloads.DownloadItem;

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.ViewHolder> {

    private List<DownloadItem> documentList;
    private Context context;

    public DownloadAdapter(List<DownloadItem> documentList, Context context) {
        this.documentList = documentList;
        this.context = context;
    }

    @NonNull
    @Override
    public DownloadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadAdapter.ViewHolder holder, int position) {
        DownloadItem item = documentList.get(position);
        holder.docName.setText(item.getTitle());

        holder.fileUrl.setOnClickListener(v -> {
            String fileUrl = item.getFile();

            if (fileUrl != null && fileUrl.toLowerCase().endsWith(".pdf")) {
                String filename = item.getTitle().replaceAll("\\s+", "_") + ".pdf";
                DownloadAndOpenPDF.downloadAndOpen(context, fileUrl, filename);
            } else {
                // Optional: Show a message for non-PDFs
                Toast.makeText(context, "Not uploaded yet", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return documentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView docName;
        ImageButton fileUrl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            docName = itemView.findViewById(R.id.docName);
            fileUrl = itemView.findViewById(R.id.fileUrl);
        }
    }
}
