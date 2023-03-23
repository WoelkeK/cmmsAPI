package pl.medos.cmmsApi.service.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.model.Part;
import pl.medos.cmmsApi.service.PartService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PartServiceImpl implements PartService {

    static Map<Long, Part> partDB = new HashMap<>();

    @Override
    public List<Part> findAll() {
        return new ArrayList<>(partDB.values());
    }

    @Override
    public void saveAll(List<Part> parts) {

        long nextId = getNextId();
        for (Part part : parts) {
            if (part.getId() == 0) {
                part.setId(nextId++);
            }
        }

        Map<Long, Part> partMap = parts.stream()
                .collect(Collectors.toMap(Part::getId, Function.identity()));

        partDB.putAll(partMap);

    }

    private Long getNextId() {
        return partDB.keySet()
                .stream()
                .mapToLong(value -> value)
                .max()
                .orElse(0) + 1;
    }
}
