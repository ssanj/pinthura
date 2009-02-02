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
package com.googlecode.pinthura.bean;

/**
 * Traversers a path on an instance.
 */
public interface PathEvaluator {

    /**
     * Evaluates the supplied <code>path</code> on the <code>instance</code> supplied to return the value of the <code>path</code>.
     * @param path The path to traverse.
     * @param instance The instance to traverse the path on.
     * @param <Instance> The type of instance.
     * @param <Result> The type of the resulting object returned from path resolution.
     * @return The object pointed to by the supplied <code>path</code> on the <code>instance</code>.
     * @throws PathEvaluatorException If the path can't be evaluated.
     */
    <Instance, Result> Result evaluate(String path, Instance instance) throws PathEvaluatorException;
}
