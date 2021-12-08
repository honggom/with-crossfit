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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import hong.gom.withcrossfit.dto.BoxDto;
import hong.gom.withcrossfit.service.BoxService;

@WebMvcTest(controllers = BoxController.class)
@MockBean(JpaMetamodelMappingContext.class)
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

	@Test
	void getBox_api_테스트() throws Exception {
		// given
		BoxDto box1 = new BoxDto();
		box1.setName("난곡");

		BoxDto box2 = new BoxDto();
		box2.setName("서울대");

		BoxDto box3 = new BoxDto();
		box3.setName("이수");

		List<BoxDto> boxes = List.of(box1, box2, box3);

		given(boxService.getBoxService()).willReturn(boxes);

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