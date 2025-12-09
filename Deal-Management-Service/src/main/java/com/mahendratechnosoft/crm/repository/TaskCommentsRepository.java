package com.mahendratechnosoft.crm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahendratechnosoft.crm.entity.TaskComments;


@Repository
public interface TaskCommentsRepository extends JpaRepository<TaskComments, String>{
	Page<TaskComments> findByTaskId(String taskId, Pageable pageable);
}
