package com.carlos.api.schedule;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.carlos.api.schedule.entity.Task;
import com.carlos.api.schedule.services.ScheduleService;

@SpringBootTest
class RestApiScheduleTaskApplicationTests {
	@Value("${url.api.task}")
    private String url;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	ScheduleService scheduleService;
	@Test
	void contextLoads() {
		try {
			ResponseEntity<Task[]> responseEntity = restTemplate.getForEntity(url, Task[].class);
			Task[] tasks = responseEntity.getBody();			

		}  
		catch (Exception e) {
			e.printStackTrace(); 
			fail("No se pudo  conectar a : " + url);
		
		}
	}

}
