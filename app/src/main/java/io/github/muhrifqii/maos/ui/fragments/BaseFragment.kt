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

package io.github.muhrifqii.maos.ui.fragments

import android.app.Activity
import android.content.Context
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trello.rxlifecycle2.components.support.RxFragment
import io.github.muhrifqii.maos.libs.FragmentViewModel
import io.github.muhrifqii.maos.libs.LifecycleFragmentType
import io.github.muhrifqii.maos.libs.ViewModelManager
import io.github.muhrifqii.maos.libs.extensions.findMaybeNull
import timber.log.Timber

/**
 * Created on   : 05/02/17
 * Author       : muhrifqii
 * Name         : Muhammad Rifqi Fatchurrahman Putra Danar
 * Github       : https://github.com/muhrifqii
 * LinkedIn     : https://linkedin.com/in/muhrifqii
 */
abstract class BaseFragment<TheViewModel : FragmentViewModel<out LifecycleFragmentType>>
  : RxFragment(), LifecycleFragmentType {

  private val VIEWMODEL_KEY_TO_BUNDLE = "view-model"
  protected var viewModel: TheViewModel? = null

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    Timber.d("onAttach $this to context")
  }

  override fun onAttach(activity: Activity?) {
    super.onAttach(activity)
    Timber.d("onAttach $this to activity")
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Timber.d("onCreate $this")
    attachViewModel(savedInstanceState)
    if (arguments !== null) {
      viewModel?.setArguments(arguments)
    }
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    Timber.d("onCreateView $this")
    return super.onCreateView(inflater, container, savedInstanceState)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    Timber.d("onActivityCreated $this")
  }

  override fun onStart() {
    super.onStart()
    Timber.d("onStart $this")
    if (VERSION.SDK_INT >= VERSION_CODES.N) {
      attachViewModel(null)
      viewModel?.onTakeView(this)
    }
  }

  override fun onResume() {
    super.onResume()
    Timber.d("onResume $this")
    if (VERSION.SDK_INT < VERSION_CODES.N) {
      attachViewModel(null)
      viewModel?.onTakeView(this)
    }
  }

  /**
   * @return The ViewModel java class
   */
  abstract fun viewModelClass(): Class<TheViewModel>

  private fun attachViewModel(bundle: Bundle?) {
    if (viewModel === null) viewModel =
        ViewModelManager.find(context.applicationContext, viewModelClass(),
            bundle.findMaybeNull(VIEWMODEL_KEY_TO_BUNDLE))

  }
}