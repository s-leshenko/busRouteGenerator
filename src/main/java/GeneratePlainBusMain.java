import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author s.leschenko
 * @since 05/11/2016 16:31
 */
public class GeneratePlainBusMain {

	public static void main(String[] args) throws Exception {
		BusGenerator busGenerator = generateBus(100_000);
		saveToFile(busGenerator);
	}

	/**
	 * You may assume 100,000 as upper limit for the number of bus routes,
	 * 1,000,000 as upper limit for the number of stations,
	 * and 1,000 as upper limit for the number of station of one bus route.
	 * Therefore, your internal data structure should still fit into memory on a suitable machine.
	 */
	private static BusGenerator generateBus(int routeSize) {
		Random random = new Random();
		random.setSeed(123456789);

		Map<Integer, int[]> routeMap = new LinkedHashMap<>();
		BusGenerator busGenerator = new BusGenerator(routeSize, routeMap);

		for (int i = 0; i < routeSize; i++) {
			int[] stationArr = new int[1_000];
			for (int j = 0; j < 1_000; j++) {
				stationArr[j] = random.nextInt(1_000_000);
			}
			routeMap.put(i, stationArr);
			if (i % 5_000 == 0) {
				System.out.println("Route number " + i);
			}
		}
		return busGenerator;
	}

	private static void saveToFile(BusGenerator bus) throws IOException {
		File file = new File("bus_routes.txt");
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();

		PrintWriter printWriter = new PrintWriter(file);
		printWriter.println(bus.getCount());
		for (Map.Entry<Integer, int[]> entry : bus.getRouteMap().entrySet()) {
			printWriter.print(String.valueOf(entry.getKey()) + ' ');
			printWriter.println(
					IntStream.of(entry.getValue())
							.mapToObj(String::valueOf)
							.collect(Collectors.joining(" "))
			);
		}
		printWriter.close();
		System.out.println(file.getAbsolutePath());
	}

	/**
	 * @author s.leschenko
	 * @since 08/11/2016 17:21
	 */
	private static class BusGenerator {
		private final int count;
		private final Map<Integer, int[]> routeMap;

		public BusGenerator(int count, Map<Integer, int[]> routeMap) {
			this.count = count;
			this.routeMap = routeMap;
		}

		public int getCount() {
			return count;
		}

		public Map<Integer, int[]> getRouteMap() {
			return routeMap;
		}
	}
}
