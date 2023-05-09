package com.example.ondeeisso.api.IBGE;

import com.example.ondeeisso.api.CEP.CEP;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IBGEService {
    @GET("localidades/estados/")
    Call<List<Estados>> buscarEstados();

    @GET("localidades/estados/{uf}/municipios")
    Call<List<Cidades>> buscarCidades(@Path("uf") String uf);

}
