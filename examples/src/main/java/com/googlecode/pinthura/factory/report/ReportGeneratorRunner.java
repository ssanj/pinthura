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
package com.googlecode.pinthura.factory.report;

import com.googlecode.pinthura.factory.FactoryCreator;
import com.googlecode.pinthura.factory.FactoryCreatorImpl;
import com.googlecode.pinthura.factory.InvocationFactoryImpl;

public final class ReportGeneratorRunner {

    private static final int POPULATION = 5023;

    private ReportGeneratorRunner() { }

    public static void main(final String[] args) {
        FactoryCreator factoryCreator = new FactoryCreatorImpl(new InvocationFactoryImpl());
        ReportFactory reportFactory = factoryCreator.create(ReportFactory.class);
        ReportGenerator generator = new ReportGeneratorImpl(reportFactory);
        generator.generate(new InformationImpl("Tok'ra", POPULATION));
    }
}
