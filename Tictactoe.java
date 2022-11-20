package bing;

import java.io.*;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


import java.util.*;

 class Tictactoe {
  private  double shishu;
  private double xushu;


    public Tictactoe(double shi, double xu) {
        shishu = shi;
        xushu = xu;
    }


    public String toString() {
            double dd = Math.sqrt(Math.pow(xushu,2)+Math.pow(shishu,2));
            String str = String.format("%.10f",dd);
            return str;
    }


    public Tictactoe jiafa(Tictactoe b) {
        Tictactoe a = this; // invoking object
        double shi = a.shishu + b.shishu;
        double xu = a.xushu + b.xushu;
        return new Tictactoe(shi, xu);
    }


    public Tictactoe jianfa(Tictactoe b) {
        Tictactoe a = this;
        double s = a.shishu - b.shishu;
        double x = a.xushu - b.xushu;
        return new Tictactoe(s, x);
    }


    public Tictactoe chengfa(Tictactoe b) {
        Tictactoe a = this;
        double s = a.shishu * b.shishu - a.xushu * b.xushu;
        double x = a.shishu * b.xushu + a.xushu * b.shishu;
        return new Tictactoe(s, x);
    }


    public static Tictactoe plus(Tictactoe a, Tictactoe b) {
        double real = a.shishu + b.shishu;
        double imag = a.xushu + b.xushu;
        Tictactoe sum = new Tictactoe(real, imag);
        return sum;
    }


    public boolean equals(Object x) {
        if (x == null)
            return false;
        if (this.getClass() != x.getClass())
            return false;
        Tictactoe that = (Tictactoe) x;
        return (this.shishu == that.shishu) && (this.xushu == that.xushu);
    }


    public int hashCode() {
        return Objects.hash(shishu, xushu);
    }
}

class a{

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
         public double nextDouble() {
            return Double.parseDouble(next());
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


    public static Tictactoe[] fft(Tictactoe[] x) {
        int l = x.length;
        if (l == 1){
            return new Tictactoe[]{x[0]};
        }

        if (l % 2 != 0) {
            int g = x.length;
        if (g == 1)
            return new Tictactoe[]{x[0]};

        Tictactoe[] result = new Tictactoe[g];
        for (int i = 0; i < g; i++) {
            result[i] = new Tictactoe(0, 0);
            for (int k = 0; k < g; k++) {
                double p = -2 * i * k* Math.PI /g;
                Tictactoe m = new Tictactoe(Math.cos(p), Math.sin(p));
                result[i] = result[i].jiafa(x[k].chengfa(m));
            }
        }
        return result;
        }

        Tictactoe[] even = new Tictactoe[l / 2];
        for (int k = 0; k < l / 2; k++) {
            even[k] = x[2 * k];
        }
        Tictactoe[] evenValue = fft(even);

        Tictactoe[] odd = even;
        for (int k = 0; k < l / 2; k++) {
            odd[k] = x[2 * k + 1];
        }
        Tictactoe[] oddValue = fft(odd);


        Tictactoe[] result = new Tictactoe[l];
        for (int k = 0; k < l / 2; k++) {

            double p = -2 * k * Math.PI /l;
            Tictactoe m = new Tictactoe(Math.cos(p), Math.sin(p));
            result[k] = evenValue[k].jiafa(m.chengfa(oddValue[k]));
            result[k + l/ 2] = evenValue[k].jianfa(m.chengfa(oddValue[k]));
        }
        return result;
    }


    public static void main(String[] args) {
         QReader input = new QReader();
        QWriter out = new QWriter();
        int n = input.nextInt();
        double n1 = Math.pow(2.0,n);
        int n2 = (int)n1;
        Tictactoe[] x = new Tictactoe[n2];
       Tictactoe[] result;
        for(int i =0;i<n2;i++){
            x[i]= new Tictactoe(input.nextDouble(),0);
        }
       result = fft(x);
       for (int k =0;k<n2;k++){
       out.println(result[k]);}
       out.close();
    }

}