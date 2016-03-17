/*
 * -\-\-
 * Spotify Apollo Metrics Module
 * --
 * Copyright (C) 2013 - 2016 Spotify AB
 * --
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * -/-/-
 */
package com.spotify.apollo.metrics;

import com.spotify.apollo.Client;
import com.spotify.apollo.Request;
import com.spotify.apollo.Response;
import com.spotify.apollo.request.TrackedOngoingRequest;

import java.util.concurrent.CompletionStage;

import okio.ByteString;

/**
 * A {@link Client} that collects metrics on outgoing calls
 */
class InstrumentingClient implements Client {

  private final Client delegate;
  private final TrackedOngoingRequest incomingRequest;

  InstrumentingClient(Client delegate, TrackedOngoingRequest incomingRequest) {
    this.incomingRequest = incomingRequest;
    this.delegate = delegate;
  }

  @Override
  public CompletionStage<Response<ByteString>> send(Request request) {
    incomingRequest.incrementDownstreamRequests();

    return delegate.send(request);
  }
}
