package firstapp.example.lipsclone.Documents;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;

public class DownloadAndOpenPDF {

    public static long downloadAndOpen(Context context, String fileUrl, String filename, String folderTag) {
        try {
            Log.d("DownloadAndOpenPDF", "fileUrl raw value: '" + fileUrl + "'");
            if (fileUrl == null || fileUrl.trim().isEmpty()) {
                Toast.makeText(context, "Pending:", Toast.LENGTH_SHORT).show();
                return -1;
            }

            // Use consistent download folder
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), filename);

            if (file.exists() && file.length() > 0) {
                // File exists → open immediately
                openPDF(context, file);
                return -1;
            } else {
                // File does not exist → download
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(fileUrl));
                request.setTitle("Downloading " + filename + "...");
                request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, filename);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                long downloadId = dm.enqueue(request);

                // Only show toast for new download
                Toast.makeText(context, "Downloading...", Toast.LENGTH_LONG).show();

                // Auto-open after download completes
                BroadcastReceiver onComplete = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context ctx, Intent intent) {
                        long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                        if (id == downloadId) {
                            openPDF(context, file);
                            try {
                                context.unregisterReceiver(this);
                            } catch (Exception ignored) {}
                        }
                    }
                };
                context.registerReceiver(onComplete,
                        new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE),
                        Context.RECEIVER_NOT_EXPORTED);

                return downloadId;
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Download failed", Toast.LENGTH_SHORT).show();
            return -1;
        }
    }


    // Direct open from URL
    public static void openDirectly(Context context, String fileUrl) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(fileUrl), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "No PDF app found. Please install a PDF viewer.", Toast.LENGTH_LONG).show();
        }
    }

    // Open local PDF
    public static void openPDF(Context context, File file) {
        try {
            Uri uri = FileProvider.getUriForFile(
                    context,
                    context.getPackageName() + ".provider",
                    file
            );

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(Intent.createChooser(intent, "Open PDF"));
        } catch (Exception e) {
            Toast.makeText(context, "No PDF app found", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
