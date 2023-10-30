package com.example.weatherapi.di

import com.example.weatherapi.data.remote.ApiDetails
import com.example.weatherapi.data.remote.ApiEndpoint
import com.example.weatherapi.data.repository.Repository
import com.example.weatherapi.data.repository.RepositoryImplementation
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module // Module > responsible for defining definition of injection
@InstallIn(SingletonComponent::class) // dictates the scope of injection
class AppModule {
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
//            .addInterceptor {
//                val request =
//                    it.request()
//                        .newBuilder()
//                        .addHeader("Authorization", BuildConfig.API_KEY)
//                        .build()
//                it.proceed(request)
//            }
            .build()
    }

    @Provides
    fun provideRetrofit(
        client: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiDetails.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    fun provideApiClient(
        retrofit: Retrofit
    ): ApiEndpoint {
        return retrofit.create(ApiEndpoint::class.java)
    }

    @Provides
    fun provideRepository(
        apiClient: ApiEndpoint
    ): Repository {
        return RepositoryImplementation(apiClient)
    }
}