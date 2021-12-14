package hong.gom.withcrossfit.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import hong.gom.withcrossfit.entity.listener.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "wod")
@EqualsAndHashCode(callSuper = true)
public class Wod extends BaseEntity {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "box_id")
    private Box box;
    
    @OneToOne
    @JoinColumn(name = "user_id")
    private SpUser writer;
    
    private LocalDate date;
    
    private String title;
    
    private boolean editable;
    
    private String content;
    
}
