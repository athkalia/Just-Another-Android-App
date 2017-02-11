package com.example.util.nullability;

import com.crashlytics.android.Crashlytics;

import javax.annotation.Nullable;

/**
 * Stripped down version of Guava preconditions. We added an extra feature on the Preconditions class and that is to log the exceptions in
 * crashlytics. This appear in the dashboard as "non-fatal" exceptions.
 */
@SuppressWarnings("all")
public final class Preconditions {

    private Preconditions() {
        throw new AssertionError();
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * @param expression a boolean expression
     *
     * @throws IllegalArgumentException if {@code expression} is false
     */
    public static void checkArgument(boolean expression) {

        if (!expression) {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Argument failed check.");
            Crashlytics.logException(illegalArgumentException);
            throw illegalArgumentException;
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * @param expression   a boolean expression
     * @param errorMessage the exception message to use if the check fails; will be converted to a
     *                     string using {@link String#valueOf(Object)}
     *
     * @throws IllegalArgumentException if {@code expression} is false
     */
    public static void checkArgument(boolean expression, @Nullable Object errorMessage) {

        if (!expression) {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException(String.valueOf(errorMessage));
            Crashlytics.logException(illegalArgumentException);
            throw illegalArgumentException;
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * @param expression           a boolean expression
     * @param errorMessageTemplate a template for the exception message should the check fail. The
     *                             message is formed by replacing each {@code %s} placeholder in the template with an
     *                             argument. These are matched by position - the first {@code %s} gets {@code
     *                             errorMessageArgs[0]}, etc.  Unmatched arguments will be appended to the formatted message
     *                             in square braces. Unmatched placeholders will be left as-is.
     * @param errorMessageArgs     the arguments to be substituted into the message template. Arguments
     *                             are converted to strings using {@link String#valueOf(Object)}.
     *
     * @throws IllegalArgumentException if {@code expression} is false
     * @throws NullPointerException     if the check fails and either {@code errorMessageTemplate} or
     *                                  {@code errorMessageArgs} is null (don't let this happen)
     */
    public static void checkArgument(boolean expression,
                                     @Nullable String errorMessageTemplate,
                                     @Nullable Object... errorMessageArgs) {

        if (!expression) {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException(format(errorMessageTemplate, errorMessageArgs));
            Crashlytics.logException(illegalArgumentException);
            throw illegalArgumentException;
        }
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param reference an object reference
     *
     * @return the non-null reference that was validated
     *
     * @throws NullPointerException if {@code reference} is null
     */
    public static <T> T checkNotNull(@Nullable T reference) {

        if (reference == null) {
            NullPointerException nullPointerException = new NullPointerException("Null check failed.");
            Crashlytics.logException(nullPointerException);
            throw nullPointerException;
        }
        return reference;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param reference    an object reference
     * @param errorMessage the exception message to use if the check fails; will be converted to a
     *                     string using {@link String#valueOf(Object)}
     *
     * @return the non-null reference that was validated
     *
     * @throws NullPointerException if {@code reference} is null
     */
    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {

        if (reference == null) {
            NullPointerException nullPointerException = new NullPointerException(String.valueOf(errorMessage));
            Crashlytics.logException(nullPointerException);
            throw nullPointerException;
        }
        return reference;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param reference            an object reference
     * @param errorMessageTemplate a template for the exception message should the check fail. The
     *                             message is formed by replacing each {@code %s} placeholder in the template with an
     *                             argument. These are matched by position - the first {@code %s} gets {@code
     *                             errorMessageArgs[0]}, etc.  Unmatched arguments will be appended to the formatted message
     *                             in square braces. Unmatched placeholders will be left as-is.
     * @param errorMessageArgs     the arguments to be substituted into the message template. Arguments
     *                             are converted to strings using {@link String#valueOf(Object)}.
     *
     * @return the non-null reference that was validated
     *
     * @throws NullPointerException if {@code reference} is null
     */
    public static <T> T checkNotNull(T reference,
                                     @Nullable String errorMessageTemplate,
                                     @Nullable Object... errorMessageArgs) {

        if (reference == null) {
            // If either of these parameters is null, the right thing happens anyway
            NullPointerException nullPointerException = new NullPointerException((format(errorMessageTemplate, errorMessageArgs)));
            Crashlytics.logException(nullPointerException);
            throw nullPointerException;
        }
        return reference;
    }

    /**
     * Substitutes each {@code %s} in {@code template} with an argument. These are matched by
     * position: the first {@code %s} gets {@code args[0]}, etc.  If there are more arguments than
     * placeholders, the unmatched arguments will be appended to the end of the formatted message in
     * square braces.
     *
     * @param template a non-null string containing 0 or more {@code %s} placeholders.
     * @param args     the arguments to be substituted into the message template. Arguments are converted
     *                 to strings using {@link String#valueOf(Object)}. Arguments can be null.
     */
    // Note that this is somewhat-improperly used from Verify.java as well.
    static String format(String template, @Nullable Object... args) {

        template = String.valueOf(template); // null -> "null"

        // start substituting the arguments into the '%s' placeholders
        StringBuilder builder = new StringBuilder(template.length() + 16 * args.length);
        int templateStart = 0;
        int i = 0;
        while (i < args.length) {
            int placeholderStart = template.indexOf("%s", templateStart);
            if (placeholderStart == -1) {
                break;
            }
            builder.append(template.substring(templateStart, placeholderStart));
            builder.append(args[i++]);
            templateStart = placeholderStart + 2;
        }
        builder.append(template.substring(templateStart));

        // if we run out of placeholders, append the extra args in square braces
        if (i < args.length) {
            builder.append(" [");
            builder.append(args[i++]);
            while (i < args.length) {
                builder.append(", ");
                builder.append(args[i++]);
            }
            builder.append(']');
        }

        return builder.toString();
    }

}
