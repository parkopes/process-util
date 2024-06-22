package net.jezevcik.processes;

import java.io.IOException;

public class Test {

    public static void main(String[] args) {
        try {
            for (ProcessInformation processInformation : Processes.getProcesses()) {
                System.out.println(processInformation.name() + " : " + processInformation.pid() + " : " + processInformation.sessionName() + " : " + processInformation.session() + " : " + processInformation.memUsage());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
