package com.carlos.api.schedule.entity;

import java.util.ArrayList;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Day {
	 private String dayId;
	 private int hrsWork = 8;
	 private ArrayList<Task> tasks;
	 private int remaining = 8;

}
