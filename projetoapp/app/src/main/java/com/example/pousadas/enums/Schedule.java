package com.example.pousadas.enums;

public enum Schedule {
    ALMOCO("Almoço"),
    JANTAR("Jantar");

    private String schedule;

    Schedule(String schedule) {
        this.schedule = schedule;
    }

    public String getSchedule() {
        return schedule;
    }

    public static Schedule getFromString(String text) {
        for (Schedule enumSchedule : Schedule.values()) {
            if (enumSchedule.schedule.equalsIgnoreCase(text)) {
                return enumSchedule;
            }
        }

        throw new IllegalArgumentException("Não existe!");
    }
}
