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

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

public final class AStopWatchUnderTest {

    private IMocksControl mockControl;
    private StopWatch stopWatch;
    private CurrentTimeRetriever mockCurrentTimeRetriever;

    @Before
    public void setup() {
        mockControl = EasyMock.createControl();
        mockCurrentTimeRetriever = mockControl.createMock(CurrentTimeRetriever.class);
        stopWatch = new StopWatchImpl(mockCurrentTimeRetriever);
    }

    @Test
    public void shouldReturnTheTimeAfterStarting() {
        EasyMock.expect(mockCurrentTimeRetriever.getCurrentTimeInMillis()).andReturn(2000l);
        EasyMock.expect(mockCurrentTimeRetriever.getCurrentTimeInMillis()).andReturn(3000l);
        mockControl.replay();

        stopWatch.start();
        assertThat(stopWatch.getTimeInMillis(), equalTo(1000l));

        mockControl.verify();
    }

    @Test
    public void shouldReturnTheTimeMultipleTimesAfterStarting() {
        EasyMock.expect(mockCurrentTimeRetriever.getCurrentTimeInMillis()).andReturn(1000l);
        EasyMock.expect(mockCurrentTimeRetriever.getCurrentTimeInMillis()).andReturn(5000l);
        EasyMock.expect(mockCurrentTimeRetriever.getCurrentTimeInMillis()).andReturn(5500l);
        EasyMock.expect(mockCurrentTimeRetriever.getCurrentTimeInMillis()).andReturn(5600l);
        mockControl.replay();

        stopWatch.start();
        assertThat(stopWatch.getTimeInMillis(), equalTo(4000l));
        assertThat(stopWatch.getTimeInMillis(), equalTo(4500l));
        assertThat(stopWatch.getTimeInMillis(), equalTo(4600l));

        mockControl.verify();
    }

    @Test
    public void shouldThrowAnExceptionIfTheTimeIsRequestedInMillisBeforeStarting() {
        mockControl.replay();

        try {
            stopWatch.getTimeInMillis();
            fail("Expected IllegalStateException.");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), equalTo("The stopwatch must be started before the time is requested."));
            mockControl.verify();
        }
    }

    @Test
    public void shouldThrowAnExceptionIfTheTimeIsRequestedInSecondsBeforeStarting() {
        mockControl.replay();

        try {
            stopWatch.getTimeInSeconds();
            fail("Expected IllegalStateException.");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), equalTo("The stopwatch must be started before the time is requested."));
            mockControl.verify();
        }
    }

    @Test
    public void shouldResetTheTimeIfStartIsCalledAgain() {
        EasyMock.expect(mockCurrentTimeRetriever.getCurrentTimeInMillis()).andReturn(0l);
        EasyMock.expect(mockCurrentTimeRetriever.getCurrentTimeInMillis()).andReturn(200l);
        EasyMock.expect(mockCurrentTimeRetriever.getCurrentTimeInMillis()).andReturn(1l);
        EasyMock.expect(mockCurrentTimeRetriever.getCurrentTimeInMillis()).andReturn(600l);
        mockControl.replay();

        stopWatch.start();//0
        assertThat(stopWatch.getTimeInMillis(), equalTo(200l));
        stopWatch.start();//1
        assertThat(stopWatch.getTimeInMillis(), equalTo(599l));

        mockControl.verify();
    }

    @Test
    public void shouldReturnTheTimeInSeconds() {
        EasyMock.expect(mockCurrentTimeRetriever.getCurrentTimeInMillis()).andReturn(450l);
        EasyMock.expect(mockCurrentTimeRetriever.getCurrentTimeInMillis()).andReturn(480l);
        EasyMock.expect(mockCurrentTimeRetriever.getCurrentTimeInMillis()).andReturn(560l);
        EasyMock.expect(mockCurrentTimeRetriever.getCurrentTimeInMillis()).andReturn(5000l);
        mockControl.replay();

        stopWatch.start();
        assertThat(stopWatch.getTimeInMillis(), equalTo(30l));
        assertThat(stopWatch.getTimeInSeconds(), equalTo(0.110d));
        assertThat(stopWatch.getTimeInSeconds(), equalTo(4.550d));

        mockControl.verify();

    }
}
