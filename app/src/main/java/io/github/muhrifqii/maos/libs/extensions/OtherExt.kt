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

package io.github.muhrifqii.maos.libs.extensions

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator

/**
 * Created on   : 29/01/17
 * Author       : muhrifqii
 * Name         : Muhammad Rifqi Fatchurrahman Putra Danar
 * Github       : https://github.com/muhrifqii
 * LinkedIn     : https://linkedin.com/in/muhrifqii
 */

/**
 * Minimize Parcelable boilerplate of a creator object implementation
 */
inline fun <reified T : Parcelable> createParcel(crossinline createFromParcel: (Parcel) -> T?)
    : Parcelable.Creator<T> = object : Creator<T> {
  override fun createFromParcel(p0: Parcel?): T = createFromParcel(p0)

  override fun newArray(p0: Int): Array<out T?> = kotlin.arrayOfNulls(p0)
}

/**
 * Optional find to a particular key on Bundle
 * return optional value
 */
inline fun <reified T> Bundle?.findMaybeNull(key: String): T? {
  if (this === null) {
    return null
  }
  return when (T::class) {
    Boolean::class -> getBoolean(key) as T
    BooleanArray::class -> getBooleanArray(key) as T
    String::class -> getString(key) as T
    Array<out String>::class -> getStringArray(key) as T
    Int::class -> getInt(key) as T
    IntArray::class -> getIntArray(key) as T
    Double::class -> getDouble(key) as T
    Bundle::class -> getBundle(key) as T
    else -> throw RuntimeException("not yet handled")
  }
}