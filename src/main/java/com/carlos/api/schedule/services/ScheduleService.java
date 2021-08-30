package com.carlos.api.schedule.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.carlos.api.schedule.entity.Day;
import com.carlos.api.schedule.entity.ResponseContainer;
import com.carlos.api.schedule.entity.Task;



@Service
public class ScheduleService {
	
	private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);
	@Value("${url.api.hours}")
    private int hours;
	
	public ResponseContainer calculatedDays(Task[] tasks) {
			
		ArrayList<Task> tasksByList = new ArrayList<Task>(Arrays.asList(tasks));
		int totalHrs = 0;
		for (int i = 0; i < tasks.length; i++) {
			Task task = (Task) tasks[i];
			task.setRemaining(task.getDuration().intValue());
			totalHrs = totalHrs + task.getDuration().intValue();
		}
		
		int totalDays = totalHrs/hours;
		int restTotal = totalHrs%hours;
		if(restTotal > 0) {
			totalDays = totalDays +1;
		} 
		logger.debug("Debug Total de horas " + totalHrs);
		logger.debug("Debug Total de dias " + totalDays);
		logger.debug("Debug Division resto de dias " + restTotal);
		
		ArrayList<Day> days = new ArrayList<>();
		
		for (int i = 0; i < totalDays; i++) {
			Day day = new Day(); 
			day.setDayId(String.valueOf(i));
			ArrayList<Task> taskByDay = new ArrayList<>();	
			logger.debug("Debug Dia" + day.getDayId());
			for (Iterator iterator = tasksByList.iterator(); iterator.hasNext();) {
				Task task = (Task) iterator.next();	
				if(task.getSuccess() == 0 && day.getRemaining() == task.getRemaining()) {
					taskByDay.add(task);
					day.setRemaining(day.getRemaining() - task.getRemaining());
					task.setRemaining(day.getRemaining() );
					task.setSuccess(1);
					break;
				}
				if(task.getSuccess() == 0 && day.getRemaining() > task.getRemaining()){
					taskByDay.add(task);
					day.setRemaining(day.getRemaining() - task.getRemaining());
					task.setRemaining(0);
					task.setSuccess(1);
				}
				if(task.getSuccess() == 0 && day.getRemaining() < task.getRemaining()){

					taskByDay.add(task);
					task.setRemaining(task.getRemaining() - day.getRemaining());
					day.setRemaining(0);
					
					break;
				}			
										
				
			}
			day.setTasks(taskByDay);
			days.add(day);

		}
		ResponseContainer response = new ResponseContainer(days,hours,totalDays);
		return response;
	}

}
