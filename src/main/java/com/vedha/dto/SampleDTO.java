package com.vedha.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "SampleDTO", description = "Sample DTO")
public class SampleDTO {

    @Schema(description = "Unique identifier", example = "123e4567-e89b-12d3-a456-426614174000", accessMode = Schema.AccessMode.READ_ONLY)
    @Builder.Default
    private String id = UUID.randomUUID().toString();

    @Schema(description = "Name", example = "Sample Name")
    private String name;

    @Schema(description = "Description", example = "Sample Description")
    private String description;
}
