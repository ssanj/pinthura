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
package com.googlecode.pinthura.example.filter.version;

import com.googlecode.pinthura.filter.FilterLink;
import com.googlecode.pinthura.filter.MatchNotFoundException;

public final class VersionTwoFilter implements FilterLink<XMLInformation, BusinessInformationObject> {

    private final XMLTransformer xmlTransformer;

    public VersionTwoFilter(final XMLTransformer xmlTransformer) {
        this.xmlTransformer = xmlTransformer;
    }

    public BusinessInformationObject filter(final XMLInformation xmlInformation) throws MatchNotFoundException {
        if (XMLVersionEnum.TWO.equals(xmlInformation.getVersion())) {
            return xmlTransformer.transform(xmlInformation);
        }

        throw new MatchNotFoundException();
    }

    public String getFilterName() {
        return "Version Two Filter";
    }
}
