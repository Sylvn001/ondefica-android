package com.example.ondeeisso.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.ondeeisso.R;
import com.example.ondeeisso.api.CEP.CEP;
import com.example.ondeeisso.api.IBGE.Cidades;
import com.example.ondeeisso.api.IBGE.Estados;
import com.example.ondeeisso.api.RetrofitConfig;
import com.example.ondeeisso.databinding.FragmentHomeBinding;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    private Button btnBuscar;
    private Spinner spEstado;
    private Spinner spCidade;
    private EditText etReferencia;
    private Estados estadoSel;
    private String cidadeSel;
    private String referenciaSel;

    private List<Estados> estados = new ArrayList<Estados>();
    private List<Cidades> cidades = new ArrayList<Cidades>();

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        btnBuscar = view.findViewById(R.id.btnBuscar);
        spCidade = view.findViewById(R.id.spCidade);
        spEstado = view.findViewById(R.id.spEstado);
        etReferencia = view.findViewById(R.id.etReferencia);


        /*LISTA ESTADOS*/
        chamarWSEstado();
        ArrayAdapter<String> estadoAdapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_spinner_item, estados);
        spEstado.setAdapter(estadoAdapter);
        spEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
                System.out.println("aaaaaaaaaaaaaaaaaaaa");
                estadoSel = (Estados) adapterView.getItemAtPosition(i);
                System.out.println(estadoSel.getSigla());
                spEstado.setSelection(i);
                chamarWSCidade();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                estadoSel = (Estados)  adapterView.getItemAtPosition(0);
                chamarWSCidade();
            }
        });

        ArrayAdapter<String> cidadeAdapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_spinner_item, cidades);
        spCidade.setAdapter(cidadeAdapter);
        spCidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cidadeSel = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                cidadeSel =  adapterView.getItemAtPosition(0).toString();
            }
        });


        btnBuscar.setOnClickListener(e -> {
            referenciaSel = etReferencia.getText().toString();
        });

        return view;
    }

    private void chamarWSEstado() {
        Call<List<Estados>> call = new RetrofitConfig().getIBGEService().buscarEstados();
        call.enqueue(new Callback<List<Estados>>() {
            @Override
            public void onResponse(Call<List<Estados>> call, Response<List<Estados>> response) {
               estados.addAll(response.body());
            }
            @Override
            public void onFailure(Call<List<Estados>> call, Throwable t) {
                Log.e("CEPService   ", "Erro ao buscar o cep:" + t.getMessage());
            }
        });
    }

    private void chamarWSCidade() {
        Call<List<Cidades>> call = new RetrofitConfig().getIBGEService().buscarCidades(estadoSel.getSigla());
        call.enqueue(new Callback<List<Cidades>>() {
            @Override
            public void onResponse(Call<List<Cidades>> call, Response<List<Cidades>> response) {
                cidades.addAll(response.body());
            }
            @Override
            public void onFailure(Call<List<Cidades>> call, Throwable t) {
                Log.e("CEPService   ", "Erro ao buscar o cep:" + t.getMessage());
            }
        });
    }


    private void buscaCidades(){

    }

    private void buscaCeps(){

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}