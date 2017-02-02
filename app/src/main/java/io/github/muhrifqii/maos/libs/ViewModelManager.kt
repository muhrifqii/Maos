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

  private val KEY_VIEW_MODEL_ID: String = "key-id-view-model"
  private val KEY_VIEW_MODEL_STATE: String = "key-state-view-model"
  private var activityViewModels =
      HashMap<String, ActivityViewModel<out LifecycleActivityType>>()
//  private var fragmentViewModels =
//      HashMap<String, FragmentViewModel<out LifecycleTypeFragment>>()

  /**
   * find ActivityViewModel state on the saved ActivityViewModel map. If state is null, then create
   * a new entry set on ActivityViewModel map
   */
  @Suppress("UNCHECKED_CAST")
  fun <T : ActivityViewModel<out LifecycleActivityType>> findActivity(context: Context,
      viewModelClass: Class<T>, savedInstanceState: Bundle?): T {
    val id = findId(savedInstanceState)
    val activityViewModel: ActivityViewModel<out LifecycleActivityType> =
        activityViewModels[id] ?: createActivityViewModel(context, viewModelClass,
            savedInstanceState, id)

    return activityViewModel as T
  }

  //  fun <T : FragmentViewModel<out LifecycleTypeFragment>> findFragment(context: Context,
//      viewModelClass: Class<T>,
//      savedInstanceState: Bundle?): T {
//
//    val id = findId(savedInstanceState)
//  }

  /**
   * save the ActivityViewModel state to handle lifecycle state changed
   */
  fun <T : ActivityViewModel<out LifecycleActivityType>> saveActivity(viewModel: T,
      savedInstanceState: Bundle?) {
    savedInstanceState?.putString(KEY_VIEW_MODEL_ID, findIdForViewModel(viewModel))
    savedInstanceState?.putBundle(KEY_VIEW_MODEL_STATE, Bundle())
  }

  fun destroy(activityViewModel: ActivityViewModel<*>) {
    activityViewModel.onDestroy()
    activityViewModels.remove { it.value == activityViewModel }
  }

  private fun findId(state: Bundle?): String {
    return if (state !== null) state.getString(KEY_VIEW_MODEL_ID)
    else UUID.randomUUID().toString()
  }

  private fun findIdForViewModel(activityViewModel: ActivityViewModel<*>): String {
    try {
      return activityViewModels.entries.find { it.value == activityViewModel }?.key!!
    } catch (ex: NullPointerException) {
      throw RuntimeException("No view model in the map!")
    }
  }

  private fun <T : ActivityViewModel<out LifecycleActivityType>> createActivityViewModel(
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
    viewModel.onCreate(state.findMaybeNull(KEY_VIEW_MODEL_STATE))

    return viewModel
  }
}