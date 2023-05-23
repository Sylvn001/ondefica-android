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
import androidx.navigation.fragment.NavHostFragment;

import com.example.ondeeisso.R;
import com.example.ondeeisso.api.CEP.CEP;
import com.example.ondeeisso.api.CEP.CEPAdapter;
import com.example.ondeeisso.databinding.FragmentResultadoBinding;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResultadoFragment extends Fragment {

    private FragmentResultadoBinding binding;
    private Context context;
    private ListView lvResultados;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getContext();

        SharedPreferences prefs = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonText  = prefs.getString("ceps","");

        CEP[] arrayCeps =  gson.fromJson(jsonText, CEP[].class);
        List<CEP> listaCeps;
        if(arrayCeps.length > 0){
            listaCeps= Arrays.asList(arrayCeps);
        }else{
            listaCeps = new ArrayList<CEP>();
        }

        View view = inflater.inflate(R.layout.fragment_resultado, container, false);
        CEPAdapter adapter = new CEPAdapter(context, R.layout.cep_item, listaCeps, getActivity().getSupportFragmentManager(), NavHostFragment.findNavController(ResultadoFragment.this));

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