import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GitRebaseExample {

    // Runs a shell command and returns the output as a string
    public static String runCommand(String command) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder();
        // For Windows, use "cmd.exe", "/c", command
        // For Linux/Mac, use "bash", "-c", command
        builder.command("bash", "-c", command);
        Process process = builder.start();

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(process.getInputStream()));

        StringBuilder output = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            BufferedReader errorReader =
                    new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder errorOutput = new StringBuilder();
            while ((line = errorReader.readLine()) != null) {
                errorOutput.append(line).append("\n");
            }
            throw new RuntimeException("Command failed with error:\n" + errorOutput.toString());
        }

        return output.toString();
    }

    public static void main(String[] args) {
        try {
            System.out.println("Checking out branch 'feature-branch'...");
            System.out.println(runCommand("git checkout feature-branch"));

            System.out.println("Pulling latest changes from origin/feature-branch...");
            System.out.println(runCommand("git pull origin feature-branch"));

            System.out.println("Rebasing onto main branch...");

