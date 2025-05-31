package com.kingmang.shuttle;

import com.kingmang.shuttle.commands.Command;
import com.kingmang.shuttle.commands.InitCommand;
import com.kingmang.shuttle.commands.InstallCommand;
import com.kingmang.shuttle.commands.RunCommand;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: shuttle <command> [options]");
            System.out.println("Commands:");
            System.out.println("  init <projectName>    Initialize a new project");
            System.out.println("  run <projectName>     Run the project");
            System.out.println("  install <repo>        Install a library from GitHub");
            return;
        }

        String command = args[0];
        Command cmd;

        switch (command) {
            case "init":
                if (args.length < 2) {
                    System.out.println("Error: Project name required");
                    return;
                }
                cmd = new InitCommand(args[1]);
                break;
            case "run":
                if (args.length < 2) {
                    System.out.println("Error: Project name required");
                    return;
                }
                cmd = new RunCommand(args[1]);
                break;
            case "install":
                if (args.length < 2) {
                    System.out.println("Error: Repository URL required");
                    return;
                }
                cmd = new InstallCommand(args[1]);
                break;
            default:
                System.out.println("Unknown command: " + command);
                return;
        }

        try {
            cmd.execute();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 