package com.example.radioonline_hotanhung_ngothithanhngan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements ChannelsAdapter.Listener{

    Toolbar toolbar;
    SearchView searchView;
    ChannelsAdapter channelsAdapter;
    ArrayList<Channel> arrayList;
    RecyclerView rvChannelFilter;
    LinearLayout linearEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolbar = findViewById(R.id.toolbarSearch);
        searchView = toolbar.findViewById(R.id.searchView);
        linearEmpty = findViewById(R.id.linearEmpty);

        rvChannelFilter = findViewById(R.id.rvChannelFilter);


        channelsAdapter = new ChannelsAdapter(arrayList, this);
        rvChannelFilter.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvChannelFilter.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rvChannelFilter.setAdapter(channelsAdapter);
        setVisible(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (channelsAdapter.arrayListFilter.size() == 0 || newText.isEmpty()){
                    setVisible(false);
                }else {
                    setVisible(true);
                }
                return false;
            }
        });
    }
    public void setVisible(boolean flag){
        if (!flag){
            linearEmpty.setVisibility(View.VISIBLE);
            rvChannelFilter.setVisibility(View.GONE);
        }else {
            linearEmpty.setVisibility(View.GONE);
            rvChannelFilter.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(Channel channel) {

    }
}