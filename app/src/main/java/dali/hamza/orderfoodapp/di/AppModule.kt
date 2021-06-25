package dali.hamza.orderfoodapp.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dali.hamza.core.datasource.network.AppClientApi
import dali.hamza.orderfoodapp.R
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(
    SingletonComponent::class
)
object AppModule {

    @Provides
    @Named("dbname")
    fun provideDbName(application: Application) =
        application.resources.getString(R.string.db_name)


//    @Provides
//    @Named("token")
//    fun provideTokenApp(application: Application) =
//        application.getString(R.string.token)


    @Provides
    fun provideBaseUrl(application: Application) =
        application.getString(R.string.server)


    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient
            .Builder()
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        BASE_URL: String,
        // moshiConverter: MoshiConverterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        //.addConverterFactory(moshiConverter)
        .build()

    @Singleton
    @Provides
    fun provideClientApi(
        retrofit: Retrofit
    ): AppClientApi = retrofit.create(AppClientApi::class.java)

}