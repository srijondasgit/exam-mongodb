package org.gitanjali.exam;


import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class Dbseeder implements CommandLineRunner{


    //private HotelRepository hotelRepository;

    private TestRepository testRepository;

    public Dbseeder(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    /*public Dbseeder(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }*/



    @Override
    public void run(String... args) throws Exception {

        Test test1 = new Test(
                "test1",
                "Nes",
                "6",
                Arrays.asList(
                        new String("Q1 : this question"),
                        new String ("Q2 : that question")
                ),
                Arrays.asList(
                        new Submission("Sam","sam@gmail.com", "123",
                                Arrays.asList(
                                        new String("A1 : this answer"),
                                        new String ("A2 : that answer")
                                ))
                )

        );

        this.testRepository.deleteAll();

        List<Test> tests = Arrays.asList(test1);
        this.testRepository.saveAll(tests);

//        Hotel mariott = new Hotel(
//                "Marr",
//                1,
//                new Address("Paris", "India"),
//                Arrays.asList(
//                        new Review("John", 8, false),
//                        new Review("Sri", 10, true)
//                )
//        );
//
//        Hotel standard = new Hotel(
//                "Stan",
//                1,
//                new Address("Paris", "India"),
//                Arrays.asList(
//                        new Review("John", 8, false),
//                        new Review("Sri", 10, true)
//                )
//        );

//        //drop all hotels
//        this.hotelRepository.deleteAll();
//
//        List<Hotel> hotels = Arrays.asList(mariott, standard);
//        this.hotelRepository.saveAll(hotels);
    }
}
