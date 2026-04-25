package ru.aston.task2.hateoas;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ru.aston.task2.controller.UserControllerImpl;
import ru.aston.task2.dto.UserCreateRequest;
import ru.aston.task2.dto.UserResponse;
import ru.aston.task2.dto.UserUpdateRequest;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserRepresentationAssembler implements RepresentationModelAssembler<UserResponse, EntityModel<UserResponse>> {

    @Override
    public EntityModel<UserResponse> toModel(UserResponse dto) {
        return EntityModel.of(
                dto,
                linkTo(methodOn(UserControllerImpl.class).getById(dto.id())).withSelfRel(),
                linkTo(methodOn(UserControllerImpl.class).getAll()).withRel("users"),
                linkTo(methodOn(UserControllerImpl.class).update(dto.id(), (UserUpdateRequest) null)).withRel("update"),
                linkTo(methodOn(UserControllerImpl.class).delete(dto.id())).withRel("delete"),
                linkTo(methodOn(UserControllerImpl.class).create((UserCreateRequest) null)).withRel("create")
        );
    }

    public CollectionModel<EntityModel<UserResponse>> toCollectionModel(List<UserResponse> dtos) {
        List<EntityModel<UserResponse>> models = dtos.stream().map(this::toModel).toList();

        return CollectionModel.of(
                models,
                linkTo(methodOn(UserControllerImpl.class).getAll()).withSelfRel(),
                linkTo(methodOn(UserControllerImpl.class).create((UserCreateRequest) null)).withRel("create")
        );
    }
}