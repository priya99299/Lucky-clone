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

public class LecturedetailsAdapter extends RecyclerView.Adapter<LecturedetailsAdapter.ViewHolder> {

    private final Context context;
    private final List<LectureItem> items;

    public LecturedetailsAdapter(Context context, List<LectureItem> items) {
        this.context = context;
        this.items = items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView subject, faculty, totalLecture;
        ImageView viewIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.lecture_title);
            faculty = itemView.findViewById(R.id.lecture_description);
//            totalLecture = itemView.findViewById(R.id.lecture_total);
            viewIcon = itemView.findViewById(R.id.view_icon);
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

        holder.subject.setText(item.subject != null ? item.subject : "--");
        holder.faculty.setText(item.facultyName != null ? item.facultyName : "--");
        holder.totalLecture.setText(item.totalLecture != null ? item.totalLecture : "--");

        holder.viewIcon.setOnClickListener(v -> {
            // Open detail screen with extras
            Intent intent = new Intent(context, Lecture_details.class);
            intent.putExtra("subject", item.subject);
            intent.putExtra("faculty", item.facultyName);
            intent.putExtra("totalLecture", item.totalLecture);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
