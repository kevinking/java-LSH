/*
 * The MIT License
 *
 * Copyright 2015 Thibault Debatty.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package info.debatty.java.lsh;

import java.io.Serializable;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

/**
 *
 * @author Thibault Debatty
 */
public class LSHSuperBit extends LSH implements Serializable {
    private SuperBit sb;

    /**
     * Instantiates a LSH instance with s stages (or bands) and b buckets (per 
     * stage), in a space with n dimensions, that uses SuperBit for computing
     * signatures (thus meant for cosine similarity).
     * 
     * @param s stages
     * @param b buckets (per stage)
     * @param n dimensionality
     */
    public LSHSuperBit(int s, int b, int n) {
        super(s, b, n);
        
        // SuberBit code length
        int K = s * b / 2;
        int superbit; // superbit value
        for (superbit = n; superbit >= 1; superbit--) {
            if (K % superbit == 0) {
                break;
            }
        }
        
        this.sb = new SuperBit(n, superbit, K/superbit);
    }
    
    public LSHSuperBit() {
        
    }

    /**
     * Hash (bin) a vector in s stages into b buckets
     * @param vector
     * @return 
     */
    public int[] hash(RealVector vector) {
        return hashSignature(sb.signature(vector));
    }
    
    /**
     * Hash (bin) a vector in s stages into b buckets
     * @param vector
     * @return 
     */
    public int[] hash(double[] vector) {
        return hash(new ArrayRealVector(vector));
    }
    
    /**
     * Hash (bin) a vector in s stages into b buckets
     * @param vector
     * @return 
     */
    public int[] hash(int[] vector) {
        
        double[] d = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            d[i] = (double) vector[i];
        }
        return hash(d);
    }
}
