package com.example.radioonline_hotanhung_ngothithanhngan;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.radioonline_hotanhung_ngothithanhngan.Ultilities.ImageUltilities;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListenFragment extends Fragment {

    ImageView imgChannels, imgPlayChannel;
    ImageButton imgPrevious, imgNext, imgPlay;
    CheckBox cbHeart;
    TextView txtNameChannels;
    Channel channel = new Channel();
    FavDB database;

    //MediaPlayer mediaPlayer;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListenFragment newInstance(String param1, String param2) {
        ListenFragment fragment = new ListenFragment();
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
        getParentFragmentManager().setFragmentResultListener("Channel", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                int id = bundle.getInt("id");
                String name = bundle.getString("name_key");
                String urlChannel = bundle.getString("url_key");
                String image = bundle.getString("linkimage_key");

                //System.out.println(id+" "+name+" "+urlChannel+" "+image);
                channel.setIdChannel(id);
                channel.setName(name);
                channel.setLinkImage(image);
                channel.setUrlChannel(urlChannel);
                channel.setImage(ImageUltilities.loadImageFromStorage(getContext(),image));
                if (database.CheckFavorite(id)){
                    cbHeart.setChecked(true);
                }
                imgPlayChannel.setImageBitmap(ImageUltilities.loadImageFromStorage(getContext(),image));
                txtNameChannels.setText(name);

//                Ngủ rồi à :))
                String url = "" + urlChannel;


                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                try {
                    mediaPlayer.setDataSource(url);
                    mediaPlayer.prepareAsync();
                    //mediaPlayer.prepare();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                            imgPlay.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);

                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
                imgPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.pause();
                            imgPlay.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                        } else {
                            mediaPlayer.start();
                            imgPlay.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
                        }
                    }
                });
                mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        switch (extra) {
                            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                                // Do Something
                                // eg. reset the media player and restart
                                break;
                            case MediaPlayer.MEDIA_ERROR_IO:
                                // Do Something
                                // eg. Show dialog to user indicating bad connectivity
                                // or attempt to restart the player
                                break;
                            case MediaPlayer.MEDIA_ERROR_TIMED_OUT:
                                //Do Something
                                //eg. Show dialog that there was error in connecting to the server
                                // or attempt some retries
                                break;
                        }
                        //You must always return true if you want the error listener to work
                        return true;
                    }
                });
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        database = new FavDB(getContext());

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listen, container, false);
        imgChannels = view.findViewById(R.id.imgChannels);
        imgPlayChannel = view.findViewById(R.id.imgPlayChannel);

        imgPrevious = view.findViewById(R.id.imgPrevious);
        imgNext = view.findViewById(R.id.imgNext);
        imgPlay = view.findViewById(R.id.imgPlay);
        cbHeart = view.findViewById(R.id.like_button);

        txtNameChannels = view.findViewById(R.id.txtNameChannels);

        cbHeart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /**
             * Called when the checked state of a compound button has changed.
             *
             * @param buttonView The compound button view whose state has changed.
             * @param isChecked  The new checked state of buttonView.
             */
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbHeart.setBackgroundResource(R.drawable.ic_baseline_favorite__heart_24);
                    database.insertChannelFavorite(channel);

                } else {
                    database.removeFavorite(channel.idChannel);
                    cbHeart.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                }
            }
        });
        return view;

    }
}


