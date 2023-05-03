package com.elsa.giantbomb.modules

import com.elsa.giantbomb.repository.GameRepository
import com.elsa.giantbomb.repository.apis.GameAPI
import com.elsa.giantbomb.utility.Constants
import com.elsa.giantbomb.viewModels.MainViewModel
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { provideRetrofit(get(), get()) }
    single { provideOkHttpClient() }
    single { provideMoshi() }
    single { provideAPI(get()) }
}

val repositoryModule = module {
    single { GameRepository(get()) }
}

val viewModelModule = module {
    single { MainViewModel(get()) }
}

/**
 * @return [Retrofit] instance
 */
private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    moshi: Moshi
): Retrofit = Retrofit.Builder()
    .baseUrl(Constants.BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .client(okHttpClient)
    .build()

/**
 * @return [OkHttpClient] instance
 */
private fun provideOkHttpClient() = OkHttpClient.Builder()
    .readTimeout(10L, TimeUnit.SECONDS)
    .addInterceptor(
        HttpLoggingInterceptor().apply{
            level = HttpLoggingInterceptor.Level.BODY
        }
    )
    .build()

/**
 * @return [Moshi] instance
 */
fun provideMoshi(): Moshi = Moshi.Builder().build()

/**
 * @return [GameAPI] instance
 */
fun provideAPI(retrofit: Retrofit): GameAPI = retrofit.create(GameAPI::class.java)