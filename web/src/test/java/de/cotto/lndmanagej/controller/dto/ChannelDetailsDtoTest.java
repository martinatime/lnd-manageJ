package de.cotto.lndmanagej.controller.dto;

import de.cotto.lndmanagej.model.Coins;
import org.junit.jupiter.api.Test;

import static de.cotto.lndmanagej.model.BalanceInformationFixtures.BALANCE_INFORMATION;
import static de.cotto.lndmanagej.model.ChannelFixtures.CAPACITY;
import static de.cotto.lndmanagej.model.ChannelIdFixtures.CHANNEL_ID;
import static de.cotto.lndmanagej.model.ChannelPointFixtures.CHANNEL_POINT;
import static de.cotto.lndmanagej.model.CoopClosedChannelFixtures.CLOSED_CHANNEL;
import static de.cotto.lndmanagej.model.LocalOpenChannelFixtures.LOCAL_OPEN_CHANNEL;
import static de.cotto.lndmanagej.model.LocalOpenChannelFixtures.LOCAL_OPEN_CHANNEL_PRIVATE;
import static de.cotto.lndmanagej.model.NodeFixtures.ALIAS;
import static de.cotto.lndmanagej.model.PubkeyFixtures.PUBKEY_2;
import static org.assertj.core.api.Assertions.assertThat;

class ChannelDetailsDtoTest {

    private static final OnChainCostsDto ON_CHAIN_COSTS = new OnChainCostsDto(Coins.ofSatoshis(1), Coins.ofSatoshis(2));
    private static final ChannelDetailsDto CHANNEL_DETAILS_DTO =
            new ChannelDetailsDto(CLOSED_CHANNEL, ALIAS, BALANCE_INFORMATION, ON_CHAIN_COSTS);

    @Test
    void channelIdShort() {
        assertThat(CHANNEL_DETAILS_DTO.channelIdShort()).isEqualTo(String.valueOf(CHANNEL_ID.getShortChannelId()));
    }

    @Test
    void channelIdCompact() {
        assertThat(CHANNEL_DETAILS_DTO.channelIdCompact()).isEqualTo(CHANNEL_ID.getCompactForm());
    }

    @Test
    void channelIdCompactLnd() {
        assertThat(CHANNEL_DETAILS_DTO.channelIdCompactLnd()).isEqualTo(CHANNEL_ID.getCompactFormLnd());
    }

    @Test
    void channelPoint() {
        assertThat(CHANNEL_DETAILS_DTO.channelPoint()).isEqualTo(CHANNEL_POINT);
    }

    @Test
    void openHeight() {
        assertThat(CHANNEL_DETAILS_DTO.openHeight()).isEqualTo(CHANNEL_ID.getBlockHeight());
    }

    @Test
    void remotePubkey() {
        assertThat(CHANNEL_DETAILS_DTO.remotePubkey()).isEqualTo(PUBKEY_2);
    }

    @Test
    void remoteAlias() {
        assertThat(CHANNEL_DETAILS_DTO.remoteAlias()).isEqualTo(ALIAS);
    }

    @Test
    void capacity() {
        assertThat(CHANNEL_DETAILS_DTO.capacity()).isEqualTo(String.valueOf(CAPACITY.satoshis()));
    }

    @Test
    void privateChannel_false() {
        assertThat(CHANNEL_DETAILS_DTO.privateChannel()).isFalse();
    }

    @Test
    void privateChannel_true() {
        ChannelDetailsDto dto =
                new ChannelDetailsDto(LOCAL_OPEN_CHANNEL_PRIVATE, ALIAS, BALANCE_INFORMATION, ON_CHAIN_COSTS);
        assertThat(dto.privateChannel()).isTrue();
    }

    @Test
    void active_true() {
        ChannelDetailsDto dto =
                new ChannelDetailsDto(LOCAL_OPEN_CHANNEL, ALIAS, BALANCE_INFORMATION, ON_CHAIN_COSTS);
        assertThat(dto.active()).isTrue();
    }

    @Test
    void active_false() {
        assertThat(CHANNEL_DETAILS_DTO.active()).isFalse();
    }

    @Test
    void closed_true() {
        assertThat(CHANNEL_DETAILS_DTO.closed()).isTrue();
    }

    @Test
    void closed_false() {
        ChannelDetailsDto dto =
                new ChannelDetailsDto(LOCAL_OPEN_CHANNEL, ALIAS, BALANCE_INFORMATION, ON_CHAIN_COSTS);
        assertThat(dto.closed()).isFalse();
    }

    @Test
    void balance() {
        assertThat(CHANNEL_DETAILS_DTO.balance()).isEqualTo(BalanceInformationDto.createFrom(BALANCE_INFORMATION));
    }

    @Test
    void onChainCosts() {
        assertThat(CHANNEL_DETAILS_DTO.onChainCosts()).isEqualTo(ON_CHAIN_COSTS);
    }
}