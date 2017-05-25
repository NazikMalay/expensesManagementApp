package service;

import com.google.gson.JsonElement;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by nazar on 21.05.17.
 */
public interface CurrencyService {

    @GET("latest")
    Call<JsonElement> getRates(@Query("base") String base,@Query("symbols") String symbols);

    @GET("latest")
    Call<JsonElement> getRatesByBase(@Query("base") String base);
}

