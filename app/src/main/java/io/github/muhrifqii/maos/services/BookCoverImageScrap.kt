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

import io.github.muhrifqii.maos.services.AmazonImageCode.DEFAULT

/**
 * Created on   : 01/02/17
 * Author       : muhrifqii
 * Name         : Muhammad Rifqi Fatchurrahman Putra Danar
 * Github       : https://github.com/muhrifqii
 * LinkedIn     : https://linkedin.com/in/muhrifqii
 * http://aaugh.com/imageabuse.html
 */
object BookCoverImageScrap {
  fun fromIsbn10(isbn: String, code: AmazonImageCode = DEFAULT): String {
    return "http://images.amazon.com/images/P/$isbn.01.${code.value}.jpg"
  }
}

enum class AmazonImageCode(val value: String) {
  DEFAULT(""), SMALL("THUMB"), MEDIUM("TZZZ"), LARGE("LZ")
}