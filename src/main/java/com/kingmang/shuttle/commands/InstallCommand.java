package com.kingmang.shuttle.commands;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.nio.file.Path;
import java.nio.file.Paths;

public class InstallCommand implements Command {
    private final String repoUrl;

    public InstallCommand(String repoUrl) {
        this.repoUrl = repoUrl;
    }

    @Override
    public void execute() throws Exception {
        String repoName = extractRepoName(repoUrl);
        if (repoName == null) {
            throw new Exception("Invalid GitHub repository URL: " + repoUrl);
        }

        Path libPath = Paths.get("lib");
        if (!libPath.toFile().exists()) {
            libPath.toFile().mkdirs();
        }


        Path targetPath = libPath.resolve(repoName);
        if (targetPath.toFile().exists()) {
            throw new Exception("Library already installed: " + repoName);
        }

        try {
            System.out.println("Cloning repository: " + repoUrl);
            Git.cloneRepository()
                .setURI(repoUrl)
                .setDirectory(targetPath.toFile())
                .call();
            System.out.println("Library installed successfully: " + repoName);
        } catch (GitAPIException e) {
            throw new Exception("Failed to clone repository: " + e.getMessage());
        }
    }

    private String extractRepoName(String repoUrl) {
        if (repoUrl.endsWith(".git")) {
            repoUrl = repoUrl.substring(0, repoUrl.length() - 4);
        }

        String[] parts = repoUrl.split("/");
        if (parts.length >= 2) {
            return parts[parts.length - 1];
        }
        return null;
    }
} 