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
package com.googlecode.pinthura.example.factory.report;

public final class ReportImpl implements Report {

    private final Information info;

    public ReportImpl(final Information info) {
        this.info = info;
    }

    public void generate() {
        String groupName = info.getGroupName();
        int memberCount = info.getMemberCount();
        System.out.println("-----------------------------");
        System.out.println("Group " + groupName + " has " + memberCount + " members");
        System.out.println("-----------------------------");
    }
}
