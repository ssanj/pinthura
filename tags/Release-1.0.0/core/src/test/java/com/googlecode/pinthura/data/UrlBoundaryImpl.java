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
package com.googlecode.pinthura.data;

import java.net.MalformedURLException;
import java.net.URL;

public final class UrlBoundaryImpl implements UrlBoundary {

    public static final String DEFAULT_URL = "http://sneaky.verysneaky.org";

    private final URL url;

    public UrlBoundaryImpl() {
        this(DEFAULT_URL);
    }

    public UrlBoundaryImpl(final String urlValue) {
        try {
            this.url = new URL(urlValue);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("url: " + urlValue + "is invalid.");
        }
    }

    public String getUrlAsString() {
        return url.toExternalForm();
    }
}
