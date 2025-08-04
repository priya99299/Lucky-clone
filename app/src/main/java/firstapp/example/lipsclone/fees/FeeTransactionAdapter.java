package firstapp.example.lipsclone.fees;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import firstapp.example.lipsclone.R;
import firstapp.example.lipsclone.api.Models.Fees.FeeTransactionItem;

public class FeeTransactionAdapter extends RecyclerView.Adapter<FeeTransactionAdapter.FeeViewHolder> {

    private final List<FeeTransactionItem> transactionList;

    public FeeTransactionAdapter(List<FeeTransactionItem> list) {
        this.transactionList = list;
    }

    @NonNull
    @Override
    public FeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fee, parent, false);
        return new FeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeeViewHolder holder, int position) {
        FeeTransactionItem item = transactionList.get(position);

        if (item.getAmount() == null ||
                item.getType() == null ||
                (!"Due".equalsIgnoreCase(item.getType()) && !"Deposit".equalsIgnoreCase(item.getType()))) {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
            return;
        }

        holder.feeName.setText(item.getParticulars());
        holder.feeAmount.setText("");
        holder.payIn.setText("");

        if ("Due".equalsIgnoreCase(item.getType())) {
            holder.feeAmount.setText("₹" + item.getAmount());
        } else if ("Deposit".equalsIgnoreCase(item.getType())) {
            holder.payIn.setText("₹" + item.getAmount());
        }
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    static class FeeViewHolder extends RecyclerView.ViewHolder {
        TextView feeName, feeAmount, payIn;

        FeeViewHolder(@NonNull View itemView) {
            super(itemView);
            feeName = itemView.findViewById(R.id.fee_name);
            feeAmount = itemView.findViewById(R.id.fee_amount);
            payIn = itemView.findViewById(R.id.pay_in);
        }
    }
}
