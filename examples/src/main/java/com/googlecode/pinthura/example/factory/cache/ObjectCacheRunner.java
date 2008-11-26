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
package com.googlecode.pinthura.example.factory.cache;

import com.googlecode.pinthura.factory.FactoryCreator;
import com.googlecode.pinthura.factory.builder.FactoryCreatorBuilder;

import java.util.Arrays;

public final class ObjectCacheRunner {

    private ObjectCacheRunner() { }

    public static void main(final String[] args) {
        System.out.println("Without Dynamic Factories");
        exerciseCache(withDynamicFactories());
        System.out.println();
        System.out.println("With Dynamic Factories");
        exerciseCache(withoutDynamicFactories());
    }

    private static ObjectCache withoutDynamicFactories() {
        FactoryCreator factoryCreator = new FactoryCreatorBuilder().build();
        ObjectCacheFactory cacheFactory = factoryCreator.create(ObjectCacheFactory.class);
        ObjectCacheEventFactory eventFactory = factoryCreator.create(ObjectCacheEventFactory.class);
        return cacheFactory.create(eventFactory);
    }

    private static ObjectCache withDynamicFactories() {
        FactoryCreator factoryCreator = new FactoryCreatorBuilder().build();
        return factoryCreator.create(ObjectCacheFactory.class).create();
    }

    private static void exerciseCache(final ObjectCache cache) {
        cache.addListener(new ObjectCacheListener() {
            public void instanceCreated(final ObjectCacheEvent event) {
                System.out.println("The following instance was cached: " + event.getInstance());
            }
        });

        cache.setInstance("Hello World!");
        cache.setInstance(1);
        cache.setInstance(Arrays.asList("3", "2", "1"));
    }
}
