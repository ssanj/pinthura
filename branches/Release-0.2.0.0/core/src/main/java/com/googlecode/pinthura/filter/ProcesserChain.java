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

import com.googlecode.pinthura.annotation.SuppressionReason;

import java.util.Collections;
import java.util.List;

/**
 * Handles passing the <code>Input</code> parameter to each successor on the <code>List<FilterLink></code> supplied to its constructor.
 *
 * If a <code>FilterLink</code> processes the <code>Input</code>, then the result is returned as an <code>Output</code>.
 * If not a <code>MatchNotFoundException</code> is thrown when there are no more <code>FilterLink</code>s to process the input.
 */
public final class ProcesserChain<Input, Output> implements ChainOfResponsibility<Input, Output> {

    private final List<? extends FilterLink> filterLinks;

    @SuppressWarnings("unchecked")
    @SuppressionReason(SuppressionReason.Reason.SIMPLIFY_GENERICS)
    public ProcesserChain(final List<? extends FilterLink> filterLinks) {
        this.filterLinks = Collections.unmodifiableList(filterLinks);
    }

    @SuppressWarnings("unchecked")
    public Output process(final Input input) {

        for (FilterLink link : filterLinks) {
            try {
                return (Output) link.filter(input);
                //CHECKSTYLE_OFF
            } catch (MatchNotFoundException e) {/*do nothing.*/}
            //CHECKSTYLE_ON
        }

        throw new MatchNotFoundException("No match found for: " + input + " with filters: " + withFilters());
    }

    private String withFilters() {
        StringBuilder builder = new StringBuilder();

        for (FilterLink filterLink : filterLinks) {
            if (builder.length() != 0) {
                builder.append(", ");
            }

            builder.append(filterLink.getFilterName());
        }

        return builder.toString();
    }
}
