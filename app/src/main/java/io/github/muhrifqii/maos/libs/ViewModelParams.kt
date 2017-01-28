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

import android.os.Parcel
import android.os.Parcelable

/**
 * Created on   : 28/01/17
 * Author       : muhrifqii
 * Name         : Muhammad Rifqi Fatchurrahman Putra Danar
 * Github       : https://github.com/muhrifqii
 * LinkedIn     : https://linkedin.com/in/muhrifqii
 *
 * Wrapper for view model parameter in a parcelable BOILERPLATE!
 */
data class ViewModelParams(var x: Int) : Parcelable {
  companion object {
    @JvmField val CREATOR: Parcelable.Creator<ViewModelParams> =
        object : Parcelable.Creator<ViewModelParams> {
          override fun createFromParcel(src: Parcel): ViewModelParams = ViewModelParams(src)

          override fun newArray(size: Int): Array<out ViewModelParams> = newArray(size)
        }
  }

  constructor(source: Parcel) : this(source.readInt())

  override fun writeToParcel(destination: Parcel?, flag: Int) {
    destination?.writeInt(x)
  }

  override fun describeContents(): Int = 0
}