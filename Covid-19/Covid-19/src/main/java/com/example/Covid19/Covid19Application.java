package com.example.Covid19;

import com.example.Covid19.BluePrint.Allcountry;
import com.example.Covid19.BluePrint.Countries.Country;
import com.example.Covid19.BluePrint.Countries.Root;
import com.example.Covid19.Repositories.AllCountryRepository;
import com.example.Covid19.Repositories.CountryRepository;
import com.example.Covid19.database.AllCountryData;
import com.example.Covid19.database.Countrydata;
import com.google.gson.Gson;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Scanner;

/* You will create a simple webapp which provides the users with all the updates on Covid-19 statistics around the world
 that are retrieved from [COVID-19 API](https://documenter.getpostman.com/view/10808728/SzS8rjbc#27454960-ea1c-4b91-a0b6-0468bb4e6712)
*/
@SpringBootApplication
public class Covid19Application {

	public static void main(String[] args) {
		SpringApplication.run(Covid19Application.class, args);

	}

	@Bean
	CommandLineRunner initDatabase(CountryRepository countryRepository , AllCountryRepository allCountryRepository) {
		return args -> {

			// Write On DATABASE
			//  Convert To Object From JSON Format
			String dataJson = ReadFromAPI("https://api.covid19api.com/summary");
			Gson gson = new Gson();
			if(countryRepository.findAll().size() == 0)
			{
				//Get the results Countries
				Root countryResult = gson.fromJson(dataJson, Root.class); // All Countries
				for (Country r:countryResult.Countries
				) {
					SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
					String stringDate = DateFor.format(r.Date);
					Countrydata country = new Countrydata(r.Country,r.TotalConfirmed,r.TotalDeaths,r.TotalRecovered,r.Date,stringDate);
					countryRepository.save(country);
				}
			}

			//  Convert To Object From JSON Format
			String dataJsonAllCountry = ReadFromAPINEW("https://api.covid19api.com/countries");
			Gson gsonAllCountry = new Gson();
			//Gson gsonAllCountry = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			if(allCountryRepository.findAll().size() == 0)
			{
				//Get All countries name
				Allcountry[] countryResult = gsonAllCountry.fromJson(dataJsonAllCountry, Allcountry[].class); // All Countries
				for (Allcountry r: countryResult
				) {
					AllCountryData country = new AllCountryData(r.Country,r.Slug);
					allCountryRepository.save(country);
				}

			}
		};
	}
	public static String ReadFromAPI(String url) throws FileNotFoundException {

		String dataJson = "";
		Gson gson = new Gson();
		try {
			// Make connection
			URL recipesURL = new URL(url);

			HttpURLConnection statisticsURLConnection = (HttpURLConnection) recipesURL.openConnection();
			// send GET request
			statisticsURLConnection.setRequestMethod("GET");
			statisticsURLConnection.connect(); // added
			// Read the Response
			InputStreamReader statisticsURLReader = new InputStreamReader(statisticsURLConnection.getInputStream());
			BufferedReader statisticsURLBuffered = new BufferedReader(statisticsURLReader);
			// Get the data
			dataJson = statisticsURLBuffered.readLine();

		} catch (Exception e) {
			// Read From File If OFFLINE //
			System.out.println(">>>>>>>> ERROR "+ e.toString());
		}

		return dataJson;
	}

	public static String ReadFromAPINEW(String url) throws FileNotFoundException {

		String dataJson = "";

		Gson gson = new Gson();
		try {
			// Make connection
			URL recipesURL = new URL(url);

			HttpURLConnection statisticsURLConnection = (HttpURLConnection) recipesURL.openConnection();
			// send GET request
			statisticsURLConnection.setRequestMethod("GET");
			statisticsURLConnection.connect(); // added
//			// Read the Response
//			InputStreamReader statisticsURLReader = new InputStreamReader(statisticsURLConnection.getInputStream());
//			BufferedReader statisticsURLBuffered = new BufferedReader(statisticsURLReader);
//			// Get the data
//			dataJson = statisticsURLBuffered.readLine();

			/* https://medium.com/swlh/getting-json-data-from-a-restful-api-using-java-b327aafb3751
			   I used this insted for catch the country name data because it didn't work with ReadFromAPI() function
			 */
			//Getting the response code
			int responsecode = statisticsURLConnection.getResponseCode();

			if (responsecode != 200) {
				throw new RuntimeException("HttpResponseCode: " + responsecode);
			} else {

				Scanner scanner = new Scanner(recipesURL.openStream());

				//Write all the JSON data into a string using a scanner
				while (scanner.hasNext()) {
					dataJson += scanner.nextLine();
				}

				//Close the scanner
				scanner.close();
			}
		} catch (Exception e) {
			// Read From File If OFFLINE //
			System.out.println(">>>>>>>> ERROR "+ e.toString());
		}

		return dataJson;
	}

}
