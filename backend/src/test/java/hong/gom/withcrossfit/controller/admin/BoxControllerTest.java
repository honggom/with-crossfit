package hong.gom.withcrossfit.controller.admin;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import hong.gom.withcrossfit.dto.BoxDto;
import hong.gom.withcrossfit.entity.Box;
import hong.gom.withcrossfit.service.BoxService;
import hong.gom.withcrossfit.util.Converter;

@WebMvcTest(controllers = BoxController.class)
@MockBean(JpaMetamodelMappingContext.class)
@ActiveProfiles(profiles = "test")
class BoxControllerTest {

	private MockMvc mvc;
	
	@Autowired
	private WebApplicationContext ctx;

	@BeforeEach
	void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(ctx)
				             .addFilters(new CharacterEncodingFilter("UTF-8", true))
				             .alwaysDo(print()).build();
	}

	@MockBean
	private BoxService boxService;
	
	@MockBean
	private Converter converter;

	@Test
	void getBox_api_테스트() throws Exception {
		// given
		Box box1 = Box.builder().name("난곡").build();

		Box box2 = Box.builder().name("서울대").build();

		Box box3 = Box.builder().name("이수").build();
		
		BoxDto boxDto1 = new BoxDto();
		boxDto1.setName("난곡");
		
		BoxDto boxDto2 = new BoxDto();
		boxDto2.setName("서울대");
		
		BoxDto boxDto3 = new BoxDto();
		boxDto3.setName("이수");

		List<Box> boxes = List.of(box1, box2, box3);
		List<BoxDto> boxDtos = List.of(boxDto1, boxDto2, boxDto3);

		given(boxService.getBoxes()).willReturn(boxes);
		given(converter.convertToBoxDtoList(boxes)).willReturn(boxDtos);

		// when
		final ResultActions actions = mvc.perform(get("/admin/api/box")
				                         .contentType(MediaType.APPLICATION_JSON));

		// then
		actions.andExpect(status().isOk())
		       .andExpect(jsonPath("$[0].name", "난곡").exists())
		       .andExpect(jsonPath("$[1].name", "서울대").exists())
		       .andExpect(jsonPath("$[2].name", "이수").exists());
	}

}