package ir.drax.ftp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import ir.drax.ftp.ftp.Content;

import java.util.List;

public class DirectoryContentAdapter extends RecyclerView.Adapter<DirectoryContentAdapter.MyViewHolder> {
    private List<Content> items;
    private Context c;
    private On listener;
    public DirectoryContentAdapter(Context ctx, List<Content> moviesList, On onClickListener) {
        this.c = ctx;
        this.items = moviesList;
        this.listener = onClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Content item = items.get(position);
        holder.name.setText(item.getName());

        if (item.getType()==1) {
            holder.icon.setImageResource(R.drawable.folder);
            holder.modified.setText("");
            holder.size.setText("");
        }
        else if (item.getType()==0){
            holder.icon.setImageResource(R.drawable.file);
            holder.modified.setText(item.getModified().getYear()+ "/"+item.getModified().getMonth()+ "/"+item.getModified().getDay());
            holder.size.setText(getSize(item.getSize()));
        }
        else if (item.getType()==-1) {
            holder.icon.setImageResource(R.drawable.ic_arrow_upward_black_24dp);
            holder.modified.setText("");
            holder.size.setText("");
        }

        holder.root.setOnClickListener(v -> listener.Click(item));
    }

    private String getSize(Long size) {
        if (size<1024)
            return size +" Bytes";
        else if (size<1024 * 1024)
            return (size / (1024 * 1024))+" KB";
        if (size<1024*1024*1024)
            return (size / (1024 * 1024 * 1024))+" MB";
        else
            return (size / (1024 * 1024 * 1024 * 1024)) + " GB";
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface On {
        public void Click(Content category);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,size,modified;
        public ImageView icon;
        View root;
        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.title);
            icon = view.findViewById(R.id.icon);
            size = view.findViewById(R.id.size);
            modified = view.findViewById(R.id.modified);
            root = view.findViewById(R.id.root);
        }
    }


}
