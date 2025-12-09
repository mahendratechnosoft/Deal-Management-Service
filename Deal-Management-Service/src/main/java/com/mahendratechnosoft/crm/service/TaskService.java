package com.mahendratechnosoft.crm.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.mahendratechnosoft.crm.config.UserDetailServiceImp;
import com.mahendratechnosoft.crm.dto.TaskDto;
import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.Employee;
import com.mahendratechnosoft.crm.entity.ModuleAccess;
import com.mahendratechnosoft.crm.entity.Task;
import com.mahendratechnosoft.crm.entity.TaskAttachment;
import com.mahendratechnosoft.crm.entity.TaskComments;
import com.mahendratechnosoft.crm.entity.Hospital.Donors;
import com.mahendratechnosoft.crm.repository.CommentsAttachmentRepository;
import com.mahendratechnosoft.crm.repository.EmployeeRepository;
import com.mahendratechnosoft.crm.repository.TaskAttachmentRepository;
import com.mahendratechnosoft.crm.repository.TaskCommentsRepository;
import com.mahendratechnosoft.crm.repository.TaskRepository;
import com.mahendratechnosoft.crm.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class TaskService {

    private final UserRepository userRepository;

    private final UserDetailServiceImp userDetailServiceImp;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private TaskAttachmentRepository taskAttachmentRepository;
	
	@Autowired
	private TaskCommentsRepository taskCommentsRepository;
	
	@Autowired
	private CommentsAttachmentRepository commentsAttachmentRepository;

    TaskService(UserDetailServiceImp userDetailServiceImp, UserRepository userRepository) {
        this.userDetailServiceImp = userDetailServiceImp;
        this.userRepository = userRepository;
    }

	@Transactional
    public Task createTask(Object loginUser,TaskDto request) {
		
		String adminId = null;
		String employeeId = null;
		String name = null;
		if (loginUser instanceof Admin admin) {
			adminId = admin.getAdminId();
			name = admin.getName();
		} else if (loginUser instanceof Employee employee) {
			adminId = employee.getAdmin().getAdminId();
			employeeId = employee.getEmployeeId();
			name = employee.getName();
		}
		
		Task task = request.getTask();
		task.setAdminId(adminId);
		task.setEmployeeId(employeeId);
		task.setCreatedBy(name);
		
		
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
        
        Task savedTask = taskRepository.save(task);
        
        List<TaskAttachment> attachments = new LinkedList<>();
        if(request.getTaskAttachments() != null) {
	        	
	        for (TaskAttachment taskAttachment : request.getTaskAttachments()) {
	        	System.out.println("check");
				taskAttachment.setTaskId(savedTask.getTaskId());
				taskAttachment.setUploadedBy(name);
				attachments.add(taskAttachment);
			}
	        taskAttachmentRepository.saveAll(attachments);
        }
        return savedTask;
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
			else if(moduleAccess.isTaskViewAll()) {
				taskPage = taskRepository.findByAdminId(adminId, search, pageable);
				
			} 
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
	
	
	@Transactional
    public Task updateTask(Task task) {
		
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


	public ResponseEntity<?> getTaskById(String taskId) {
		try {
			Optional<Task> task = taskRepository.findById(taskId);
			if(task.isEmpty()){
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Task is not found for taskId : "+ taskId ); 
			}
			return ResponseEntity.ok(task.get());

		} catch (Exception e) {
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}
	}


	public ResponseEntity<?> deleteTaskById(String taskId) {

		try {
			taskRepository.deleteById(taskId);
			return ResponseEntity.ok("Deleted Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}


	public ResponseEntity<?> addAttachmentToTask(Object loginUser, List<TaskAttachment> request) {
		try {
			
		
		final String uploadedBy = 
	            (loginUser instanceof Admin admin) ? admin.getName()
	          : (loginUser instanceof Employee employee) ? employee.getName()
	          : null;

	    // Validate max 4 attachments
	    if (request != null && request.size() > 4) {
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body("Error: Only 4 attachments are allowed at a time.");
	    }

	    // Update fields using Streams
	    List<TaskAttachment> attachments = request.stream()
	            .peek(a -> {
	                a.setUploadedBy(uploadedBy);
	            })
	            .collect(Collectors.toList());

	    taskAttachmentRepository.saveAll(attachments);

	    return ResponseEntity.ok("All attachments added successfully.");
	    
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}
	}


	public ResponseEntity<?> getAttachmentByTaskId(String taskId) {
		try {
			List<TaskAttachment> byTaskId = taskAttachmentRepository.findByTaskId(taskId);
			return ResponseEntity.ok(byTaskId);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error "+e.getMessage());
		}
		
	}

	public ResponseEntity<?> deleteTaskAttachement(String taskAttachmentId) {
		try {
			taskAttachmentRepository.deleteById(taskAttachmentId);
			return ResponseEntity.ok("Attachment deleted sucessfully...");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error "+e.getMessage());
		}
	}

	public ResponseEntity<?> addCommentOnTask(Object loginUser, TaskComments taskComments) {
		
		try {
			
			String adminId = null;
			String employeeId = null;
			String name=null;
			
			if (loginUser instanceof Admin admin) {
				adminId = admin.getAdminId();
				name = admin.getName();
			} else if (loginUser instanceof Employee employee) {
				adminId = employee.getAdmin().getAdminId();
				employeeId = employee.getEmployeeId();
				name = employee.getName();
			}
			
			
			if (taskComments.getAttachments() != null && taskComments.getAttachments().size() > 4) {
		        return ResponseEntity
		                .status(HttpStatus.BAD_REQUEST)
		                .body("Error: Only 4 attachments are allowed at a time.");
		    }
			if (taskComments.getAttachments() != null) {
			    taskComments.getAttachments().stream()
			        .forEach(attachment -> attachment.setTaskComment(taskComments));
			}
			
			
			taskComments.setAdminId(adminId);
			taskComments.setEmployeeId(employeeId);
			taskComments.setCommentedBy(name);
			TaskComments saved = taskCommentsRepository.save(taskComments);
			
			return ResponseEntity.ok(saved);
		
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error "+e.getMessage());
		}
	}

	public Page<TaskComments> getAllCommentByTaskIs(String taskId, Pageable pageable) {		
		return taskCommentsRepository.findByTaskId(taskId, pageable);
	}

	public ResponseEntity<?> deleteCommentOnTask(String commentId, Object loginUser) {
	    try {
	        TaskComments comment = taskCommentsRepository.findById(commentId)
	                .orElseThrow(() -> new RuntimeException("Comment not found"));

	        if (loginUser instanceof Admin admin) {
	            
	            if (comment.getAdminId() == null || !comment.getAdminId().equals(admin.getAdminId())) {
	                return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                        .body("Unauthorized: You cannot delete comments from other organizations.");
	            }
	            taskCommentsRepository.delete(comment);

	            return ResponseEntity.ok("Comment deleted permanently by Admin.");

	        } 
	        else if (loginUser instanceof Employee employee) {

	            if (comment.getEmployeeId() == null || !comment.getEmployeeId().equals(employee.getEmployeeId())) {
	                return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                        .body("Unauthorized: You can only delete your own comments.");
	            }
	            
	            if(comment.isDeleted()) {
	            	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                        .body("Comment already deleted");
	            }

	            LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
	            long minutesElapsed = java.time.Duration.between(comment.getCommentedAt(), now).toMinutes();

	            if (minutesElapsed > 60) {
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                        .body("Time limit exceeded: You can only delete comments within 1 hour of posting.");
	            }
	            comment.setDeleted(true);
	            if (comment.getAttachments() != null) {
	                comment.getAttachments().clear();
	            }

	            taskCommentsRepository.save(comment);

	            return ResponseEntity.ok("Comment deleted successfully (Soft Delete).");
	        } 
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid User Type");

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Error deleting comment: " + e.getMessage());
	    }
	}
	
	
	public ResponseEntity<?> deleteTaskCommentAttachement(String commentAttachmentId) {
		try {
			commentsAttachmentRepository.deleteById(commentAttachmentId);
	        return ResponseEntity.ok("Comment and its attachments deleted successfully.");

	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Error deleting comment: " + e.getMessage());
	    }
	}
	
	public ResponseEntity<?> updateCommentOfTask(Object loginUser, TaskComments taskComments) {
		
		try {
			
			String adminId = 
		            (loginUser instanceof Admin admin) ? admin.getAdminId()
		          : (loginUser instanceof Employee employee) ? employee.getAdmin().getAdminId()
		          : null;

			if (taskComments.getAttachments() != null && taskComments.getAttachments().size() > 4) {
		        return ResponseEntity
		                .status(HttpStatus.BAD_REQUEST)
		                .body("Error: Only 4 attachments are allowed at a time.");
		    }
			if (taskComments.getAttachments() != null) {
			    taskComments.getAttachments().stream()
			        .forEach(attachment -> attachment.setTaskComment(taskComments));
			}
			
			
			taskComments.setAdminId(adminId);
			TaskComments saved = taskCommentsRepository.save(taskComments);
			
			return ResponseEntity.ok(saved);
		
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error "+e.getMessage());
		}
	}
	

	
}
