package com.authine.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellTest {
    public static void main(String[] args) throws IOException {

        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec("/root/shelltest/deploy.sh");
            BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stderrReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;
            System.out.println("OUTPUT");
            while ((line = stdoutReader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("ERROR");
            while ((line = stderrReader.readLine()) != null) {
                System.out.println(line);
            }
            int exitVal = process.waitFor();
            System.out.println("process exit value is " + exitVal);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
