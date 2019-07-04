package com.zaigo.coordinatelayouttest.restapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {


 private static String URL_BASE = "https://organicfoodsandcafe.com/nr_butchery/wp-content/themes/tuscany/nrbutchery/";
  //  https://organicfoodsandcafe.com/nr_butchery/wp-content/themes/tuscany/nrbutchery/

   // https://organicfoodsandcafe.com/wp-content/themes/tuscany/nrbutchery/
    private static Retrofit retrofit = null;


   // form json_string like this - [{"id":"13","order_item_name":"Chicken Breast","qty":"6","total":"127.5"},{"id":"14","order_item_name":"Chicken 65","qty":"2","total":"50.0"}]



    public static Retrofit getClientArrayParse() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15000, TimeUnit.SECONDS)
                .readTimeout(15000,TimeUnit.SECONDS).build();

        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL_BASE).client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }


}