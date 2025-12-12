package com.mahendratechnosoft.crm.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
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
import com.mahendratechnosoft.crm.entity.TaskTimeLog;
import com.mahendratechnosoft.crm.entity.Hospital.Donors;
import com.mahendratechnosoft.crm.enums.TaskStatus;
import com.mahendratechnosoft.crm.enums.TimeLogStatus;
import com.mahendratechnosoft.crm.repository.CommentsAttachmentRepository;
import com.mahendratechnosoft.crm.repository.EmployeeRepository;
import com.mahendratechnosoft.crm.repository.TaskAttachmentRepository;
import com.mahendratechnosoft.crm.repository.TaskCommentsRepository;
import com.mahendratechnosoft.crm.repository.TaskRepository;
import com.mahendratechnosoft.crm.repository.TaskTimeLogRepository;
import com.mahendratechnosoft.crm.repository.UserRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

@Service
public class TaskService {
	
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
	
	@Autowired
	private TaskTimeLogRepository taskTimeLogRepository;
	
	@Autowired
	private ExcelService excelService;
	
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
		task.setStatus(TaskStatus.NOT_STARTED);
		
		
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
	
	
	public ResponseEntity<?> getAllTaskList(int page, int size, Object loginUser, String search,TaskStatus status,String listType) {

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

			Pageable pageable = PageRequest.of(page, size);
			
			if (role.equals("ROLE_ADMIN")) {
				taskPage = taskRepository.findByAdminId(adminId,status, search, pageable);
			}
			else if(moduleAccess.isTaskViewAll()) {
				taskPage = taskRepository.findByAdminId(adminId,status, search, pageable);
			} 
			else {
				taskPage = taskRepository.findTasksForEmployee(adminId,employeeId,status, search,listType, pageable);
			}

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


	public ResponseEntity<?> getTaskById(String taskId, Object loginUser) {
	    try {
	        Optional<Task> taskOptional = taskRepository.findById(taskId);
	        if (taskOptional.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Task is not found");
	        }
	        Task task = taskOptional.get();
	        boolean canStartTimer = false;
	        boolean canEdit = false;

	        if (loginUser instanceof Admin) {
	            canStartTimer = true; 
	            canEdit = true;
	        } else if (loginUser instanceof Employee employee) {
	            String empId = employee.getEmployeeId();
	            boolean isOwner = (task.getEmployeeId() != null) && task.getEmployeeId().equals(empId);
	            boolean isAssignee = task.getAssignedEmployees().stream()
	                    .anyMatch(emp -> emp.getEmployeeId().equals(empId));
	            canStartTimer = isOwner || isAssignee;
	            canEdit = isOwner;
	        }

	        Map<String, Object> response = new HashMap<>();
	        response.put("task", task);
	        response.put("canStartTimer", canStartTimer);
	        response.put("canEdit", canEdit);
	        return ResponseEntity.ok(response);

	    } catch (Exception e) {
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
	
	public ResponseEntity<?> startTimer(String taskId, Object loginUser) {
	    try {
	        String adminId = null;
	        String employeeId = null;
	        String name = null;

	        // Identify User
	        if (loginUser instanceof Admin admin) {
	            adminId = admin.getAdminId();
	            name = admin.getName();
	        } else if (loginUser instanceof Employee employee) {
	            adminId = employee.getAdmin().getAdminId();
	            employeeId = employee.getEmployeeId();
	            name = employee.getName();
	        }

	        // 1. Check for CURRENT active log based on Role
	        Optional<TaskTimeLog> currentActiveLog;
	        if (employeeId == null) {
	            currentActiveLog = taskTimeLogRepository.findActiveLogForAdmin(adminId, TimeLogStatus.ACTIVE);
	        } else {
	            currentActiveLog = taskTimeLogRepository.findByEmployeeIdAndStatus(employeeId, TimeLogStatus.ACTIVE);
	        }

	        // 2. Auto-stop existing timer
	        if (currentActiveLog.isPresent()) {
	            TaskTimeLog activeLog = currentActiveLog.get();
	            if (activeLog.getTaskId().equals(taskId)) {
	                return ResponseEntity.badRequest().body("Timer is already running for this task.");
	            }
	            stopActiveLogLogic(activeLog, "Auto-stopped because new task (" + taskId + ") was started.");
	        }

	        // 3. Start New Timer
	        Optional<Task> taskOptional = taskRepository.findById(taskId);
	        if (taskOptional.isEmpty()) {
	            return ResponseEntity.badRequest().body("Task not found.");
	        }
	        
	        Task task = taskOptional.get();
	        // Update task status if needed
	        if (task.getStatus() == TaskStatus.NOT_STARTED) {
	            task.setStatus(TaskStatus.IN_PROGRESS);
	            taskRepository.save(task);
	        }

	        TaskTimeLog log = new TaskTimeLog();
	        log.setTaskId(taskId);
	        log.setAdminId(adminId);
	        log.setEmployeeId(employeeId);
	        log.setName(name);
	        log.setStartTime(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
	        log.setStatus(TimeLogStatus.ACTIVE);

	        return ResponseEntity.ok(taskTimeLogRepository.save(log));

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
	    }
	}
	
	public ResponseEntity<?> stopTimer(String taskId, Object loginUser, String endNote) {
	    try {
	        String adminId = null;
	        String employeeId = null;
	        boolean isAdmin = false;

	        if (loginUser instanceof Admin admin) {
	            adminId = admin.getAdminId();
	            isAdmin = true;
	        } else if (loginUser instanceof Employee employee) {
	            employeeId = employee.getEmployeeId();
	        }

	        Optional<TaskTimeLog> activeLogOptional;
	        
	        if (isAdmin) {
	            activeLogOptional = taskTimeLogRepository.findActiveLogForAdminByTask(taskId, adminId, TimeLogStatus.ACTIVE);
	        } else {
	            activeLogOptional = taskTimeLogRepository.findByTaskIdAndEmployeeIdAndStatus(taskId, employeeId, TimeLogStatus.ACTIVE);
	        }

	        if (activeLogOptional.isEmpty()) {
	            return ResponseEntity.badRequest().body("No active timer found to stop for this task.");
	        }

	        TaskTimeLog activeLog = activeLogOptional.get();
	        long minutes = stopActiveLogLogic(activeLog, endNote);

	        return ResponseEntity.ok("Timer stopped. Worked for: " + minutes + " minutes.");

	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
	    }
	}
	
	private long stopActiveLogLogic(TaskTimeLog activeLog, String endNote) {
	    LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
	    activeLog.setEndTime(now);
	    long minutes = Duration.between(activeLog.getStartTime(), now).toMinutes();
	    activeLog.setDurationInMinutes(minutes);
	    activeLog.setEndNote(endNote);
	    activeLog.setStatus(TimeLogStatus.COMPLETED);
	    taskTimeLogRepository.save(activeLog);
	    return minutes;
	}
	
	public ResponseEntity<?> getAllTaskTimerList(Object loginUser, String taskId) {

		try {
            ModuleAccess moduleAccess=null;
			String role = "ROLE_EMPLOYEE";
			String adminId = null;
			String employeeId = null;
			if (loginUser instanceof Admin admin) {
				role = admin.getUser().getRole();
				adminId = admin.getAdminId();
			} else if (loginUser instanceof Employee employee) {
				adminId = employee.getAdmin().getAdminId();
				employeeId = employee.getEmployeeId();
				moduleAccess=employee.getModuleAccess();
			}
			
			List<TaskTimeLog> response = new LinkedList<>();
			
			if (role.equals("ROLE_ADMIN") || moduleAccess.isTaskViewAll()) {
				response = taskTimeLogRepository.findByTaskIdAndAdminIdOrderByStartTimeDesc(taskId, adminId);
			} else {
				response = taskTimeLogRepository.findByTaskIdAndEmployeeIdOrderByStartTimeDesc(taskId, employeeId);
			}

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	public ResponseEntity<?> getActiveTimer(Object loginUser) {
		Optional<TaskTimeLog> activeLog = Optional.empty();

        if (loginUser instanceof Admin admin) {
            activeLog = taskTimeLogRepository.findActiveLogForAdmin(
                admin.getAdminId(), 
                TimeLogStatus.ACTIVE
            );
        } else if (loginUser instanceof Employee employee) {
            activeLog = taskTimeLogRepository.findByEmployeeIdAndStatus(
                employee.getEmployeeId(), 
                TimeLogStatus.ACTIVE
            );
        }
	        
	    if(activeLog.isPresent()) {
	    	TaskTimeLog taskTimeLog = activeLog.get();
	    	String taskId = taskTimeLog.getTaskId();
	    	Optional<Task> task = taskRepository.findById(taskId);
	    	if (task.isPresent()) {
	    		Task task2 = task.get();
		    	HashMap<String, Object> responce = new HashMap<>();
		    	responce.put("taskSubject", task2.getSubject());
		    	responce.put("taskDescription", task2.getDescription());
		    	responce.put("taskStatus", task2.getStatus());
		    	responce.put("taskLogId", taskTimeLog.getTaskLogId());
		    	responce.put("employeeId", taskTimeLog.getEmployeeId());
		    	responce.put("name", taskTimeLog.getName());
		    	responce.put("startTime", taskTimeLog.getStartTime());
		    	
		        return ResponseEntity.ok(responce);
				
			}
	    }
	    return ResponseEntity.noContent().build();
	}
	
	public ResponseEntity<?> getActiveTimerForTask(String taskId, Object loginUser) {
	    try {
	        Optional<TaskTimeLog> activeLog = Optional.empty();
	        if (loginUser instanceof Admin admin) {
	            activeLog = taskTimeLogRepository.findActiveLogForAdminByTask(
	                taskId, 
	                admin.getAdminId(), 
	                TimeLogStatus.ACTIVE
	            );
	        } else if (loginUser instanceof Employee employee) {
	            activeLog = taskTimeLogRepository.findByTaskIdAndEmployeeIdAndStatus(
	                taskId, 
	                employee.getEmployeeId(), 
	                TimeLogStatus.ACTIVE
	            );
	        }
	        if (activeLog.isPresent()) {
	            return ResponseEntity.ok(activeLog.get());
	        } else {
	            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No active timer for this task.");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
	    }
	}
	
	
	@Transactional
    public Task addAssignees(String taskId, Set<String> employeeIds) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));

        if (employeeIds != null && !employeeIds.isEmpty()) {
            List<Employee> newAssignees = employeeRepository.findAllById(employeeIds);
            task.getAssignedEmployees().addAll(newAssignees);
        }

        return taskRepository.save(task);
    }

    @Transactional
    public Task addFollowers(String taskId, Set<String> employeeIds) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));

        if (employeeIds != null && !employeeIds.isEmpty()) {
            List<Employee> newFollowers = employeeRepository.findAllById(employeeIds);
            task.getFollowersEmployees().addAll(newFollowers);
        }

        return taskRepository.save(task);
    }

    @Transactional
    public Task removeAssignee(String taskId, String employeeId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
        task.getAssignedEmployees().removeIf(emp -> emp.getEmployeeId().equals(employeeId));

        return taskRepository.save(task);
    }

    @Transactional
    public Task removeFollower(String taskId, String employeeId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
        task.getFollowersEmployees().removeIf(emp -> emp.getEmployeeId().equals(employeeId));

        return taskRepository.save(task);
    }
    
    public Task updateTaskStatus(String taskId, TaskStatus newStatus) {
        Task task = taskRepository.findById(taskId)
        		 .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
        task.setStatus(newStatus);
        return taskRepository.save(task);
    }
    
    public ResponseEntity<?> getTaskCounts(Object loginUser, String listType) {
        try {
            String role = "ROLE_EMPLOYEE";
            String adminId = null;
            String employeeId = null;
            ModuleAccess moduleAccess = null;

            if (loginUser instanceof Admin admin) {
                role = admin.getUser().getRole();
                adminId = admin.getAdminId();
            } else if (loginUser instanceof Employee employee) {
                adminId = employee.getAdmin().getAdminId();
                employeeId = employee.getEmployeeId();
                moduleAccess = employee.getModuleAccess();
            }

            List<Object[]> dbCounts;

            if (role.equals("ROLE_ADMIN")) {
                dbCounts = taskRepository.countStatusByAdminId(adminId);
            } else if (moduleAccess != null && moduleAccess.isTaskViewAll()) {
                dbCounts = taskRepository.countStatusByAdminId(adminId);
            } else {
                dbCounts = taskRepository.countStatusForEmployee(adminId, employeeId, listType);
            }

            Map<String, Long> responseMap = new LinkedHashMap<>();

            for (TaskStatus status : TaskStatus.values()) {
                responseMap.put(status.name(), 0L);
            }

            for (Object[] row : dbCounts) {
                TaskStatus status = (TaskStatus) row[0];
                Long count = (Long) row[1];
                responseMap.put(status.name(), count);
                
            }
            long totalTasks = responseMap.values().stream().mapToLong(Long::longValue).sum();
            responseMap.put("TOTAL", totalTasks);

            return ResponseEntity.ok(responseMap);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
        }
    }
    
    public void exportTaskExcel(HttpServletResponse response, Object loginUser, TaskStatus status, String listType) {
        try {
            ModuleAccess moduleAccess = null;
            String role = "ROLE_EMPLOYEE";
            String adminId = null;
            String employeeId = null;
            Page<Task> taskPage = null;

            // 1. Determine User Role
            if (loginUser instanceof Admin admin) {
                role = admin.getUser().getRole();
                adminId = admin.getAdminId();
            } else if (loginUser instanceof Employee employee) {
                adminId = employee.getAdmin().getAdminId();
                employeeId = employee.getEmployeeId();
                moduleAccess = employee.getModuleAccess();
            }

            Pageable pageable = Pageable.unpaged();

            if (role.equals("ROLE_ADMIN")) {
                taskPage = taskRepository.findByAdminId(adminId, status, null, pageable);
            } 
            else if (moduleAccess != null && moduleAccess.isTaskViewAll()) {
                taskPage = taskRepository.findByAdminId(adminId, status, null, pageable);
            } 
            else {
                taskPage = taskRepository.findTasksForEmployee(adminId, employeeId, status, null, listType, pageable);
            }

            excelService.generateTaskExcel(response, taskPage.getContent());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
