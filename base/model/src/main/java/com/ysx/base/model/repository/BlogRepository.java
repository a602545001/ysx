package com.ysx.base.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.ysx.base.model.entity.Blog;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
	Blog findById(Integer id);
}