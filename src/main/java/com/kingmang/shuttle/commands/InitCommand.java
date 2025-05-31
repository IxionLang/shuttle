package com.kingmang.shuttle.commands;

import java.io.FileWriter;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InitCommand implements Command {
    private final String projectName;

    public InitCommand(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public void execute() throws Exception {
        Path projectPath = Paths.get(URI.create(projectName));
        if (Files.exists(Path.of(projectPath.toUri()))) {
            throw new Exception("Project directory already exists: " + projectName);
        }


        Files.createDirectories(projectPath);

        Path libPath = projectPath.resolve("lib");
        Files.createDirectories(libPath);

        Path xmlPath = projectPath.resolve("project.xml");
        try (FileWriter writer = new FileWriter(xmlPath.toFile())) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<project>\n");
            writer.write("    <name>" + projectName + "</name>\n");
            writer.write("    <mainFile>main.ix</mainFile>\n");
            writer.write("</project>");
        }

        Path mainFile = projectPath.resolve("main.ix");
        try (FileWriter writer = new FileWriter(mainFile.toFile())) {
            writer.write("// Main entry point for " + projectName + "\n");
            writer.write("def main(args : String[]) {\n");
            writer.write("    println(\"Hello from " + projectName + "!\");\n");
            writer.write("}\n");
        }

        System.out.println("Project initialized successfully: " + projectName);
    }
} 