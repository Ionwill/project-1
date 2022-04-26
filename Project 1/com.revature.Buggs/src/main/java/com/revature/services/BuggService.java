package com.revature.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.revature.exceptions.BuggNotFoundException;
import com.revature.models.Bugg;
import com.revature.repositories.BuggRepository;
import com.revature.repositories.UserRepository;

@Service
public class BuggService {

	private BuggRepository br;

	@Autowired
	public BuggService(BuggRepository br) {
		super();
		this.br = br;
	}

	public List<Bugg> getAll() {
		return br.findAll();
	}

	public Bugg getById(int id) throws BuggNotFoundException {
		Bugg bugg;
		bugg = br.findById(id).orElseThrow(BuggNotFoundException::new);
		return bugg;
	}

	public List<Bugg> getBuggsByFam(String fam) {
		if (br.findBuggsByFam(fam).isEmpty()) {
			throw new BuggNotFoundException();
		}
		return br.findBuggsByFam(fam);
	}

	public List<Bugg> getBuggsByHab(String hab) {
		if (br.findBuggsByHab(hab).isEmpty()) {
			throw new BuggNotFoundException();
		}
		return br.findBuggsByHab(hab);
	}

	@Transactional
	public boolean deleteBuggById(int id) throws BuggNotFoundException {
		br.deleteById(id);
		return true;
	}

	@Transactional
	public Bugg createBugg(Bugg bugg) {
		return br.save(bugg);
	}

	@Transactional
	public Bugg updateBugg(int id, Bugg bugg) throws BuggNotFoundException {
		Bugg b = br.findById(id).orElseThrow(BuggNotFoundException::new);

		bugg.setid(b.getid());

		return br.save(bugg);

	}

}
