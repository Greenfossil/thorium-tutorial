/*
 * Copyright 2022 Greenfossil Pte Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers

import com.greenfossil.thorium.{Redirect, Request}
import com.linecorp.armeria.server.annotation.{Get, Param}

object SimpleController:
  @Get("/sayHello/:name")
  def sayHello(@Param name: String)(using request: Request) =
    s"Hello $name"

  @Get("/redirect")
  def redirect(using request: Request) =
    Redirect(sayHello("User"))

  @Get("regex:^/plusOne/(?<number>\\d+)")
  def plusOne(@Param number: Int)(using request: Request) =
    s"${number + 1}"
