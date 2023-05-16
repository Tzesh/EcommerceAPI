package com.tzesh.ecommerceapi.service;

import com.tzesh.ecommerceapi.base.entity.field.BaseAuditableFields;
import com.tzesh.ecommerceapi.base.error.GenericErrorMessage;
import com.tzesh.ecommerceapi.base.exception.BaseException;
import com.tzesh.ecommerceapi.base.exception.NotFoundException;
import com.tzesh.ecommerceapi.base.service.BaseService;
import com.tzesh.ecommerceapi.dto.UserDTO;
import com.tzesh.ecommerceapi.entity.User;
import com.tzesh.ecommerceapi.enums.message.UserErrorMessage;
import com.tzesh.ecommerceapi.enums.auth.RoleEnum;
import com.tzesh.ecommerceapi.mapper.UserMapper;
import com.tzesh.ecommerceapi.repository.UserRepository;
import com.tzesh.ecommerceapi.request.user.CreateUserRequest;
import com.tzesh.ecommerceapi.request.user.RemoveUserRequest;
import com.tzesh.ecommerceapi.request.user.UpdateUserRequest;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Service for {@link User} objects.
 *
 * @author tzesh
 */
@Service
public class UserService extends BaseService<User, UserDTO, UserRepository, UserMapper> {
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor for the service
     *
     * @param repository Repository for the service
     */
    @Autowired
    public UserService(UserRepository repository, UserDetailsService service, PasswordEncoder encoder) {
        super(repository, service);
        this.passwordEncoder = encoder;
    }

    /**
     * Initialize mapper for the service
     *
     * @return class of the mapper to be initialized
     */
    @Override
    protected UserMapper initializeMapper() {
        return Mappers.getMapper(UserMapper.class);
    }

    /**
     * Update current user
     *
     * @param request Update user request
     * @return UserDTO
     * @throws RuntimeException if email already exists
     * @see UpdateUserRequest
     */
    public UserDTO updateCurrentUser(UpdateUserRequest request) {
        // get current user
        User user = repository.findByUsername(this.getCurrentUser()).orElseThrow(
                () -> new NotFoundException(
                        GenericErrorMessage.builder().message(
                                UserErrorMessage.USER_NOT_FOUND.getMessage()
                        ).build()
                )
        );

        // get current user id
        Long id = user.getId();

        // update user
        return this.updateUser(id, request);
    }

    /**
     * Get current user from the database
     *
     * @return UserDTO
     */
    public UserDTO getCurrentUserDTO() {
        // get user
        User user = repository.findByUsername(this.getCurrentUser()).orElseThrow(
                () -> new NotFoundException(
                        GenericErrorMessage.builder().message(
                                UserErrorMessage.USER_NOT_FOUND.getMessage()
                        ).build()
                )
        );

        // return user
        return mapper.toDTO(user);
    }

    /**
     * Create a new user in the database
     *
     * @param request request for the new user
     * @return User
     */
    public UserDTO createUser(CreateUserRequest request) {
        // control if email or username is already used
        if (repository.existsByUsernameOrEmail(request.username(), request.email())) {
            throw new BaseException(
                    GenericErrorMessage.builder().message(
                            UserErrorMessage.USER_ALREADY_EXISTS.getMessage()
                    ).build()
            );
        }

        // create user
        User user = new User();
        user.setUsername(request.username());
        user.setName(request.name());
        user.setEmail(request.email());
        user.setRole(RoleEnum.valueOf(request.role().name()));
        user.setTelephone(request.telephone());
        user.setType(request.type());
        user.setPassword(passwordEncoder.encode(request.password()));

        // control if user is exists with username, email or telephone
        if (repository.existsByUsernameOrEmailOrTelephone(user.getUsername(), user.getEmail(), user.getTelephone())) {
            throw new BaseException(
                    GenericErrorMessage.builder()
                            .message("User with username, email or telephone already exists")
                            .build()
            );
        }

        // create base auditable fields
        BaseAuditableFields baseAuditableFields = new BaseAuditableFields();

        // set base auditable fields
        baseAuditableFields.setCreatedBy(user.getUsername());
        baseAuditableFields.setCreatedDate(LocalDateTime.now());

        // set base auditable fields to user
        user.setAuditableFields(baseAuditableFields);

        // save user and return
        return mapper.toDTO(this.trySave(user));
    }

    /**
     * Update user in the database
     *
     * @param id      id of the user
     * @param request request for the user
     * @return User
     * @see UpdateUserRequest
     */
    public UserDTO updateUser(Long id, UpdateUserRequest request) {
        // check if the entity exists
        this.checkIfEntityExists(id);

        // get user
        User user = repository.findById(id).get();

        // check if email is changed
        if (!user.getEmail().equals(request.email())) {
            // control if email is already used
            if (repository.existsByUsernameOrEmail(null, request.email())) {
                throw new BaseException(
                        GenericErrorMessage.builder().message(
                                UserErrorMessage.USER_ALREADY_EXISTS.getMessage()
                        ).build()
                );
            }
        } else if (!user.getTelephone().equals(request.telephone())) {
            // control if username is already used
            if (repository.existsByTelephone(request.telephone())) {
                throw new BaseException(
                        GenericErrorMessage.builder().message(
                                UserErrorMessage.USER_ALREADY_EXISTS.getMessage()
                        ).build()
                );
            }
        }

        // update user
        user.setName(request.name());
        user.setEmail(request.email());
        user.setTelephone(request.telephone());
        user.setType(request.type());
        user.setPassword(passwordEncoder.encode(request.password()));

        // update base auditable fields
        BaseAuditableFields baseAuditableFields = user.getAuditableFields();

        // set base auditable fields
        baseAuditableFields.setUpdatedBy(this.getCurrentUser());
        baseAuditableFields.setUpdatedDate(LocalDateTime.now());

        // save user and return
        return mapper.toDTO(this.trySave(user));
    }

    /**
     * Delete user from the database by telephone and username
     *
     * @param request request for the user
     * @return User
     * @see RemoveUserRequest
     */
    public UserDTO deleteUser(RemoveUserRequest request) {
        // get user
        User user = repository.findByUsernameAndTelephone(request.username(), request.telephone()).orElseThrow(
                () -> new NotFoundException(
                        GenericErrorMessage.builder().message(
                                UserErrorMessage.USER_NOT_FOUND.getMessage()
                        ).build()
                )
        );

        repository.delete(user);

        return mapper.toDTO(user);
    }

    /**
     * Get user from the database by username
     *
     * @param username username of the user
     * @return User
     */
    public UserDTO getUserByUsername(String username) {
        // get user
        User user = repository.findByUsername(username).orElseThrow(
                () -> new NotFoundException(
                        GenericErrorMessage.builder().message(
                                UserErrorMessage.USER_NOT_FOUND.getMessage()
                        ).build()
                )
        );

        return mapper.toDTO(user);
    }
}
