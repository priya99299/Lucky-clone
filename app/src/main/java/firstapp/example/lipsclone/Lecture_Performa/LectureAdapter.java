package firstapp.example.lipsclone.Lecture_Performa;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import firstapp.example.lipsclone.R;
import firstapp.example.lipsclone.api.Models.Lecture.LectureItem;

public class LectureAdapter extends RecyclerView.Adapter<LectureAdapter.ViewHolder> {
    private final Context context;
    private final List<LectureItem> items;
    private final String session;

    public LectureAdapter(Context context, List<LectureItem> items, String session) {
        this.context = context;
        this.items = items;
        this.session = session;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView subject, faculty, duration;
        ImageView viewIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.lecture_title);
            faculty = itemView.findViewById(R.id.lecture_description);
            duration = itemView.findViewById(R.id.lecture_duration);
            viewIcon = itemView.findViewById(R.id.view_icon);
        }
    }

    @NonNull
    @Override
    public LectureAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lecture, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LectureAdapter.ViewHolder holder, int position) {
        LectureItem item = items.get(position);
        holder.subject.setText(item.subject);
        holder.faculty.setText(item.facultyName);
        holder.duration.setText(item.duration);

        holder.viewIcon.setOnClickListener(v -> {
            Intent intent = new Intent(context, Lecture_details.class);
            intent.putExtra("subject", item.subject);
            intent.putExtra("faculty", item.facultyName);
            intent.putExtra("totalLecture", item.totalLecture);
            intent.putExtra("p_id", item.action);
            intent.putExtra("session", session);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
