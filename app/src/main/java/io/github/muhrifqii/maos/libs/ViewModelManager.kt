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
import io.github.muhrifqii.maos.MaosApplication
import io.github.muhrifqii.maos.libs.extensions.findMaybeNull
import io.github.muhrifqii.maos.libs.extensions.remove
import io.github.muhrifqii.maos.libs.qualifiers.ApplicationContext
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.util.HashMap
import java.util.UUID

/**
 * Created on   : 27/01/17
 * Author       : muhrifqii
 * Name         : Muhammad Rifqi Fatchurrahman Putra Danar
 * Github       : https://github.com/muhrifqii
 * LinkedIn     : https://linkedin.com/in/muhrifqii
 */
object ViewModelManager {

  private val KEY_ACTIVITY_VIEW_MODEL_ID: String = "key-id-activity-view-model"
  private val KEY_FRAGMENT_VIEW_MODEL_ID: String = "key-id-fragment-view-model"
  private val KEY_ACTIVITY_VIEW_MODEL_STATE: String = "key-state-activity-view-model"
  private val KEY_FRAGMENT_VIEW_MODEL_STATE: String = "key-state-fragment-view-model"
  private var activityViewModels =
      HashMap<String, ActivityViewModel<out LifecycleActivityType>>()
  private var fragmentViewModels =
      HashMap<String, FragmentViewModel<out LifecycleFragmentType>>()

  /**
   * find ActivityViewModel state on the saved ActivityViewModel map. If state is null, then create
   * a new entry set on ActivityViewModel map
   */
  @Suppress("UNCHECKED_CAST")
  fun <T : ActivityViewModel<out LifecycleActivityType>> find(context: Context,
      viewModelClass: Class<T>, savedInstanceState: Bundle?): T {
    val id = findId(savedInstanceState, KEY_ACTIVITY_VIEW_MODEL_ID)
    val viewModel: ActivityViewModel<out LifecycleActivityType> =
        activityViewModels[id] ?: createViewModel(context, viewModelClass,
            savedInstanceState, id)

    return viewModel as T
  }

  /**
   * find FragmentViewModel state on the saved FragmentViewModel map. If state is null, then create
   * a new entry set on FragmentViewModel map
   */
  @Suppress("UNCHECKED_CAST")
  fun <T : FragmentViewModel<out LifecycleFragmentType>> find(context: Context,
      viewModelClass: Class<T>, savedInstanceState: Bundle?): T {
    val id = findId(savedInstanceState, KEY_FRAGMENT_VIEW_MODEL_ID)
    val viewModel: FragmentViewModel<out LifecycleFragmentType> =
        fragmentViewModels[id] ?: createViewModel(context, viewModelClass,
            savedInstanceState, id)

    return viewModel as T
  }

  /**
   * save the ActivityViewModel state to handle lifecycle state changed
   */
  fun <T : ActivityViewModel<out LifecycleActivityType>> save(viewModel: T,
      savedInstanceState: Bundle?) {
    savedInstanceState?.putString(KEY_ACTIVITY_VIEW_MODEL_ID, findIdForViewModel(viewModel))
    savedInstanceState?.putBundle(KEY_ACTIVITY_VIEW_MODEL_STATE, Bundle())
  }

  /**
   * save the FragmentViewModel state to handle lifecycle state changed
   */
  fun <T : FragmentViewModel<out LifecycleFragmentType>> save(viewModel: T,
      savedInstanceState: Bundle?) {
    savedInstanceState?.putString(KEY_FRAGMENT_VIEW_MODEL_ID, findIdForViewModel(viewModel))
    savedInstanceState?.putBundle(KEY_FRAGMENT_VIEW_MODEL_STATE, Bundle())
  }

  fun destroy(activityViewModel: ActivityViewModel<*>) {
    activityViewModel.onDestroy()
    activityViewModels.remove { it.value == activityViewModel }
  }
  fun destroy(fragmentViewModel: FragmentViewModel<*>) {
    fragmentViewModel.onDetach()
    activityViewModels.remove { it.value == fragmentViewModel }
  }

  private fun findId(state: Bundle?, keyId: String): String {
    return if (state !== null) state.getString(keyId)
    else UUID.randomUUID().toString()
  }

  private fun findIdForViewModel(activityViewModel: ActivityViewModel<*>): String {
    try {
      return activityViewModels.entries.find { it.value == activityViewModel }!!.key
    } catch (ex: NullPointerException) {
      throw RuntimeException("No view model in the map!")
    }
  }

  private fun findIdForViewModel(fragmentViewModel: FragmentViewModel<*>): String {
    try {
      return fragmentViewModels.entries.find { it.value == fragmentViewModel }!!.key
    } catch (ex: NullPointerException) {
      throw RuntimeException("No view model in the map!")
    }
  }

  private fun <T : ActivityViewModel<out LifecycleActivityType>> createViewModel(
      @ApplicationContext context: Context, clazz: Class<T>, state: Bundle?, id: String)
      : ActivityViewModel<out LifecycleActivityType> {
    val params = (context as MaosApplication).component.viewModelParams()
    val viewModel: ActivityViewModel<out LifecycleActivityType>

    try {
      val constructor = clazz.getConstructor(ViewModelParams::class.java)
      viewModel = constructor!!.newInstance(params) as ActivityViewModel<out LifecycleActivityType>
    } catch (ex: NullPointerException) {
      throw RuntimeException(ex) // if the constructor null
    } catch (ex: IllegalAccessException) {
      throw RuntimeException(ex) // exception from newInstance(params)
    } catch (ex: IllegalArgumentException) {
      throw RuntimeException(ex) // exception from newInstance(params)
    } catch (ex: InstantiationException) {
      throw RuntimeException(ex) // exception from newInstance(params)
    } catch (ex: InvocationTargetException) {
      throw RuntimeException(ex) // exception from newInstance(params)
    } catch (ex: ExceptionInInitializerError) {
      throw RuntimeException(ex) // exception from newInstance(params)
    }
    activityViewModels.put(id, viewModel)
    viewModel.onCreate(state.findMaybeNull(KEY_ACTIVITY_VIEW_MODEL_STATE))

    return viewModel
  }

  private fun <T : FragmentViewModel<out LifecycleFragmentType>> createViewModel(
      @ApplicationContext context: Context, clazz: Class<T>, state: Bundle?, id: String)
      : FragmentViewModel<out LifecycleFragmentType> {
    val params = (context as MaosApplication).component.viewModelParams()
    val viewModel: FragmentViewModel<out LifecycleFragmentType>

    try {
      val constructor = clazz.getConstructor(ViewModelParams::class.java)
      viewModel = constructor!!.newInstance(params) as FragmentViewModel<out LifecycleFragmentType>
    } catch (ex: NullPointerException) {
      throw RuntimeException(ex) // if the constructor null
    } catch (ex: IllegalAccessException) {
      throw RuntimeException(ex) // exception from newInstance(params)
    } catch (ex: IllegalArgumentException) {
      throw RuntimeException(ex) // exception from newInstance(params)
    } catch (ex: InstantiationException) {
      throw RuntimeException(ex) // exception from newInstance(params)
    } catch (ex: InvocationTargetException) {
      throw RuntimeException(ex) // exception from newInstance(params)
    } catch (ex: ExceptionInInitializerError) {
      throw RuntimeException(ex) // exception from newInstance(params)
    }
    fragmentViewModels.put(id, viewModel)
    viewModel.onCreate(state.findMaybeNull(KEY_FRAGMENT_VIEW_MODEL_STATE))

    return viewModel
  }
}