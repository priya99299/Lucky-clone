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

    // Old version
    public static void downloadAndOpen(Context context, String fileUrl, String filename) {
        downloadAndOpen(context, fileUrl, filename, "common");
    }

    // New version
    public static long downloadAndOpen(Context context, String fileUrl, String filename, String s_id) {
        try {
            Log.d("DownloadAndOpenPDF", "fileUrl raw value: '" + fileUrl + "'");
            if (fileUrl == null || fileUrl.trim().isEmpty()) {
                Toast.makeText(context, "Pending:", Toast.LENGTH_SHORT).show();
                return -1;
            }

            String uniqueFilename = s_id + "_" + filename;
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), uniqueFilename);

            if (file.exists()) {
                // Already downloaded → open directly
                openPDF(context, file);
                return -1;
            } else {
                // First time → download + auto open when complete
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(fileUrl));
                request.setTitle("Downloading PDF...");
                request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, uniqueFilename);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                long downloadId = dm.enqueue(request);

                Toast.makeText(context, "Downloading...", Toast.LENGTH_LONG).show();

                // Receiver for auto-open after download completes
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
            Toast.makeText(context, "Pending.......", Toast.LENGTH_SHORT).show();
            return -1;
        }
    }

    //  Direct open from URL (first click)
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
