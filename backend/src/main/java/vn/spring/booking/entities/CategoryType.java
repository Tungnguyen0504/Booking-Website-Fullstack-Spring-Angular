package vn.spring.booking.entities;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.spring.common.entities.GenericUUID;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CATEGORY_TYPE")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryType extends GenericUUID {
  @Column(name = "CODE")
  String code;

  @Column(name = "NAME")
  String name;

  @Column(name = "DESCRIPTION")
  String description;

  @Column(name = "ENABLED")
  Boolean enabled;

  @OneToMany(mappedBy = "categoryType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  List<Category> categories;
}
