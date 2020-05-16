package ir.drax.ftp.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import ir.drax.ftp.R;
import ir.drax.ftp.data.sharedPreferences.model.Profile;

import java.util.Date;
import java.util.List;

public class ProfilesAdapter extends RecyclerView.Adapter<ProfilesAdapter.MyViewHolder> {
    private List<Profile> items;
    private Context context;
    private On listener;
    public ProfilesAdapter(Context context,List<Profile> moviesList, On onClickListener){
        this.context=context;
        this.items = moviesList;
        this.listener = onClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Profile item = items.get(position);
        holder.name.setText(item.getName());
        holder.username.setText(item.getUsername());

        if (item.getLastConnect() == 0)
            holder.last_connect.setText(R.string.never);
        else
            holder.last_connect.setText(new Date(item.getLastConnect()).toLocaleString());


        holder.root.setOnClickListener(v -> listener.click(item));
        holder.root.setOnLongClickListener((v) -> {
//creating a popup menu
            PopupMenu popup = new PopupMenu(context, holder.root);
            //inflating menu from xml resource
            popup.inflate(R.menu.profile);
            //adding click listener
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.edit:
                            listener.edit(item);
                            return true;

                            case R.id.remove:
                            listener.remove(item);
                            return true;

                            case R.id.duplicate:
                            listener.duplicate(item);
                            return true;

                            case R.id.sync:
                            listener.sync(item);
                            return true;
                    }
                    return false;
                }
            });
            //displaying the popup
            popup.show();

            return true;
        });
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,username,last_connect;
        public ImageView icon;
        View root;
        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.title);
            icon = view.findViewById(R.id.icon);
            username = view.findViewById(R.id.username);
            last_connect = view.findViewById(R.id.last_connect);
            root = view.findViewById(R.id.root);
        }
    }
    public interface On{
        public void click(Profile profile);
        public void remove(Profile profile);
        public void duplicate(Profile profile);
        public void edit(Profile profile);
        public void sync(Profile profile);

    }
}
