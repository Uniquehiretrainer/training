package com.uniquehire.training.serviceimpl;

import com.uniquehire.cafe.dto.OrderResponseDTO;
import com.uniquehire.training.config.RestTemplateConfig;
import com.uniquehire.training.dto.UserProfileDTO;
import com.uniquehire.training.dto.UserRequestDTO;
import com.uniquehire.training.dto.UserResponseDTO;
import com.uniquehire.training.model.User;
import com.uniquehire.training.model.UserProfile;
import com.uniquehire.training.repository.UserProfileRepository;
import com.uniquehire.training.repository.UserRepository;
import com.uniquehire.training.service.UserService;
import com.uniquehire.training.utils.RestTemplateUtils;
import com.uniquehire.training.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final RestTemplateConfig restTemplateConfig;
    private final RestTemplateUtils restTemplateUtils;
    /*@Autowired
    private final UserUtils userUtils;*/

    public UserServiceImpl(UserRepository userRepository, UserProfileRepository userProfileRepository/*, UserUtils userUtils*/,
                           RestTemplateConfig restTemplateConfig, RestTemplateUtils restTemplateUtils) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
       // this.userUtils = userUtils;
        this.restTemplateConfig = restTemplateConfig;
        this.restTemplateUtils = restTemplateUtils;
    }

    public User saveUser(UserRequestDTO userDTO) {

        User user = new User();
        if(Objects.nonNull(userDTO.getName())){
            user.setName(userDTO.getName());
        }
        UserProfile userProfile = new UserProfile();
        userProfile.setPhone(userDTO.getProfile().getPhone());
        userProfile.setAddress(userDTO.getProfile().getAddress());
        UserProfile profileResponse = userProfileRepository.save(userProfile);
        user.setProfileId(profileResponse.getId());

       // user.setProfile(userProfile);

        return userRepository.save(user);
    }

    public List<UserResponseDTO> getAllUsers() {
        List<User> userDetails = userRepository.findAll();
        List<UserResponseDTO> userResponseList = new ArrayList<>();

        UserUtils userUtils = new UserUtils();
        for(User user : userDetails){
            UserResponseDTO responseDTO = userUtils.prepareUserResponse(user);
            userResponseList.add(responseDTO);
        }
        //RestTemplate restTemplate = restTemplateConfig.getRestTemplate();
        final String uri = "http://cafe";
        String url = uri + "/api/orders/getOrders?tableName=Table-5";
        /*OrderResponseDTO[] response =
                restTemplate.getForObject("http://192.168.0.88:8085/api/orders/getOrders?tableName=Table-5", OrderResponseDTO[].class);

        System.err.println(response);
        System.out.println(Arrays.asList(response));*/
        /*ResponseEntity<List<OrderResponseDTO>> response =
                restTemplate.exchange(
                        uri,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<OrderResponseDTO>>() {}
                );*/
        ResponseEntity<List<OrderResponseDTO>> response = restTemplateUtils.callIntermicroServiceCommunication(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<OrderResponseDTO>>() {});
        System.out.println(response.getBody());
        return userResponseList;
    }

}

