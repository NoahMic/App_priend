package io.github.noahmic.app_priend

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object{
        private val BASE_URL = "http://127.0.0.1:8000"

        fun getService(): RetrofitService {
            return getInstance().create(RetrofitService::class.java)
        }
        private val client = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor()).build()

        private fun getInstance() : Retrofit {
            val gson = GsonBuilder().setLenient().create()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }

        private fun httpLoggingInterceptor() : HttpLoggingInterceptor {
            val interceptor = HttpLoggingInterceptor {
                android.util.Log.d("HttpLoging", it+ "")
            }
            return interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        }

    }

}