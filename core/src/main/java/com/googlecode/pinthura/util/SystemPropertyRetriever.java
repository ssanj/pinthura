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
package com.googlecode.pinthura.util;

/**
 * Retrieves commonly used system properties @{link System#getProperty()} as well as arbitrary named properties.
 */
public interface SystemPropertyRetriever {

    /**
     * Returns the value of a named-property. 
     * @param propertyName The name of the property whose value is required.
     * @return The value of the property.
     * @throws PropertyRetrieverException If the property could not be retrieved or is null.
     */
    String getProperty(String propertyName) throws PropertyRetrieverException;

    /**
     * @return The current os-specific line separator.
     * @throws PropertyRetrieverException If the property could not be retrieved or is null.
     */
    String getLineSeparator() throws PropertyRetrieverException;

    /**
     * @return The current os-specific file separator.
     * @throws PropertyRetrieverException If the property could not be retrieved or is null.
     */
    String getFileSeparator() throws PropertyRetrieverException;
}
