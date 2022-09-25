package com.example.radioonline_hotanhung_ngothithanhngan;

import android.location.GpsStatus;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChannelsAdapter extends RecyclerView.Adapter<ChannelsAdapter.ChannelSVH> implements Filterable {

    ArrayList<Channel> arrayList;
    ArrayList<Channel> arrayListFilter;
    Listener listener;

    public ChannelsAdapter(ArrayList<Channel> arrayList, Listener listener) {
        this.arrayList = arrayList;
        this.arrayListFilter = arrayList;
        this.listener = listener;
    }

    public ChannelsAdapter(ArrayList<Channel> arrayList) {
        this.arrayList = arrayList;
        this.arrayListFilter = arrayList;
    }


    /**
     * Called when RecyclerView needs a new {@link RecyclerView.ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * . Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     */
    @NonNull
    @Override
    public ChannelSVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_channels, parent, false);
        return new ChannelSVH(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link RecyclerView.ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link RecyclerView.ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override  instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ChannelSVH holder, int position) {
        Channel channel = arrayListFilter.get(position);

        holder.imgChannel.setImageBitmap(channel.image);
        holder.txtName.setText(channel.name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(channel);
            }
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if (arrayListFilter!=null)
        return arrayListFilter.size();
        return 0;
    }

    /**
     * <p>Returns a filter that can be used to constrain data with a filtering
     * pattern.</p>
     *
     * <p>This method is usually implemented by {@link Adapter}
     * classes.</p>
     *
     * @return a filter used to constrain data
     */
    @Override
    public Filter getFilter() {
        return new ChannelFilter();
    }

    class ChannelSVH extends RecyclerView.ViewHolder{

        TextView txtName;
        ImageView imgChannel;

        public ChannelSVH(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            imgChannel = itemView.findViewById(R.id.imgChannel);

        }
    }
    interface Listener{
        void onClick(Channel channel);
    }
    class ChannelFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String charString = charSequence.toString();
            if (charString.isEmpty()) {
                arrayListFilter = arrayList;
            } else {
                ArrayList<Channel> filteredList = new ArrayList<>();
                for (Channel row : arrayList) {
                    if (row.name.toLowerCase().contains(charSequence)) {
                        filteredList.add(row);
                    }
                }
                arrayListFilter = filteredList;
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = arrayListFilter;
            return filterResults;
        }

        /**
         * <p>Invoked in the UI thread to publish the filtering results in the
         * user interface. Subclasses must implement this method to display the
         * results computed in {@link #performFiltering}.</p>
         *
         * @param constraint the constraint used to filter the data
         * @param results    the results of the filtering operation
         * @see #filter(CharSequence, FilterListener)
         * @see #performFiltering(CharSequence)
         * @see FilterResults
         */
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            arrayListFilter = (ArrayList<Channel>) results.values;

            notifyDataSetChanged();
        }
    }
}
