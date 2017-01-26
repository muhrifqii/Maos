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

import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.BackpressureStrategy.LATEST
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
import timber.log.Timber
import java.util.concurrent.CancellationException

/**
 * Created on   : 25/01/17
 * Author       : muhrifqii
 * Name         : Muhammad Rifqi Fatchurrahman Putra Danar
 * Github       : https://github.com/muhrifqii
 * LinkedIn     : https://linkedin.com/in/muhrifqii
 *
 * Custom lifecycleStream transformation to support viewmodel
 * @param lifecycleStream is Observable
 */
class ViewModelLifecycleTransformer<Stream>(
    private val lifecycleStream: Observable<out LifecycleType<out Any>>) :
    ObservableTransformer<Stream, Stream>, FlowableTransformer<Stream, Stream>,
    MaybeTransformer<Stream, Stream>, SingleTransformer<Stream, Stream>, CompletableTransformer {

  override fun apply(upstream: Observable<Stream>): ObservableSource<Stream> {
    return upstream.takeUntil(lifecycle())
  }

  override fun apply(upstream: Flowable<Stream>): Publisher<Stream> {
    return upstream.takeUntil(lifecycle().toFlowable(LATEST))
  }

  override fun apply(upstream: Maybe<Stream>): MaybeSource<Stream> {
    return upstream.takeUntil(lifecycle().firstElement())
  }

  override fun apply(upstream: Single<Stream>): SingleSource<Stream> {
    return upstream.takeUntil(lifecycle().firstOrError())
  }

  override fun apply(upstream: Completable): CompletableSource {
    return Completable.ambArray(upstream,
        lifecycle().flatMapCompletable { Completable.error(CancellationException()) })
    // remember that flatmap returns all stream from flatten function
  }

  override fun hashCode(): Int {
    return lifecycleStream.hashCode()
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other === null || other.javaClass != javaClass) return false
    return lifecycleStream == (other as ViewModelLifecycleTransformer<*>).lifecycleStream
  }

  private fun lifecycle() = lifecycleStream.switchMap { it.lifecycle() }.filter {
    when (it) {
      is ActivityEvent -> it === ActivityEvent.DESTROY
      is FragmentEvent -> it === FragmentEvent.DETACH
      else -> {
        Timber.e("error")
        false
      }
    }
  }
}