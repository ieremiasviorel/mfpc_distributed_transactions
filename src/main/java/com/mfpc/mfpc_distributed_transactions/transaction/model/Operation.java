package com.mfpc.mfpc_distributed_transactions.transaction.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Operation {
    private UUID id;
    private Transaction parent;
    private OperationType type;
    private Resource resource;
    private String compensationQuery;

    @Override
    public String toString() {
        return "OPERATION [" +
                "id=" + id +
                ", parent=" + parent.getId() +
                ", thread=" + parent.getThread().getId() +
                ", type=" + type +
                ", resource=" + resource +
                " ]";
    }
}
