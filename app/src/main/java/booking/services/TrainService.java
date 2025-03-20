package booking.services;

import booking.entities.Train;
import booking.entities.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrainService {

    private List<Train> trainList;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String TRAINS_PATH="app/src/main/java/booking/localDB/trains.json";
    public List<Train> loadTrains() throws IOException {
        File trains=new File(TRAINS_PATH);
        return trainList=objectMapper.readValue(trains, new TypeReference<List<Train>>() {});
    }

    public List<Train> searchTrains(String source, String destination){
        List<Train> availableTrains=new ArrayList<>();
        int sourceIndex=-1;
        int destinationIndex=-1;
        for(int i=0;i<trainList.size();i++){
            List<String> stations=trainList.get(i).getStations();
            for(int j=0;j<stations.size();j++){
                if(stations.get(j).equals(source)){
                    sourceIndex=j;
                }
                else if(stations.get(j).equals(destination)){
                    destinationIndex=j;
                }
            }
            if(sourceIndex!=-1 && destinationIndex!=-1 && sourceIndex<destinationIndex){
                availableTrains.add(trainList.get(sourceIndex));
            }
        }
        return availableTrains;
    }
}
