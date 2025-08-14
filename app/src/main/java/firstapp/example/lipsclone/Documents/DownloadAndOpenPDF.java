package firstapp.example.lipsclone.Documents;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import androidx.core.content.FileProvider;

import java.io.File;

public class DownloadAndOpenPDF {

    // Old version for modules without s_id
    public static void downloadAndOpen(Context context, String fileUrl, String filename) {
        downloadAndOpen(context, fileUrl, filename, "common");
    }

    // New version for account-specific files
    public static long downloadAndOpen(Context context, String fileUrl, String filename, String s_id) {
        try {
            Log.d("DownloadAndOpenPDF", "fileUrl raw value: '" + fileUrl + "'");

            // Check for empty, null, or placeholder files
            if (fileUrl == null || fileUrl.trim().isEmpty() ||
                    fileUrl.equalsIgnoreCase("null") ||
                    fileUrl.contains("404.jpg") || fileUrl.contains("no-file")) {
                Toast.makeText(context, "Pending", Toast.LENGTH_SHORT).show();
                return -1;
            }

            String uniqueFilename = s_id + "_" + filename;
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), uniqueFilename);

            // If already exists, open directly
            if (file.exists()) {
                openPDF(context, file);
                return -1;
            }

            // Start download
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(fileUrl));
            request.setTitle("Downloading PDF...");
            request.setDestinationUri(Uri.fromFile(file));
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

            DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            long downloadId = dm.enqueue(request);

            Toast.makeText(context, "Downloading..... ", Toast.LENGTH_LONG).show();
            return downloadId;

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Pending.......", Toast.LENGTH_SHORT).show();
            return -1;
        }
    }

    public static void openPDF(Context context, File file) {
        Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NO_HISTORY);

        try {
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "No PDF app found. Please install a PDF viewer.", Toast.LENGTH_LONG).show();
        }
    }
}
