package hong.gom.withcrossfit.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "schedule_set")
public class ScheduleSet {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "box_id")
    private Box box;
    
    @OneToOne
    @JoinColumn(name = "monday_id")
    private Schedule monday;
    
    @OneToOne
    @JoinColumn(name = "tuesday_id")
    private Schedule tuesday;
    
    @OneToOne
    @JoinColumn(name = "wednesday_id")
    private Schedule wednesday;
    
    @OneToOne
    @JoinColumn(name = "thursday_id")
    private Schedule thursday;
    
    @OneToOne
    @JoinColumn(name = "friday_id")
    private Schedule friday;
    
    @OneToOne
    @JoinColumn(name = "saturady_id")
    private Schedule saturady;
    
    @OneToOne
    @JoinColumn(name = "sunday_id")
    private Schedule sunday;

}
