package com.realtimestudio.transport.event.simulation.emitter.impl;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.realtimestudio.transport.event.GPSSignalParser;
import com.realtimestudio.transport.event.gps.GPSSignalParserImpl;
import com.realtimestudio.transport.event.simulation.Generator.SignalGenerator;
import com.realtimestudio.transport.event.simulation.Generator.Impl.SignalGeneratorImpl;
import com.realtimestudio.transport.event.simulation.config.SimulationConfiguration;
import com.realtimestudio.transport.event.simulation.emitter.SignalEmitter;
import com.realtimestudio.transport.model.Car;
import com.realtimestudio.transport.model.Driver;
import com.realtimestudio.transport.model.Route;
import com.realtimestudio.transport.streaming.SignalCollector;
import com.realtimestudio.transport.utils.RandomUtils;

public class FastSimulator implements SignalEmitter {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(FastSimulator.class);

	private static List<Car> cars = SimulationConfiguration.getCars();
	private static List<Driver> drivers = SimulationConfiguration.getDrivers();
	private static List<Route> routes = SimulationConfiguration
			.getRoutes();
	
	private static String SEPARATOR = "|";

	private int roundNum;
	private int interval; // sec
	private int num_emitters;
	private List<Integer> randomList;
	private SignalCollector signalCollector;
	private PrintWriter printWriter;
	

	public FastSimulator(int roundNum, int interval, int emitterNum, String outputFilePath) throws FileNotFoundException {
		super();
		this.roundNum = roundNum;
		this.interval = interval;
		this.num_emitters = emitterNum;
		printWriter = new PrintWriter(new File(outputFilePath));
		
		signalCollector = new SignalCollector(){
			

			@Override
			public void send(String signal) {
				synchronized(this){
					printWriter.println(signal);
				}	
			}

			@Override
			public void send(String key, String signal) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void close() {
				printWriter.flush();
				printWriter.close();
				
			}
			
		};
	}

	//random choose N drivers
	private void initCars() {
		
		randomList = RandomUtils.getRandomNumbers(0, drivers.size()-1, num_emitters);
		LOGGER.info("Getting random list of drivers: " + randomList);
		Date now = new Date();
		
		for(int i=0; i<num_emitters; i++){
			Driver driver = drivers.get(randomList.get(i));
			cars.get(i).setDriver(driver);
			cars.get(i).setRouteName(routes.get(i).getStart() + SEPARATOR  + routes.get(i).getEnd() + SEPARATOR
			+ driver.getLicenseNum() + SEPARATOR + now.getTime());
		}		
	}
	
	private void oneRoundRun(int round){
		LOGGER.info("Round "+ round);
		ExecutorService threadPools = Executors.newFixedThreadPool(num_emitters);
		try {
			initCars();
			List<Callable<Long>> threads = new ArrayList<>(num_emitters);
			LOGGER.info("Creating the simulator threads");
			for (int i = 0; i < num_emitters; i++) {
				threads.add(new SignalEmitterThread(cars.get(i), routes.get(i)));
			}

			LOGGER.info("Starting the simulator threads");
			threadPools.invokeAll(threads);
		} catch (InterruptedException e) {
				LOGGER.error("emitter thread is interrupted");
		} finally {
			threadPools.shutdown();
		}
		LOGGER.info("Signal emission is completed");
	}

	@Override
	public void emit() {
		LOGGER.info(num_emitters+" cars are going to run for one round. The total round is " + roundNum);
		for(int i=1; i<=roundNum; i++){
			oneRoundRun(i);
			doSleep();
		}
		signalCollector.close();
		LOGGER.info("Simulation is completed");
		
	}
	
	private void doSleep(){
		try {
			TimeUnit.SECONDS.sleep(interval);
		} catch (InterruptedException e) {
			String errorMessage = "SignalEmitterThead is interrupted: " + toString();
		    LOGGER.error(errorMessage);
			throw new RuntimeException(errorMessage);
		}
		
	}

	private class SignalEmitterThread implements Callable<Long> {
		private long count;
		private Car car;
		private Route route;
		
		public SignalEmitterThread(Car car, Route route) {
			this.car = car;
			this.route = route;
			count = 0;
			LOGGER.info("New Emission thread is initalized: " + toString());
		}
		
		private void onerun() throws ParseException{
			SignalGenerator signalGenerator = new SignalGeneratorImpl(car.getDriver(), car,  route.getLocations());
			List<String> signals = signalGenerator.generateSignals();
				
			for (String rawSignal : signals) {
				   String signal = getSignal(rawSignal);
			       signalCollector.send(signal);
				   
			    }
		}
		
		private String getSignal(String raw) throws ParseException{
			GPSSignalParser parser=new GPSSignalParserImpl(raw);
			StringBuilder builder = new StringBuilder();
			builder.append(car.getRouteName());
			builder.append(SEPARATOR);
			builder.append(parser.getRoutePoint().getLocation().getLatitude());
			builder.append(SEPARATOR);
			builder.append(parser.getRoutePoint().getLocation().getLongitude());
			builder.append(SEPARATOR);
			builder.append(parser.getRoutePoint().getGasVol());
			builder.append(SEPARATOR);
			builder.append(parser.getRoutePoint().getSpeed());
			builder.append(SEPARATOR);
			builder.append(parser.getRoutePoint().getWeather());
			builder.append(SEPARATOR);
			builder.append(parser.getRoutePoint().getPressure());	
			return builder.toString();
		}

		@Override
		public Long call() throws ParseException {
			onerun();		
			return count;
		}
		
		@Override
		public String toString(){
			return String.format("car=%s; route=%s; thread=%s", car.getId(), car.getRouteName(), Thread.currentThread().getName());
		}
	}
	
	public static void main (String[] args) throws FileNotFoundException{
		int roundNum = Integer.parseInt(args[0]);
		int interval = Integer.parseInt(args[1]);
		int numDrivers = Integer.parseInt(args[2]);
		String path = args[3];
		FastSimulator simulator = new FastSimulator(roundNum, interval, numDrivers, path);
		simulator.emit();
	}

}
