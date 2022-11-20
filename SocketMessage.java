package bing;

import java.io.Serializable;
import java.io.*;
import java.util.*;
public class SocketMessage {

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
        long shang = input.nextLong();
        if(shang==0){
            out.println(0);
            out.close();
        }
        else if(shang==1){
            out.println(1);
            out.close();
        }
        else {
            String x = String.valueOf(shang);
            String str = "a";
            String yushu = "0";
            while (shang != 0) {
                if (shang % 2 == 1) {
                    yushu = "1";
                    str = str + yushu;
                    shang = shang / 2;
                } else {
                    yushu = "0";
                    str = str + yushu;
                    shang = shang / 2;
                }
            }
            String str1 = str.substring(1, str.length());
            String str2 = new StringBuffer(str1).reverse().toString();
            char[] cc = str2.toCharArray();
            String str3 = "1";
            for (int i = 0; i < cc.length - 1; i++) {
                if (cc[i] == cc[i + 1]) {
                    str3 = str3 + "0";
                } else {
                    str3 = str3 + "1";
                }
            }
            out.println(str3);
            out.close();
        }
    }


}