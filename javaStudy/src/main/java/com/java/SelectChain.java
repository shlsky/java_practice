package com.java;

import java.util.LinkedList;
import java.util.List;

/**
 * 互斥决策链
 */

public class SelectChain<P, D, R> {
	
	//决策集合
	private List<ChainNode<P, D, R>> SelectChains = new LinkedList<>();
	
	//待处理数据
	private P baseData;
	
	//决策规则参数
	private D decideData;
	
	/**
	 * 构建决策链
	 *
	 * @param baseData   待处理数据
	 * @param decideData 决策规则参数
	 * @param <X>
	 * @param <Y>
	 * @param <Z>
	 * @return
	 */
	public static <X, Y, Z> SelectChain<X, Y, Z> BUILD(X baseData, Y decideData) {
		SelectChain<X, Y, Z> selectChain = new SelectChain<>();
		selectChain.baseData = baseData;
		selectChain.decideData = decideData;
		return selectChain;
	}
	
	/**
	 * @param realExecutor
	 * @return
	 */
	public SelectChain<P, D, R> next(ChainNode<P, D, R> realExecutor) {
		this.SelectChains.add(realExecutor);
		return this;
	}
	
	/**
	 * @return
	 */
	public R execute() {
		
		for (ChainNode<P, D, R> function : SelectChains) {
			if (function.isSelect(baseData, decideData)) {
				return function.apply(baseData, decideData);
			}
		}
		
		return null;
	}
	
	/**
	 * 决策点
	 *
	 * @param <M>
	 * @param <N>
	 * @param <V>
	 */
	public abstract static class ChainNode<M, N, V> {
		
		public abstract V apply(M p, N d);
		
		public abstract boolean isSelect(M p, N d);
	}
}
