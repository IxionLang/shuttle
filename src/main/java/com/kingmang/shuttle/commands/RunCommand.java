package com.kingmang.shuttle.commands;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RunCommand implements Command {
    private final String projectName;

    public RunCommand(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public void execute() throws Exception {
        Path projectPath = Paths.get(projectName);
        Path xmlPath = projectPath.resolve("project.xml");

        if (!xmlPath.toFile().exists()) {
            throw new Exception("Project not found: " + projectName);
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlPath.toFile());

        NodeList mainFileNodes = document.getElementsByTagName("mainFile");
        if (mainFileNodes.getLength() == 0) {
            throw new Exception("Main file not specified in project.xml");
        }

        String mainFile = mainFileNodes.item(0).getTextContent();
        Path mainFilePath = projectPath.resolve(mainFile);

        if (!mainFilePath.toFile().exists()) {
            throw new Exception("Main file not found: " + mainFile);
        }

        System.out.println("Running project: " + projectName);
        System.out.println("Main file: " + mainFilePath);
        System.out.println("To run the project, call CompRunner.build(\"" + mainFilePath + "\")");
    }
} 