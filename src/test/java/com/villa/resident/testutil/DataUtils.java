package com.villa.resident.testutil;

import com.villa.resident.model.Resident;
import java.util.Arrays;
import java.util.List;

public class DataUtils {
    public static List<Resident> getResidentList() {
        return Arrays.asList(
                Resident.builder().id("1").firstname("jabbi").build(),
                Resident.builder().id("2").firstname("vien").build(),
                Resident.builder().id("3").firstname("gareth").build(),
                Resident.builder().id("4").firstname("lucas").build());
    }

    public static Resident getResident() {
        return Resident.builder().id("1").firstname("jabbi").build();
    }
}
