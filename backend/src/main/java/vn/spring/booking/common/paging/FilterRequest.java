package vn.spring.booking.common.paging;

import vn.spring.booking.constant.enums.FieldType;
import vn.spring.booking.model.Operator;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FilterRequest {
    private String key;
    private List<String> values;
    private Operator operator;
    private FieldType fieldType;
}
