package com.example.ondeeisso.api;

import android.util.Log;

import com.example.ondeeisso.api.CEP.CEP;
import com.example.ondeeisso.api.CEP.CEPService;
import com.example.ondeeisso.api.IBGE.IBGEService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {
   private final Retrofit retrofitCEP;
   private final Retrofit retrofitIBGE;
//   private final Retrofit retrofitMaps;

   public RetrofitConfig() {
       Gson gson = new GsonBuilder().setLenient().create();

       retrofitCEP = new Retrofit.Builder().baseUrl("https://viacep.com.br/ws/")
               .addConverterFactory(GsonConverterFactory.create()).build();

       retrofitIBGE = new Retrofit.Builder().baseUrl("https://servicodados.ibge.gov.br/api/v1/")
               .addConverterFactory(GsonConverterFactory.create()).build();

//       retrofitMaps = new Retrofit.Builder().baseUrl("")
//               .addConverterFactory(GsonConverterFactory.create()).build();
   }

   public CEPService getCEPService() {
       return this.retrofitCEP.create(CEPService.class);
   }

   public IBGEService getIBGEService(){
       return this.retrofitIBGE.create(IBGEService.class);
   }
}
