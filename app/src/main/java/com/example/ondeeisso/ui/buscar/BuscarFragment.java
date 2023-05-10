package com.example.ondeeisso.ui.buscar;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;

import com.example.ondeeisso.R;
import com.example.ondeeisso.api.CEP.CEP;
import com.example.ondeeisso.api.IBGE.Cidades;
import com.example.ondeeisso.api.IBGE.Estados;
import com.example.ondeeisso.api.RetrofitConfig;
import com.example.ondeeisso.databinding.FragmentBuscarBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuscarFragment extends Fragment {
    private FragmentBuscarBinding binding;

    private Button btnBuscar;
    private Spinner spEstado;
    private Spinner spCidade;
    private EditText etReferencia;
    private Estados estadoSel;
    private Cidades cidadeSel;
    private String referenciaSel;
    private Context context;

    private List<Estados> estados;
    private List<Cidades> cidades = new ArrayList<Cidades>();

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        context = this.getContext();
        View view = inflater.inflate(R.layout.fragment_buscar, container, false);
        btnBuscar = view.findViewById(R.id.btnBuscar);
        spCidade = (Spinner) view.findViewById(R.id.spCidade);
        spEstado = (Spinner) view.findViewById(R.id.spEstado);
        etReferencia = view.findViewById(R.id.etReferencia);


        /*LISTA ESTADOS*/
        chamarWSEstado();
        spEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
                estadoSel = (Estados) adapterView.getItemAtPosition(i);
                chamarWSCidade();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                estadoSel = (Estados)  adapterView.getItemAtPosition(0);
                chamarWSCidade();
            }
        });

        /* LISTA CIDADES */
        spCidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cidadeSel = (Cidades) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                cidadeSel = (Cidades)  adapterView.getItemAtPosition(0);
            }
        });


        btnBuscar.setOnClickListener(e -> {
            referenciaSel = etReferencia.getText().toString();
            buscaCeps();
        });

        return view;
    }

    private void chamarWSEstado() {
        Call<List<Estados>> call = new RetrofitConfig().getIBGEService().buscarEstados();
        call.enqueue(new Callback<List<Estados>>() {
            @Override
            public void onResponse(Call<List<Estados>> call, Response<List<Estados>> response) {
               estados = new ArrayList<Estados>();
               estados.addAll(response.body());
               ArrayAdapter<Estados> estadoAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, estados);
               spEstado.setAdapter(estadoAdapter);
               estadoSel = estados.get(0);
               chamarWSCidade();

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
                cidades = new ArrayList<Cidades>();
                cidades.addAll(response.body());
                ArrayAdapter<String> cidadeAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, cidades);
                spCidade.setAdapter(cidadeAdapter);
            }
            @Override
            public void onFailure(Call<List<Cidades>> call, Throwable t) {
                Log.e("CEPService   ", "Erro ao buscar o cep:" + t.getMessage());
            }
        });
    }


    private void buscaCeps(){
        String uf = estadoSel.getSigla();
        String cidade = cidadeSel.getNome();
        Call<List<CEP>> call = new RetrofitConfig().getCEPService().buscarCEP(uf, cidade, referenciaSel);

        if(uf.isEmpty() || cidade.isEmpty() || referenciaSel.isEmpty()){
            Toast.makeText(context, "Os dados não podem estar em branco!", Toast.LENGTH_SHORT).show();
        }else{
            call.enqueue(new Callback<List<CEP>>() {
                @Override
                public void onResponse(Call<List<CEP>> call, Response<List<CEP>> response) {
                    if(response.body() != null){
                        Gson gson = new Gson();
                        List<CEP> cepList = new ArrayList<CEP>();
                        cepList.addAll(response.body());
                        String jsonText = gson.toJson(cepList);

                        SharedPreferences prefs = context.getSharedPreferences("config", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("ceps", jsonText);
                        editor.commit();

                        Toast.makeText(context, "Sucesso ao buscar enderecos!", Toast.LENGTH_SHORT).show();

                    }
                }
                @Override
                public void onFailure(Call<List<CEP>> call, Throwable t) {
                    Log.e("CEPService   ", "Erro ao buscar o cep:" + t.getMessage());
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}