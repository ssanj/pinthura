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
 * Represents a single filter. Each <code>FilterLink</code> should accept a default value of type <R>.
 * <S> Type of input.
 * <R> Type of output.
 *
 *
 * Eg. for a DivideByTwoLink:
 *
 * public class DivideByTwoLink implements FilterLink<Integer, Boolean> {
 *
 *  private final Boolean defaulValue;
 *
 *  DivideByTwoLink(Boolean defaultValue) {
 *      this.defaultValue = defaulValue;
 *  }
 *  Boolean filter(final Integer input) {
 *      return (input % 2 == 0) ? true : defaultValue;
 *  }
 * }

 */
public interface FilterLink<Input, Output> {

    /**
     * Filters an input. This method should can be called multiple times. Therefore it's state should be handled accordingly.
     * @param input The input to filter.
     * @return The output of filtering if successful.
     * @throws MatchNotFoundException If a match is not found.
     * <S> Type of input.
     * <R> Type of output.
     */
    Output filter(final Input input) throws MatchNotFoundException;

    String getFilterName();
}
