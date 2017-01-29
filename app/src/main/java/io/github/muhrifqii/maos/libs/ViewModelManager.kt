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

import android.content.Context
import android.os.Bundle
import io.github.muhrifqii.maos.libs.qualifiers.ApplicationContext
import java.lang.reflect.InvocationTargetException
import java.util.HashMap
import java.util.UUID
import kotlin.reflect.KClass

/**
 * Created on   : 27/01/17
 * Author       : muhrifqii
 * Name         : Muhammad Rifqi Fatchurrahman Putra Danar
 * Github       : https://github.com/muhrifqii
 * LinkedIn     : https://linkedin.com/in/muhrifqii
 */
object ViewModelManager {

  private val KEY_VIEW_MODEL_ID: String = "key-id-view-model"
  private var activityViewModels =
      HashMap<String, ActivityViewModel<out LifecycleTypeActivity>>()
//  private var fragmentViewModels =
//      HashMap<String, FragmentViewModel<out LifecycleTypeFragment>>()

  fun <T : ActivityViewModel<out LifecycleTypeActivity>> findActivity(context: Context,
      viewModelClass: Class<T>, savedInstanceState: Bundle?): T {
    val id = findId(savedInstanceState)
    val activityViewModel: ActivityViewModel<out LifecycleTypeActivity> =
        activityViewModels[id] ?: createActivityViewModel(context, savedInstanceState, id)

    return activityViewModel as T
  }

//  fun <T : FragmentViewModel<out LifecycleTypeFragment>> findFragment(context: Context,
//      viewModelClass: Class<T>,
//      savedInstanceState: Bundle?): T {
//
//    val id = findId(savedInstanceState)
//  }

  private fun findId(state: Bundle?): String {
    return if (state !== null) state.getString(KEY_VIEW_MODEL_ID)
    else UUID.randomUUID().toString()
  }

  private fun createActivityViewModel(@ApplicationContext context: Context, state: Bundle?,
      id: String)
      : ActivityViewModel<out LifecycleTypeActivity> {

}