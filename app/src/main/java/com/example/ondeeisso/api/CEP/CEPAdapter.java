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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

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
import com.example.ondeeisso.ui.buscar.BuscarFragment;
import com.example.ondeeisso.ui.resultado.ResultadoFragment;
import com.example.ondeeisso.ui.slideshow.SlideshowFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

public class CEPAdapter extends ArrayAdapter<CEP> {
    private int layout;
    private FragmentManager fragmanager;
    private  NavController navController;

    public CEPAdapter(@NonNull Context context, int resource, @NonNull List<CEP> ceps, FragmentManager fragm, NavController navController) {
        super(context, resource, ceps);
        this.layout = resource;
        this.fragmanager = fragm;
        this.navController = navController;
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
            chamarWsGeocoding(searchString, cep);

            navController.navigate(R.id.action_SecondFragment_to_ThirdFragment);
        });

        return convertView;
    }

    private void chamarWsGeocoding(String searchString, CEP cep){
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
                                LatLng latlng= new LatLng(location.lat, location.lng);
                                if(location != null){
                                    Gson gson = new Gson();
                                    String jsonText = gson.toJson(latlng);
                                    String jsonCEP = gson.toJson(cep);
                                    SharedPreferences prefs = getContext().getSharedPreferences("config", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putString("location", jsonText);
                                    editor.putString("cepSel", jsonCEP);
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
