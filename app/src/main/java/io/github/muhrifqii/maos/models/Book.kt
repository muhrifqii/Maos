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

package io.github.muhrifqii.maos.models

/**
 * Created on   : 03/02/17
 * Author       : muhrifqii
 * Name         : Muhammad Rifqi Fatchurrahman Putra Danar
 * Github       : https://github.com/muhrifqii
 * LinkedIn     : https://linkedin.com/in/muhrifqii
 */
data class Book(
    //    val dewey_decimal: String,
    //    val notes: String,
    //    val lcc_number:String,
    val book_id: String,
    val isbn10: String,
    val isbn13: String,
    val title: String,
    val title_latin: String,
    val title_long: String,
    val author_data: List<AuthorData>,
    val publisher_id: String,
    val publisher_text: String,
    val publisher_name: String,
    val edition_info: String,
    val summary: String,
    val physical_description_text: String,
    val urls_text: String,
    val awards_text: String,
    val language: String,
    val dewey_normal: Float
)

data class AuthorData(val name: String, val id: String)