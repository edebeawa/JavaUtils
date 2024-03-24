package pers.edebe.util.io;

import org.jetbrains.annotations.NotNull;
import pers.edebe.util.function.IOFunction;
import pers.edebe.util.function.IOSupplier;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ResettableInputStream extends InputStream {
    private final IOSupplier<InputStream> supplier;
    private InputStream stream;

    public ResettableInputStream(IOSupplier<InputStream> supplier) throws IOException {
        this.supplier = supplier;
        reset();
    }

    public <T> ResettableInputStream(T input, IOFunction<T, InputStream> function) throws IOException {
        this(() -> function.apply(input));
    }

    public ResettableInputStream(InputStream stream) throws IOException {
        this(StreamUtils.toByteArray(stream), ByteArrayInputStream::new);
    }

    @Override
    public int read() throws IOException {
        return stream.read();
    }

    @Override
    public int read(byte @NotNull [] b) throws IOException {
        return stream.read(b);
    }

    @Override
    public int read(byte @NotNull [] b, int off, int len) throws IOException {
        return stream.read(b, off, len);
    }

    @Override
    public byte[] readAllBytes() throws IOException {
        return stream.readAllBytes();
    }

    @Override
    public byte[] readNBytes(int len) throws IOException {
        return stream.readNBytes(len);
    }

    @Override
    public int readNBytes(byte[] b, int off, int len) throws IOException {
        return stream.readNBytes(b, off, len);
    }

    @Override
    public long skip(long n) throws IOException {
        return stream.skip(n);
    }

    @Override
    public void skipNBytes(long n) throws IOException {
        stream.skipNBytes(n);
    }

    @Override
    public int available() throws IOException {
        return stream.available();
    }

    @Override
    public void close() throws IOException {
        stream.close();
    }

    @Override
    public synchronized void mark(int readLimit) {
        stream.mark(readLimit);
    }

    @Override
    public synchronized void reset() throws IOException {
        stream = supplier.get();
    }

    @Override
    public boolean markSupported() {
        return stream.markSupported();
    }

    @Override
    public long transferTo(OutputStream out) throws IOException {
        return stream.transferTo(out);
    }
}