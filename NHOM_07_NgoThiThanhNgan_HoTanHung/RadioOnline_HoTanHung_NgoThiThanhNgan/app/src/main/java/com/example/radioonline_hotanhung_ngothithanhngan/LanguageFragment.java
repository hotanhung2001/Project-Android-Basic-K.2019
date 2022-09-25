package com.example.radioonline_hotanhung_ngothithanhngan;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LanguageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LanguageFragment extends Fragment {
    Button btnVie, btnEng, btnChi, btnKo, btnJa;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context baseContext;

    public LanguageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LanguageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LanguageFragment newInstance(String param1, String param2) {
        LanguageFragment fragment = new LanguageFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_language, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnEng= view.findViewById(R.id.btnEng);
        btnVie= view.findViewById(R.id.btnVie);
        btnChi= view.findViewById(R.id.btnChi);
        btnKo= view.findViewById(R.id.btnKo);
        btnJa= view.findViewById(R.id.btnJa);

        btnEng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLanguage("en");
            }
        });
        btnVie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLanguage("vi");
            }
        });
        btnChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLanguage("zh");
            }
        });
        btnKo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLanguage("ko");
            }
        });
        btnJa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLanguage("ja");
            }
        });
    }
    public void updateLanguage(String language){
        Locale locale = new Locale(language);

        Configuration config=new Configuration();
        config.locale = locale;

        requireContext().getResources().updateConfiguration(config, requireContext().getResources().getDisplayMetrics());
        Fragment fragment = new LanguageFragment();
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.content, fragment);

        fragmentTransaction.commit();
    }
}