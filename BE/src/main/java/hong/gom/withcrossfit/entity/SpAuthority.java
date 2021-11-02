package hong.gom.withcrossfit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import hong.gom.withcrossfit.entity.listener.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@IdClass(SpAuthority.class)
@Table(name = "sp_user_authority")
public class SpAuthority extends BaseEntity implements GrantedAuthority {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    private String authority;

}
