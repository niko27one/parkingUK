package com.parkinguk.parkinguk.service.initSlots.impl;
import com.parkinguk.parkinguk.config.FloorConfigInit;
import com.parkinguk.parkinguk.entity.Floor;
import com.parkinguk.parkinguk.entity.Slot;
import com.parkinguk.parkinguk.helper.FloorConfiguration;
import com.parkinguk.parkinguk.repository.FloorsRepository;
import com.parkinguk.parkinguk.repository.SlotsRepository;
import com.parkinguk.parkinguk.service.initSlots.ParkingInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class ParkingsInitImpl implements ParkingInit {
    @Autowired
    private SlotsRepository slotsRepository;
    @Autowired
    private FloorsRepository floorsRepository;
    @Autowired
    private FloorConfigInit floorConfigInit;

    @Override
    public void initFloors() throws Exception {
        List<Slot> existedSchema =  slotsRepository.findAll();
        if(!existedSchema.isEmpty()){
            return;
        }
        Floor floorOne = Floor.builder().capacity(4).description("groundFloor").build();
        floorsRepository.save(floorOne);
        Floor floorTwo = Floor.builder().capacity(4).description("firstFloor").build();
        floorsRepository.save(floorTwo);

        Floor floorThree = Floor.builder().capacity(4).description("secondFloor").build();
        floorsRepository.save(floorThree);
        initSlots();
    }

    @Override
    public void initSlots() throws Exception {
        Map<String, FloorConfiguration> floorConfigurationMap = new HashMap<>();
        FloorConfiguration floorConfiguration = new FloorConfiguration();
        floorConfiguration.setDisable(floorConfigInit.getFirstFloorDisable());
        floorConfiguration.setParents(floorConfigInit.getFirstFloorParents());
        floorConfiguration.setMotorbikes(floorConfigInit.getFirstFloorMotorbikes());
        floorConfigurationMap.put("groundFloor",floorConfiguration);
        initGroundFloor(floorConfigurationMap);
        ArrayList<String> floorNames = new ArrayList<>();
        floorNames.add("firstFloor");
        floorNames.add("secondFloor");
        for(String s:floorNames){
            Optional<Floor> floorName = floorsRepository.findByDescription(s);
            if(!floorName.isPresent()){
                throw new Exception("floor not present");
            }
            initFirstSecondFloors(floorName.get());
        }
    }

    public void initGroundFloor(Map<String,FloorConfiguration> floorConfigurationMap) throws Exception {
        Optional<Floor> floorOne = floorsRepository.findByDescription("groundFloor");
        if(!floorOne.isPresent()){
            throw new Exception("No floors are initialized");
        }
        for(int i = 0; i< floorConfigurationMap.get("groundFloor").getDisable(); i++){
            Slot slot = Slot.builder().disable(true).motorbike(false).occupied(false).parent(false).floor(floorOne.get()).build();
            slotsRepository.save(slot);
        }
        for(int i = 0;i<floorConfigurationMap.get("groundFloor").getMotorbikes();i++){
            Slot slot = Slot.builder().disable(false).motorbike(true).occupied(false).parent(false).floor(floorOne.get()).build();
            slotsRepository.save(slot);
        }
        for(int i = 0;i<floorConfigurationMap.get("groundFloor").getParents();i++){
            Slot slot = Slot.builder().disable(false).motorbike(false).occupied(false).parent(true).floor(floorOne.get()).build();
            slotsRepository.save(slot);
        }
    }

    public void initFirstSecondFloors(Floor floor){
        for(int i = 0;i<4;i++){
            Slot slot = Slot.builder().disable(false).motorbike(false).occupied(false).parent(false).floor(floor).build();
            slotsRepository.save(slot);
        }
    }


}
