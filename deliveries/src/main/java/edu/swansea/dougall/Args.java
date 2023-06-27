package edu.swansea.dougall;

import com.beust.jcommander.Parameter;
import edu.swansea.dougall.validators.FileExists;


/**
 * Class to define command line arguments for the program.
 */
public class Args {

    @Parameter(names={"--assignments", "-a"}, echoInput=true, validateWith=FileExists.class)
    String assignments = "assignments.txt";

    @Parameter(names={"--routes", "-r"}, echoInput=true, validateWith=FileExists.class)
    String routes = "routes.txt";

    @Parameter(names={"--threads", "-t"}, echoInput=true)
    int threads = 10;

    @Parameter(names={"--print", "-p"}, echoInput=true)
    int print = 20;

    @Parameter(names={"--debug", "-d"})
    boolean debug = false;
}
