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
 * A specification of the (Gof) Chain of Responsibility pattern.  An implementation would have some mechanism to
 * receive a list of processers. When <code>process(Input, Output)</code> is called, each processer would be given
 * a turn to process the <code>Input</code>. If one does, then it returns the result or the <code>Output</code>.
 * <br/>
 * If not the implementation of this interface would pass it to the next processer until a result is returned or a
 * <code>CouldNotProcessInputException</code> is thrown if none of the processers can process the <code>Input</code>.
 *
 * @param <Input> The input type to process.
 * @param <Output> The result of processing the <code>Input</code>.
 */
public interface ChainOfResponsibility<Input, Output> {

    /**
     * Returns the result of processing the supplied <code>Input</code>.
     * 
     * @param input The input to process.
     * @return The result of processing the <code>Input</code>.
     * @throws CouldNotProcessInputException If none of the processers can process the <code>Input</code>.
     */
    Output process(Input input) throws CouldNotProcessInputException;
}
