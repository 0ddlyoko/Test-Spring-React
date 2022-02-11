package me.oddlyoko.demo.payloads.responses;

import java.util.List;

public record PagedResponse<T>(List<T> content, int page, int size, long totalElements, long totalPages) {
}
