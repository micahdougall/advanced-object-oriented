package edu.swansea.dougall.validators;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

import java.io.File;

/**
 * Validator class for JCommander to check if a file exists in the resources folder.
 */
public class FileExists implements IParameterValidator {

    /**
     * Checks if a file exists in the resources folder.
     * @param name the name of the parameter, i.e. the filename argument.
     * @param value the value of the parameter, i.e. the filename.
     * @throws ParameterException if the file does not exist.
     */
    @Override
    public void validate(String name, String value) throws ParameterException {
        File file = new File(String.format("src/main/resources/%s", value));
        if (!file.exists()) {
            throw  new ParameterException(
                    String.format(
                            "Argument %s must be a valid file in resources (invalid value %s)",
                            name, value)
            );
        }
    }
}
