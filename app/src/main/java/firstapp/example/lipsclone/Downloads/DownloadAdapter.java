package firstapp.example.lipsclone.Downloads;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DownloadItem item = documentList.get(position);
        holder.docName.setText(item.getTitle());

        holder.fileUrl.setOnClickListener(v -> {
            String fileUrl = item.getFile();
            if (fileUrl == null || fileUrl.trim().isEmpty()) {
                Toast.makeText(context, "File not uploaded yet", Toast.LENGTH_SHORT).show();
                return;
            }

            String lowerUrl = fileUrl.toLowerCase();
            String filename = item.getTitle().replaceAll("\\s+", "_");

            try {
                if (lowerUrl.endsWith(".pdf")) {
                    filename += ".pdf";
                    File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), filename);
                    openOrDownload(fileUrl, file, "application/pdf");

                } else if (lowerUrl.endsWith(".jpg") || lowerUrl.endsWith(".jpeg") || lowerUrl.endsWith(".png")) {
                    filename += lowerUrl.substring(lowerUrl.lastIndexOf("."));
                    File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), filename);
                    openOrDownload(fileUrl, file, "image/*");

                } else if (lowerUrl.endsWith(".doc") || lowerUrl.endsWith(".docx") ||
                        lowerUrl.endsWith(".xls") || lowerUrl.endsWith(".xlsx") ||
                        lowerUrl.endsWith(".ppt") || lowerUrl.endsWith(".pptx")) {

                    filename += lowerUrl.substring(lowerUrl.lastIndexOf("."));
                    File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), filename);
                    openDocument(fileUrl, file, lowerUrl);

                } else {
                    Toast.makeText(context, "Unsupported file type", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "Error opening file", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openOrDownload(String url, File file, String mimeType) {
        if (file.exists()) {
            openLocalFile(file, mimeType);
        } else {
            openUrlDirectly(url, mimeType);
            downloadFile(url, file.getName(), file.getParentFile().getAbsolutePath());
        }
    }

    private void openDocument(String url, File file, String lowerUrl) {
        String type = "";
        if (lowerUrl.endsWith(".doc") || lowerUrl.endsWith(".docx")) type = "doc";
        else if (lowerUrl.endsWith(".xls") || lowerUrl.endsWith(".xlsx")) type = "xls";
        else if (lowerUrl.endsWith(".ppt") || lowerUrl.endsWith(".pptx")) type = "ppt";

        String mime = "*/*";
        switch (type) {
            case "doc": mime = lowerUrl.endsWith(".doc") ? "application/msword" : "application/vnd.openxmlformats-officedocument.wordprocessingml.document"; break;
            case "xls": mime = lowerUrl.endsWith(".xls") ? "application/vnd.ms-excel" : "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"; break;
            case "ppt": mime = lowerUrl.endsWith(".ppt") ? "application/vnd.ms-powerpoint" : "application/vnd.openxmlformats-officedocument.presentationml.presentation"; break;
        }

        if (file.exists()) {
            openLocalFile(file, mime);
        } else {
            // fallback to Google Viewer
            String gView = "";
            if(type.equals("doc")) gView = "https://docs.google.com/viewer?url=" + url;
            else if(type.equals("xls")) gView = "https://docs.google.com/spreadsheets/viewer?url=" + url;
            else if(type.equals("ppt")) gView = "https://docs.google.com/presentation/viewer?url=" + url;

            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(gView));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch(Exception e) {
                Toast.makeText(context, "No app found. Install Word/Excel/PPT or use Google Viewer.", Toast.LENGTH_LONG).show();
            }

            downloadFile(url, file.getName(), file.getParentFile().getAbsolutePath());
        }
    }

    private void openLocalFile(File file, String mimeType) {
        try {
            Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, mimeType);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);

            // Only start if an app exists to handle it
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "No app found to open this file", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Failed to open file", Toast.LENGTH_SHORT).show();
        }
    }


    private void openUrlDirectly(String url, String mimeType) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(url), mimeType);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(Intent.createChooser(intent, "Open with"));
        } catch(Exception e) {
            Toast.makeText(context, "No app found to open this file", Toast.LENGTH_SHORT).show();
        }
    }

    private void downloadFile(String url, String filename, String directory) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDestinationInExternalFilesDir(context, directory, filename);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
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