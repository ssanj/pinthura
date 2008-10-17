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

import com.googlecode.pinthura.filter.FilterChainImpl;
import com.googlecode.pinthura.filter.FilterLink;
import com.googlecode.pinthura.filter.MatchNotFoundException;

import java.util.ArrayList;
import java.util.List;

public final class XMLVersionFilterRunner {

    private static class UnknownVersion implements XMLVersion {

        public String toString() {
            return "Unknown Version";
        }
    }

    private XMLVersionFilterRunner() { }

    public static void main(final String[] args) {
        List<FilterLink<XMLInformation, BusinessInformationObject>> filters =
                new ArrayList<FilterLink<XMLInformation, BusinessInformationObject>>();
        filters.add(new VersionOneFilter(new VersionOneXMLTransformer()));
        filters.add(new VersionTwoFilter(new VersionTwoXMLTransformer()));

        FilterLink<XMLInformation, BusinessInformationObject> chainOfResp =
                new FilterChainImpl<XMLInformation, BusinessInformationObject>(filters);

        BusinessInformationObject bo1 = chainOfResp.filter(new XMLInformationImpl(XMLVersionEnum.ONE));
        BusinessInformationObject bo2 = chainOfResp.filter(new XMLInformationImpl(XMLVersionEnum.TWO));

        System.out.println(bo1.getInformation());
        System.out.println(bo2.getInformation());

        try {
            chainOfResp.filter(new XMLInformationImpl(new UnknownVersion()));
        } catch (MatchNotFoundException e) {
            throw new UnknownXMLVersionException(e);
        }
    }
}
