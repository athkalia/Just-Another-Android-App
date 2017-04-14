package com.example;

import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BuildConfigTest {

    @Test
    public void application_id_test() {
        assertThat(BuildConfig.APPLICATION_ID).isEqualTo("com.justanotherandroidapp");
    }

    @Test
    public void build_type_test() {
        assertThat(BuildConfig.BUILD_TYPE).isEqualTo("release");
    }

    @Test
    public void build_time_generated_for_release_builds() {
        // Only reading part of the string, I hate timezones.
        DateTime buildDateTime = DateTime.parse(BuildConfig.BUILD_TIME.substring(0, 21), DateTimeFormat.forPattern("MM-dd-yyyy' 'h:mm:ss a"));
        DateTime now = DateTime.now();

        assertThat(Minutes.minutesBetween(buildDateTime, now).getMinutes()).isLessThan(5);
    }

    @Test
    public void git_sha_generated_for_release_builds() {
        assertThat(BuildConfig.GIT_SHA).isNotEqualToIgnoringCase("DEBUG");
    }

}
