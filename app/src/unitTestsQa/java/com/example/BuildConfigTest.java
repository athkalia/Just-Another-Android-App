package com.example;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BuildConfigTest {

    @Test
    public void application_id_test() throws Exception {

        assertThat(BuildConfig.APPLICATION_ID).isEqualTo("com.justanotherandroidapp.qa");
    }

    @Test
    public void build_type_test() {
        assertThat(BuildConfig.BUILD_TYPE).isEqualTo("qa");
    }

    @Test
    public void build_time_not_generated_for_qa_builds() {
        assertThat(BuildConfig.BUILD_TIME).isEqualTo("DEBUG");
    }

    @Test
    public void git_sha_not_generated_for_qa_builds() {
        assertThat(BuildConfig.GIT_SHA).isEqualTo("DEBUG");
    }

}
