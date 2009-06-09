/*
 * Copyright 2008 Sanjiv Sahayam
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
package com.googlecode.pinthura.util;

/**
 * Defines a stopwatch. The stop watch must be started to be used. After which the the time and can retrieved in milliseconds and/or seconds.
 * Note that the stopwatch will continue to return the time difference from when @{link #start()} was called.
 *
 * To reset the stop watch simply call @{link #start()} again.
 */
public interface StopWatch {

    /**
     * Starts the stop watch. Also stops a stopwatch that was started before.
     */
    void start();

    /**
     * @return The time differenece between the current moment and the moment when the stopwatch was started, in milliseconds.
     */
    long getTimeInMillis();

    /**
     * @return The time differenece between the current moment and the moment when the stopwatch was started, in seconds.
     */
    double getTimeInSeconds();
}
