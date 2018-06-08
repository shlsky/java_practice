package com.shl.rxjava.study;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by hongling.shl on 2018/6/8.
 */
public class RxJavaSerice {
	
	public static void main(String[] args) {
		
		Observable<String> myObservable = Observable.create(new Observable.OnSubscribe<String>() {
			public void call(Subscriber<? super String> subscriber) {
				subscriber.onNext("onNext");
//				subscriber.onCompleted();
			}
		});
		
//		myObservable.subscribe(new Subscriber<String>() {
//			public void onCompleted() {
//				System.out.println("Subscriber onCompleted");
//			}
//
//			public void onError(Throwable e) {
//				System.out.println(e.getMessage());
//			}
//
//			public void onNext(String s) {
//				System.out.println("Subscriber " + s);
//			}
//		});
		
		myObservable.subscribe(new Observer<String>() {
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
