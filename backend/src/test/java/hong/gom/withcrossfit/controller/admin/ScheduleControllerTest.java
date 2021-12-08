package hong.gom.withcrossfit.controller.admin;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import hong.gom.withcrossfit.service.ScheduleService;
import hong.gom.withcrossfit.util.RegexValidator;

@WebMvcTest(controllers = ScheduleController.class)
@MockBean(JpaMetamodelMappingContext.class)
@ActiveProfiles(profiles = "test")
class ScheduleControllerTest {
	
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
	private ScheduleService scheduleService;
	
	@MockBean
	private RegexValidator regexValidator;

}
