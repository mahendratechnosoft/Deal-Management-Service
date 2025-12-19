package com.mahendratechnosoft.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahendratechnosoft.crm.entity.TaskAttachment;
import com.mahendratechnosoft.crm.entity.VendorAttachment;

@Repository
public interface VendorAttachmentRepository extends JpaRepository<VendorAttachment, String>{

	List<VendorAttachment> findByVendorId(String vendorId);

}
