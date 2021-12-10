package hong.gom.withcrossfit.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class MyRmDto {
	
	private Long id;
    private String name;
    private int repetition;
    private int lb;

}
