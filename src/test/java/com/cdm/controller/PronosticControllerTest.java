package com.cdm.controller;

import com.cdm.pojo.PronosticPojo;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PronosticControllerTest
{
    private PronosticController pronosticController;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "dd/MM/yyyy" );

    @BeforeClass
    public void setup ()
    {
        pronosticController = new PronosticController();
    }

    @Test
    public void getPronosticAvailableTest ()
    {
        LocalDate dateReference = LocalDate.parse( "12/03/2018", formatter );

        PronosticPojo pronosticPojo = new PronosticPojo();
        //pronosticPojo.setDateMatch(  );
        List<PronosticPojo> pronosticPojos = new ArrayList<>();

        ReflectionTestUtils.invokeMethod( pronosticController, "getPronosticAvailable", dateReference, null );
    }
}
