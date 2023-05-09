package com.example.ondeeisso.ui.resultado;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ondeeisso.R;
import com.example.ondeeisso.api.CEP.CEP;
import com.example.ondeeisso.databinding.FragmentResultadoBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ResultadoFragment extends Fragment {

    private FragmentResultadoBinding binding;
    private Context context;
    private ListView lvResultados;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getContext();
        binding = FragmentResultadoBinding.inflate(inflater, container, false);

        SharedPreferences prefs = context.getSharedPreferences("config", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String jsonText  = prefs.getString("ceps","");
        List<CEP> listaCeps = gson.fromJson(jsonText, List.class);

        View view = inflater.inflate(R.layout.fragment_resultado, container, false);
        ArrayAdapter<CEP> adapter;
        adapter = new ArrayAdapter<CEP>(context, android.R.layout.simple_list_item_1, listaCeps);

        lvResultados = view.findViewById(R.id.lvResultados);
        lvResultados.setAdapter(adapter);



        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}