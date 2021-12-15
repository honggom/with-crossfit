package hong.gom.withcrossfit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import hong.gom.withcrossfit.entity.MyRm;
import hong.gom.withcrossfit.repository.MyRmRepository;

@ExtendWith(MockitoExtension.class)
class MyRmServiceTest {

	@InjectMocks
	private MyRmService myRmService;
	
	@Mock
	private MyRmRepository myRmRepository;
	
	@Test
	void 저장된_RM을_ID로_조회한다() {
		// given
		Long fakeId = 1L;
		
		MyRm newRm = MyRm.builder()
				         .id(fakeId)
                         .name("squat")
                         .repetition(10)
                         .lb(100) 
                         .build();
		
		given(myRmRepository.findById(fakeId)).willReturn(Optional.of(newRm));
		
		// when
		Optional<MyRm> rm = myRmService.getMyRmById(fakeId);
		
		// then
		assertEquals(rm.get().getId(), newRm.getId());
		assertEquals(rm.get().getRepetition(), newRm.getRepetition());
		assertEquals(rm.get().getLb(), newRm.getLb());
	}

}
