package com.java;

import com.google.common.collect.Lists;
import com.google.common.collect.Range;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by hongling.shl on 2018/7/18.
 */
public class RangeStudy {
	
	private final static BigDecimal TOTAL_PROBABILITY =  new BigDecimal("1.0");
	
	public static void main(String[] args) {
		
		
		List<String> values = Lists.newArrayList("0.5","0.49999","0.0000001");
		
		Optional<String> sumProbabilityOpt = values.stream().reduce((x1, x2)
				-> (new BigDecimal(x1)).add(new BigDecimal(x2)).toString());
		if (!sumProbabilityOpt.isPresent() || (new BigDecimal("1.0")).compareTo(new BigDecimal(sumProbabilityOpt.get()))<0){
			throw new RuntimeException("奖品中奖概率不合法！！");
		}
		
		System.out.println(TOTAL_PROBABILITY.subtract(new BigDecimal(sumProbabilityOpt.get())).toString());
		BigDecimal bigDecimal = new BigDecimal(sumProbabilityOpt.get());
		
		int scale = bigDecimal.scale();
		
		BigDecimal baseUpperBound = new BigDecimal("10").pow(scale);
		
		Long minBound = 0L;
		Long maxBound = 0L;
		
		Map<String,Range<Long>> distribution = new HashMap<>();
		
		for (String type : values) {
			maxBound = minBound + new BigDecimal(type).multiply(baseUpperBound).longValue();
			distribution.put(type,Range.closedOpen(minBound,maxBound));
			minBound = maxBound;
		}
		
		System.out.println(distribution);
		
	}
}
