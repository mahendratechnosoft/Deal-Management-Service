package com.mahendratechnosoft.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahendratechnosoft.crm.entity.TaskAttachment;

@Repository
public interface TaskAttachmentRepository extends JpaRepository<TaskAttachment, String>{

	List<TaskAttachment> findByTaskId(String taskId);

}
