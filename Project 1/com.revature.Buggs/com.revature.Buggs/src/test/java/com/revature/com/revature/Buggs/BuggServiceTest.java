package com.revature.com.revature.Buggs;

import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.revature.controllers.BuggController;
import com.revature.dtos.BuggDTO;
import com.revature.exceptions.BuggNotFoundException;
import com.revature.models.Bugg;
import com.revature.repositories.BuggRepository;
import com.revature.services.BuggService;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BuggServiceTest {

//		static BuggController bc;
	static BuggRepository br;
	static BuggService bs;
	static List<Bugg> buggs = new ArrayList<>();
	static List<BuggDTO> buggd = new ArrayList<>();
	static Bugg bugg;

	@BeforeAll
	public static void setup() {
		br = mock(BuggRepository.class);
		bs = new BuggService(br);
		bugg = new Bugg(1, "x", "x", "x", 1);
		buggs.add(bugg);

	}

	@Test
	public void getAllBuggsTest() {
		when(br.findAll()).thenReturn(buggs);
		assertEquals(buggs, bs.getBugg());
	}
			
	@Test
	public void getBuggByIdTest() {
		when(br.findById(1)).thenReturn(Optional.of(bugg));
		assertEquals(bugg, bs.getById(1));	
	}
		
	@Test
	public void getBuggByFamTest() {
		buggs.add(new Bugg(2, "ladybug", "beetle", "temperate", 5));
		when(br.findBuggsByFam("beetle")).thenReturn(buggd);
		assertEquals(buggs, bs.getBuggsByFam("beetle"));
	}
	
	@Test
	public void getBuggByHabTest() {
		buggs.add(new Bugg(3, "jumping spider", "spider", "arid", 10));
		when(br.findBuggsByHab("arid")).thenReturn(buggd);
		assertEquals(buggs, bs.getBuggsByHab("arid"));
	}

	@Test
	public void createBuggTest() {
		when(br.save(bugg)).thenReturn(bugg);
		assertEquals(bugg, bs.createBugg(bugg));
	}
	
	@Test
	public void updateBuggTest() throws BuggNotFoundException{
		when(br.findById(1)).thenReturn(Optional.of(bugg));
		when(br.save(bugg)).thenReturn(bugg);
		assertEquals(bugg, bs.updateBugg(1, bugg));
	}
	
	@Test
	public void deleteBuggByIdTest() {
		when(br.getById(1)).thenReturn(bugg);
		assertEquals(bs.deleteBuggById(1),true);
	}
}


