package com.tinqinacademy.hotel.core.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JsonPatchService {
    private final ObjectMapper objectMapper;

    @Valid
    public <T> T patch(JsonPatch patch, T target, Class<T> clazz) throws JsonProcessingException, JsonPatchException {
        JsonNode patched = patch.apply(objectMapper.convertValue(target, JsonNode.class));
        return objectMapper.treeToValue(patched, clazz);
    }
}
