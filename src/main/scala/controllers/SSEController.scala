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

import com.greenfossil.thorium.Request
import com.linecorp.armeria.common.ResponseHeaders
import com.linecorp.armeria.server.annotation.Get
import com.linecorp.armeria.server.streaming.ServerSentEvents
import model.TimestampSSE
import reactor.core.publisher.Sinks

import java.time.LocalDateTime
import java.util.concurrent.CompletableFuture

object SSEController {
  val sink = Sinks.many().multicast().directAllOrNothing[TimestampSSE]()
  val flux = sink.asFlux()

  @Get("/sse/subscribe")
  def subscribeToSSE(using request: Request) = {
    ServerSentEvents
      .fromPublisher(
        ResponseHeaders.of(200),
        flux.doOnSubscribe{_ =>
          CompletableFuture.runAsync(() =>
            (1 to 5).foreach{_ =>
              sink.tryEmitNext(TimestampSSE(LocalDateTime.now(), "ping"))
              Thread.sleep(1000)
            }
          )
        }
      )
  }

  @Get("/sse/sendEvent")
  def sendEvent(using request: Request) = {
    val event = TimestampSSE(LocalDateTime.now(), "user-initiated")
    sink.tryEmitNext(event)
    s"Event Id: ${event.id}"
  }


}
