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
package io.micronaut.pulsar.schemas.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.pulsar.client.api.SchemaSerializationException;
import org.apache.pulsar.client.api.schema.SchemaWriter;

/**
 * JSON Schema Writer to allow using {@link ObjectMapper} from Micronaut instead of shaded one in Pulsar library.
 *
 * @param <T> POJO type to process.
 * @author Haris Secic
 * @since 1.0
 */
public final class JacksonJsonWriter<T> implements SchemaWriter<T> {

    private final ObjectMapper mapper;

    public JacksonJsonWriter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public byte[] write(T message) {
        try {
            return mapper.writeValueAsBytes(message);
        } catch (JsonProcessingException e) {
            throw new SchemaSerializationException(e);
        }
    }
}