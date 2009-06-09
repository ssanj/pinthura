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
package com.googlecode.pinthura.processer;

/**
 * This represents a "Handler" in the Gof Chain of Responsibility pattern.
 * 
 * <p>
 * A {@link ProcesserChain} takes in a <code>List<ProcesserChainlet></code>s. Each processer is mutually
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
 * @see ProcesserChain
 */
public interface ProcesserChainlet<Input, Output> {

    /**
     * Either processes an input and returns its value or throws a <code>CouldNotProcessInputException</code>to indicate that
     * another processer should handle it.
     *
     * <i>Implementations do not pass on the <code>Input</code> to another processer if it can't
     * handle it. This is done via the <code>ProcesserChain</code>.<i>
     * 
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