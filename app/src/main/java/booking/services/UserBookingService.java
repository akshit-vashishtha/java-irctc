package booking.services;

import booking.entities.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserBookingService {

    private User user;

    private List<User> userList;

    private static final String USERS_PATH="app/src/main/java/booking/localDB/users.json";

    private ObjectMapper objectMapper = new ObjectMapper();

    public UserBookingService(User user1) throws IOException {

        this.user=user1;
        File users=new File(USERS_PATH);
        userList=objectMapper.readValue(users, new TypeReference<List<User>>() {});
    }

    public Boolean loginUser(){
        Optional<User> foundUser=userList.stream().filter(user1->{
                    return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
                }).findFirst();
        return foundUser.isPresent();
    }
}
