import com.google.common.collect.FluentIterable;
import lombok.Data;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import scala.Tuple2;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hongling.shl on 2018/7/17.
 */
public class SparkStudyMain {
	
	public static void main(String[] args) {
		JavaSparkContext sparkContext = new JavaSparkContext((new SparkConf()).setAppName("pyramid-spark").setMaster("local").set("spark.default.parallelism", "20"));
		JavaRDD<TestModel> rdd = sparkContext.parallelize(TestModel.getLists());
		
		long result = rdd.groupBy(new Function<TestModel, Integer>() {
			public Integer call(TestModel testModel) throws Exception {
				return testModel.getKey();
			}
		}).filter(new Function<Tuple2<Integer, Iterable<TestModel>>, Boolean>() {
			public Boolean call(Tuple2<Integer, Iterable<TestModel>> integerIterableTuple2) throws Exception {
				System.out.println("size:" + FluentIterable.from(integerIterableTuple2._2).toList().size());
				return FluentIterable.from(integerIterableTuple2._2).toList().size()>0;
			}
		}).count();
		
		System.out.println("最终结果："+result);
		
	}
	
	@Data
	public static class TestModel implements Serializable {
		private Integer key;
		private Integer value;
		
		TestModel(Integer key, Integer value) {
			this.key = key;
			this.value = value;
		}
		
		public static List<TestModel> getLists() {
			
			List<TestModel> result = new LinkedList<TestModel>();
			
			for (int i = 0; i < 5; i++) {
				result.add(new TestModel(i, i));
			}
			return result;
		}
	}
}
