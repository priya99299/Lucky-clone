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
    private final List<LectureDetailItem> lectureList;

    public LecturedetailsAdapter(Context context, List<LectureDetailItem> lectureList) {
        this.context = context;
        this.lectureList = lectureList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lecture_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LectureDetailItem item = lectureList.get(position);

        // Only show main topic, sub topic, and completed on
        holder.topic.setText(item.getMainTopic() != null ? item.getMainTopic() : "--");
        holder.subTopic.setText(item.getSubTopic() != null ? item.getSubTopic() : "--");
        holder.completedOn.setText(item.getCompletedOn() != null && !item.getCompletedOn().equals("0000-00-00")
                ?   item.getCompletedOn()
                : "Not Completed");
    }

    @Override
    public int getItemCount() {
        return lectureList != null ? lectureList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView topic, subTopic, completedOn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            topic = itemView.findViewById(R.id.topicText);
            subTopic = itemView.findViewById(R.id.subTopicText);
            completedOn = itemView.findViewById(R.id.completedOnText);
        }
    }
}
