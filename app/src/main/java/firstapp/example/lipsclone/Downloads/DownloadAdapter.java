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
    // Map to store download IDs and their corresponding File objects, for post-download processing
    private final Map<Long, File> downloadMap = new HashMap<>();

    public DownloadAdapter(List<DownloadItem> documentList, Context context) {
        this.documentList = documentList;
        this.context = context;

        // Register a BroadcastReceiver to listen for when downloads are complete.
        // This allows the app to automatically open a file once it's finished downloading.
        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context ctx, Intent intent) {
                // Get the ID of the completed download
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                // Check if this download ID is one we initiated and are tracking
                if (downloadMap.containsKey(id)) {
                    File file = downloadMap.get(id); // Retrieve the file associated with this download
                    if (file != null) {
                        openFile(file); // Open the downloaded file
                    }
                    downloadMap.remove(id); // Remove from tracking map as it's processed
                }
            }
        }, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE), Context.RECEIVER_NOT_EXPORTED);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        View view = LayoutInflater.from(context).inflate(R.layout.notes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DownloadItem item = documentList.get(position);
        holder.docName.setText(item.getTitle()); // Set the document title

        // Set an OnClickListener for the download/open button
        holder.fileUrl.setOnClickListener(v -> {
            String fileUrl = item.getFile(); // Get the URL of the file
            if (fileUrl == null || fileUrl.trim().isEmpty()) {
                Toast.makeText(context, "File not uploaded yet", Toast.LENGTH_SHORT).show();
                return;
            }

            String lowerUrl = fileUrl.toLowerCase();
            // Sanitize filename
            String filename = item.getTitle().replaceAll("[^a-zA-Z0-9.-]", "_");

            File file;
            String mimeType;

            try {
                if (lowerUrl.endsWith(".pdf")) {
                    filename += ".pdf";
                    file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), filename);
                    mimeType = "application/pdf";

                } else if (lowerUrl.endsWith(".jpg") || lowerUrl.endsWith(".jpeg") || lowerUrl.endsWith(".png")) {
                    filename += lowerUrl.substring(lowerUrl.lastIndexOf("."));
                    file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), filename);
                    mimeType = "image/*";

                } else if (lowerUrl.endsWith(".doc") || lowerUrl.endsWith(".docx") ||
                        lowerUrl.endsWith(".xls") || lowerUrl.endsWith(".xlsx") ||
                        lowerUrl.endsWith(".ppt") || lowerUrl.endsWith(".pptx")) {
                    filename += lowerUrl.substring(lowerUrl.lastIndexOf("."));
                    file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), filename);
                    mimeType = getMimeType(filename);

                } else {
                    Toast.makeText(context, "Unsupported file type", Toast.LENGTH_SHORT).show();
                    return;
                }

                // ---------- NEW LOGIC ----------
                if (file.exists() && file.length() > 0) {
                    // File exists → open immediately
                    openFile(file);
                } else {
                    // File doesn't exist → download & auto-open after completion
                    long downloadId = downloadFile(fileUrl, filename, getDirectory(file));
                    downloadMap.put(downloadId, file); // Track this download
                    Toast.makeText(context, "Downloading file...", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "Error processing file: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    /**
     * Handles opening an existing file or initiating a download if the file doesn't exist locally.
     *
     * @param url The URL of the file to download.
     * @param file The File object representing the local path.
     * @param mimeType The MIME type of the file. (Note: mimeType is currently not used in this method, but passed on)
     */
    private void handleFile(String url, File file, String mimeType) {
        if (file.exists() && file.length() > 0) {
            // If the file already exists and is not empty, open it directly
            openFile(file);
        } else {
            // If the file doesn't exist or is empty, start the download
            long downloadId = downloadFile(url, file.getName(), getDirectory(file));
            downloadMap.put(downloadId, file); // Track the download
            Toast.makeText(context, "Downloading file...", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Opens a local file using an appropriate intent.
     * This method is designed to work with various file types, including DOCX for MS Word.
     *
     * @param file The File object to be opened.
     */
    private void openFile(File file) {
        try {
            String mimeType = getMimeType(file.getName());
            Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, mimeType);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            // Wrap the intent in a chooser to show "Open with" dialog
            Intent chooser = Intent.createChooser(intent, "Open with");
            chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(chooser);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Failed to open file: " + e.getMessage() + ". Install a compatible app.", Toast.LENGTH_LONG).show();
        }
    }


    /**
     * Initiates a file download using Android's DownloadManager.
     *
     * @param url The URL of the file to download.
     * @param filename The desired filename for the downloaded file.
     * @param directory The standard Android directory (e.g., Environment.DIRECTORY_DOCUMENTS).
     * @return The ID of the initiated download.
     */
    private long downloadFile(String url, String filename, String directory) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        // Set the destination for the downloaded file within the app's external files directory
        request.setDestinationInExternalFilesDir(context, directory, filename);
        // Make the download visible in the notifications and show a completion notification
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        // Optionally, set title and description for the notification
        request.setTitle("Downloading " + filename);
        request.setDescription("Downloading file from " + Uri.parse(url).getHost());

        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        return manager.enqueue(request); // Enqueue the download request
    }

    /**
     * Determines the standard Android directory based on the file's path.
     *
     * @param file The File object.
     * @return A string representing the standard Android directory (e.g., Environment.DIRECTORY_DOWNLOADS).
     */
    private String getDirectory(File file) {
        String path = file.getParentFile().getAbsolutePath();
        if (path.contains(Environment.DIRECTORY_DOWNLOADS)) return Environment.DIRECTORY_DOWNLOADS;
        if (path.contains(Environment.DIRECTORY_PICTURES)) return Environment.DIRECTORY_PICTURES;
        // Default to DOCUMENTS if not pictures or general downloads
        return Environment.DIRECTORY_DOCUMENTS;
    }

    /**
     * Returns the MIME type for a given filename based on its extension.
     *
     * @param filename The name of the file.
     * @return The MIME type string.
     */
    private String getMimeType(String filename) {
        filename = filename.toLowerCase();
        if (filename.endsWith(".pdf")) return "application/pdf";
        if (filename.endsWith(".doc")) return "application/msword";
        if (filename.endsWith(".docx")) return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        if (filename.endsWith(".xls")) return "application/vnd.ms-excel";
        if (filename.endsWith(".xlsx")) return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        if (filename.endsWith(".ppt")) return "application/vnd.ms-powerpoint";
        if (filename.endsWith(".pptx")) return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
        if (filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".png"))
            return "image/*"; // Generic image MIME type
        return "*/*";  // Fallback for unknown types
    }

    @Override
    public int getItemCount() {
        return documentList.size(); // Return the total number of items in the list
    }

    /**
     * ViewHolder class to hold references to the views for each item in the RecyclerView.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView docName;
        ImageButton fileUrl; // Assuming fileUrl is the button to trigger download/open

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            docName = itemView.findViewById(R.id.docName);
            fileUrl = itemView.findViewById(R.id.fileUrl);
        }
    }
}