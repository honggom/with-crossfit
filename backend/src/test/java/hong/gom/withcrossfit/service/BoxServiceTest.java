package hong.gom.withcrossfit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import hong.gom.withcrossfit.entity.Box;
import hong.gom.withcrossfit.repository.BoxRepository;

@SpringBootTest
@Transactional
@Rollback(true)
// @ActiveProfiles(profiles = "dev")
class BoxServiceTest {
	
	@Autowired
	BoxService boxService;
	
	@Autowired
	BoxRepository boxRepository;
	
	// FIXME 수정 필요
	@Test @Disabled
    void 저장된_모든_박스가_조회된다() {
		// given
		Box box1 = Box.builder()
				      .name("난곡")
				      .build();
		
		Box box2 = Box.builder()
			          .name("서울대입구")
			          .build();
		
		Box box3 = Box.builder()
			          .name("이수역")
			          .build();
		
		boxRepository.saveAll(List.of(box1, box2, box3));
		
		// when
		List<Box> boxes = boxService.getBoxes();
		
		// then
		assertEquals(3, boxes.size());
	}
}