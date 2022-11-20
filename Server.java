package bing;

import java.io.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;
import java.util.*;
import java.util.HashMap;


class Node {
    Node pre;
	Node next;
	int key;
	int value;//又是dsaa熟悉的逆天双向链表
}

 class  LRU {

    public static int rongliang;
    public static int capacity;
   public static Node head;
     public static Node tail;
     public static HashMap<Integer, Node>  cache = new HashMap<Integer,Node>();

    public  LRU(int capacity) {
        this.rongliang = 0;
        this.capacity = capacity;

       head = new Node();
       tail = new Node();
        head.pre = null;
        tail.next = null;//初始化头部和尾部


        head.next = tail;
        tail.pre = head;//建立头部和尾部的初始关系
    }

    public int getvalue(int key) {

        Node node = cache.get(key);
        if(node == null){
            return -1; //
        }
        this.move(node);
        return node.value;
    }//获取缓存内某key的值


    public void set(int key, int value) {
        Node node = cache.get(key);
        if(node == null){
            Node aa = new Node();
            aa.key = key;
            aa.value = value;

            this.cache.put(key, aa);
            this.add(aa);
            rongliang++;

            if(rongliang > capacity){
                // pop the tail
                Node tail = this.pop();
                this.cache.remove(tail.key);
                rongliang=rongliang-1;
            }
        }//增加键值对
        else{
            node.value = value;
            this.move(node);
        }//更新键的值
    }//向缓存中添加值

    public static void add(Node node){
        node.pre = head;
        node.next = head.next;
        head.next.pre = node;
        head.next = node;
    }//增加节点，并且放在头部的next处

    public static void delete(Node node){
        node.pre.next = node.next;
        node.next.pre = node.pre;
    }//移除某节点

    public void move(Node node){
        this.delete(node);
        this.add(node);
    }//将节点移动到头部

    public Node pop(){
        Node dd = tail.pre;
        this.delete(dd);
        return dd;
    }//删除并返回位于栈底的元素

}
class Main{

    static class Untitled {
        public static void main(String[] args) {
            QReader in = new QReader();
            QWriter out = new QWriter();
            while (in.hasNext()) {
                int x = in.nextInt();
                out.println(x);
            }
            out.close();
        }
    }
    static class QReader {
        private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        private StringTokenizer tokenizer = new StringTokenizer("");

        private String innerNextLine() {
            try {
                return reader.readLine();
            } catch (IOException e) {
                return null;
            }
        }

        public boolean hasNext() {
            while (!tokenizer.hasMoreTokens()) {
                String nextLine = innerNextLine();
                if (nextLine == null) {
                    return false;
                }
                tokenizer = new StringTokenizer(nextLine);
            }
            return true;
        }

        public String nextLine() {
            tokenizer = new StringTokenizer("");
            return innerNextLine();
        }

        public String next() {
            hasNext();
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }
    }
    static class QWriter implements Closeable {
        private BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        public void print(Object object) {
            try {
                writer.write(object.toString());
            } catch (IOException e) {
                return;
            }
        }

        public void println(Object object) {
            try {
                writer.write(object.toString());
                writer.write("\n");
            } catch (IOException e) {
                return;
            }
        }

        @Override
        public void close() {
            try {
                writer.close();
            } catch (IOException e) {
                return;
            }
        }


        //public void flush() {
        // }
    }


     public static void main(String[] args) {
        QReader input = new QReader();
        QWriter out = new QWriter();
        int n = input.nextInt();
        int m = input.nextInt();

        LRU lru = new LRU(n);

        for(int i =1;i<=m;i++){
            String s = input.next();
            if(s.equals("put")){
            int a = input.nextInt();
            int b = input.nextInt();
            lru.set(a,b);
            }
            else if(s.equals("get")){
                int c = input.nextInt();
             int d= lru.getvalue(c);
             out.println(d);
            }
        }
        out.close();
     }


}
