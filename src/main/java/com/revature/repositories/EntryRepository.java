package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.models.Entry;
import com.revature.models.User;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Integer>{
	List<Entry> findByUser(User user);
}
