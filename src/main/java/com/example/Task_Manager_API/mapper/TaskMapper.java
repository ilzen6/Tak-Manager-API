package com.example.Task_Manager_API.mapper;

import com.example.Task_Manager_API.dto.request.CreateTaskRequest;
import com.example.Task_Manager_API.dto.request.UpdateTaskRequest;
import com.example.Task_Manager_API.dto.response.TaskDetailResponse;
import com.example.Task_Manager_API.dto.response.TaskResponse;
import com.example.Task_Manager_API.entity.Task;
import com.example.Task_Manager_API.enums.TaskStatus;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface TaskMapper {
    public TaskResponse toResponse(Task task);

    @Mapping(target = "ownerUsername", expression = "java(task.getUser().getUsername())")
    public TaskDetailResponse toDetailResponse(Task task);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "completedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    public Task toEntity(CreateTaskRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "completedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy =
            NullValuePropertyMappingStrategy.IGNORE)
    public void updateEntityFromRequest(UpdateTaskRequest request,
                                        @MappingTarget Task task);

    public List<TaskResponse> toResponseList(List<Task> tasks);
}
