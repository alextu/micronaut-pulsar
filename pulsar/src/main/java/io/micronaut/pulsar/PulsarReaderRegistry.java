/*
 * Copyright 2017-2021 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.pulsar;

import org.apache.pulsar.client.api.Reader;

import java.util.Collection;

/**
 * Keeps track of all created Pulsar readers.
 *
 * @author Haris Secic
 * @since 1.0
 */
public interface PulsarReaderRegistry {

    /**
     * If not specified explicitly, reader name will default to property/field name.
     *
     * @param identifier unique identifier for a reader
     * @return Pulsar Reader if found for a given name
     */
    Reader<?> getReader(String identifier);

    /**
     * @return all registered Pulsar Readers
     */
    Collection<Reader<?>> getReaders();
}
