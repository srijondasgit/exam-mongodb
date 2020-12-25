package org.gitanjali.exam;


import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class Dbseeder implements CommandLineRunner{


    private HotelRepository hotelRepository;

    public Dbseeder(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }


    @Override
    public void run(String... args) throws Exception {

        Hotel mariott = new Hotel(
                "Marr",
                1,
                new Address("Paris", "India"),
                Arrays.asList(
                        new Review("John", 8, false),
                        new Review("Sri", 10, true)
                )
        );

        Hotel standard = new Hotel(
                "Stan",
                1,
                new Address("Paris", "India"),
                Arrays.asList(
                        new Review("John", 8, false),
                        new Review("Sri", 10, true)
                )
        );

        //drop all hotels
        this.hotelRepository.deleteAll();

        List<Hotel> hotels = Arrays.asList(mariott, standard);
        this.hotelRepository.saveAll(hotels);
    }
}
