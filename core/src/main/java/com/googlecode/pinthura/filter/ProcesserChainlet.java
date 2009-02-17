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
 * 
 * <p>
 * A <code>ChainOfResponsibility</code> takes in a list of <code>ProcesserChainlet</code>s. The processers are mutallually
 * exclusive. If a processer can't handle a request it throws a <code>CouldNotProcessInputException</code>.
 * If it can handle the request it simply returns the value of the processing of the request.
 *
 * <Input> Type of input.
 * <Output> Type of output.
 *
 * Eg. ProcesserChainlet<String, Integer>
 * </p>
 *
 * @see ChainOfResponsibility
 */
public interface ProcesserChainlet<Input, Output> {

    /**
     * Either processes an input and returns its value or throws a <code>CouldNotProcessInputException</code>to indicate that
     * another processer should handle it.
     * @param input The input parameter.
     * @return The result of processing the input paramater.
     * @throws CouldNotProcessInputException If the input parameter can't be handled by this filter.
     */
    Output process(final Input input) throws CouldNotProcessInputException;

    /**
     * @return The name of this processer.
     */
    String getProcesserName();
}
