package hong.gom.withcrossfit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import hong.gom.withcrossfit.dto.MyRmDto;
import hong.gom.withcrossfit.entity.MyRm;
import hong.gom.withcrossfit.repository.MyRmRepository;

@ExtendWith(MockitoExtension.class)
class MyRmServiceTest {

	@InjectMocks
	private MyRmService myRmService;
	
	@Mock
	private MyRmRepository myRmRepository;
	
	@Mock
	private ModelMapper modelMapper;
	
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
		given(modelMapper.map(newRm, MyRmDto.class)).willReturn(MyRmDto.builder()
		                                                               .id(fakeId)
                                                                       .name("squat")
                                                                       .repetition(10)
                                                                       .lb(100) 
				                                                       .build());
		
		// when
		MyRmDto dto = myRmService.getMyRmByIdService(fakeId);
		
		// then
		assertEquals(dto.getId(), newRm.getId());
		assertEquals(dto.getRepetition(), newRm.getRepetition());
		assertEquals(dto.getLb(), newRm.getLb());
	}

}
