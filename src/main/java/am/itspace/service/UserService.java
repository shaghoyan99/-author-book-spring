package am.itspace.service;

import am.itspace.model.User;
import am.itspace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    public User getOne(int id) {
        return userRepository.getOne(id);
    }

    public Page<User> findAllPageUser(PageRequest pageRequest) {
        return userRepository.findAll(pageRequest);
    }
    public List<User> findAll() {
        return userRepository.findAll();
    }


    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
