package com.ysx.admin.ui;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.ysx.base.model.entity.Blog;

public interface PeopleRepository extends JpaRepository<People, Integer> {
	
}