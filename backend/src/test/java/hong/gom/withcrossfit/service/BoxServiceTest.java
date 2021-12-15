package hong.gom.withcrossfit.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import hong.gom.withcrossfit.repository.BoxRepository;

@ExtendWith(MockitoExtension.class)
class BoxServiceTest {
	
	@InjectMocks
	private BoxService boxService;
	
	@Mock
	BoxRepository boxRepository;
	
}