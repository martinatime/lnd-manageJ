package de.cotto.lndmanagej.controller.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import de.cotto.lndmanagej.model.ChannelId;
import de.cotto.lndmanagej.model.Pubkey;

import java.util.List;

public record NodeDetailsDto(
        @JsonSerialize(using = ToStringSerializer.class) Pubkey node,
        String alias,
        List<ChannelId> channels,
        boolean online
) {
}