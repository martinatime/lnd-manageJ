package de.cotto.lndmanagej.controller;

import de.cotto.lndmanagej.controller.dto.ObjectMapperConfiguration;
import de.cotto.lndmanagej.model.ChannelIdResolver;
import de.cotto.lndmanagej.model.Coins;
import de.cotto.lndmanagej.pickhardtpayments.MultiPathPaymentComputation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static de.cotto.lndmanagej.model.ChannelIdFixtures.CHANNEL_ID;
import static de.cotto.lndmanagej.model.PubkeyFixtures.PUBKEY;
import static de.cotto.lndmanagej.model.PubkeyFixtures.PUBKEY_2;
import static de.cotto.lndmanagej.pickhardtpayments.model.MultiPathPaymentFixtures.MULTI_PATH_PAYMENT;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SuppressWarnings("CPD-START")
@Import(ObjectMapperConfiguration.class)
@WebMvcTest(controllers = PickhardtPaymentsController.class)
class PickhardtPaymentsControllerIT {
    private static final String PREFIX = "/beta/pickhardt-payments";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MultiPathPaymentComputation multiPathPaymentComputation;

    @MockBean
    @SuppressWarnings("unused")
    private ChannelIdResolver channelIdResolver;

    @Test
    void sendTo() throws Exception {
        Coins amount = MULTI_PATH_PAYMENT.amount();
        String amountAsString = String.valueOf(amount.satoshis());
        double expectedProbability = MULTI_PATH_PAYMENT.probability();
        when(multiPathPaymentComputation.getMultiPathPaymentTo(PUBKEY, amount))
                .thenReturn(MULTI_PATH_PAYMENT);
        mockMvc.perform(get(PREFIX + "/to/" + PUBKEY + "/amount/" + amount.satoshis()))
                .andExpect(jsonPath("$.probability", is(expectedProbability)))
                .andExpect(jsonPath("$.amountSat", is(amountAsString)))
                .andExpect(jsonPath("$.routes", hasSize(1)))
                .andExpect(jsonPath("$.routes[0].amountSat", is(amountAsString)))
                .andExpect(jsonPath("$.routes[0].channelIds", contains(CHANNEL_ID.toString())))
                .andExpect(jsonPath("$.routes[0].probability", is(expectedProbability)));
    }

    @Test
    void send() throws Exception {
        Coins amount = MULTI_PATH_PAYMENT.amount();
        String amountAsString = String.valueOf(amount.satoshis());
        double expectedProbability = MULTI_PATH_PAYMENT.probability();
        when(multiPathPaymentComputation.getMultiPathPaymentTo(PUBKEY, amount))
                .thenReturn(MULTI_PATH_PAYMENT);
        when(multiPathPaymentComputation.getMultiPathPayment(PUBKEY, PUBKEY_2, Coins.ofSatoshis(1_234)))
                .thenReturn(MULTI_PATH_PAYMENT);
        mockMvc.perform(get(PREFIX + "/from/" + PUBKEY + "/to/" + PUBKEY_2 + "/amount/" + 1_234))
                .andExpect(jsonPath("$.probability", is(expectedProbability)))
                .andExpect(jsonPath("$.amountSat", is(amountAsString)))
                .andExpect(jsonPath("$.routes", hasSize(1)))
                .andExpect(jsonPath("$.routes[0].amountSat", is(amountAsString)))
                .andExpect(jsonPath("$.routes[0].channelIds", contains(CHANNEL_ID.toString())))
                .andExpect(jsonPath("$.routes[0].probability", is(expectedProbability)));
    }
}