package org.example.validators;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

import java.io.File;

public class FileExists implements IParameterValidator {
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
