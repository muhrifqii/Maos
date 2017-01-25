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

import io.reactivex.Completable
import io.reactivex.CompletableSource
import io.reactivex.CompletableTransformer
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.Maybe
import io.reactivex.MaybeSource
import io.reactivex.MaybeTransformer
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.SingleTransformer
import org.reactivestreams.Publisher

/**
 * Created on   : 25/01/17
 * Author       : muhrifqii
 * Name         : Muhammad Rifqi Fatchurrahman Putra Danar
 * Github       : https://github.com/muhrifqii
 * LinkedIn     : https://linkedin.com/in/muhrifqii
 *
 * Custom stream transformation to support viewmodel
 */
class ViewModelLifecycleTransformer<Event, TheView>(private val stream: Observable<TheView>) :
    ObservableTransformer<Event, Event>, FlowableTransformer<Event, Event>,
    MaybeTransformer<Event, Event>, SingleTransformer<Event, Event>, CompletableTransformer {

  override fun apply(upstream: Observable<Event>): ObservableSource<Event> {
    upstream.takeUntil(stream.switchMap{it})
  }

  override fun apply(upstream: Flowable<Event>): Publisher<Event> {
  }

  override fun apply(upstream: Maybe<Event>): MaybeSource<Event> {
  }

  override fun apply(upstream: Single<Event>): SingleSource<Event> {
  }

  override fun apply(upstream: Completable): CompletableSource {
  }
}