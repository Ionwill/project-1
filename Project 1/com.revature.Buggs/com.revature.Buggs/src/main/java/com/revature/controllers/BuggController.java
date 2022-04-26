package com.revature.controllers;

import java.util.List;
import java.util.UUID;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dtos.BuggDTO;
import com.revature.models.Bugg;
import com.revature.services.AuthService;
import com.revature.services.BuggService;


@RestController
@RequestMapping("/bugg")
public class BuggController {
	
	private AuthService as;
	private BuggService bs;
	private BuggDTO bt;
	private static final Logger LOG = LoggerFactory.getLogger(BuggController.class);

	@Autowired
	public BuggController(BuggService bs) {
		super();
		this.bs = bs;
	}

	@GetMapping
	public ResponseEntity<List<BuggDTO>> getAll(@RequestParam(name="fam", required=false) String fam, 
			@RequestParam(name="hab", required=false)String hab){
		
		if(fam!=null) {
		return new ResponseEntity<>(bs.getBuggsByFam(fam),HttpStatus.OK);
		}
		if(hab!=null) {
			return new ResponseEntity<>(bs.getBuggsByHab(hab),HttpStatus.OK);
		}
		LOG.info("Buggs retrieved.");
			return new ResponseEntity<>(bs.getBugg(), HttpStatus.OK);
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<BuggDTO> getBuggById(@PathVariable("id") int id){
		LOG.info("retrieved users by ID");
		return new ResponseEntity<>(bs.getById(id),HttpStatus.OK);
	}

	//ADMIN ONLY
	
	@PostMapping
	public ResponseEntity<String> createBugg(@RequestBody Bugg bugg, @RequestHeader(value = "Authorization", required = true) String token){
		if (token == null) {
			MDC.put("User unauthorized to add buggs", UUID.randomUUID().toString());
			LOG.warn("Non user attempted adding a bugg");
			return new ResponseEntity<>("Not authorized to post buggs.", HttpStatus.FORBIDDEN);
		}
		MDC.put("requestId", UUID.randomUUID().toString());
		as.adVerify(token);
		LOG.info("users retrieved");
		Bugg b = bs.createBugg(bugg);
		return new ResponseEntity<>("Bugg " + b.getKind() + " has been created.", HttpStatus.CREATED);
	}
	

	@PutMapping("/{id}")
	public ResponseEntity<Bugg> updateBugg(@RequestBody Bugg bugg, @PathVariable("id") int id, @RequestHeader(value = "Authorization", required = true) String token) {
		if (token == null) {
			MDC.put("User unauthorized to update buggs", UUID.randomUUID().toString());
			LOG.warn("Unauthorized user attempted to update buggs.");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		MDC.put("requestId", UUID.randomUUID().toString());
		as.adVerify(token);
		LOG.info("users retrieved");
		return new ResponseEntity<>(bs.updateBugg(id, bugg), HttpStatus.OK); 
		
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable("id") int id, @RequestHeader(value = "Authorization", required = true) String token) {
		if (token == null) {
			MDC.put("User unauthorized to delete buggs", UUID.randomUUID().toString());
			LOG.warn("User unauthorized to delete buggs.");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		MDC.put("users deleted by Id", UUID.randomUUID().toString());
		as.adVerify(token);
		LOG.info("users deleted by Id.");
		bs.deleteBuggById(id);
		return new ResponseEntity<>("Bugg #"+id +" was deleted", HttpStatus.OK);
	}
}