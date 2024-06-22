package net.jezevcik.processes;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Processes {

    public static ProcessInformation[] getProcesses() throws IOException {
        final Process process = Runtime.getRuntime().exec("tasklist.exe");
        final Scanner scanner = new Scanner(new InputStreamReader(process.getInputStream()));

        final int[] columns = new int[4];

        final List<ProcessInformation> processInformation = new ArrayList<>();

        boolean begunListing = false;

        while (scanner.hasNext()) {
            final String line = scanner.nextLine();

            if (line.trim().isEmpty())
                continue;

            if (line.startsWith("=")) {
                int index = line.indexOf(' ');
                int counter = 0;

                while(index >= 0) {
                    columns[counter] = index;
                    index = line.indexOf(' ', index + 1);
                    counter++;
                }

                begunListing = true;
            } else if (begunListing) {
                final String name = line.substring(0, columns[0]).trim()
                        , memory = line.substring(columns[3]).trim()
                        , pid = line.substring(columns[0], columns[1]).trim()
                        , sessionName = line.substring(columns[1], columns[2]).trim()
                        , session = line.substring(columns[2], columns[3]).trim();

                processInformation.add(new ProcessInformation(name, Long.parseLong(pid), sessionName, Integer.parseInt(session)
                        , memory.equals("N/A") ? -1 : Long.parseLong(memory.substring(0, memory.length() - 2).replace(",", "").trim())));
            }
        }

        scanner.close();

        return processInformation.toArray(new ProcessInformation[0]);
    }

}
