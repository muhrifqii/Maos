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

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import io.github.muhrifqii.maos.R
import io.github.muhrifqii.maos.libs.LifecycleFragmentType
import io.github.muhrifqii.maos.libs.extensions.inflate
import io.github.muhrifqii.maos.viewmodels.MainFragmentViewModel

/**
 * Created on   : 24/01/17
 * Author       : muhrifqii
 * Name         : Muhammad Rifqi Fatchurrahman Putra Danar
 * Github       : https://github.com/muhrifqii
 * LinkedIn     : https://linkedin.com/in/muhrifqii
 */
class MainFragment(position: Int) : BaseFragment<MainFragmentViewModel>(), LifecycleFragmentType {

  val KEY_ARGS_POSITION = "key-args-to-position"
  @BindView(R.id.home_swipe_refresh_layout) lateinit var swipeRefreshLayout: SwipeRefreshLayout
  @BindView(R.id.recycler_view) lateinit var recyclerView: RecyclerView

  init {
    arguments = Bundle().apply {
      putInt(KEY_ARGS_POSITION, position)
    }
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    val view = container!!.inflate(R.layout.home_fragment)
    return view
  }

  override fun viewModelClass(): Class<MainFragmentViewModel> {
    return MainFragmentViewModel::class.java
  }
}