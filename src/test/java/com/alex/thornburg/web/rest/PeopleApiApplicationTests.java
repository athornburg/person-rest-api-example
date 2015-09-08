package com.alex.thornburg.web.rest;

import com.alex.thornburg.web.rest.model.*;
import com.alex.thornburg.web.rest.repo.*;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
	private Address alexsAddress;
	private Address anotherAddress;
	private Person alex;
	private Sex sex;
	private Person andy;
	private Family family;
	private Kinship kinship;

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
	private KinshipRepository kinshipRepository;


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
		alexsAddress = new Address("451 W Wrightwood","United States","Chicago",60614);
		sex = new Sex(true,false);
		alex=new Person("Alex","Thornburg",23,sex,"alexthornburg1@gmail.com","740-526-6225",alexsAddress);
		anotherAddress = new Address("111 Park Drive","United States","St. Clairsville",43950);
		andy = new Person("Andy","Thornburg",21,sex,"athornbu@unca.edu","740-526-6226",anotherAddress);
		List<Kinship> list = new ArrayList<Kinship>();
		family = new Family("Thornburg",list);
		kinship = new Kinship("Brothers",alex,andy,0.01);

	}

	@Test
	public void createNew(){
		try {
			String payload = json(alex);
			this.mockMvc.perform(post("/person")
					.contentType(contentType)
					.content(payload))
					.andExpect(status().isCreated());
			personRepository.save(andy);
			String anotherPayload = json(family);
			this.mockMvc.perform(post("/family")
					.contentType(contentType)
					.content(anotherPayload))
					.andExpect(status().isCreated());
			String kinshipPayload = json(kinship);
			this.mockMvc.perform(post("/kinship")
					.contentType(contentType)
					.content(kinshipPayload))
					.andExpect(status().isCreated());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void readTest(){
		try {
			sexRepository.save(sex);
			addressRepository.save(alexsAddress);
			personRepository.save(alex);
			this.mockMvc.perform(get("/person/" + alex.getId()))
			.andExpect(status().isOk())
					.andExpect(content().contentType(contentType))
					.andExpect(jsonPath("$.firstName", is(this.alex.getFirstName())))
					.andExpect(jsonPath("$.lastName", is(this.alex.getLastName())))
					.andExpect(jsonPath("$.age", is(this.alex.getAge())));
			familyRepository.save(family);
			this.mockMvc.perform(get("/family/" + family.getId()))
					.andExpect(status().isOk())
					.andExpect(content().contentType(contentType))
					.andExpect(jsonPath("$.surname", is(this.family.getSurname())));
			kinshipRepository.save(kinship);
			this.mockMvc.perform(get("/kinship/" + kinship.getId()))
					.andExpect(status().isOk())
					.andExpect(content().contentType(contentType))
					.andExpect(jsonPath("$.relName", is(this.kinship.getRelName())));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void findTest(){
		try{
			sexRepository.save(sex);
			addressRepository.save(alexsAddress);
			personRepository.save(alex);
			this.mockMvc.perform(get("/person/findByFirstName/" + alex.getFirstName()+"/"))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$[0].firstName", is(alex.getFirstName())));
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void updateTest(){
		try{
			sexRepository.save(sex);
			addressRepository.save(alexsAddress);
			personRepository.save(alex);
			alex.setEmail("someotheremail@gmail.com");
			String payload = json(alex);
			this.mockMvc.perform(put("/person/"+alex.getId())
					.contentType(contentType)
					.content(payload));
			assertEquals(personRepository.findById(alex.getId()).getEmail(), "someotheremail@gmail.com");

			familyRepository.save(family);
			family.setSurname("AnotherLastName");
			String familyPayload = json(family);
			this.mockMvc.perform(put("/family/" + family.getId())
					.contentType(contentType)
					.content(familyPayload));
			assertEquals(family.getSurname(),familyRepository.findById(family.getId()).getSurname());

			kinshipRepository.save(kinship);
			kinship.setRelName("Cousins");
			String kinshipPayload = json(family);
			this.mockMvc.perform(put("/kinship/"+kinship.getId())
					.contentType(contentType)
					.content(kinshipPayload));
			assertEquals(kinship.getRelName(),kinshipRepository.findById(kinship.getId()).getRelName());

		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void deleteTest(){
		try{
			sexRepository.save(sex);
			addressRepository.save(alexsAddress);
			personRepository.save(alex);
			this.mockMvc.perform(delete("/person/"+alex.getId())).andExpect(status().isOk());
			assert(personRepository.findById(alex.getId())==null);

			this.mockMvc.perform(delete("/family/"+family.getId())).andExpect(status().isOk());
			assert(familyRepository.findById(family.getId())==null);

			this.mockMvc.perform(delete("/kinship/"+kinship.getId())).andExpect(status().isOk());
			assert(kinshipRepository.findById(kinship.getId())==null);
		}catch (Exception e){
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
