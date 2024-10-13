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

import com.greenfossil.commons.json.Json
import com.greenfossil.thorium.{*, given}
import com.greenfossil.data.mapping.Mapping.*
import com.linecorp.armeria.server.annotation.{Get, Post}
import model.User

object FormController:
  val form = tuple(
    "name" -> nonEmptyText,
    "age" -> number
  )

  @Get("/viewUser")
  def viewUser(using request: Request) =
    User.getUser match
      case Some(User(name, age)) => s"Hi $name! You are $age years old"
      case None => "No users found."

  //curl -d name=john -d age=50 http://localhost:8080/updateUser
  //curl -H 'Content-Type: application/json' -d '{"name":"john","age":50}'  http://localhost:8080/updateUser
  @Post("/updateUser")
  def updateUser(using request: Request) =
    form.bindFromRequest().fold(
      errorForm => 
        val response = Json.obj(
          "errors" -> errorForm.errors.map { error =>
            Json.obj(
              "key" -> error.key,
              "message" -> error.message
            )
          }
        )
        BadRequest(response)
      ,
      data => 
        User.updateUser.tupled(data)
        Ok("Updated user!")
    )

  //curl -X POST http://localhost:8080/deleteUser
  @Post("/deleteUser")
  def deleteUser(using request: Request) = 
    User.deleteUser
    Ok("User deleted")