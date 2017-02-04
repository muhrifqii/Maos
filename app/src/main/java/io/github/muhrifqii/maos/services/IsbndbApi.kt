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

package io.github.muhrifqii.maos.services

import com.serjltt.moshi.adapters.FirstElement
import com.serjltt.moshi.adapters.Wrapped
import io.github.muhrifqii.maos.models.Book
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created on   : 03/02/17
 * Author       : muhrifqii
 * Name         : Muhammad Rifqi Fatchurrahman Putra Danar
 * Github       : https://github.com/muhrifqii
 * LinkedIn     : https://linkedin.com/in/muhrifqii
 */
interface IsbndbApi {
  /**
   * @param key isbn10 or isbn13 or isbndb_id
   */
  @GET("/book/{key}") // need to test this
  @Wrapped("data") @FirstElement fun aBook(@Path("key") key: String) : Observable<Book>

  /**
   * @param query a search query
   * @param type null for query by title
   * @param page null for page 1
   */
  @GET("/books")
  @Wrapped("data") fun booksBy(@Query("q") query: String,
      @Query("i") type: String? = null,
      @Query("p") page: Int? = null) : Observable<List<Book>>
}