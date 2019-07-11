package com.example.tradelog.api.spec.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
public class LomModel {

    private @Getter String firstName;
    private @Getter String lastName;
}
