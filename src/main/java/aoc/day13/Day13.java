package aoc.day13;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import aoc.DayBase;

public class Day13 extends DayBase {

	private Long myArrival;
	private List<Long> ids;
	private Map<Long, Long> idsToDeparture;

	public static void main(String[] args) {
		new Day13().run();
	}

	@Override
	protected List<String> getInputFilenames() {
		return super.getInputFilenames();
//		return Arrays.asList("dummy","dummy2", "dummy3");
	}

	@Override
	protected List<String> getAdditionalInputFileNames() {
		return Arrays.asList("dummy2");
	}

	@Override
	protected void processInput() {
		myArrival = parseLong(input.get(0));
		ids = Arrays.stream(input.get(1).split(",")).filter(id -> !"x".equals(id)).mapToLong(Long::parseLong).boxed().collect(Collectors.toList());
		idsToDeparture = new HashMap<>();
	}

	@Override
	protected void reset() {

	}

	@Override
	protected void puzzle1() {
		idsToDeparture = ids.stream().collect(Collectors.toMap(id -> id, id -> myArrival - myArrival % id + id));
		long min = Integer.MAX_VALUE;
		long minId = -1;
		for (Long id : idsToDeparture.keySet()) {
			Long departure = idsToDeparture.get(id);
			if (departure - myArrival < min) {
				min = departure - myArrival;
				minId = id;
			}
		}

		System.out.println(minId * min);
	}

	@Override
	protected void puzzle2() {
		List<Integer> ids2 = Arrays.stream(input.get(1).split(",")).map(id -> "x".equals(id) ? -1 : parseInt(id)).collect(Collectors.toList());
		Integer[] ids3 = new Integer[ids2.size()];
		ids2.toArray(ids3);
		int[] offsets = new int[ids2.size()];
		for (int i = 0; i < ids2.size(); i++) {
			int offset = 0;
			for (int j = i+1; j < ids2.size(); j++) {
				if (ids2.get(j) == -1) {
					offset++;
				} else {
					break;
				}
			}
			offsets[i] = offset + 1;
		}

		long t = 0L;
		int ci = 0;
		int length = ids3.length;
		long change = 1;
		while (ci < length) {
			if (t % ids3[ci] == 0) {
				change *= ids3[ci];
				int offset = offsets[ci];
				ci += offset;
				t+= offset;
			} else {
				t += change;
			}
		}
		System.out.println(t - ids3.length);
	}

}
