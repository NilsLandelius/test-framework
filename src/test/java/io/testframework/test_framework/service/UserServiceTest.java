package io.testframework.test_framework.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.testframework.test_framework.domain.User;
import io.testframework.test_framework.model.UserDTO;
import io.testframework.test_framework.repos.AssetRepository;
import io.testframework.test_framework.repos.UserRepository;
import io.testframework.test_framework.utilities.JsonResourceReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.isA;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private AssetRepository assetRepository;

    private UserService sut;

    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    User user1 = new User();
    private List<User> userList = new ArrayList<>();

    @BeforeEach
    public void init(){
        this.sut = new UserService(userRepositoryMock,assetRepository);
        user1.setId(UUID.randomUUID());

    }

    /*
    TODO:
     - Create a objectmapper method to map json files to DTO classes
     */

    @Test
    public void findAllShouldFindUser1Test(){
        //set-up
        user1.setFirstname("Nils");
        user1.setLastname("Landelius");
        userList.add(user1);
        Mockito.when(userRepositoryMock.findAll()).thenReturn(userList);

        //verification
        List<UserDTO> dtoList = sut.findAll();
        assertAll(() -> assertEquals("Nils",dtoList.get(0).getFirstname()),
                  () -> assertEquals("Landelius",dtoList.get(0).getLastname()
        ));
    }

    @Test
    public void getTestShouldGetUser1() throws IOException{
        Mockito.when(userRepositoryMock.findById(user1.getId())).thenReturn(Optional.of(user1));
        mapJsonToUserDTO();

        assertEquals(sut.get(user1.getId()).getId(),user1.getId());
    }

    @Test()
    public void shouldReturnResponseStatusException(){
        assertThrows(ResponseStatusException.class,() -> sut.get(null));
    }

    @Test
    public void shouldCreateANewUser() throws IOException{
        UserDTO userDTO = mapJsonToUserDTO();
        user1.setId(userDTO.getId());
        Mockito.when(userRepositoryMock.save(isA(User.class))).thenReturn(user1);

        UUID uuid = sut.create(userDTO);

        assertEquals(uuid,userDTO.getId());
    }

    private UserDTO mapJsonToUserDTO() throws IOException {
        JsonResourceReader reader = new JsonResourceReader();
        JsonNode user1 = reader.getJsonFileAsJsonNode("./testdata/test_DTOUser.json").get("user1");
        UserDTO user = objectMapper.treeToValue(user1,UserDTO.class);
        return user;
    }

}
