package org.sample;

import java.util.*;
import java.util.concurrent.*;

public class MatrixMultiply {
    static final int DEFAULT_GRANULARITY = 256;

    static int granularity = DEFAULT_GRANULARITY;

    // public static void main(String[] args) {
    //     int n = 64;
    //     int reps = 1;
    //     int sreps = 1;
    //     try {
    //         if (args.length > 0)
    //           n = Integer.parseInt(args[0]);
    //         if (args.length > 1)
    //           reps = Integer.parseInt(args[1]);
    //         if (args.length > 2)
    //           sreps = Integer.parseInt(args[2]);
    //     }
    //     catch (Exception e) {
    //         // TODO: update this note
    //         System.out.println("Usage: java Integrate <lower bound> <upper bound> \n (for example 2 1 48 5).");
    //         return;
    //     }

    //     float[][] a = new float[n][n];
    //     float[][] b = new float[n][n];
    //     float[][] c = new float[n][n];

    //     final int n2 = n;
    //     Runner.run_with_setup(
    //         (Void v) -> { init(a, b, c, n2); return null; },
    //         (Void v) -> { run(a, b, c, n2); return null; },
    //         reps,
    //         sreps);
    // }

    public static void run(float[][] a, float[][] b, float[][] c, int n) {
        ForkJoinPool.commonPool().invoke(new Multiplier(a, 0, 0, b, 0, 0, c, 0, 0, n));
    }

    // To simplify checking, fill with all 1's. Answer should be all n's.
    static void init(float[][] a, float[][] b, float[][] c, int n) {
      for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
          a[i][j] = 1.0F;
          b[i][j] = 1.0F;
          c[i][j] = 0.0F;
        }
      }
    }

    static void check(float[][] c, int n) {
      for (int i = 0; i < n; i++ ) {
        for (int j = 0; j < n; j++ ) {
          if (c[i][j] != n) {
            throw new Error("Check Failed at [" + i +"]["+j+"]: " + c[i][j]);
          }
        }
      }
    }

    static public class Seq extends RecursiveTask {
        Multiplier a;
        Multiplier b;
        Seq (Multiplier a, Multiplier b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public Object compute () {
            a.compute();
            b.compute();
            return null;
        }
    }

    static class Multiplier extends RecursiveTask {
        final float[][] A;   // Matrix A
        final int aRow;      // first row    of current quadrant of A
        final int aCol;      // first column of current quadrant of A

        final float[][] B;   // Similarly for B
        final int bRow;
        final int bCol;

        final float[][] C;   // Similarly for result matrix C
        final int cRow;
        final int cCol;

        final int size;      // number of elements in current quadrant
    
        Multiplier(float[][] A, int aRow, int aCol,
                   float[][] B, int bRow, int bCol,
                   float[][] C, int cRow, int cCol,
                   int size) {
          this.A = A; this.aRow = aRow; this.aCol = aCol;
          this.B = B; this.bRow = bRow; this.bCol = bCol;
          this.C = C; this.cRow = cRow; this.cCol = cCol;
          this.size = size;
        }

        @Override
        public Object compute() {
            if (size <= granularity) {
              multiplyStride2();
              return null;
            } else {
              int h = size / 2;
              Seq s1 = new Seq(
                  new Multiplier(A, aRow,   aCol,    // A11
                                 B, bRow,   bCol,    // B11
                                 C, cRow,   cCol,    // C11
                                 h),
                  new Multiplier(A, aRow,   aCol+h,  // A12
                                 B, bRow+h, bCol,    // B21
                                 C, cRow,   cCol,    // C11
                                 h));
              s1.fork();
              Seq s2 = new Seq(
                new Multiplier(A, aRow,   aCol,    // A11
                               B, bRow,   bCol+h,  // B12
                               C, cRow,   cCol+h,  // C12
                               h),
                new Multiplier(A, aRow,   aCol+h,  // A12
                               B, bRow+h, bCol+h,  // B22
                               C, cRow,   cCol+h,  // C12
                               h));
              s2.fork();
              Seq s3 = new Seq(
                new Multiplier(A, aRow+h, aCol,    // A21
                               B, bRow,   bCol,    // B11
                               C, cRow+h, cCol,    // C21
                               h),
                new Multiplier(A, aRow+h, aCol+h,  // A22
                               B, bRow+h, bCol,    // B21
                               C, cRow+h, cCol,    // C21
                               h));
              s3.fork();
              Seq s4 = new Seq(
                new Multiplier(A, aRow+h, aCol,    // A21
                               B, bRow,   bCol+h,  // B12
                               C, cRow+h, cCol+h,  // C22
                               h),
                new Multiplier(A, aRow+h, aCol+h,  // A22
                               B, bRow+h, bCol+h,  // B22
                               C, cRow+h, cCol+h,  // C22
                               h));
              s4.fork();

              s1.join();
              s2.join();
              s3.join();
              s4.join();
              return null;
        }
    }

    void multiplyStride2() {
      for (int j = 0; j < size; j+=2) {
        for (int i = 0; i < size; i +=2) {

          float[] a0 = A[aRow+i];
          float[] a1 = A[aRow+i+1];
        
          float s00 = 0.0F; 
          float s01 = 0.0F; 
          float s10 = 0.0F; 
          float s11 = 0.0F; 

          for (int k = 0; k < size; k+=2) {

            float[] b0 = B[bRow+k];

            s00 += a0[aCol+k]   * b0[bCol+j];
            s10 += a1[aCol+k]   * b0[bCol+j];
            s01 += a0[aCol+k]   * b0[bCol+j+1];
            s11 += a1[aCol+k]   * b0[bCol+j+1];

            float[] b1 = B[bRow+k+1];

            s00 += a0[aCol+k+1] * b1[bCol+j];
            s10 += a1[aCol+k+1] * b1[bCol+j];
            s01 += a0[aCol+k+1] * b1[bCol+j+1];
            s11 += a1[aCol+k+1] * b1[bCol+j+1];
          }

          C[cRow+i]  [cCol+j]   += s00;
          C[cRow+i]  [cCol+j+1] += s01;
          C[cRow+i+1][cCol+j]   += s10;
          C[cRow+i+1][cCol+j+1] += s11;
        }
      }
    }
  }
}
