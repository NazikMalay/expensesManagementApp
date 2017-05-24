package service;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by nazar on 24.05.17.
 */
public class RetrofitUtil {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
