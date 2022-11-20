package bing;

import java.io.*;


import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.PriorityQueue;

 class serializeanddeserialization implements Comparable<serializeanddeserialization> {
	int l;
    int r;
    int shijian;
    long zhi;
     int yiyongshijian;



    @Override
    public int compareTo(serializeanddeserialization o ) {
        if(this.zhi-o.zhi<0){
            return 1;}
        else if(this.zhi-o.zhi>0){
            return -1;
        }
        return 0;
    }


}

 class bb {
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
        int m = input.nextInt();//总时间，可变
        HashMap<Integer, Integer> map1 = new HashMap<>();
        int a[] = new int[n+1];
        long b[] = new long[n+1];
         double f =Math.log(n+1)/Math.log(2);
        int h =(int)f +1;
        int dp [][]= new int[n+1][h];
        long ak[] = new long[n+1];
        boolean dfg[] = new boolean[n+1];

        for(int i = 1;i<=n;i++){
            a[i]= input.nextInt();
        }
        for (int i = 1;i<=n;i++){
            b[i]= input.nextLong();
        }
        for(int i =1;i<=n;i++){
            map1.put(a[i],i);
        }


         for(int i=1;i<=n;i++){
        dp[i][0]=a[i];}//初始化
    for(int j=1;(1<<j)<=n;j++){
        for(int i=1;i+(1<<j)-1<=n;i++){
            dp[i][j]=Math.min(dp[i][j-1],dp[i+(1<<j-1)][j-1]);}}


          ak[0] = 0;
        for(int i = 0; i < ak.length-1; i++){
            ak[i + 1] = ak[i] + b[i+1];
        }



       PriorityQueue<serializeanddeserialization> pq = new PriorityQueue<serializeanddeserialization>();


        serializeanddeserialization e = new serializeanddeserialization();
        e.l=1;
        e.r = n;
        e.shijian=rmqchaxun(e.l,e.r,dp);
        e.zhi = qianzhuihe(e.l,e.r,ak);
        pq.offer(e);
        long income = 0;

        while (m!=0&&pq.size()!=0){
            serializeanddeserialization x = pq.peek();pq.poll();
           if(m<=x.shijian-x.yiyongshijian){
               income = income + m*(ak[x.r]-ak[x.l-1]);
               x.yiyongshijian = x.yiyongshijian+m;
               m=0;
               break;
           }
           else {
               income = income + (x.shijian-x.yiyongshijian)*(ak[x.r]-ak[x.l-1]);
                m= m-(x.shijian-x.yiyongshijian);
               x.yiyongshijian = x.shijian;
               dfg[map1.get(x.shijian)]= true;
           }
            int w = map1.get(x.shijian);


            if(w-1>0&&!dfg[w-1]){
                serializeanddeserialization y = new serializeanddeserialization();
                y.l=x.l;
                y.r= w-1;
                y.shijian=rmqchaxun(y.l,y.r,dp);
                y.yiyongshijian = y.yiyongshijian+x.yiyongshijian;
                y.zhi = qianzhuihe(y.l,y.r,ak);
                pq.offer(y);
            }
            if(w+1<=n&&!dfg[w+1]){
                serializeanddeserialization d = new serializeanddeserialization();
                d.l = w+1;
                d.r=x.r;
                d.shijian=rmqchaxun(d.l,d.r,dp);
                d.yiyongshijian = d.yiyongshijian+x.yiyongshijian;
                d.zhi = qianzhuihe(d.l,d.r,ak);
                pq.offer(d);
            }






        }
        out.print(income);
        out.close();



    }



public static int rmqchaxun(int l,int r,int dp[][])
{
    double c =Math.log(r-l+1)/Math.log(2);
    int k =(int)c;
    return Math.min(dp[l][k],dp[r-(1<<k)+1][k]);
}

public static long qianzhuihe (int l,int r,long ak[]) {
        return ak[r]-ak[l-1];
    }


}