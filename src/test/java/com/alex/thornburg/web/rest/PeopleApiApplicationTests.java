package com.alex.thornburg.web.rest;

import com.alex.thornburg.web.rest.model.Address;
import com.alex.thornburg.web.rest.model.Person;
import com.alex.thornburg.web.rest.repo.AddressRepository;
import com.alex.thornburg.web.rest.repo.FamilyRepository;
import com.alex.thornburg.web.rest.repo.PersonRepository;
import com.alex.thornburg.web.rest.repo.SexRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import com.alex.thornburg.web.rest.model.Sex;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PeopleApiApplication.class)
@WebAppConfiguration
public class PeopleApiApplicationTests {
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(),
			Charset.forName("utf8"));

	private MockMvc mockMvc;

	private HttpMessageConverter mappingJackson2HttpMessageConverter;
	Address alexsAddress;
	Person alex;
	Sex sex;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private FamilyRepository familyRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private SexRepository sexRepository;

	@Autowired
	private PersonRepository personRepository;


	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
				hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

		Assert.assertNotNull("the JSON message converter must not be null",
				this.mappingJackson2HttpMessageConverter);
	}

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
		this.personRepository.deleteAllInBatch();
		alexsAddress = new Address("451 W Wrightwood","United States","Chicago",60614);
		sex = new Sex(true,false);
		alex=new Person("Alex","Thornburg",23,sex,"alexthornburg1@gmail.com","740-526-6225",alexsAddress);
	}

	@Test
	public void createNewPerson(){
		try {
			String payload = json(alex);
			this.mockMvc.perform(post("/person")
					.contentType(contentType)
					.content(payload))
					.andExpect(status().isCreated());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void readPerson(){
		try {
			sexRepository.save(sex);
			addressRepository.save(alexsAddress);
			personRepository.save(alex);
			this.mockMvc.perform(get("/person/" + alex.getId()))
			.andExpect(status().isOk())
					.andExpect(content().contentType(contentType))
					.andExpect(jsonPath("$[0].id", is(this.alex.getId())))
					.andExpect(jsonPath("$[0].firstName", is(alex.getFirstName())))
					.andExpect(jsonPath("$[0].lastName", is(alex.getLastName())))
					.andExpect(jsonPath("$[0].age", is(alex.getAge())));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(
				o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}

}