import groovyx.net.http.HTTPBuilder;
import static groovyx.net.http.Method.GET;
import static groovyx.net.http.ContentType.TEXT;

def http = new HTTPBuilder('http://www.google.com');
 
http.get(path : '/search',contentType : TEXT,query : [q:'Groovy'] ) { objResponse, objReader ->

    println "response status: ${resp.statusLine}";
    println 'Headers: -----------';
    objResponse.headers.each { objHeader ->
        println " ${objHeader.name} : ${objHeader.value}";
    }

    println 'Response data: -----';
    System.out << objReader;
    println '\n--------------------';
}