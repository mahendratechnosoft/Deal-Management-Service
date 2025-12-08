package com.mahendratechnosoft.crm.dto;

import java.util.List;

import com.mahendratechnosoft.crm.entity.Task;
import com.mahendratechnosoft.crm.entity.TaskAttachment;

public class TaskDto {
	private Task task;
	private List<TaskAttachment> taskAttachments;
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	public List<TaskAttachment> getTaskAttachments() {
		return taskAttachments;
	}
	public void setTaskAttachments(List<TaskAttachment> taskAttachments) {
		this.taskAttachments = taskAttachments;
	}

}
