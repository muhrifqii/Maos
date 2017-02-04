/*
 *    Copyright 2017 Muhammad Rifqi Fatchurrahman Putra Danar
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.github.muhrifqii.maos

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import android.preference.PreferenceManager
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.serjltt.moshi.adapters.FirstElementJsonAdapter
import com.serjltt.moshi.adapters.WrappedJsonAdapter
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import io.github.muhrifqii.maos.libs.Secrets
import io.github.muhrifqii.maos.libs.ViewModelParams
import io.github.muhrifqii.maos.libs.qualifiers.ApplicationContext
import io.github.muhrifqii.maos.libs.qualifiers.IsbndbRetrofit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Created on   : 23/01/17
 * Author       : muhrifqii
 * Name         : Muhammad Rifqi Fatchurrahman Putra Danar
 * Github       : https://github.com/muhrifqii
 * LinkedIn     : https://linkedin.com/in/muhrifqii
 */

@Module
class AppModule(val app: MaosApplication) {

  @Provides @Singleton @ApplicationContext
  fun provideContext(): Context = app

  @Provides @Singleton
  fun provideApplication(): Application = app

  @Provides @Singleton
  fun provideAssetManager(): AssetManager = app.assets

  @Provides @Singleton
  fun providePackageInfo(): PackageInfo = app.packageManager
      .getPackageInfo(app.packageName, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT)

  @Provides @Singleton
  fun provideResources(): Resources = app.resources

  @Provides @Singleton
  fun provideSharedPreferences(): SharedPreferences =
      PreferenceManager.getDefaultSharedPreferences(app)

  @Provides @Singleton
  fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
      HttpLoggingInterceptor().setLevel(BODY) // log body and header

  @Provides @Singleton
  fun provideMoshi(): Moshi = Moshi.Builder().apply {
    add(WrappedJsonAdapter.FACTORY)
    add(FirstElementJsonAdapter.FACTORY)
  }.build()

  @Provides @Singleton
  fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
      OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) addInterceptor(httpLoggingInterceptor)
      }.build() // network log ez

  @Provides @Singleton @IsbndbRetrofit
  fun provideIsbndbRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit =
      createRetrofit("http://isbndb.com/api/v2/json/${Secrets.ISBNDB_API_KEY}", okHttpClient, moshi)

  @Provides @Singleton
  fun provideViewModelParams(sharedPreferences: SharedPreferences): ViewModelParams =
      ViewModelParams(
          x = 0,
          sharedPreferences = sharedPreferences
      )

  private fun createRetrofit(baseUrl: String, okHttpClient: OkHttpClient, moshi: Moshi): Retrofit =
      Retrofit.Builder().apply {
        client(okHttpClient)
        baseUrl(baseUrl)
        addConverterFactory(MoshiConverterFactory.create(moshi))
        addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      }.build()
}