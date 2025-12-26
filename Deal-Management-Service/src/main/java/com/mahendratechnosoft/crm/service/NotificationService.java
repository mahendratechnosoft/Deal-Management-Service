package com.mahendratechnosoft.crm.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.mahendratechnosoft.crm.dto.TaskNotifcation;

@Service
public class NotificationService {

	private final SimpMessagingTemplate messagingTemplate;

	public NotificationService(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	public void sendTaskNotification(TaskNotifcation taskNotification) {

		for (String employeeId : taskNotification.getAssignTo()) {

			messagingTemplate.convertAndSend("/topic/createTasknotifications/" + employeeId, taskNotification);
		}

	}
	
	

	public void sendTaskStatusNotification(TaskNotifcation taskNotification) {

		if (taskNotification.getEmployeeId() != null) {
			messagingTemplate.convertAndSend(
					"/topic/updateTasknotificationsToEmployee/" + taskNotification.getEmployeeId(), taskNotification);
		}

		messagingTemplate.convertAndSend("/topic/updateTasknotificationsToAdmin/" + taskNotification.getAdminId(),
				taskNotification);
	}
}
