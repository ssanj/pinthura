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

import java.util.List;

public final class FilterChainImpl<Input, Output> implements FilterLink<Input, Output> {

    private static final String FILTER_CHAIN = "FilterChain";

    private final List<? extends FilterLink<Input, Output>> filterLinks;

    public FilterChainImpl(final List<? extends FilterLink<Input, Output>> filterLinks) {
        this.filterLinks = filterLinks;
    }

    @SuppressWarnings({ "unchecked" })
    public Output filter(final Input input) {

        for (FilterLink link : filterLinks) {
            try {
                return (Output) link.filter(input);
                //CHECKSTYLE_OFF
            } catch (MatchNotFoundException e) { /*do nothing*/ }
            //CHECKSTYLE_ON
        }

        throw new MatchNotFoundException("No match found for: " + input + " with filters: " + withFilters());
    }

    private String withFilters() {
        StringBuilder builder = new StringBuilder();

        for (FilterLink<Input, Output> filterLink : filterLinks) {
            if (builder.length() != 0) {
                builder.append(", ");
            }

            builder.append(filterLink.getFilterName());
        }

        return builder.toString();
    }

    public String getFilterName() {
        return FILTER_CHAIN;
    }
}
