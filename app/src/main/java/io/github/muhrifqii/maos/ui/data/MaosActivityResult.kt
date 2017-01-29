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

package io.github.muhrifqii.maos.ui.data

import android.app.Activity
import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import io.github.muhrifqii.maos.libs.extensions.createParcel

/**
 * Created on   : 23/01/17
 * Author       : muhrifqii
 * Name         : Muhammad Rifqi Fatchurrahman Putra Danar
 * Github       : https://github.com/muhrifqii
 * LinkedIn     : https://linkedin.com/in/muhrifqii
 *
 * Model the activity result
 */
data class MaosActivityResult(var requestCode: Int, var resultCode: Int, var intent: Intent?)
  : Parcelable {
  companion object {
    val CREATOR = createParcel(::MaosActivityResult)
  }

  override fun writeToParcel(p0: Parcel?, p1: Int) {
    p0?.apply {
      writeInt(requestCode)
      writeInt(resultCode)
      writeValue(intent)
//      writeParcelable(intent, Parcelable.PARCELABLE_WRITE_RETURN_VALUE)
    }
  }

  override fun describeContents(): Int = 0

  constructor(parcel: Parcel) : this(
      parcel.readInt(), parcel.readInt(),
      parcel.readValue(Intent::class.java.classLoader) as Intent?
  )

  fun isCancelled(): Boolean = resultCode == Activity.RESULT_CANCELED
  fun isOk(): Boolean = resultCode == Activity.RESULT_OK
  fun isRequestCode(code: Int): Boolean = code == requestCode
}