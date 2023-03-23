package pl.medos.cmmsApi.dto;

import pl.medos.cmmsApi.model.Part;

import java.util.ArrayList;
import java.util.List;

public class PartListDto {

    private List<Part> parts;

    public PartListDto() {
        this.parts = new ArrayList<>();
    }

    public PartListDto(List<Part> parts) {
        this.parts = parts;
    }

    public List<Part> getParts() {
        return parts;
    }

    public void setBooks(List<Part> parts) {
        this.parts = parts;
    }

    public void addPart(Part part) {
        this.parts.add(part);
    }
}
