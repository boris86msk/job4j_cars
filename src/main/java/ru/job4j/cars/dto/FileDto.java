package ru.job4j.cars.dto;

import lombok.Data;

@Data
public class FileDto {
    private String name;

    private byte[] content;

    public FileDto(String name, byte[] content) {
        this.name = name;
        this.content = content;
    }
}
