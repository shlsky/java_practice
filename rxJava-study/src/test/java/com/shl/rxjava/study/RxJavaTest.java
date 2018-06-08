package com.shl.rxjava.study;

import org.junit.Test;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by hongling.shl on 2018/6/8.
 */
public class RxJavaTest {
	
	/**
	 * OnSubscribe 衔接 Observable(被观察者) 和 Observer(观察者)
	 * OnSubscribe 决定了Observable发射事件时会执行Observer的哪些动作
	 */
	@Test
	public void testOnSubscribe() {
		
		Observable.create(new Observable.OnSubscribe<String>() {
			
			public void call(Subscriber<? super String> subscriber) {
				subscriber.onNext("onNext");
				//如果注释此处，Observer的onCompleted()不会执行
				subscriber.onCompleted();
			}
		}).subscribe(new Observer<String>() {
			
			public void onCompleted() {
				System.out.println("Observer onCompleted");
			}
			
			public void onError(Throwable e) {
				System.out.println(e.getMessage());
			}
			
			public void onNext(String s) {
				System.out.println("Observer " + s);
			}
		});
	}
	
	@Test
	public void testObservableJust() {
		
		Observable.just("hello").subscribe(new Observer<String>() {
			
			public void onCompleted() {
				System.out.println("Observer onCompleted");
			}
			
			public void onError(Throwable e) {
				System.out.println(e.getMessage());
			}
			
			public void onNext(String s) {
				System.out.println("Observer " + s);
			}
		});
	}
}
