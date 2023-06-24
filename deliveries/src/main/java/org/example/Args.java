package org.example;

import com.beust.jcommander.Parameter;
import org.example.validators.FileExists;

import java.util.ArrayList;
import java.util.List;


public class Args {

    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(names={"--debug", "-d"})
    private boolean debug = false;

    @Parameter(names={"--assignments", "-a"}, echoInput=true, validateWith=FileExists.class)
    String assignments;

    @Parameter(names={"--routes", "-r"}, echoInput=true, validateWith=FileExists.class)
    String routes;
}
