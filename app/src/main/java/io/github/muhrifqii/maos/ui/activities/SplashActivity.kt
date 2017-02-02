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

package io.github.muhrifqii.maos.ui.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.github.muhrifqii.maos.R.layout
import io.github.muhrifqii.maos.libs.extensions.intentToClass
import io.github.muhrifqii.maos.viewmodels.SplashViewModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit.MILLISECONDS

/**
 * Created on   : 21/01/17
 * Author       : muhrifqii
 * Name         : Muhammad Rifqi Fatchurrahman Putra Danar
 * Github       : https://github.com/muhrifqii
 * LinkedIn     : https://linkedin.com/in/muhrifqii
 */

class SplashActivity : BaseActivity<SplashViewModel>() {

  val subs: CompositeDisposable = CompositeDisposable()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_splash)

    subs.add(Completable.timer(1500, MILLISECONDS, Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(complete(), error()))
  }

  override fun onDestroy() {
    super.onDestroy()
    subs.clear()
  }

  override fun viewModelClass(): Class<SplashViewModel> {
    return SplashViewModel::class.java
  }

  override fun finishActivityTransition(): Pair<Int, Int>? {
    return null
  }

  fun complete(): () -> Unit = {
    // logic goes here
    startActivity(intentToClass(MainActivity::class))
    finish()
  }

  fun error(): (Throwable) -> Unit = { Timber.e(it) }
}
