package booking.services;

import booking.entities.Ticket;
import booking.entities.Train;
import booking.entities.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserBookingService {

    private User user;

    private List<User> userList;

    private static final String USERS_PATH="app/src/main/java/booking/localDB/users.json";

    private ObjectMapper objectMapper = new ObjectMapper();

    public UserBookingService(User user1) throws IOException {

        this.user=user1;
        loadUsers();
    }

    public UserBookingService() throws IOException{
        loadUsers();
    }

    public List<User> loadUsers() throws IOException{
        File users=new File(USERS_PATH);
        return userList=objectMapper.readValue(users, new TypeReference<List<User>>() {});
    }

    public Boolean loginUser(){
        Optional<User> foundUser=userList.stream().filter(user1->{
                    return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
                }).findFirst();
        return foundUser.isPresent();
    }

    public Boolean signUp(User user1){
        try{
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        }catch(IOException ex){
            return Boolean.FALSE;
        }
    }

    private void saveUserListToFile() throws IOException {
        File users=new File(USERS_PATH);
        objectMapper.writeValue(users, userList);
    }

    public void fetchBooking(){
        user.printTickets();
    }
    public Boolean cancelBooking(String ticketId){
        List<Ticket> userTickets=user.getTicketsBooked();
        for(int i=0;i<userTickets.size();i++) {
            if (userTickets.get(i).getTicketId().equals(ticketId)) {
                userTickets.remove(i);
                user.setTicketsBooked(userTickets);
                try {
                    saveUserListToFile();
                    System.out.println("Ticket cancelled successfully");
                    return Boolean.TRUE;
                } catch (IOException e) {
                    return Boolean.FALSE;
                }
            }
            else{
                System.out.println("Ticket not found");
                return Boolean.FALSE;
            }
        }
        return Boolean.FALSE; // ticket not found
    }

    public List<Train> getTrains(String source, String destination){
        TrainService trainService=new TrainService();
        return trainService.searchTrains(source, destination);

    }

//    public Boolean bookTicket(Train trainId){
//
//    }
}

//json -> object is called DESERIALIZATION
//object -> json is called SERIALIZATION