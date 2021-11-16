package de.cotto.lndmanagej.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static de.cotto.lndmanagej.model.ChannelFixtures.CAPACITY;
import static de.cotto.lndmanagej.model.ChannelIdFixtures.CHANNEL_ID;
import static de.cotto.lndmanagej.model.ChannelPointFixtures.CHANNEL_POINT;
import static de.cotto.lndmanagej.model.ClosedChannelFixtures.CLOSED_CHANNEL;
import static de.cotto.lndmanagej.model.PubkeyFixtures.PUBKEY;
import static de.cotto.lndmanagej.model.PubkeyFixtures.PUBKEY_2;
import static org.assertj.core.api.Assertions.assertThat;

class ClosedChannelTest {
    @Test
    void create() {
        assertThat(new ClosedChannel(CHANNEL_ID, CHANNEL_POINT, CAPACITY, PUBKEY, PUBKEY_2)).isEqualTo(CLOSED_CHANNEL);
    }

    @Test
    void getId() {
        assertThat(CLOSED_CHANNEL.getId()).isEqualTo(CHANNEL_ID);
    }

    @Test
    void getRemotePubkey() {
        assertThat(CLOSED_CHANNEL.getRemotePubkey()).isEqualTo(PUBKEY_2);
    }

    @Test
    void getCapacity() {
        assertThat(CLOSED_CHANNEL.getCapacity()).isEqualTo(CAPACITY);
    }

    @Test
    void getChannelPoint() {
        assertThat(CLOSED_CHANNEL.getChannelPoint()).isEqualTo(CHANNEL_POINT);
    }

    @Test
    void getPubkeys() {
        assertThat(CLOSED_CHANNEL.getPubkeys()).containsExactlyInAnyOrder(PUBKEY, PUBKEY_2);
    }

    @Test
    void testEquals() {
        EqualsVerifier.forClass(ClosedChannel.class).usingGetClass().verify();
    }
}