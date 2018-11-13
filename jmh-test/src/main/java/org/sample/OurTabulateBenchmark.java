/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.sample;

import java.util.*;
import java.util.function.*;
import java.util.concurrent.*;
import java.util.stream.*;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.infra.Blackhole;

public class OurTabulateBenchmark {

    public static class ParFor extends RecursiveTask<Void> {
	int hi;
	int lo;
	int grain = 10000;
	Function<Integer, Void> f;
	
	ParFor(int l, int h, Function<Integer, Void> func) {
	  this.lo = l;
	  this.hi = h;
	  this.f = func;
	}

	@Override
	public Void compute() {
	  int length = hi - lo;
	  if (length <= grain) {
	    for(int i = lo;i < hi;i++) {
	      f.apply(i);
	    }
	    return null;
	  }
	  int mid = lo + length/2;
	  ParFor left = new ParFor(lo, mid, f);
	  left.fork();
	  ParFor right = new ParFor(mid, hi, f);
	  right.compute();
	  left.join();
	  return null;
	}
    }

    @Benchmark @BenchmarkMode(Mode.AverageTime)
    public void OurTabulate(Blackhole bh) {
	int n = 10000000;
	char[][] result = new char[n][0];
	ParFor x = new ParFor(0, n, i -> {result[i] = StrGen.generate(i); return null; });
	ForkJoinPool.commonPool().invoke(x);
	bh.consume(result);
    }

}
