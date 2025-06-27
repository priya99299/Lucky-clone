package firstapp.example.lipsclone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import firstapp.example.lipsclone.api.Models.LectureItem;

public class LectureAdapter extends RecyclerView.Adapter<LectureAdapter.ViewHolder> {

    private final List<LectureItem> items;

    public LectureAdapter(List<LectureItem> items) {
        this.items = items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView subject, faculty, duration;
        View topicHeader;
        TextView topics;

        public ViewHolder(View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.lecture_title);
            faculty = itemView.findViewById(R.id.lecture_description);
            duration = itemView.findViewById(R.id.lecture_duration);
            topicHeader = itemView.findViewById(R.id.TopicList);
            topics = itemView.findViewById(R.id.lecture_topics);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lecture, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LectureItem item = items.get(position);

        holder.subject.setText(item.subject);
        holder.faculty.setText(item.facultyName);
        holder.duration.setText("Duration: " + item.duration);

        // Set your hard-coded topics text
        holder.topics.setText("📌 Topic 1: Introduction\n📌 Topic 2: Deep Dive\n📌 Topic 3: Q&A Session");
        holder.topics.setVisibility(View.GONE);

        holder.topicHeader.setOnClickListener(v -> {
            if (holder.topics.getVisibility() == View.GONE) {
                holder.topics.setVisibility(View.VISIBLE);
            } else {
                holder.topics.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
