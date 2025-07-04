package firstapp.example.lipsclone.Lecture_Performa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import firstapp.example.lipsclone.R;
import firstapp.example.lipsclone.api.Models.Lecture.Lecturedetails.LectureDetailItem;

public class LecturedetailsAdapter extends RecyclerView.Adapter<LecturedetailsAdapter.ViewHolder> {
    private final Context context;
    private final List<LectureDetailItem> items;

    public LecturedetailsAdapter(Context context, List<LectureDetailItem> items) {
        this.context = context;
        this.items = items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView topic, subTopic, completedOn;

        public ViewHolder(View itemView) {
            super(itemView);
            topic = itemView.findViewById(R.id.lecture_title);
            subTopic = itemView.findViewById(R.id.lecture_description);
            completedOn = itemView.findViewById(R.id.lecture_completed_on);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lecture_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (items == null || items.size() <= position) return;
        LectureDetailItem item = items.get(position);

        if (holder.topic != null)
            holder.topic.setText(item.getMainTopic() != null ? item.getMainTopic() : "--");

        if (holder.subTopic != null)
            holder.subTopic.setText(item.getSubTopic() != null ? item.getSubTopic() : "--");

        if (holder.completedOn != null)
            holder.completedOn.setText(
                    (item.getCompletedOn() != null && !item.getCompletedOn().equals("0000-00-00"))
                            ? item.getCompletedOn()
                            : "--"
            );

    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}
