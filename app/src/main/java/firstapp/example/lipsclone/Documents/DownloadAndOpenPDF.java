package firstapp.example.lipsclone.Documents;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;
import androidx.core.content.FileProvider;

import java.io.File;

public class DownloadAndOpenPDF {

    public static void downloadAndOpen(Context context, String fileUrl, String filename) {
        try {
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), filename);

            if (file.exists()) {
                openPDF(context, file);
                return;
            }

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(fileUrl));
            request.setTitle("Downloading PDF...");
            request.setDestinationUri(Uri.fromFile(file));
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

            DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            long downloadId = dm.enqueue(request);

            Toast.makeText(context, "Downloading... ", Toast.LENGTH_LONG).show();

            // Better UX: register BroadcastReceiver for download complete event
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error downloading PDF", Toast.LENGTH_SHORT).show();
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
