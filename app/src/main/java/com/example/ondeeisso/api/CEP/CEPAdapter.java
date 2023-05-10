package com.example.ondeeisso.api.CEP;
import com.example.ondeeisso.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.ondeeisso.api.Geocoding.Geocoding;
import com.example.ondeeisso.api.Geocoding.Geometry;
import com.example.ondeeisso.api.Geocoding.Location;
import com.example.ondeeisso.api.Geocoding.Result;
import com.example.ondeeisso.api.RetrofitConfig;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.ondeeisso.api.Geocoding.Geocoding.*;
import com.google.gson.Gson;

public class CEPAdapter extends ArrayAdapter<CEP> {
    private int layout;

    public CEPAdapter(@NonNull Context context, int resource, @NonNull List<CEP> ceps) {
        super(context, resource, ceps);
        this.layout = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {   if (convertView == null){
        LayoutInflater inflater = (LayoutInflater)
                getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(this.layout,parent,false);
    }
        TextView rua= (TextView) convertView.findViewById(R.id.tvLog);
        TextView bairro= (TextView)convertView.findViewById(R.id.tvBairro);
        TextView uf= (TextView)convertView.findViewById(R.id.tvUf);
        TextView txcep= (TextView)convertView.findViewById(R.id.tvCep);


        CEP cep = (CEP) this.getItem(position);
        rua.setText(""+cep.getLogradouro());
        bairro.setText("" + cep.getBairro());
        uf.setText("" + uf.getText());
        txcep.setText("" + cep.getCep());

        convertView.setOnClickListener(e -> {
            String searchString = cep.getLogradouro() + ", " + cep.getBairro() + ", " + cep.getLocalidade() + ", " + cep.getUf();
            chamarWsGeocoding(searchString);
        });

        return convertView;
    }

    private void chamarWsGeocoding(String searchString){
        Call<Geocoding> call = new RetrofitConfig().getGeocodingService().buscarLatitudeLongitude(searchString);
        call.enqueue(new Callback<Geocoding>() {
            @Override
            public void onResponse(Call<Geocoding> call, Response<Geocoding> response) {
                if(response.body().status.equalsIgnoreCase("OK"))
                {
                    Geocoding geo = response.body();
                    Result result =  geo.results.get(0);
                    try{
                        if(result != null){
                            Geometry geometry =  result.geometry;
                            if(geometry != null){
                                Location location = geometry.location;
                                if(location != null){
                                    Gson gson = new Gson();
                                    String jsonText = gson.toJson(location);
                                    SharedPreferences prefs = getContext().getSharedPreferences("config", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putString("location", jsonText);
                                    editor.commit();
                                    System.out.println(jsonText);
                                }
                            }
                        }
                    }catch(Error e){
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "Não foi possivel realizar a busca", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Geocoding> call, Throwable t) {
                Log.e("CEPService   ", "Erro ao buscar o geocoding:" + t.getMessage());
            }
        });
    }

}
