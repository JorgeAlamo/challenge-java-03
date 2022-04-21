package com.business.service.payment;

import com.business.service.payment.entities.Channel;
import com.business.service.payment.entities.Servicing;
import com.business.service.payment.entities.Transaction;
import com.business.service.payment.repositories.ServicingRepository;
import com.business.service.payment.repositories.TransactionRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;

@OpenAPIDefinition(
		info = @Info(
				title = "Transaction Service",
				version = "1.0",
				description = "Documentation Transaction Service API v1.0"))
@EnableEurekaClient
@SpringBootApplication
public class ServicePaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicePaymentApplication.class, args);
	}

	@Bean
	public CommandLineRunner initializer(ServicingRepository servicingRepository,
										 TransactionRepository transactionRepository) {
		return (args) -> {
			Channel ch1 = new Channel("Banca Movil",
					"BM");
			Channel ch2 = new Channel("Banca por Internet",
					"BI");

			Servicing sr1 = new Servicing("6224e5ce19c03918bd6c9507",
					"Enel Generacion Peru",
					"30102",
					"Luz",
					ch1);
			Servicing sr2 = new Servicing("6224e5ce19c03918bd6c9508",
							"Cibertec Peru S.a.c.",
							"42324",
							"Otras Empresas",
							ch2);
			Servicing sr3 = new Servicing("6224e5ce19c03918bd6c9509",
					"Movistar",
					"63016",
					"Telefono",
					ch1);
			Servicing sr4 = new Servicing("6224e5ce19c03918bd6c950a",
					"Win Peru",
					"67831",
					"Internet",
					ch2);

			Transaction tr1 = new Transaction("6224ec5bc634f04c0890ce17",
					"8721396",
					"30102",
					new BigDecimal("150.50"),
					"AS9999A001",
					true,
					"Pago Enel");
			Transaction tr2 = new Transaction("6224ec5bc634f04c0890ce18",
					"2887475",
					"42324",
					new BigDecimal("2750.00"),
					"DF9999B042",
					true,
					"Pago Cibertec");
			Transaction tr3 = new Transaction("6224ec5bc634f04c0890ce19",
					"9710035",
					"63016",
					new BigDecimal("89.20"),
					"RT9999F007",
					true,
					"Pago Movistar Hogar");

			Flux<Servicing> srFlux = Flux.just(sr1, sr2, sr3, sr4);
			Flux<Transaction> trFlux = Flux.just(tr1, tr2, tr3);

			Flux.merge(
					servicingRepository.deleteAll(),
					transactionRepository.deleteAll()).blockLast();

			srFlux.flatMap(servicingRepository::save)
					.flatMap(service ->
							trFlux.flatMap(
									transactionRepository::save)).blockLast();

		};
	}

}
