package com.lambdaschool.countryapp.controllers;

import com.lambdaschool.countryapp.models.Country;
import com.lambdaschool.countryapp.repositories.CountryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountryController
{

    private List<Country> findCountries(List<Country> myList, CheckCountry tester)
    {
        List<Country> tempList = new ArrayList<>();
        for (Country e : myList)
        {
            if (tester.test(e))
            {
                tempList.add(e);
            }
        }
        return tempList;
    }



    @Autowired
    CountryRepo countryRepo;

    // http://localhost:2019/countries/all
    @GetMapping(value = "/countries/all", produces = {"application/json"})
    public ResponseEntity<?> listAllCountry()
    {
        List<Country> myList = new ArrayList<>();
        countryRepo.findAll().iterator().forEachRemaining(myList::add);

        myList.sort((e1, e2) -> e1.getName().compareToIgnoreCase(e2.getName()));
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

//     http://localhost:2019/countries/names/start/u
    @GetMapping(value = "/countries/names/start/{letter}", produces = {"application/json"})
    public ResponseEntity<?> listAllCountryName(@PathVariable char letter)
    {
        List<Country> myList = new ArrayList<>();
        countryRepo.findAll().iterator().forEachRemaining(myList::add);

        List<Country> rtnList = findCountries(myList, e -> e.getName().charAt(0) == letter);

        for (Country e : rtnList)
        {
            System.out.println(e);
        }

        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

//        http://localhost:2019/population/total
    @GetMapping(value = "/countries/population/total", produces = {"application/json"})
    public ResponseEntity<?> listPopulationTotal()
    {
        List<Country> myList = new ArrayList<>();
        countryRepo.findAll().iterator().forEachRemaining(myList::add);


        double total = 0;
        for (Country e : myList)
        {
            total = total + e.getPopulation();
        }

        System.out.println(total);
        return new ResponseEntity<>("The total population is:" + total, HttpStatus.OK);
    }

    //        http://localhost:2019/population/min
    @GetMapping(value = "/countries/population/min", produces = {"application/json"})
    public ResponseEntity<?> listPopulationMin()
    {
        List<Country> myList = new ArrayList<>();
        countryRepo.findAll().iterator().forEachRemaining(myList::add);

        myList.sort((c1, c2) -> (int)(c1.getPopulation() - c2.getPopulation()));
//        myList.get(0);

        System.out.println(myList.get(0));
        return new ResponseEntity<>(myList.get(0), HttpStatus.OK);
    }

    //        http://localhost:2019/population/max
    @GetMapping(value = "/countries/population/max", produces = {"application/json"})
    public ResponseEntity<?> listPopulationMax()
    {
        List<Country> myList = new ArrayList<>();
        countryRepo.findAll().iterator().forEachRemaining(myList::add);

        myList.sort((c1, c2) -> (int)(c1.getPopulation() - c2.getPopulation()));


        System.out.println(myList.get(myList.size()-1));
        return new ResponseEntity<>(myList.get(myList.size()-1), HttpStatus.OK);
    }

    //        http://localhost:2019/population/median
    @GetMapping(value = "/countries/population/median", produces = {"application/json"})
    public ResponseEntity<?> listPopulationMedian()
    {
        List<Country> myList = new ArrayList<>();
        countryRepo.findAll().iterator().forEachRemaining(myList::add);

        myList.sort((c1, c2) -> (int)(c1.getPopulation() - c2.getPopulation()));


        System.out.println(myList.get((myList.size() / 2) + 1));
        return new ResponseEntity<>(myList.get((myList.size() / 2) + 1), HttpStatus.OK);
    }




}
