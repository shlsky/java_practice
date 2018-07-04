package java.com.java.study;


import com.google.common.collect.Lists;
import com.java.SelectChain;
import lombok.Getter;
import org.junit.Test;

import java.util.List;

/**
 * Created by hongling.shl on 2018/7/4.
 */
public class SelectChainTest {
	
	@Test
	public void test() {
		SelectChain<List<Integer>, TestInnerModel, Integer> selectChain = SelectChain.<List<Integer>, TestInnerModel, Integer>BUILD(Lists.newArrayList(1, 2, 3), TestInnerModel.build(2, 3))
				.next(new SelectChain.ChainNode<List<Integer>, TestInnerModel, Integer>() {
					@Override
					public Integer apply(List<Integer> p, TestInnerModel d) {
						System.out.println("执行first");
						return null;
					}
					
					@Override
					public boolean isSelect(List<Integer> p, TestInnerModel d) {
						return !(d.getFirst() == null);
					}
				}).next(new SelectChain.ChainNode<List<Integer>, TestInnerModel, Integer>() {
					@Override
					public Integer apply(List<Integer> p, TestInnerModel d) {
						System.out.println("执行second");
						return null;
					}
					
					@Override
					public boolean isSelect(List<Integer> p, TestInnerModel d) {
						return !(d.getSecond() == null);
					}
				});
		selectChain.execute();
		
		
	}
	
	@Getter
	public static class TestInnerModel {
		private Integer first;
		private Integer second;
		private Integer third;
		
		private TestInnerModel(Integer first, Integer second) {
			this.second = first;
			this.third = second;
		}
		
		public static TestInnerModel build(Integer first, Integer second) {
			return new TestInnerModel(first, second);
		}
	}
}
