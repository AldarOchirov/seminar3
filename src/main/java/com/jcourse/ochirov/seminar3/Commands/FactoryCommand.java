package com.jcourse.ochirov.seminar3.Commands;

import com.jcourse.ochirov.seminar3.Inject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;

public class FactoryCommand {
    static Properties properties;
    static Map<String, Double> variables = new HashMap<>();
    static Stack<Double> stack = new Stack<>();

    static {
        try {
            properties = new Properties();
            InputStream stream = FactoryCommand.class
                    .getClassLoader()
                    .getResourceAsStream("commands.properties");
            properties.load(stream);
            System.out.println("properties = " + properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //на вход строка, например, DEFINE a 4 -> DefineCommand()
    static Command getCommand(String line) {
        try {
            String[] tokens = line.split(" ");
            String className = properties.getProperty(tokens[0]);
            Class<?> aClass = Class.forName(className);

            Object command = aClass.newInstance();

            Field[] fields = aClass.getDeclaredFields();
            for (Field field : fields) {
                Inject annotation = field.getAnnotation(Inject.class);
                if (annotation != null) {
                    field.setAccessible(true);
                    switch (annotation.arg()) {
                        case STACK:
                            field.set(command, stack);
                            break;
                        case ARGUMENTS:
                            field.set(command, tokens);
                            break;
                        case VARIABLES:
                            field.set(command, variables);
                            break;
                    }
                    field.setAccessible(false);
                }
            }

            return (Command) command;
        } catch (IllegalAccessException |
                InstantiationException |
                ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        Command command = FactoryCommand.getCommand("PUSH a");
        command.execute();
    }
}
