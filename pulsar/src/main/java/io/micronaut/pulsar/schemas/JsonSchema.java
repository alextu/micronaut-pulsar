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
package io.micronaut.pulsar.schemas;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.jackson.databind.JacksonDatabindMapper;
import io.micronaut.json.JsonMapper;
import io.micronaut.pulsar.schemas.json.JacksonJsonReader;
import io.micronaut.pulsar.schemas.json.JacksonJsonWriter;
import org.apache.pulsar.client.api.schema.SchemaDefinition;
import org.apache.pulsar.client.api.schema.SchemaReader;
import org.apache.pulsar.client.api.schema.SchemaWriter;
import org.apache.pulsar.client.impl.schema.AvroBaseStructSchema;
import org.apache.pulsar.client.impl.schema.SchemaDefinitionBuilderImpl;
import org.apache.pulsar.client.impl.schema.util.SchemaUtil;
import org.apache.pulsar.common.schema.SchemaInfo;
import org.apache.pulsar.common.schema.SchemaType;

/**
 * JSON Schema to allow using {@link ObjectMapper} from Micronaut instead of shaded one in Pulsar library.
 * @param <T> POJO type to send and receive via Pulsar.
 * @author Haris Secic
 * @since 1.0
 */
public class JsonSchema<T> extends AvroBaseStructSchema<T> {

    public JsonSchema(SchemaInfo schemaInfo, SchemaReader<T> reader, SchemaWriter<T> writer) {
        super(schemaInfo);
        this.setWriter(writer);
        this.setReader(reader);
    }

    /**
     * @param pojo         The pojo class to map
     * @param objectMapper The jackson object mapper to use for mapping
     * @param <T>          The pojo class to map
     * @return The parsed json schema
     * @deprecated Use {@link #of(Class, JsonMapper)} instead
     */
    @Deprecated
    public static <T> JsonSchema<T> of(Class<T> pojo, ObjectMapper objectMapper) {
        return of(pojo, new JacksonDatabindMapper(objectMapper));
    }

    /**
     * @param pojo       The pojo class to map
     * @param jsonMapper The json mapper to use for mapping
     * @param <T>        The pojo class to map
     * @return The parsed json schema
     * @since 1.1.0
     */
    public static <T> JsonSchema<T> of(Class<T> pojo, JsonMapper jsonMapper) {
        final SchemaReader<T> reader = new JacksonJsonReader<>(jsonMapper, pojo);
        final SchemaWriter<T> writer = new JacksonJsonWriter<>(jsonMapper);
        final SchemaDefinition<T> schemaDefinition = new SchemaDefinitionBuilderImpl<T>().withPojo(pojo)
                .withSchemaReader(reader)
                .withSchemaWriter(writer)
                .build();
        return new JsonSchema<>(SchemaUtil.parseSchemaInfo(schemaDefinition, SchemaType.JSON), reader, writer);
    }
}
