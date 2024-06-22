package net.jezevcik.processes;

public record ProcessInformation(String name, long pid, String sessionName, int session, long memUsage) {
}
