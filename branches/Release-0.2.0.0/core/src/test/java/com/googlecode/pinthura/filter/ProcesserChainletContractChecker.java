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
package com.googlecode.pinthura.filter;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public final class ProcesserChainletContractChecker {

    private ProcesserChainletContractChecker() {
        //Utility.
    }

    @SuppressWarnings("unchecked")
    public static <T> void checkContract(final ProcesserChainlet<T, ?> link,  final T validInput, final T invalidInput) {
        assertThat(ProcesserChainlet.class.isAssignableFrom(link.getClass()), equalTo(true));
        assertThat(link.process(validInput), notNullValue());

        try {
            link.process(invalidInput);
            fail("Excepted MatchNotFoundException to be thrown with invalid input: " + invalidInput);
        } catch (MatchNotFoundException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail("Excepted MatchNotFoundException to be thrown with invalid input: " + invalidInput + ". Got: " + e);
        }
    }
}
