package com.example.util.other;

import android.content.res.AssetManager;
import com.example.util.nullability.Preconditions;
import timber.log.Timber;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class that provides easy access to properties files found in the project.
 */
public class PropertiesManager {

    private static final String PROPERTIES_FILENAME = "project.properties";

    private final Properties properties;

    public PropertiesManager(AssetManager assetManager) {
        properties = new Properties();
        InputStream inputStream;
        try {
            inputStream = assetManager.open(PROPERTIES_FILENAME);
            properties.load(inputStream);
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            Timber.e(e, "Failed to close input stream");
        }
    }

    public String getDribleClientAccessToken() {

        @Nullable String authToken = properties.getProperty(Property.DRIBBLE_CLIENT_ACCESS_TOKEN.getPropertyKey());
        Preconditions.checkNotNull(authToken);
        return authToken;
    }

    public String getBaseUrl() {
        @Nullable String baseUrl = properties.getProperty(Property.BASE_URL.getPropertyKey());
        Preconditions.checkNotNull(baseUrl);
        return baseUrl;
    }

    private enum Property {

        DRIBBLE_CLIENT_ACCESS_TOKEN("dribbleClientAccessToken"),
        BASE_URL("dribbleBaseUrl");

        private final String propertyKey;

        Property(String authToken) {

            this.propertyKey = authToken;
        }

        public String getPropertyKey() {

            return propertyKey;
        }
    }

}
