package firstapp.example.lipsclone.Canteen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import firstapp.example.lipsclone.R;
import firstapp.example.lipsclone.api.Models.canteen.CanteenItem;

public class CanteenAdapter extends RecyclerView.Adapter<CanteenAdapter.ViewHolder> {

    private Context context;
    private List<CanteenItem> itemList;

    public CanteenAdapter(Context context, List<CanteenItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_canteen, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CanteenItem item = itemList.get(position);

        // Assuming API returns proper fields
        holder.tvName.setText(item.getEventname()); // Menu item name
        holder.tvPrice.setText("₹" + item.getDatefrom()); // Price or Date field
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
