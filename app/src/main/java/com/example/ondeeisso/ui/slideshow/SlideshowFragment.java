package com.example.ondeeisso.ui.slideshow;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.example.ondeeisso.R;
import com.example.ondeeisso.api.CEP.CEP;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

public class SlideshowFragment extends Fragment implements OnMapReadyCallback {

    private Context context;
    private MapView mapView;
    private GoogleMap gmap;
    private MarkerOptions markerOptions = new MarkerOptions();
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    private TextView txvCEP;
    private TextView txvRua;
    private TextView txvBairro;
    private TextView txvCidade;
    private TextView txvEstado;
    private TextView txvIbge;
    private TextView txvGia;
    private TextView txvDDD;
    private TextView txvSiafi;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getContext();
        View view = inflater.inflate(R.layout.fragment_slideshow, container, false);

        txvBairro = view.findViewById(R.id.txvBairro);
        txvCEP = view.findViewById(R.id.txvCep);
        txvCidade = view.findViewById(R.id.txvCidade);
        txvDDD = view.findViewById(R.id.txvDDD);
        txvEstado = view.findViewById(R.id.txvEstado);
        txvGia = view.findViewById(R.id.txvGia);
        txvIbge = view.findViewById(R.id.txvIbge);
        txvRua = view.findViewById(R.id.txvRua);
        txvSiafi = view.findViewById(R.id.txvSiafi);

        /*Populate CEP info*/
        SharedPreferences prefs = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonText  = prefs.getString("cepSel","");
        CEP cep;
        if(jsonText.isEmpty())
        {
            cep = new CEP();
        }else{
            cep = gson.fromJson(jsonText, CEP.class);
        }

        txvBairro.setText(""+ cep.getBairro());
        txvCEP.setText(""+ cep.getCep());
        txvCidade.setText(""+ cep.getLocalidade());
        txvDDD.setText(""+ cep.getDdd());
        txvEstado.setText(""+ cep.getUf());
        txvGia.setText(""+ cep.getGia());
        txvIbge.setText(""+ cep.getIbge());
        txvRua.setText(""+ cep.getLogradouro());
        txvSiafi.setText(""+ cep.getSiafi());


        /*Mapa*/
        mapView = view.findViewById(R.id.mapViewId);
        mapView.setClickable(true);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);


        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle (MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle (MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }
        mapView.onSaveInstanceState(mapViewBundle);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMinZoomPreference(12);
        gmap.setIndoorEnabled(true);

        UiSettings ponto = gmap.getUiSettings();
        ponto.setIndoorLevelPickerEnabled(true);

        ponto.setMyLocationButtonEnabled(true);
        ponto.setMapToolbarEnabled(true);
        ponto.setCompassEnabled(true);
        ponto.setZoomControlsEnabled(true);

        /*Obter shared preferences para gson*/
        SharedPreferences prefs = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonText  = prefs.getString("location","");

        //PONTO CENTRAL DA CIDADE
        LatLng latLong;

        if(jsonText.isEmpty()){
            latLong = new LatLng(-22.1244244, -51.3860479);
        }
        latLong = gson.fromJson(jsonText, LatLng.class);


        //MARCADOR NO MAPA
        markerOptions.position(latLong);
        gmap.addMarker(markerOptions);

        gmap.moveCamera(CameraUpdateFactory.newLatLng(latLong));
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }
}