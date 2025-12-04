package com.mahendratechnosoft.crm.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.Employee;
import com.mahendratechnosoft.crm.entity.ModuleAccess;
import com.mahendratechnosoft.crm.entity.Task;
import com.mahendratechnosoft.crm.entity.Hospital.Donors;
import com.mahendratechnosoft.crm.repository.EmployeeRepository;
import com.mahendratechnosoft.crm.repository.TaskRepository;

import jakarta.transaction.Transactional;

@Service
public class TaskService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private TaskRepository taskRepository;

	@Transactional
    public Task createTask(Task task) {
		
        if (task.getAssignedEmployees() != null && !task.getAssignedEmployees().isEmpty()) {
            Set<String> assigneeIds = task.getAssignedEmployees().stream()
                    .map(Employee::getEmployeeId)
                    .collect(Collectors.toSet());
            
            // Fetch real entities
            Set<Employee> realAssignees = new HashSet<>(employeeRepository.findAllById(assigneeIds));
            task.setAssignedEmployees(realAssignees);
        }

        // 2. Handle Followers
        if (task.getFollowersEmployees() != null && !task.getFollowersEmployees().isEmpty()) {
            Set<String> followerIds = task.getFollowersEmployees().stream()
                    .map(Employee::getEmployeeId)
                    .collect(Collectors.toSet());

            Set<Employee> realFollowers = new HashSet<>(employeeRepository.findAllById(followerIds));
            task.setFollowersEmployees(realFollowers);
        }

        return taskRepository.save(task);
    }
	
	
	public ResponseEntity<?> getAllTaskList(int page, int size, Object loginUser, String search) {

		try {
            ModuleAccess moduleAccess=null;
			String role = "ROLE_EMPLOYEE";
			String adminId = null;
			String employeeId = null;
			Page<Task> taskPage = null;
			if (loginUser instanceof Admin admin) {
				role = admin.getUser().getRole();
				adminId = admin.getAdminId();
			} else if (loginUser instanceof Employee employee) {
				adminId = employee.getAdmin().getAdminId();
				employeeId = employee.getEmployeeId();
				moduleAccess=employee.getModuleAccess();
			}

			// 2. Fetch paginated leads for company
			Pageable pageable = PageRequest.of(page, size);
			if (role.equals("ROLE_ADMIN")) {
				taskPage = taskRepository.findByAdminId(adminId, search, pageable);

			}
//			else if(moduleAccess.isDonorViewAll()) {
//				donorPage = donorsRepository.findByAdminId(adminId, search, status,pageable);
//				
//			} 
			else {

				taskPage = taskRepository.findByEmployeeId(employeeId, search, pageable);
			}
			// 3. Prepare response
			Map<String, Object> response = new HashMap<>();

			response.put("taskList", taskPage.getContent());
			response.put("currentPage", taskPage.getNumber());
			response.put("totalElement", taskPage.getTotalElements());
			response.put("totalPages", taskPage.getTotalPages());

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}

}
