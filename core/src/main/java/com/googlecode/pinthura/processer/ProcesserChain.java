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

import com.googlecode.pinthura.annotation.SuppressionReason;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles passing the <code>Input</code> parameter to each processer on the <code>List<ProcesserChainlet></code> supplied to its constructor.
 *
 * If a <code>ProcesserChainlet</code> processers the <code>Input</code>, then the result is returned as an <code>Output</code>.
 * If not a <code>CouldNotProcessInputException</code> is thrown when there are no more <code>ProcesserChainlet</code>s to process the input.
 */
public final class ProcesserChain<Input, Output> implements ChainOfResponsibility<Input, Output> {

    private final List<? extends ProcesserChainlet> processers;

    @SuppressionReason(SuppressionReason.Reason.SIMPLIFY_GENERICS)
    public ProcesserChain(final List<? extends ProcesserChainlet> processers) {
        this.processers = new ArrayList<ProcesserChainlet>(processers); //create a copy so the list is not modifyable externally.
    }

    @SuppressWarnings("unchecked")
    public Output process(final Input input) {

        for (ProcesserChainlet processerChainlet : processers) {
            try {
                return (Output) processerChainlet.process(input);
                //CHECKSTYLE_OFF
            } catch (CouldNotProcessInputException e) {/*do nothing.*/}
            //CHECKSTYLE_ON
        }

        throw new CouldNotProcessInputException("No match found for: " + input + " with processers: " + withProcessers());
    }

    private String withProcessers() {
        StringBuilder builder = new StringBuilder();

        for (ProcesserChainlet processerChainlet : processers) {
            if (builder.length() != 0) {
                builder.append(", ");
            }

            builder.append(processerChainlet.getProcesserName());
        }

        return builder.toString();
    }
}
