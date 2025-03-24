package com.FreeBoard.FreeBoard_EventHandler_Spring.utils;

import org.reactivestreams.Publisher;
import org.springframework.core.ResolvableType;
import org.springframework.core.codec.Decoder;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.security.rsocket.metadata.BearerTokenMetadata;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public class BearerTokenMetadataDecoder implements Decoder<BearerTokenMetadata> {

    private static final MimeType BEARER_AUTHENTICATION_MIME_TYPE =
            MimeTypeUtils.parseMimeType("message/x.rsocket.authentication.bearer.v0");

    @Override
    public boolean canDecode(ResolvableType elementType, MimeType mimeType) {
        return BearerTokenMetadata.class.isAssignableFrom(elementType.toClass()) &&
                BEARER_AUTHENTICATION_MIME_TYPE.equals(mimeType);
    }

    @Override
    public Flux<BearerTokenMetadata> decode(Publisher<DataBuffer> inputStream, ResolvableType elementType, MimeType mimeType, Map<String, Object> hints) {
        return Flux.from(inputStream)
                .map(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
                    return new BearerTokenMetadata(new String(bytes));
                });
    }

    @Override
    public Mono<BearerTokenMetadata> decodeToMono(Publisher<DataBuffer> inputStream, ResolvableType elementType, MimeType mimeType, Map<String, Object> hints) {
        return Flux.from(inputStream)
                .next().map(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
                    return new BearerTokenMetadata(new String(bytes));
                });
    }

    @Override
    public List<MimeType> getDecodableMimeTypes() {
        return List.of(MimeTypeUtils.parseMimeType("message/x.rsocket.authentication.bearer.v0"));
    }
}