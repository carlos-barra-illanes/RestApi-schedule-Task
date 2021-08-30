package com.carlos.api.schedule.entity;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseContainer {
	private ArrayList<Day> days;
	private int hoursByDay;
	private int totalDays;
}
