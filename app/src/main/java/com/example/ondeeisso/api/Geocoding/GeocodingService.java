package com.example.ondeeisso.api.Geocoding;

import com.example.ondeeisso.api.CEP.CEP;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GeocodingService {
    String API_KEY="AIzaSyAl3DIOS1BhSlDGBouwSGrv8TI7eaE1CpE";

    @GET("geocode/json?key=" + API_KEY)
    Call<Geocoding> buscarLatitudeLongitude(@Query("address") String address);
}
