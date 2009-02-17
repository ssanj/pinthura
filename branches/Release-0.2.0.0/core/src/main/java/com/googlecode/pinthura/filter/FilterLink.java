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

/**
 * This represents a "Handler" in the Gof Chain of Responsibility pattern.
 * This implementation of the Chain of Responsibility pattern has been modified such that the passing of the input parameter to subsequent
 * successors when it can't be handled, is not performed by each Handler/FilterLink. It is done by the {@link FilterChainImpl}
 *
 * <p>
 * The <code>FilterChainImpl</code> takes in a list of <code>FilterLink</code>s. The filters are mutallually exclusive. If a filter can't
 * handle a request it throws a <code>MatchNotFoundException</code>.
 * If it can handle the request it simply returns the value of the processing of the request.
 *
 * <Input> Type of input.
 * <Output> Type of output.
 *
 * Eg. FilterLink<String, Integer>
 * </p>
 *
 * @see FilterChainImpl
 */
public interface FilterLink<Input, Output> {

    /**
     * Either processes an input and returns its value or throws a <code>MatchNotFoundException</code>to indicate that
     * another filter should handle it.
     * @param input The input parameter.
     * @return The result of processing the input paramater.
     * @throws MatchNotFoundException If the input parameter can't be handled by this filter.
     */
    Output filter(final Input input) throws MatchNotFoundException;

    /**
     * @return The name of this filter.
     */
    String getFilterName();
}
