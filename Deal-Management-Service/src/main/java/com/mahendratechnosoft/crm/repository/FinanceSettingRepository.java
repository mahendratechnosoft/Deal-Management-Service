package com.mahendratechnosoft.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahendratechnosoft.crm.entity.FinanceSetting;
import com.mahendratechnosoft.crm.enums.TYPE;

@Repository
public interface FinanceSettingRepository  extends JpaRepository<FinanceSetting, String>{

	FinanceSetting findByAdminIdAndType(String adminId,TYPE type);
}
