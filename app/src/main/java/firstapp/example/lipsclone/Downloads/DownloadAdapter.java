package firstapp.example.lipsclone.Downloads;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import firstapp.example.lipsclone.R;
import firstapp.example.lipsclone.api.Models.Downloads.DownloadItem;

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.ViewHolder> {

    private final List<DownloadItem> documentList;
    private final Context context;
    private final Map<Long, File> downloadMap = new HashMap<>();

    public DownloadAdapter(List<DownloadItem> documentList, Context context) {
        this.documentList = documentList;
        this.context = context;

        // Register receiver to open file after download completes
        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context ctx, Intent intent) {
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (downloadMap.containsKey(id)) {
                    File file = downloadMap.get(id);
                    openFile(file);
                    downloadMap.remove(id);
                }
            }
        }, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE), Context.RECEIVER_NOT_EXPORTED);
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
            File file;
            String mimeType;

            try {
                if (lowerUrl.endsWith(".pdf")) {
                    filename += ".pdf";
                    file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), filename);
                    mimeType = "application/pdf";
                    handleFile(fileUrl, file, mimeType);

                } else if (lowerUrl.endsWith(".jpg") || lowerUrl.endsWith(".jpeg") || lowerUrl.endsWith(".png")) {
                    filename += lowerUrl.substring(lowerUrl.lastIndexOf("."));
                    file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), filename);
                    mimeType = "image/*";
                    handleFile(fileUrl, file, mimeType);

                } else if (lowerUrl.endsWith(".doc") || lowerUrl.endsWith(".docx") ||
                        lowerUrl.endsWith(".xls") || lowerUrl.endsWith(".xlsx") ||
                        lowerUrl.endsWith(".ppt") || lowerUrl.endsWith(".pptx")) {

                    filename += lowerUrl.substring(lowerUrl.lastIndexOf("."));
                    file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), filename);
                    mimeType = getMimeType(filename);
                    handleFile(fileUrl, file, mimeType);

                } else {
                    Toast.makeText(context, "Unsupported file type", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "Error opening file", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleFile(String url, File file, String mimeType) {
        if (file.exists() && file.length() > 0) {
            openFile(file);
        } else {
            long downloadId = downloadFile(url, file.getName(), getDirectory(file));
            downloadMap.put(downloadId, file);
            Toast.makeText(context, "Downloading file...", Toast.LENGTH_SHORT).show();
        }
    }

    private void openFile(File file) {
        try {
            String mimeType = getMimeType(file.getName());
            Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, mimeType);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);

            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "No app installed to open this file type. Please install Microsoft Office, WPS, or Google Docs.", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Failed to open file. Make sure you have an app that can open this type.", Toast.LENGTH_LONG).show();
        }
    }


    private long downloadFile(String url, String filename, String directory) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDestinationInExternalFilesDir(context, directory, filename);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        return manager.enqueue(request);
    }

    private String getDirectory(File file) {
        String path = file.getParentFile().getAbsolutePath();
        if (path.contains(Environment.DIRECTORY_DOWNLOADS)) return Environment.DIRECTORY_DOWNLOADS;
        if (path.contains(Environment.DIRECTORY_PICTURES)) return Environment.DIRECTORY_PICTURES;
        return Environment.DIRECTORY_DOCUMENTS;
    }

    private String getMimeType(String filename) {
        filename = filename.toLowerCase();
        if (filename.endsWith(".pdf")) return "application/pdf";
        if (filename.endsWith(".doc")) return "application/msword";
        if (filename.endsWith(".docx"))
            return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        if (filename.endsWith(".xls")) return "application/vnd.ms-excel";
        if (filename.endsWith(".xlsx"))
            return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        if (filename.endsWith(".ppt")) return "application/vnd.ms-powerpoint";
        if (filename.endsWith(".pptx"))
            return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
        if (filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".png"))
            return "image/*";
        return "*/*";
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
