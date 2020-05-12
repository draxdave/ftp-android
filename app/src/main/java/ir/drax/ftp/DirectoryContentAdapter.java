package ir.drax.ftp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import ir.top.niroucard.data.entities.Category;

import java.util.List;

public class DirectoryContentAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {
    private List<Category> items;
    private Context c;
    private On listener;
    public DirectoryContentAdapter(Context ctx, List<Category> moviesList, On onClickListener) {
        this.c = ctx;
        this.items = moviesList;
        this.listener = onClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Category item = items.get(position);
        holder.name.setText(item.getTitle());
        try {
            Glide.with(c)
                    .load(item.getIconUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.round_placeholder)
                    .into(holder.icon);


        } catch (Exception ex) {
        }
        holder.btn.setOnClickListener(v -> listener.Click(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface On {
        public void Click(Category category);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView icon;
        public LinearLayout btn;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.itemName);
            icon = view.findViewById(R.id.itemImg);
            btn = view.findViewById(R.id.layout);
        }
    }


}
