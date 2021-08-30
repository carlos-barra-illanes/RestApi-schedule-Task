package com.carlos.api.schedule.controller;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.carlos.api.schedule.entity.Day;
import com.carlos.api.schedule.entity.Task;
import com.carlos.api.schedule.services.ScheduleService;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Value;





@RestController
@RequestMapping("/scheduleByDays")
@Api(value = "Users microservice", description = "Api que maneja una agenda de dias y tareas ")
public class ScheduleController {
	
	private static final String TASKS = "/tasks";
	private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${url.api.task}")
    private String url;
	
	

	@Autowired
	ScheduleService scheduleService;

	@ApiOperation(value = "Obtiene el total de dias necesarios para realizar las N tareas que devuelve la api RANDOM")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Day.class ),
			@ApiResponse(code = 400, message = "Request mal formado ", response = String.class),
			@ApiResponse(code = 503, message = "No fue posible conectar a la API que genera las tareas ", response = String.class),
			@ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@GetMapping(value = TASKS, produces = {MediaType.APPLICATION_JSON_VALUE})
	@CrossOrigin(origins="*")
	public ResponseEntity<Object> getScheduleOrder() {
		
		logger.info("Se intenta conectar con : " + url);
		try {
			ResponseEntity<Task[]> responseEntity = restTemplate.getForEntity(url, Task[].class);
			Task[] tasks = responseEntity.getBody();
			logger.info("Se conecto con exito a  : " + url);		
			return new ResponseEntity<>(
					scheduleService.calculatedDays(tasks), 
					HttpStatus.OK);

		}catch (HttpClientErrorException ex) {
			logger.error("Se produjo en error intentando conectar ");
			ex.printStackTrace(); 
			return ResponseEntity
					.status(HttpStatus.SERVICE_UNAVAILABLE)
					.body("No fue posible conectar a la API que genera las tareas");
		}     
		catch (Exception e) {
			logger.error("Se produjo en error general ");
			e.printStackTrace(); 
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error Message");
		}


	}


}
