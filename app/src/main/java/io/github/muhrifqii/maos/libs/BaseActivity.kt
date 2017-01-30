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

package io.github.muhrifqii.maos.libs

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.CallSuper
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.github.muhrifqii.maos.AppComponent
import io.github.muhrifqii.maos.MaosApplication
import io.github.muhrifqii.maos.libs.extensions.find
import io.github.muhrifqii.maos.ui.data.MaosActivityResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

/**
 * Created on   : 24/01/17
 * Author       : muhrifqii
 * Name         : Muhammad Rifqi Fatchurrahman Putra Danar
 * Github       : https://github.com/muhrifqii
 * LinkedIn     : https://linkedin.com/in/muhrifqii
 *
 * All ViewModel and lifecycle handling in here
 */
abstract class BaseActivity<TheViewModel : ActivityViewModel<out LifecycleTypeActivity>>
  : RxAppCompatActivity(), LifecycleTypeActivity {

  private val VIEWMODEL_KEY_TO_BUNDLE = "view-model"
  private val back: PublishSubject<Unit> = PublishSubject.create()
  protected var viewModel: TheViewModel? = null

  /**
   * lifecycle start, but viewmodel should not be started yet
   */
  @CallSuper override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Timber.d("OnCreate on %s", this.toString())
    attachViewModel(savedInstanceState)
    viewModel?.setIntent(intent)
  }

  @CallSuper override fun onStart() {
    super.onStart()
    Timber.d("OnStart on %s", this.toString())
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      viewModel?.onDropView()
    }
    back.bindToLifecycle(this).observeOn(AndroidSchedulers.mainThread())
        .subscribe { back() }
  }

  @CallSuper override fun onResume() {
    super.onResume()
    Timber.d("OnResume on %s", this.toString())
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
      viewModel?.onTakeView(this)
    }
  }

  @CallSuper override fun onPause() {
    super.onPause()
    Timber.d("OnPause on %s", this.toString())
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
      viewModel?.onDropView()
    }
  }

  @CallSuper override fun onStop() {
    super.onStop()
    Timber.d("OnStop on %s", this.toString())
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      viewModel?.onDropView()
    }
  }

  @CallSuper override fun onDestroy() {
    super.onDestroy()
    Timber.d("OnDestroy on %s", this.toString())
  }

  override fun onSaveInstanceState(outState: Bundle?) {
    super.onSaveInstanceState(outState)
  }

  override fun onNewIntent(intent: Intent?) {
    super.onNewIntent(intent)
    viewModel?.setIntent(intent!!)
  }

  @CallSuper override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    viewModel?.setActivityResult(MaosActivityResult(requestCode, resultCode, data))
  }

  override fun onBackPressed() = back.onNext(Unit)

  /**
   * @return The ViewModel java class
   */
  abstract fun viewModelClass(): Class<TheViewModel>

  /**
   * @return enterAnim, exitAnim
   */
  abstract fun transition(): Pair<Int, Int>

  /**
   * @return Dagger component
   */
  protected fun appComponent() = (application as MaosApplication).component

  private fun back() {
    super.onBackPressed()
    val (transitionIn, transitionOut) = transition()
    overridePendingTransition(transitionIn, transitionOut)
  }

  private fun attachViewModel(bundle: Bundle?) {
    if (viewModel === null) viewModel = ViewModelManager
        .findActivity(applicationContext, viewModelClass(), bundle.find(VIEWMODEL_KEY_TO_BUNDLE))

  }
}