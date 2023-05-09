package com.example.ondeeisso.api.CEP;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CEPService {
    @GET("{estado}/{cidade}/{referencia}/json")
    Call <List<CEP>> buscarCEP(@Path("estado") String estado, @Path("cidade") String cidade, @Path("referencia") String referencia);
}
