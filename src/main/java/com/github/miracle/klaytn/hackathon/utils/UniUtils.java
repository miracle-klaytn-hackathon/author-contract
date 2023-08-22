package com.github.miracle.klaytn.hackathon.utils;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

public class UniUtils {

    private UniUtils() {
    }

    public static <T> Uni<T> createFromSupplier(Supplier<T> supplier) {
        return createFromSupplier(supplier, Infrastructure.getDefaultWorkerPool());
    }

    public static <T> Uni<T> createFromSupplier(Supplier<T> supplier, Executor executor) {
        CompletableFuture<T> supplierStage = CompletableFuture.supplyAsync(supplier);
        return Uni.createFrom()
                .completionStage(supplierStage);
    }

}
