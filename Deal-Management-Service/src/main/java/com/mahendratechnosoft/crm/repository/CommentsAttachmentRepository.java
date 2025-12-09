package com.mahendratechnosoft.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahendratechnosoft.crm.entity.CommentsAttachment;

@Repository
public interface CommentsAttachmentRepository extends JpaRepository<CommentsAttachment, String>{

}
