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
import android.os.Bundle
import android.support.annotation.CallSuper
import com.trello.rxlifecycle2.android.ActivityEvent
import io.github.muhrifqii.maos.ui.data.MaosActivityResult
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

/**
 * Created on   : 23/01/17
 * Author       : muhrifqii
 * Name         : Muhammad Rifqi Fatchurrahman Putra Danar
 * Github       : https://github.com/muhrifqii
 * LinkedIn     : https://linkedin.com/in/muhrifqii
 */
open class ActivityViewModel<TheView>(params: ViewModelParams) // enables reflection on subclass
where TheView : LifecycleActivityType {

  private val viewChange: PublishSubject<LifecycleActivityType> = PublishSubject.create()
  private val view: Observable<TheView> =
      viewChange.filter { it !is EmptyLifecycleActivityType }.map { it as TheView }
  private val activityResult: PublishSubject<MaosActivityResult> = PublishSubject.create()
  private val intent: PublishSubject<Intent> = PublishSubject.create()
  protected val disposables: CompositeDisposable = CompositeDisposable()

  fun setActivityResult(activityResult: MaosActivityResult) =
      this.activityResult.onNext(activityResult)

  fun setIntent(intent: Intent) = this.intent.onNext(intent)

  protected fun getActivityResult() = activityResult.hide()
  protected fun getIntent() = intent.hide()

  /**
   * lifecycle start, but viewmodel should not be started yet
   */
  @CallSuper open fun onCreate(savedInstanceState: Bundle?) {
    Timber.d("onCreate %s", this.toString())
    val x = EmptyLifecycleActivityType()
    viewChange.onNext(x)
  }

  /**
   * begin to change the view
   */
  @CallSuper open fun <TheView : LifecycleActivityType> onTakeView(view: TheView) {
    Timber.d("onTakeView %s in %s", this.toString(), view.toString())
    viewChange.onNext(view)
  }

  /**
   * stop the view with an empty type of view
   */
  @CallSuper open fun onDropView() {
    Timber.d("onDropView %s", this.toString())
    val x = EmptyLifecycleActivityType()
    viewChange.onNext(x)
  }

  @CallSuper open fun onDestroy() {
    Timber.d("onDestroy %s", this.toString())
    disposables.clear()
    viewChange.onComplete()
  }

  /**
   * All observables in a view model must compose `bindToLifecycle()` before calling
   * `subscribe`.
   */
  fun <T> bindToLifecycle() = ViewModelLifecycleTransformer<T>(view)
}


