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
import com.trello.rxlifecycle2.android.ActivityEvent
import io.github.muhrifqii.maos.ui.data.MyActivityResult
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

/**
 * Created on   : 23/01/17
 * Author       : muhrifqii
 * Name         : Muhammad Rifqi Fatchurrahman Putra Danar
 * Github       : https://github.com/muhrifqii
 * LinkedIn     : https://linkedin.com/in/muhrifqii
 */
open class ActivityViewModel<VIEW : LifecycleClue<ActivityEvent>> {

  val viewChange: PublishSubject<VIEW> = PublishSubject.create()
  val view: Observable<VIEW> = viewChange.filter { it != null }
  val disposables: CompositeDisposable = CompositeDisposable()
  val activityResult: PublishSubject<MyActivityResult> = PublishSubject.create()
  val intent: PublishSubject<Intent> = PublishSubject.create()


}