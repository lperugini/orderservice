package com.sap.orderservice;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sap.orderservice.messaging.MessageConsumer;
import com.sap.orderservice.model.Order;
import com.sap.orderservice.model.OrderRepo;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class) // per usare Mockito
class OrderServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc; // Oggetto MockMvc per simulare le richieste HTTP

	@MockBean // Mock del repository JPA
	private OrderRepo orderRepo; // Mock del repository JPA

	@InjectMocks
	private MessageConsumer orderConsumer; // L'oggetto da testare

	@Test
	public void testGetSingleOrder() throws Exception {
		Order order = new Order(Long.valueOf(1), Long.valueOf(3));
		Optional<Order> optionalOrder = Optional.of(order);

		when(orderRepo.findById(1L)).thenReturn(optionalOrder);

		// Eseguiamo la richiesta GET alla route /orders
		mockMvc.perform(get("/orders/1"))
				.andExpect(status()
						.isOk()) // Verifica che la risposta sia 200 OK
				.andExpect(jsonPath("$.item")
						.value(Long.valueOf(3))); // Verifica il primo ordine
	}

	@Test
	public void testGetAllOrders() throws Exception {
		// Simuliamo i dati restituiti dal repository
		Order order1 = new Order(Long.valueOf(1), Long.valueOf(3));
		Order order2 = new Order(Long.valueOf(2), Long.valueOf(2));
		List<Order> orders = Arrays.asList(order1, order2);

		// Definiamo cosa deve restituire il mock del repository
		when(orderRepo.findAll()).thenReturn(orders);

		// Eseguiamo la richiesta GET alla route /orders
		mockMvc.perform(get("/orders"))
				.andExpect(status().isOk()) // Verifica che la risposta sia 200 OK
				.andExpect(jsonPath("$._embedded.orderList[0].item").value(Long.valueOf(3))) // Verifica il primo ordine
				.andExpect(jsonPath("$._embedded.orderList[1].item").value(Long.valueOf(2)));

		// Verifica che il metodo findAll() sia stato chiamato una sola volta
		verify(orderRepo, times(1)).findAll();
	}

	@Test
	public void testReceiveAndSave() {
		// Simuliamo il messaggio in formato JSON
		 String message = "{\"userId\":1,\"itemId\":1}";

		ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);

		// Chiamata al metodo che riceve e processa il messaggio
		// NON SALVA IN QUANTO MOCK
		orderConsumer.consumeNewOrder(message);

		// Verifico che sia il 4 inserimento - dopo i 3 in LoadDatabase
		verify(orderRepo, times(4)).save(orderCaptor.capture());

		Order savedOrder = orderCaptor.getValue();
		assertEquals(Long.valueOf(1), savedOrder.getItem()); 
		
	}

}
