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

import android.os.Bundle
import android.support.annotation.CallSuper
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

/**
 * Created on   : 05/02/17
 * Author       : muhrifqii
 * Name         : Muhammad Rifqi Fatchurrahman Putra Danar
 * Github       : https://github.com/muhrifqii
 * LinkedIn     : https://linkedin.com/in/muhrifqii
 */
open class FragmentViewModel<TheView>(params: ViewModelParams) where TheView : LifecycleFragmentType {
  private val viewChange: PublishSubject<LifecycleFragmentType> = PublishSubject.create()
  private val view: Observable<TheView> =
      viewChange.filter { it !is EmptyLifecycleFragmentType }.map { it as TheView }
  private val disposables: CompositeDisposable = CompositeDisposable()
  private val arguments: PublishSubject<Bundle> = PublishSubject.create()

  fun setArguments(bundle: Bundle) = arguments.onNext(bundle)
  fun getArguments() = arguments.hide()

  @CallSuper open fun onCreate(savedInstanceState: Bundle?) {
    Timber.d("onCreate $this")
    val x = EmptyLifecycleFragmentType()
    viewChange.onNext(x)
  }

  @CallSuper open fun <TheView : LifecycleFragmentType> onTakeView(view: TheView) {
    Timber.d("onTakeView $this in $view")
    viewChange.onNext(view)
  }

  @CallSuper open fun onDropView() {
    Timber.d("onDropView $this")
    val x = EmptyLifecycleFragmentType()
    viewChange.onNext(x)
  }

  @CallSuper open fun onDetach() {
    Timber.d("onDetach $this")
    disposables.clear()
    viewChange.onComplete()
  }

  /**
   * All observables in a view model must compose `bindToLifecycle()` before calling
   * `subscribe`.
   */
  fun <T> bindToLifecycle() = ViewModelLifecycleTransformer<T>(view)
}