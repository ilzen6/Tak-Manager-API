package com.example.Task_Manager_API.mapper;

import com.example.Task_Manager_API.dto.request.CreateTaskRequest;
import com.example.Task_Manager_API.dto.request.UpdateTaskRequest;
import com.example.Task_Manager_API.dto.response.TaskDetailResponse;
import com.example.Task_Manager_API.dto.response.TaskResponse;
import com.example.Task_Manager_API.entity.Task;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-02T19:01:52+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public TaskResponse toResponse(Task task) {
        if ( task == null ) {
            return null;
        }

        TaskResponse taskResponse = new TaskResponse();

        taskResponse.setId( task.getId() );
        taskResponse.setTitle( task.getTitle() );
        taskResponse.setStatus( task.getStatus() );
        taskResponse.setPriority( task.getPriority() );
        taskResponse.setDueDate( task.getDueDate() );
        taskResponse.setCreatedAt( task.getCreatedAt() );

        return taskResponse;
    }

    @Override
    public TaskDetailResponse toDetailResponse(Task task) {
        if ( task == null ) {
            return null;
        }

        TaskDetailResponse taskDetailResponse = new TaskDetailResponse();

        taskDetailResponse.setId( task.getId() );
        taskDetailResponse.setDescription( task.getDescription() );
        taskDetailResponse.setStatus( task.getStatus() );
        taskDetailResponse.setPriority( task.getPriority() );
        taskDetailResponse.setDueDate( task.getDueDate() );
        taskDetailResponse.setCreatedAt( task.getCreatedAt() );
        taskDetailResponse.setUpdatedAt( task.getUpdatedAt() );
        taskDetailResponse.setCompletedAt( task.getCompletedAt() );

        taskDetailResponse.setOwnerUsername( task.getUser().getUsername() );

        return taskDetailResponse;
    }

    @Override
    public Task toEntity(CreateTaskRequest request) {
        if ( request == null ) {
            return null;
        }

        Task task = new Task();

        task.setTitle( request.getTitle() );
        task.setDescription( request.getDescription() );
        task.setPriority( request.getPriority() );
        task.setDueDate( request.getDueDate() );

        return task;
    }

    @Override
    public void updateEntityFromRequest(UpdateTaskRequest request, Task task) {
        if ( request == null ) {
            return;
        }

        if ( request.getTitle() != null ) {
            task.setTitle( request.getTitle() );
        }
        if ( request.getDescription() != null ) {
            task.setDescription( request.getDescription() );
        }
        if ( request.getStatus() != null ) {
            task.setStatus( request.getStatus() );
        }
        if ( request.getPriority() != null ) {
            task.setPriority( request.getPriority() );
        }
        if ( request.getDueDate() != null ) {
            task.setDueDate( request.getDueDate() );
        }
    }

    @Override
    public List<TaskResponse> toResponseList(List<Task> tasks) {
        if ( tasks == null ) {
            return null;
        }

        List<TaskResponse> list = new ArrayList<TaskResponse>( tasks.size() );
        for ( Task task : tasks ) {
            list.add( toResponse( task ) );
        }

        return list;
    }
}
