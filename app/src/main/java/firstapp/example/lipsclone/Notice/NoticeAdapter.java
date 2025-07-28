package firstapp.example.lipsclone.Notice;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import firstapp.example.lipsclone.Documents.DownloadAndOpenPDF;
import firstapp.example.lipsclone.R;
import firstapp.example.lipsclone.api.Models.Notice.Notice_Reponse;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder> {

    private static final String TAG = "NoticeAdapter";
    private final List<Notice_Reponse.Notice> noticeList;
    private final Context context;

    public NoticeAdapter(Context context, List<Notice_Reponse.Notice> noticeList) {
        this.context = context;
        this.noticeList = noticeList;
    }

    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notice_card, parent, false);
        return new NoticeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewHolder holder, int position) {
        Notice_Reponse.Notice notice = noticeList.get(position);
        Log.d(TAG, "Binding notice: " + notice.title + " -> " + notice.description);

        holder.title.setText(notice.title);

        holder.icon.setOnClickListener(v -> {
            String url = notice.description;

            if (url == null || url.trim().isEmpty()) {
                Toast.makeText(context, "No link available", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!url.toLowerCase().endsWith(".pdf")) {
                Toast.makeText(context, "Only PDF notices are supported", Toast.LENGTH_SHORT).show();
                return;
            }

            String filename = notice.title.replaceAll("\\s+", "_") + ".pdf";
            DownloadAndOpenPDF.downloadAndOpen(context, url, filename);
        });

    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }

    public static class NoticeViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icon;

        public NoticeViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.noticeTitle);
            icon = itemView.findViewById(R.id.iconImage);
        }
    }
}
