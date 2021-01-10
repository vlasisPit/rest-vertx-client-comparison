# A simple application to compare the performance of Microprofile Rest client and VertX client
Usage of jconsole to check the peak number of threads.

## Running this curl command 200 times with max 10 jobs in parallel.
seq 1 10 | xargs -n1 -P10 curl --request GET --url http://localhost:8080/countries/rxjava/restclient	//peak 88  
seq 1 10 | xargs -n1 -P10 curl --request GET --url http://localhost:8080/countries/rxjava/vertx			//peak 78  

seq 1 3000 | xargs -n1 -P30 curl --request GET --url http://localhost:8080/countries/rxjava/restclient	//peak 152  
seq 1 3000 | xargs -n1 -P30 curl --request GET --url http://localhost:8080/countries/rxjava/vertx		//peak 114  

seq 1 3000 | xargs -n1 -P100 curl --request GET --url http://localhost:8080/countries/rxjava/restclient	//peak 191  
seq 1 3000 | xargs -n1 -P100 curl --request GET --url http://localhost:8080/countries/rxjava/vertx		//peak 113  

seq 1 100000 | xargs -n1 -P1000 curl --request GET --url http://localhost:8080/countries/rxjava/restclient	//peak 190  
seq 1 100000 | xargs -n1 -P1000 curl --request GET --url http://localhost:8080/countries/rxjava/vertx		//peak 108  

seq 1 10000 | xargs -n1 -P2000 curl --request GET --url http://localhost:8080/countries/rxjava/restclient	//peak 200   
seq 1 10000 | xargs -n1 -P2000 curl --request GET --url http://localhost:8080/countries/rxjava/vertx		//peak 112  

## With blocking Microprofile Rest client
For every request, 3 new threads are created from JVM.
1. executor-thread-2
2. vert.x-worker-thread-0
3. vert.x-internal-blocking-0  
#### Logs 
```
2021-01-09 14:59:37,194 INFO  [org.acm.res.con.ExampleResource] (executor-thread-1) First log RxJava with Microprofile Rest Client !!!!  
2021-01-09 14:59:37,769 INFO  [org.acm.res.con.ExampleResource] (executor-thread-1) Run on thread (RxJava) with Microprofile Rest Client ...
2021-01-09 14:59:37,769 INFO  [org.acm.res.con.ExampleResource] (executor-thread-1) -----------------------
```  
 		
 		
## With vertx client which is non-blocking 		
For every request, 3 new threads are created from JVM.
1. vert.x-worker-thread-0
2. vert.x-internal-blocking-0    
A thread from event poll is triggered for every new call
#### Logs
```
2021-01-09 14:55:44,376 INFO  [org.acm.res.con.ExampleResource] (executor-thread-1) First log RxJava with Vertx Client !!!!
2021-01-09 14:55:44,378 WARN  [io.qua.ver.run.VertxProducer] (executor-thread-1) `io.vertx.reactivex.core.Vertx` is deprecated  and will be removed in a future version - it is recommended to switch to `io.vertx.mutiny.core.Vertx`
2021-01-09 14:55:44,949 INFO  [org.acm.res.con.ExampleResource] (vert.x-eventloop-thread-4) Run on thread with Vertx client...
2021-01-09 14:55:44,949 INFO  [org.acm.res.con.ExampleResource] (vert.x-eventloop-thread-4) -----------------------
```