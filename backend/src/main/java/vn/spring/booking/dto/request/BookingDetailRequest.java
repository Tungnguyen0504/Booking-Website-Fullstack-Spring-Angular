package vn.spring.booking.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class BookingDetailRequest {
    private Integer quantity;
    private UUID roomId;
}
