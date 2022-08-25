package io.testframework.test_framework.service;

import io.testframework.test_framework.domain.Asset;
import io.testframework.test_framework.domain.User;
import io.testframework.test_framework.model.UserDTO;
import io.testframework.test_framework.repos.AssetRepository;
import io.testframework.test_framework.repos.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final AssetRepository assetRepository;

    public UserService(final UserRepository userRepository, final AssetRepository assetRepository) {
        this.userRepository = userRepository;
        this.assetRepository = assetRepository;
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .collect(Collectors.toList());
    }

    public UserDTO get(final UUID id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public UUID create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final UUID id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final UUID id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setFirstname(user.getFirstname());
        userDTO.setLastname(user.getLastname());
        userDTO.setEmail(user.getEmail());
        userDTO.setRegistredAt(user.getRegistredAt());
        userDTO.setDeletedAt(user.getDeletedAt());
        userDTO.setUserAssets(user.getUserAssetAssets() == null ? null : user.getUserAssetAssets().stream()
                .map(asset -> asset.getId())
                .collect(Collectors.toList()));
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setEmail(userDTO.getEmail());
        user.setRegistredAt(userDTO.getRegistredAt());
        user.setDeletedAt(userDTO.getDeletedAt());
        if (userDTO.getUserAssets() != null) {
            final List<Asset> userAssets = assetRepository.findAllById(userDTO.getUserAssets());
            if (userAssets.size() != userDTO.getUserAssets().size()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "one of userAssets not found");
            }
            user.setUserAssetAssets(userAssets.stream().collect(Collectors.toSet()));
        }
        return user;
    }

}
