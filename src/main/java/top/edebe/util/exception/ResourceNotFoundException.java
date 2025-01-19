package top.edebe.util.exception;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class ResourceNotFoundException extends IOException {
    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(final @Nullable String message) {
        super(message);
    }

    public ResourceNotFoundException(final @Nullable String message, final @Nullable Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(final @Nullable Throwable cause) {
        super(cause);
    }
}