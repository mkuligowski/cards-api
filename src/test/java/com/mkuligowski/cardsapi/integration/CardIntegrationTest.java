package com.mkuligowski.cardsapi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkuligowski.cardsapi.cards.domainapi.MaskedCreditCard;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class CardIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldAddCreditCard() throws Exception {
        mockMvc.perform(
                post("/api/cards")
                        .content("{" +
                                "\"cardNumber\":\"1111222233334444\"," +
                                "\"cvv\":\"999\"," +
                                "\"expirationDate\":\"2022-08\"" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardNumber", is("xxxx-xxxx-xxxx-x444")));
    }

    @Test
    public void shouldNotAddCreditCardWhenCardNumberFormatIsInvalid() throws Exception {
        mockMvc.perform(
                post("/api/cards")
                        .content("{" +
                                "\"cardNumber\":\"1234-1234-1234-1234\"," +
                                "\"cvv\":\"999\"," +
                                "\"expirationDate\":\"2022-08\"" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest());
    }


    @Test
    public void shouldNotAddCreditCardWhenExpirationDateFormatIsInvalid() throws Exception {
        mockMvc.perform(
                post("/api/cards")
                        .content("{" +
                                "\"cardNumber\":\"1234123412341234\"," +
                                "\"cvv\":\"999\"," +
                                "\"expirationDate\":\"22-08\"" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest());
    }


    @Test
    public void shouldNotAddCreditCardWhenCVVNotMatch() throws Exception {
        mockMvc.perform(
                post("/api/cards")
                        .content("{" +
                                "\"cardNumber\":\"1234123412341234\"," +
                                "\"cvv\":\"000\"," +
                                "\"expirationDate\":\"2022-08\"" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldNotAddCreditCardTwice() throws Exception {
        String json = mockMvc.perform(
                post("/api/cards")
                        .content("{" +
                                "\"cardNumber\":\"1234123412349999\"," +
                                "\"cvv\":\"999\"," +
                                "\"expirationDate\":\"2022-08\"" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
        .andReturn()
        .getResponse().getContentAsString();

        MaskedCreditCard createdCard = objectMapper.readValue(json, MaskedCreditCard.class);

        mockMvc.perform(
                post("/api/cards")
                        .content("{" +
                                "\"cardNumber\":\"1234123412349999\"," +
                                "\"cvv\":\"999\"," +
                                "\"expirationDate\":\"2022-08\"" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isSeeOther())
                .andExpect(header().string("Location",String.format("/api/cards/%d", createdCard.getId())));
    }


    @Test
    public void shouldFetchAddedCreditCard() throws Exception {
        String json = mockMvc.perform(
                post("/api/cards")
                        .content("{" +
                                "\"cardNumber\":\"1234123412341234\"," +
                                "\"cvv\":\"999\"," +
                                "\"expirationDate\":\"2022-08\"" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        MaskedCreditCard createdCard = objectMapper.readValue(json, MaskedCreditCard.class);

        mockMvc.perform(
                get("/api/cards/{id}", createdCard.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardNumber", is("xxxx-xxxx-xxxx-x234")))
                .andExpect(jsonPath("$.expirationDate", is("2022-08")));
    }
}
