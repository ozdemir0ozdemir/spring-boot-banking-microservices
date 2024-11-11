package ozdemir0zdemir.userservice.domain;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ozdemir0zdemir.userservice.exception.EmailAlreadyRegisteredException;
import ozdemir0zdemir.userservice.exception.UserNotFoundException;
import ozdemir0zdemir.userservice.request.CreateUser;
import ozdemir0zdemir.userservice.response.UserRead;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Transactional
    public String createUser(CreateUser request) throws EmailAlreadyRegisteredException {

        log.info("Create user requested for email: {}", request.email());
        throwIfEmailExists(request.email());
        log.trace("Email not registered. Creating user for request: {}", request);

        User user = User.from(request);
        this.userRepository.save(user);

        log.info("User created for email: {}", user.getEmail());
        return user.getEmail();
    }

    public UserRead readUserById(Long id) throws UserNotFoundException {

        log.info("Read user by id requested for id: {}", id);
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        log.trace("User found for id: {}", id);
        return new UserRead(
                user.getEmail(),
                user.getContactNo(),
                user.getStatus(),
                user.getUserProfile().getFirstName(),
                user.getUserProfile().getLastName(),
                user.getUserProfile().getGender()
        );
    }

    public UserRead readUserByEmail(String email) throws EmailAlreadyRegisteredException {

        log.info("Read user by email requested for email: {}", email);
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        log.trace("User found for email: {}", email);
        return new UserRead(
                user.getEmail(),
                user.getContactNo(),
                user.getStatus(),
                user.getUserProfile().getFirstName(),
                user.getUserProfile().getLastName(),
                user.getUserProfile().getGender()
        );
    }

    // TODO: Read User By ...
    // TODO: Real All Users
    // TODO: Update User Status

    private void throwIfEmailExists(String email) {
        userRepository.findByEmail(email)
                .ifPresent(_ -> {
                    throw new EmailAlreadyRegisteredException(email);
                });
    }
}
