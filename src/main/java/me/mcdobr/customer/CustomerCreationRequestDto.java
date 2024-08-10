package me.mcdobr.customer;

import io.smallrye.common.constraint.NotNull;

public class CustomerCreationRequestDto {
    @NotNull
    private String idempotencyKey;

    @NotNull
    private String name;

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
