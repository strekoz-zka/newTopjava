package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * GKislin 31.05.2015.
 */
public class UserMealsUtil {
	public static void main(String[] args) {
		List<UserMeal> mealList = Arrays.asList(
				new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
				new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
				new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
				new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
				new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
				new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
		List<UserMealWithExceed> filteredWithExceeded = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
		filteredWithExceeded.forEach(System.out::println);
		// .toLocalDate();
		// .toLocalTime();
	}

	public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime,
			LocalTime endTime, int caloriesPerDay) {
		Map<LocalDate, Integer> caloriesByDate = new HashMap<>();
		mealList.forEach(userMeal -> caloriesByDate.put(userMeal.getDateTime().toLocalDate(),
				caloriesByDate.getOrDefault(userMeal.getDateTime().toLocalDate(), 0) + userMeal.getCalories()));
		List<UserMealWithExceed> withExceed = new ArrayList<>();
		mealList.forEach(u -> withExceed.add(new UserMealWithExceed(u.getDateTime(), u.getDescription(), u.getCalories(),
				caloriesByDate.get(u.getDateTime().toLocalDate()) > caloriesPerDay)));
		return withExceed.stream().filter((u) -> TimeUtil.isBetween(u.getDateTime().toLocalTime(), startTime, endTime)).collect(Collectors.toList());
	}
}