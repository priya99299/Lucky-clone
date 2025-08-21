package firstapp.example.lipsclone.Downloads;

import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

import firstapp.example.lipsclone.Documents.DownloadAndOpenPDF;
import firstapp.example.lipsclone.R;
import firstapp.example.lipsclone.api.Models.Downloads.DownloadItem;

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.ViewHolder> {

    private final List<DownloadItem> documentList;
    private final Context context;

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

            if (fileUrl == null || !fileUrl.toLowerCase().endsWith(".pdf")) {
                Toast.makeText(context, "Not uploaded yet", Toast.LENGTH_SHORT).show();
                return;
            }

            // unique filename (without spaces)
            String filename = item.getTitle().replaceAll("\\s+", "_") + ".pdf";
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), filename);

            if (file.exists()) {
                // Already downloaded → open local
                DownloadAndOpenPDF.openPDF(context, file);
            } else {
                // First time click → open directly + download in background
                DownloadAndOpenPDF.openDirectly(context, fileUrl);
                DownloadAndOpenPDF.downloadAndOpen(context, fileUrl, filename, "downloads");
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
