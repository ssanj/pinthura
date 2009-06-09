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

public final class StopWatchImpl implements StopWatch {

    private final CurrentTimeRetriever currentTimeRetriever;
    private long startTime = -1;

    public StopWatchImpl(final CurrentTimeRetriever currentTimeRetriever) {
        this.currentTimeRetriever = currentTimeRetriever;
    }

    @Override
    public void start() {
        startTime = currentTimeRetriever.getCurrentTimeInMillis();
    }

    @Override
    public long getTimeInMillis() {
        if (hasStarted()) {
            return currentTimeRetriever.getCurrentTimeInMillis() - startTime;
        }

        throw new IllegalStateException("The stopwatch must be started before the time is requested.");
    }

    @Override
    public double getTimeInSeconds() {
        return getTimeInMillis() / 1000d;
    }
    private boolean hasStarted() {
        return startTime != -1;
    }
}
