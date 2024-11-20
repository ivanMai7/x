package xyz.ivan.x.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class UserDTO {
   private Long id;
   private String nickName;
   private String icon;
}
