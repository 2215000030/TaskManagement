package com.Project.TaskManagementApp.ApiResponses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DogResponse {

    private Data data;

    @Getter
    @Setter
    public static class Data {
        private Attributes attributes;
    }

    @Getter
    @Setter
    public static class Attributes {
        private String name;
        private String description;
    }
}