/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.trilogy.dcm.data.reader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

@Slf4j
public class JSONReader implements DataReader {
    private final ObjectMapper MAPPER = new ObjectMapper();
    private final String key;

    public JSONReader(String key) {
        this.key = key;
    }

    @Override
    public Object[][] getInputData(InputStream inputStream) {
        try {
            ObjectNode objectNode = MAPPER.readValue(inputStream, ObjectNode.class);
            ObjectNode[] objectNodes;
            if (objectNode.has(key)) {
                if (objectNode.get(key).isArray()) {
                    objectNodes = MAPPER.convertValue(objectNode.get(key), ObjectNode[].class);
                } else {
                    objectNodes = new ObjectNode[]{(ObjectNode) objectNode.get(key)};
                }
                if (objectNodes.length > 0) {
                    Object[][] data = new Object[objectNodes.length][objectNodes[0].size()];
                    for (int i = 0; i < objectNodes.length; i++) {
                        // Initialize j for each row in the data array
                        int j = 0;

                        // Get the iterator for fields of the current ObjectNode
                        Iterator<Map.Entry<String, JsonNode>> fields = objectNodes[i].fields();

                        while (fields.hasNext()) {
                            Map.Entry<String, JsonNode> field = fields.next();
                            if (field.getValue().isObject()) {
                                data[i][j] = field.getValue();
                            } else if (field.getValue().isArray()) {
                                data[i][j] = field.getValue();
                            } else {
                                data[i][j] = field.getValue().asText();
                            }
                            j++;
                        }
                    }
                    return data;
                }
            }
        } catch (IOException e) {
            log.error("Failed to load the JSON data", e);
            throw new RuntimeException(String.format("Failed to load the JSON data error [%s]", e.getMessage()));
        }
        return new Object[0][];
    }
}
