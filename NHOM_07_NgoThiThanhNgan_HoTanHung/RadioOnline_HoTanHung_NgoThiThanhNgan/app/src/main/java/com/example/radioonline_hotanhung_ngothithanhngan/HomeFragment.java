package com.example.radioonline_hotanhung_ngothithanhngan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.radioonline_hotanhung_ngothithanhngan.Ultilities.ImageUltilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements ChannelsAdapter.Listener{

    RecyclerView rvChannelHome;
    ArrayList<Channel> arrayList = new ArrayList<>();
    ChannelsAdapter channelsAdapter;

    String link = "https://raw.githubusercontent.com/hotanhung2001/RadioOnline/main/srcChannel.json";
    String linkimage ="https://admin.vov.gov.vn//UploadFolder/DonVi_Image";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvChannelHome = view.findViewById(R.id.rvChannelHome);

        JsonArrayDownload jsonArrayDownload = new JsonArrayDownload();
        jsonArrayDownload.execute(link);
        channelsAdapter = new ChannelsAdapter(arrayList, this::onClick);



    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mnuSearch){
            Intent intent = new Intent(getContext(), SearchActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(Channel channel) {

        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.replace(R.id.content, new ListenFragment());
        ft.addToBackStack("");
        ft.commit();

        ImageUltilities.saveToInternalStorage(getContext(),channel.getImage(),channel.getLinkImage());

        Bundle bundle = new Bundle();
        bundle.putInt("id", channel.getIdChannel());
        bundle.putString("name_key", channel.name);
        bundle.putString("url_key", channel.urlChannel);
        bundle.putString("linkimage_key", channel.linkImage);

        getParentFragmentManager().setFragmentResult("Channel", bundle);

    }



    class JsonArrayDownload extends AsyncTask<String,Void, ArrayList<Employee>> {

        @Override
        protected ArrayList<Employee> doInBackground(String... strings) {
            URL url;
            try {
                url = new URL(strings[0]);
                InputStream inputStream = url.openStream();
                BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String downloadedString;
                for (String line; (line = r.readLine()) != null; ) {
                    stringBuilder.append(line);
                }
                inputStream.close();
                downloadedString = stringBuilder.toString();

                JSONObject fullJson = new JSONObject(downloadedString);

                JSONArray channel = fullJson.getJSONArray("channels");
                for (int i = 0; i < channel.length(); i++) {
                    arrayList.add(new Channel());

                    JSONObject jsonObject = (JSONObject) channel.get(i);

                    int id = jsonObject.getInt("id");
                    String name = jsonObject.getString("name");
                    String src = jsonObject.getString("src");
                    String imageName = jsonObject.getString("image");

                    url = new URL(linkimage + imageName);
                    inputStream = url.openStream();
                    arrayList.get(i).setImage(BitmapFactory.decodeStream(inputStream));
                    inputStream.close();
                    arrayList.get(i).setIdChannel(id);
                    arrayList.get(i).setLinkImage(imageName);
                    arrayList.get(i).setUrlChannel(src);
                    arrayList.get(i).setName(name);

                    channelsAdapter.notifyDataSetChanged();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
            }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(ArrayList<Employee> employees) {
            super.onPostExecute(employees);
            channelsAdapter.arrayList = arrayList;

            rvChannelHome.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            rvChannelHome.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
            rvChannelHome.setAdapter(channelsAdapter);
        }
    }
}